package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 11:21
 * @Version：1.0
 */
public class Problem_0169_MajorityElement {
    /**
     * 多数元素
     * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。
     * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
     *
     * 示例 1:
     * 输入: [3,2,3]
     * 输出: 3
     *
     * 示例 2:
     * 输入: [2,2,1,1,1,2,2]
     * 输出: 2
     * @param nums
     * @return
     */

    public int majorityElement(int[] nums) {
        int cand = 0;
        int HP = 0;
        for (int i = 0; i < nums.length; i++) {
            if (HP == 0) {
                cand = nums[i];
                HP = 1;
            } else if (nums[i] == cand){
                HP++;
            } else {
                HP--;
            }
        }
        return cand;
    }
}
