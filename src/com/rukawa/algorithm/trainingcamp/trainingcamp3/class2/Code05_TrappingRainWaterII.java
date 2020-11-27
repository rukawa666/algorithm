package com.rukawa.algorithm.trainingcamp.trainingcamp3.class2;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-22 9:04
 * @Version：1.0
 */
public class Code05_TrappingRainWaterII {

    /**
     * 如果给你一个二维数组，每一个值表示这一块地形的高度，
     * 求整块地形能装下多少的水
     */

    /**
     * 如果小根堆的平均大小估计为(N * M)
     * 则时间复杂度：O(N * M * log(N * M))
     * @param heightMap
     * @return
     */
    public static int trapRainWater(int[][] heightMap) {
        if (heightMap == null || heightMap.length == 0 || heightMap[0] == null || heightMap[0].length == 0) {
            return 0;
        }

        int N = heightMap.length;
        int M = heightMap[0].length;
        // isEnter[i][j] == true  之前进过
        //  isEnter[i][j] == false 之前没进过
        boolean[][] isEnter = new boolean[N][M];
        PriorityQueue<Node> heap = new PriorityQueue<>(new NodeComparator());
        for (int col = 0; col < M - 1; col++) {
            isEnter[0][col] = true;
            heap.add(new Node(heightMap[0][col], 0, col));
        }

        for (int row = 0; row < N - 1; row++) {
            isEnter[row][M - 1] = true;
            heap.add(new Node(heightMap[row][M - 1], row, M - 1));
        }

        for (int col = M - 1; col > 0; col--) {
            isEnter[N - 1][col] = true;
            heap.add(new Node(heightMap[N - 1][col], N - 1, col));
        }

        for (int row = N - 1; row > 0; row--) {
            isEnter[row][0] = true;
            heap.add(new Node(heightMap[row][0], row, 0));
        }

        int water = 0;  // 每个位置的水量，累加到water上去
        int max = 0;    // 每个node在弹出的时候，如果value更大，更新max，否则max的值维持不变
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
