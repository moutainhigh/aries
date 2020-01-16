package com.github.yafeiwang1240.aries.scheduler;

import com.github.yafeiwang1240.aries.BaseExecute;

public class Scheduler implements BaseExecute {

    private Long id;

    public Long getId() {
        return id;
    }

    @Override
    public boolean isFinish() {
        return false;
    }

    @Override
    public boolean shutdown() {
        return false;
    }

    @Override
    public void run() {

    }
}
