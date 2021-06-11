package com.zhyen.test;

import java.util.Stack;

/**
 * 用两个栈实现队列
 * <p>
 * 维护两个栈，第一个栈支持插入操作，第二个栈支持删除操作。
 * 根据栈先进后出的特性，我们每次往第一个栈里插入元素后，第一个栈的底部元素是最后插入的元素，第一个栈的顶部元素是下一个待删除的元素。
 * 为了维护队列先进先出的特性，我们引入第二个栈，用第二个栈维护待删除的元素，在执行删除操作的时候我们首先看下第二个栈是否为空。如果为空，我们将第一个栈里的元素一个个弹出插入到第二个栈里，这样第二个栈里元素的顺序就是待删除的元素的顺序，要执行删除操作的时候我们直接弹出第二个栈的元素返回即可。
 * <p>
 * author : fengxing
 * date : 2021/6/9 上午10:30
 * description :
 */
public class CQueue {

    private Stack<Integer> stack1;
    private Stack<Integer> stack2;

    public static void test() {
        CQueue cQueue = new CQueue();
        cQueue.appendTail(4);
        cQueue.appendTail(5);
        cQueue.appendTail(6);
        int deleteValue;
        deleteValue = cQueue.deleteHead();
        System.out.println(deleteValue);
        deleteValue = cQueue.deleteHead();
        System.out.println(deleteValue);
        deleteValue = cQueue.deleteHead();
        System.out.println(deleteValue);
    }

    public CQueue() {
        stack1 = new Stack<Integer>();
        stack2 = new Stack<Integer>();

    }

    /**
     * 分别完成在队列尾部插入整数
     *
     * @param value
     */
    public void appendTail(int value) {
        stack1.push(value);
    }

    public int deleteHead() {
        if (stack1 == null) {
            throw new IllegalArgumentException("stack不可能为null");
        }
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }
        int deleteValue = stack2.pop();
        while (!stack2.isEmpty()) {
            stack1.push(stack2.pop());
        }
        return deleteValue;
    }
}
