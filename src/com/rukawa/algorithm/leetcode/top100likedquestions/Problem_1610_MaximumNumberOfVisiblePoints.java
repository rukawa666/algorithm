package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.Arrays;
import java.util.List;

/**
 * create by hqh on 2023/1/12
 */
public class Problem_1610_MaximumNumberOfVisiblePoints {
    /**
     * 给你一个点数组 points 和一个表示角度的整数 angle，你的位置是 location，其中 location=[posx, posy] 且points[i] = [xi, yi] 都表示 X-Y 平面上的整数坐标。
     *
     * 最开始，你面向东方进行观测。你 不能 进行移动改变位置，但可以通过 自转 调整观测角度。换句话说，posx 和 posy 不能改变。你的视野范围的角度用 angle 表示，
     * 这决定了你观测任意方向时可以多宽。设 d 为你逆时针自转旋转的度数，那么你的视野就是角度范围 [d - angle/2, d + angle/2] 所指示的那片区域。
     */
    public static int visiblePoints(List<List<Integer>> points, int angle, List<Integer> location) {
        int n = points.size();
        int a = location.get(0);
        int b = location.get(1);
        int zero = 0;
        double[] arr = new double[n << 1];
        int m = 0;
        for (int i = 0; i < n; i++) {
            int x = points.get(i).get(0) - a;
            int y = points.get(i).get(1) - b;
            if (x == 0 && y == 0) {
                zero++;
            } else {
                arr[m] = Math.toDegrees(Math.atan2(y, x));
                arr[m + 1] = arr[m] + 360;
                m += 2;
            }
        }
        Arrays.sort(arr, 0, m);
        int max = 0;
        for (int L = 0, R = 0; L < n; L++) {
            while (R < m && arr[R] - arr[L] <= angle) {
                R++;
            }
            max = Math.max(max, R - L);
        }
        return max + zero;
    }
}
