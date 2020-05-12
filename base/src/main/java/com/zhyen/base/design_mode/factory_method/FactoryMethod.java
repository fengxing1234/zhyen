package com.zhyen.base.design_mode.factory_method;

public class FactoryMethod {
    /**
     * 某系统日志记录器要求支持多种日志记录方式，
     * 如文件记录、数据库记录等，且用户可以根据要求动态选择日志记录方式，
     * 现使用工厂方法模式设计该系统。
     *
     * @param arg
     */
    public static void main(String[] arg) {
        FileLogFactory fileLogFactory = new FileLogFactory();
        ILog fileLog = fileLogFactory.createLog();
        fileLog.writeLog("把日志写进文件");
        DatabaseFactory databaseFactory = new DatabaseFactory();
        ILog databaseLog = databaseFactory.createLog();
        databaseLog.writeLog("把日记写进数据库");
    }
}
