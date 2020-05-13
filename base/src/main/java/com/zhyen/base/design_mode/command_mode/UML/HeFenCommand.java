package com.zhyen.base.design_mode.command_mode.UML;

import com.zhyen.base.design_mode.command_mode.demo.HeFenChef;

/**
 * 河粉的具体命令
 */
public class HeFenCommand implements IBreakfastCommand {
    private HeFenChef heFenChef = new HeFenChef();

    @Override
    public void cooking() {
        System.out.println("命令河粉厨师 做河粉 ");
        heFenChef.cooking();
    }
}
