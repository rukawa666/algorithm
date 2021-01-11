package com.rukawa.algorithm.leetcode;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-11 21:10
 * @Version：1.0
 */
public class Problem_0004_MedianOfTwoSortedArrays {
    /**
     * 寻找两个正序数组的中位数
     * 给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的中位数。
     * 进阶：你能设计一个时间复杂度为 O(log (m+n)) 的算法解决此问题吗？
     *
     * 示例 1：
     * 输入：nums1 = [1,3], nums2 = [2]
     * 输出：2.00000
     * 解释：合并数组 = [1,2,3] ，中位数 2
     *
     * 示例 2：
     * 输入：nums1 = [1,2], nums2 = [3,4]
     * 输出：2.50000
     * 解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
     *
     * 示例 3：
     * 输入：nums1 = [0,0], nums2 = [0,0]
     * 输出：0.00000
     *
     * 示例 4：
     * 输入：nums1 = [], nums2 = [1]
     * 输出：1.00000
     *
     * 示例 5：
     * 输入：nums1 = [2], nums2 = []
     * 输出：2.00000
     *
     * 提示：
     * nums1.length == m
     * nums2.length == n
     * 0 <= m <= 1000
     * 0 <= n <= 1000
     * 1 <= m + n <= 2000
     * -106 <= nums1[i], nums2[i] <= 106
     */

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int size = nums1.length + nums2.length;
        // 总长度是奇数还是偶数
        boolean even = (size & 1) == 0;

        if (nums1.length == 0 && nums2.length != 0) {
            // 偶数
            if (even) {
                return (double) (nums2[(size - 1) / 2] + nums2[size / 2]) / 2;
            } else { // 奇数
                return nums2[size / 2];
            }
        } else if (nums2.length == 0 && nums1.length != 0) {
            if (even) {
                return (double)(nums1[(size - 1) / 2] + nums1[size / 2]) / 2;
            } else {
                return nums1[size / 2];
            }
        } else if (nums1.length != 0 && nums2.length != 0) {
            if (even) {
                return (double) (findKth(nums1, nums2, size / 2) + findKth(nums1, nums2, size / 2 + 1)) / 2D;
            } else {
                return findKth(nums1, nums2, size / 2 + 1);
            }
        } else {
            return 0;
        }
    }

    // O(log(sLen))
    public static int findKth(int[] nums1, int[] nums2, int kth) {
        int[] longs = nums1.length >= nums2.length ? nums1 : nums2;
        int[] shorts = nums1.length < nums2.length ? nums1 : nums2;
        int lLen = longs.length;
        int sLen = shorts.length;

        // 如果kth小于小数组的长度
        // sLen = 6 lLen = 10 kth = 4
        // 可能出现在shorts ->[0,3]  longs ->[0,3]
        if (kth <= sLen) {
            return getUpMedian(longs, 0, kth - 1, shorts, 0, kth - 1);
        }

        // 如果kth大于长数组的长度
        // sLen = 6 lLen = 10 kth = 13
        // 首先去除不可能的位置,在剩余的部分确认  shorts ->[2,5]  longs ->[6, 9]
        // 在8个数中取中位数，有4个位置，shorts去除[0,1] , shorts去除[0,5] 总共4+2+6,最终得到是第12小的位置
        // 1. shorts[2]和longs[9]比较, 如果大于则是第13小
        // 2. longs[6]和shorts[5]比较，如果大于则是第13小
        // 如果上述情况1和2都不可能出现，则总共排除3+7，在剩余的6个数中取第3小，最终就是第13小
        if (kth > lLen) {
            if (shorts[kth - lLen - 1] >= longs[lLen - 1]) {
                return shorts[kth - lLen - 1];
            }

            if (longs[kth - sLen - 1] >= shorts[sLen - 1]) {
                return longs[kth - sLen - 1];
            }

            return getUpMedian(shorts, kth - lLen, sLen - 1, longs, kth - sLen, lLen - 1);
        }

        // 如果kth长度大于小数组的长度但是小于长数组的长度
        // sLen = 6 lLen = 10 kth = 8
        // shorts ->[0,5] longs ->[1,7]
        // 此时排除longs[1]的位置
        if (longs[kth - sLen - 1] >= shorts[sLen - 1]) {
            return longs[kth - sLen - 1];
        }
        return getUpMedian(shorts, 0, sLen - 1, longs, kth - sLen, kth - 1);

    }

    // 两个等长的数组，求中位数
    // 时间复杂度O(logN)
    public static int getUpMedian(int[] A, int aL, int aR, int[] B, int bL, int bR) {
        int mid1 = 0;
        int mid2 = 0;
        while (aL < aR) {
            mid1 = aL + ((aR - aL) >>> 1);
            mid2 = bL + ((bR - bL) >>> 1);
            if (A[mid1] == B[mid2]) {
                return A[mid1];
            }
            if (((aR - aL + 1) & 1) == 1) { // 奇数长度
                if (A[mid1] > B[mid2]) {
                    if (B[mid2] >= A[mid1 - 1]) {
                        return B[mid2];
                    }
                    aR = mid1 - 1;
                    bL = mid2 + 1;
                } else {
                    if (A[mid1] >= B[mid2 - 1]) {
                        return A[mid1];
                    }
                    bR = mid2 - 1;
                    aL = mid1 + 1;
                }
            } else {    // 偶数长度
                if (A[mid1] > B[mid2]) {
                    aR = mid1;
                    bL = mid2 + 1;
                } else {
                    bR = mid2;
                    aL = mid1 + 1;
                }
            }
        }
        return Math.min(A[aL], B[bL]);
    }

    public static void main(String[] args) {
        int[] nums1 = {};
        int[] nums2 = {2, 3};
        System.out.println(findMedianSortedArrays(nums1, nums2));
    }
}
