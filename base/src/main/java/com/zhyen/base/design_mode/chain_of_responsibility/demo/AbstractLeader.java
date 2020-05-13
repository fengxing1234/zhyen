package com.zhyen.base.design_mode.chain_of_responsibility.demo;

/**
 * 领导类
 */
public abstract class AbstractLeader {
    private AbstractLeader next;

    public void setNext(AbstractLeader next) {
        this.next = next;
    }

    public AbstractLeader getNext() {
        return next;
    }

    public abstract void handlerRequest(int days);
}
