package com.zhyen.base.design_mode.factory_method;

public class FileLogFactory implements ILogFactory {

    @Override
    public ILog createLog() {
        return new FileLog();
    }
}
