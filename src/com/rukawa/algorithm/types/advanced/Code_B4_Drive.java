package com.rukawa.algorithm.types.advanced;

/**
 * @name: Code_B4_Drive
 * @description: 描述类的用途
 * @date: 2021/8/7 20:38
 * @auther: hanqinghai
 */
public class Code_B4_Drive {
    /**
     * 司机调度 时间限制：3000MS 内存限制：589824KB
     * 题目描述:
     * 正直下班高峰期，现在可载司机数2N人，调度中心将调度相关司机服务A、B两个出行高峰地区。第i个司机前往服务A区域可得收入为income[i][0]，前往
     * 服务B区域可得收入为income[i][1]
     * 返回将每位司机调度完成服务后，所有司机总可得的最高收入金额，要求每个区域都有N位司机服务。输入描述10 20 20 40 # 如上：
     * 第一个司机服务A区域，收入为10元，第一个服务B区域，收入为20元，第二个司机服务A区域，收入为20元，第二个司机服务B区域，收入为40元
     * 输入参数为"#"结束输入输出描述 最高总收入为10+40=50，每个区域都有一半司机服务
     * 参数及相关数据异常请输出：error 样例输入：10 30 100 200 150 50 60 20 #样例输出 440
     */

    // 给定一个N*2的正数矩阵matrix，N一定是偶数，可以保证，一定要让A区域分到N/2个司机，让B区域也分到N/2个司机
    // 返回最大的总收益

    // 这是暴力解
    public static int maxMoney1(int[][] income) {
        if (income == null || income.length < 2 || (income.length & 1) != 0) {
            return 0;
        }
        // 司机数量一定是偶数，所以才能平均分配，A N/2 B N/2
        int N = income.length;
        // 要去A区域的人
        int M = N >> 1;
        return process1(income, 0, M);
    }

    // index...所有的司机，往A和B平均分配
    // A区域还有rest个名额
    // 返回把index及其之后的司机全部分配完，并且最终A区域和B区域同样多的情况下，index...这些司机，整体收入最大是多少？
    // 有10个司机，A区域去5个  B区域去5个  从0号司机开始，A区域还剩余5个名额，把0号司机分配给A -> f(0,5)，现在1号司机去A区域还有4个名额f(1,4)
    // 在A区域还剩下4个名额的情况下，最终达成A区域和B区域都去5个，返回1号司机及其后面所有的司机，收入是多少，0号司机不用管
    public static int process1(int[][] income, int index, int rest) {
        // index及其往后的司机，收入是0
        if (index == income.length) {
            return 0;
        }
        // 还剩下司机
        // 剩下司机的数量正好等于A区域的名额，还剩下3个司机没有分配，A区域还剩下3个名额，说明B区域已经满了，剩下3个司机只能去A区域
        if (income.length - index == rest) {
            // 该司机去A区域的金额+后续司机去A区域的金额
            return income[index][0] + process1(income, index + 1, rest - 1);
        }
        // 还有司机，但是A区域满了，剩下司机只能去B区域
        if (rest == 0) {
            // 去往B区域，后续的司机陆续去B区域
            return income[index][1] + process1(income, index + 1, rest);
        }
        // 当前司机可以去A，也可以去B
        // 当前司机去A区域
        int p1 = income[index][0] + process1(income, index + 1, rest - 1);
        // 当前司机去B区域
        int p2 = income[index][1] + process1(income, index + 1, rest);
        return Math.max(p1, p2);
    }

    // 暴力递归该动态规划
    public static int maxMoney2(int[][] income) {
        int N = income.length;
        int M = N >> 1;
        int[][] dp = new int[N + 1][M + 1];
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= M; j++) {
                // 还剩下司机
                // 剩下司机的数量正好等于A区域的名额，还剩下3个司机没有分配，A区域还剩下3个名额，说明B区域已经满了，剩下3个司机只能去A区域
                if (N - i == j) {
                    dp[i][j] = income[i][0] + dp[i + 1][j - 1];
                } else if (j == 0) {    // 还有司机，但是A区域满了，剩下司机只能去B区域
                    dp[i][j] = income[i][1] + dp[i + 1][j];
                } else {    // 当前司机可以去A，也可以去B
                    // 当前司机去A区域
                    int p1 = income[i][0] + dp[i + 1][j - 1];
                    // 当前司机去B区域
                    int p2 = income[i][1] + dp[i + 1][j];
                    dp[i][j] = Math.max(p1, p2);
                }
            }
        }
        return dp[0][M];
    }

    // 返回随机len*2大小的正数矩阵
    // 值在0~value-1之间
    public static int[][] randomMatrix(int len, int value) {
        int[][] ans = new int[len << 1][2];
        for (int i = 0; i < ans.length; i++) {
            ans[i][0] = (int) (Math.random() * value);
            ans[i][1] = (int) (Math.random() * value);
        }
        return ans;
    }

    public static void main(String[] args) {
        int N = 10;
        int value = 100;
        int testTime = 500;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N) + 1;
            int[][] matrix = randomMatrix(len, value);
            int ans1 = maxMoney1(matrix);
            int ans2 = maxMoney2(matrix);
//            int ans3 = maxMoney3(matrix);
            if (ans1 != ans2) {
                System.out.println(ans1);
                System.out.println(ans2);
//                System.out.println(ans3);
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }
}
