package com.rukawa.algorithm.base.class04;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-19 8:09
 * @Version：1.0
 */
public class Code04_CoverMax {
    /**
     * 最大线段重合问题(用堆实现)
     * 给定很多线段，每个线段都有两个数[start, end]，表示线段开始位置和结束位置，左右都是闭区间
     * 规定：
     * 	1、线段的开始和结束位置一定都是整数值
     * 	2、线段重合区域的长度必须>=1
     * 返回线段最多重合区域中，包含了几条线段
     *
     * 思路：
     *  1、线段从开始位置，从小到大排序，依次考察每一个线段
     *  2、任何一个线段，在小跟堆中弹出比当前线段开始位置的值小于等于的元素
     *  3、在小跟堆中加入当前结束位置的值
     *  4、每次记录堆中的元素个数，最的个数即最终结果
     */
    public static int maxCover(int[][] m) {
        Line[] lines = new Line[m.length];
        // O(N)
        for (int i = 0; i < m.length; i++) {
            lines[i] = new Line(m[i][0], m[i][1]);
        }

        // O(N * logN)
//        Arrays.sort(lines, new LineStartComparator());
        Arrays.sort(lines, Comparator.comparingInt(n -> n.start));
        // 小跟堆，放每条线段的结尾值
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int max = 0;
        // O(N * logN)
        for (int i = 0; i < lines.length; i++) {
            while (!minHeap.isEmpty() && minHeap.peek() <= lines[i].start) {
                minHeap.poll();
            }
            minHeap.add(lines[i].end);
            max = Math.max(max, minHeap.size());
        }
        return max;
    }

    public static class Line {
        public int start;
        public int end;

        public Line(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static class LineStartComparator implements Comparator<Line> {
        @Override
        public int compare(Line o1, Line o2) {
            return o1.start - o2.start;
        }
    }

    public static void main(String[] args) {
        int[][] m = {{10,16}, {2,8}, {1,6}, {7,12}};
        System.out.println(maxCover(m));
    }
}
