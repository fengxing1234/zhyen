package com.zhyen.base.design_mode.builder;

public class PlayGameBuilder extends AbstractBuilder {
    //private Computer mComputer = new Computer();

    @Override
    void setCpu(String cpu) {
        mComputer.setCpu(cpu);
    }

    @Override
    void setMainBoard(String board) {
        mComputer.setMainBoard(board);
    }

    @Override
    void setHardDisk(String hardDisk) {
        mComputer.setHardDisk(hardDisk);
    }

    @Override
    void setMemory(String memory) {
        mComputer.setMemory(memory);
    }

    @Override
    void setGpu(String gpu) {
        mComputer.setGpu(gpu);
    }

    @Override
    Computer build() {
        return mComputer;
    }
}
