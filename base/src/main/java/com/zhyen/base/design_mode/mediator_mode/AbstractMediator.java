package com.zhyen.base.design_mode.mediator_mode;

/**
 * 抽象中介者
 */
public abstract class AbstractMediator {
    public abstract void register(AbstractColleague colleague);

    //转发
    public abstract void relay(AbstractColleague colleague);
}
