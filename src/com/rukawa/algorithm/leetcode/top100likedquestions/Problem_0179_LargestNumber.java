package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.Arrays;
import java.util.Comparator;
import java.util.StringJoiner;

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

    public String largestNumber2(int[] nums) {
        String[] strings = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strings[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(strings, new MyComparator());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            sb.append(strings[i]);
        }
        String res = sb.toString();
        // 处理开头是0的情况
        char[] chs = res.toCharArray();
        int index = -1;
        for (int i = 0; i < chs.length; i++) {
            if (chs[i] != '0') {
                index = i;
                break;
            }
        }
        return index == -1 ? "0" : res.substring(index);
    }

    /**
     * 传递性
     * 如果a.concat(b) <= b.concat(a), b.concat(c) <= c.concat(b)
     * 能得出a.concat(c) <= c.concat(a) 则说明具有传递性
     *
     * *******证明传递性********
     * 拼接是什么
     * "123".concat("456")  => "123456"
     * 如果是k进制的，则有如下转化
     * 用数学代替，则是 "123" * k^3 + "456"
     *
     * 拼接得出的表达式：a * k^len(b) + b
     * 如果k^len(x)用函数m(x)代替
     *
     * 则上面的表达式可以改写为：
     * 表达式1：a * m(b) + b <= b * m(a) + a
     * 表达式2：b * m(c) + c <= c * m(b) + b
     *
     * 表达式1等式两边先减b，在同时乘c，得到如下表达式3
     * a * m(b) <= b * m(a) + a - b
     * a * m(b) * c <= c * b * m(a) + ac - bc
     *
     * 表达式2等式两边先减b，在同时乘a，得到如下表达式4
     * b * m(c) + c - b <= c * m(b)
     * a * b * m(c) + ac - ba <= a * m(b) * c
     *
     * 此时比较两个等式3和4
     * a * m(b) * c <= c * b * m(a) + ac - bc
     * a * b * m(c) + ac - ba <= a * m(b) * c
     *
     * 得出a * b * m(c) + ac - ba <= c * b * m(a) + ac - bc
     * a * m(c) - a <= c * m(a) - c
     * a * m(c) + c <= c * m(a) + a
     *
     * 此时得出结论：a.concat(c) <= c.concat(a)
     */
    public static class MyComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return (o2 + o1).compareTo(o1 + o2);
        }
    }
}
