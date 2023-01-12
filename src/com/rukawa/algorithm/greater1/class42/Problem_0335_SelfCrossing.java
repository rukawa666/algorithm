package com.rukawa.algorithm.greater1.class42;

public class Problem_0335_SelfCrossing {
	/**
	 * 路径交叉
	 *
	 * 给你一个整数数组 distance 。
	 * 从 X-Y 平面上的点 (0,0) 开始，先向北移动 distance[0] 米，然后向西移动 distance[1] 米，向南移动 distance[2] 米，
	 * 向东移动 distance[3] 米，持续移动。也就是说，每次移动后你的方位会发生逆时针变化。
	 *
	 * 判断你所经过的路径是否相交。如果相交，返回 true ；否则，返回 false 。
	 */
	public static boolean isSelfCrossing(int[] x) {
		if (x == null || x.length < 4) {
			return false;
		}
		if ((x.length > 3 && x[2] <= x[0] && x[3] >= x[1])
				|| (x.length > 4 
						&& ((x[3] <= x[1] && x[4] >= x[2]) || (x[3] == x[1] && x[0] + x[4] >= x[2])))) {
			return true;
		}
		for (int i = 5; i < x.length; i++) {
			if (x[i - 1] <= x[i - 3] && ((x[i] >= x[i - 2])
					|| (x[i - 2] >= x[i - 4] && x[i - 5] + x[i - 1] >= x[i - 3] && x[i - 4] + x[i] >= x[i - 2]))) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		int[] arr = { 2, 2, 3, 2, 2 };
		System.out.println(isSelfCrossing(arr));
	}

}
