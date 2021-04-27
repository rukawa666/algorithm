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
     *  1、arr有可能有负数，比如[3,-4,2]
     *  因为你能在每个数之前要添加+号或者-号
     *  所以[3,-4,2]其实和[3,4,2]达成一样的结果
     *  那么把arr变成非负数，不会影响结果
     *
     *  2、如果arr处理成非负数，并且所有数的累加和是sum
     *  如果sum<target，此时没有任何方法可以达到target，可以直接返回0
     *
     *  3、因为题目要求所有的数字都要去拼target，
     *  所以不管这些数字怎么用+或-去处理，最终的结果都一定不会改变奇偶性
     *  所以，如果所有数的累加和是sum，并且与target的奇偶性不一样，
     *  没有任何方法可以达到target，可以直接返回0
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

    public static int findTargetSumWays3(int[] nums, int target) {
        int sum = 0;
        for (int n : nums) {
            sum += n;
        }
        return sum < target || ((sum & 1) ^ (target & 1)) != 0 ? 0 : subset1(nums, (sum + target) >> 1);
    }

    // 在arr数组中，所有数字自由选择累加和达到target的方法数有多少个？
    public static int subset1(int[] nums, int target) {
        /**
         * 动态规划
         * 所有数字自由选择，正好得出累加和是9的方法数有多少个？
         * dp[i][j] -> 在arr[0...i]自由选择，得出累加和j的方法数有多少种？
         *
         * 例子：arr={3,2,4,1,0} target=6
         */
        int N = nums.length;
        int[][] dp = new int[N][target + 1];
        // arr[0]=3 得出累加和是0的方法数，有一种，不用任何数字
        dp[0][0] = 1;
        for (int j = 1; j <= target; j++) {
            if (nums[j] == target) {
                dp[0][j] = 1;
            }
        }

        for (int i = 1; i < N; i++) {
            dp[i][0] = 1;
            if (nums[i] == target) {
                dp[i][0] += 1;
            }
        }

        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= target; j++) {
                // 不去使用当前j的值
                dp[i][j] = dp[i - 1][j];
                if (j - nums[i] >= 0) {
                    dp[i][j] += dp[i - 1][j - nums[i]];
                }
            }
        }
        return dp[N][target];
    }

    // 上述方法的空间压缩技巧
    public static int subset2(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int num : nums) {
            for (int i = target; i >= num; i--) {
                dp[i] += dp[i - num];
            }
        }
        return dp[target];
    }

    public static void main(String[] args) {
        int[] arr = {3,4,5,6,7};
        int target = 1;
        System.out.println(findTargetSumWays1(arr, target));
        System.out.println(findTargetSumWays2(arr, target));
        System.out.println(findTargetSumWays2(arr, target));
    }
}
