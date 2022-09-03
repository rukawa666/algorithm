package com.rukawa.algorithm.base.class01;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-10 8:11
 * @Version：1.0
 */
public class Code08_EvenTimesOddTimes {

    /**
     * o ^ N = N;
     * N ^ N = 0;
     * a ^ b = b ^ a;
     */

    // arr中，只有一种数，出现奇数次
    public static void printOddTimesNum1(int[] arr) {
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        System.out.println(eor);
    }

    // arr中，有两种数，出现奇数次
    public static void printOddTimesNum2(int[] arr) {
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        // eor = a ^ b  a,b为两个奇数
        // eor != 0
        // eor必然有一个位置为1，随便获取一个位置的1，人为规定拿最右侧的1
        int rightOne = eor & (~eor + 1);    // 提取出最后侧的1

        // 挑选最右侧为1的数
        int onlyOne = 0; // eor'
        // 把最右侧为1的数字提取出来
        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] & rightOne) != 0) {
                onlyOne ^= arr[i];
            }
        }
        // a = b ^ c
        // c = a ^ b 满足交换律
        System.out.println(onlyOne + " " + (eor ^ onlyOne));
    }

    public static void main(String[] args) {
        int[] arr = {2,2,3,4,4,5,6,6};
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        System.out.println(eor + "," + (3 ^ 5));
        int mosRight = eor & (-eor);
        System.out.println(mosRight);
        int onlyOne = 0;
        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] & mosRight) != 0) {
                onlyOne ^= arr[i];
            }
        }
        System.out.println(onlyOne);
        System.out.println(onlyOne ^ eor);

    }
}
