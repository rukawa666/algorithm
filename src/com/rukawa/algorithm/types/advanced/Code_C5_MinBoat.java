package com.rukawa.algorithm.types.advanced;

import java.util.Arrays;

/**
 * @className: Code_C5_MinBoat
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/8/9 0009 21:41
 **/
public class Code_C5_MinBoat {
    /**
     * 给定一个正整数数组arr，代表若干人的体重
     * 再给定一个正数limit，表示所有船共同拥有的载重量
     * 每艘船最多坐俩人，且不能超过载重
     * 想让所有人同时过河，并且拥有最好的分配方法让船尽量少
     * 返回最少的船数？
     */
    public static int minBoat(int[] arr, int limit) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        Arrays.sort(arr);
        if (arr[N - 1] > limit) {
            return -1;
        }
        // 找到有序数组中，左边离limit/2最近的位置
        int lessR = -1;
        for (int i = N - 1; i >= 0 ; i--) {
            if (arr[i] <= (limit >> 1)) {
                lessR = i;
                break;
            }
        }
        if (lessR == -1) {
            return N;
        }
        int L = lessR;
        int R = lessR + 1;
        // 俩俩匹配使用的船的数量
        int noUsed = 0;
        while (L >= 0) {
            int solved = 0;
            while (R < N && arr[L] + arr[R] <= limit) {
                R++;
                solved++;
            }
            if (solved == 0) {
                noUsed++;
                L--;
            } else {
                L = Math.max(-1, L - solved);
            }

        }
        int all = lessR + 1;
        int used = all - noUsed;
        int moreUnsolved = (N - all) - used;
        return used + ((noUsed + 1) >> 1) + moreUnsolved;
    }

}
