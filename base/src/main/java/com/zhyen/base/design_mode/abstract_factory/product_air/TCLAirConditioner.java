package com.zhyen.base.design_mode.abstract_factory.product_air;

public class TCLAirConditioner implements IAirConditioner {
    @Override
    public void airConditioner() {
        System.out.println("TCL空调");
    }
}
