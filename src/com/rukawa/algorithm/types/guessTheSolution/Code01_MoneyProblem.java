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

    // dp[i][j]: 如果我从0号怪兽一路通关到i号怪兽过程中，能力大于等于j的情况下，至少需要花费多少钱？达不到j能力，返回-1
    public static long func1(int[] d, int[] p) {
        return process(d, p, 0, 0);
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
        if (ability < p[index]) {
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

    public static long func1(int[] d, int[] p) {

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
        if (index == - 1) {
            return money == 0 ? 0 : -1;
        }
    }
}
