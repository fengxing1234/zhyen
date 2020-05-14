package com.zhyen.base.design_mode.proxy.dynamic;

public class Buyer1 implements ISubject {
    @Override
    public void buy() {
        System.out.println("小成要买Mac");
    }
}
