package com.rukawa.algorithm.types.advanced;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @name: Code_D1_QueryHobby
 * @description: 描述类的用途
 * @date: 2021/8/10 11:12
 * @auther: hanqinghai
 */
public class Code_D1_QueryHobby {
    /**
     * 数组为{3, 2, 2, 3, 1},查询为(0, 3, 2)
     * 意思是在数组里下标0～3这个范围上，有几个2？答案返回2
     * 假设给你一个数组arr，对这个数组的查询非常频繁，都给出来
     * 请返回所有查询的结果
     */
    public static class QueryBox1 {
        private int[] arr;

        public QueryBox1(int[] array) {
            arr = new int[array.length];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = array[i];
            }
        }

        public int query(int L, int R, int V) {
            int ans = 0;
            for (; L <= R; L++) {
                if (arr[L] == V) {
                    ans++;
                }
            }
            return ans;
        }
    }

    public static class QueryBox2 {
        private HashMap<Integer, ArrayList<Integer>> maps;

        // 统计每个字符出现的位置且有序
        public QueryBox2(int[] array) {
            maps = new HashMap<>();
            for (int i = 0; i < array.length; i++) {
                if (!maps.containsKey(array[i])) {
                    maps.put(array[i], new ArrayList<>());
                }
                maps.get(array[i]).add(i);
            }
        }

        // 在V数组中找离L最右的位置
        // 在V数组中找离R最右的位置
        public int query(int L, int R, int V) {
            if (!maps.containsKey(V)) {
                return 0;
            }
            ArrayList<Integer> values = maps.get(V);
            // 查询<L的下标有几个
            int a = countLess(values, L);
            // 查询<R+1的下标有几个
            int b = countLess(values, R + 1);
            return b - a;
        }

        // 在有序数组中，用二分法得出<limit的数有多少个
        // 也就是用二分法，找到<limit的数中最右的位置
        private int countLess(ArrayList<Integer> arr, int limit) {
            int L = 0;
            int R = arr.size() - 1;
            int mostRight = -1;
            while (L <= R) {
                int mid = L + ((R - L) >> 1);
                if (arr.get(mid) < limit) {
                    mostRight = mid;
                    L = mid + 1;
                } else {
                    R = mid - 1;
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
