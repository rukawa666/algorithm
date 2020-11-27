package com.rukawa.algorithm.trainingcamp.trainingcamp3.class7;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-06 9:15
 * @Version：1.0
 */
public class Code01_MinLengthForSort {
    /**
     * 给定一个无序数组arr，如果只能再一个子数组上排序
     * 返回如果让arr整体有序，需要排序的最短子数组长度
     */
    public static int getMinLength(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
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
        int max = arr[0];
        int maxLeft = -1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < max){
                maxLeft = i;
            } else {
                max = Math.max(max, arr[i]);
            }
        }

        int min = arr[arr.length - 1];
        int minRight = -1;
        for (int i = arr.length - 2; i >= 0; i--) {
            if (arr[i] > min) {
                minRight = i;
            } else {
                min = Math.min(min, arr[i]);
            }
        }
        if (minRight == -1) {
            return 0;
        }
        return maxLeft - minRight + 1;
    }

    public static void main(String[] args) {
//        int[] arr = {1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19};
        int[] arr = {1, 2, 5, 3, 2, 4, 6, 7};
        System.out.println(getMinLength(arr));

    }
}
