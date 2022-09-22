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
        /**
         * 思路：
         *  把最外面一层全部放到小根堆，然后定义一个max=0，每次弹出一个数看看能不能把max推高，max就是相当于一个瓶颈
         */
        if (heightMap == null || heightMap.length == 0 || heightMap[0] == null || heightMap[0].length == 0) {
            return 0;
        }
        int n = heightMap.length;
        int m = heightMap[0].length;
        boolean[][] isEnter = new boolean[n][m];
        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(x -> x.value));
        // 把四个边加进去
        for (int col = 0; col < m - 1; col++) {
            isEnter[0][col] = true;
            minHeap.add(new Node(0, col, heightMap[0][col]));
        }

        for (int row = 0; row < n - 1; row++) {
            isEnter[row][m - 1] = true;
            minHeap.add(new Node(row, m - 1, heightMap[row][m - 1]));
        }

        for (int col = m - 1; col > 0; col--) {
            isEnter[n - 1][col] = true;
            minHeap.add(new Node(n - 1, col, heightMap[n - 1][col]));
        }

        for (int row = n - 1; row > 0; row--) {
            isEnter[row][0] = true;
            minHeap.add(new Node(row, 0, heightMap[row][0]));
        }

        int water = 0;
        int max = 0;
        while (!minHeap.isEmpty()) {
            Node cur = minHeap.poll();
            max = Math.max(max, cur.value);
            int r = cur.row;
            int c = cur.col;
            // 把上下左右的位置放入小根堆，然后放入的时候结算水量
            if (r > 0 && !isEnter[r - 1][c]) {
                water += Math.max(0, max - heightMap[r - 1][c]);
                isEnter[r - 1][c] = true;
                minHeap.add(new Node(r - 1, c, heightMap[r - 1][c]));
            }

            if (r < n - 1 && !isEnter[r + 1][c]) {
                water += Math.max(0, max - heightMap[r + 1][c]);
                isEnter[r + 1][c] = true;
                minHeap.add(new Node(r + 1, c, heightMap[r + 1][c]));
            }

            if (c > 0 && !isEnter[r][c - 1]) {
                water += Math.max(0, max - heightMap[r][c - 1]);
                isEnter[r][c - 1] = true;
                minHeap.add(new Node(r, c - 1, heightMap[r][c - 1]));
            }

            if (c < m - 1 && !isEnter[r][c + 1]) {
                water += Math.max(0, max - heightMap[r][c + 1]);
                isEnter[r][c + 1] = true;
                minHeap.add(new Node(r, c + 1, heightMap[r][c + 1]));
            }
        }
        return water;
    }

    public static class Node {
        public int row;
        public int col;
        public int value;

        public Node(int row, int col, int value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }
    }
}
