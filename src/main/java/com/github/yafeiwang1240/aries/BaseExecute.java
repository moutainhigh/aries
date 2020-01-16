package com.github.yafeiwang1240.aries;

public interface BaseExecute extends Runnable {

    /**
     * 判断当前操作是结束
     * @return
     */
    boolean isFinish();

    /**
     * kill
     * @return
     */
    boolean shutdown();

}
