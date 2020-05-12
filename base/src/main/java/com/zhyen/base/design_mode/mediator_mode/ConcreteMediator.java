package com.zhyen.base.design_mode.mediator_mode;

import java.util.ArrayList;
import java.util.List;

/**
 * 具体中介者
 */
public class ConcreteMediator extends AbstractMediator {

    private List<AbstractColleague> colleagues = new ArrayList<>();

    @Override
    public void register(AbstractColleague colleague) {
        System.out.println("具体中介者 register");
        if (!colleagues.contains(colleague)) {
            colleagues.add(colleague);
            colleague.setMediator(this);
        }
    }

    //转发
    @Override
    public void relay(AbstractColleague colleague) {
        System.out.println("具体中介者 转发");
        for (AbstractColleague ob : colleagues) {
            if (!ob.equals(colleague)) {
                ob.receive();
            }
        }
    }
}
