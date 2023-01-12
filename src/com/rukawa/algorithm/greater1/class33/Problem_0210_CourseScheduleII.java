package com.rukawa.algorithm.greater1.class33;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Problem_0210_CourseScheduleII {
	/**
	 * 课程表 II
	 *
	 * 现在你总共有 numCourses 门课需要选，记为 0 到 numCourses - 1。给你一个数组 prerequisites ，其中 prerequisites[i] = [ai, bi] ，
	 * 表示在选修课程 ai 前 必须 先选修 bi 。
	 *
	 * 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示：[0,1] 。
	 * 返回你为了学完所有课程所安排的学习顺序。可能会有多个正确的顺序，你只要返回 任意一种 就可以了。如果不可能完成所有课程，返回 一个空数组 。
	 */
	public static class Node {
		public int name;
		public int in;
		public ArrayList<Node> nexts;

		public Node(int n) {
			name = n;
			in = 0;
			nexts = new ArrayList<>();
		}
	}

	public int[] findOrder(int numCourses, int[][] prerequisites) {
		int[] ans = new int[numCourses];
		for (int i = 0; i < numCourses; i++) {
			ans[i] = i;
		}
		if (prerequisites == null || prerequisites.length == 0) {
			return ans;
		}
		HashMap<Integer, Node> nodes = new HashMap<>();
		for (int[] arr : prerequisites) {
			int to = arr[0];
			int from = arr[1];
			if (!nodes.containsKey(to)) {
				nodes.put(to, new Node(to));
			}
			if (!nodes.containsKey(from)) {
				nodes.put(from, new Node(from));
			}
			Node t = nodes.get(to);
			Node f = nodes.get(from);
			f.nexts.add(t);
			t.in++;
		}
		int index = 0;
		Queue<Node> zeroInQueue = new LinkedList<>();
		for (int i = 0; i < numCourses; i++) {
			if (!nodes.containsKey(i)) {
				ans[index++] = i;
			} else {
				if (nodes.get(i).in == 0) {
					zeroInQueue.add(nodes.get(i));
				}
			}
		}
		int needPrerequisiteNums = nodes.size();
		int count = 0;
		while (!zeroInQueue.isEmpty()) {
			Node cur = zeroInQueue.poll();
			ans[index++] = cur.name;
			count++;
			for (Node next : cur.nexts) {
				if (--next.in == 0) {
					zeroInQueue.add(next);
				}
			}
		}
		return count == needPrerequisiteNums ? ans : new int[0];
	}

}
