package com.rukawa.algorithm.trainingcamp.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-11 0:49
 * @Version：1.0
 */
public class Problem_0371_SumOfTwoIntegers {
    /**
     * 两整数之和
     * 不使用运算符 + 和 - ​​​​​​​，计算两整数 ​​​​​​​a 、b ​​​​​​​之和。
     *
     * 示例 1:
     * 输入: a = 1, b = 2
     * 输出: 3
     *
     * 示例 2:
     * 输入: a = -2, b = 3
     * 输出: 1
     */
    public static int getSum(int a, int b) {
        int sum = 0;
        while (b != 0) {
            sum = a ^ b;   // 无进位相加
            b = (a & b) << 1;   //
            a = sum;
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(getSum(7, 8));
    }
}
