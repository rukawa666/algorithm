package com.rukawa.algorithm.trainingcamp.trainingcamp4.class3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-13 20:43
 * @Version：1.0
 */
public class Code05_FindKMajority {
    /**
     * 给定一个数组arr，如果有某个数出现次数超过了数组长度的一半，打印这个数，如果没有不打印
     *
     * 给定一个数组arr和整数k，arr长度为N，如果有某些数出现次数超过了N/K，打印这些数，
     * 如果没有不打印
     */
    public static void printHalfMajor(int[] arr) {
        /**
         * 思路：
         * 一个数组中删掉两个不同的数，如果不剩下数，说明没有任何一个数次数大于一半
         * 如果剩下数，再把这个数遍历一编，考察真实出现的次数
         */
        int cand = 0;
        int HP = 0;
        for (int i = 0; i < arr.length; i++) {
            if (HP == 0) {
                cand = arr[i];
                HP = 1;
            } else if (arr[i] == cand) {
                HP++;
            } else {
                HP--;
            }
        }
        if (HP == 0) {
            System.out.println("no such number.");
            return;
        }

        HP = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == cand) {
                HP++;
            }
        }
        if (HP > arr.length / 2) {
            System.out.println(cand);
        } else {
            System.out.println("no such number.");
        }
    }

    /**
     * 给定一个数组arr和整数k，arr长度为N，如果有某些数出现次数超过了N/K，打印这些数，
     * 如果没有不打印
     */
    public static void printKMajor(int[] arr, int k) {
        /**
         * 思路：
         *   1、超过N/2，只有1个候选(不可能2个数都超过N/2)，超过N/5，只有4个候选，超过N/K，只有K-1候选
         */
        if (k < 2) {
            System.out.println("the value of k is invalid.");
            return;
        }
        HashMap<Integer, Integer> cands = new HashMap<>();
        // 时间复杂度O(N * K)
        for (int i = 0; i < arr.length; i++) {
            if (cands.containsKey(arr[i])) {
                cands.put(arr[i], cands.get(arr[i]) + 1);
            } else {
                if (cands.size() == k - 1) {
                    allCandsMinusOne(cands);
                } else {
                    cands.put(arr[i], 1);
                }
            }
        }
        HashMap<Integer, Integer> reals = getReals(arr, cands);
        boolean hasPrint = false;
        for (Map.Entry<Integer, Integer> entry : cands.entrySet()) {
            Integer key = entry.getKey();
            if (reals.get(key) > arr.length / k) {
                hasPrint = true;
                System.out.print(key + " ");
            }
        }
        System.out.println(hasPrint ? "" : "no such number.");

    }

    // map中每个key的value-1，如果当前key的value为1，则直接删除当前key
    public static void allCandsMinusOne(HashMap<Integer, Integer> cands) {
        List<Integer> removeList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : cands.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            if (value == 1) {
                removeList.add(key);
            }
            cands.put(key, value - 1);
        }

        for (Integer removeKey : removeList) {
            cands.remove(removeKey);
        }
    }

    public static HashMap<Integer, Integer> getReals(int[] arr,
                                                     HashMap<Integer, Integer> cands) {
        HashMap<Integer, Integer> reals = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            int curNum = arr[i];
            if (cands.containsKey(curNum)) {
                if (reals.containsKey(curNum)) {
                    reals.put(curNum, reals.get(curNum) + 1);
                } else {
                    reals.put(curNum,1);
                }
            }
        }
        return reals;
    }

    public static void main(String[] args) {
        int[] arr = { 1, 2, 3, 1, 1, 2, 1 };
        printHalfMajor(arr);
        int K = 4;
        printKMajor(arr, K);
    }
}
