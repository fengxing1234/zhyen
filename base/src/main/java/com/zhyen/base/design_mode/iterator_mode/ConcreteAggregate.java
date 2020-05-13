package com.zhyen.base.design_mode.iterator_mode;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象聚合类
 */
public class ConcreteAggregate implements IAggregate {
    private List<Object> list = new ArrayList();

    @Override
    public void add(Object o) {
        list.add(o);
    }

    @Override
    public void remove(Object o) {
        list.remove(o);
    }

    @Override
    public IIterator getIterator() {
        return new ConcreteIterator(list);
    }
}
