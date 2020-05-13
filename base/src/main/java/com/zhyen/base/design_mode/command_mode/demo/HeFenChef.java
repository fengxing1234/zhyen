package com.zhyen.base.design_mode.command_mode.demo;
/**
 * 接收者：河粉厨师
 */
public class HeFenChef implements IChef {
    @Override
    public void cooking() {
        System.out.println("我是河粉厨师 接到命令--- 我给你做河粉");
    }
}
