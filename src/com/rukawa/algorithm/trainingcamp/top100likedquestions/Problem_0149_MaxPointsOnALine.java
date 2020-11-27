package com.rukawa.algorithm.trainingcamp.top100likedquestions;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 11:07
 * @Version：1.0
 */
public class Problem_0149_MaxPointsOnALine {
    /**
     * 直线上最多的点数
     * 给定一个二维平面，平面上有 n 个点，求最多有多少个点在同一条直线上。
     *
     * 示例 1:
     * 输入: [[1,1],[2,2],[3,3]]
     * 输出: 3
     * 解释:
     * ^
     * |
     * |        o
     * |     o
     * |  o  
     * +------------->
     * 0  1  2  3  4
     *
     * 示例 2:
     * 输入: [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]]
     * 输出: 4
     * 解释:
     * ^
     * |
     * |  o
     * |     o        o
     * |        o
     * |  o        o
     * +------------------->
     * 0  1  2  3  4  5  6
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
        for (int i = 0; i < n; i++) {   // i位置的点开始
            map.clear();
            // 情况1：同一个点(点重合)
            int samePosition = 1;
            // 情况2：有多少个点是同一个x
            int sameX = 0;
            // 情况3：有多少个点事同一个y
            int sameY = 0;
            // 情况4：同斜率的点有多少个(a, b)的斜率 = (a.x - b.x) / (a.y - b.y)
            int line = 0;
            for (int j = i + 1; j < n; j++) { // 判断和i的点的性质，只需要判断i+1位置后面的点即可
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
                    if (!map.containsKey(x)) {
                        map.put(x, new HashMap<>());
                    }

                    if (!map.get(x).containsKey(y)) {
                        map.get(x).put(y, 0);
                    }

                    map.get(x).put(y, map.get(x).get(y) + 1);
                    line = Math.max(line, map.get(x).get(y));
                }
            }
            result = Math.max(result, Math.max(Math.max(sameX, sameY), line) + samePosition);
        }
        return result;

    }

    // 保证初始调用的时候，a和b不等于0
    // 如果约最大公约数 O(1)  辗转相除法求最大公约数
    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }
}
