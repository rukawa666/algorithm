package com.rukawa.algorithm.greater1.class33;

public class Problem_0242_ValidAnagram {
	/**
	 * 有效的字母异位词
	 *
	 * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
	 * 注意：若 s 和 t 中每个字符出现的次数都相同，则称 s 和 t 互为字母异位词。
	 */
	public static boolean isAnagram(String s, String t) {
		if (s.length() != t.length()) {
			return false;
		}
		char[] str1 = s.toCharArray();
		char[] str2 = t.toCharArray();
		int[] count = new int[256];
		for (char cha : str1) {
			count[cha]++;
		}
		for (char cha : str2) {
			if (--count[cha] < 0) {
				return false;
			}
		}
		return true;
	}

}
