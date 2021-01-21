package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 11:21
 * @Version：1.0
 */
public class Problem_0171_ExcelSheetColumnNumber {
    /**
     * Excel表列序号
     * 给定一个Excel表格中的列名称，返回其相应的列序号。
     *
     * 例如，
     *     A -> 1
     *     B -> 2
     *     C -> 3
     *     ...
     *     Z -> 26
     *     AA -> 27
     *     AB -> 28
     *     ...
     * 示例 1:
     * 输入: "A"
     * 输出: 1
     *
     * 示例 2:
     * 输入: "AB"
     * 输出: 28
     *
     * 示例 3:
     * 输入: "ZY"
     * 输出: 701
     */
    public int titleToNumber(String s) {
        char[] str = s.toCharArray();
        int res = 0;
        for (int i = 0; i < str.length; i++) {
            res = res * 26 + (str[i] - 'A') + 1;
        }
        return res;
    }
}
