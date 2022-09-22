package com.rukawa.algorithm.leetcode.highfrequency;

/**
 * create by hqh on 2022/3/1
 */
public class Problem_0027_RemoveElement {

    public int removeElement(int[] nums, int val) {
        int l = 0;
        int r = nums.length;
        while (l < r) {
            if (nums[l] == val) {
                nums[l] = nums[r - 1];
                r--;
            } else {
                l++;
            }
        }
        return l;
    }


    public int removeElement1(int[] nums, int val) {
        int left = 0;
        int right = nums.length;
        while (left < right) {
            if (nums[left] == val) {
                nums[left] = nums[right - 1];
                right--;
            } else {
                left++;
            }
        }
        return left;
    }
}
