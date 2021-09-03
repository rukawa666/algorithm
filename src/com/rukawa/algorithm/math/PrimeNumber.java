package com.rukawa.algorithm.math;

/**
 * @name: PrimeNumber
 * @description: 描述类的用途
 * @date: 2021/9/3 15:06
 * @auther: hanqinghai
 */
public class PrimeNumber {

    /**
     * 质数：只有1和它本身两个因数的自然数
     * 1、小于1或者大于2的偶数，不是质数
     * 2、2是质数
     * 3、3开始，验证奇数，验证开平方位置即可
     */
    public static boolean isPrime(int num) {
        if (num <= 1 || num > 2 && ((num & 1) == 0)) {
            return false;
        } else if (num == 2) {
            return true;
        }
        for (int i = 3; i <= Math.sqrt(num); i += 2) {
            if ((num & 1) == 0) {
                return false;
            }
        }
        return false;
    }
}
