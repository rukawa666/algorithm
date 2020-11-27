package com.rukawa.algorithm.other.sort;

import java.util.Arrays;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-05-03 16:13
 * @Version：1.0
 */
public class InsertionSort {

    public static void main(String[] args) {
        int[] nums = {7,1,2,6,5,3,4};
        sort(nums);
        System.out.println(Arrays.toString(nums));
    }

    public static void sort(int[] nums) {
        for (int i = 1, j, current; i < nums.length; ++i) {
            current = nums[i];
            for (j = i - 1; j >= 0 && nums[j] > current; --j) {
                nums[j + 1] = nums[j];
            }
            nums[j + 1] = current;
        }
    }
}
