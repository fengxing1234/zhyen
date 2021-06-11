package com.zhyen.test;

/**
 * 菲波那切数列
 * author : fengxing
 * date : 2021/6/9 下午2:21
 * description :
 */
public class Fibonacci {
    /**
     * 递归实现斐波那契数列
     * <p>
     * 原理： 把 f(n) 问题的计算拆分成 f(n-1) 和 f(n-2)两个子问题的计算，并递归，以 f(0) 和 f(1) 为终止条件。
     * <p>
     * 缺点： 大量重复的递归计算，例如 f(n) 和 f(n−1) 两者向下递归需要 各自计算 f(n−2) 的值。
     *
     * @param n
     * @return
     */
    public static int fib(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }

    private int[] array = new int[101];


    /**
     * 记忆化递归法
     * <p>
     * 原理： 在递归法的基础上，新建一个长度为 n 的数组，用于在递归时存储 f(0) 至 f(n) 的数字值，重复遇到某数字则直接从数组取用，避免了重复的递归计算。
     * <p>
     * 缺点： 记忆化存储需要使用 O(N) 的额外空间。
     *
     * @param n
     * @return
     */
    public int memoryFib(int n) {
        if (n <= 0 || n >= 100) {
            throw new IllegalArgumentException("参数输入错误");
        }
        if (n == 0 || n == 1) {
            return n;
        }
        if (array[n] != 0) {
            return array[n];
        }
        array[n] = (memoryFib(n - 1) + memoryFib(n - 2)) % 1000000007;
        return array[n];
    }

    /**
     * 动态规划
     * <p>
     * 这道题其实最传统的做法就是使用递归，但是我们知道，若递归深度过大，就会导致栈溢出。
     * <p>
     * 为了解决该问题，我们可以使用动态规划，将每次前两数之和存起来，便于下次直接使用，这样子，我们就把一个栈溢出的问题，变为了单纯的数学加法，大大减少了内存的压力。
     * <p>
     * 从计算效率、空间复杂度上看，动态规划是本题的最佳解法。
     */
    public int dynamicPlanning(int n) {
        if (n == 0 || n == 1) {
            return n;
        }
        int a = 0, b = 1;
        int sum = 0;
        for (int i = 2; i <= n; ++i) {
            sum = (a + b) % 1000000007;
            a = b;
            b = sum;
        }
        return sum;
    }

    /**
     * 动态规划解析：
     * 状态定义： 设 dp 为一维数组，其中 dp[i] 的值代表 斐波那契数列第 i 个数字 。
     * 转移方程： dp[i+1]=dp[i]+dp[i−1] ，即对应数列定义 f(n + 1) = f(n) + f(n - 1);
     * 初始状态： dp[0]=0, dp[1]=1 ，即初始化前两个数字；
     * 返回值： dp[n]，即斐波那契数列的第 n 个数字。
     *
     * @return
     */
    public int dynamicPlanning2(int n) {
        int[] dp = new int[n + 1];
        if (n == 0 || n == 1) return n;
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; ++i) {
            dp[i] = dp[i - 1] + dp[i - 2];
            dp[i] %= 1000000007;
        }
        return dp[n];
    }
}
