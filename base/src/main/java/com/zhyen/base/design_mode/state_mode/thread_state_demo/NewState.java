package com.zhyen.base.design_mode.state_mode.thread_state_demo;

public class NewState extends ThreadState {

    public static final String NEW_STATE = "新建状态";

    public NewState() {
        stateName = NEW_STATE;
        System.out.println("当前线程处于：" + stateName);
    }

    public void start(ThreadContext context) {
        if (NEW_STATE.equals(stateName)) {
            System.out.print("调用start()方法-->");
            context.setState(new RunnableState());
        } else {
            System.out.println("当前线程不是新建状态，不能调用start()方法.");
        }
    }
}
