package com.zhyen.base.design_mode.strategy_mode.UML;

public class StrategyContext {
    private IStrategy strategy;

    public IStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(IStrategy strategy) {
        this.strategy = strategy;
    }

    public void strategy(){
        strategy.strategyMethod();
    }
}
