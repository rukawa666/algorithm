package com.rukawa.algorithm.trainingcamp.trainingcamp3.class8;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-09 7:13
 * @Version：1.0
 */
public class Code03_OneNumber {
    /**
     * 给定一个正数N，表示你在纸上写下1~N所有的数字
     * 返回在书写过程中，一共写下了多少个1
     */

    // 时间复杂度：O((lgN)^2)
    public static int solution2(int num) {
        /**
         * 42735
         * 1~2735 递归处理
         * 2736~42735 非递归处理
         *  最高位是1：10000~19999  是万位都有1，所以最高位是1的数量有10^(k-1)  k = len = 5
         *  非最高位是1：举例(千位、百位、十位、个位是1的答案一样，以十位是1为例)，此时个位、百位、千位、万位没决定。
         *   1、 2736~12735  千位、百位、个位都是随意变，一定会存在，所以10^(k-2)
         *   2、12736~22735
         *   3、22736~32735
         *   4、32736~42735
         *  所以总共有(k-1) * 10^(k-2) + 10^(k-1)
         *
         * 大的调度每次拨掉一个位置，所以时间复杂度是O(lgN)
         * 在一个调度里面求出现多少个1,时间复杂度O(lgN)
         * 所以总的时间复杂度：O((lgN)^2)
         *
         */
        if (num < 1) {
            return 0;
        }
        // num -> 13625
        // len = 5位数
        int len = getLenOfNum(num);
        if (len == 1) {
            return 1;
        }
        // num -> 13625
        // tmp1 = 10000
        int tmp1 = powerBaseOf10(len - 1);
        // 得到num最高位的数
        int first = num / tmp1;
        // 最高位是1，则总共最高位是1的有 3625 + 1
        // 最高位不是1，则总共有10000个
        int firstOneNum = first == 1 ? num % tmp1 + 1 : tmp1;
        // 除去最高位之外，剩下1的数量
        // 最高位是1，公式：(k-1) * 10^(k-2)
        // 最高位不是1，公式：(k-1) * 10^(k-2) * first ，（* first)每一组都有一个
        int otherOneNum = first * (len - 1) * (tmp1 / 10);
        return firstOneNum + otherOneNum + solution2(num % tmp1);
    }

    public static int getLenOfNum(int n) {
        int len = 0;
        while (n != 0) {
            len++;
            n /= 10;
        }
        return len;
    }

    public static int powerBaseOf10(int base) {
        return (int) Math.pow(10, base);
    }


    // 暴力方法求解
    public static int solution1(int num) {
        if (num < 1) {
            return 0;
        }
        int count = 0;
        for (int i = 1; i != num + 1; i++) {
            count += getNum1(i);
        }
        return count;
    }

    public static int getNum1(int num) {
        int res = 0;
        while (num != 0) {
            if (num % 10 == 1) {
                res++;
            }
            num /= 10;
        }
        return res;
    }

    public static void main(String[] args) {

        int num = 50000000;
        long start1 = System.currentTimeMillis();
        System.out.println(solution1(num));
        long end1 = System.currentTimeMillis();
        System.out.println("cost time: " + (end1 - start1) + " ms");

        long start2 = System.currentTimeMillis();
        System.out.println(solution2(num));
        long end2 = System.currentTimeMillis();
        System.out.println("cost time: " + (end2 - start2) + " ms");
    }
}
