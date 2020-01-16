package com.github.yafeiwang1240.aries;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorPoolFactory {

    public static ThreadPoolExecutor newThreadPoolExecutor(int corePoolSize) {
        ThreadFactory threadFactory = new ExecutorPoolFactory.NameTreadFactory();
        RejectedExecutionHandler handler = new ExecutorPoolFactory.IgnorePolicy();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, corePoolSize, 0, TimeUnit.MICROSECONDS,
                new ArrayBlockingQueue<>(Integer.MAX_VALUE), threadFactory, handler);
        return executor;
    }

    public static ThreadPoolExecutor newThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        ThreadFactory threadFactory = new ExecutorPoolFactory.NameTreadFactory();
        RejectedExecutionHandler handler = new ExecutorPoolFactory.IgnorePolicy();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue, threadFactory, handler);
        return executor;
    }

    public static ThreadPoolExecutor newThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, String executorName) {
        ThreadFactory threadFactory = new ExecutorPoolFactory.NameTreadFactory(executorName);
        RejectedExecutionHandler handler = new ExecutorPoolFactory.IgnorePolicy();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue, threadFactory, handler);
        return executor;
    }

    public static ExecutorService newSingleThreadExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
        return Executors.newScheduledThreadPool(corePoolSize);
    }

    private static class IgnorePolicy implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            System.err.println(r.toString() + " rejected");
        }
    }

    private static class NameTreadFactory implements ThreadFactory {

        public NameTreadFactory() {
        }

        public NameTreadFactory(String mBaseName) {
            this.mBaseName = mBaseName;
        }

        private String mBaseName = "executor-thread-";

        private final AtomicInteger mThreadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, mBaseName + mThreadNum.getAndIncrement());
            System.out.println(t.getName() + " has been created");
            return t;
        }
    }
}
