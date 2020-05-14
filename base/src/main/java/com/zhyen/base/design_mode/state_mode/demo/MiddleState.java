package com.zhyen.base.design_mode.state_mode.demo;

public class MiddleState extends AbstractState {

    public MiddleState(AbstractState state) {
        context = state.context;
        stateName = "中等";
        score = state.score;
    }

    @Override
    public void checkState() {
        if (score < 60) {
            context.setState(new LowState(this));
        } else if (score >= 90) {
            context.setState(new HighState(this));
        }
    }
}
