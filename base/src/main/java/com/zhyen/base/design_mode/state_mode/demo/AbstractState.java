package com.zhyen.base.design_mode.state_mode.demo;

public abstract class AbstractState {
    protected ScoreContext context;
    protected String stateName;
    protected int score;

    public abstract void checkState();

    public void addScore(int x) {
        score += x;
        System.out.print("加上：" + x + "分，\t当前分数：" + score);
        checkState();
        System.out.println("分，\t当前状态：" + context.getState().stateName);
    }
}
