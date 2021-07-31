package com.rukawa.algorithm.types.advanced;

/**
 * @className: Code_A3_Near2Power
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/31 0031 19:29
 **/
public class Code_A3_Near2Power {
    /**
     * 给定一个非负整数num，
     * 如何不用循环语句
     * 返回>=num,并且离num最近的，2的某次方
     */
    public static int tableSizeFor(int num) {
        num--;
        num |= num >>> 1;
        num |= num >>> 2;
        num |= num >>> 4;
        num |= num >>> 8;
        num |= num >>> 16;
        // 负数和0的值 是1
        return (num < 0) ? 1 : num + 1;
    }

    public static void main(String[] args) {
        int num = 128;
        System.out.println(tableSizeFor(num));
    }
}
