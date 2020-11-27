package com.rukawa.algorithm.trainingcamp.trainingcamp5.class3;

import java.util.Stack;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-11-09 7:55
 * @Version：1.0
 */
public class Code02_BricksFallingWhenHit1 {
    /**
     * 给定一个只含有0和1的二维数组matrix，第0行表示天花板，每个位置认为从上、下、左、右四个方向有粘性，比如：
     * matrix =
     * 1 0 0 1 0
     * 1 0 0 1 1
     * 1 1 0 1 1
     * 1 0 0 0 0
     * 0 0 1 1 0
     * 注意到0行0列是1，然后能延伸出5个1的一片。
     * 同理0行3列也是1，也能延伸出5个1的一片。
     * 注意4行2列是1，然后能延伸出2个1的一片。
     * 其中有两片1是粘在天花板上。
     * 在给定一个二维数组bomb，表示炸弹的位置，比如：
     * bomb =
     * 2 0
     * 1 3
     * 1 4
     * 0 3
     * 第一枚炮弹在2行0列，该处的1直接被打碎，然后会有2个1掉下来
     * 第二枚炮弹在1行3列，该处的1直接被打碎，然后会有3个1掉下来
     * 第三枚炮弹在1行4列，该处的1已经被打碎，所以直接打空，不会有1掉下来
     * 第四枚炮弹在0行3列，该处的1直接被打碎，不会有1掉下来，因为这一片1只剩这一个了
     * 根据matrix和bomb，返回结果[2, 3, 0, 0]
     */
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

    public static class UnionFind {
        // 原始数组，经过炮弹的影响之后，所得到的grid
        private int[][] grid;  // 主函数处理后的原始矩阵
        private int N; // 行数
        private int M; // 列数
        private int cellingAll; // 有多少个1能连接上天花板上
        // 某个集合，是不是整体连接到天花板上了，如果是，假设这个集合代表点是x，cellingSet包含x
        // 如果不是，假设这个集合代表点是x，cellingSet不包含x
        private boolean[] cellingSet; // 集合能够连到天花板，它的代表点才在里面
        private int[] fatherMap;
        private int[] sizeMap;

        // matrix，炮弹会让某些位置的1变为2,2和0一样表示不连通
        public UnionFind(int[][] matrix) {
            initSpace(matrix);
            initConnect();
        }

        public void initSpace(int[][] matrix) {
            grid = matrix;
            N = grid.length;
            M = grid[0].length;
            int all = N * M;
            // 连接到天花板上砖的数量
            cellingAll = 0;
            cellingSet = new boolean[all];
            fatherMap = new int[all];
            sizeMap = new int[all];
            // 找到集合中所有的1，每个1独立成一个集合
            for (int row = 0; row < N; row++) {
                for (int col = 0; col < M; col++) {
                    // 遍历每一个[i][j] 2,0直接跳过
                    if (grid[row][col] == 1) {
                        int index = row * M + col;
                        fatherMap[index] = index;
                        sizeMap[index] = 1;
                        if (row == 0) {
                            cellingSet[index] = true;
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

        private int find(int row, int col) {
            Stack<Integer> stack = new Stack<>();
            int index = row * M + col;
            while (index != fatherMap[index]) {
                stack.add(index);
                index = fatherMap[index];
            }

            while (!stack.isEmpty()) {
                fatherMap[stack.pop()]= index;
            }

            return index;
        }

        // (row1, col1) 和(row2, col2)背后的东西合并, 都是1才合并
        private void union(int row1, int col1, int row2, int col2) {
            if (isValid(row1, col1) && isValid(row2, col2)) {
                int father1 = find(row1, col1);
                int father2 = find(row2, col2);
                if (father1 != father2) {
                    int size1 = sizeMap[father1];
                    int size2 = sizeMap[father2];

                    int big = size1 >= size2 ? father1 : father2;
                    int small = big == father1 ? father2 : father1;
                    fatherMap[small] = big;
                    sizeMap[big] = size1 + size2;

                    // 集合1整体连不连得到天花板
                    boolean status1 = cellingSet[father1];
                    // 集合2整体连不连得到天花板
                    boolean status2 = cellingSet[father2];
                    // 如果两个集合能够连接到天花板的状态是不一样的
                    if (status1 ^ status2) {
                        cellingSet[big] = true;
                        cellingAll += status1 ? size2 : size1;
                    }
//                    if (size1 <= size2) {
//                        // 集合1和集合2，已经合并，共同的父亲节点father2
//                        fatherMap[father1] = father2;
//                        sizeMap[father2] = size1 + size2;
//                        // 如果两个集合能够连接到天花板的状态是不一样的
//                        if (status1 ^ status2) {
//                            cellingSet[father2] = true;
//                            cellingAll += status1 ? size2 : size1;
//                        }
//                    } else {
//                        fatherMap[father2] = father1;
//                        sizeMap[father1] = size1 + size2;
//                        if (status1 ^ status2) {
//                            cellingSet[father1] = true;
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
            grid[row][col] = 1;
            int cur = row * M + col;
            if (row == 0) {
                cellingSet[col] = true;
                cellingAll++;
            }
            fatherMap[cur] = cur;
            sizeMap[cur] = 1;
            int pre = cellingAll;
            // 上、下、左、右的1相连
            union(row, col, row - 1, col);
            union(row, col, row + 1, col);
            union(row, col, row, col - 1);
            union(row, col, row, col + 1);
            int now = cellingAll;
            if (row == 0) {
                return now - pre;
            } else {
                return now == pre ? 0 : now - pre - 1;
            }
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
