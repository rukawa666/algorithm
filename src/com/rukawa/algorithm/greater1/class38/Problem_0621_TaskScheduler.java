package com.rukawa.algorithm.greater1.class38;

public class Problem_0621_TaskScheduler {
	/**
	 * 任务调度器
	 * 给你一个用字符数组 tasks 表示的 CPU 需要执行的任务列表。其中每个字母表示一种不同种类的任务。任务可以以任意顺序执行，
	 * 并且每个任务都可以在 1 个单位时间内执行完。在任何一个单位时间，CPU 可以完成一个任务，或者处于待命状态。
	 *
	 * 然而，两个 相同种类 的任务之间必须有长度为整数 n 的冷却时间，因此至少有连续 n 个单位时间内 CPU 在执行不同的任务，或者在待命状态。
	 *
	 * 你需要计算完成所有任务所需要的 最短时间 。
	 */
	// ['A', 'B', 'A']
	public static int leastInterval(char[] tasks, int free) {
		int[] count = new int[256];
		// 出现最多次的任务，到底是出现了几次
		int maxCount = 0;
		for (char task : tasks) {
			count[task]++;
			maxCount = Math.max(maxCount, count[task]);
		}
		// 有多少种任务，都出现最多次
		int maxKinds = 0;
		for (int task = 0; task < 256; task++) {
			if (count[task] == maxCount) {
				maxKinds++;
			}
		}
		// maxKinds : 有多少种任务，都出现最多次
		// maxCount : 最多次，是几次？
		// 砍掉最后一组剩余的任务数
		int tasksExceptFinalTeam = tasks.length - maxKinds;
		int spaces = (free + 1) * (maxCount - 1);
		// 到底几个空格最终会留下！
		int restSpaces = Math.max(0, spaces - tasksExceptFinalTeam);
		return tasks.length + restSpaces;
		// return Math.max(tasks.length, ((n + 1) * (maxCount - 1) + maxKinds));
	}
	

}
