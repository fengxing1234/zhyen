package com.zhyen.base.design_mode.iterator_mode;

/**
 * 抽象聚合
 */
public interface IAggregate {
    void add(Object o);

    void remove(Object o);

    IIterator getIterator();
}
