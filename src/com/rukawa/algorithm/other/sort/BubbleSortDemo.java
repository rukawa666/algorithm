package com.rukawa.algorithm.other.sort;

import java.util.Arrays;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-05-03 16:00
 * @Version：1.0
 */
public class BubbleSortDemo {

    public static void main(String[] args) {
        int[] nums = {7,1,2,6,5,3,4};
        sort(nums);
        System.out.println(Arrays.toString(nums));
    }

    public static void sort(int[] nums) {
        // 标记每轮交换中是否发生了交换
        boolean hasChange = true;
        for (int i = 0; i < nums.length - 1&&hasChange; ++i) {
            hasChange = false;
            for (int j = 0; j < nums.length - 1 - i; ++j) {
                if (nums[j] > nums[j + 1]) {
                    nums[j] = nums[j] ^ nums[j + 1];
                    nums[j + 1] = nums[j] ^ nums[j + 1];
                    nums[j] = nums[j] ^ nums[j + 1];
                    hasChange = true;
                }
            }
        }
    }
}
