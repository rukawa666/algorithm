package com.rukawa.algorithm.base.class10_unionSet;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * create by hqh on 2022/9/3
 */
public class Code02_NumberOfIsLands {
    /**
     * Leetcode原题200
     * https://leetcode.cn/problems/number-of-islands/
     */

    /**
     * int[一亿][一亿]
     * 建议并查集
     * public static class Position {
     *     public int row;
     *     public int col;
     * }
     *
     * 申请 Position[一亿][一亿]
     * 假设(4,5)的父亲是(13,9)，设置position
     * position[4][5] = new Position(13, 9)
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

    // 为了测试
    public static char[][] generateRandomMatrix(int row, int col) {
        char[][] board = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = Math.random() < 0.5 ? '1' : '0';
            }
        }
        return board;
    }

    // 为了测试
    public static char[][] copy(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        char[][] ans = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                ans[i][j] = board[i][j];
            }
        }
        return ans;
    }

    // 为了测试
    public static void main(String[] args) {
        int row = 0;
        int col = 0;
        char[][] board1 = null;
        char[][] board2 = null;
        char[][] board3 = null;
        long start = 0;
        long end = 0;

        row = 1000;
        col = 1000;
        board1 = generateRandomMatrix(row, col);
        board2 = copy(board1);
        board3 = copy(board1);

        System.out.println("感染方法、并查集(map实现)、并查集(数组实现)的运行结果和运行时间");
        System.out.println("随机生成的二维矩阵规模 : " + row + " * " + col);

        start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果: " + numIslands3(board1));
        end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(map实现)的运行结果: " + numIslands2(board2));
        end = System.currentTimeMillis();
        System.out.println("并查集(map实现)的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行结果: " + numIslands(board3));
        end = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行时间: " + (end - start) + " ms");

        System.out.println();

        row = 10000;
        col = 10000;
        board1 = generateRandomMatrix(row, col);
        board3 = copy(board1);
        System.out.println("感染方法、并查集(数组实现)的运行结果和运行时间");
        System.out.println("随机生成的二维矩阵规模 : " + row + " * " + col);

        start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果: " + numIslands3(board1));
        end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(数组你实现)的运行结果: " + numIslands(board3));
        end = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行时间: " + (end - start) + " ms");

    }
}
