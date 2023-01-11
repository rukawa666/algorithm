package com.rukawa.algorithm.base.class31_quardrilateral;

/**
 * create by hqh on 2023/1/11
 */
public class Code06_ThrowChessPiecesProblem {
    /**
     * 一座大楼有0~N层，地面算作第0层，最高的一层为第N层。已知棋子从第0层掉落肯定不会摔碎，从第i层掉落可能会摔碎，也可能不会摔碎(1≤i≤N)
     * 。给定整数N作为楼层数，再给定整数K作为棋子数，返回如果想找到棋子不会摔碎的最高层数，即使在最差的情况下扔的最少次数。一次只能扔一个棋子。
     * 【举例】
     * N=10，K=1。
     * 返回10。因为只有1棵棋子，所以不得不从第1层开始一直试到第10层，在最差的情况下，
     * 即第10层是不会摔坏的最高层，最少也要扔10次。
     *
     * N=3，K=2。
     * 返回2。先在2层扔1棵棋子，如果碎了，试第1层，如果没碎，试第3层。
     *
     * N=105，K=2
     * 返回 14。
     * 第一个棋子先在14层扔，碎了则用仅存的一个棋子试1~13。
     * 若没碎，第一个棋子继续在27层扔，碎了则用仅存的一个棋子试15~26。
     * 若没碎，第一个棋子继续在39层扔，碎了则用仅存的一个棋子试28~38。
     * 若没碎，第一个棋子继续在50层扔，碎了则用仅存的一个棋子试40~49。
     * 若没碎，第一个棋子继续在60层扔，碎了则用仅存的一个棋子试51~59。
     * 若没碎，第一个棋子继续在69层扔，碎了则用仅存的一个棋子试61~68。
     * 若没碎，第一个棋子继续在77层扔，碎了则用仅存的一个棋子试 70~76。
     * 若没碎，第一个棋子继续在 84 层扔，碎了则用仅存的一个棋子试 78~83。
     * 若没碎，第一个棋子继续在90层扔，碎了则用仅存的一个棋子试85~89。
     * 若没碎，第一个棋子继续在95层扔，碎了则用仅存的一个棋子试91~94。
     * 若没碎，第一个棋子继续在99层扔，碎了则用仅存的一个棋子试96~98。
     * 若没碎，第一个棋子继续在102层扔，碎了则用仅存的一个棋子试100、101。
     * 若没碎，第一个棋子继续在104层扔，碎了则用仅存的一个棋子试103。
     * 若没碎，第一个棋子继续在 105层扔，若到这一步还没碎，那么105便是结果。
     */
    // 方法1和方法2会超时
    // 方法3勉强通过
    // 方法4打败100%
    // 方法5打败100%，方法5是在方法4的基础上做了进一步的常数优化
    // 暴力方法
    public static int superEggDrop1(int nLevel, int kChess) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        return process1(nLevel, kChess);
    }

    // rest还剩多少层需要验证
    // k还有多少颗棋子能够使用
    // 一定要验证出最高的不会碎的楼层！但是每次都是坏运气
    // 返回至少需要仍几次？
    public static int process1(int rest, int k) {
        if (rest == 0) {
            return 0;
        }
        if (k == 1) {
            return rest;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i != rest + 1; i++) { // 第一次仍的时候，在i层
            min = Math.min(min, Math.max(process1(i - 1, k - 1), process1(rest - i, k)));
        }
        return min + 1;
    }

    // 完全不优化的方法
    public static int superEggDrop2(int nLevel, int kChess) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        if (kChess == 1) {
            return nLevel;
        }
        int[][] dp = new int[nLevel + 1][kChess + 1];
        for (int i = 1; i != dp.length; i++) {
            dp[i][1] = i;
        }
        /**
         * dp[7][2]的值？
         * 可能性1：棋子扔在第1层，碎了还剩0层去尝试最后一颗棋子，dp[0][1]；如果没碎，去剩下6个位置去尝试剩余2颗棋子，dp[6][2]
         * 可能性2：棋子扔在第2层，碎了还剩1层去尝试最后一颗棋子，dp[1][1]；如果没碎，去剩下5个位置去尝试剩余2颗棋子，dp[5][2]
         * 可能性3：棋子扔在第3层，碎了还剩2层去尝试最后一颗棋子，dp[2][1]；如果没碎，去剩下4个位置去尝试剩余2颗棋子，dp[4][2]
         * 可能性4：棋子扔在第4层，碎了还剩3层去尝试最后一颗棋子，dp[3][1]；如果没碎，去剩下3个位置去尝试剩余2颗棋子，dp[3][2]
         * 可能性5：棋子扔在第5层，碎了还剩4层去尝试最后一颗棋子，dp[4][1]；如果没碎，去剩下2个位置去尝试剩余2颗棋子，dp[2][2]
         * 可能性5：棋子扔在第6层，碎了还剩5层去尝试最后一颗棋子，dp[5][1]；如果没碎，去剩下1个位置去尝试剩余2颗棋子，dp[1][2]
         * 可能性5：棋子扔在第7层，碎了还剩6层去尝试最后一颗棋子，dp[6][1]；如果没碎，去剩下0个位置去尝试剩余2颗棋子，dp[0][2]
         */
        for (int i = 1; i != dp.length; i++) {
            for (int j = 2; j != dp[0].length; j++) {
                int min = Integer.MAX_VALUE;
                // 枚举
                for (int k = 1; k != i + 1; k++) {
                    min = Math.min(min, Math.max(dp[k - 1][j - 1], dp[i - k][j]));
                }
                dp[i][j] = min + 1;
            }
        }
        return dp[nLevel][kChess];
    }

    // k个棋子，必须S次弄完，时间复杂度O(k*S)
    // 只有一颗棋子，O(N)
    // 最差O(k * N)
    public static int superEggDrop3(int kChess, int nLevel) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        if (kChess == 1) {
            return nLevel;
        }
        int[][] dp = new int[nLevel + 1][kChess + 1];
        for (int i = 1; i != dp.length; i++) {
            dp[i][1] = i;
        }

        int[][] best = new int[nLevel + 1][kChess + 1];
        for (int j = 1; j != dp[0].length; j++) {
            dp[1][j] = 1;
            // 一层楼，有i颗棋子，最优方案在第一层
            best[1][j] = 1;
        }

        // 从上往下，从右往左填写，因为要取得上+右的位置对
        for (int i = 2; i < nLevel + 1; i++) {
            for (int j = kChess; j > 1; j--) {
                int ans = Integer.MAX_VALUE;
                int bestChoose = -1;
                int down = best[i - 1][j];
                int up = j == kChess ? i : best[i][j + 1];
                for (int first = down; first <= up; first++) {
                    int cur = Math.max(dp[first - 1][j - 1], dp[i - first][j]);
                    if (cur <= ans) {
                        ans = cur;
                        bestChoose = first;
                    }
                }
                dp[i][j] = ans + 1;
                best[i][j] = bestChoose;
            }
        }
        return dp[nLevel][kChess];
    }

    // 最优解
    public static int superEggDrop4(int kChess, int nLevel) {
        /**
         * 思路：i颗棋子扔j次可以解决几层楼？
         * 假设，当前有7颗棋子，扔10次，可以解决多少层楼的问题？
         *  假设，6颗棋子扔9次，可以解决50层楼的问题
         *  假设，7颗棋子扔9次，可以解决55层楼的问题
         *  那么，7颗棋子，扔10次，可以解决106层的问题
         *
         * 原因：
         *  假设，当前有7颗棋子，扔10次，第一颗棋子仍在x层，但是碎了，还剩余6颗棋子，还能扔9次
         *  只要保证，下方的楼层<=50层，剩余的棋子扔9次就能解决50层楼的问题
         *
         *  假设，当前有7颗棋子，扔10次，第一颗棋子仍在x层，但是没碎，还剩余7颗棋子，还能扔9次
         *  只要保证，上方的楼层<=55层，剩余的棋子扔9次就能解决55层楼的问题
         *
         *  所以，总共能解决50+55+当前x层=106层
         */
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        int[] dp = new int[kChess];
        int res = 0;
        while (true) {
            res++;
            int previous = 0;
            for (int i = 0; i < dp.length; i++) {
                int tmp = dp[i];
                dp[i] = dp[i] + previous + 1;
                previous = tmp;
                if (dp[i] >= nLevel) {
                    return res;
                }
            }
        }
    }
}
