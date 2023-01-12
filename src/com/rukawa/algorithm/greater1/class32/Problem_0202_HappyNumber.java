package com.rukawa.algorithm.greater1.class32;

import java.util.HashSet;
import java.util.TreeSet;

public class Problem_0202_HappyNumber {
	/**
	 * 快乐数
	 *
	 * 编写一个算法来判断一个数 n 是不是快乐数。
	 * 「快乐数」 定义为：
	 * 对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和。
	 * 然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。
	 * 如果这个过程 结果为 1，那么这个数就是快乐数。
	 * 如果 n 是 快乐数 就返回 true ；不是，则返回 false 。
	 */
	public static boolean isHappy1(int n) {
		HashSet<Integer> set = new HashSet<>();
		while (n != 1) {
			int sum = 0;
			while (n != 0) {
				int r = n % 10;
				sum += r * r;
				n /= 10;
			}
			n = sum;
			if (set.contains(n)) {
				break;
			}
			set.add(n);
		}
		return n == 1;
	}

	// 实验代码
	public static TreeSet<Integer> sum(int n) {
		TreeSet<Integer> set = new TreeSet<>();
		while (!set.contains(n)) {
			set.add(n);
			int sum = 0;
			while (n != 0) {
				sum += (n % 10) * (n % 10);
				n /= 10;
			}
			n = sum;
		}
		return set;
	}

	public static boolean isHappy2(int n) {
		while (n != 1 && n != 4) {
			int sum = 0;
			while (n != 0) {
				sum += (n % 10) * (n % 10);
				n /= 10;
			}
			n = sum;
		}
		return n == 1;
	}

	public static void main(String[] args) {
		for (int i = 1; i < 1000; i++) {
			System.out.println(sum(i));
		}
	}

}
