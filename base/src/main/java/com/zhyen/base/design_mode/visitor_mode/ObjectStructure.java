package com.zhyen.base.design_mode.visitor_mode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 对象结构类
 */
public class ObjectStructure {
    private List<IElement> list = new ArrayList<>();

    public void accept(IVisitor visitor) {
        Iterator<IElement> iterator = list.iterator();
        while (iterator.hasNext()) {
            IElement next = iterator.next();
            next.accept(visitor);
        }
    }

    public void add(IElement element) {
        list.add(element);
    }

    public void remote(IElement element) {
        list.remove(element);
    }

}
