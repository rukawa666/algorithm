package com.rukawa.algorithm.trainingcamp.trainingcamp1.class7;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-06 10:18
 * @Version：1.0
 */
public class Code03_CoverMax {

    /**
     * 线段最大重合问题，可以选择用线段树做，但是比较重
     * 线段被覆盖最多的高度，给一些线段，哪一段线段被覆盖最多
     * 给线段[1,3], [2,6], [4,9],哪一段是覆盖最多？最多的线段有几段？
     * @param lines
     * @return
     */

    public static int maxCover1(int[][] lines) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < lines.length; i++) {
            min = Math.min(min, lines[i][0]);
            max = Math.max(max, lines[i][1]);
        }

        int cover = 0;
        for (double p = min + 0.5 ; p < max; p+=1) {
            int cur = 0;
            for (int i = 0; i < lines.length; i++) {
                if (lines[i][0] < p && lines[i][1] > p) {
                    cur++;
                }
            }
            cover = Math.max(cover, cur);
        }
        return cover;
    }

    public static int maxCover2(int[][] m) {
        Line[] lines = new Line[m.length];
        for (int i = 0; i < m.length; i++) {
            lines[i] = new Line(m[i][0] ,m[i][1]);
        }

        Arrays.sort(lines, new StartComparator());
        PriorityQueue<Line> heap = new PriorityQueue<>(new EndComparator());
        int max = 0;
        for (int i = 0; i < lines.length; i++) {
            // 弹出<=R 的数
            while (!heap.isEmpty() && heap.peek().end <= lines[i].start) {
                heap.poll();
            }
            heap.add(lines[i]);
            max = Math.max(max, heap.size());
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

    public static class StartComparator implements Comparator<Line> {

        @Override
        public int compare(Line o1, Line o2) {
            return o1.start - o2.start;
        }
    }

    public static class EndComparator implements Comparator<Line> {
        @Override
        public int compare(Line o1, Line o2) {
            return o1.end - o2.end;
        }
    }

    public static int[][] generateLines(int N, int L, int R) {
        int size = (int)(Math.random() * N) + 1;
        int[][] ans = new int[size][2];
        for (int i = 0; i < size; i++) {
            int a = L + (int)(Math.random() * (R - L + 1));
            int b = L + (int)(Math.random() * (R - L + 1));
            if (a == b) {
                b = a + 1;
            }
            ans[i][0] = Math.min(a, b);
            ans[i][1] = Math.max(a, b);
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        int N = 100;
        int L = 0;
        int R = 200;
        int testTimes = 200000;
        for (int i = 0; i < testTimes; i++) {
            int[][] lines = generateLines(N, L, R);
            int ans1 = maxCover1(lines);
            int ans2 = maxCover2(lines);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");
    }

}
