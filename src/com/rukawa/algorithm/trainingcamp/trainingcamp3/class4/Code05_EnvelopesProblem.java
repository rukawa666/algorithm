package com.rukawa.algorithm.trainingcamp.trainingcamp3.class4;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-26 21:30
 * @Version：1.0
 */
public class Code05_EnvelopesProblem {
    /**
     * 每个信封都有长和宽两个维度的数据，A信封如果想嵌套在B信封里面，A信封必须在长和宽上都小于B才行
     * 如果给你一批信封，返回最大的嵌套层数
     */
    public static class Envelope {
        public int l;
        public int h;

        public Envelope(int weight, int height) {
            this.l = weight;
            this.h = height;
        }
    }

    public static class EnvelopeComparator implements Comparator<Envelope> {

        @Override
        public int compare(Envelope o1, Envelope o2) {
            return o1.l != o2.l ? o1.l - o2.l : o2.h - o1.h;
        }
    }

    public static Envelope[] getSortedEnvelopes(int[][] matrix) {
        Envelope[] res = new Envelope[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            res[i] = new Envelope(matrix[i][0], matrix[i][1]);
        }
        Arrays.sort(res, new EnvelopeComparator());
        return res;
    }
    public static int maxEnvelopes(int[][] matrix) {
        Envelope[] envelopes = getSortedEnvelopes(matrix);
        int[] ends = new int[matrix.length];
        ends[0] = envelopes[0].h;
        int right = 0;
        int l = 0;
        int r = 0;
        int m = 0;
        for (int i = 0; i != envelopes.length; i++) {
            l = 0;
            r = right;
            while (l <= r) {
                m = l + ((r - l) >> 1);
                if (envelopes[i].h > ends[m]) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }
            right = Math.max(right, l);
            ends[l] = envelopes[i].h;
        }
        return right + 1;
    }

    public static void main(String[] args) {
        int[][] test = { { 3, 4 }, { 2, 3 }, { 4, 5 }, { 1, 3 }, { 2, 2 }, { 3, 6 }, { 1, 2 }, { 3, 2 }, { 2, 4 } };
        System.out.println(maxEnvelopes(test));
    }
}
