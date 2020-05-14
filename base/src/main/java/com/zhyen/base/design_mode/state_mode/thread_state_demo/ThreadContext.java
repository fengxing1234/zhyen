package com.zhyen.base.design_mode.state_mode.thread_state_demo;

public class ThreadContext {
    private ThreadState state;

    public ThreadContext() {
        this.state = new NewState();
    }

    public ThreadState getState() {
        return state;
    }

    public void setState(ThreadState state) {
        this.state = state;
    }

    public void start() {
        ((NewState) state).start(this);
    }

    public void getCPU() {
        ((RunnableState) state).getCPU(this);
    }

    public void suspend() {
        ((RunningState) state).suspend(this);
    }

    public void stop() {
        ((RunningState) state).stop(this);
    }

    public void resume() {
        ((BlockState) state).resume(this);
    }
}
