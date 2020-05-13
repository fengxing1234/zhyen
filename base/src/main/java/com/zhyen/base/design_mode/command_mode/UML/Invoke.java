package com.zhyen.base.design_mode.command_mode.UML;

public class Invoke {

    private ICommand command;

    public Invoke(ICommand command) {
        this.command = command;
    }

    public void setCommand(ICommand command) {
        this.command = command;
    }

    public void cell() {
        System.out.println("调用者执行命令cell...");
        command.execute();
    }
}
