package com.rukawa.algorithm.leetcode.highfrequency;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-21 8:39
 * @Version：1.0
 */
public class Problem_0119_PascalTriangleII {
    /**
     * 杨辉三角
     * 给定一个非负索引 rowIndex，返回「杨辉三角」的第 rowIndex 行。
     * 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
     *
     * 提示:
     * 1 <= numRows <= 30
     *
     * 进阶：
     * 你可以优化你的算法到 O(rowIndex) 空间复杂度吗？
     */
    public List<Integer> getRow(int rowIndex) {
        /**
         * 根据观察得到：下一行的值，除了第一个位置是1，最后一个位置是1，其余位置是上面行的左上角的值+上面的值
         * 从右往左填
         * 1
         * 1 1
         * 1 2 1
         * 1 3 3  1
         * 1 4 6  4 1
         * 1 5 10 10 5 1
         */
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i <= rowIndex; i++) {
            for (int j = i - 1; j > 0; j--) {
                res.set(j, res.get(j - 1) + res.get(j));
            }
            res.add(1);
        }
        return res;
    }
}
