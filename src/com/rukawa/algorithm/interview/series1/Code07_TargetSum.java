package com.rukawa.algorithm.interview.series1;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/4/20 0020 20:50
 */
public class Code07_TargetSum {
    /**
     * 给定一个数组arr，你可以在每个数字之前决定+或者-
     * 但是必须所有数字都参与
     * 在给定一个数target，请问最后算出target的方法数是多少个？
     */

    public static int findTargetSumWays1(int[] arr, int target) {
        return process1(arr, 0, target);
    }

    // 可以自由使用arr[index...]所有的数字
    // 搞出rest这个数，方法数是多少？
    public static int process1(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        }
        // rest + arr[index] -> 减去这个数
        // rest - arr[index] -> 加上这个数
        return process1(arr, index + 1, rest + arr[index]) + process1(arr, index + 1, rest - arr[index]);
    }

    public static int findTargetSumWays2(int[] arr, int target) {
        return process2(arr, 0, target, new HashMap<>());
    }

    /**
     * HashMap<Integer, HashMap<Integer, Integer>> dp
     * index: 7   rest: 13  result: 256
     * {7: {13 : 256}}
     */
    public static int process2(int[] arr, int index, int rest, HashMap<Integer, HashMap<Integer, Integer>> dp) {
        // 缓存命中
        if (dp.containsKey(index) && dp.get(index).containsKey(rest)) {
            return dp.get(index).get(rest);
        }

        int ans = 0;
        if (index == arr.length) {
            ans = rest == 0 ? 1 : 0;
        } else {
            ans = process2(arr, index + 1, rest - arr[index], dp) + process2(arr, index + 1, rest + arr[index], dp);
        }
        if (!dp.containsKey(index)) {
            dp.put(index, new HashMap<>());
        }
        dp.get(index).put(rest, ans);
        return ans;
    }

    /**
     * 优化点：
     *
     *  4、比如说给定一个数组, arr = [1, 2, 3, 4, 5] 并且 target = 3
     *  其中一个方案是：+1  -2  +3  -4  +5
     *  该方案取了正数的集合定义为p:{1, 3, 5}
     *  该方案取了负数的集合定义为n:{-2, -4}
     *  所以对于任何一种方案，都有sum(p) - sum(n) = target
     *  现在在等式的左右两边同时+sum(p)+sum(n)，等式变成
     *  2sum(p) = target + sum(p) + sum(n)   sum(p) + sum(n)为所有数的累加和
     *  此时等式为：2sum(p) = target + 所有数的累加和
     *  sum(p) = (target + 所有数的累加和) / 2
     *  只要有一个集合是上面的情况，就一定意味着一种组成target的方法
     *
     *  5、二维动态规划的空间压缩技巧
     */

    // TODO 问模代码
    /*public static int dp(int[] arr, int target) {
        int N = arr.length;
        int[][] dp = new int[N + 1][target + 1];
        // dp[N][1..] = 0
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= target; rest++) {
                if (rest - arr[index] >= 0) {
                    dp[index][rest] += dp[index + 1][rest - arr[index]];
                }

                if (rest + arr[index] <= target) {
                    dp[index][rest] = dp[index + 1][rest + arr[index]];
                }
            }
        }
        return dp[0][target];
    }*/

    public static void main(String[] args) {
        int[] arr = {3,4,5,6,7};
        int target = 1;
        System.out.println(findTargetSumWays1(arr, target));
        System.out.println(findTargetSumWays2(arr, target));
//        System.out.println(dp(arr, target));
    }
}
