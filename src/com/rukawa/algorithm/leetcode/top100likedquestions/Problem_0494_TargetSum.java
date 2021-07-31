package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * @className: Problem_0494_TargetSum
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/31 0031 22:53
 **/
public class Problem_0494_TargetSum {
    /**
     * 给你一个整数数组 nums 和一个整数 target 。
     * 向数组中的每个整数前添加'+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 ：
     * 例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，然后串联起来得到表达式 "+2-1" 。
     * 返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
     *
     * 示例 1：
     * 输入：nums = [1,1,1,1,1], target = 3
     * 输出：5
     * 解释：一共有 5 种方法让最终目标和为 3 。
     * -1 + 1 + 1 + 1 + 1 = 3
     * +1 - 1 + 1 + 1 + 1 = 3
     * +1 + 1 - 1 + 1 + 1 = 3
     * +1 + 1 + 1 - 1 + 1 = 3
     * +1 + 1 + 1 + 1 - 1 = 3
     *
     * 示例 2：
     * 输入：nums = [1], target = 1
     * 输出：1
     *
     * 提示：
     * 1 <= nums.length <= 20
     * 0 <= nums[i] <= 1000
     * 0 <= sum(nums[i]) <= 1000
     * -1000 <= target <= 1000
     */
    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        for (int n : nums) {
            sum += n;
        }
        return sum < target || ((target & 1) ^ (sum & 1)) != 0 ? 0 : subSet(nums, (sum + target) >> 1);
    }

    // 最终版本
    /**
     * 优化：
     *  1、数组可以全部处理成非负的，不会影响最终结果
     *  2、如果出现数组全部加起来的和sum<target，则0种方法
     *  3、如果出现数组全部加起来的和sum和target的奇偶性不一样，则0种方法
     *  4、数组中所有取正数的数组定义为集合P，所有取负数的数组定义为集合N，一定有 P-N=target
     *     再等式两边同时加P+N,得出P-N+P+N=target+P+N, 2P=target+sum, P=(target+sum)/2
     *     原来的动态规划的规模是index*rest的大小，现在是index*sum的规模，表的大小减小一半
     *  5、填写整张二维表的时候，空间压缩技巧。原来的二维表可以用一个数组滚动下去得到答案即可。
     */

    // 经典背包动态规划
    // 在nums中自由选择数字，得出target的方法数有多少种？
    public static int subSet(int[] nums, int target) {
        int N = nums.length;
        int[][] dp = new int[N][target + 1];
        // nums 0~0范围上，怎么得出0的累加和，一种方法
        dp[0][0] = nums[0] == 0 ? 2 : 1;
        for (int j = 1; j <= target; j++) {
            dp[0][j] = nums[0] == j ? 1 : 0;
        }
        // TODO 此处有问题，如果都是0，此次循环有问题
        for (int i = 1; i < nums.length; i++) {
            dp[i][0] = nums[i] == 0 ? 2 : 1;
        }
        // dp[i][j] -> nums[0...i]上怎么得出j
        // 完全不使用i位置的数，dp[0...i-1][j]
        // 使用i位置的数，dp[0...i-1][j-nums[i]]
        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= target; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - nums[i] >= 0) {
                    dp[i][j] += dp[i - 1][j - nums[i]];
                }
            }
        }
        return dp[N - 1][target];
    }

    // 动态规划进行空间压缩
    public static int findTargetSumWays4(int[] arr, int target) {
        int sum = 0;
        for (int n : arr) {
            sum += n;
        }
        return sum < target || ((target & 1) ^ (sum & 1)) != 0 ? 0 : subSet2(arr, (sum + target) >> 1);
    }
    // 上述方法的空间压缩技巧
    public static int subSet2(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int num : nums) {
            for (int i = target; i >= num; i--) {
                dp[i] += dp[i - num];
            }
        }
        return dp[target];
    }
}
