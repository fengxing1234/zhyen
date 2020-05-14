package com.zhyen.base.design_mode.bridge_mode;

public abstract class Bag {
    protected IColor color;

    public void setColor(IColor color) {
        this.color = color;
    }

    public abstract String getName();
}
