package com.rukawa.algorithm.greater1.class33;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Problem_0207_CourseSchedule {
	/**
	 * 课程表
	 *
	 * 你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。
	 * 在选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，其中 prerequisites[i] = [ai, bi] ，
	 * 表示如果要学习课程 ai 则 必须 先学习课程  bi 。
	 * 例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
	 * 请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false
	 */
	// 一个node，就是一个课程
	// name是课程的编号
	// in是课程的入度
	public static class Course {
		public int name;
		public int in;
		public ArrayList<Course> nexts;

		public Course(int n) {
			name = n;
			in = 0;
			nexts = new ArrayList<>();
		}
	}

	public static boolean canFinish1(int numCourses, int[][] prerequisites) {
		if (prerequisites == null || prerequisites.length == 0) {
			return true;
		}
		// 一个编号 对应 一个课的实例
		HashMap<Integer, Course> nodes = new HashMap<>();
		for (int[] arr : prerequisites) {
			int to = arr[0];
			int from = arr[1]; // from -> to
			if (!nodes.containsKey(to)) {
				nodes.put(to, new Course(to));
			}
			if (!nodes.containsKey(from)) {
				nodes.put(from, new Course(from));
			}
			Course t = nodes.get(to);
			Course f = nodes.get(from);
			f.nexts.add(t);
			t.in++;
		}
		int needPrerequisiteNums = nodes.size();
		Queue<Course> zeroInQueue = new LinkedList<>();
		for (Course node : nodes.values()) {
			if (node.in == 0) {
				zeroInQueue.add(node);
			}
		}
		int count = 0;
		while (!zeroInQueue.isEmpty()) {
			Course cur = zeroInQueue.poll();
			count++;
			for (Course next : cur.nexts) {
				if (--next.in == 0) {
					zeroInQueue.add(next);
				}
			}
		}
		return count == needPrerequisiteNums;
	}

	// 和方法1算法过程一样
	// 但是写法优化了
	public static boolean canFinish2(int courses, int[][] relation) {
		if (relation == null || relation.length == 0) {
			return true;
		}
		// 3 :  0 1 2
		// nexts :   0   {}
		//           1   {}
		//           2   {}
		//           3   {0,1,2}
		ArrayList<ArrayList<Integer>> nexts = new ArrayList<>();
		for (int i = 0; i < courses; i++) {
			nexts.add(new ArrayList<>());
		}
		// 3 入度 1  in[3] == 1
		int[] in = new int[courses];
		for (int[] arr : relation) {
			// arr[1] from   arr[0] to
			nexts.get(arr[1]).add(arr[0]);
			in[arr[0]]++;
		}
		
		// 队列
		int[] zero = new int[courses];
		// 该队列有效范围是[l,r)
		// 新来的数，放哪？r位置，r++
		// 出队列的数，从哪拿？l位置，l++
		// l == r 队列无元素  l < r 队列有元素
		int l = 0;
		int r = 0;
		for (int i = 0; i < courses; i++) {
			if (in[i] == 0) {
				zero[r++] = i;
			}
		}
		int count = 0;
		while (l != r) {
			count++; // zero[l] 出队列   l++
			for (int next : nexts.get(zero[l++])) {
				if (--in[next] == 0) {
					zero[r++] = next;
				}
			}
		}
		return count == nexts.size();
	}

}
