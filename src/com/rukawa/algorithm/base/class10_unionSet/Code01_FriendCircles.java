package com.rukawa.algorithm.base.class10_unionSet;

/**
 * create by hqh on 2022/9/3
 */
public class Code01_FriendCircles {
    /**
     * 本题为LeetCode原题547
     * 链接地址：https://leetcode.cn/problems/number-of-provinces/
     */

    public int findCircleNum(int[][] isConnected) {
        int N = isConnected.length;
        UnionFind unionFind = new UnionFind(N);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (isConnected[i][j] == 1) {
                    unionFind.union(i, j);
                }
            }
        }
        return unionFind.sets;
    }

    public static class UnionFind {
        // parents[i] = k 说明i的父亲节点是k
        private int[] parents;
        // size[i] = k 如果i是代表节点才有意义，否则无意义
        // i所在的集合大小是多少
        private int[] size;
        // 辅助结构
        private int[] help;
        // 一共有多少个集合
        private int sets;

        public UnionFind(int N) {
            parents = new int[N];
            size = new int[N];
            help = new int[N];
            sets = N;
            for (int i = 0; i < N; i++) {
                parents[i] = i;
                size[i] = 1;
            }
        }

        // 从index一直往上，往上到不能再往上，代表节点，返回
        // 这个过程要路径压缩
        private int findFather(int index) {
            int fatherIndex = 0;
            while (index != parents[index]) {
                help[fatherIndex++] = index;
                index = parents[index];
            }
            // 此时fatherIndex多出一个位置，所以先--，再循环
            for (fatherIndex--; fatherIndex >= 0; fatherIndex--) {
                parents[help[fatherIndex]] = index;
            }

            return index;
        }

        public void union(int i, int j) {
            int fi = findFather(i);
            int fj = findFather(j);
            if (fi != fj) {
                if (size[fi] >= size[fj]) {
                    size[fi] += size[fj];
                    parents[fj] = fi;
                } else {
                    size[fj] += size[fi];
                    parents[fi] = fj;
                }
                sets--;
            }
        }

        public int sets() {
            return sets;
        }
    }
}
