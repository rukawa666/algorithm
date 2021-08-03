package com.rukawa.algorithm.types.stateCompression;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: Code02_TSP
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/8/2 0002 23:22
 **/
public class Code02_TSP {
    /**
     * TSP问题
     * 有N个城市，任何两个城市之间都有距离，任何一座城市到自己的距离都为0。所有点到点的距离都存在一个N*N的二维矩阵数组matrix里，
     * 也就是整张图有邻接矩阵表示。现要求一旅行商从k城市出发必须经过每一个城市且只在一个城市逗留一次,最后回到出发的k城，返回总距离
     * 最短的路的距离。
     * 参数给定一个matrix，给定k。
     */

    // 暴力方法
    public static int f1(int[][] matrix) {
        // 城市数量
        int N = matrix.length;
        // set
        // set.get(i) != null i这座城市在集合中
        List<Integer> set = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            set.add(1);
        }
        return func1(matrix, set, 0);
    }

    // 潜台词：start必然在set中存在
    // 从start，但是要把整个set串一遍，最后返回一个整型，最后的城市要接到目标
    public static int func1(int[][] matrix, List<Integer> set, int start) {

    }

}
