package com.zhyen.base.design_mode.abstract_factory.product_air;

public class HaierAirConditionerImpl implements IAirConditioner {
    @Override
    public void airConditioner() {
        System.out.println("海尔空调");
    }
}
