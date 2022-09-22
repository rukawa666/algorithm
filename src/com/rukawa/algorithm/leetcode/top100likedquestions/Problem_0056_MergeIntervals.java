package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-06 9:09
 * @Version：1.0
 */
public class Problem_0056_MergeIntervals {
    /**
     * 合并区间
     * 给出一个区间的集合，请合并所有重叠的区间。
     *
     * 示例 1:
     * 输入: intervals = [[1,3],[2,6],[8,10],[15,18]]
     * 输出: [[1,6],[8,10],[15,18]]
     * 解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
     *
     * 示例 2:
     * 输入: intervals = [[1,4],[4,5]]
     * 输出: [[1,5]]
     * 解释: 区间 [1,4] 和 [4,5] 可被视为重叠区间。
     * 注意：输入类型已于2019年4月15日更改。 请重置默认代码定义以获取新方法签名。
     */

    public int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return new int[0][0];
        }
        Range[] arr = new Range[intervals.length];
        for (int i = 0; i < intervals.length; i++) {
            arr[i] = new Range(intervals[i][0], intervals[i][1]);
        }
        Arrays.sort(arr, Comparator.comparingInt(x -> x.start));

        List<Range> res = new ArrayList<>();
        int start = arr[0].start;
        int end = arr[0].end;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].start > end) {
                res.add(new Range(start, end));
                start = arr[i].start;
                end = arr[i].end;
            } else {
                end = Math.max(end, arr[i].end);
            }
        }
        res.add(new Range(start, end));

        return generateMatrix(res);
    }

    public int[][] generateMatrix(List<Range> list) {
        int[][] res = new int[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            res[i] = new int[]{list.get(i).start, list.get(i).end};
        }
        return res;
    }

    public int[][] merge2(int[][] intervals) {
        if (intervals.length == 0) {
            return new int[0][0];
        }
        Arrays.sort(intervals, Comparator.comparingInt(x -> x[0]));

        int start = intervals[0][0];
        int end = intervals[0][1];
        int size = 0;
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] > end) {
                intervals[size][0] = start;
                intervals[size][1] = end;
                size++;
                start = intervals[i][0];
                end = intervals[i][1];

            } else {
                end = Math.max(end, intervals[i][1]);
            }
        }
        intervals[size][0] = start;
        intervals[size][1] = end;
        size++;
        return Arrays.copyOf(intervals, size);
    }

    public static class Range {
        public int start;
        public int end;

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
