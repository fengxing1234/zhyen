package com.zhyen.base.design_mode.memento_mode.demo;

/**
 * 发起人
 */
public class You {
    //未婚妻
    public String fiancee;

    public You() {
    }

    public String getFiancee() {
        return fiancee;
    }

    public void setFiancee(String fiancee) {
        this.fiancee = fiancee;
    }

    public Girl createGirl() {
        return new Girl(fiancee);
    }

    public void restoreGirl(Girl girl) {
        setFiancee(girl.getFiancee());
    }
}
