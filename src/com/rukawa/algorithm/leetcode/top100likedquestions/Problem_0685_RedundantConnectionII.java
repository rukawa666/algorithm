package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * create by hqh on 2023/1/12
 */
public class Problem_0685_RedundantConnectionII {
    /**
     * 冗余连接 II
     *
     * 输入一个有向图，该图由一个有着 n 个节点（节点值不重复，从 1 到 n）的树及一条附加的有向边构成。附加的边包含在 1 到 n 中的两个不同顶点间，
     * 这条附加的边不属于树中已存在的边。
     * 结果图是一个以边组成的二维数组 edges 。 每个元素是一对 [ui, vi]，用以表示 有向 图中连接顶点 ui 和顶点 vi 的边，其中 ui 是 vi 的一个父节点。
     * 返回一条能删除的边，使得剩下的图是有 n 个节点的有根树。若有多个答案，返回最后出现在给定二维数组的答案。
     *
     * 示例 1：
     * 输入：edges = [[1,2],[1,3],[2,3]]
     * 输出：[2,3]
     *
     * 示例 2：
     * 输入：edges = [[1,2],[2,3],[3,4],[4,1],[1,5]]
     * 输出：[4,1]
     *
     * 提示：
     * n == edges.length
     * 3 <= n <= 1000
     * edges[i].length == 2
     * 1 <= ui, vi <= n
     */
    // 边的数量 = 点的数量！
    public static int[] findRedundantDirectedConnection(int[][] edges) {
        // N是点的数量
        // 点的编号，1~N，没有0
        int N = edges.length;
        // 并查集！N个点，去初始化，每个点各自是一个集合
        UnionFind uf = new UnionFind(N);
        // pre[i] = 0 来到i节点是第一次
        // pre[i] = 6 之前来过i，是从6来的！
        int[] pre = new int[N + 1];

        // 如果，没有入度为2的点，
        // first second 都维持是null
        // 如果，有入度为2的点，那么也只可能有一个
        // 比如入度为2的点，是5
        // first = [3,5]
        // second = [12,5]
        int[] first = null;
        int[] second = null;
        // 有没有环！非常不单纯！含义复杂！
        int[] circle = null;
        for (int i = 0; i < N; i++) { // 遍历每条边！
            int from = edges[i][0];
            int to = edges[i][1];
            if (pre[to] != 0) { // 不止一次来过to！
                first = new int[] { pre[to], to };
                second = edges[i];
            } else { // 第一次到达to，
                pre[to] = from;
                if (uf.same(from, to)) {
                    circle = edges[i];
                } else {
                    uf.union(from, to);
                }
            }
        }
        // 重点解析！这是啥？？？
        // first != null
        // 有入度为2的点！
        return first != null ? (circle != null ? first : second) : circle;
    }

    public static class UnionFind {
        private int[] f;
        private int[] s;
        private int[] h;

        public UnionFind(int N) {
            f = new int[N + 1];
            s = new int[N + 1];
            h = new int[N + 1];
            for (int i = 0; i <= N; i++) {
                f[i] = i;
                s[i] = 1;
            }
        }

        private int find(int i) {
            int hi = 0;
            while (i != f[i]) {
                h[hi++] = i;
                i = f[i];
            }
            while (hi > 0) {
                f[h[--hi]] = i;
            }
            return i;
        }

        public boolean same(int i, int j) {
            return find(i) == find(j);
        }

        public void union(int i, int j) {
            int fi = find(i);
            int fj = find(j);
            if (fi != fj) {
                if (s[fi] >= s[fj]) {
                    f[fj] = fi;
                    s[fi] = s[fi] + s[fj];
                } else {
                    f[fi] = fj;
                    s[fj] = s[fi] + s[fj];
                }
            }
        }

    }
}
