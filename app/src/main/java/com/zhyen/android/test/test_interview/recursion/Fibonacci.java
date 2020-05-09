package com.zhyen.android.test.test_interview.recursion;

import java.math.BigInteger;

public class Fibonacci {

    public int fibonacci(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1 || n == 2) {//F(1)=1
            return 1;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public int fibonacciClear(int n) {
        return n == 0 ? 0 : n == 1 || n == 2 ? 1 : fibonacciClear(n - 1) + fibonacciClear(n - 2);
    }

    public BigInteger fibonacciN(int n) {
        if (n == 1) {
            return new BigInteger("1");
        }
        /**
         * s1 = 2  s1 = 3
         * s2 = 3  s2 = 5
         * sn = 5  sn = 8
         */
        //f(0)=0;
        BigInteger n1 = new BigInteger("0");
        //f(1)=1;
        BigInteger n2 = new BigInteger("1");
        //记录最终值f(n)
        BigInteger sn = new BigInteger("0");
        for (int i = 0; i < n - 2; i++) {
            sn = n1.add(n2);
            n1 = n2;
            n2 = sn;
        }
        return sn;
    }

    public long fibonacciNormal(int n) {
        if (n <= 2) {
            return 1;
        }
        int n1 = 1, n2 = 1, sn = 0;
        for (int i = 0; i < n - 2; i++) {
            sn = n1 + n2;
            n1 = n2;
            n2 = sn;
        }
        return sn;
    }
}
