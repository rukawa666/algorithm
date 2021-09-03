package com.rukawa.algorithm.math;

/**
 * @name: LeastCommonMultiple
 * @description: 描述类的用途
 * @date: 2021/9/3 15:03
 * @auther: hanqinghai
 */
public class LeastCommonMultiple {

    public static int lcm(int a, int b) {
        return a * b / gcb(a, b);
    }

    public static int gcb(int a, int b) {
        return b == 0 ? a : gcb(b, a % b);
    }
}
