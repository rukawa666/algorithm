package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-21 8:41
 * @Version：1.0
 */
public class Problem_0088_MergeSortedArray {
    /**
     * 合并两个有序数组
     * 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
     * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
     * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，
     * nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。

     * 提示：
     * nums1.length == m + n
     * nums2.length == n
     * 0 <= m, n <= 200
     * 1 <= m + n <= 200
     * -109 <= nums1[i], nums2[j] <= 109
     * 进阶：你可以设计实现一个时间复杂度为 O(m + n) 的算法解决此问题吗？
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        /**
         * num1: 1,2,2,6,6,0,0,0,0
         *       0,1,2,3,4,5,6,7,8
         * num2: 3,5,7,7
         *       0,1,2,3
         * 此时m在nums1的4位置，n在nums2的3位置
         * 两边从右往左遍历，nums1[m-1]和nums[n-1]谁大拷贝谁，如果相等则拷贝长数组中的位置，提前释放位置
         */
        int index = nums1.length;
        while (m > 0 && n > 0) {
            if (nums1[m - 1] >= nums2[n -1]) {
                nums1[--index] = nums1[--m];
            } else {
                nums1[--index] = nums2[--n];
            }
        }

        while (m > 0) {
            nums1[--index] = nums1[--m];
        }

        while (n > 0) {
            nums1[--index] = nums2[--n];
        }
    }
}
