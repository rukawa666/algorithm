package com.rukawa.algorithm.base.class14;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/4/14 0014 0:34
 */
public class Code02_MinCoinsNoLimit {
    /**
     * arr是面值数组，其中的值都是正数且没有重复的。再给定一个正数aim。
     * 每个值都认为是一种面值，且认为张数是无限的。
     * 返回组成aim的最少货币数。
     */

    // 暴力递归
    public static int minCoins(int[] arr, int aim) {
        return process(arr, 0, aim);
    }

    /**
     * @param arr 面值数组，每种面值自由选择
     * @param index 从index...开始到往后
     * @param rest 搞出正好rest的钱数
     * @return  返回最小张数
     */
    public static int process(int[] arr, int index, int rest) {
        // 没钱了，剩余的钱也为0 此时需要的最少张数是0张
        if (index == arr.length) {
            return rest == 0 ? 0 : -1;
        }

        int res = -1;
        for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
            int next = process(arr, index + 1, rest - zhang * arr[index]);
            if (next != -1) {
                if (res == -1) {
                    res = next + zhang;
                } else {
                    res = Math.min(res, next + zhang);
                }
            }
        }
        return res;
    }

    // 暴力递归改动态规划
    public static int dp1(int[] arr, int aim) {
        int n = arr.length;
        int[][] dp = new int[n + 1][aim + 1];
        for (int j = 1; j <= aim; j++) {
            dp[n][j] = -1;
        }
        dp[n][0] = 0;
        for (int i = n - 1; i >= 0; i--) {
            for (int rest = 0; rest <= aim; rest++) {
                int res = -1;
                for (int zhang = 0; zhang * arr[i] <= rest; zhang++) {
                    int next = dp[i + 1][rest - zhang * arr[i]];
                    if (next != -1) {
                        if (res == -1) {
                            res = next + zhang;
                        } else {
                            res = Math.min(res, next + zhang);
                        }
                    }
                }
                dp[i][rest] = res;
            }
        }
        return dp[0][aim];
    }

    // 动态规划优化
    public static int dp3(int[] arr, int aim) {
        int n = arr.length;
        int[][] dp = new int[n + 1][aim + 1];
        for (int j = 1; j <= aim; j++) {
            dp[n][j] = -1;
        }
        dp[n][0] = 0;
        for (int i = n - 1; i >= 0; i--) {
            for (int rest = 0; rest <= aim; rest++) {
                /**
                 * 枚举行为优化：当前面值的钱是3元，但是需要搞定14元
                 * dp[3][14] 依赖的位置有 dp[4][14-0*3] dp[4][14-1*3] dp[4][14-2*3] dp[4][14-3*3] dp[4][14-4*3]
                 *
                 * dp[3][11] 依赖的位置有 dp[4][11-0*3] dp[4][11-1*3] dp[4][11-2*3] dp[4][11-3*3]
                 *
                 * 所以dp[3][14] 依赖 min{dp[3][11]+1,dp[4][14-0*3]}
                 */
                dp[i][rest] =  dp[i + 1][rest];
                if (rest - arr[i] >= 0 && dp[i][rest - arr[i]] != -1) {
                    if (dp[i][rest] == -1) {
                        dp[i][rest] = dp[i][rest - arr[i]] + 1;
                    } else {
                        dp[i][rest] = Math.min(dp[i][rest], dp[i][rest - arr[i]] + 1);
                    }
                }
            }
        }
        return dp[0][aim];
    }

    public static int dp2(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }
        /**
         * 假设当前rest=15，arr[index]=3
         * A、分析枚举行为：dp[index][rest]
         *      dp[index + 1][rest - zhang * arr[index]]
         *      1、如果张数为0，则依赖dp[index + 1][15]，选择为0张
         *      2、如果张数为1，则依赖dp[index + 1][12]， 选择为1张
         *      3、如果张数为2，则依赖dp[index + 1][9]， 选择为2张
         *      4、。。。
         * B、再分析dp[index][12]左侧的格子，依赖分别是
         *      1、如果张数为0，则依赖dp[index + 1][12]，选择0张
         *      2、如果张数为1，则依赖dp[index + 1][9]，选择1张
         *      3、如果张数为2，则依赖dp[index + 1][6]，选择2张
         *      4、。。。
         *  最终分析得到，dp[index][rest]依赖dp[index+1][rest]和dp[index][rest-arr[index]]+1获取最小值
         */
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                // rest - arr[index] >= 0 保证数组不越界
                // dp[index][rest - arr[index]] != Integer.MAX_VALUE 保证左边的格子是有效值
                if (rest - arr[index] >= 0 && dp[index][rest - arr[index]] != Integer.MAX_VALUE) {
                    dp[index][rest] = Math.min(dp[index][rest], dp[index][rest - arr[index]] + 1);
                }
            }
        }
        return dp[0][aim];
    }

    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        boolean[] has = new boolean[maxValue + 1];
        for (int i = 0; i < N; i++) {
            do {
                arr[i] = (int) (Math.random() * maxValue) + 1;
            } while (has[arr[i]]);
            has[arr[i]] = true;
        }
        return arr;
    }

    // 为了测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 为了测试
    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 300000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxLen);
            int[] arr = randomArray(N, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = minCoins(arr, aim);
            int ans2 = dp1(arr, aim);
            int ans3 = dp3(arr, aim);
            if (ans1 != ans2 && ans1 != ans3) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("功能测试结束");
    }
}
