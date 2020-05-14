package com.zhyen.base.design_mode.bridge_mode;

public class HandBag extends Bag {
    @Override
    public String getName() {
        return color.getColor() + "挎包";
    }
}
