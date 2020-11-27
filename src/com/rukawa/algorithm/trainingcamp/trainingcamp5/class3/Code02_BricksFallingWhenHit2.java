package com.rukawa.algorithm.trainingcamp.trainingcamp5.class3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-11-10 19:36
 * @Version：1.0
 */
public class Code02_BricksFallingWhenHit2 {
    // 01:12:40
    public static int[] hitBricks(int[][] grid, int[][] hits) {
        /**
         * 步骤：
         *  1、一开始在matrix中把炮弹的影响给加上，如果当前位置是1，则变为2，否则不变
         *  2、把matrix里面所有的1认为单独的集合，在集合中打标是否连在天花板上，此时连在天花板上的1有2个
         *  3、(0,3)，2变为1，此时只有它自己形成一个1，共有3个1连在天花板上，但是自己要被打爆 (0,3):0
         *  4、(1,4)，2变为1，共有3个1连成一片，但是不接在天花板上，此时只有3个1连在天花板上，(1,4):0
         *  5、(1,3)，2变为1，此处5个1连在天花板上，共有7个1连在天花板上，原来有3个1连在天花板上，自己要被打爆，所以7-3-1=4 (1,3):3
         *  6、(2,0)，2变为1，此处5个1连在天花板上，共有10个1连在天花板上，原来有7个1连在天花板上，自己要被打爆，所以10-7-1=2 (2,0):2
         */
        for (int i = 0; i < hits.length; i++) {
            if (grid[hits[i][0]][hits[i][1]] == 1) {
                grid[hits[i][0]][hits[i][1]] = 2;
            }
        }

        UnionFind unionFind = new UnionFind(grid);
        int[] res = new int[hits.length];
        for (int i = hits.length - 1; i >= 0; i--) {
            if (grid[hits[i][0]][hits[i][1]] == 2) {
                res[i] = unionFind.finger(hits[i][0], hits[i][1]);
            }
        }
        return res;
    }
    // 每个1，都有一个专属于自己的Dot
    // (5,1)=1, (7,2)=1, 怎么区分这两个1不同，用内存地址表示不同
    public static class Dot {

    }

    public static class UnionFind {
        // 原始数组，经过炮弹的影响之后，所得到的grid
        private int[][] grid;  // 主函数处理后的原始矩阵
        // 如果原始矩阵grid[i][j]=1, dot[i][j]=new的点
        private Dot[][] dots; // 位置到点的对应关系
        private int N; // 行数
        private int M; // 列数
        private int cellingAll; // 有多少个1能连接上天花板上
        // 某个集合，是不是整体连接到天花板上了，如果是，假设这个集合代表点是x，cellingSet包含x
        // 如果不是，假设这个集合代表点是x，cellingSet不包含x
        private HashSet<Dot> cellingSet; // 集合能够连到天花板，它的代表点才在里面
        private HashMap<Dot, Dot> fatherMap;    // 任何一个dot都有记录，value就是父节点
        private HashMap<Dot, Integer> sizeMap;  // 只有一个dot是代表点，才有记录，value表示这个集合的大小

        // matrix，炮弹会让某些位置的1变为2,2和0一样表示不连通
        public UnionFind(int[][] matrix) {
            initSpace(matrix);
            initConnect();
        }

        public void initSpace(int[][] matrix) {
            grid = matrix;
            N = grid.length;
            M = grid[0].length;
            // 连接到天花板上砖的数量
            cellingAll = 0;
            dots = new Dot[N][M]; // dot null
            cellingSet = new HashSet<>();
            fatherMap = new HashMap<>();
            sizeMap = new HashMap<>();
            // 找到集合中所有的1，每个1独立成一个集合
            for (int row = 0; row < N; row++) {
                for (int col = 0; col < M; col++) {
                    // 遍历每一个[i][j] 2,0直接跳过
                    if (grid[row][col] == 1) {
                        Dot cur = new Dot();
                        dots[row][col] = cur;
                        fatherMap.put(cur, cur);
                        sizeMap.put(cur, 1);
                        if (row == 0) { // dot是天花板上的点
                            cellingSet.add(cur);
                            cellingAll++;
                        }
                    }
                }
            }
        }

