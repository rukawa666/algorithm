package com.rukawa.algorithm.trainingcamp.trainingcamp3.class7;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 23:07
 * @Version：1.0
 */
public class Code07_MaxPointsOnLine {
    /**
     * 给定两个数组arrx和arry，长度都为N。代表二维平面内有N个点，
     * 第i个点的x坐标和y坐标分别为arrx[i]和arry[i]，返回求一条直线最多能穿过多少个点？
     */

    public int maxPoints(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }

        if (points.length <= 2) {
            return points.length;
        }
        int n = points.length;
        // key:分子  value：分母表
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        int result = 0;
        for (int i = 0; i < n; i++) {
            map.clear();
            // 情况1：同一个点(点重合)
            int samePosition = 1;
            // 情况2：有多少个点是同一个x
            int sameX = 0;
            // 情况3：有多少个点事同一个y
            int sameY = 0;
            // 情况4：同斜率的点有多少个(a, b)的斜率 = (a.x - b.x) / (a.y - b.y)
            int line = 0;
            for (int j = i + 1; j < n; j++) {
                int x = points[j][0] - points[i][0];
                int y = points[j][1] - points[i][1];
                if (x == 0 && y == 0) {
                    samePosition++;
                } else if (x == 0) {
                    sameX++;
                } else if (y == 0) {
                    sameY++;
                } else {    // 有斜率
                    int gcb = gcd(x, y);
                    x /= gcb;
                    y /= gcb;
                    // 分子不存在，先构建分子
                    if (!map.containsKey(x)) {
                        map.put(x, new HashMap<>());
                    }
                    // 分母不存在，则先构建分母
                    if (!map.get(x).containsKey(y)) {
                        map.get(x).put(y, 0);
                    }

                    map.get(x).put(y, map.get(x).get(y) + 1);
                    line = Math.max(line, map.get(x).get(y));
                }
            }
            // 同x、同y、同斜率对比处最大值，然后加同一个点
            result = Math.max(result, Math.max(Math.max(sameX, sameY), line) + samePosition);
        }
        return result;

    }

    // 保证初始调用的时候，a和b不等于0
    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

}
