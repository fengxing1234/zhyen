package com.zhyen.base.design_mode.factory_method;

public class DatabaseLog implements ILog {
    @Override
    public void writeLog(String log) {
        System.out.println("把日志写进数据库 日志内容 = " + log);
    }
}
