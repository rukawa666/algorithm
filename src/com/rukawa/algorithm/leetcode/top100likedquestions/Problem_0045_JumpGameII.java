package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:33
 * @Version：1.0
 */
public class Problem_0045_JumpGameII {
    /**
     * 跳跃游戏 II
     * 给你一个非负整数数组 nums ，你最初位于数组的第一个位置。
     * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
     * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
     * 假设你总是可以到达数组的最后一个位置。
     *
     * 示例 1:
     * 输入: nums = [2,3,1,1,4]
     * 输出: 2
     * 解释: 跳到最后一个位置的最小跳跃数是 2。
     *      从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
     *
     * 示例 2:
     * 输入: nums = [2,3,0,1,4]
     * 输出: 2
     *  
     * 提示:
     * 1 <= nums.length <= 104
     * 0 <= nums[i] <= 1000
     */
    public int jump(int[] nums) {
        /**
         * 流程：
         *  变量：
         *      step:跳了几步
         *      cur:如果不增加步数，0步以内最远到内  step=3 cur=130 => 3步以内最远能到130
         *      next:如果允许多跳一步，最远能到哪
         */
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int step = 0;
        int cur = 0;
        int next = 0;
        for (int i = 0; i < nums.length; i++) {
            if (cur < i) {
                step++;
                cur = next;
            }
            next = Math.max(next, i + nums[i]);
        }
        return step;
    }
}
