package com.rukawa.algorithm.trainingcamp.trainingcamp3.class1;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-19 7:49
 * @Version：1.0
 */
public class Code06_MakeNO {
    /**
     * 给定一个正整数M，请构造出一个长度为M的数组arr，要求对任意的i，j，k三个位置，如果i<j<k，
     * 都有arr[i] + arr[k] != arr[j] * 2，返回构造出的arr
     */
    public static int[] makeNo(int M) {
        if (M == 1) {
            return new int[]{1};
        }
        // size 一半长度
        // 7 : 4
        // 8 : 4
        // [4个奇数][3个偶数]
        int halfSize = (M + 1) / 2;
        int[] base = makeNo(halfSize);
        // base -> 等长奇数达标来
        // base -> 等长偶数达标来
        int[] res = new int[M];
        int index = 0;
        for (; index < halfSize; index++) {
            res[index] = base[index] * 2 + 1;
        }

        for (int i = 0; index < M; index++, i++) {
            res[index] = base[i] * 2;
        }
        return res;
    }
}
