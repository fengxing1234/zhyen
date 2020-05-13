package com.zhyen.base.design_mode.command_mode.UML;

import com.zhyen.base.design_mode.command_mode.demo.ChangFenChef;

/**
 * 肠粉的具体命令
 */
public class ChangHenCommand implements IBreakfastCommand {
    private ChangFenChef changFenChef = new ChangFenChef();

    @Override
    public void cooking() {
        System.out.println("命令肠粉厨师 做肠粉 ");
        changFenChef.cooking();
    }
}
