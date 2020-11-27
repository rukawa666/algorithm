package com.rukawa.algorithm.trainingcamp.trainingcamp3.class7;

import java.util.Arrays;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-06 9:15
 * @Version：1.0
 */
public class Code03_MinPatches {
    /**
     * 给定一个有序数的正数数组arr和一个正数aim，如果可以自由选择arr中的数字，想累加得到1~aim范围上
     * 所有的数，返回arr最少还缺几个数
     * 举例：
     *  arr={1,2,3,7}, aim=15
     *  想累加得到1~15范围上所有的数，arr还缺14这个数，所以返回1
     *  arr={1,5,7}， aim=15
     *  想累加得到1~15范围上所有的数，arr还缺2和4，所以返回2
     */

    /**
     * 思路：
     *  从1开始，想得到1~100上面所有的数，看缺少哪些数
     *  1、得到2，需要2这个数
     *  2、然后得到1~3上面所有的数
     *  3、此时需要得到4
     *  4、能得到1~7范围所有的数，(1~3) + 4 -> (1~7)
     *  5、此时需要得到8
     *  6、能得到1~15范围所有的数，(1~7) + 8 -> (1~15)
     *  7、依次类推，最后一个缺少的是64，之前能得到的所有数范围是1~63
     *
     *  结论：已经实现得到了1~range上所有的目标，但是目标还未达到1~aim，缺少的是永远都是range+1。
     *       此时的range范围是1~2range+1
     */

    public static int minPatches(int[] arr, int aim) {
        int patches = 0;    // 缺少多少个数字
        long range = 0; // 已经完成了1~range的目标
        Arrays.sort(arr);
        for (int i = 0; i < arr.length; i++) {
            // 1~range
            // 实现1~arr[i]-1的目标
            while (arr[i] - 1 > range) {    // 此时目标没有达成
                range += range + 1;  // range + 1 是缺少的数字
                patches++;
                if (range >= aim) {
                    return patches;
                }
            }
            // 此时1~range的目标已经达成
            range += arr[i];
            if (range >= aim) {
                return patches;
            }
        }
        // 此时遍历完成之后，还未达成目标
        while (aim - 1 >= range) {
            range += range + 1;
            patches++;
        }
        return patches;
    }

    public static void main(String[] args) {
        int[] test = { 1, 2, 31, 33 };
        int n = 2147483647;
        System.out.println(minPatches(test, n));

    }
}
