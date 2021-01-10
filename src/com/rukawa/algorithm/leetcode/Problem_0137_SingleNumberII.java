package com.rukawa.algorithm.leetcode;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-10 17:37
 * @Version：1.0
 */
public class Problem_0137_SingleNumberII {

    /**
     * 只出现一次的数字II
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现了三次。找出那个只出现了一次的元素。
     * 说明：
     * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
     * 示例 1:
     * 输入: [2,2,3,2]
     * 输出: 3
     *
     * 示例 2:
     * 输入: [0,1,0,1,0,1,99]
     * 输出: 99
     */

    public int singleNumber(int[] nums) {
        // 异或运算 ->  x ^ 0 = x;
        //            x ^ x = 0
        // 与运算  ->  x & 0 = 0;
        //            x & 1 = x;
        int oneTimes = 0, twiceTimes = 0;
        for (int num : nums) {
            oneTimes = oneTimes ^ num & (~twiceTimes);
            twiceTimes = twiceTimes ^ num & (~oneTimes);
        }
        return oneTimes;
    }

    public static void main(String[] args) {
        System.out.println(6 ^ 1);
    }
}
