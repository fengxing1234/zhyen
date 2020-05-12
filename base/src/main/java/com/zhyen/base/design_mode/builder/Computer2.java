package com.zhyen.base.design_mode.builder;

public class Computer2 {
    private String mainBoard;     // 主板
    private String cpu;           // cpu
    private String hd;            // 硬盘
    private String powerSupplier; // 电源
    private String graphicsCard;   // 显卡

    // 其它一些可选配置
    private String mouse; // 鼠标
    private String computerCase; //机箱
    private String mousePad;   //鼠标垫
    private String other;  //其它配件

    public Computer2(ComputerBuilder builder) {
        this.mainBoard = builder.mainBoard;
        this.cpu = builder.cpu;
        this.hd = builder.hd;
        this.powerSupplier = builder.powerSupplier;
        this.graphicsCard = builder.graphicsCard;

        this.mouse = builder.mouse;
        this.computerCase = builder.computerCase;
        this.mousePad = builder.mousePad;
        this.other = builder.other;
    }


    public String show() {
        return "Computer2{" +
                "mainBoard='" + mainBoard + '\'' +
                ", cpu='" + cpu + '\'' +
                ", hd='" + hd + '\'' +
                ", powerSupplier='" + powerSupplier + '\'' +
                ", graphicsCard='" + graphicsCard + '\'' +
                ", mouse='" + mouse + '\'' +
                ", computerCase='" + computerCase + '\'' +
                ", mousePad='" + mousePad + '\'' +
                ", other='" + other + '\'' +
                '}';
    }

    private void apply() {

    }

    public static class ComputerBuilder {
        private String mainBoard;     // 主板
        private String cpu;           // cpu
        private String hd;            // 硬盘
        private String powerSupplier; // 电源
        private String graphicsCard;   // 显卡

        // 其它一些可选配置
        private String mouse; // 鼠标
        private String computerCase; //机箱
        private String mousePad;   //鼠标垫
        private String other;  //其它配件

        public ComputerBuilder() {
        }

        public ComputerBuilder setMainBoard(String mainBoard) {
            this.mainBoard = mainBoard;
            return this;
        }

        public ComputerBuilder setCpu(String cpu) {
            this.cpu = cpu;
            return this;
        }

        public ComputerBuilder setHd(String hd) {
            this.hd = hd;
            return this;
        }

        public ComputerBuilder setPowerSupplier(String powerSupplier) {
            this.powerSupplier = powerSupplier;
            return this;
        }

        public ComputerBuilder setGraphicsCard(String graphicsCard) {
            this.graphicsCard = graphicsCard;
            return this;
        }

        public ComputerBuilder setMouse(String mouse) {
            this.mouse = mouse;
            return this;
        }

        public ComputerBuilder setComputerCase(String computerCase) {
            this.computerCase = computerCase;
            return this;
        }

        public ComputerBuilder setMousePad(String mousePad) {
            this.mousePad = mousePad;
            return this;
        }

        public ComputerBuilder setOther(String other) {
            this.other = other;
            return this;
        }

        public Computer2 build() {
            Computer2 computer2 = new Computer2(this);
            computer2.apply();
            return computer2;
        }
    }
}
