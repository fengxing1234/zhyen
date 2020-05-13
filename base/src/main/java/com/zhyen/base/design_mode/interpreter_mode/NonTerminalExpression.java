package com.zhyen.base.design_mode.interpreter_mode;

/**
 * 非终结符表达式
 */
public class NonTerminalExpression implements IAbstractExpression {
    private IAbstractExpression city;
    private IAbstractExpression person;

    public NonTerminalExpression(IAbstractExpression city, IAbstractExpression person) {
        this.city = city;
        this.person = person;
    }

    @Override
    public boolean interpret(String info) {
        String[] s = info.split("的");
        return city.interpret(s[0]) && person.interpret(s[1]);
    }
}
