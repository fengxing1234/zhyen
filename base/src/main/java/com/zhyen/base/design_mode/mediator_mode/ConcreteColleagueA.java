package com.zhyen.base.design_mode.mediator_mode;

/**
 * 具体同时类
 */
public class ConcreteColleagueA extends AbstractColleague {
    @Override
    public void receive() {
        System.out.println("具体同时类A receive");
    }

    @Override
    public void send() {
        System.out.println("具体同时类A send");
        mediator.relay(this); //请中介者转发
    }
}
