package com.zhyen.base.design_mode.chain_of_responsibility.UML;

/**
 * 抽象处理者
 */
public abstract class AbstractHandler {
    private AbstractHandler next;

    public AbstractHandler getNext() {
        return next;
    }

    public void setNext(AbstractHandler next) {
        this.next = next;
    }

    public abstract String handlerRequest(String request);
}
