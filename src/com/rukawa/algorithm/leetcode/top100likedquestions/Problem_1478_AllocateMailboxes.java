package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.Arrays;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-11-05 8:03
 * @Version：1.0
 */
public class Problem_1478_AllocateMailboxes {
    /**
     * 安排邮筒
     * 给你一个房屋数组houses和一个整数k，其中houses[i]是第i栋房子在一条街上的位置，现需要在这条街上安排k个邮筒。
     * 请你返回每栋房子与离它最近的邮筒之间的距离的最小总和。
     * 答案保证在32位有符号整数范围以内。
     *
     * 示例 1：
     * 输入：houses = [1,4,8,10,20], k = 3
     * 输出：5
     * 解释：将邮筒分别安放在位置 3， 9 和 20 处。
     * 每个房子到最近邮筒的距离和为 |3-1| + |4-3| + |9-8| + |10-9| + |20-20| = 5 。
     *
     * 示例 2：
     * 输入：houses = [2,3,5,12,18], k = 2
     * 输出：9
     * 解释：将邮筒分别安放在位置 3 和 14 处。
     * 每个房子到最近邮筒距离和为 |2-3| + |3-3| + |5-3| + |12-14| + |18-14| = 9 。
     *
     * 示例 3：
     * 输入：houses = [7,4,6,1], k = 1
     * 输出：8
     *
     * 示例 4：
     * 输入：houses = [3,6,14,10], k = 4
     * 输出：0
     * 
     * 提示：
     * n == houses.length
     * 1 <= n<= 100
     * 1 <= houses[i] <= 10^4
     * 1 <= k <= n
     * 数组houses中的整数互不相同。
     */

    public static int minDistance(int[] houses, int k) {
        if (houses == null || houses.length < 2 || k < 1) {
            return 0;
        }
        Arrays.sort(houses);
        //i~j范围上只建立一个邮筒，负责i~j的所有居民点，总距离最小是多少
        int[][] records = getRecordArray(houses);

        int n = houses.length;
        // 0~i范围上，建议j个邮筒，总距离最小
        int[][] dp = new int[n][k + 1];
        // 第0列不考虑，0个邮筒，直接放弃, dp[i][0]不考虑
        // 第0行，只有一个居民点，建立多个邮筒，总距离都为0, dp[0][i]=0

        // 第1列，在居民点之间放1个邮筒，总是放在0~i的中间位置
        for (int i = 0; i < n; i++) {
            // record[0][i]:表示0~i范围上放一个邮筒，总距离是多少
            dp[i][1] = records[0][i];
        }

        // 普遍位置，一个样本做行，一个样本做列，总是考虑结尾的情况
        // 普遍1：j个邮筒，最后一个邮筒放置在最后一个居民点i，之前的j-1个邮筒处理0~i-1个居民点，dp[i-1][j-1]
        // 普遍2：j个邮筒，最优一个邮筒放置在i-1~i之间的居民点，之前的j-1个邮筒处理0~i-2个居民点，dp[i-2][j-1]
        // ... 最后一个邮筒的情况，都枚举一遍，答案必然在其中
        for (int i = 1; i < n; i++) {
            // 邮筒数量>=居民点数量，总距离为0，所以邮筒数量一定要小于居民点数量
            for (int j = 2; j <= Math.min(i, k); j++) {
                // 枚举最后一个邮筒负责的范围，k~i的范围上放置一个邮筒

                // 0~i位置放置一个邮筒
                dp[i][j] = records[0][i];
                for (int m = i; m > 0; m--) {
                    // 0~f-1放置k-1个邮筒，f~i放置一个邮筒
                    dp[i][j] = Math.min(dp[i][j], dp[m - 1][j - 1] + records[m][i]);
                }
            }
        }
        return dp[n - 1][k];
    }

    public static int[][] getRecordArray(int[] houses) {
        // record[i][j] 从i~j放置一个邮筒，总距离最小是多少
        // 表格子是O(N^2)规模，然后枚举每个位置的可能性，达到O(N^3)
        // 要实现O(N^2)规模
        /**
         * 思路：
         *  0~i位置，邮筒放置在中间位置
         *  1、0~0放置一个邮筒，在0位置放置邮筒，record[0][0] = 0;
         *  2、0~1放置一个邮筒，邮筒不需要动，还是0位置放置邮筒，record[0][1] = houses[1]-houses[0]的距离
         *    求出record[0][1]的具体值后，可以把邮筒挪到1位置，总距离不变(放在1位置还是0位置一样)
         *  3、0~2放置一个邮筒，邮筒不需要动，还是1位置放置邮筒，record[0][2] = record[0][1] + houses[2]-houses[1]
         *    record[0][1]:表示0~1的总距离，houses[2]-houses[1]：表示2到1的总距离
         *  4、0~3放置一个邮筒，在1位置或者2位置放置邮筒没区别，record[0][3]=record[0][2]+houses[3]-houses[1]
         *    record[0][2]:表示0~1,2~1的总距离，houses[3]-houses[1]：表示3到1的总距离
         *    求出record[0][3]的具体值后，邮筒挪到2位置，因为总距离不变(放置在1位置或者2位置)
         *  5、....,得出结论record[0][i] = record[0][i-1] + houses[i] - houses[(0+i)/2]，houses[(0+i)/2]表示邮筒的位置
         */
        int N = houses.length;
        int[][] record = new int[N][N];
        // 范围上的尝试，下半区不需要，L>R不需要
        // record[L][R] L==R 只有一个居民点，建立一个邮筒，总距离必然是0
        for (int L = 0; L < N; L++) {
            for (int R = L + 1; R < N; R++) {
                /**
                 * 推导：
                 *  L  R  邮筒
                 *  0  1  0 -> (0+1)/2
                 *  0  2  1 -> (0+2)/2
                 *  0  3  1 -> (0+3)/2
                 *  ...
                 *  邮筒肯定在L...R的中位数, arr[(L+R)/2]：邮筒的位置
                 */
                record[L][R] = record[L][R - 1] + Math.abs(houses[R] - houses[(L + R) >> 1]);
            }
        }
        return record;
    }

