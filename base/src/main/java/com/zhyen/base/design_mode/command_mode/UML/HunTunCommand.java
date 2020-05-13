package com.zhyen.base.design_mode.command_mode.UML;

import com.zhyen.base.design_mode.command_mode.demo.HunTunChef;

/**
 * 馄饨的具体命令
 */
public class HunTunCommand implements IBreakfastCommand {
    private HunTunChef hunTunChef = new HunTunChef();

    @Override
    public void cooking() {
        System.out.println("命令馄饨厨师 做馄饨 ");
        hunTunChef.cooking();
    }
}
