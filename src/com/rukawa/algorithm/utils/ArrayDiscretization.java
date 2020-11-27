package com.rukawa.algorithm.utils;

import java.util.HashMap;
import java.util.TreeSet;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-11-27 9:23
 * @Version：1.0
 */
public class ArrayDiscretization {

    // 做范围的离散化，因为落下一个正方形有可能位置出现的值很大
    // 离散化：所有的块左右两个边界都列一下有哪些值，从小到大排个序，最小的是1下标，然后依次
    // positions
    // [2,7] -> 2,8
    // [3,10] -> 3,12
    public HashMap<Integer, Integer> index(int[][] positions) {
        TreeSet<Integer> pos = new TreeSet<>();
        for (int[] arr : positions) {
            // 左边界
            pos.add(arr[0]);
            // 右边界
            // 为什么右边界-1？
            // 比如[3,2] 从x在位置，往右延伸到5
            // 但是[5,3] 在x等于5的位置要落下，但是此时5位置已经被占用所以不能落到底，实际上是可以的
            // 所以规定[3,2] 从x在3位置开始，能覆盖到4
            pos.add(arr[0] + arr[1] - 1);
        }

        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        // 从小到大遍历，第一个位置的index从1开始，后续++
        for (Integer index : pos) {
            map.put(index, ++count);
        }
        return map;
    }
}
