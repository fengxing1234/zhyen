package com.zhyen.base.design_mode.memento_mode.demo;


/**
 * 备忘录
 */
public class Girl {
    //未婚妻
    public String fiancee;

    public Girl(String fiancee) {
        this.fiancee = fiancee;
    }

    public String getFiancee() {
        return fiancee;
    }

    public void setFiancee(String fiancee) {
        this.fiancee = fiancee;
    }
}
