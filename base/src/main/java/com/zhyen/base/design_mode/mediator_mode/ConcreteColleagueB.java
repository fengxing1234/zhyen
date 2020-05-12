package com.zhyen.base.design_mode.mediator_mode;

/**
 * 具体同时类B
 */
public class ConcreteColleagueB extends AbstractColleague {
    @Override
    public void receive() {
        System.out.println("具体同时类B receive");
    }

    @Override
    public void send() {
        System.out.println("具体同时类B send");
        mediator.relay(this); //请中介者转发

    }
}
