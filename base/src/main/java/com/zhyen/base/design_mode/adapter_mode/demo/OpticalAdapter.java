package com.zhyen.base.design_mode.adapter_mode.demo;

//光能适配器
public class OpticalAdapter implements Motor {
    private OpticalMotor opticalMotor;

    public OpticalAdapter() {
        opticalMotor = new OpticalMotor();
    }

    @Override
    public void drive() {
        opticalMotor.opticalMotor();
    }
}
