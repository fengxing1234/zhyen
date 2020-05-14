package com.zhyen.base.design_mode.state_mode.thread_state_demo;

public class RunnableState extends ThreadState {
    public static final String RUNNABLE_STATE = "就绪状态";

    public RunnableState() {
        stateName = RUNNABLE_STATE;
        System.out.println("当前线程处于：" + stateName);
    }
    public void getCPU(ThreadContext context){
        if(RUNNABLE_STATE.equals(stateName)){
            System.out.print("getCPU()方法-->");
            context.setState(new RunningState());
        }else{
            System.out.println("当前线程不是新建状态，不能调用start()方法.");
        }
    }
}
