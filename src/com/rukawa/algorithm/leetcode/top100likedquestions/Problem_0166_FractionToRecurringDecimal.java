package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.HashMap;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 11:21
 * @Version：1.0
 */
public class Problem_0166_FractionToRecurringDecimal {
    /**
     * 分数到小数
     * 给定两个整数，分别表示分数的分子 numerator 和分母 denominator，以 字符串形式返回小数 。
     * 如果小数部分为循环小数，则将循环的部分括在括号内。
     * 如果存在多个答案，只需返回 任意一个 。
     * 对于所有给定的输入，保证 答案字符串的长度小于 104
     *
     * 示例 1:
     * 输入: numerator = 1, denominator = 2
     * 输出: "0.5"
     *
     * 示例 2:
     * 输入: numerator = 2, denominator = 1
     * 输出: "2"
     *
     * 示例 3:
     * 输入: numerator = 2, denominator = 3
     * 输出: "0.(6)"
     *
     * 提示：
     * -231 <= numerator, denominator <= 231 - 1
     * denominator != 0
     */

    public String fractionToDecimal(int numerator, int denominator) {
        /**
         * 思路：
         *   如果 a/b=c且b*c=a 或者a%b==0 说明是整除
         */
        if (numerator == 0) {
            return "0";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(((numerator > 0) ^ (denominator > 0)) ? "-" : "");

        long num = Math.abs((long) numerator);
        long den = Math.abs((long) denominator);
        builder.append(num / den);
        // num 取模的结果
        num %= den;
        // 没有小数
        if (num == 0) {
            return builder.toString();
        }
        // 否则就是有小数
        builder.append(".");

        /**
         * 小数点后面的数字计算
         * 1. a/b  a*10/b=c a*10%b=d  c就是小数点后第一位的数字 d就是下一轮计算的除数
         * 2. d/b  d*10/b=e d*10%b=f  e就是小数点后第二位的数字 f就是下一轮计算的除数
         * 3. f/b  f*10/b=g f*10%b=a  g就是小数点后第三位的数字 a就是下一轮计算的除数  a是重复出现的，说明此时循环开始了
         */
        // key:余数 value:在builder中出现的位置
        HashMap<Long, Integer> map = new HashMap<>();
        map.put(num, builder.length());
        while (num != 0) {
            num *= 10;
            builder.append(num / den);
            num %= den;
            if (map.containsKey(num)) {
               int position = map.get(num);
               builder.insert(position, "(");
               builder.append(")");
               break;
            } else {
                map.put(num, builder.length());
            }
        }
        return builder.toString();
    }
}
