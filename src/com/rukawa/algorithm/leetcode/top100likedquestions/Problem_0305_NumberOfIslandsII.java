package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-28 7:44
 * @Version：1.0
 */
public class Problem_0305_NumberOfIslandsII {
    /**
     * 给你一个大小为 m x n 的二进制网格 grid 。网格表示一个地图，其中，0 表示水，1 表示陆地。最初，grid 中的所有单元格都是水单元格（即，所有单元格都是 0）。
     *
     * 可以通过执行 addLand 操作，将某个位置的水转换成陆地。给你一个数组 positions ，其中 positions[i] = [ri, ci] 是要执行第 i 次操作的位置 (ri, ci) 。
     * 返回一个整数数组 answer ，其中 answer[i] 是将单元格 (ri, ci) 转换为陆地后，地图中岛屿的数量。
     * 岛屿 的定义是被「水」包围的「陆地」，通过水平方向或者垂直方向上相邻的陆地连接而成。你可以假设地图网格的四边均被无边无际的「水」所包围。
     *  
     * 示例 1：
     * 输入：m = 3, n = 3, positions = [[0,0],[0,1],[1,2],[2,1]]
     * 输出：[1,1,2,3]
     * 解释：
     * 起初，二维网格 grid 被全部注入「水」。（0 代表「水」，1 代表「陆地」）
     * - 操作 #1：addLand(0, 0) 将 grid[0][0] 的水变为陆地。此时存在 1 个岛屿。
     * - 操作 #2：addLand(0, 1) 将 grid[0][1] 的水变为陆地。此时存在 1 个岛屿。
     * - 操作 #3：addLand(1, 2) 将 grid[1][2] 的水变为陆地。此时存在 2 个岛屿。
     * - 操作 #4：addLand(2, 1) 将 grid[2][1] 的水变为陆地。此时存在 3 个岛屿。
     *
     * 示例 2：
     * 输入：m = 1, n = 1, positions = [[0,0]]
     * 输出：[1]
     *
     * 提示：
     * 1 <= m, n, positions.length <= 104
     * 1 <= m * n <= 104
     * positions[i].length == 2
     * 0 <= ri < m
     * 0 <= ci < n
     *
     * 进阶：你可以设计一个时间复杂度 O(k log(mn)) 的算法解决此问题吗？（其中 k == positions.length）
     */
    // 并查集
    // 涉及动态初始化的技巧
    // 如果空降k个位置，时间复杂度O(k)
    // 初始化O(m*n)
    // O(m*n) + O(k)
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        List<Integer> res = new ArrayList<>();
        UnionFind uf = new UnionFind(m, n);
        for (int[] position : positions) {
            res.add(uf.connect(position[0], position[1]));
        }
        return res;
    }

    public static class UnionFind {
        private int[] parents;
        // size[i]和size[j] 合并的时候需要把小的初始化为0，这里不做处理
        // size[i] != 0 表示曾经被初始化过
        // size[i] == 0 表示曾经没有初始化
        private int[] size;
        private int[] help;
        private final int row;
        private final int col;
        private int sets;

        public UnionFind(int row, int col) {
            this.row = row;
            this.col = col;
            this.sets = 0;
            int len = row * col;
            parents = new int[len];
            size = new int[len];
            help = new int[len];
        }

        public int connect(int r, int c) {
            int index = index(r, c);
            if (size[index] == 0) { // 曾经没有初始化过
                parents[index] = index;
                size[index] = 1;
                sets++;
                union(r - 1, c, r, c);
                union(r, c - 1, r, c);
                union(r + 1, c, r, c);
                union(r, c + 1, r, c);
            }
            return sets;
        }

        private void union(int ri, int ci, int rj, int cj) {
            // 如果数组越界，则直接返回，保证数组不越界
            if (ri < 0 || ri == row || ci < 0 || ci == col || rj < 0 || rj == row || cj < 0 || cj == col) {
                return;
            }
            int i = index(ri, ci);
            int j = index(rj, cj);
            // 连通的两个位置要保证之前被初始化过，只要有一个没有初始化过，则不需要连通
            if (size[i] == 0 || size[j] == 0) {
                return;
            }
            int fi = findFather(i);
            int fj = findFather(j);
            if (fi != fj) {
                if (size[fi] >= size[fj]) {
                    parents[fj] = fi;
                    size[fi] += size[fj];
                } else {
                    parents[fi] = fj;
                    size[fj] += size[fi];
                }
                sets--;
            }
        }

        private int findFather(int index) {
            int indexFather = 0;
            while (index != parents[index]) {
                help[indexFather++] = index;
                index = parents[index];
            }

            for (indexFather--; indexFather >= 0; indexFather--) {
                parents[help[indexFather]] = index;
            }

            return index;
        }

        private int index(int r, int c) {
            return r * col + c;
        }
    }

    // 优化初始化过程
    // 规定m是100亿，n是10亿，但是k只空降了5
    // 初始化过程不合适
    public static List<Integer> numIslands22(int m, int n, int[][] positions) {
        List<Integer> res = new ArrayList<>();
        UnionFind2 uf = new UnionFind2();
        for (int[] position : positions) {
            res.add(uf.connect(position[0], position[1], m, n));
        }
        return res;
    }

    // (17,1009) 可以用字符串代替 "17_1009"
    public static class UnionFind2 {
        private HashMap<String, String> parents;
        private HashMap<String, Integer> size;
        private int sets;

        public UnionFind2() {
            parents = new HashMap<>();
            size = new HashMap<>();
            sets = 0;
        }

        public int connect(int r, int c, int m, int n) {
            String key = new StringBuilder().append(r).append("_").append(c).toString();
            if (!size.containsKey(key)) {
                parents.put(key, key);
                size.put(key, 1);
                sets++;

                if (r - 1 >= 0 && r - 1 < m) {
                    String keyUp = new StringBuilder().append(r - 1).append("_").append(c).toString();
                    union(keyUp, key);
                }
                if (r + 1 >= 0 && r + 1 < m) {
                    String keyDown = new StringBuilder().append(r + 1).append("_").append(c).toString();
                    union(keyDown, key);
                }
                if (c - 1 >= 0 && c - 1 < n) {
                    String keyLeft = new StringBuilder().append(r).append("_").append(c - 1).toString();
                    union(keyLeft, key);
                }
                if (c + 1 >= 0 && c + 1 < n) {
                    String keyRight = new StringBuilder().append(r).append("_").append(c + 1).toString();
                    union(keyRight, key);
                }
            }
            return sets;
        }

        private void union(String key1, String key2) {
            if (!parents.containsKey(key1) || !parents.containsKey(key2)) {
                return;
            }
            String f1 = findFather(key1);
            String f2 = findFather(key2);
            if (!f1.equals(f2)) {
                int f1Size = size.get(f1);
                int f2Size = size.get(f2);
                String big = f1Size >= f2Size ? f1 : f2;
                String small = big.equals(f1) ? f2 : f1;
                parents.put(small, big);
                size.put(big, f1Size + f2Size);
                size.remove(small);
                sets--;
            }
        }

        private String findFather(String key) {
            Stack<String> stack = new Stack<>();
            while (!key.equals(parents.get(key))) {
                stack.push(key);
                key = parents.get(key);
            }

            while (!stack.isEmpty()) {
                parents.put(stack.pop(), key);
            }
            return key;
        }
    }
}
