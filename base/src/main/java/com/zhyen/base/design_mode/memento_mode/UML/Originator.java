package com.zhyen.base.design_mode.memento_mode.UML;

/**
 * 发起人
 */
public class Originator {
    private String state;

    public Originator() {
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Memento createMemento() {
        return new Memento(state);
    }

    public void restoreMemento(Memento memento) {
        setState(memento.getState());
    }
}
