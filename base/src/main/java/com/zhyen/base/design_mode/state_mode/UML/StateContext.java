package com.zhyen.base.design_mode.state_mode.UML;

public class StateContext {

    private AbstractState state;

    public StateContext() {
        this.state = new ConcreteStateA();
    }

    public AbstractState getState() {
        return state;
    }

    public void setState(AbstractState state) {
        this.state = state;
    }

    public void handle() {
        state.handle(this);
    }
}