    // 四边形不等式优化枚举行为
    public static int minDistance1(int[] houses, int k) {
        /**
         * 四边形不等式优化
         * 特征：
         *  1、每一个格子有枚举行为
         *  2、求的动态规划的值dp[i][j]对于i和j有单调关系；
         *    假设二维参数j不变，与一维参数i的关系，i范围增大，结果会变大；
         *    假设一维参数i不变，与二维参数j的关系，i增大，结果会变小；
         *  3、所有的dp值，概念上是一种区间划分问题；
         *  4、单独求一个格子的时候，不同时依赖本行和本列的值，或者都不依赖
         *
         * 结论：
         *  如果你求的动态规划，拥有上面的三个特征，
         *  假设0~4范围给3个邮局，最优划分(最后一个邮局)处理k~4的范围，
         *  当范围变大到5，还是3个邮局，最优划分是k'~5，最优划分位置不会出现在k的左边，一定存在k<=k'
         *
         *  下限：dp[i][j] 的上限 dp[i-1][j] 或者 dp[i][j-1] -> b
         *  上限：dp[i][j] 的下限 dp[i][j+1] 或者 dp[i+1][j] -> a
         *  所以a <= dp[i][j] <= b可以省略很多枚举情况
         */
        if (houses == null || houses.length < 2 || k < 1) {
            return 0;
        }
        Arrays.sort(houses);
        //i~j范围上只建立一个邮筒，负责i~j的所有居民点，总距离最小是多少
        int[][] records = getRecordArray(houses);

        int n = houses.length;
        // 0~i范围上，建议j个邮筒，总距离最小
        int[][] dp = new int[n][k + 1];
        // 第0列不考虑，0个邮筒，直接放弃, dp[i][0]不考虑
        // 第0行，只有一个居民点，建立多个邮筒，总距离都为0, dp[0][i]=0

        // choose[i][j]：当时在求dp[i][j]的时候，k来到哪个位置最优，把此时的k记录下来
        int[][] choose = new int[n][k + 1];

        // 第1列，在居民点之间放1个邮筒，总是放在0~i的中间位置
        for (int i = 0; i < n; i++) {
            // record[0][i]:表示0~i范围上放一个邮筒，总距离是多少
            dp[i][1] = records[0][i];
        }

        // 普遍位置，一个样本做行，一个样本做列，总是考虑结尾的情况
        // 普遍1：j个邮筒，最后一个邮筒放置在最后一个居民点i，之前的j-1个邮筒处理0~i-1个居民点，dp[i-1][j-1]
        // 普遍2：j个邮筒，最优一个邮筒放置在i-1~i之间的居民点，之前的j-1个邮筒处理0~i-2个居民点，dp[i-2][j-1]
        // ... 最后一个邮筒的情况，都枚举一遍，答案必然在其中
        for (int i = 1; i < n; i++) {
            // 邮筒数量>=居民点数量，总距离为0，所以邮筒数量一定要小于居民点数量
            for (int j = Math.min(i, k); j >= 2; j--) {
                // 下界：上边的格子告诉我
                int down = choose[i - 1][j];
                // 上界：右侧的格子告诉, j就是最右的位置，认为没有上界，就是i了
                int up = j == Math.min(i, k) ? i : choose[i][j + 1];
                // 0~i位置放置一个邮筒
                dp[i][j] = records[0][i];

                // 枚举最后一个邮筒负责的范围，k~i的范围上放置一个邮筒
                for (int m = Math.max(1, down); m <= Math.min(up, i); m++) {
                    if (dp[m - 1][j - 1] + records[m][i] < dp[i][j]) {
                        // 0~f-1放置k-1个邮筒，f~i放置一个邮筒
                        dp[i][j] = dp[m - 1][j - 1] + records[m][i];
                        // choose[i][j]：记录此时的最优选择位置
                        choose[i][j] = m;
                    }
                }
            }
        }
        return dp[n - 1][k];
    }

    public static void main(String[] args) {
        int[] arr = {1, 4, 8, 10, 20};
        int num = 3;
        System.out.println(minDistance(arr, num));
        System.out.println(minDistance1(arr, num));
    }

}
