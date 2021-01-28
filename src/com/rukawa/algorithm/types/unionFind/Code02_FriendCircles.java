package com.rukawa.algorithm.types.unionFind;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-28 8:06
 * @Version：1.0
 */
public class Code02_FriendCircles {
    /**
     * 班上有 N 名学生。其中有些人是朋友，有些则不是。他们的友谊具有是传递性。如果已知 A 是 B 的朋友，B 是 C 的朋友，
     * 那么我们可以认为 A 也是 C 的朋友。所谓的朋友圈，是指所有朋友的集合。
     * 给定一个 N * N 的矩阵 M，表示班级中学生之间的朋友关系。如果M[i][j] = 1，表示已知第 i 个和 j 个学生互为朋友关系，否则为不知道。
     * 你必须输出所有学生中的已知的朋友圈总数。
     *
     * 示例 1：
     * 输入：
     * [[1,1,0],
     *  [1,1,0],
     *  [0,0,1]]
     * 输出：2
     * 解释：已知学生 0 和学生 1 互为朋友，他们在一个朋友圈。
     * 第2个学生自己在一个朋友圈。所以返回 2 。
     *
     * 示例 2：
     * 输入：
     * [[1,1,0],
     *  [1,1,1],
     *  [0,1,1]]
     * 输出：1
     * 解释：已知学生 0 和学生 1 互为朋友，学生 1 和学生 2 互为朋友，所以学生 0 和学生 2 也是朋友，所以他们三个在一个朋友圈，返回 1 。
     *
     * 提示：
     * 1 <= N <= 200
     * M[i][i] == 1
     * M[i][j] == M[j][i]
     */
    public static int friendCircleNum(int[][] num) {
        int N = num.length;
        UnionFind find = new UnionFind(N);
        // 只遍历上半区。因为下半区对称，不用处理
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (num[i][j] == 1) {
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
            // 总共hi的长度，但是hi的下标是hi-1开始
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

    public static void main(String[] args) {
        int size = 9;
        for (size--; size >= 0; size--) {
            System.out.println(size);
        }
    }
}
