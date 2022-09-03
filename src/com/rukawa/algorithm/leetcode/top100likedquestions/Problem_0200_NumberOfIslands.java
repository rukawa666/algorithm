package com.rukawa.algorithm.leetcode.top100likedquestions;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 11:22
 * @Version：1.0
 */
public class Problem_0200_NumberOfIslands {
    /**
     * 岛屿数量
     * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
     * 岛屿总是被水包围，并且每座岛屿只能由水平方向或竖直方向上相邻的陆地连接形成。
     * 此外，你可以假设该网格的四条边均被水包围。
     *
     * 示例 1:
     * 输入:
     * [
     * ['1','1','1','1','0'],
     * ['1','1','0','1','0'],
     * ['1','1','0','0','0'],
     * ['0','0','0','0','0']
     * ]
     * 输出: 1
     *
     * 示例 2:
     * 输入:
     * [
     * ['1','1','0','0','0'],
     * ['1','1','0','0','0'],
     * ['0','0','1','0','0'],
     * ['0','0','0','1','1']
     * ]
     * 输出: 3
     * 解释: 每座岛屿只能由水平和/或竖直方向上相邻的陆地连接而成。
     */

    public static int numIslands(char[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        UnionFind2 uf = new UnionFind2(grid);
        for (int j = 1; j < col; j++) {
            if (grid[0][j - 1] == '1' && grid[0][j] == '1') {
                uf.union(0, j - 1, 0, j);
            }
        }

        for (int i = 1; i < row; i++) {
            if (grid[i - 1][0] == '1' && grid[i][0] == '1') {
                uf.union(i - 1, 0, i, 0);
            }
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (grid[i][j] == '1') {
                    if (grid[i - 1][j] == '1') {
                        uf.union(i, j, i - 1, j);
                    }

                    if (grid[i][j - 1] == '1') {
                        uf.union(i, j, i, j - 1);
                    }
                }
            }
        }
        return uf.sets();
    }

    /**
     * 因为是二位数组，用数组表示下标，可以用row*col个长度的一维数组记录值
     * 一维数组中的位置可以用二维数组的下标表示：i * col + j
     */
    public static class UnionFind2 {
        private int[] parents;
        private int[] size;
        private int[] help;
        private int col;
        private int sets;

        public UnionFind2(char[][] grid) {
            col = grid[0].length;
            int row = grid.length;
            int len = row * col;
            sets = 0;
            parents = new int[len];
            size = new int[len];
            help = new int[len];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (grid[i][j] == '1') {
                        int index = index(i, j);
                        parents[index] = index;
                        size[index] = 1;
                        sets++;
                    }
                }
            }
        }

        // 不是原始下标
        // index = index(i, j)
        private int findFather(int index) {
            int fatherIndex = 0;
            while (index != parents[index]) {
                help[fatherIndex++] = index;
                index = parents[index];
            }

            for (fatherIndex--; fatherIndex >=0; fatherIndex--) {
                parents[help[fatherIndex]] = index;
            }
            return index;
        }

        public void union(int ri, int ci, int rj, int cj) {
            int i = index(ri, ci);
            int j = index(rj, cj);
            int fi = findFather(i);
            int fj = findFather(j);
            if (fi != fj) {
                if (size[fi] >= size[fj]) {
                    parents[fj] = fi;
                    size[fi] += size[fj];
                    size[fj] = 0;
                } else {
                    parents[fi] = fj;
                    size[fj] += size[fi];
                    size[fi] = 0;
                }
                sets--;
            }
        }

        public int sets() {
            return sets;
        }

        private int index(int i, int j) {
            return i * col + j;
        }
    }


    // 普通并查集  用dot对象表示一个'1'
    public static int numIslands2(char[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        Dot[][] dots = new Dot[row][col];
        List<Dot> dotList = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == '1') {
                    dots[i][j] = new Dot();
                    dotList.add(dots[i][j]);
                }
            }
        }
        UnionFind unionFind = new UnionFind(dotList);
        for (int j = 1; j < col; j++) {
            if (grid[0][j - 1] == '1' && grid[0][j] == '1') {
                unionFind.union(dots[0][j - 1], dots[0][j]);
            }
        }

        for (int i = 1; i < row; i++) {
            if (grid[i - 1][0] == '1' && grid[i][0] == '1') {
                unionFind.union(dots[i - 1][0], dots[i][0]);
            }
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (grid[i][j] == '1') {
                    if (grid[i - 1][j] == '1') {
                        unionFind.union(dots[i][j], dots[i - 1][j]);
                    }

                    if (grid[i][j - 1] == '1') {
                        unionFind.union(dots[i][j], dots[i][j - 1]);
                    }
                }
            }
        }
        return unionFind.sets();
    }

    public static class UnionFind<V> {
        private HashMap<V, Node<V>> nodes;
        private HashMap<Node<V>, Node<V>> parents;
        private HashMap<Node<V>, Integer> sizeMap;

        public UnionFind(List<V> lists) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V val : lists) {
                Node<V> node = new Node<>(val);
                nodes.put(val, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        private Node<V> findFather(Node node) {
            Stack<Node<V>> stack = new Stack<>();
            while (node != parents.get(node)) {
                stack.push(node);
                node = parents.get(node);
            }

            while (!stack.isEmpty()) {
                parents.put(stack.pop(), node);
            }
            return node;
        }

        public void union(V a, V b) {
            Node<V> fa = findFather(nodes.get(a));
            Node<V> fb = findFather(nodes.get(b));
            if (fa != fb) {
                int aFatherSize = sizeMap.get(fa);
                int bFatherSize = sizeMap.get(fb);
                Node<V> big = aFatherSize >= bFatherSize ? fa : fb;
                Node<V> small = big == fa ? fb : fa;
                parents.put(small, big);
                sizeMap.put(big, aFatherSize + bFatherSize);
                sizeMap.remove(small);
            }
        }

        public int sets() {
            return sizeMap.size();
        }
    }

    public static class Dot {

    }

    public static class Node<V> {
        V val;

        public Node(V v) {
            this.val = v;
        }
    }

    // O(M*N)
    public static int numIslands3(char[][] grid) {
        int lands = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    lands++;
                    inject(grid, i, j);
                }
            }
        }
        return lands;
    }

    // 从(i,j)位置出发，把所有连成一片的'1'字符全部变成'2'
    public static void inject(char[][] grid, int i, int j) {
        if (i < 0 || i == grid.length || j < 0 || j == grid[0].length || grid[i][j] != '1') {
            return;
        }
        grid[i][j] = 2;
        inject(grid, i + 1, j);
        inject(grid, i - 1, j);
        inject(grid, i, j + 1);
        inject(grid, i, j - 1);
    }
}
