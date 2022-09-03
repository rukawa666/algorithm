package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-25 21:54
 * @Version：1.0
 */
public class Problem_0253_MeetingRoomsII {
    /**
     * 会议室 II
     * 给你一个会议时间安排的数组 intervals ，每个会议时间都会包括开始和结束的时间 intervals[i] = [starti, endi] ，返回 所需会议室的最小数量 。
     *
     * 示例 1：
     * 输入：intervals = [[0,30],[5,10],[15,20]]
     * 输出：2
     *
     * 示例 2：
     * 输入：intervals = [[7,10],[2,4]]
     * 输出：1
     *
     * 提示：
     * 1 <= intervals.length <= 104
     * 0 <= starti < endi <= 106
     */

    public int minMeetingRooms(int[][] intervals) {

        int res = 0;

        return res;
    }

    public static class Program {
        public int start;
        public int end;

        public Program(int s, int e) {
            this.start = s;
            this.end = e;
        }
    }
}
