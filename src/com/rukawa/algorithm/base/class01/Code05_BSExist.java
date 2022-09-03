package com.rukawa.algorithm.base.class01;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-09 23:13
 * @Version：1.0
 */
public class Code05_BSExist {

    public static boolean exists(int[] sortedArr, int num) {
        if (sortedArr == null || sortedArr.length == 0) {
            return false;
        }

        int L = 0;
        int R = sortedArr.length - 1;
        int mid = 0;
        while (L <= R) {
            /**
             * mid = (L + R) / 2;
             * L：10亿  R：18亿
             * mid = L + (R - L) / 2;  // L + R-L 一半的距离，近似中点
             */
            mid = L + ((R - L) >> 1);       // mid = (L + R) / 2;
            if (sortedArr[mid] == num) {
                return true;
            } else if (sortedArr[mid] > num) {
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        return sortedArr[L] == num;
    }
}
