package com.rukawa.algorithm.math;

/**
 * @name: CommonDivisor
 * @description: 描述类的用途
 * @date: 2021/9/3 11:42
 * @auther: hanqinghai
 */
public class GreatestCommonDivisor {

    /**
     * 辗转相除法求最大公约数（欧几里德算法）
     * @param a
     * @param b
     * @return
     */
    public static int gcd1(int a, int b) {
        /**
         * 原始：(319,377)
         * 第一次：319/377=0(余319)  -> (377,319)
         * 第二次：(377/319)=1(余58) -> (319,58)
         * 第三次：(319/58)=5(余29) -> (58/29)
         * 第四次：(58/29)=2(余0) -> (29,0)
         * 余数29
         */
        return b == 0 ? a : gcd1(b, a % b);
    }

    /**
     * 更相减损法
     * @param a
     * @param b
     * @return
     */
    public static int gcd2(int a, int b) {
        /**
         * 第一步：任意给定两个正整数；判断它们是否都是偶数。若是，则用2约简；若不是则执行第二步。
         * 第二步：以较大的数减较小的数，接着把所得的差与较小的数比较，并以大数减小数。继续这个操作，直到所得的减数和差相等为止。
         * 则第一步中约掉的若干个2与第二步中等数的乘积就是所求的最大公约数。
         */
        int z = 1;
        while ((a & 1) == 0 && (b & 1) == 0) {
            a >>= 1;
            b >>= 1;
            z <<= 1;
        }
        return z * sub(a, b);
    }

    public static int sub(int a, int b) {
        int x = a > b ? a : b;
        int y = (a == x ? b : a);
        return a == b ? a : sub(y, x - y);
    }

    public static void main(String[] args) {
        int a = 260;
        int b = 104;
        System.out.println(gcd1(a, b));
        System.out.println(gcd2(a, b));
    }
}
