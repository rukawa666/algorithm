package com.rukawa.algorithm.base.class10_unionSet;

/**
 * create by hqh on 2022/9/4
 */
public class Code04_NumberOfIsLandsIII {
    /**
     * 给定一个二维数组matrix，里面的值不是0就是1
     * 上、下、左、右相邻的1认为是一片岛
     * 返回matrix中岛屿的数量
     *
     * 如果matrix极大，涉及一种可行的并行计算方案
     */

    /**
     * 思路：可以把matrix切分成两块，假设用感染的方法获取岛的数量
     * 左右两边的岛屿之间的边界信息，感染为2的节点记录是属于哪个岛的
     *
     * 最终合并左边两边的时候，如果相邻两个节点可以连通，岛屿数量-1，合并两个岛屿，依次类推
     */
}
