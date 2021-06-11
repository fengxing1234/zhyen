package com.zhyen.test;

import java.util.HashMap;
import java.util.HashSet;

/**
 * author : fengxing
 * date : 2021/5/7 下午6:41
 * description :
 */
public class ArrayDemo {

    public static int operationArrayXOR(int n, int start) {
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = start + 2 * i;
        }
        int sum = nums[0];
        for (int i = 1; i < n; i++) {
            sum = sum ^ nums[i];
        }
        return sum;
    }

    public static int operationArrayXOR2(int n, int start) {
        int xor = 0;
        for (int i = 0; i < n; i++) {
            xor ^= start + 2 * i;
        }
        return xor;
    }

    /**
     * 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。
     * 数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。
     * 请找出数组中任意一个重复的数字。
     *
     * @param num
     * @return
     */
    public static int findRepeatNumber(int[] num) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < num.length; ++i) {
            Integer integer = hashMap.get(num[i]);
            hashMap.put(num[i], integer == null ? 0 : ++integer);
            if (integer != null) {
                return num[i];
            }
        }
        return -1;
    }

    public static int findRepeatNumber2(int[] nums) {
        HashSet<Object> set = new HashSet<>();
        for (int num : nums) {
            //如果集合中已经有该元素了，则返回false。代表重复
            if (!set.add(num)) {
                return num;
            }
        }
        return -1;
    }

    /**
     * 原地置换
     *
     * @param nums
     * @return
     */
    public static int findRepeatNumber3(int[] nums) {
        int i = 0;
        while (i < nums.length) {
            if (i == nums[i]) {
                i++;
                continue;
            }
            if (nums[i] == nums[nums[i]]) {
                return nums[i];
            }
            //交换
            int temp = nums[i];
            nums[i] = nums[nums[i]];
            nums[temp] = temp;

        }
        return -1;
    }
}
