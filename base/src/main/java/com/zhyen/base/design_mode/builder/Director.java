package com.zhyen.base.design_mode.builder;

/**
 * 导演类、指挥者，负责安排已有模块的顺序，然后通知 Builder 开始建造。
 */
public class Director {
    private AbstractBuilder builder;

    public Director(){

    }
    public Director(AbstractBuilder builder) {
        this.builder = builder;
    }

    public void setBuilder(AbstractBuilder builder) {
        this.builder = builder;
    }

    public Computer createComputer(String cpu, String hardDisk, String mainBoard, String memory, String gpu) {
        builder.setCpu(cpu);
        builder.setMainBoard(mainBoard);
        builder.setHardDisk(hardDisk);
        builder.setMemory(memory);
        builder.setGpu(gpu);
        return builder.build();
    }
}
