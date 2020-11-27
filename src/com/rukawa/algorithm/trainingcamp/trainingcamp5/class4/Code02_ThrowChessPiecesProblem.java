package com.rukawa.algorithm.trainingcamp.trainingcamp5.class4;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-11-08 15:28
 * @Version：1.0
 */
public class Code02_ThrowChessPiecesProblem {
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

    // 暴力方法
    public static int solution1(int nLevel, int kChess) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        return process(nLevel, kChess);
    }

    // rest还剩下多少层楼需要去验证
    // k还有多少颗棋子能够使用
    // 一定要验证出最高的不会碎的楼层！但是每次都是坏运气
    // 返回至少需要扔几次？
    public static int process(int rest, int k) {
        if (rest == 0) {
            return 0;
        }

        if (k == 1) {
            return rest;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 1; i != rest + 1; i++) {   // 第一次扔的时候，扔在了i层
            min = Math.min(min, Math.max(process(i - 1, k - 1), process(rest - i, k)));
        }
        return min + 1;
    }

    public static int solution2(int nLevel, int kChess) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        // 如果只有一个棋子，必须要从1层往上开始尝试
        if (kChess == 1) {
            return nLevel;
        }
        int[][] dp = new int[nLevel][kChess];
        // 0行，永远不会摔碎，所以都是0
        // 0列，没有棋子可以摔，返回0
        for (int i = 0; i <= nLevel; i++) {
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
        for (int i = 1; i <= nLevel; i++) {
            for (int j = 2; j <= kChess; j++) {
                int min = Integer.MAX_VALUE;
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
    public static int solution3(int nLevel, int kChess) {
        /**
         * 思路：
         *
         */
        // dp[i][j]：假设有i颗棋子，可以扔j次的情况，可以搞定多少层楼
        // 第0列，只能扔0次，再多棋子没用，直接废弃
        // 第0行，没有棋子可以扔，不能搞定，直接废弃
        // 第1列，只能扔1次，只能搞定一层楼，所以都是1
        // 第1行，1颗棋子扔多次，扔一次搞定一层楼，都是都是j
        /**
         * 普遍位置dp[3][10]
         * 第一种方式：假设3颗棋子扔10次，假设是最优层，如果碎了，扔过一次，还剩9次可以扔，还剩2颗棋子 -> dp[2][9] = b
         *                                      如果没碎，扔过一次，还剩9次可以扔，还剩3颗棋子 -> dp[3][9] = a
         *         dp[3][10] = a + b + 1;
         * 第二种方式：客观情况下，假设3颗棋子扔9次可以解决20层楼，2颗棋子扔9次可以解决15层楼，3颗棋子扔10次绝对可以解决36层楼。
         *           假设客观上，第一次扔在16层，最高的碎的楼层在16层以下，得到结果是棋子碎了，还剩2颗棋子可以扔9次
         *           假设客观上，最高的不碎的楼层在16层以上，第一次扔没碎，还剩3颗棋子可以扔9次
         *           最后假设，最高的不碎的楼层就是16，
         */
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        if (kChess == 1) {
            return nLevel;
        }
        int[] preArr = new int[nLevel + 1];
        int[] curArr = new int[nLevel + 1];
        for (int i = 1; i != curArr.length; i++) {
            curArr[i] = i;
        }
        for (int i = 1; i != kChess; i++) {
            int[] tmp = preArr;
            preArr = curArr;
            curArr = tmp;
            for (int j = 1; j != curArr.length; j++) {
                int min = Integer.MAX_VALUE;
                for (int k = 1; k != j + 1; k++) {
                    min = Math.min(min, Math.max(preArr[k - 1], curArr[j - k]));
                }
                curArr[j] = min + 1;
            }
        }
        return curArr[curArr.length - 1];
    }
}
