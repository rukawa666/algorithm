package com.rukawa.algorithm.other.sort;

import java.util.Arrays;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-05-06 8:08
 * @Version：1.0
 */
public class QuickSort01 {


    public static void main(String[] args) {
        int[] nums = {2, 1, 7, 8, 5, 4, 9, 3, 6};
        finalSort(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));
    }

    private static void exchange(int[] sum, int i, int j) {
        int temp = sum[i];
        sum[i] = sum[j];
        sum[j] = temp;
    }

    /**
     * 优化版本
     *     1、快速排序对于处理小数组(N<=20)的数组时，快速排序比插入排序要慢，所以再排序小数组时应该切换到插入排序，
     *        面对小数组的通常解决办法是对于小的数组不递归的使用快速排序，而代之如插入排序这样的对小数组有效的排序算法
     *     2、普通的快速排序还有一个确定就是交换一些相同的元素，尤其是我们处理大量重复元素的数组时，快排会做很多的无用功，
     *        所以由此出现三切分快排(三路划分)，在左右游标的基础上，再增加一个游标，用于处理和基准元素相同的元素，
     *        也就是将数组分为三部分：小于当前切分元素的部分，等于当前切分元素的部分，大于当前切分元素的部分
     *
     *  复杂度分析：
     *      时间复杂度：最优：O(logn)提升到O(n)
     *      普通复杂度：根据实际情况而定
     *
     * @param nums
     * @param top
     * @param tail
     */
    public static void finalSort(int[] nums, int top, int tail) {
        if (top >= tail) return;

        int l = top, r = tail, i = top + 1;
        int pivot = nums[top];
        while (i <= r) {
            if (nums[i] > pivot) {
                exchange(nums, i, r--);
            } else if (nums[i] < pivot) {
                exchange(nums, i++, l++);
            } else {
                i++;
            }
        }
        finalSort(nums, top, l - 1);
        finalSort(nums, r + 1, tail);

    }

    public static void sortByMid(int[] nums, int top, int tail) {
        if (top >= tail) return;

        int left = top, right = tail, mid = nums[top + (tail - top) / 2];

        while (left <= right) {
            while (nums[left] < mid) {
                ++left;
            }

            while (nums[right] > mid) {
                --right;
            }

            if (left < right) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                ++left;
                --right;
            }

            if (left == right) {
                ++left;
            }
        }

        sortByMid(nums, top, right);
        sortByMid(nums, left, tail);
    }

    public static void sortByTail(int[] nums, int top, int tail) {
        if (top >= tail) return;

        int left = top, right = tail - 1;
        int pivot = nums[tail];

        while (left < right) {
            while (left < right && nums[left] < pivot) {
                left++;
            }

            while (left < right && nums[right] >= pivot) {
                right--;
            }

            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
        }

        if (nums[left] >= nums[tail]) {
            int temp = nums[left];
            nums[left] = nums[tail];
            nums[tail] = temp;
        } else {
            left++;
        }

        sortByTail(nums, top, left - 1);
        sortByTail(nums, left + 1, tail);
    }


    public static void sortByTop(int[] nums, int top, int tail) {
        if (top >= tail) return;

        int left = top, right = tail;
        int temp = nums[left];  // 数组的第一个元素作为中轴

        while (left < right) {
            // 右半边
            while (left < right && nums[right] >= temp) {
                right--;
            }
            nums[left] = nums[right];

            // 左半边
            while (left < right && nums[left] < temp) {
                left++;
            }
            nums[right] = nums[left];
        }

        if (left == right) nums[left] = temp;

        if (left != top) {
            sortByTop(nums, 0, left - 1);
        }

        if (right != tail) {
            sortByTop(nums, right + 1, nums.length - 1);
        }
    }
}

