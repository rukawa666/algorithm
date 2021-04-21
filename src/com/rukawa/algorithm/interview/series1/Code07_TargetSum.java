package com.rukawa.algorithm.interview.series1;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/4/20 0020 20:50
 */
public class Code07_TargetSum {
    /**
     * 给定一个数组arr，你可以在每个数字之前决定+或者-
     * 但是必须所有数字都参与
     * 在给定一个数target，请问最后算出target的方法数是多少个？
     */

    public static int findTargetSumWays1(int[] arr, int s) {
        return process(arr, 0, s);
    }

    // 可以自由使用arr[index...]所有的数字
    // 搞出rest这个数，方法数是多少？
    public static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        }
        // rest + arr[index] -> 减去这个数
        // rest - arr[index] -> 加上这个数
        return process(arr, index + 1, rest + arr[index]) + process(arr, index + 1, rest - arr[index]);
    }
}
