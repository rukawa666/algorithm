package com.rukawa.algorithm.classify.type4;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * create by hqh on 2022/9/27
 */
public class Code01_QueryHobby {
    /**
     * 数组为{3,2,2,3,1}，查询为(0,3,2)
     * 意思是数组里下标0～3范围上，有几个2？答案返回2
     * 假设给你一个数组nums
     * 对这个数组的查询非常频繁，都给出来
     * 请返回所有查询的结果
     */

    public static class QueryBox1 {
        int[] arr;
        public QueryBox1(int[] array) {
            arr = new int[array.length];
            for (int i = 0; i < array.length; i++) {
                arr[i] = array[i];
            }
        }

        public int query(int l, int r, int val) {
            int res = 0;
            for (; l <= r; l++) {
                if (arr[l] == val) {
                    res++;
                }
            }
            return res;
        }
    }

    public static class QueryBox2 {
        /**
         * 思路：
         *  nums：3,2,1,3,2,1,1,3,2
         *       0,1,2,3,4,5,6,7,8
         *  建立一张表：比如1这个数字在哪几个位置出现了
         *  1: {2,5,6}
         *  2: {1,4,8}
         *  3: {0,3,7}
         *  现在求1～4范围上有几个2，在1的记录中{2,5,6}进行二分查找
         */
        private HashMap<Integer, ArrayList<Integer>> cache;

        public QueryBox2(int[] arr) {
            cache = new HashMap<>();
            for (int i = 0; i < arr.length; i++) {
                if (!cache.containsKey(arr[i])) {
                    cache.put(arr[i], new ArrayList<>());
                }
                cache.get(arr[i]).add(i);
            }
        }

        public int query(int l, int r, int v) {
            if (!cache.containsKey(v)) {
                return 0;
            }
            ArrayList<Integer> array = cache.get(v);
            // 小于l的有a个  小于r的有b个  大于a且小于b的肯定b-a个
            int a = countLess(array, l);
            int b = countLess(array, r + 1);
            return b - a;
        }

        // 在有序数组中，用二分找到小于limit的数有几个
        // 也就是二分找到<limit最右的位置
        public int countLess(ArrayList<Integer> array, int limit) {
            int l = 0;
            int r = array.size() - 1;
            int mostRight = -1;
            while (l <= r) {
                int m = l + ((r - l) >> 1);
                if (array.get(m) < limit) {
                    mostRight = m;
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }
            return mostRight + 1;
        }
    }

    public static int[] generateRandomArray(int len, int value) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value) + 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        int len = 300;
        int value = 20;
        int testTimes = 1000;
        int queryTimes = 1000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(len, value);
            int N = arr.length;
            QueryBox1 box1 = new QueryBox1(arr);
            QueryBox2 box2 = new QueryBox2(arr);
            for (int j = 0; j < queryTimes; j++) {
                int a = (int) (Math.random() * N);
                int b = (int) (Math.random() * N);
                int L = Math.min(a, b);
                int R = Math.max(a, b);
                int v = (int) (Math.random() * value) + 1;
                if (box1.query(L, R, v) != box2.query(L, R, v)) {
                    System.out.println("Oops!");
                }
            }
        }
        System.out.println("test end");
    }
}
