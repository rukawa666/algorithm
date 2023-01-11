package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * create by hqh on 2023/1/12
 */
public class Problem_0675_CutOffTreesForGolfEvent {
    /**
     * 为高尔夫比赛砍树
     * 你被请来给一个要举办高尔夫比赛的树林砍树。树林由一个 m x n 的矩阵表示， 在这个矩阵中：
     * 0 表示障碍，无法触碰
     * 1 表示地面，可以行走
     * 比 1 大的数 表示有树的单元格，可以行走，数值表示树的高度
     * 每一步，你都可以向上、下、左、右四个方向之一移动一个单位，如果你站的地方有一棵树，那么你可以决定是否要砍倒它。
     * 你需要按照树的高度从低向高砍掉所有的树，每砍过一颗树，该单元格的值变为 1（即变为地面）。
     * 你将从 (0, 0) 点开始工作，返回你砍完所有树需要走的最小步数。 如果你无法砍完所有的树，返回 -1 。
     * 可以保证的是，没有两棵树的高度是相同的，并且你至少需要砍倒一棵树。
     *
     * 示例 1：
     * 输入：forest = [[1,2,3],[0,0,4],[7,6,5]]
     * 输出：6
     * 解释：沿着上面的路径，你可以用 6 步，按从最矮到最高的顺序砍掉这些树。
     *
     * 示例 2：
     * 输入：forest = [[1,2,3],[0,0,0],[7,6,5]]
     * 输出：-1
     * 解释：由于中间一行被障碍阻塞，无法访问最下面一行中的树。
     *
     * 示例 3：
     * 输入：forest = [[2,3,4],[0,0,5],[8,7,6]]
     * 输出：6
     * 解释：可以按与示例 1 相同的路径来砍掉所有的树。
     * (0,0) 位置的树，可以直接砍去，不用算步数。
     *
     * 提示：
     * m == forest.length
     * n == forest[i].length
     * 1 <= m, n <= 50
     * 0 <= forest[i][j] <= 109
     */
    public static int cutOffTree(List<List<Integer>> forest) {
        int n = forest.size();
        int m = forest.get(0).size();
        // [ [3,5,2], [1,9,4] , [2,6,10] ]
        // 低 中 高
        ArrayList<int[]> cells = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int val = forest.get(i).get(j);
                if (val > 1) {
                    cells.add(new int[] { i, j, val });
                }
            }
        }
        cells.sort((a, b) -> a[2] - b[2]);
        int ans = 0, lastR = 0, lastC = 0;
        for (int[] cell : cells) {
            int step = bestWalk(forest, lastR, lastC, cell[0], cell[1]);
            if (step == -1) {
                return -1;
            }
            ans += step;
            lastR = cell[0];
            lastC = cell[1];
            forest.get(lastR).set(lastC, 1);
        }
        return ans;
    }

    public static int[] next = { -1, 0, 1, 0, -1 };

    // 0 1 2 3 4
    // i
    // 行 + next[i-1]
    // 列 + next[i]
    // i == 1 -> 上
    // i == 2 -> 右
    // i == 3 -> 下
    // i == 4 -> 左
    public static int bestWalk(List<List<Integer>> forest, int sr, int sc, int tr, int tc) {
        int n = forest.size();
        int m = forest.get(0).size();
        boolean[][] seen = new boolean[n][m];
        LinkedList<int[]> deque = new LinkedList<>();
        deque.offerFirst(new int[] { 0, sr, sc });
        while (!deque.isEmpty()) {
            int[] cur = deque.pollFirst();
            int step = cur[0], r = cur[1], c = cur[2];
            if (r == tr && c == tc) {
                return step;
            }
            seen[r][c] = true;
            for (int i = 1; i < 5; i++) { // (r,c) 上下左右，全试试！
                int nr = r + next[i - 1];
                int nc = c + next[i];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m && !seen[nr][nc] && forest.get(nr).get(nc) > 0) {
                    int[] move = { step + 1, nr, nc };
                    // 更近的话
                    if ((i == 1 && r > tr) || (i == 2 && c < tc) || (i == 3 && r < tr) || (i == 4 && c > tc)) {
                        deque.offerFirst(move);
                    } else { // 更远的话，放到尾部！
                        deque.offerLast(move);
                    }
                }
            }
        }
        return -1;
    }
}
