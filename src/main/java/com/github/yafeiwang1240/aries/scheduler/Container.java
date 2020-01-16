package com.github.yafeiwang1240.aries.scheduler;

import com.github.yafeiwang1240.aries.BaseExecute;

import java.util.concurrent.atomic.AtomicBoolean;

public class Container<T extends BaseExecute> implements BaseExecute {

    private T execute;

    private long time;

    private AtomicBoolean run;

    public Container(T execute) {
        this.execute = execute;
        time = System.currentTimeMillis();
        run = new AtomicBoolean();
    }

    public boolean isRun() {
        return run.get();
    }

    @Override
    public boolean isFinish() {
        return execute.isFinish();
    }

    @Override
    public boolean shutdown() {
        return execute.shutdown();
    }

    public long getTime() {
        return time;
    }

    public T getExecute() {
        return execute;
    }

    @Override
    public void run() {
        run.set(true);
        execute.run();
    }
}
