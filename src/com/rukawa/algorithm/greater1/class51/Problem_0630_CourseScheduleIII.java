package com.rukawa.algorithm.greater1.class51;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Problem_0630_CourseScheduleIII {
	/**
	 * 课程表 III
	 * 这里有 n 门不同的在线课程，按从 1 到 n 编号。给你一个数组 courses ，其中 courses[i] = [durationi, lastDayi]
	 * 表示第 i 门课将会 持续 上 durationi 天课，并且必须在不晚于 lastDayi 的时候完成。
	 *
	 * 你的学期从第 1 天开始。且不能同时修读两门及两门以上的课程。
	 *
	 * 返回你最多可以修读的课程数目。
	 */
	public static int scheduleCourse(int[][] courses) {
		// courses[i]  = {花费，截止}
		Arrays.sort(courses, (a, b) -> a[1] - b[1]);
		// 花费时间的大根堆
		PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> b - a);
		// 时间点
		int time = 0;
		for (int[] c : courses) {
			// 
			if (time + c[0] <= c[1]) { // 当前时间 + 花费 <= 截止时间的
				heap.add(c[0]);
				time += c[0];
			} else { // 当前时间 + 花费 > 截止时间的, 只有淘汰掉某课，当前的课才能进来！
				if (!heap.isEmpty() && heap.peek() > c[0]) {
//					time -= heap.poll();
//					heap.add(c[0]);
//					time += c[0];
					heap.add(c[0]);
					time += c[0] - heap.poll();
				}
			}
		}
		return heap.size();
	}

}
