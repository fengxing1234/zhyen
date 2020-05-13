package com.zhyen.base.design_mode.iterator_mode;

/**
 * 抽象迭代器
 */
public interface IIterator {
    Object first();

    Object next();

    boolean hasNext();
}
