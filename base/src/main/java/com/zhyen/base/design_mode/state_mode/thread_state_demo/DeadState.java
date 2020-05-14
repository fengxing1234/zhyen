package com.zhyen.base.design_mode.state_mode.thread_state_demo;

public class DeadState extends ThreadState {
    public DeadState() {
        stateName = "死亡状态";
        System.out.println("当前线程处于：死亡状态.");
    }
}
