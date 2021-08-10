package com.rukawa.algorithm.types.advanced;

/**
 * @className: Code_C3_Largest1BorderedSquare
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/8/9 0009 7:49
 **/
public class Code_C3_Largest1BorderedSquare {
    /**
     * 给定一个0和1组成的二位数组
     * 返回边框全是1的最大正方形面积
     */
    public static int largest1BorderedSquare(int[][] m) {
        /**
         * 在一个N*N的二维矩阵中
         * 长方形的数量级别是多少？ (N^4),N*N一共N^2个点，选一个点的可能性是N^2，点为a，再选一个点的可能性是N^2，点为b，
         * 它们俩能组成一个长方形，重复的点只有一次，所以是O(N^2 * N^2)
         *
         * 正方形的数量级别是多少？ (N^3),N*N一共N^2个点，选一个点的可能性是N^2，边长只有0~N，所以O(N^2 * N)
         */
        int N = m.length;
        int M = m[0].length;

        // O(N^2)
        int[][] right = new int[N][M];
        int[][] down = new int[N][M];
        setBorderMap(m, right, down);

        // O(N^3)
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < M; j++) {
//                // 如果当前在第i行，往下有多少长度，此时N=5，i=2，2只能延伸到4，总共有3的长度，所以N-i
//                // 如果当前在第j列，往右有多少长度，此时M=7，j=5，5只能延伸到6，总共有2的长度，所以M-j
//                // 但是要形成一个正方形，所以边长最长只能扩到2的长度
//                for (int border = 1; border <= Math.min(N - i, M - j); border++) {
//                    // 该正方形左上顶点(i,j),变成为border
//                    // 验证这个正方形，是不是边框都是1！ O(1)
//                    if (hasSizeOfBorder(border, right, down)) {
//                        return border * border;
//                    }
//                }
//            }
//        }
        // 去尝试每个边长
        for (int size = Math.min(m.length, m[0].length); size != 0; size--) {
            if (hasSizeOfBorder(size, right, down)) {
                return size * size;
            }
        }
        return 0;
    }

    public static boolean hasSizeOfBorder(int border, int[][] right, int[][] down) {
        // 优化，假设向右的长度是7，要验证长度为5的正方形，所以只需要验证前两位即可，因为从3开始，长度不够5位，所以小优化
        for (int i = 0; i != right.length - border + 1; i++) {
            for (int j = 0; j != right[0].length - border + 1; j++) {
                // 假设i位置为2，border为4，从2~5长度为4，所以是2+4-1 -> i+border-1
                if (right[i][j] >= border && down[i][j] >= border && down[i][j + border -1] >= border && right[i + border - 1][j] >= border) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void setBorderMap(int[][] m, int[][] right, int[][] down) {
        int row = m.length;
        int col = m[0].length;
        // 先填写右下角的位置的信息
        if (m[row - 1][col - 1] == 1) {
            right[row - 1][col - 1] = 1;
            down[row - 1][col - 1] = 1;
        }
        // 填写最后一列
        // 向上填
        for (int i = row - 2; i >= 0; i--) {
            if (m[i][col - 1] == 1) {
                right[i][col - 1] = 1;
                down[i][col - 1] = down[i + 1][col - 1] + 1;
            }
        }
        // 填写最后一行
        // 向左填
        for (int j = col - 2; j >= 0; j--) {
            if (m[row - 1][j] == 1) {
                down[row - 1][j] = 1;
                right[row - 1][j] = right[row - 1][j + 1] + 1;
            }
        }
        // 填写剩余的其他位置
        for (int i = row - 2; i >= 0; i--) {
            for (int j = col - 2; j >= 0; j--) {
                if (m[i][j] == 1) {
                    right[i][j] = right[i][j + 1] + 1;
                    down[i][j] = down[i + 1][j] + 1;
                }
            }
        }
    }
}
