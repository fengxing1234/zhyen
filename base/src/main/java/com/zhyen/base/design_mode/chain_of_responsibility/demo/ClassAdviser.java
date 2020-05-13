package com.zhyen.base.design_mode.chain_of_responsibility.demo;

/**
 * 班主任
 */
public class ClassAdviser extends AbstractLeader {

    @Override
    public void handlerRequest(int days) {
        if (days <= 2) {
            System.out.println("我是班主任" + days + "天假我在我的能力范围内，批准。");
            return;
        }
        System.out.println("我是班主任" + days + "天假我不在我能力范围内，你问问我领导吧。");
        getNext().handlerRequest(days);
    }
}
