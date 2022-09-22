package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-06 9:26
 * @Version：1.0
 */
public class Problem_0066_PlusOne {
    /**
     * 加一
     * 给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。
     * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
     * 你可以假设除了整数 0 之外，这个整数不会以零开头。
     *
     * 示例 1:
     * 输入: [1,2,3]
     * 输出: [1,2,4]
     * 解释: 输入数组表示数字 123。
     *
     * 示例 2:
     * 输入: [4,3,2,1]
     * 输出: [4,3,2,2]
     * 解释: 输入数组表示数字 4321。
     */

    public int[] plusOne(int[] digits) {
        int n = digits.length;
        for (int i = n - 1; i >= 0; i--) {
            // 如果最后一个数字小于9，则最后一位+1，直接返回
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }
            // 当前数字等于9，+1之后变成0，前一位去加1
            digits[i] = 0;
        }
        // 如果能计算到最后，跳出循环，说明最高位置是0，产生一个进位
        // 产生一个n+1的格子，0位置是1，其余位置是0
        int[] ans = new int[n + 1];
        ans[0] = 1;
        return ans;
    }
}
