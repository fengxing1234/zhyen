package com.zhyen.base.design_mode.interpreter_mode;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 终结符表达式
 */
public class TerminalExpression implements IAbstractExpression {
    private HashSet<String> set = new HashSet<String>();

    public TerminalExpression(String[] data) {
        set.addAll(Arrays.asList(data));
    }

    @Override
    public boolean interpret(String info) {
        return set.contains(info);
    }


}
