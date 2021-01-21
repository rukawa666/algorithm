package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-25 21:55
 * @Version：1.0
 */
public class Problem_0300_LongestIncreasingSubsequence {

    /**
     * 最长上升子序列
     * 给定一个无序的整数数组，找到其中最长上升子序列的长度。
     *
     * 示例:
     * 输入: [10,9,2,5,3,7,101,18]
     * 输出: 4
     * 解释: 最长的上升子序列是 [2,3,7,101]，它的长度是 4。
     *
     * 说明:
     * 可能会有多种最长上升子序列的组合，你只需要输出对应的长度即可。
     * 你算法的时间复杂度应该为 O(n2) 。
     * 进阶: 你能将算法的时间复杂度降低到 O(n log n) 吗?
     */

    /**
     * eg:
     *   arr [3 2 4 5 1 3]
     *   dp  [1 1 2 3 1 2]
     *   ends[1 3 5]
     *   1、有效区0~0，i=3，在ends找到最左的大于等于3的数，找不到则扩充ends，此时ends[0]=3,dp[0]=1
     *   2、有效区0~1，i=2，在ends找到最左的大于等于2的数，此时找到为3，则更新ends[0]=2,此时ends中只有一个数，dp[1]=1
     *   3、有效区0~2，i=4，在ends找到最左的大于等于4的数，此时找不到，扩充有效区，ends[1]=4,此时ends包含自己在内左边有2个数，dp[2]=2
     *   4、有效区0~3，i=5，在ends找到最左的大于等于5的数，此时找不到，扩充有效区，ends[2]=5,此时ends包含自己在内左边有3个数，dp[3]=3
     *   5、有效区0~4，i=1，在ends找到最左的大于等于1的数，此时找到为2，更新ends[0]=1，此时ends包含自己在内左边有1个数，dp[4]=1
     *   6、有效区0~5，i=3，在ends找到最左的大于等于3的数，此时找到为4，更新ends[1]=3，此时ends包含自己在内左边有2个数，dp[5]=2
     */
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int[] ends = new int[nums.length];
        ends[0] = nums[0];
        int right = 0;
        int l = 0;
        int r = 0;
        int m = 0;
        for (int i = 1; i < nums.length; i++) {
            l = 0;
            r = right;
            while (l <= r) {
                m = (l + r) / 2;
                if (nums[i] > ends[m]) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }
            right = Math.max(right, l);
            ends[l] = nums[i];
        }
        return right + 1;
    }
}
