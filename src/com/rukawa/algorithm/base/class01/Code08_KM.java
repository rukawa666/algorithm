package com.rukawa.algorithm.base.class01;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-10 15:27
 * @Version：1.0
 */
public class Code08_KM {

    // 请保证arr中只有一种数出现了k次，其他数都出现了m次
    public static int onlyKTimes1(int[] arr, int k, int m) {
        int[] times = new int[32];
        for (int num : arr) {
            for (int i = 0; i < 32; i++) {
                times[i] += (num >> i) & 1;
            }
        }

        int res = 0;
        for (int i = 0; i < 32; i++) {
            // 说明这个数在i位置没有1
            if (times[i] % m == 0) {
                continue;
            }
            // i位置的次数不是m的整数倍，说明time[i]一定有出现k次的数
            res |= 1 << i;
        }
        return res;
    }

    /**
     * 一个数组中有一种数出现K次，其他数都出现M次，M>1, K<M
     * 找到出现了K次的数，如果没有找到出现K次的数，返回-1
     * 要求，额外空间复杂度O(1),时间复杂度O(N)
     * @param arr
     * @param k
     * @param m
     * @return
     */
    public static int onlyKTimes(int[] arr, int k, int m) {
        int[] t = new int[32];
        // t[0] 表示0位置的1出现了几次
        // t[i] 表示i位置的1出现了几次
        for (int num : arr) {
            for (int i = 0; i <= 31; i++) {
                t[i] += (num >> i) & 1;
            }
        }

        int res = 0;
        for (int i = 0; i < 32; i++) {
            // 说明这个数在i位置没有1
            if (t[i] % m == 0) {
                continue;
            }
            // 第i位置是1
            // i位置的次数不是m的整数倍，说明time[i]一定有出现k次的数
            if (t[i] % m == k) {
                res |= (1 << i);
            } else {
                return -1;
            }
        }

        if (res == 0) {
            int count = 0;
            for (int num : arr) {
                if (num == 0) {
                    count++;
                }
            }
            if (count != k) {
                return -1;
            }
        }


        return res;
    }

    // 对数器
    public static int test(int[] arr, int k, int m) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }

        for (int key : map.keySet()) {
            if (map.get(key) == k) {
                return key;
            }
        }
        return -1;
    }

    public static int[] randomArray(int kinds, int range, int k, int m) {
        // 该出现k次的数
        int kTimeNum = randomNumber(range);
        // k 出现的次数
        int times = Math.random() < 0.5 ? k : ((int) (Math.random() * (m - 1)) + 1);

        // m次数的数有几种
        int numKinds = (int) (Math.random() * kinds) + 2;
        // k * 1 + (numKinds - 1) * m
        int[] arr = new int[times + (numKinds - 1) * m];

        int index = 0;
        for (; index < times; index++) {
            arr[index] = kTimeNum;
        }
        // 还剩余几种数需要去填写
        numKinds--;
        HashSet<Integer> set = new HashSet<>();
        set.add(kTimeNum);
        while (numKinds != 0) {
            int curNum;
            // 保证数都是新的
            do {
                curNum = randomNumber(range);
            } while (set.contains(curNum));
            set.add(curNum);
            numKinds--;
            for (int i = 0; i < m; i++) {
                arr[index++] = curNum;
            }
        }

        // 打乱arr
        for (int i = 0; i < arr.length; i++) {
            // i 位置的数，随机的和j位置的数交换
            int j = (int) (Math.random() * arr.length); // 0~N-1
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        return arr;
    }

    // [-range, +range]
    public static int randomNumber(int range) {
        return ((int) (Math.random() * range) + 1) - ((int) (Math.random() * range) + 1);
    }

    public static void main(String[] args) {
        int kinds = 100;
        int range = 200;
        int testTime = 100000;
        int max = 9;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            // (Math.random() * max) 0~8
            int a = (int)(Math.random() * max) + 1;
            int b = (int)(Math.random() * max) + 1;
            int k = Math.min(a, b);
            int m = Math.max(a, b);
            // k < m
            if (k == m) {
                m++;
            }
            int[] arr = randomArray(kinds, range, k, m);

            int ans1 = test(arr, k, m);
            int ans2 = onlyKTimes(arr, k, m);
            if (ans1 != ans2) {
                System.out.println("出错了！");
            }
        }
        System.out.println("测试结束");
    }
}
