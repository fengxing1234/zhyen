package com.zhyen.base.design_mode.adapter_mode.demo;

//电能适配器
public class ElectricAdapter implements Motor {
    private ElectricMotor electricMotor;

    public ElectricAdapter() {
        electricMotor = new ElectricMotor();
    }

    @Override
    public void drive() {
        electricMotor.electricDrive();
    }
}
