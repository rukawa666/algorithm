package com.rukawa.algorithm.types.dp.quadrilateral;

/**
 * @className: Code06_ThrowChessPiecesProblem
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/20 0020 22:28
 **/
public class Code06_ThrowChessPiecesProblem {
    /**
     * 一座大楼有0~N层，地面算作第0层，最高的一层为第N层。已知棋子从第0层掉落肯定不会摔碎，从第i层掉落可能会摔碎，也可能不会摔碎(1≤i≤N)。
     * 给定整数N作为楼层数，再给定整数K作为棋子数，返回如果想找到棋子不会摔碎的最高层数，即使在最差的情况下扔的最少次数。一次只能扔一个棋子。
     * 【举例】
     * N=10，K=1。
     * 返回10。因为只有1棵棋子，所以不得不从第1层开始一直试到第10 层，在最差的情况下，即第10层 是不会摔坏的最高层，最少也要扔10次。
     * N=3，K=2。
     * 返回 2。
     * 先在2层扔1棵棋子如果碎了，试第1层，如果没碎，试第3层。 N=105，K=2 返回14。
     * 第一个棋子先在14层扔，碎了则用仅存的一个棋子试1~13。 若没碎，第一个棋子继续在27层扔，碎了则用仅存的一个棋子试15~26。
     * 若没碎，第一个棋子继续在39层扔，碎了则用仅存的一个棋子试 28~38。 若没碎，第一个棋子继续在50层扔，碎了则用仅存的一个棋子试40~49。
     * 若没碎，第一个棋子继续在60层扔，碎了则用仅存的一个棋子试 51~59。 若没碎，第一个棋子继续在69层扔，碎了则用仅存的一个棋子试 61~68。
     * 若没碎，第一个棋子继续在77层扔，碎了则用仅存的一个棋子试 70~76。 若没碎，第一个棋子继续在84层 扔，碎了则用仅存的一个棋子试 78~83。
     * 若没碎，第一个棋子继续在90层扔，碎了则用仅存的一个棋子试 85~89。 若没碎，第一个棋子继续在95层扔，碎了则用仅存的一个棋子试 91~94。
     * 若没碎，第一个棋子继续在99层扔，碎了则用仅存的一个棋子试 96~98。 若没碎，第一个棋子继续在102层扔，碎了则用仅存的一个棋子试100、101。
     * 若没碎，第一个棋子继续在104 层扔，碎了则用仅存的一个棋子试103。 若没碎，第一个棋子继续在105层扔，若到这一步还没碎，那么105便是结果。
     */

    // 暴力递归
    public static int superEggDrop1(int kChess, int nLevel) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        return process1(nLevel, kChess);
    }

    /**
     *
     * @param rest  剩余多少层楼去验证
     * @param k 剩余多少棋子能够使用
     * @return  一定要要验证出最高的不会碎的楼层！但是每次都是坏运气，返回至少需要扔几次？
     */
    public static int process1(int rest, int k) {
        if (rest == 0) {
            return 0;
        }
        if (k == 1) {
            return rest;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 1; i != rest + 1; i++) { // 第一次扔的时候，仍在了i层
            min = Math.min(min, Math.max(process1(i - 1, k - 1), process1(rest - i, k)));
        }
        return min + 1;
    }

    // 暴力递归改缓存
    public static int superEggDrop2(int kChess, int nLevel) {
        if (kChess < 1 || nLevel < 1) {
            return 0;
        }
        // 棋子只有一颗，只能从第1层开始扔，一直尝试
        if (kChess == 1) {
            return nLevel;
        }
        int[][] dp = new int[nLevel + 1][kChess + 1];
        for (int i = 1; i <= nLevel; i++) {
            dp[i][1] = i;
        }
        // 常规位置：dp[7][3],总共有7层，从第1层开始尝试，如果扔在第5层碎了，则还剩4层去尝试剩余2颗棋子
        // 如果扔在第5层没有碎，还剩下两层去尝试3颗棋子
        for (int i = 1; i <= nLevel; i++) {
            for (int j = 2; j <= kChess; j++) {
                int min = Integer.MAX_VALUE;
                // 从第1层开始枚举尝试
                for (int k = 1; k != i + 1; k++) {
                    min = Math.min(min, Math.max(dp[i - k][j], dp[k - 1][j - 1]));
                }
                dp[i][j] = min + 1;
            }
        }
        return dp[nLevel][kChess];
    }

    // 枚举优化
    public static int superEggDrop3(int kChess, int nLevel) {
        if (kChess < 1 || nLevel < 1) {
            return 0;
        }
        // 棋子只有一颗，只能从第1层开始扔，一直尝试
        if (kChess == 1) {
            return nLevel;
        }
        int[][] dp = new int[nLevel + 1][kChess + 1];
        for (int i = 1; i <= nLevel; i++) {
            dp[i][1] = i;
        }
        // 100层楼剩余三颗棋子，把第一颗棋子仍在15层得到的最优解，所以best记为15
        int[][] best = new int[nLevel + 1][kChess + 1];
        // 只剩下1层楼，最优方案肯定是第1层
        for (int j = 1; j <= kChess; j++) {
            dp[1][j] = 1;
            best[1][j] = 1;
        }

        // 从上往下，从右往左填写，因为要取得上+右的位置对
        for (int i = 2; i <= nLevel; i++) {
            for (int j = kChess; j > 1; j--) {
                int ans = Integer.MAX_VALUE;
                int choose = -1;
                // 上方给予一个下限
                int down = best[i -1][j];
                // 右方给予一个上限
                int up = j == kChess ? i : best[i][j + 1];
                for (int first = down; first <= up; first++) {
                    int cur = Math.max(dp[i - first][j], dp[first - 1][j - 1]);
                    if (cur <= ans) {
                        ans = cur;
                        choose = first;
                    }
                }
                dp[i][j] = ans + 1;
                best[i][j] = choose;
            }
        }
        return dp[nLevel][kChess];
    }

    public static int superEggDrop4(int kChess, int nLevel) {
        /**
         * 行：棋子数  列:扔的次数
         * dp[i][j]：在命运最差的情况下，有i颗棋子，但是能扔j次，能搞定多少层楼？
         */
        return 0;
    }

    public static void main(String[] args) {
        int maxN = 500;
        int maxK = 30;
        int testTime = 100;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxN) + 1;
            int K = (int) (Math.random() * maxK) + 1;
            int ans1 = superEggDrop1(K, N);
            int ans2 = superEggDrop2(K, N);
            int ans3 = superEggDrop3(K, N);
//            int ans4 = superEggDrop4(K, N);
//            int ans5 = superEggDrop5(K, N);
            if (ans2 != ans3) {
                System.out.println("出错了!");
            }
        }
        System.out.println("测试结束");
    }

}
