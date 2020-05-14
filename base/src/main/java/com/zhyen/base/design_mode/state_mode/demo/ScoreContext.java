package com.zhyen.base.design_mode.state_mode.demo;

public class ScoreContext {
    private AbstractState state;

    public ScoreContext() {
        state = new LowState(this);
    }

    public AbstractState getState() {
        return state;
    }

    public void setState(AbstractState state) {
        this.state = state;
    }

    public void add(int score) {
        state.addScore(score);
    }
}
