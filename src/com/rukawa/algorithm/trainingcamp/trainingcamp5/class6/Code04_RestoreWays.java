package com.rukawa.algorithm.trainingcamp.trainingcamp5.class6;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-11-12 8:06
 * @Version：1.0
 */
public class Code04_RestoreWays {
    /**
     * 整型数组arr长度为n(3 <= n <= 10^4)，最初每个数字是 <=200 的正数且满足如下条件：
     * 1、0位置的要求：arr[0] <= arr[1]
     * 2、n-1位置的要求：arr[n - 1] <= arr[n - 2]
     * 3、中间i位置的数的要求：arr[i] <= max(arr[i - 1], arr[i + 1])
     * 但是在arr中有些数字丢失了，比如k位置的数字之前是正数，丢失之后k位置的数字变为0.
     * 请你根据以上条件，计算可能有多少种不同的arr可以满足以上的条件。
     * 比如[6,0,9]只有还原成[6,9,9]满足全部三个条件，所以返回1种
     */
    // 01:42:35
    // 纯暴力解
    public static int ways0(int[] arr) {
        return process0(arr, 0);
    }
    // 纯暴力解
    public static int process0(int[] arr, int index) {
        if (index == arr.length) {
            return isValid(arr) ? 1 : 0;
        } else {
            if (arr[index] != 0) {
                return process0(arr, index + 1);
            } else {
                int ways = 0;
                for (int v = 1; v < 201; v++) {
                    arr[index] = v;
                    ways += process0(arr, index + 1);
                }
                arr[index] = 0;
                return ways;
            }
        }
    }

    public static boolean isValid(int[] arr) {
        if (arr[0] > arr[1]) {
            return false;
        }
        if (arr[arr.length - 1] > arr[arr.length - 2]) {
            return false;
        }
        for (int i = 1; i < arr.length - 1; i++) {
            if (arr[i] > Math.max(arr[i - 1], arr[i + 1])) {
                return false;
            }
        }
        return true;
    }

    // 正儿八经的尝试
    public static int ways1(int[] arr) {
        /**
         * 思路：
         *   1、int f(v, i, s)：现在来到i位置，i位置的数一定要变成是v，s是右边数与当前数的关系,从0到i有几种有效的变法？
         *      I、s的含义：
         *          a、s==0，代表arr[index] < arr[index+1]
         *          b、s==1，代表arr[index] = arr[index+1]
         *          c、s==2，代表arr[index] > arr[index+1]
         *
         *  流程：arr=[6, 0, 9]  [6,9,0]
         *  1、如果最右侧数字不是0，f(9,2,?)
         *     满足条件2和条件3，arr[i] <= arr[i-1],此时arr[i+1]的数字是个无效数字，
         *     右边没有数，只能默认小于arr[N-1]的数字，所以arr[i]达标，只需要考虑arr[i]<=arr[i-1]即可
         *     所以调用主函数f(9,2,2)
         *  2、如果最右侧的数字是0，f(0,2,?)
         *     最右侧的数字是丢掉的数字，f(1, N-1, 2) + f(2, N-1, 2) + ... + f(200, N-1, 2)
         *     把上面值加起来就是最终的答案？不考虑i-1的数字，只考虑i和i+1位置的数字，i+1位置没有数字，所以i位置随意填写
         */
        int N = arr.length;
        if (arr[N - 1] != 0) {
            return process1(arr, N - 1, arr[N - 1], 2);
        } else {
            int ways = 0;
            for (int v = 1; v < 201; v++) {
                ways += process1(arr, N - 1, v, 2);
            }
            return ways;
        }
    }
    // int f(i, v, s)：现在来到i位置，i位置的数一定要变成是v，s是右边数与当前数的关系,从0到i有几种有效的变法？
    // s的含义：
    //    a、s==0，代表arr[i] < arr[i+1]
    //    b、s==1，代表arr[i] = arr[i+1]
    //    c、s==2，代表arr[i] > arr[i+1]
    // 返回0...i范围上有多少种有效的方式？
    public static int process1(int[] arr, int i, int v, int s) {
        if (i == 0) { // 代表0~i 只剩下一个数  0位置的要求：arr[0] <= arr[1]
            // 右侧 > v 或者 右侧 = v     arr[i]=0，代表i位置数字丢失，或者v就想变成arr[i]
            return ((s == 0 || s == 1) && (arr[i] == 0 || v == arr[i])) ? 1 : 0;
        }
        // i > 0
        // i位置的数字没有丢失且v变成的数字不是i位置的数字返回0种方法
        if (arr[i] != 0 && v != arr[i]) {
            return 0;
        }
        int ways = 0;
        // arr[i+1]>=arr[i]
        if (s == 0 || s == 1) { //arr[i] <= max(arr[i - 1], arr[i + 1]) 现在arr[i]<arr[i+1] 所以arr[i-1]的值随便
            for (int pre = 1; pre < 201; pre++) {
                ways += process1(arr, i - 1, pre, pre < v ? 0 : (pre == v ? 1 : 2));
            }
        } else { // arr[i+1] < arr[i], 所以arr[i-1] >= arr[i],所以arr[i-1]>=v即可
            for (int pre = v; pre < 201; pre++) {
                ways += process1(arr, i - 1, pre, pre == v ? 1 : 2);
            }
        }
        return ways;
    }

