package com.zhyen.base.design_mode.state_mode.demo;

public class HighState extends AbstractState {
    public HighState(AbstractState state) {
        context = state.context;
        stateName = "优秀";
        score = state.score;
    }

    @Override
    public void checkState() {
        if (score < 60) {
            context.setState(new LowState(this));
        } else if (score < 90) {
            context.setState(new MiddleState(this));
        }
    }
}
