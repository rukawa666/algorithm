package com.rukawa.algorithm.greater1.class38;

import java.util.ArrayList;
import java.util.List;

public class Problem_0763_PartitionLabels {
	/**
	 * 划分字母区间
	 *
	 * 给你一个字符串 s 。我们要把这个字符串划分为尽可能多的片段，同一字母最多出现在一个片段中。
	 * 注意，划分结果需要满足：将所有划分结果按顺序连接，得到的字符串仍然是 s 。
	 * 返回一个表示每个字符串片段的长度的列表。
	 */
	public static List<Integer> partitionLabels(String S) {
		char[] str = S.toCharArray();
		int[] far = new int[26];
		for (int i = 0; i < str.length; i++) {
			far[str[i] - 'a'] = i;
		}
		List<Integer> ans = new ArrayList<>();
		int left = 0;
		int right = far[str[0] - 'a'];
		for (int i = 1; i < str.length; i++) {
			if (i > right) {
				ans.add(right - left + 1);
				left = i;
			}
			right = Math.max(right, far[str[i] - 'a']);
		}
		ans.add(right - left + 1);
		return ans;
	}

}
