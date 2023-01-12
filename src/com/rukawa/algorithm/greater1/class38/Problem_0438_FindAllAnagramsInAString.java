package com.rukawa.algorithm.greater1.class38;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Problem_0438_FindAllAnagramsInAString {
	/**
	 * 找到字符串中所有字母异位词
	 *
	 * 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
	 * 异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。
	 */
	public static List<Integer> findAnagrams(String s, String p) {
		List<Integer> ans = new ArrayList<>();
		if (s == null || p == null || s.length() < p.length()) {
			return ans;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		char[] pst = p.toCharArray();
		int M = pst.length;
		HashMap<Character, Integer> map = new HashMap<>();
		for (char cha : pst) {
			if (!map.containsKey(cha)) {
				map.put(cha, 1);
			} else {
				map.put(cha, map.get(cha) + 1);
			}
		}
		int all = M;
		for (int end = 0; end < M - 1; end++) {
			if (map.containsKey(str[end])) {
				int count = map.get(str[end]);
				if (count > 0) {
					all--;
				}
				map.put(str[end], count - 1);
			}
		}
		for (int end = M - 1, start = 0; end < N; end++, start++) {
			if (map.containsKey(str[end])) {
				int count = map.get(str[end]);
				if (count > 0) {
					all--;
				}
				map.put(str[end], count - 1);
			}
			if (all == 0) {
				ans.add(start);
			}
			if (map.containsKey(str[start])) {
				int count = map.get(str[start]);
				if (count >= 0) {
					all++;
				}
				map.put(str[start], count + 1);
			}
		}
		return ans;
	}

}
