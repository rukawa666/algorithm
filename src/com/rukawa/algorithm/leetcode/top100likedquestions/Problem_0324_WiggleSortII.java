package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-11 9:04
 * @Version：1.0
 */
public class Problem_0324_WiggleSortII {
    /**
     * 摆动排序 II
     * 给定一个无序的数组nums，将它重新排列成nums[0] < nums[1] > nums[2] < nums[3]... 的顺序。
     *
     * 示例 1:
     * 输入: nums = [1, 5, 1, 1, 6, 4]
     * 输出: 一个可能的答案是 [1, 4, 1, 5, 1, 6]
     *
     * 示例 2:
     * 输入: nums = [1, 3, 2, 2, 3, 1]
     * 输出: 一个可能的答案是 [2, 3, 1, 3, 1, 2]
     * 说明:
     * 你可以假设所有输入都会得到有效的结果。
     *
     * 进阶:
     * 你能用 O(n) 时间复杂度和 / 或原地 O(1) 额外空间来实现吗？
     */
    public void wiggleSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        int n = nums.length;
        findIndexNum(nums, 0, nums.length - 1, n / 2);

        if ((n & 1) == 0) {

        } else {

        }
    }

    public static int findIndexNum(int[] arr, int L, int R, int index) {
        int pivot = 0;
        int[] range = null;
        while (L < R) {
            pivot = arr[L + (int)(Math.random() * ( R - L + 1))];
            range = partition(arr, L, R, pivot);
            if (index >= range[0] && index <= range[1]) {
                return arr[index];
            } else if (index < range[0]) {
                R = range[0] - 1;
            } else {
                L = range[1] + 1;
            }
        }
        return arr[L];
    }

    public static int[] partition(int[] arr, int L, int R, int pivot) {
        int less = L - 1;
        int more = R + 1;
        int cur = L;
        while (cur < more) {
            if (arr[cur] < pivot) {
                swap(arr, ++ less, cur++);
            } else if (arr[cur] > pivot) {
                swap(arr, cur, --more);
            } else {
                cur++;
            }
        }
        return new int[] {less + 1, more - 1};
    }

    public static void shuffle(int[] nums, int L, int R) {

    }

    public static void cycles(int[] nums, int base, int bloom, int k) {

    }

    public static void rotate(int[] arr, int l, int m, int r) {

    }

    public static void reverse(int[] arr, int l, int r) {

    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
