package com.zhyen.test;


public class TestMain {

    public static void main(String[] arg) {
//        Scanner s = new Scanner(System.in);
//        //输入&字符结束程序
//        while (!s.hasNext("&")) {
//            String line = s.nextLine();
//            System.out.println("enter first parameter:" + line);
//
//            log("结果 = ");
//        }
        int[] a = new int[]{7,6,5,4,3,2,1,0,7};
        log("结果 = " + ArrayDemo.findRepeatNumber3(a));
    }


    public static void log(String msg) {
        System.out.println(msg);
    }
}
