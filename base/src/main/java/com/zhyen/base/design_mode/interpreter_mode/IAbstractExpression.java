package com.zhyen.base.design_mode.interpreter_mode;

/**
 * 抽象表达式
 * 文法规则
 * <expression> ::= <city>的<person>
 * <city> ::= 韶关|广州
 * <person> ::= 老人|妇女|儿童
 */
public interface IAbstractExpression {
    //解释方法
    boolean interpret(String info);
}
