package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-21 8:39
 * @Version：1.0
 */
public class Problem_0118_PascalTriangle {
    /**
     * 杨辉三角
     * 给定一个非负整数 numRows，生成「杨辉三角」的前 numRows 行。
     * 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
     *
     * 示例 1:
     * 输入: numRows = 5
     * 输出: [[1]
     *       [1,1],
     *       [1,2,1],
     *       [1,3,3,1],
     *       [1,4,6,4,1]]
     *
     * 示例 2:
     * 输入: numRows = 1
     * 输出: [[1]]
     *
     * 提示:
     * 1 <= numRows <= 30
     */
    public List<List<Integer>> generate(int numRows) {
        /**
         * 根据观察得到：下一行的值，除了第一个位置是1，最后一个位置是1，其余位置是上面行的左上角的值+上面的值
         * 1
         * 1 1
         * 1 2 1
         * 1 3 3  1
         * 1 4 6  4 1
         * 1 5 10 10 5 1
         */
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            res.add(new ArrayList<>());
            res.get(i).add(1);
        }

        for (int i = 1; i < numRows; i++) {
            // 先填写1～n-2的位置
            for (int j = 1; j < i; j++) {
                res.get(i).add(res.get(i - 1).get(j - 1) + res.get(i - 1).get(j));
            }
            res.get(i).add(1);
        }
        return res;
    }
}
