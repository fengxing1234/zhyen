package com.zhyen.base.design_mode.factory_method;

public class DatabaseFactory implements ILogFactory {
    @Override
    public ILog createLog() {
        return new DatabaseLog();
    }
}
