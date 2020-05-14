package com.zhyen.base.design_mode.state_mode.demo;

public class LowState extends AbstractState {

    public LowState(ScoreContext c) {
        super.context = c;
        stateName = "不及格";
        score = 0;
    }

    public LowState(AbstractState state) {
        context = state.context;
        stateName = "不及格";
        score = state.score;
    }

    @Override
    public void checkState() {
        if (score >= 90) {
            context.setState(new HighState(this));
        } else if (score >= 60) {
            context.setState(new MiddleState(this));
        }
    }
}
