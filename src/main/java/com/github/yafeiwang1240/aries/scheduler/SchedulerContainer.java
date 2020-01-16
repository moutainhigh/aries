package com.github.yafeiwang1240.aries.scheduler;

import com.github.yafeiwang1240.aries.ExecutorPoolFactory;
import com.github.yafeiwang1240.aries.factory.ContainerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SchedulerContainer {

    private static Logger logger = LoggerFactory.getLogger(SchedulerContainer.class);

    private SchedulerContainer(){}

    private static Map<Long, Container<Scheduler>> schedulerCache = new HashMap<>();

    private static final int PARALLEL = 5;

    private static ScheduledExecutorService scheduled = ExecutorPoolFactory.newScheduledThreadPool(2);

    private static ThreadPoolExecutor executors = ExecutorPoolFactory.newThreadPoolExecutor(5);

    private static AtomicInteger runNumber = new AtomicInteger();

    static {
        // 加入执行
        scheduled.scheduleAtFixedRate(() -> {
            try {
                if (runNumber.get() < PARALLEL && !schedulerCache.isEmpty()) {
                    synchronized (schedulerCache) {
                        if (schedulerCache.isEmpty()) return;
                        List<Container<Scheduler>> containers = new ArrayList<>(schedulerCache.values());
                        containers.sort((c1, c2) -> {
                            if (c1.isFinish()) {
                                return 1;
                            }
                            if (c2.isFinish()) {
                                return -1;
                            }
                            return (int) (c1.getTime() - c2.getTime());
                        });
                        if (!containers.get(0).isRun()) {
                            executors.execute(containers.get(0));
                            runNumber.getAndIncrement();
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("调度容器错误", e);
            }
        }, 5, 5, TimeUnit.SECONDS);
    }

    static {
        // 清理结束
        scheduled.scheduleAtFixedRate(() -> {
            try {
                synchronized (schedulerCache) {
                    if (schedulerCache.isEmpty()) return;
                    List<Long> remove = null;
                    for (Container<Scheduler> container : schedulerCache.values()) {
                        if (container.isFinish()) {
                            if (remove == null) {
                                remove = new ArrayList<>();
                            }
                            remove.add(container.getExecute().getId());
                        }
                    }
                    if (remove != null && remove.size() > 0) {
                        for (Long id : remove) {
                            schedulerCache.remove(id);
                            runNumber.getAndDecrement();
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("调度容器错误", e);
            }
        }, 5, 5, TimeUnit.SECONDS);
    }

    /**
     * 加入调度系统
     * @param scheduler
     * @return
     */
    public static boolean execute(Scheduler scheduler) {
        if (schedulerCache.containsKey(scheduler.getId())) return false;
        synchronized (schedulerCache) {
            schedulerCache.put(scheduler.getId(), ContainerFactory.newContainer(scheduler));
        }
        return true;
    }

    public static boolean shutdown(Long id) {
        if (!schedulerCache.containsKey(id)) {
            logger.warn("当前该任务不在调度中，id = " + id);
        }
        synchronized (schedulerCache) {
            return schedulerCache.get(id).shutdown();
        }
    }
}
