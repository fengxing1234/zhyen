package com.zhyen.base.design_mode.chain_of_responsibility.UML;

public class ConcreteHandlerB extends AbstractHandler {
    @Override
    public String handlerRequest(String request) {
        return request + " 我是B 我就不处理了";
    }
}
