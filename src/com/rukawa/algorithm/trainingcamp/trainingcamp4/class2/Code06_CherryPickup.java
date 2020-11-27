package com.rukawa.algorithm.trainingcamp.trainingcamp4.class2;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-13 0:01
 * @Version：1.0
 */
public class Code06_CherryPickup {
    /**
     * 给定一个矩阵matrix，先从左上角开始，每一步只能往右或者往下走，走到右下角。
     * 然后从右下角出发，每一步只能往上或者往左走，再回到左上角。
     * 任何一个位置的数字，只能或者一遍。返回最大路径和。
     */
    public static int comeGoMaxPathSum(int[][] matrix) {
        return process(matrix, 0, 0, 0);
    }

    // 两个点走的距离相等，则可以省一个参数
    public static int process(int[][] matrix, int Ar, int Ac, int Br) {
        if (Ar == matrix.length || Ac == matrix[0].length
                || Br == matrix.length || Ar + Ac - Br == matrix[0].length) {
            return Integer.MIN_VALUE;
        }
        /**
         * 尝试原则
         * 1、给了几种尝试模型
         * 2、每一个可变参数的类型不要突破到整形以上 -> 记忆化搜索
         * 3、可变参数的个数能省则省
         */
        int N = matrix.length;
        int M= matrix[0].length;
        // A来到右下角，则B也一定来到右下角
        if (Ar == N - 1 && Ac == M - 1) {
            return matrix[Ar][Ac];
        }

        // 还没来到右下角
        // A 下  B 右
        // A 下  B 下
        // A 右  B 右
        // A 右  B 下
        int Bc = Ar + Ac - Br;
        int ADownBRight = -1;
        if (Ar + 1 < N && Bc + 1 < M) {
            ADownBRight = process(matrix, Ar + 1, Ac, Br);
        }

        int ADownBDown = -1;
        if (Ar + 1 < N && Br + 1 < N) {
            ADownBDown = process(matrix, Ar + 1, Ac, Br + 1);
        }

        int ARightBRight = -1;
        if (Ac + 1 < M && Bc + 1 < M) {
            ARightBRight = process(matrix, Ar, Ac + 1, Br);
        }

        int ARightBDown = -1;
        if (Ac + 1 < M && Br + 1 < N) {
            ARightBDown = process(matrix, Ar, Ac + 1, Br + 1);
        }

        int nextBest = Math.max(Math.max(ADownBRight, ADownBDown), Math.max(ARightBRight, ARightBDown));

        if (Ar == Br) {  // A和B在同一个位置
            return matrix[Ar][Ac] + nextBest;
        }
        // A和B一定不是同一个位置
        return matrix[Ar][Ac] + matrix[Br][Bc] + nextBest;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1,1,1,1,1,0,0,0,0,0},
                {0,0,0,0,1,0,0,0,0,0},
                {0,0,0,0,1,0,0,0,0,1},
                {1,0,0,0,1,0,0,0,0,0},
                {0,0,0,0,1,1,1,1,1,1}
        };
        System.out.println(comeGoMaxPathSum(matrix));
    }
}
