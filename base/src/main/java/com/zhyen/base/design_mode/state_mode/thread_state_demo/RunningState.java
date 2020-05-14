package com.zhyen.base.design_mode.state_mode.thread_state_demo;

public class RunningState extends ThreadState {
    public static final String RUNNING_STATE = "运行状态";

    public RunningState() {
        stateName = RUNNING_STATE;
        System.out.println("当前线程处于：" + stateName);
    }

    public void suspend(ThreadContext hj) {
        System.out.print("调用suspend()方法-->");
        if (RUNNING_STATE.equals(stateName)) {
            hj.setState(new BlockState());
        } else {
            System.out.println("当前线程不是运行状态，不能调用suspend()方法.");
        }
    }

    public void stop(ThreadContext hj) {
        System.out.print("调用stop()方法-->");
        if (RUNNING_STATE.equals(stateName)) {
            hj.setState(new DeadState());
        } else {
            System.out.println("当前线程不是运行状态，不能调用stop()方法.");
        }
    }
}