        // 初始连接,每一个点和上、下、左、右去连接
        public void initConnect() {
            for (int row = 0; row < N; row++) {
                for (int col = 0; col < M; col++) {
                    union(row, col, row - 1, col);
                    union(row, col, row + 1, col);
                    union(row, col, row, col - 1);
                    union(row, col, row, col + 1);
                }
            }
        }

        // row,col 的dot，所在的集合，代表dot是谁返回
        private Dot find(int row, int col) {
            Dot cur = dots[row][col];
            Stack<Dot> stack = new Stack<>();
            while (cur != fatherMap.get(cur)) {
                stack.add(cur);
                cur = fatherMap.get(cur);
            }

            while (!stack.isEmpty()) {
                fatherMap.put(stack.pop(), cur);
            }

            return cur;
        }

        // (row1, col1) 和(row2, col2)背后的东西合并, 都是1才合并
        private void union(int row1, int col1, int row2, int col2) {
            if (isValid(row1, col1) && isValid(row2, col2)) {
                Dot father1 = find(row1, col1);
                Dot father2 = find(row2, col2);
                if (father1 != father2) {
                    int size1 = sizeMap.get(father1);
                    int size2 = sizeMap.get(father2);

                    Dot big = size1 >= size2 ? father1 : father2;
                    Dot small = big == father1 ? father2 : father1;
                    fatherMap.put(small, big);
                    sizeMap.put(big, size1 + size2);

                    // 集合1整体连不连得到天花板
                    boolean status1 = cellingSet.contains(father1);
                    // 集合2整体连不连得到天花板
                    boolean status2 = cellingSet.contains(father2);
                    // 如果两个集合能够连接到天花板的状态是不一样的
                    if (status1 ^ status2) {
                        cellingSet.add(big);
                        cellingAll += status1 ? size2 : size1;
                    }
//                    if (size1 <= size2) {
//                        // 集合1和集合2，已经合并，共同的父亲节点father2
//                        fatherMap.put(father1, father2);
//                        sizeMap.put(father2, size1 + size2);
//                        // 如果两个集合能够连接到天花板的状态是不一样的
//                        if (status1 ^ status2) {
//                            cellingSet.add(father2);
//                            cellingAll += status1 ? size2 : size1;
//                        }
//                    } else {
//                        fatherMap.put(father2, father1);
//                        sizeMap.put(father1, size1 + size2);
//                        if (status1 ^ status2) {
//                            cellingSet.add(father1);
//                            cellingAll += status1 ? size2 : size1;
//                        }
//                    }
                }
            }
        }
        // 有效性检查
        private boolean isValid(int row, int col) {
            return row >= 0 && row < N && col >= 0 && col < M && grid[row][col] == 1;
        }

        public int cellingNum() {
            return cellingAll;
        }

        // 原来row， col 2 finger 1
        // 在该位置变成1的情况下，并查集该如何变化，接到天花板上的1改如何变化
        public int finger(int row, int col) {
            int pre = cellingAll;
            grid[row][col] = 1;
            Dot cur = new Dot();
            dots[row][col] = cur;
            if (row == 0) {
                cellingSet.add(cur);
                cellingAll++;
            }
            fatherMap.put(cur, cur);
            sizeMap.put(cur, 1);
            // 上、下、左、右的1相连
            union(row, col, row - 1, col);
            union(row, col, row + 1, col);
            union(row, col, row, col - 1);
            union(row, col, row, col + 1);
            int now = cellingAll;
            return now == pre ? 0 : now - pre - 1;
        }
    }

    public static void main(String[] args) {
        int[][] grid = {{1, 0, 0, 1, 0}, {1, 0, 0, 1, 1}, {1, 1, 0, 1, 1}, {1, 0, 0, 0, 0}, {0, 0, 1, 1, 0}};
        int[][] hits = { {2, 0}, {1, 3}, {1, 4}, {0, 3}};
        int[] ans = hitBricks(grid, hits);
        for (int i = 0; i < ans.length; i++) {
            System.out.println(ans[i]);
        }
    }
}
