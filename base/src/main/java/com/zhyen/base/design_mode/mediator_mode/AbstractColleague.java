package com.zhyen.base.design_mode.mediator_mode;

/**
 * 抽象同时类
 */
public abstract class AbstractColleague {
    protected AbstractMediator mediator;

    protected void setMediator(AbstractMediator mediator) {
        System.out.println("抽象同时类 setMediator");
        this.mediator = mediator;
    }

    public abstract void receive();

    public abstract void send();

}
