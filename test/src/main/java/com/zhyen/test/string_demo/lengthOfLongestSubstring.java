package com.zhyen.test.string_demo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * author : fengxing
 * date : 2021/5/13 下午2:06
 * description :
 */
public class lengthOfLongestSubstring {
    /**
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     * <p>
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     *
     * @param first
     * @return
     */
    private static String lengthOfLongestSubstring(String first) {
        int max = 0;
        HashMap<Integer, ArrayList<Character>> hashMap = new HashMap<>();
        c:
        for (int i = 0; i < first.length(); ++i) {
            ArrayList<Character> list = new ArrayList<>();
            char start = first.charAt(i);
            list.add(start);
            b:
            for (int j = i + 1; j < first.length(); ++j) {
                char next = first.charAt(j);
                if (!list.contains(next)) {
                    list.add(first.charAt(j));
                } else {
                    break b;
                }
            }
            max = Math.max(max, list.size());
            hashMap.put(list.size(), list);
        }
        ArrayList<Character> characters = hashMap.get(max);
        String a = "";
        for (char c : characters) {
            a += c + "";
        }
        return a;
    }

}
