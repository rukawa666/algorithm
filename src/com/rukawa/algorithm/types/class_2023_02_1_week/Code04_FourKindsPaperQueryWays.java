package com.rukawa.algorithm.types.class_2023_02_1_week;

// 测试链接 : https://www.luogu.com.cn/problem/P1450

import java.io.*;

public class Code04_FourKindsPaperQueryWays {

	public static int limit = 100000;
	public static long[] dp = new long[limit + 1];
	public static int[] c = new int[4];
	public static int[] d = new int[4];
	public static int n, s;

	public static void init() {
		dp[0] = 1;
		for (int i = 0; i <= 3; i++) {
			for (int j = c[i]; j <= limit; j++) {
				dp[j] += dp[j - c[i]];
			}
		}
	}

	public static long query() {
		long minus = 0;
		for (int status = 1; status <= 15; status++) {
			long t = s;
			int sign = -1;
			for (int j = 0; j <= 3; j++) {
				if (((status >> j) & 1) == 1) {
					t -= c[j] * (d[j] + 1);
					sign = -sign;
				}
			}
			if (t >= 0) {
				minus += dp[(int) t] * sign;
			}
		}
		return dp[s] - minus;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			c[0] = (int) in.nval;
			in.nextToken();
			c[1] = (int) in.nval;
			in.nextToken();
			c[2] = (int) in.nval;
			in.nextToken();
			c[3] = (int) in.nval;
			in.nextToken();
			n = (int) in.nval;
			init();
			for (int i = 0; i < n; i++) {
				in.nextToken();
				d[0] = (int) in.nval;
				in.nextToken();
				d[1] = (int) in.nval;
				in.nextToken();
				d[2] = (int) in.nval;
				in.nextToken();
				d[3] = (int) in.nval;
				in.nextToken();
				s = (int) in.nval;
				out.println(query());
				out.flush();
			}
		}
	}

}
