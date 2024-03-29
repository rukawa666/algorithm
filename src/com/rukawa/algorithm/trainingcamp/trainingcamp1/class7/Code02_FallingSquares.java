package com.rukawa.algorithm.trainingcamp.trainingcamp1.class7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-06 8:29
 * @Version：1.0
 */
public class Code02_FallingSquares {
    /**
     * 想象一下标准的俄罗斯方块游戏，X轴是积木最终下落到底的轴线
     * 	下面是这个游戏的简化版：
     * 	1、只会下落正方形积木
     * 	2、[a、b] -> 代表一个边长为b的正方形积木，积木左边缘沿着X=a这条线从上方掉落
     * 	3、认为整个X轴都可能接住积木，也就是说简化版游戏是没有整体的左右边界的
     * 	4、没有整体的左右边界，所以简化版游戏不会消除积木，因为不会有哪一层被填满
     * 	给定一个N*2的二维数组matrix，可以代表N个积木依次掉落，返回每一次掉落之后的最大高度
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
        SegmentTree segmentTree = new SegmentTree(N);
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
