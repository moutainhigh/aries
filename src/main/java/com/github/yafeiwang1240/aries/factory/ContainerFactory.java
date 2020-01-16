package com.github.yafeiwang1240.aries.factory;

import com.github.yafeiwang1240.aries.BaseExecute;
import com.github.yafeiwang1240.aries.scheduler.Container;

/**
 * container factory
 * @author wangyafei
 */
public class ContainerFactory {
    private ContainerFactory(){}

    public static <T extends Runnable & BaseExecute> Container<T> newContainer(T runnable) {
        return new Container<T>(runnable);
    }
}
