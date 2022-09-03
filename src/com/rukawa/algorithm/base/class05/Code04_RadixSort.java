package com.rukawa.algorithm.base.class05;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-16 8:08
 * @Version：1.0
 */
public class Code04_RadixSort {

    // only for no-negative value
    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        radixSort(arr, 0, arr.length - 1, maxBits(arr));
    }

    // 最大数位数
    public static int maxBits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }
        int res = 0;
        while (max != 0) {
            res++;
            max /= 10;
        }
        return res;
    }

    // 复杂度 O(N * log(10,Max)), 额外的空间复杂度O(M)
    // digit 最大值的10进制位数
    public static void radixSort(int[] arr, int L, int R, int digit) {
        final int radix = 10;
        int i = 0, j = 0;
        // 有多少个数准备多少个辅助空间
        int[] help = new int[R - L + 1];
        for (int d = 1; d < digit; d++) {
            // 10个空间
            /**
             * count[0] 当前为(d位)是0的数字有多少个
             * count[1] 当前为(d位)是(0和1)的数字有多少个
             * count[2] 当前为(d位)是(0,1,2)的数字有多少个
             * count[i] 当前为(d位)是(0~i)的数字有多少个
             */
            int[] count = new int[radix];  // count[0...9]
            for (i = L; i <= R; i++) {
                j = getDigit(arr[i], d);
                count[j]++;
            }

            // 代表所有位置中小于等于i的值有多少个
            for (i = 1; i < radix; i++) {
                count[i] = count[i] + count[i - 1];
            }
            // eg：021，010，111，022，011，012
            // 从右往左遍历，
            // 012个位数字小于等于2的一共有6个，又是从右往左遍历，012填写在5位置
            // 011个位数字是1，count中小于等于1的总共有4个，又是从右往左遍历，011填写在3位置
            // 022个位数字是1，count中小于等于2的总共有5个，之前012的时候减去1，所以此时012填写在4位置
            // 111个位数字是1，count中小于等于1的总共有3个，之前011的时候减去1，所以此时011填写在2位置
            // 010个位数字是0，count中小于等于0的总共有1个，010填写在0位置
            // 021个位数字是1，count中小于等于1的总共有2个，之前111的时候减去1，所以此时021填写在1位置
            for (i = R; i >= L; i--) {
                j = getDigit(arr[i], d);
                // count[j] - 1代表位置
                help[count[j] - 1] = arr[i];
                count[j]--;
            }

            // help的数字拷贝回来，继续下一轮
            for (i = L, j = 0; i <= R; i++, j++) {
                arr[i] = help[j];
            }
        }
    }

    public static int getDigit(int x, int d) {
        return ((x / ((int) Math.pow(10, d - 1))) % 10);
    }

}
