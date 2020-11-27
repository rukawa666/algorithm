package com.rukawa.algorithm.trainingcamp.trainingcamp4.class5;

import java.util.Arrays;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-19 7:34
 * @Version：1.0
 */
public class Code02_SnakeGame {
    /**
     * 给定一个二维数组matrix，每个单元都是一个整数，有正有负。最开始的时候小Q操作一条长度为0
     * 的蛇蛇从矩阵最左侧任选一个单元进入地图，蛇每次只能够到达当前位置的右上相邻、右侧相邻和
     * 右下相邻的单元格。蛇蛇到达一个单元格后，自身的长度会瞬间加上该单元格的数值，任何情况下
     * 长度为负则游戏结束。小Q是一个天才，她拥有超能力，可以在游戏开始的时候把地图中的某一个
     * 节点的值变为其相反数(注：最多只能改变一个节点)。问在小Q游戏过程中，他的蛇蛇最长长度
     * 可以到多少？
     * 比如：
     *  1  -4  10
     *  3  -2  -1
     *  2  -1  0
     *  0  5   -2
     *  最右路径为最左侧的3开始，3 -> -4(利用能力变成4) -> 10.所以返回17
     */

    public static int walk1(int[][] matrix) {
        /**
         * 思路：
         *  1、标准的业务限制模型
         */
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int[] ans = process(matrix, i, j);
                res = Math.max(res, Math.max(ans[0], ans[1]));
            }
        }
        return res;
    }

    // 在假想的最优的左侧到达(i,j)的旅程中
    // 0) 在没有使用过能力的情况下，返回路径最大和,(在i，j都结束的情况下，都不使用能力),没有可能到达返回负
    // 1) 在使用过能力的情况下，返回路径的最大和,(在之前使用过还是在[i,j]位置使用),没有可能到达返回负
    public static int[] process(int[][] m, int i, int j) {
        if (j == 0) {
            // 使用过能力 m[i][j]  使用能力 -m[i][j]
            return new int[] {m[i][j], -m[i][j]};
        }
        // j > 0
        // 来到(i,j)位置，一定有之前的路

        //  第一条路，(i,j j>0)
        int[] preAns = process(m, i, j - 1);
        // 所有的路中，完全不使用能力的情况下，能够到达的最大的长度
        int preUnUse = preAns[0];
        // 所有的路中，在使用过一次能力的情况下，能够到达的最大的长度
        int preUse = preAns[1];

        // 左上角有值
        if (i - 1 >= 0) {
            preAns = process(m, i - 1, j - 1);
            preUnUse = Math.max(preUnUse, preAns[0]);
            preUse = Math.max(preUse, preAns[1]);
        }

        // 左下角有值
        if (i + 1 < m.length) {
            preAns = process(m, i + 1, j - 1);
            preUnUse = Math.max(preUnUse, preAns[0]);
            preUse = Math.max(preUse, preAns[1]);
        }

        // 之前没有使用过能力，当前位置也不适用能力的情况下最优解
        // 不管之前是否使用能力，还是当前使用能力，请保证能力只使用一次，最优解
        int no = -1;
        int yes = -1;
        if (preUnUse >= 0) {
            no = m[i][j] + preUnUse;
            yes = -m[i][j] + preUnUse;  // 当前使用能力
        }
        if (preUse >= 0) {
            yes = Math.max(yes, m[i][j] + preUse);  // 之前使用能力
        }
        return new int[]{no, yes};
    }


    // 记忆化搜索改暴力递归
    public static int walk2(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        int[][][] dp = new int[matrix.length][matrix[0].length][2];
        for (int i = 0; i < matrix.length; i++) {
            dp[i][0][0] = matrix[i][0];
            dp[i][0][1] = -matrix[i][0];
            max = Math.max(max, Math.max(dp[i][0][0], dp[i][0][1]));
        }

        for (int j = 1; j < matrix[0].length; j++) {
            for (int i = 0; i < matrix.length; i++) {
                int preUnUse = dp[i][j - 1][0];
                int preUse = dp[i][j - 1][1];
                if (i - 1 >= 0) {
                    preUnUse = Math.max(preUnUse, dp[i - 1][j - 1][0]);
                    preUse = Math.max(preUse, dp[i - 1][j - 1][1]);
                }
                if (i + 1 < matrix.length) {
                    preUnUse = Math.max(preUnUse, dp[i + 1][j - 1][0]);
                    preUse = Math.max(preUse, dp[i + 1][j - 1][1]);
                }
                dp[i][j][0] = -1;
                dp[i][j][1] = -1;
                if (preUnUse >= 0) {
                    dp[i][j][0] = matrix[i][j] + preUnUse;
                    dp[i][j][1] = -matrix[i][j] + preUnUse;
                }

                if (preUse >= 0) {
                    dp[i][j][1] = Math.max(dp[i][j][1], matrix[i][j] + preUse);
                }
                max = Math.max(max, Math.max(dp[i][j][0], dp[i][j][1]));
            }
        }
        return max;
    }

    public static int[][] generateRandomArray(int row, int col, int value) {
        int[][] arr = new int[(int) (Math.random() * row) + 1][(int) (Math.random() * col) + 1];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = (int) (Math.random() * value) * (Math.random() > 0.5 ? -1 : 1);
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        int times = 1000000;
        for (int i = 0; i < times; i++) {
            int[][] matrix = generateRandomArray(5, 5, 10);
            int ans1 = walk1(matrix);
            int ans2 = walk2(matrix);
            if (ans1 != ans2) {
                for (int j = 0; j < matrix.length; j++) {
                    System.out.println(Arrays.toString(matrix[j]));
                }
                System.out.println("Oops   ans1: " + ans1 + "   ans2:" + ans2);
                break;
            }
        }
        System.out.println("finish");
    }
}
