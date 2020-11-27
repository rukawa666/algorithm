package com.rukawa.algorithm.trainingcamp.trainingcamp4.class6;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-20 7:35
 * @Version：1.0
 */
public class Code02_KInversePairs {
    /**
     * 给定一个整数N，代表你有1~N这些数字。在给定一个整数K。你可以随意排列这些数字，但是每一种排列都有若干个逆序对。
     * 返回有多少种排列，正好有k个逆序对
     *
     * 例子1：
     *    Input: n=3,k=0
     *    Output: 1
     *    解释：只有[1,2,3]这一个排列有0个逆序对
     *
     * 例子2：
     *    Input: n=3,k=1
     *    Output: 2
     *    解释：[3,2,1]有(3,2)、(3,1)、(2,1)三个逆序对，所以不达标
     *         达标的只有：[1,3,2]只有(3,2)这一个逆序对，所以达标
     *                  [2,1,3]只有(2,1)这一个逆序对，所以达标
     */

    // 一个样本做行一个样本做列的尝试模型
    public static int kInversePairs1(int n, int k) {
        if (n < 1 || k < 0) {
            return 0;
        }

        if (k == 0) {
            return 1;
        }

        int[][] dp = new int[n + 1][k + 1];
        // 0行不用
        // 1行，只有1个数，组成0个逆序对，有一种情况，其余都是0，逆序对要两个数
        // 1列，组成0个逆序对，比如n=4，有4个数，组成0个逆序对，只有1234一种情况
        for (int i = 1; i <= n; i++) {
            dp[i][0] = 1;
        }
        /**
         * 思路：
         *   dp[7][3] -> 有7个数，求有3个逆序对的个数有多少个？
         *   1、假设此时第6行已经填写完成，因为已经得知第1行和第1列的数据，普遍处理都是从左到右或者从上到下填写
         *   2、1~6已经形成之后，在第7行，去决定7这个数怎么摆
         *      情况1：把7插入到最后，此时3个逆序对 -> dp[6][3]
         *      情况2：把7插入到倒数第二个位置，此时会形成一个逆序对，
         *            此时在1~6形成2个逆序对的情况下，把7插入倒数第二个位置，得到3个逆序对 -> dp[6][2]
         *      情况3：把7插入到倒数第3，会产生2个逆序对，依赖dp[6][1]
         *      情况4：把7插入到倒数第4，会产生3个逆序对，依赖dp[6][0]
         *      不能继续往下插入，否则产生的逆序对会大于3
         */
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                // dp[i][j]  i>j -> dp[i-1][j...0]
                // dp[i][j]  i>=j -> dp[i-1][j...j-i+1]
                /**
                 *  举例说明
                 *  dp[7][10] ->
                 *   X X X X X X
                 *  ? 放到最开头产生6个逆序对，还差4个，所以10-7+1产生四个
                 */
                for (int s = j; s >= Math.max(0, j - i + 1); s--) {
                    dp[i][j] += dp[i - 1][s];
                }
            }
        }
        return dp[n][k];
    }



    public static int kInversePairs2(int n, int k) {
        if (n < 1 || k < 0) {
            return 0;
        }
        if (k == 0) {
            return 0;
        }
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 1; i <= n; i++) {
            dp[i][0] = 1;
        }

        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                /**
                 * 枚举优化
                 * dp[7][3] = dp[6][3...0]
                 * dp[7][4] = dp[6][4...0] = dp[6][4] + dp[6][3...0] = dp[6][4] + dp[7][3]
                 *
                 * dp[7][9] =  dp[6][9...3]
                 * dp[7][10] = dp[6][10...4] = dp[6][10] + dp[6][9...4] = dp[6][10] + dp[6][9...3] - dp[6][3] = dp[6][10] + dp[7][9] - dp[6][3]
                 */
                if (i > j) {
                    dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i][j - 1] + dp[i - 1][j] - dp[i - 1][j - i];
                }
            }
        }
        return dp[n][k];
    }

    public static int kInversePairs3(int n, int k) {
        if (n < 1 || k < 0) {
            return 0;
        }
        if (k == 0) {
            return 1;
        }
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 1; i <= n; i++) {
            dp[i][0] = 1;
        }
        int mod = 1000000007;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                /**
                 * 枚举优化
                 * dp[7][3] = dp[6][3...0]
                 * dp[7][4] = dp[6][4...0] = dp[6][4] + dp[6][3...0] = dp[6][4] + dp[7][3]
                 *
                 * dp[7][9] =  dp[6][9...3]
                 * dp[7][10] = dp[6][10...4] = dp[6][10] + dp[6][9...4] = dp[6][10] + dp[6][9...3] - dp[6][3] = dp[6][10] + dp[7][9] - dp[6][3]
                 */

                dp[i][j] = (dp[i][j - 1] + dp[i - 1][j]) % mod;
                if (j >= i) {
                    dp[i][j] = (dp[i][j] - dp[i - 1][j - i] + mod) % mod;
                }
            }
        }
        return dp[n][k];
    }


    public static void main(String[] args) {
        int n = 10, k = 10;
        System.out.println(kInversePairs1(n,k));
        System.out.println(kInversePairs1(n,k));
        System.out.println(kInversePairs1(n,k));
    }
}
