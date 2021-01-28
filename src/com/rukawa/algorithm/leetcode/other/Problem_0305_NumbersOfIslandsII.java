package com.rukawa.algorithm.leetcode.other;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-29 0:10
 * @Version：1.0
 */
public class Problem_0305_NumbersOfIslandsII {
    /**
     * 假设你设计一个游戏，用一个 m 行 n 列的 2D 网格来存储你的游戏地图。
     * 起始的时候，每个格子的地形都被默认标记为「水」。
     * 我们可以通过使用 addLand 进行操作，将位置 (row, col) 的「水」变成「陆地」。
     * 你将会被给定一个列表，来记录所有需要被操作的位置，然后你需要返回计算出来 每次 addLand 操作后岛屿的数量。
     * 注意：一个岛的定义是被「水」包围的「陆地」，通过水平方向或者垂直方向上相邻的陆地连接而成。
     * 你可以假设地图网格的四边均被无边无际的「水」所包围。
     * 请仔细阅读下方示例与解析，更加深入了解岛屿的判定。
     *
     * 示例:
     * 输入: m = 3, n = 3,
     *  positions = [[0,0], [0,1], [1,2], [2,1]]
     * 输出: [1,1,2,3]
     * 解析:
     * 起初，二维网格 grid 被全部注入「水」。（0 代表「水」，1 代表「陆地」）
     * 0 0 0
     * 0 0 0
     * 0 0 0
     *
     * 操作 #1：addLand(0, 0) 将 grid[0][0] 的水变为陆地。
     * 1 0 0
     * 0 0 0   Number of islands = 1
     * 0 0 0
     *
     * 操作 #2：addLand(0, 1) 将 grid[0][1] 的水变为陆地。
     * 1 1 0
     * 0 0 0   岛屿的数量为 1
     * 0 0 0
     *
     * 操作 #3：addLand(1, 2) 将 grid[1][2] 的水变为陆地。
     * 1 1 0
     * 0 0 1   岛屿的数量为 2
     * 0 0 0
     *
     * 操作 #4：addLand(2, 1) 将 grid[2][1] 的水变为陆地。
     * 1 1 0
     * 0 0 1   岛屿的数量为 3
     * 0 1 0
     *
     * 拓展：
     * 你是否能在 O(k log mn) 的时间复杂度程度内完成每次的计算？
     * （k 表示 positions 的长度）
     */

    // O(k) + O(m * n)
    public static List<Integer> numIslands(int m, int n, int[][] positions) {
        UnionFind uf = new UnionFind(m, n);
        List<Integer> res = new ArrayList<>();
        for (int[] position : positions) {
            res.add(uf.connect(position[0], position[1]));
        }
        return res;
    }

    public static class UnionFind {
        private int[] parent;
        private int[] size;
        private int[] help;
        private final int row;
        private final int col;
        private int sets;

        public UnionFind(int m, int n) {
            row = m;
            col = n;
            sets = 0;
            int len = row * col;
            parent = new int[len];
            size = new int[len];
            help = new int[len];
        }

        public int index(int r, int c) {
            return r * col + c;
        }

        public int find(int i) {
            int hi = 0;
            while (i != parent[i]) {
                help[hi++] = i;
                i = parent[i];
            }
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = i;
            }
            return i;
        }

        public void union(int r1, int c1, int r2, int c2) {
            // 越界判断
            if (r1 < 0 || r1 == row || r2 < 0 || r2 == row || c1 < 0 || c1 == col || c2 < 0 || c2 == col) {
                return;
            }
            int i1 = index(r1, c1);
            int i2 = index(r2, c2);
            // 之前
            if (size[i1] == 0 || size[i2] == 0) {
                return;
            }
            int f1 = find(i1);
            int f2 = find(i2);
            if (f1 != f2) {
                if (size[f1] >= size[f2]) {
                    size[f1] += size[f2];
                    parent[f2] = f1;
                } else {
                    size[f2] += size[f1];
                    parent[f1] = f2;
                }
            }
            sets--;
        }
        // 动态连接
        public int connect(int r, int c) {
            int index = index(r, c);
            // 之前的1没有出现过
            if (size[index] == 0) {
                parent[index] = index;
                size[index] = 1;
                sets++;
                union(r - 1, c, r, c);
                union(r + 1, c, r, c);
                union(r, c - 1, r, c);
                union(r, c + 1, r, c);
            }
            return sets;
        }
    }
}
