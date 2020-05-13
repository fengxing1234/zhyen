package com.zhyen.base.design_mode.command_mode.UML;

/**
 * 服务员调用者
 */
public class Waiter {
    private IBreakfastCommand heFenCommand, changFenCommand, hunTunCommand;

    public Waiter() {

    }

    public void setHeFenCommand(IBreakfastCommand heFenCommand) {
        this.heFenCommand = heFenCommand;
    }

    public void setChangFenCommand(IBreakfastCommand changFenCommand) {
        this.changFenCommand = changFenCommand;
    }

    public void setHunTunCommand(IBreakfastCommand hunTunCommand) {
        this.hunTunCommand = hunTunCommand;
    }

    public void chooseHeFen() {
        System.out.println("服务员：这小子点了一份河粉 快点上菜");
        heFenCommand.cooking();
    }

    public void chooseChangFen() {
        System.out.println("服务员：这小子点了一份肠粉 快点上菜");
        changFenCommand.cooking();
    }

    public void chooseHunTun() {
        System.out.println("服务员：这小子点了一份馄饨 快点上菜");
        hunTunCommand.cooking();
    }
}
