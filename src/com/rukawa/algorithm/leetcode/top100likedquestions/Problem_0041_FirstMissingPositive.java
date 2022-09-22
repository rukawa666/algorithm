package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:33
 * @Version：1.0
 */
public class Problem_0041_FirstMissingPositive {
    /**
     * 缺失的第一个正数
     * 给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
     * 请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
     *
     * 示例 1：
     * 输入：nums = [1,2,0]
     * 输出：3
     *
     * 示例 2：
     * 输入：nums = [3,4,-1,1]
     * 输出：2
     *
     * 示例 3：
     * 输入：nums = [7,8,9,11,12]
     * 输出：1
     *
     * 提示：
     * 1 <= nums.length <= 5 * 105
     * -231 <= nums[i] <= 231 - 1
     */
    public int firstMissingPositive(int[] nums) {
        /**
         * 在数组上，0位置放1，1位置放2，。。。
         * 每个位置i放i+1的数，哪个位置做不到了，它就是缺的最小正整数
         * 分成两个区域：L-1是有效区的右边界  R是垃圾区的左边界
         * R最好预期，代表最终收集到的值让返回值尽量大：0～5的位置，1～6的数字只收集一个，而且不缺，最终R返回7
         *
         * 假设此时l来到17位置，说明有效区0～16的位置分别填写的是1～17的数字
         *        r来到31位置，说明最好预期r+1=32，要搞定在0～31位置搞定1～17的数字出现一次
         * 此时17位置的数字什么时候属于垃圾？
         *   情况1：数字<18,因为1～17的数字已经填过了
         *   情况2：数字>32,因为在r位置要填写32
         *   情况3：如果是23，要填写到22位置，但是22位置已经有了22，此时的23是垃圾
         *         如果22位置不是23，则把17位置和22位置交换，。。。
         */
        int l = 0;
        int r = nums.length;
        while (l != r) {
            // 有效区域
            if (nums[l] == l + 1) {
                l++;
            } else if (nums[l] <= l || nums[l] > r || nums[l] == nums[nums[l] - 1]) {
                // 无效区域
                swap(nums, l, --r);
            } else {
                swap(nums, l , nums[l] - 1);
            }
        }
        return l + 1;
    }

    public void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
