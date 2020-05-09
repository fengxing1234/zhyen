package com.zhyen.android.test.test_interview.recursion;

/**
 * 游戏的规则如下，有三根细柱（A、B、C），A柱上套着6个圆盘，圆盘的大小都不一样，它们按照从大到小的顺序自下而上地摆放，现在我们需要把A柱上的圆盘全部移动到B柱上去，并且在移动时有如下约定：
 * <p>
 * 一次只能移动柱子最上端的一个圆盘。
 * 小圆盘上不能放大圆盘
 */
public class HanoiRecursion {

    /**
     * 移动次数
     *
     * @param n 汉诺塔的层数
     * @param x 起始柱A
     * @param y 目标柱B
     * @param z 中转柱C
     * @return
     */
    public int hanoi(int n, char x, char y, char z) {
        if (n == 0) {
            return 0;
        }
        int moveCount;
        //将n-1个圆盘从x移动到z,y为中转柱
        moveCount = hanoi(n - 1, x, y, z);//解出n-1层汉诺塔:H(n-1)
        //移动最大圆盘到目的柱
        moveCount += 1;
        //将n-1个圆盘从z移动到y,x为中转柱
        moveCount += hanoi(n - 1, x, y, z);//解出n-1层汉诺塔:H(n-1)
        return moveCount;
    }
}
