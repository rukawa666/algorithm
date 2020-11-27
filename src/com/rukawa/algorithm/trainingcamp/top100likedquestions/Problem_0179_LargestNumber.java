package com.rukawa.algorithm.trainingcamp.top100likedquestions;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 11:21
 * @Version：1.0
 */
public class Problem_0179_LargestNumber {
    /**
     * 最大数
     * 给定一组非负整数，重新排列它们的顺序使之组成一个最大的整数。
     *
     * 示例 1:
     * 输入: [10,2]
     * 输出: 210
     *
     * 示例 2:
     * 输入: [3,30,34,5,9]
     * 输出: 9534330
     * 说明: 输出结果可能非常大，所以你需要返回一个字符串而不是整数。
     */

    public String largestNumber(int[] nums) {
        String[] strs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strs[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(strs, new MyComparator());
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(str);
        }
        String res = sb.toString();
        char[] str = res.toCharArray();
        int index = -1;
        for (int i = 0; i < str.length; i++) {
            if (str[i] != '0') {
                index = i;
                break;
            }
        }
        return index == -1 ? "0" : res.substring(index);
    }

    public static class MyComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return (o2 + o1).compareTo(o1 + o2);
        }
    }
}
