package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-25 21:52
 * @Version：1.0
 */
public class Problem_0215_KthLargestElementInAnArray {
    /**
     * 数组中的第K个最大元素
     * 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
     *
     * 示例 1:
     * 输入: [3,2,1,5,6,4] 和 k = 2
     * 输出: 5
     *
     * 示例 2:
     * 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
     * 输出: 4
     *
     * 说明:
     * 你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。
     */
    public int findKthLargest(int[] nums, int k) {
        // k的长度是>=1，转换第k个最小的元素
        // length：7，k：6  第六大元素  => 第二小元素
        // 递归版本
//        return minKth(nums, nums.length + 1 - k);
        // 迭代版本
        return minKth2(nums, nums.length - k);
    }

    public int minKth(int[] nums, int k) {
        return process(nums, 0, nums.length - 1, k - 1);
    }

    public int process(int[] nums, int l, int r, int index) {
        if (l == r) {
            return nums[l];
        }
        int pivot = nums[l + (int)(Math.random() * (r - l + 1))];
        int[] range = netherlandsFlag(nums, l, r, pivot);
        if (index >= range[0] && index <= range[1]) {
            return nums[index];
        } else if (index < range[0]) {
            return process(nums, l, range[0] - 1, index);
        } else {
            return process(nums, range[1] + 1, r, index);
        }
    }

    // 递归改成迭代
    public  int minKth2(int[] nums, int k) {
        int l = 0;
        int r = nums.length - 1;
        int[] range = null;
        int pivot = 0;
        while (l < r) {
            pivot = nums[l + (int) (Math.random() * (r - l + 1))];
            range = netherlandsFlag(nums, l, r, pivot);
            if (k < range[0]) {
                r = range[0] - 1;
            } else if (k > range[1]) {
                l = range[1] + 1;
            } else {
                return pivot;
            }
        }
        return nums[l];
    }

    public int[] netherlandsFlag(int[] nums, int l, int r, int pivot) {
       int less = l - 1;
       int more = r + 1;
       int index = l;
       while (index < more) {
           if (nums[index] < pivot) {
                swap(nums, index++, ++less);
           } else if (nums[index] > pivot) {
               swap(nums, index, --more);
           } else {
               index++;
           }
       }
       return new int[] {less + 1, more - 1};
    }

    public void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
