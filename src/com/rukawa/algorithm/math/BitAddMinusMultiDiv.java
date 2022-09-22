package com.rukawa.algorithm.math;

/**
 * create by hqh on 2022/9/20
 */
public class BitAddMinusMultiDiv {
    /**
     * 实现无运算符号的'+','-','*','/'
     */

    // 两个数相加
    public static int add(int x, int y) {
        // 46的二进制：32+14(8+4+2)  0101110
        // 20的二进制：16+4          0010100
        // 无进位相加'^'  进位'&'
        // ^: 0111010   &:0000100
        // 所以两个数相加=> 无进位相加 + 进位信息
        int sum = x;
        // 有进位信息
        while (y != 0) {
            sum = x ^ y;
            // 进位信息
            y = (x & y) << 1;
            x = sum;
        }
        return sum;
    }

    // x的相反数
    public static int negNum(int x) {
        return add(~x, 1);
    }

    // 两数的减法
    public static int minus(int x, int y) {
        return add(x, negNum(y));
    }

    // 两数的乘法
    public static int multi(int x, int y) {
        /**
         * x： 0 1 1 0
         * y： 0 1 1 1
         * 从右往左依次判断y的二进制是否是1
         * res = x*2^0 + x*2^1 + x*2^2
         * res = 0110 + 01100 + 011000
         */
        int res = 0;
        while (y != 0) {
            if ((y & 1) != 0) {
                res = add(res, x);
            }
            x <<= 1;
            y >>>= 1;
        }
        return res;
     }

     // 两个数相除
    public static int div(int x, int y) {
        /**
         * a/b=c  => b=01110 c=00110
         * a=b*2^1 + b*2^2
         *
         * 2的几次方*b 离a最近，找到最近的2的某次方假设是2^7  a'= a - 2^7 * b
         * 此时离a'最近的2的某次方是5  a'' = a' - 2^5 * b
         * ...
         * 找到的这些2的某次方就是c
         *
         * 2的几次方*b最接近a，只需要把b往左移动7位，a >= 2^7*b，此时有风险，b往左移动，可能来到符号位
         * 所以让a右移动，看移动几个位置能够到b
         */
        int a = isNeg(x) ? negNum(x) : x;
        int b = isNeg(y) ? negNum(y) : y;
        int res = 0;
        for (int i = 30; i >= 0; i = minus(i, 1)) {
            // 右移30位看看能不能找到接近b
            // 右移29位去尝试
            if ((a >> i) >= b) {
                // 如果右移30位，此时2^30是答案，异或进去
                res |= (1 << i);
                // a要减去2^30*b
                // 相当于b<<30 得到a'
                a = minus(a, b << i);
            }
        }
        // 只要有一个数是负数，则结果变成相反数
        return isNeg(x) ^ isNeg(y) ? negNum(res) : res;
    }

    public static int divide(int x, int y) {
        if (x == Integer.MIN_VALUE && y == Integer.MIN_VALUE) {
            return 1;
        } else if (y == Integer.MIN_VALUE) {
            return 0;
        } else if (x == Integer.MIN_VALUE) {
            // y=-1
            if (y == negNum(1)) {
                return Integer.MAX_VALUE;
            } else {
                int res = div(add(x, 1), y);
                // 系统最小值无法转相反数
                // 此时x是系统最小，可以把x+1，除以y 得到结果m
                // 然后得到结果m*y 看看和x相差多少，补偿
                int xx = multi(res, y);
                int xxx = minus(x, multi(res, y));
                int xxxx = div(minus(x, multi(res, y)), y);
                return add(res, xxxx);
            }
        } else {
            return div(x, y);
        }
    }

    public static boolean isNeg(int n) {
        return n < 0;
    }

    public static void main(String[] args) {
        int x = 7;
        int y = 8;
        System.out.println(multi(715827882, -3));
        System.out.println(add(715827882, 0));
        System.out.println(divide(-2147483648, -3));
    }
}
