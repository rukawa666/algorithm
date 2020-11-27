package com.rukawa.algorithm.trainingcamp.trainingcamp1.class7;

import java.util.*;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-06 10:51
 * @Version：1.0
 */
public class Code04_CoverMax {
    /**
     * 矩形的覆盖面积最大的区域
     */
    public static class Rectangle {
        public int up;
        public int down;
        public int left;
        public int right;

        public Rectangle(int up, int down, int left, int right) {
            this.up = up;
            this.down = down;
            this.left = left;
            this.right = right;
        }
    }

    public static class DownComparator implements Comparator<Rectangle> {
        @Override
        public int compare(Rectangle o1, Rectangle o2) {
            return o1.down - o2.down;
        }
    }

    public static class LeftComparator implements Comparator<Rectangle> {
        @Override
        public int compare(Rectangle o1, Rectangle o2) {
            return o1.left - o2.left;
        }
    }

    public static class RightComparator implements Comparator<Rectangle> {
        @Override
        public int compare(Rectangle o1, Rectangle o2) {
            return o1.right - o2.right;
        }
    }

    // 矩形的数量是N
    // O(N*logN) + O(N) * [O(N) + O(N * logN)]
    // O(N^2 * logN)
    public static int maxCover(Rectangle[] recs) {
        if (recs == null || recs.length == 0) {
            return 0;
        }
        // 矩阵根据底排好序
        Arrays.sort(recs, new DownComparator());
        // 可能会对当前底边的公共区域，产生影响的矩形
        // list -> TreeSet（有序表表达）
        TreeSet<Rectangle> leftOrdered = new TreeSet<>(new LeftComparator());
        int ans = 0;
        // O(N)
        for (int i = 0; i < recs.length; i++) { // 一次考察每个矩形的底边
            int curDown = recs[i].down; // 当前的底边值取出来
            int index = i;
            while (recs[index].down == curDown) {
                leftOrdered.add(recs[index]);   // O(logN)
                index++;
            }
            i = index;
            // O(N)list是不是有一些顶<=底的矩形
            removeLowerOnCurDown(leftOrdered, curDown);
            // 维持了右边界排序的容器
            TreeSet<Rectangle> rightOrdered = new TreeSet<>(new RightComparator());
            for (Rectangle rec : leftOrdered) { // O(N)
                removeLeftOnCurLeft(rightOrdered, rec.left);    // O(N)
                rightOrdered.add(rec);
                ans = Math.max(ans, rightOrdered.size());
            }

        }

        return ans;
    }

    public static void removeLowerOnCurDown(TreeSet<Rectangle> set, int curDown) {
        List<Rectangle> removes = new ArrayList<>();
        for (Rectangle rec : set) {
            if (rec.up <= curDown) {
                removes.add(rec);
            }
        }

        for (Rectangle rec : removes) {
            set.remove(rec);
        }
    }

    public static void removeLeftOnCurLeft(TreeSet<Rectangle> rightOrdered, int curLeft) {
        List<Rectangle> removes = new ArrayList<>();
        for (Rectangle rec : rightOrdered) {
            if (rec.right > curLeft) {
                break;
            }
            removes.add(rec);
        }

        for (Rectangle rec : removes) {
            rightOrdered.remove(rec);
        }
    }

}
