package com.rukawa.algorithm.interview.series1;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/4/20 0020 20:22
 */
public class Code03_Near2Power {
    /**
     * 已知n是正数
     * 返回大于等于，且最接近n的，2的某次方的值
     */
    public static final int tableSizeFor(int n) {
        // 如果刚好是2的某次方，先--，最后+1，此时自己本身即是最接近的值
        n--;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        // 如果是负数，只有2的0次方为1
        return (n < 0) ? 1 : n + 1;
    }

    public static void main(String[] args) {
        System.out.println(tableSizeFor(7));
    }
}
