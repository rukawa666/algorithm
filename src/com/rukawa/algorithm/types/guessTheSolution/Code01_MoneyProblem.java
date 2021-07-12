package com.rukawa.algorithm.types.guessTheSolution;

/**
 * @className: Code01_
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/11 0011 23:43
 **/
public class Code01_MoneyProblem {
    /**
     * int[] d, d[i]: i号怪兽的能力
     * int[] p, p[i]: i号怪兽要求的钱
     * 开始时你的能力是0，你的目标是从0号怪兽开始，通过所有的怪兽、
     * 如果你当前的能力，小于i号怪兽的能力，你必须付出p[i]的钱，贿赂这个怪兽，然后怪兽就会加入你，它的能力直接累加到你的能力上；
     * 如果你当前的能力，大于等于i号怪兽的能力，你可以选择直接通过，你的能力并不会下降，你也可以直接贿赂这个怪兽，然后怪兽就会直接加入你，它的能力直接累加到你的能力上。
     * 返回通过所有的怪兽，需要花的最小钱数。
     */

    // 怪兽花费的钱1~10之间，怪兽的能力值在10^6以上
    // 怪兽的能力减少，但是钱数变大了，选择第一种方案

    // dp[i][j]: 如果我从0号怪兽一路通关到i号怪兽过程中，能力大于等于j的情况下，至少需要花费多少钱？达不到j能力，返回-1
    public static long func1(int[] d, int[] p) {
        return process1(d, p, 0, 0);
    }

    /**
     * 目前你的能力是ability，来到了index号怪兽的面前，如果要通过后续所有的怪兽
     * 请返回需要花的最少钱数
     * @param d 能力数组
     * @param p 钱数组
     * @param ability 当前人的能力
     * @param index 来到了第index号怪兽的 面前
     * @return
     */
    public static long process1(int[] d, int[] p, int ability, int index) {
        // 如果已经到达终止位置，只需要花费0元
        if (index == d.length) {
            return 0;
        }

        // 如果人的能力小于怪兽的能力，则必须花钱
        if (ability < d[index]) {
            return p[index] + process1(d, p, ability + d[index], index + 1);
        } else {    // 可以花钱也可以不花钱
            return Math.min(p[index] + process1(d, p, ability + d[index], index + 1), process1(d, p, ability, index + 1));
        }
    }
    // 如果告诉你，怪兽的能力值在10^8~10^12之间，做出一张表dp表，列是能力的累加和，这张表不可能在10^8内填写完
    // dp[i][j]: 如果我从0号怪兽一路通关到i号怪兽过程中，能力大于等于j的情况下，至少需要花费多少钱？达不到j能力，返回-1

    // 另外一种思路：
    // dp[i][j]: 当前来到i号怪兽，j是严格花的钱数，dp[i][j],从0号怪兽一路通关到i号怪兽，严格花费j元的情况下，达到的最大能力？达不到返回-1
    // 最后一行，从左往右，哪一个不是-1就是答案

    // 如果一个怪兽花费的钱非常大，此方法不适用
    // 怪兽的钱数都是1~10的数字，怪兽的规模有100个，10^8肯定通过
    public static long func2(int[] d, int[] p) {
        int allMoney = 0;
        for (int i = 0; i < p.length; i++) {
            allMoney += p[i];
        }
        int N = d.length;
        for (int money = 0; money < allMoney; money++) {
            // 最后一行从左到右只要能通过就是最终答案
            if (process2(d, p, N - 1, money) != -1) {
                return money;
            }
        }
        // 否则就是花掉所有的钱
        return allMoney;
    }

