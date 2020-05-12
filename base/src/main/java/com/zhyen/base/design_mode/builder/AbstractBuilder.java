package com.zhyen.base.design_mode.builder;

public abstract class AbstractBuilder {
    protected Computer mComputer = new Computer();

    //设置CPU
    abstract void setCpu(String cpu);

    //设置主板
    abstract void setMainBoard(String board);

    //设置硬盘
    abstract void setHardDisk(String hardDisk);

    //设置内存
    abstract void setMemory(String memory);

    //设置显卡
    abstract void setGpu(String gpu);

    abstract Computer build();
}