    // 改成记忆化搜索
    // 02:31:35
    public static int ways2(int[] arr) {
        int N = arr.length;
        int[][][] dp = new int[N][201][3];
        /**
         * 第i层要填写，需要依赖i-1层，所以先填写最底层，然后依次往上推
         */
        // 先填写dp[0][...][...]
        if (arr[0] != 0) {
            // 如果0位置！= 0，只能变成arr[0]的值
            dp[0][arr[0]][0] = 1;
            dp[0][arr[0]][1] = 1;
        } else {
            // 如果arr[0]==0, i位置的数字随意
            for (int v = 1; v < 201; v++) {
                dp[0][v][0] = 1;
                dp[0][v][1] = 1;
            }
        }

        for (int i = 1; i < N; i++) {
            for (int v = 1; v < 201; v++) {
                for (int s = 0; s < 3; s++) {
                    if (arr[i] == 0 || v == arr[i]) {
                        if (s == 0 || s == 1) {
                            for (int pre = 1; pre < 201; pre++) {
                                dp[i][v][s] += dp[i - 1][pre][pre < v ? 0 : (pre == v ? 1 : 2)];
                            }
                        } else {
                            for (int pre = v; pre < 201; pre++) {
                                dp[i][v][s] += dp[i - 1][pre][pre == v ? 1 : 2];
                            }
                        }
                    }
                }
            }
        }
        // 主函数怎么调
        if (arr[N - 1] != 0) {
            return dp[N - 1][arr[N - 1]][2];
        } else {
            int ways = 0;
            for (int v = 1; v < 201; v++) {
                ways += dp[N - 1][v][2];
            }
            return ways;
        }
    }

    // 改成常规的动态规划
    // 02:42:40
    // 时间复杂度 O(N * 201 * 3)  preSum O(N * 201 * 3)
    public static int ways3(int[] arr) {
        /**
         * dp[i][v][s]依赖哪些状态？
         * 1、首先看dp[i][v][0]的情况
         *    a、dp[i][100分][0] = dp[i-1][1...99][0] + dp[i-1][100分][1] + dp[i-1][101...200][2]
         *    b、dp[i][100分][1] = dp[i-1][1...99][0] + dp[i-1][100分][1] + dp[i-1][101...200][2]
         *    b、dp[i][100分][2] = dp[i-1][100分][1] + dp[i-1][101...200][2]
         *
         * 2、假设有三个个数据结构分别是,隐含的含义是对i-1位面负责，是对中间位面前缀和的结果
         *      sum0[0...200] 代表dp[i-1][0...s][0]
         *      sum1[0...200] 代表dp[i-1][0...s][1]
         *      sum2[0...200] 代表dp[i-1][0...s][2]
         *
         * 3、a、dp[i][100分][0] = dp[i-1][1...99][0] + dp[i-1][100分][1] + dp[i-1][101...200][2] 可以替换为
         *       dp[i][100分][0] = sum0[99] - sum0[0] + dp[i-1][100分][1] + sum2[200] - sum2[100分]
         */
        int N = arr.length;
        int[][][] dp = new int[N][201][3];
        /**
         * 第i层要填写，需要依赖i-1层，所以先填写最底层，然后依次往上推
         */
        // 先填写dp[0][...][...]
        if (arr[0] != 0) {
            // 如果0位置！= 0，只能变成arr[0]的值
            dp[0][arr[0]][0] = 1;
            dp[0][arr[0]][1] = 1;
        } else {
            // 如果arr[0]==0, i位置的数字随意
            for (int v = 1; v < 201; v++) {
                dp[0][v][0] = 1;
                dp[0][v][1] = 1;
            }
        }
        // preSum[0...v][0] -> sum0
        // preSum[0...v][1] -> sum1
        // preSum[0...v][2] -> sum2
        int[][] preSum = new int[201][3];
        // 先填写0位面的preSum
        for (int v = 1; v < 201; v++) {
            for (int s = 0; s < 3; s++) {
                preSum[v][s] = preSum[v - 1][s] + dp[0][v][s];
            }
        }

        // O(600 * N) O(N * V * 3) = O(N)
        for (int i = 1; i < N; i++) {
            // O(600)
            for (int v = 1; v < 201; v++) {
                for (int s = 0; s < 3; s++) {
                    if (arr[i] == 0 || v == arr[i]) {
                        if (s == 0 || s == 1) {
                            // 省略枚举行为
                            dp[i][v][s] += sum(1, v - 1, 0, preSum);
                            dp[i][v][s] += dp[i - 1][v][1];
                            dp[i][v][s] += sum(v + 1, 200, 2, preSum);
                        } else {
                            dp[i][v][s] += dp[i - 1][v][1];
                            dp[i][v][s] += sum(v + 1, 200, 2, preSum);
                        }
                    }
                }
            }
            // 处理当前位面的前缀和
            // O(600)
            for (int v = 1; v < 201; v++) {
                for (int s = 0; s < 3; s++) {
                    preSum[v][s] = preSum[v - 1][s] + dp[i][v][s];
                }
            }
        }

        // 主函数怎么调
        if (arr[N - 1] != 0) {
            return dp[N - 1][arr[N - 1]][2];
        } else {
            int ways = 0;
            for (int v = 1; v < 201; v++) {
                ways += dp[N - 1][v][2];
            }
            return ways;
        }
    }

    public static int sum(int begin, int end, int relation, int[][] preSum) {
        return preSum[end][relation] - preSum[begin - 1][relation];
    }
}
