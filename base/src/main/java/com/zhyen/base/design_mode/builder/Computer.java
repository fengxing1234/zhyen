package com.zhyen.base.design_mode.builder;

public class Computer {
    //CPU
    private String mCpu;
    //主板
    private String mMainBoard;
    //硬盘
    private String mHardDisk;
    //内存
    private String mMemory;
    //显卡
    private String mGpu;

    public String getCpu() {
        return mCpu;
    }

    public void setCpu(String cpu) {
        this.mCpu = cpu;
    }

    public String getMainBoard() {
        return mMainBoard;
    }

    public void setMainBoard(String mainBoard) {
        this.mMainBoard = mainBoard;
    }

    public String getHardDisk() {
        return mHardDisk;
    }

    public void setHardDisk(String hardDisk) {
        this.mHardDisk = hardDisk;
    }

    public String getMemory() {
        return mMemory;
    }

    public void setMemory(String memory) {
        this.mMemory = memory;
    }

    public String getGpu() {
        return mGpu;
    }

    public void setGpu(String gpu) {
        this.mGpu = gpu;
    }

    public String show() {
        return "开始组装电脑 " + "\n" +
                "CPU = " + mCpu + "\n" +
                "主板 = " + mMainBoard + "\n" +
                "硬盘 = " + mHardDisk + "\n" +
                "内存 = " + mMemory + "\n" +
                "显卡 = " + mGpu + "\n" +
                "电脑组装完毕";

    }
}
