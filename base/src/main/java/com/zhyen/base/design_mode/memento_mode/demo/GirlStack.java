package com.zhyen.base.design_mode.memento_mode.demo;

/**
 * 管理者
 */
public class GirlStack {
    private Girl[] girls = new Girl[5];
    private int top = -1;

    public boolean push(Girl girl) {
        if (top > 4) {
            System.out.println("你太花心了，变来变去的！");
            return false;
        } else {
            girls[++top] = girl;
            return true;
        }
    }

    public Girl pop() {
        if (top <= 0) {
            System.out.println("美女栈空了！");
            return girls[0];
        } else return girls[--top];
    }
}
