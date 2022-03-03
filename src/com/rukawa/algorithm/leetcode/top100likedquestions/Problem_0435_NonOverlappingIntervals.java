package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.Arrays;
import java.util.Comparator;

/**
 * create by hqh on 2022/3/3
 */
public class Problem_0435_NonOverlappingIntervals {
    /**
     * 无重叠区间
     * 给定一个区间的集合 intervals ，其中 intervals[i] = [starti, endi] 。返回 需要移除区间的最小数量，使剩余区间互不重叠 。
     *
     * 示例 1:
     * 输入: intervals = [[1,2],[2,3],[3,4],[1,3]]
     * 输出: 1
     * 解释: 移除 [1,3] 后，剩下的区间没有重叠。
     *
     * 示例 2:
     * 输入: intervals = [ [1,2], [1,2], [1,2] ]
     * 输出: 2
     * 解释: 你需要移除两个 [1,2] 来使剩下的区间没有重叠。
     *
     * 示例 3:
     * 输入: intervals = [ [1,2], [2,3] ]
     * 输出: 0
     * 解释: 你不需要移除任何区间，因为它们已经是无重叠的了。
     *
     * 提示:
     * 1 <= intervals.length <= 105
     * intervals[i].length == 2
     * -5 * 104 <= starti < endi <= 5 * 104
     */

    public int eraseOverlapIntervals(int[][] intervals) {
        /**
         * 思路：转化为不重叠的区域的有几块？最终的答案是需要删除的区域=总区域-不重叠的区域
         */
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[1]));
        int pos = intervals[0][1];
        int res = 1;
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] >= pos) {
                res++;
                pos = intervals[i][1];
            }
        }
        return intervals.length - res;
    }
}
