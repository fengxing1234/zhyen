package com.zhyen.base.design_mode.visitor_mode;

/**
 * 抽象元素类
 */
public interface IElement {
    void accept(IVisitor visitor);
}
