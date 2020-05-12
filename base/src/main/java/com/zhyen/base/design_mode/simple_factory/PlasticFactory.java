package com.zhyen.base.design_mode.simple_factory;

public class PlasticFactory {

    public static IPlasticProduct factory(String productName) {
        switch (productName) {
            case "A":
                return new PlasticProductA();
            case "B":
                return new PlasticProductB();
            default:
                return null;
        }
    }

}
