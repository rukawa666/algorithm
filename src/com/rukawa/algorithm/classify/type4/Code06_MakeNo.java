package com.rukawa.algorithm.classify.type4;

/**
 * create by hqh on 2022/9/27
 */
public class Code06_MakeNo {
    /**
     * 生成长度为size的达标数组，什么是达标？
     * 达标：对于任意的i<k<j 满足[i]+[j] != 2*[k]
     * 给定一个正数size，返回长度为size的达标数组
     */
    public static int[] makeNo(int size) {
        /**
         * 思路：如果有三个数 a b c  a+c!=2b
         * 此时2a+2c!=2b  (2a+1)+(2c+1)!=2b+1也达标
         * 2a, 2c, 2b, (2a+1), (2c+1), (2b+1) 这6个数任意选择三个数都达标
         */
        if (size == 1) {
            return new int[] {1};
        }
        int halfSize = (size + 1) / 2;
        int[] base = makeNo(halfSize);

        int[] res = new int[size];
        int index = 0;
        for (; index < halfSize; index++) {
            res[index] = base[index] * 2 + 1;
        }

        for (int i = 0; index < size; index++, i++) {
            res[index] = base[i] * 2;
        }
        return res;
    }

    // 检验函数
    public static boolean isValid(int[] arr) {
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            for (int k = i + 1; k < N; k++) {
                for (int j = k + 1; j < N; j++) {
                    if (arr[i] + arr[j] == 2 * arr[k]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        for (int N = 1; N < 1000; N++) {
            int[] arr = makeNo(N);
            if (!isValid(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");
        System.out.println(isValid(makeNo(1042)));
        System.out.println(isValid(makeNo(2981)));
    }
}
