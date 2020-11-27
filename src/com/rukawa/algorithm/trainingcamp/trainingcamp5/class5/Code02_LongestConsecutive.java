package com.rukawa.algorithm.trainingcamp.trainingcamp5.class5;

import java.util.HashMap;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-11-12 8:06
 * @Version：1.0
 */
public class Code02_LongestConsecutive {
    /**
     * 给定无序数组arr，返回其中最长的连续序列的长度
     * 举例：
     *  arr=[100, 4, 200, 1, 3, 2]，最长的连续序列为[1, 2, 3, 4]
     * 所以返回4
     */

    public static int longestConsecutive(int[] arr) {
        /**
         * [100, 1, 3, 4, 0, 2, 17]
         * 方法1：
         *    1、创建两个map，一个map头，一个map尾
         *    2、任何一个连续区间，头记录在map头，尾记录在map尾
         *    3、每次进来一个数字去查找，arr[i]-1是否在map尾中，arr[i]+1是否在map头中
         *      如果有就合并，长度加1；删除合并头对应的尾，合并尾对应的头
         *    4、最后返回map中value最大的值
         *
         *
         * 方法2：
         *    1、建立一张map表，代表key所在的连续区间一共有多少长度
         *    2、map还有另外一个功能，所有的key不删除，下次遇到map有这个key，直接跳过
         *
         * 流程：[100, 1, 3, 0, 4, 2, 17]
         *    1、100 -> map.put(100, 1);
         *    2、1   -> map.put(1, 2);  插入0，此时更新map.put(1, 1)的value++
         *    3、3   -> map.put(3, 2);  插入4，此时更新map.put(3, 1)的value++
         *    4、0   -> map.put(0, 5);  0是第一次插入，此时有1结尾的数，更新为map(0, 2)
         *                              2插入的时候，更新为map(0, 5)
         *    5、4   -> map.put(4, 5);  4是第一次插入，此时有3开头的数，更新为map(4, 2)
         *                              2插入的时候，更新为map为(4, 5)
         *    6、2   -> map.put(2, 1);  2是第一次插入，此时有1结尾的数，和3开头的数字，总共2+2+1=5
         *                             需要去更新0和4的记录为5
         *
         *    此时map(1, 2), map(2, 1), map(3, 2)为脏数据，不去参考value值，只判断key是否存在
         */
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int max = 1;
        // 不区分开头和结尾
        // （key, value）
        // key所在的连续区域，这个区域一共几个数， value
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {  // 从左到右依次遍历每一个数字
            if (!map.containsKey(arr[i])) { // 数字第一次，才去处理，否则直接跳过
                map.put(arr[i], 1);
                if (map.containsKey(arr[i] - 1)) {  // 是否有arr[i]-1的结尾
                    max = Math.max(max, merge(map, arr[i] - 1, arr[i]));
                }

                if (map.containsKey(arr[i] + 1)) {  // 是否有arr[i]+1的开头
                    max = Math.max(max, merge(map, arr[i], arr[i] + 1));
                }
            }
        }
        return max;
    }

    //
    public static int merge(HashMap<Integer, Integer> map, int preRangeEnd, int curRangeStart) {
        // map.put(1, 2) preRangeEnd=1 map.put(2, 1) curRangeStart=2
        // map.put(2, 3) preRangeEnd=2 map.put(3, 2) curRangeStart=3
        int preRangeStart = preRangeEnd - map.get(preRangeEnd) + 1; // 1-2+1=0  2-3+1=0
        int curRangeEnd = curRangeStart + map.get(curRangeStart) - 1;   // 2+1-1=2  3+2-1=4
        int len = curRangeEnd - preRangeStart + 1;  // 2-0+1=3  4-0+1=5
        // 只更新开头和结尾的东西，中间的内容不处理
        map.put(preRangeStart, len); // map.put(0, 3)  map.put(0, 5)
        map.put(curRangeEnd, len);  // map.put(2, 3)  map.put(4, 5)
        return len;
    }
}
