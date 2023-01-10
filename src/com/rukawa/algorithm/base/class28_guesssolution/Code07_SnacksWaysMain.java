package com.rukawa.algorithm.base.class28_guesssolution;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

/**
 * create by hqh on 2023/1/10
 */
public class Code07_SnacksWaysMain {
    /**
     * 牛牛家里一共有n袋零食，第i袋零食体积为v[i]，背包容量是w
     * 牛牛想知道在总体积不超过背包容量的情况下，一共有多少种零食放法，体积为0也算是一种放法
     * 1 <= n <= 30, 1 <= w <= 2*10^9
     * v[i] (0 <= v[i] <= 10^9)
     *
     * 思路：告诉分治的应用场景，剩下的值都很大，除了arr的长度不大，整个玩暴力递归拿不下，坚定要去搞整合逻辑
     */
    // 这是牛客的测试链接：https://www.nowcoder.com/questionTerminal/d94bb2fa461d42bcb4c0f2b94f5d4281

    public static long ways1(int[] v, int w) {
        if (v == null || v.length == 0) {
            return 0;
        }
        if (v.length == 1) {
            return v[0] <= w ? 2 : 1;
        }
        int mid = (v.length - 1) >> 1;
        TreeMap<Long, Long> leftMap = new TreeMap<>();
        long ways = func1(v, 0, mid, 0, w, leftMap);

        TreeMap<Long, Long> rightMap = new TreeMap<>();
        ways += func1(v, mid + 1, v.length - 1, 0, w, rightMap);

        // 右侧的前缀和表
        TreeMap<Long, Long> rightPreMap = new TreeMap<>();
        long pre = 0;
        for (Map.Entry<Long, Long> entry : rightMap.entrySet()) {
            pre += entry.getValue();
            rightPreMap.put(entry.getKey(), pre);
        }

        for (Map.Entry<Long, Long> entry : leftMap.entrySet()) {
            long lWeight = entry.getKey();
            long lWays = entry.getValue();
            Long floor = rightPreMap.floorKey(w - lWeight);
            if (floor != null) {
                long rWays = rightPreMap.get(floor);
                ways += lWays * rWays;
            }
        }
        // 体积为0的时候，不进入map表,最后单独算
        return ways + 1;
    }

    // 从index出发，到end结束
    // 之前的选择，已经形成的累加和sum
    // 零食[index...end]自由选择，出来的所有体积累加和，不能超过bag，填写在map
    // 最后不能什么货都没选
    // [3,3,3,3] bag = 6
    //  0,1,2,3
    //  - - - -  0 -> (0:1)
    //  - - - &  3 -> (0,1),(3,1)
    //  - - & -  3 -> (0,1),(3,2)
    public static long func1(int[] v, int index, int end, long sum, long bag, TreeMap<Long, Long> map) {
        // 中途收到的sum一旦大于bag，后面不用尝试
        if (sum > bag) {
            return 0;
        }
        // sum <= bag
        if (index > end) { // 所有商品自由选择完了
            if (sum != 0) { // 表示选了货
                if (!map.containsKey(sum)) {
                    map.put(sum, 1L);
                } else {
                    map.put(sum, map.get(sum + 1));
                }
                return 1;  // 此时出来一种新的方法
            } else {
                return 0;  // 体积为0的时候，不进入map表
            }
        }

        // sum <= bag 并且 index <= end （还有货）
        // 1、不要当前index的货
        long way1 = func1(v, index + 1, end, sum, bag, map);
        // 2、要当前index的货
        long way2 = func1(v, index + 1, end, sum + v[index], bag, map);
        return way1 + way2;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            int n = (int) in.nval;
            in.nextToken();
            int bag = (int) in.nval;
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                in.nextToken();
                arr[i] = (int) in.nval;
            }
            long ways = ways1(arr, bag);
            out.println(ways);
            out.flush();
        }
    }
}
