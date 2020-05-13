package com.zhyen.base.design_mode.chain_of_responsibility.UML;

public class ConcreteHandlerA extends AbstractHandler {
    @Override
    public String handlerRequest(String request) {
        return getNext().handlerRequest(request + "我是A 我简单处理下在发给你");
    }
}
