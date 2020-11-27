package com.rukawa.algorithm.trainingcamp.other;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-17 9:09
 * @Version：1.0
 */
public class Code02_Drive {
    /*
     * 司机调度 时间限制： 3000MS 内存限制： 589824KB 题目描述：
     * 正值下班高峰时期，现有可载客司机数2N人，调度中心将调度相关司机服务A、B两个出行高峰区域。 第 i 个司机前往服务A区域可得收入为
     * income[i][0]，前往服务B区域可得收入为 income[i][1]。
     * 返回将每位司机调度完成服务后，所有司机总可得的最高收入金额，要求每个区域都有 N 位司机服务。 输入描述 10 20 20 40 # 如上：
     * 第一个司机服务 A 区域，收入为 10元 第一个司机服务 B 区域，收入为 20元 第二个司机服务 A 区域，收入为 20元 第二个司机服务 B
     * 区域，收入为 40元 输入参数以 '#' 结束输入 输出描述 最高总收入为 10 + 40= 50，每个区域都有一半司机服务
     * 参数及相关数据异常请输出：error 样例输入 : 10 30 100 200 150 50 60 20 # 样例输出 440
     */

    public static int maxMoney(int[][] matrix) {
        return process(matrix, 0, matrix.length / 2);
    }

    // int[][] matrix N * 2大小
    // i A:matrix[i][0]  B:matrix[i][1]
    // 0...i-1司机已经做完选择了，不用再操心
    // 从i开始到最后所有的司机，在A区域还有aRest名额的情况下，返回最优分配收益
    public static int process(int[][] matrix, int i, int aRest) {
        if (aRest < 0) {
            return -1;
        }
        // 潜台词 aRest >= 0
        // 所有司机已经做完决定了
        if (i == matrix.length) {
            return aRest == 0 ? 0 : -1;
        }

        int goToA = -1;
        int nextA = process(matrix, i + 1, aRest - 1);
        if (nextA != -1) {
            goToA = matrix[i][0] + nextA;
        }

        int goToB = -1;
        int nextB = process(matrix, i + 1, aRest);
        if (nextB != -1) {
            goToB = matrix[i][1] + nextB;
        }
        return Math.max(goToA, goToB);
    }


    public static void main(String[] args) {
        int[][] matrix = { { 10, 20 }, { 20, 40 } };

        System.out.println(maxMoney(matrix));
    }
}
