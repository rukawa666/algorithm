package com.rukawa.algorithm.trainingcamp.trainingcamp4.class4;

import java.util.Arrays;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-14 23:24
 * @Version：1.0
 */
public class Code02_FindKthMinNumber {
    /**
     * 给定两个整数数组A和B
     * A是长度为m，元素从小到大排好序了
     * B是长度为n，元素从小到大排好序了
     * 希望从A和B数组中，找到最大的k个数字
     */

    // 时间复杂度O(log(min(N, M)))
    public static int findKthNum(int[] arr1, int[] arr2, int kth) {
        if (arr1 == null || arr2 == null) {
            return -1;
        }
        if (kth < 1 || kth > arr1.length + arr2.length) {
            return -1;
        }
        int[] longs = arr1.length >= arr2.length ? arr1 : arr2;
        int[] shorts = arr1.length < arr2.length ? arr1 : arr2;
        int l = longs.length;
        int s = shorts.length;
        if (kth <= s) {
            return getUpMedian(shorts, 0 , kth - 1, longs, 0, kth - 1);
        }

        /**
         * shorts: 1  2  3  4  5  6  7  8  9  10
         * longs : 1' 2' 3' 4' 5' 6' 7' 8' 9' 10' 11' 12' 13' 14' 15'
         * kth = 23
         * 情况：shorts： 1 ~ 7 不在考虑范围
         *      longs:  1'~ 12' 不在考虑范围
         *      所以第23小数在8 9 10 13' 14' 15',所以在6个数选择3个数作为目标数，前面必须有12+7个数+3个数 = 22个数
         *      解决方案8位置数和13'位置数去判断满足条件？不满足在9, 10, 14', 15'选择2个数，12+7+2+2=23
         *
         */
        if (kth > l) {
            if (shorts[kth - 1 - l] >= longs[l - 1]) {
                return shorts[kth - 1 - l];
            }
            if (longs[kth - 1 - s] >= shorts[s - 1]) {
                return longs[kth - 1 - s];
            }
            return getUpMedian(shorts, kth - l, s - 1, longs, kth - s, l - 1);
        }
        /**
         * shorts: 1  2  3  4  5  6  7  8  9  10
         * longs : 1' 2' 3' 4' 5' 6' 7' 8' 9' 10' 11' 12' 13' 14' 15'
         * kth = 13
         * 情况：longs:  1'~ 2', 14' ~ 15' 不在考虑范围
         *      shorts -> all      总共10个数
         *      longs  -> 3' ~ 13' 总共11个数
         *      所以先判断3' >= 10？ 不满足则1~10,4'~13' 找第10个数
         */
        if (longs[kth - s - 1] >= shorts[s - 1]) {
            return longs[kth - s - 1];
        }
        return getUpMedian(shorts, 0, s - 1, longs, kth - s, kth - 1);


    }


    // 这两段一定要等长且有序
    // 求这两段整体上的上中位数，且返回
    // O(logN)
    public static int getUpMedian(int[] arr1, int l1, int r1, int[] arr2, int l2, int r2) {
        int mid1 = 0;
        int mid2 = 0;
        int offset = 0;
        while (l1 < r1) {
            mid1 = (l1 + r1) / 2;
            mid2 = (l2 + r2) / 2;
            // 奇数为0，偶数为1
            offset = ((r1 - l1 + 1) & 1) ^ 1;
            /**
             * 举例说明：
             *  1、奇数情况，求出第5小的数
             *     1  2  3  4  5  -->  l1 ... r1
             *     1' 2' 3' 4' 5' -->  l2 ... r2
             *     a、如果3>3',则3,4,5不可能,1',2'不可能，所以在1,2,3',4',5'中获取中位数，为了等长需要3位置,则r1=mid1,l2=mid2
             *     b、如果3'>3,则1,2不可能,3',4',5'不可能，所以在3,4,5,1',2'中获取中位数，为了等长需要3'位置,则l1=mid1,r2=mid2
             *     c、3=3',直接返回
             *  2、偶数情况
             *     1  2  3  4   -->  l1 ... r1
             *     1' 2' 3' 4'  -->  l2 ... r2
             *     a、如果2>2',则3,4不可能，1',2'不可能，所以在1,2,3',4'中产生中位数，所以l2=mid2+1, r1=mid1
             *     b、如果2'>2,则3',4'不可能，1,2不可能，所以在1',2',3,4中产生中位数，所以r2=mid2, l1=mid1+1
             *     c、如果2=2',直接返回
             */
            if (arr1[mid1] > arr1[mid2]) {
                r1 = mid1;
                l2 = mid2 + offset;
            } else if (arr1[mid1] < arr2[mid2]) {
                l1 = mid1 + offset;
                r2 = mid2;
            } else {
                return arr1[mid1];
            }
        }
        return Math.max(arr1[l1], arr2[l2]);
    }

    // getUpMedian方法最初版本
    public static int getUpMedian2(int[] arr1, int l1, int r1, int[] arr2, int l2, int r2) {
        int mid1 = 0;
        int mid2 = 0;
        while (l1 < r1) {
            mid1 = (l1 + r1) / 2;
            mid2 = (l2 + r2) / 2;
            if (arr1[mid1] == arr2[mid2]) {
                return arr1[mid1];
            }

            if (((r1 - l1 - 1) & 1) == 1) { // 奇数
                if (arr1[mid1] > arr2[mid2]) {  // 3 > 3'
                    if (arr2[mid2] >= arr1[mid1 - 1]) { // 3' > 2, 如果成立，则3'就是中位数
                        return arr2[mid2];
                    }
                    r1 = mid1 - 1;
                    l2 = mid2 + 1;
                } else {  // 3' > 3
                    if (arr1[mid1] > arr2[mid2 - 1]) { // 3 > 2'
                        return arr1[mid1];
                    }
                    r2 = mid2 - 1;
                    l1 = mid1 + 1;
                }
            } else {    // 偶数
                if (arr1[mid1] > arr2[mid2]) {  // 2 > 2'
                    r1 = mid1;
                    l2 = mid2 + 1;
                } else {
                    r2 = mid2;
                    l1 = mid1 + 1;
                }
            }

        }
        // 两个数组中各剩一个数，谁小谁返回
        return Math.max(arr1[l1], arr2[l2]);
    }

    // For test, this method is inefficient but absolutely right
    public static int[] getSortedAllArray(int[] arr1, int[] arr2) {
        if (arr1 == null || arr2 == null) {
            throw new RuntimeException("Your arr is invalid!");
        }
        int[] arrAll = new int[arr1.length + arr2.length];
        int index = 0;
        for (int i = 0; i != arr1.length; i++) {
            arrAll[index++] = arr1[i];
        }
        for (int i = 0; i != arr2.length; i++) {
            arrAll[index++] = arr2[i];
        }
        Arrays.sort(arrAll);
        return arrAll;
    }

    public static int[] generateSortedArray(int len, int maxValue) {
        int[] res = new int[len];
        for (int i = 0; i != len; i++) {
            res[i] = (int) (Math.random() * (maxValue + 1));
        }
        Arrays.sort(res);
        return res;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len1 = 10;
        int len2 = 23;
        int maxValue1 = 20;
        int maxValue2 = 100;
        int[] arr1 = generateSortedArray(len1, maxValue1);
        int[] arr2 = generateSortedArray(len2, maxValue2);
        printArray(arr1);
        printArray(arr2);
        int[] sortedAll = getSortedAllArray(arr1, arr2);
        printArray(sortedAll);
        int kth = 17;
        System.out.println(findKthNum(arr1, arr2, kth));
        System.out.println(sortedAll[kth - 1]);

    }

}
