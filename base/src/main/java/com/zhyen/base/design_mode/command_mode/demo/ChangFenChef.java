package com.zhyen.base.design_mode.command_mode.demo;

/**
 * 接收者：肠粉厨师
 */
public class ChangFenChef implements IChef {
    @Override
    public void cooking() {
        System.out.println("我是肠粉厨师 接到命令--- 我给你做肠粉");
    }
}
