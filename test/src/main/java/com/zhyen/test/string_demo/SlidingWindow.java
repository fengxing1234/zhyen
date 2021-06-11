package com.zhyen.test.string_demo;

import java.util.HashMap;
import java.util.HashSet;

/**
 * author : fengxing
 * date : 2021/6/8 上午11:00
 * description : 滑动窗口
 */
public class SlidingWindow {

    /**
     * 滑动窗口解题：
     * 无重复字符的最长子串
     *
     * @param s
     */
    public static int lengthOfLongestSubstring(String s) {
        if (s == null) {
            throw new IllegalArgumentException("参数异常：参数不能为null");
        }
        if (s.equals("")) {
            return 0;
        }
        HashMap<Character, Integer> map = new HashMap<>();
        int max = 0, left = 0;
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                Integer index = map.get(c);
                left = index + 1;
            }
            map.put(c, i);
            max = Math.max(max, i - left + 1);//取出结果中最大的Max。
        }
        return max;
    }

    public static int lengthOfLongestSubstring2(String s) {
        if (s == null) {
            throw new IllegalArgumentException("参数异常：参数不能为null");
        }
        if (s.equals("")) {
            return 0;
        }
        int max = 0, start = 0, end = 0;
        HashSet<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (set.contains(c)) {

            }
            set.add(c);
        }
        return max;
    }

    public static int lengthOfLongestSubstring3(String s) {
        if (s.length() == 0) {
            return 0;
        }
        int inLen = s.length();
        int result = 0;
        int start = 0;  // 左指针初始位置
        int end = 0;    // 右指针初始位置
        HashSet<Character> subset = new HashSet<>();
        subset.add(s.charAt(0)); // 先把第0个字符放入Set

        while (start < inLen) {
            // 先判断长度，再判断是否包含字符，避免越界
            // end初始位置是0，0号字符已被加入集合，从下一个字符开始计算
            while (end + 1 < inLen && !subset.contains(s.charAt(end + 1))) {
                subset.add(s.charAt(end + 1));
                end++;
            }
            result = Math.max(result, end - start + 1);
            if (end + 1 == inLen) { // 右指针移动到最后，可以终止计算，不需要再循环
                break;
            }
            subset.remove(s.charAt(start));
            start++;
        }

        return result;
    }
}
