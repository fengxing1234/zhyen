package com.zhyen.base.design_mode.command_mode.UML;

public class ConcreteCommandA implements ICommand {
    private ReceiverA receiverA = new ReceiverA();

    @Override
    public void execute() {
        System.out.println("具体命名A execute ...");
        receiverA.action();
    }
}
