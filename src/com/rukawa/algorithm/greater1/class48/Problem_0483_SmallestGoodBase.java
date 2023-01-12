package com.rukawa.algorithm.greater1.class48;

public class Problem_0483_SmallestGoodBase {
	/**
	 * 最小好进制
	 * 以字符串的形式给出 n , 以字符串的形式返回 n 的最小 好进制  。
	 *
	 * 如果 n 的  k(k>=2) 进制数的所有数位全为1，则称 k(k>=2) 是 n 的一个 好进制 。
	 */
	// ""4651" -> 4651
	public static String smallestGoodBase(String n) {
		long num = Long.valueOf(n);
		// n这个数，需要从m位开始试，固定位数，一定要有m位！
		for (int m = (int) (Math.log(num + 1) / Math.log(2)); m > 2; m--) {
			// num开m次方
			long l = (long) (Math.pow(num, 1.0 / m));
			long r = (long) (Math.pow(num, 1.0 / (m - 1))) + 1L;
			while (l <= r) {
				long k = l + ((r - l) >> 1);
				long sum = 0L;
				long base = 1L;
				for (int i = 0; i < m && sum <= num; i++) {
					sum += base;
					base *= k;
				}
				if (sum < num) {
					l = k + 1;
				} else if (sum > num) {
					r = k - 1;
				} else {
					return String.valueOf(k);
				}
			}
		}
		return String.valueOf(num - 1);
	}

}
