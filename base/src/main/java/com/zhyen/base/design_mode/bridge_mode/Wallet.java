package com.zhyen.base.design_mode.bridge_mode;

public class Wallet extends Bag {
    @Override
    public String getName() {
        return color.getColor() + "钱包";
    }
}
