package com.zhyen.base.design_mode.interpreter_mode;

/**
 * 环境角色
 */
public class InterpreterContext {

    private String[] cities = {"韶关", "广州"};
    private String[] persons = {"老人", "妇女", "儿童"};
    private final NonTerminalExpression nonTerminalExpression;

    public InterpreterContext() {
        TerminalExpression cityExpression = new TerminalExpression(cities);
        TerminalExpression personExpression = new TerminalExpression(persons);
        nonTerminalExpression = new NonTerminalExpression(cityExpression, personExpression);
    }

    public void operation(String info) {
        boolean ok = nonTerminalExpression.interpret(info);
        if (ok) System.out.println("您是" + info + "，您本次乘车免费！");
        else System.out.println(info + "，您不是免费人员，本次乘车扣费2元！");
    }
}
