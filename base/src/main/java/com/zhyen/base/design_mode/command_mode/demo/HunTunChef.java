package com.zhyen.base.design_mode.command_mode.demo;

/**
 * 接收者：馄饨厨师
 */
public class HunTunChef implements IChef {
    @Override
    public void cooking() {
        System.out.println("我是馄饨厨师 接收到命令--- 我给你做馄饨");
    }
}
