package com.rukawa.algorithm.leetcode.other;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-28 8:03
 * @Version：1.0
 */
public class Problem_0547_NumberOfProvinces {
    /**
     *  n 个城市，其中一些彼此相连，另一些没有相连。如果城市 a 与城市 b 直接相连，且城市 b 与城市 c 直接相连，那么城市 a 与城市 c 间接相连。
     * 省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。
     * 给你一个 n x n 的矩阵 isConnected ，其中 isConnected[i][j] = 1 表示第 i 个城市和第 j 个城市直接相连，
     * 而 isConnected[i][j] = 0 表示二者不直接相连。
     * 返回矩阵中 省份 的数量。
     *
     * 示例 1：
     * 输入：isConnected = [[1,1,0],[1,1,0],[0,0,1]]
     * 输出：2
     *
     * 示例 2：
     * 输入：isConnected = [[1,0,0],[0,1,0],[0,0,1]]
     * 输出：3
     *
     * 提示：
     * 1 <= n <= 200
     * n == isConnected.length
     * n == isConnected[i].length
     * isConnected[i][j] 为 1 或 0
     * isConnected[i][i] == 1
     * isConnected[i][j] == isConnected[j][i]
     */
    public int findCircleNum(int[][] isConnected) {
        int N = isConnected.length;
        UnionFind find = new UnionFind(N);
        // 只遍历上半区。因为下半区对称，不用处理
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (isConnected[i][j] == 1) {
                    find.union(i, j);
                }
            }
        }
        return find.sets;
    }

    public static class UnionFind {
        // parents[i] = k i的父亲节点是k
        private int[] parents;
        // size[i] = k 如果i是代表节点，size[i]才有意义，否则无意义
        // i所在集合大小是多少
        private int[] size;
        // 辅助数组
        private int[] help;
        // 一共多少集合
        private int sets;

        public UnionFind(int limit) {
            parents = new int[limit];
            size = new int[limit];
            help = new int[limit];
            sets = limit;
            for (int i = 0; i < limit; i++) {
                parents[i] = i;
                size[i] = 1;
            }
        }

        // 从i开始一直往上，往上到不能往上，为代表节点，返回
        // 这个过程要路径压缩
        private int find(int i) {
            int hi = 0;
            while (i != parents[i]) {
                help[hi++] = i;
                i = parents[i];
            }
            // 沿途所有节点的父亲节点更新
            for (hi--; hi >= 0; hi--) {
                parents[help[hi]] = i;
            }
            return i;
        }

        public void union(int i, int j) {
            int fatherI = find(i);
            int fatherJ = find(j);
            if (fatherI != fatherJ) {
                if (size[fatherI] > size[fatherJ]) {
                    size[fatherI] += size[fatherJ];
                    parents[fatherJ] = fatherI;
                } else {
                    size[fatherJ] += size[fatherI];
                    parents[fatherI] = fatherJ;
                }
                sets--;
            }
        }

        public int sets() {
            return sets;
        }

    }

}
