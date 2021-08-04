package com.rukawa.algorithm.types.advanced;

/**
 * @className: Code_B6_MinLengthForSort
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/8/4 0004 23:47
 **/
public class Code_B6_MinLengthForSort {
    /**
     * 给定一个数组arr，只能对arr中的一个子数组排序，但是想让arr整体都有序
     * 返回满足这一设定的子数组中，最短的是多长？
     */

    /**
     * 思路：
     *  1、从左往右遍历，arr[i] == x
     *      maxLeft > x     ×
     *      maxLeft <= x    √
     *  2、从右往左遍历，arr[i] == x
     *     minRight < x     ×
     *     minRight >= x    √
     *
     * eg：
     *  1 2 5 3 2 4 6 7
     *
     * 1、max=1,依次往右遍历，记录最右侧的×号，因为最右侧的√号是不需要重排的位置
     *          1 2 5 3 2 4 6 7
     *  max ->    1 2 5 5 5 5 6
     *            √ √ × × × √ √
     *
     * 2、min=7，依次从右往左遍历，记录最左侧的×号，因为最左侧是√好是不需要重排的位置
     *      1 2 5 3 2 4 6 7
     *      2 2 2 2 4 6 7   <- min
     *      √ √ × × √ √ √
     *
     * 3、得到min最左侧的×号在位置2，max最右侧的×号位置5
     *    所以排序最短子数组的长度为5 - 2 + 1
     */
    public static int getMinLength(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int min = arr[arr.length - 1];
        int noMinIndex = -1;
        for (int i = arr.length - 2; i >= 0; i--) {
            if (arr[i] > min) {
                noMinIndex = i;
            } else {
                min = Math.min(min, arr[i]);
            }
        }

        if (noMinIndex == -1) {
            return 0;
        }

        int max = arr[0];
        int noMaxIndex = -1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < max) {
                noMaxIndex = i;
            } else {
                max = Math.max(max, arr[i]);
            }
        }
        return noMaxIndex - noMinIndex + 1;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 4, 3, 6, 5, 7, 8, 9};
        System.out.println(getMinLength(arr));
    }
}
