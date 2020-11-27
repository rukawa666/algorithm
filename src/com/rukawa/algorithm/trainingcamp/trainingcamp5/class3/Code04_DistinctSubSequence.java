package com.rukawa.algorithm.trainingcamp.trainingcamp5.class3;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-11-09 7:55
 * @Version：1.0
 */
public class Code04_DistinctSubSequence {
    /**
     * 给定一个字符串s，求s中有多少个字面值不相同的子序列
     */

    // 00:44:26
    public static int distinctSubSequence1(String s) {
        /**
         * 思路：
         * 1、假设字符是a~z中的字符，也可以假设是256个字符
         * 2、字面值以某个字符结尾，有多少种不同的字面值
         * 3、建立总的字面值初始值=1，代表空字符串
         */
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        // a~z
        // count[0] = a的统计
        // ...
        // count[25] = z的统计
        int[] count = new int[26];
        int all = 1; // 算空集
        for (char x : str) {
            /**
             * 例子："abcb"
             *  1、"a" -> 可能性："a",
             *     add=1-0=1, all=2, count['a']=1
             *  2、"b" -> 可能性："ab","a",
             *      add=2-0=2, all=4, count['b']=2
             *  3、"c" -> 可能性："ac", "bc", "abc","c"
             *      add=4-0=4, all=8, count['c']=4
             *  4、"b" -> 可能性："ab","bc","abc","b"
             *      add=8-2=6, all=14, count['b']=4
             */
            int add = all - count[x - 'a'];
            all += add;
            count[x - 'a'] += add;
        }
        return all;
    }

    public static void main(String[] args) {
        String s = "abcb";
        System.out.println(distinctSubSequence1(s));
    }
}
