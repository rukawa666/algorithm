package com.rukawa.algorithm.types.class_2022_03_4_week;

// 来自理想汽车
// a -> b，代表a在食物链中被b捕食
// 给定一个有向无环图，返回
// 这个图中从最初级动物到最顶级捕食者的食物链有几条
// 线上测试链接 : https://www.luogu.com.cn/problem/P4017
// 以下代码都提交，提交时把主类名改成"Main"即可
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 注意：洛谷测试平台对java提交非常不友好，空间限制可能会卡住，C++的提交就完全不会
// 所以提交时如果显示失败，就多提交几次，一定是能成功的
// 这道题本身就是用到拓扑排序，没什么特别的
// 但是为了提交能通过，逼迫我在空间上做的优化值得好好说一下，可以推广到其他题目

import java.io.*;

public class Code05_HowManyWaysFromBottomToTop {

	public static int[] in = new int[5001];
	public static boolean[] out = new boolean[5001];
	public static int[] lines = new int[5001];
	public static int[] headEdge = new int[5001];
	public static int[] queue = new int[5001];
	public static int mod = 80112002;
	public static int n = 0;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer pin = new StreamTokenizer(br);
		PrintWriter pout = new PrintWriter(new OutputStreamWriter(System.out));
		while (pin.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int)pin.nval;
			pin.nextToken();
			int m = (int)pin.nval;
			int[] preEdge = new int[m + 1];
			int[] edgesTo = new int[m + 1];
			for (int i = 1; i <= m; i++) {
				pin.nextToken();
				int from = (int)pin.nval;
				pin.nextToken();
				int to = (int)pin.nval;
				edgesTo[i] = to;
				preEdge[i] = headEdge[from];
				headEdge[from] = i;
				out[from] = true;
				in[to]++;
			}
			pout.println(howManyWays(preEdge, edgesTo));
			pout.flush();
		}
	}

	public static int howManyWays(int[] preEdge, int[] edgesTo) {
		int ql = 0;
		int qr = 0;
		for (int i = 1; i <= n; i++) {
			if (in[i] == 0) {
				queue[qr++] = i;
				lines[i] = 1;
			}
		}
		while (ql < qr) {
			int cur = queue[ql++];
			int edge = headEdge[cur];
			while (edge != 0) {
				int next = edgesTo[edge];
				lines[next] = (lines[next] + lines[cur]) % mod;
				if (--in[next] == 0) {
					queue[qr++] = next;
				}
				edge = preEdge[edge];
			}
		}
		int ans = 0;
		for (int i = 1; i <= n; i++) {
			if (!out[i]) {
				ans = (ans + lines[i]) % mod;
			}
		}
		return ans;
	}

}
