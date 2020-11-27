package com.rukawa.algorithm.trainingcamp.trainingcamp5.class4;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-11-08 15:28
 * @Version：1.0
 */
public class Code05_FactorialProblem {
    /**
     * 输入数据状况：
     * power的范围[0,20]
     * arr长度范围[1,10的7次方]
     * reverse长度范围[1,10的6次方]
     */

    // 底层依赖一个等概率返回a~b的随机函数f
    // 如何加工出等概率返回c~d的随机函数
    public static int g(int a, int b, int c, int d) {
        int range = d - c;  // 0~range  c~d -> 0~d-c
        int num = 1;
        while ((1 << num) - 1 < range) {    // 求0~range需要几个2进制位
            num++;
        }
        int ans = 0;   // 最终的累加和，首先+0位上是1还是0，1位上是1还是0，2位上是1还是0 ...
        do {
            ans = 0;
            for (int i = 0; i < num; i++) {
                ans += (rand01(a, b) << i);
            }
        } while (ans > range);
        return ans + c;
    }

    // 底层依赖于一个等概率返回a~b的随机函数f
    // 如何加工出等概率返回0和1的随机函数
    public static int rand01(int a, int b) {
        int size = b - a + 1;
        boolean odd = size % 2 != 0;
        int mid = size / 2;
        int ans = 0;
        do {
            ans = f(a, b) - a;
        } while (odd && ans == mid);
        return ans < mid ? 0 : 1;
    }

    public static int f(int a, int b) {
        return a + (int) (Math.random() * (b - a + 1));
    }
}
