package com.zhyen.base.design_mode.adapter_mode.UML;

public class ClassAdapter extends Adapter implements ITarget {
    @Override
    public void request() {
        specificRequest();
    }
}
