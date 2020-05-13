package com.zhyen.base.design_mode.chain_of_responsibility.demo;

/**
 * 院长
 */
public class Dean extends AbstractLeader {
    @Override
    public void handlerRequest(int days) {
        if (days <= 10) {
            System.out.println("我是院长" + days + "天假批准。");
        } else {
            System.out.println("我是院长" + days + "天假太长，不批");
        }

    }
}
