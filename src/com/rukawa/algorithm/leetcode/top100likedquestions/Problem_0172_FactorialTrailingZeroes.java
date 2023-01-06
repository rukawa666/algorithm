package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 11:21
 * @Version：1.0
 */
public class Problem_0172_FactorialTrailingZeroes {
    /**
     * 阶乘后的零
     * 给定一个整数 n，返回 n! 结果尾数中零的数量。
     *
     * 示例 1:
     * 输入: 3
     * 输出: 0
     * 解释: 3! = 6, 尾数中没有零。
     *
     * 示例 2:
     * 输入: 5
     * 输出: 1
     * 解释: 5! = 120, 尾数中有 1 个零.
     * 说明: 你算法的时间复杂度应为 O(log n) 。
     */
    public int trailingZeroes(int n) {
        /**
         * 思路：末尾是0  由2*5得到
         * 而n!中是2的倍数分解为2*某个数
         * 所以得到的2肯定比5多
         * 本质上求n！有几个5的因子
         * 每5个数至少有1个5  25(2个5) 50(2个5) 75(2个5) ... 125(3个5) 所以每25个数多出一个5的因子
         * 所以最后是n/5 + n/25 + n/125 + ...
         */
        int res = 0;
        while (n != 0) {
            n /= 5;
            res += n;
        }
        return res;
    }
}
