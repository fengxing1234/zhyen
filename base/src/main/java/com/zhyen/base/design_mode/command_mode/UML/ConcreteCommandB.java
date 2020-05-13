package com.zhyen.base.design_mode.command_mode.UML;

public class ConcreteCommandB implements ICommand {
    private ReceiverB receiverB = new ReceiverB();

    @Override
    public void execute() {
        System.out.println("具体命名B execute ...");
        receiverB.action();
    }
}
