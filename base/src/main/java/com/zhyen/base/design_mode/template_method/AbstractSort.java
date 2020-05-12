package com.zhyen.base.design_mode.template_method;

public abstract class AbstractSort {

    public void printSort(int[] array) {
        sort(array);
        printList(array);
    }

    private void printList(int[] array) {
        System.out.println("排序结果：");
        for (int i = 0; i < array.length; i++) {
            System.out.printf("%3s", array[i]);
        }

    }

    protected abstract void sort(int[] array);
}