    /**
     * 从0~index号怪兽，花的钱，必须严格==money
     * 如果不通过，返回-1
     * 如果可以通过，返回能通过情况下的最大能力值
     * @param d 能力数组
     * @param p 钱数组
     * @param index 来到了第index号怪兽的面前
     * @param money 花费的钱严格==money
     * @return
     */
    public static long process2(int[] d, int[] p, int index, int money) {
        if (index == - 1) {     // 一个怪兽也没遇到
            return money == 0 ? 0 : -1;
        }

        // index >= 0
        // 1、不贿赂当前index号怪兽，从0~index-1 花费130的能力是多少
        long preMaxAbility = process2(d, p, index - 1, money);
        long p1 = -1;
        // 之前的过程要有效 且 之前得到的能力值要>=当前怪兽的能力值 才能通关
        if (preMaxAbility != -1 && preMaxAbility >= d[index]) {
            p1 = preMaxAbility;
        }

        // 2、贿赂当前怪兽
        preMaxAbility = process2(d, p, index - 1, money - p[index]);
        long p2 = -1;
        if (preMaxAbility != -1) {
            p2 = preMaxAbility + d[index];
        }
        return Math.max(p1, p2);
    }
    // func1方法改动态规划
    public static long func3(int[] d, int[] p) {
        int sum = 0;
        for (int num : d) {
            sum += num;
        }
        long[][] dp = new long[d.length + 1][sum + 1];
        for (int i = 0; i <= sum; i++) {
            dp[0][i] = 0;
        }

        for (int cur = d.length - 1; cur >= 0; cur--) {
            for (int hp = 0; hp <= sum; hp++) {
                if (hp + d[cur] > sum) {
                    continue;
                }

                if (hp < d[cur]) {
                    dp[cur][hp] = p[cur] + dp[cur + 1][hp + d[cur]];
                } else {
                    dp[cur][hp] = Math.min(p[cur] + dp[cur + 1][hp + d[cur]], dp[cur + 1][hp]);
                }
            }
        }
        return dp[0][0];
    }


    // func2方法改动态规划
    public static long func4(int[] d, int[] p) {
        int sum = 0;
        for (int num : p) {
            sum += num;
        }
        // dp[i][j]的含义
        // 能经过0~i的怪兽，且花钱为j(花的钱严格等于j)时的能力值最大是多少？
        // 如果dp[i][j] == -1,表示经过0~i的怪兽，花钱为j是无法通过的，或者之前的钱怎么组合也得不到正好为j的钱数
        int[][] dp = new int[d.length][sum + 1];
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j <= sum; j++) {
                dp[i][j] = -1;
            }
        }
        // 经过0~i的怪兽，花钱一定是p[0]，达到的能力值d[0]的地步。其他第0行的状态一律是无效的
        dp[0][p[0]] = d[0];
        for (int i = 1; i < d.length; i++) {
            for (int j = 0; j <= sum; j++) {
                // 1、当前怪兽不被贿赂
                if (dp[i - 1][j] >= d[i]) {
                    dp[i][j] = dp[i - 1][j];
                }
                // 2、贿赂当前怪兽
                // 保证之前一步花费j-p[i]这么多钱，有有效能力值
                if (j - p[i] >= 0 && dp[i - 1][j - p[i]] != -1) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - p[i]] + d[i]);
                }
            }
        }
        int ans = 0;
        for (int j = 0; j <= sum; j++) {
            if (dp[d.length - 1][j] != -1) {
                ans = j;
                break;
            }
        }
        return ans;
    }
    public static int[][] generateTwoRandomArray(int len, int value) {
        int size = (int) (Math.random() * len) + 1;
        int[][] arrs = new int[2][size];
        for (int i = 0; i < size; i++) {
            arrs[0][i] = (int) (Math.random() * value) + 1;
            arrs[1][i] = (int) (Math.random() * value) + 1;
        }
        return arrs;
    }

    public static void main(String[] args) {
        int len = 10;
        int value = 20;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            int[][] arrs = generateTwoRandomArray(len, value);
            int[] d = arrs[0];
            int[] p = arrs[1];
            long ans1 = func1(d, p);
            long ans2 = func2(d, p);
            long ans3 = func3(d, p);
            long ans4 = func4(d,p);
            if (ans1 != ans2 || ans2 != ans3 || ans1 != ans4) {
                System.out.println("oops!");
            }
        }

    }
}
