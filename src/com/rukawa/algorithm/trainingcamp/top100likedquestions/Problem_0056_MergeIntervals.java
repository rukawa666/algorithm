package com.rukawa.algorithm.trainingcamp.top100likedquestions;

import java.util.Comparator;

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

    public static class Range {
        public int start;
        public int end;

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static class RangeComparator implements Comparator<Range> {

        @Override
        public int compare(Range o1, Range o2) {
            return o1.start - o2.start;
        }
    }

    public static int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return new int[0][0];
        }
        Range[] arr = new Range[intervals.length];
        return null;
    }
}
