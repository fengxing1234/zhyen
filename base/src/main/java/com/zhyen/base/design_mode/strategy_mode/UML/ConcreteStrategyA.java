package com.zhyen.base.design_mode.strategy_mode.UML;

public class ConcreteStrategyA implements IStrategy {
    @Override
    public void strategyMethod() {
        System.out.println("使用实现类A的方法进行计算");
    }
}
