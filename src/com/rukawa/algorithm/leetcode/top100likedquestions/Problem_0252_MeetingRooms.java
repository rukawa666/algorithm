package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.Arrays;
import java.util.Comparator;

/**
 * create by hqh on 2022/9/2
 */
public class Problem_0252_MeetingRooms {
    /**
     * 会议室
     * 给定一个会议时间安排的数组 intervals ，每个会议时间都会包括开始和结束的时间 intervals[i] = [starti, endi],
     * 请你判断一个人是否能够参加这里面的全部会议。
     *
     * 示例 1：
     * 输入：intervals = [[0,30],[5,10],[15,20]]
     * 输出：false
     *
     * 示例 2：
     * 输入：intervals = [[7,10],[2,4]]
     * 输出：true
     *
     * 提示：
     * 0 <= intervals.length <= 104
     * intervals[i].length == 2
     * 0 <= starti < endi <= 106
     */

    public static boolean canAttendMeetings(int[][] intervals) {
        int len = intervals.length;
        Program[] programs = new Program[len];
        for (int i = 0; i < len; i++) {
            programs[i] = new Program(intervals[i][0], intervals[i][1]);
        }
        Arrays.sort(programs, new ProgramComparator());
        int timeLine = 0;
        int res = 0;
        for (int i = 0; i < len; i++) {
            if (timeLine <= programs[i].start) {
                res++;
                timeLine = programs[i].end;
            }
        }
        return res == len;
    }

    public static class Program {
        public int start;
        public int end;

        public Program(int s, int e) {
            this.start = s;
            this.end = e;
        }
    }

    public static class ProgramComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o1.end - o2.end;
        }
    }

    public static void main(String[] args) {
        int[][] intervals = {{0,30},{5,10},{15,20}};
        boolean b = canAttendMeetings(intervals);
        System.out.println(b);
    }
}
