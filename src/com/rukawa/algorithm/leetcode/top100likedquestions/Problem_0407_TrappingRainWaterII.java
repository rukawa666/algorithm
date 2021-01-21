package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-24 21:27
 * @Version：1.0
 */
public class Problem_0407_TrappingRainWaterII {
    /**
     * 接雨水 II
     * 给你一个 m x n 的矩阵，其中的值均为非负整数，代表二维高度图每个单元的高度，请计算图中形状最多能接多少体积的雨水。
     * 示例：
     * 给出如下 3x6 的高度图:
     * [
     *   [1,4,3,1,3,2],
     *   [3,2,1,3,2,4],
     *   [2,3,3,2,3,1]
     * ]
     * 返回 4 。
     * 如上图所示，这是下雨前的高度图[[1,4,3,1,3,2],[3,2,1,3,2,4],[2,3,3,2,3,1]] 的状态。
     * 下雨后，雨水将会被存储在这些方块中。总的接雨水量是4。
     *
     * 提示：
     * 1 <= m, n <= 110
     * 0 <= heightMap[i][j] <= 20000
     */
    public int trapRainWater(int[][] heightMap) {
        if (heightMap == null || heightMap.length == 0
                || heightMap[0] == null || heightMap[0].length == 0) {
            return 0;
        }
        int N = heightMap.length;
        int M = heightMap[0].length;
        boolean[][] isEnter = new boolean[N][M];
        PriorityQueue<Node> heap = new PriorityQueue<>(new NodeComparator());
        for (int col = 0; col < M - 1; col++) {
            heap.add(new Node(heightMap[0][col], 0, col));
            isEnter[0][col] = true;
        }

        for (int row = 0; row < N - 1; row++) {
            heap.add(new Node(heightMap[row][M - 1], row, M - 1));
            isEnter[row][M - 1] = true;
        }

        for (int col = M - 1; col > 0; col--) {
            heap.add(new Node(heightMap[N - 1][col], N - 1, col));
            isEnter[N - 1][col] = true;
        }

        for (int row = N - 1; row > 0; row--) {
            heap.add(new Node(heightMap[row][0], row, 0));
            isEnter[row][0] = true;
        }

        int max = 0;
        int water = 0;
        while (!heap.isEmpty()) {
            Node cur = heap.poll();
            max = Math.max(max, cur.value);
            int row = cur.row;
            int col = cur.col;
            if (row > 0 && !isEnter[row - 1][col]) {
                water += Math.max(0, max - heightMap[row - 1][col]);
                isEnter[row - 1][col] = true;
                heap.add(new Node(heightMap[row - 1][col], row - 1, col));
            }

            if (col > 0 && !isEnter[row][col - 1]) {
                water += Math.max(0, max - heightMap[row][col - 1]);
                isEnter[row][col - 1] = true;
                heap.add(new Node(heightMap[row][col - 1], row, col - 1));
            }

            if (row < N - 1 && !isEnter[row + 1][col]) {
                water += Math.max(0, max - heightMap[row + 1][col]);
                isEnter[row + 1][col] = true;
                heap.add(new Node(heightMap[row + 1][col], row + 1, col));
            }

            if (col < M - 1 && !isEnter[row][col + 1]) {
                water += Math.max(0, max - heightMap[row][col + 1]);
                isEnter[row][col + 1] = true;
                heap.add(new Node(heightMap[row][col + 1], row, col + 1));
            }
        }
        return water;
    }

    public static class Node {
        public int value;
        public int row;
        public int col;

        public Node(int value, int row, int col) {
            this.value = value;
            this.row = row;
            this.col = col;
        }
    }

    public static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.value - o2.value;
        }
    }
}
