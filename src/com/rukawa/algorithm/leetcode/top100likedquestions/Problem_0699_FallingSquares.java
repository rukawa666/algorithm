package com.rukawa.algorithm.leetcode.top100likedquestions;

import com.rukawa.algorithm.trainingcamp.trainingcamp1.class7.Code02_FallingSquares;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-11-27 7:47
 * @Version：1.0
 */
public class Problem_0699_FallingSquares {
    /**
     * 掉落的方块
     * 在无限长的数轴（即 x 轴）上，我们根据给定的顺序放置对应的正方形方块。
     * 第 i 个掉落的方块（positions[i] = (left, side_length)）是正方形，
     * 其中 left 表示该方块最左边的点位置(positions[i][0])，side_length 表示该方块的边长(positions[i][1])。
     * 每个方块的底部边缘平行于数轴（即 x 轴），并且从一个比目前所有的落地方块更高的高度掉落而下。在上一个方块结束掉落，
     * 并保持静止后，才开始掉落新方块。
     * 方块的底边具有非常大的粘性，并将保持固定在它们所接触的任何长度表面上（无论是数轴还是其他方块）。
     * 邻接掉落的边不会过早地粘合在一起，因为只有底边才具有粘性。
     * 返回一个堆叠高度列表 ans 。每一个堆叠高度 ans[i] 表示在通过 positions[0], positions[1], ..., positions[i] 
     * 表示的方块掉落结束后，目前所有已经落稳的方块堆叠的最高高度。
     *
     * 示例 1:
     * 输入: [[1, 2], [2, 3], [6, 1]]
     * 输出: [2, 5, 5]
     * 解释:
     *
     * 第一个方块 positions[0] = [1, 2] 掉落：
     * _aa
     * _aa
     * -------
     * 方块最大高度为 2 。
     * 第二个方块 positions[1] = [2, 3] 掉落：
     * __aaa
     * __aaa
     * __aaa
     * _aa__
     * _aa__
     * --------------
     * 方块最大高度为5。
     * 大的方块保持在较小的方块的顶部，不论它的重心在哪里，因为方块的底部边缘有非常大的粘性。
     * 第三个方块 positions[1] = [6, 1] 掉落：
     * __aaa
     * __aaa
     * __aaa
     * _aa
     * _aa___a
     * --------------
     * 方块最大高度为5。
     * 因此，我们返回结果[2, 5, 5]。
     *
     * 示例 2:
     * 输入: [[100, 100], [200, 100]]
     * 输出: [100, 100]
     * 解释: 相邻的方块不会过早地卡住，只有它们的底部边缘才能粘在表面上。
     *
     * 注意:
     * 1 <= positions.length <= 1000.
     * 1 <= positions[i][0] <= 10^8.
     * 1 <= positions[i][1] <= 10^6.
     */
    public static class SegmentTree {

        private int[] max;
        private int[] change;
        private boolean[] update;

        public SegmentTree(int size) {
            // 把0给丢弃，所以是size+1
            int N = size + 1;
            max = new int[N << 2];
            change = new int[N << 2];
            update = new boolean[N << 2];
        }

        public void pushOn(int rt) {
            max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
        }

        public void pushDown(int rt, int ln, int rn) {
            if (update[rt]) {
                update[rt << 1] = update[rt];
                update[rt << 1 | 1] = update[rt];
                change[rt << 1] = change[rt];
                change[rt << 1 | 1] = change[rt];
                max[rt << 1] = change[rt];
                max[rt << 1 | 1] = change[rt];
                update[rt] = false;
            }
        }

        public void update(int L, int R, int C, int l, int r, int rt) {
            if (L <= l && r <= R) {
                update[rt] = true;
                change[rt] = C;
                max[rt] = C;
                return;
            }
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            if (L <= mid) {
                update(L, R, C, l, mid, rt << 1);
            }

            if (R > mid) {
                update(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            pushOn(rt);
        }

        public int query(int L, int R, int l, int r, int rt) {
            if (L <= l && r <= R) {
                return max[rt];
            }

            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            int left = 0;
            int right = 0;
            if (L <= mid) {
                left = query(L, R, l, mid, rt << 1);
            }

            if (R > mid) {
                right = query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return Math.max(left, right);
        }
    }

    // 做范围的离散化，因为落下一个正方形有可能位置出现的值很大
    // 离散化：所有的块左右两个边界都列一下有哪些值，从小到大排个序，最小的是1下标，然后依次
    // positions
    // [2,7] -> 2,8
    // [3,10] -> 3,12
    public HashMap<Integer, Integer> index(int[][] positions) {
        // 有序表
        // 离散化
        TreeSet<Integer> pos = new TreeSet<>();
        for (int[] arr : positions) {
            // 左边界
            pos.add(arr[0]);
            // 右边界
            // 为什么右边界-1？
            // 比如[3,2] 从x在位置，往右延伸到5
            // 但是[5,3] 在x等于5的位置要落下，但是此时5位置已经被占用所以不能落到底，实际上是可以的
            // 所以规定[3,2] 从x在3位置开始，能覆盖到4
            pos.add(arr[0] + arr[1] - 1);
        }
        // 每一个位置，在线段树中的对应的标号标好
        // 100 -> 0, 105 -> 1, 108 -> 2
        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        // 从小到大遍历，第一个位置的index从1开始，后续++
        for (Integer index : pos) {
            map.put(index, ++count);
        }
        return map;
    }

    public List<Integer> fallingSquares(int[][] positions) {
        HashMap<Integer, Integer> map = index(positions);
        // 100 -> 1, 306 -> 2, 403 -> 3
        // [100,403]  请你在1~3的范围上进行修改
        // 不拿原数组的值去做线段树的修改，用编好的号去做修改
        int N = map.size(); // 1~N
        Code02_FallingSquares.SegmentTree segmentTree = new Code02_FallingSquares.SegmentTree(N);
        int max = 0;
        List<Integer> res = new ArrayList<>();
        // 每落下一个正方形，收集一下，所有东西组成的图像，最大的高度是什么
        for (int[] arr : positions) {
            // 左边界
            int L = map.get(arr[0]);
            // 右边界
            int R = map.get(arr[0] + arr[1] - 1);
            // 之前的最大高度+此时块的高度
            int height = segmentTree.query(L, R, 1, N, 1) + arr[1];
            max = Math.max(max, height);
            res.add(max);
            // L...R 范围上，此时的高度更新为height
            segmentTree.update(L, R, height, 1, N, 1);
        }
        return res;
    }
}
