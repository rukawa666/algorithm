package com.rukawa.algorithm.types.guessTheSolution;

import java.util.Map;
import java.util.TreeMap;

/**
 * @className: Code03_SnacksWays
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/13 0013 23:28
 **/
public class Code03_SnacksWays {

    /**
     * 牛牛家里一共有n袋零食，第i袋零食体积为v[i]，背包容量是w
     * 牛牛想知道在总体积不超过背包容量的情况下，一共有多少种零食放法，体积为0也算是一种放法
     * 1 <= n <= 30, 1 <= w <= 2*10^9
     * v[i] (0 <= v[i] <= 10^9)
     *
     * 思路：告诉分治的应用场景，剩下的值都很大，除了arr的长度不大，整个玩暴力递归拿不下，坚定要去搞整合逻辑
     */

    //但是用的分治的方法
    //这是牛客的测试链接：https://www.nowcoder.com/questionTerminal/d94bb2fa461d42bcb4c0f2b94f5d4281
    public static long ways(int[] arr, int bag) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0] <= bag ? 2 : 1;
        }
        int mid = (arr.length - 1) >> 1;
        TreeMap<Long, Long> lMap = new TreeMap<>();
        long ways = func(arr, 0, mid, 0, bag, lMap);
        TreeMap<Long, Long> rMap = new TreeMap<>();
        ways += func(arr, mid + 1, arr.length - 1, 0, bag, rMap);
        // 右侧的前缀和表
        TreeMap<Long, Long> rPre = new TreeMap<>();
        long pre = 0;
        for (Map.Entry<Long, Long> entry : rMap.entrySet()) {
            pre += entry.getValue();
            rPre.put(entry.getKey(), pre);
        }

        for (Map.Entry<Long, Long> entry : lMap.entrySet()) {
            long lWeight = entry.getKey();
            long lWays = entry.getValue();
            Long floor = rPre.floorKey(bag - lWeight);
            if (floor != null) {
                long rWays = rPre.get(floor);
                ways += rWays * lWays;
            }
        }
        // 体积为0的时候，不进入map表,最后单独算
        return ways + 1;
    }

    // 从index出发，到end结束
    // 之前的选择，已经形成的累加和 sum
    // 零食[index...end]自由选择，出来的所有累加和，不能超过bag，每一种累加和对应的方法数，填在map里
    // 最后不能什么货都没选
    // [3, 3, 3, 3] bag = 6
    //  0  1  2  3
    //  -  -  -  -  0 -> (0 : 1) 0~3号零食都不要，map中加一条记录(0:1)
    //  -  -  -  $  3 -> (0 : 1) (3 : 1)，map加一条记录(3:1)
    //  -  -  $  -  3 -> (0 : 1) (3 : 2)，map中3的记录的次数改为2
    public static long func(int[] arr, int index, int end, long sum, long bag, TreeMap<Long, Long> map) {
        if (sum > bag) {    // 中途搜集到sum>bag,后面不用尝试
            return 0;
        }
        // sum<=bag
        if (index > end) {  // 所有商品自由选择完成
            if (sum != 0) { // 表示选了货
                if (!map.containsKey(sum)) {
                    map.put(sum, 1L);
                } else {
                    map.put(sum, map.get(sum) + 1);
                }
                // 此时出来一种新的方法
                return 1;
            } else {
                // 体积为0的时候，不进入map表
                return 0;
            }
        }

        // sum<=bag 且 index <= end(表示还有货)
        // 1、不要当前index的货
        long ways = func(arr, index + 1, end, sum, bag, map);

        // 2、要当前index的货
        ways += func(arr, index + 1, end, sum + arr[index], bag, map);
        return ways;
    }
}
