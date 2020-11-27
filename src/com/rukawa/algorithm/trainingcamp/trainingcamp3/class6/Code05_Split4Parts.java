package com.rukawa.algorithm.trainingcamp.trainingcamp3.class6;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-30 7:49
 * @Version：1.0
 */
public class Code05_Split4Parts {
    /**
     * 给定一个正数数组arr，返回该数组能不能分为4个部分，并且每个部分的累加和相等，切分位置的数不要
     *
     * 例如：
     *  arr={3,2,4,1,4,9,5,10,1,2,2} 返回true
     *  三个切割点下标分别为2,5,7切出的四个子数组分别为[3,2]、[1,4]、[5]、[1,2,2]
     *  累加和都是5
     */
    public static boolean canSplits1(int[] arr) {
        if (arr == null || arr.length < 7) {
            return false;
        }
        // key -> 某一个累加和，value -> 出现的位置
        HashMap<Integer, Integer> map = new HashMap<>();
        int sum = arr[0];
        for (int i = 1; i < arr.length; i++) {
            map.put(sum, i);
            sum += arr[i];
        }
        int leftSum = arr[0]; // 第一刀左侧的累加和
        // 第一刀的位置，第一刀的位置最多只能到N-6位置，因为第一刀后面还要切两刀，还有三个区域还要算累加和
        for (int s1 = 1; s1 < arr.length - 5; s1++) {   // s1是第一刀的位置
            int checkSum = leftSum * 2 + arr[s1];
            if (map.containsKey(checkSum)) {
                // 第二刀的位置
                int s2 = map.get(checkSum);
                checkSum += leftSum + arr[s2];
                if (map.containsKey(checkSum)) {
                    // 第三刀的位置
                    int s3 = map.get(checkSum);
                    if (checkSum + leftSum + arr[s3] == sum) {
                        return true;
                    }
                }
            }
            leftSum += arr[s1];
        }
        return false;
    }

    /**
     * 此方法不好
     * @param arr
     * @return
     */
    public static boolean canSplits2(int[] arr) {
        if (arr == null || arr.length < 7) {
            return false;
        }
        HashSet<String> set = new HashSet<String>();
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        int leftSum = arr[0];
        for (int i = 1; i < arr.length - 1; i++) {
            set.add(String.valueOf(leftSum) + "_" + String.valueOf(sum - leftSum - arr[i]));
            leftSum += arr[i];
        }
        int l = 1;
        int lsum = arr[0];
        int r = arr.length - 2;
        int rsum = arr[arr.length - 1];
        while (l < r - 3) {
            if (lsum == rsum) {
                String lkey = String.valueOf(lsum * 2 + arr[l]);
                String rkey = String.valueOf(rsum * 2 + arr[r]);
                if (set.contains(lkey + "_" + rkey)) {
                    return true;
                }
                lsum += arr[l++];
            } else if (lsum < rsum) {
                lsum += arr[l++];
            } else {
                rsum += arr[r--];
            }
        }
        return false;
    }


    public static int[] generateRandomArray() {
        int[] res = new int[(int) (Math.random() * 10) + 7];
        for (int i = 0; i < res.length; i++) {
            res[i] = (int) (Math.random() * 10) + 1;
        }
        return res;
    }

    public static void main(String[] args) {
        int testTime = 3000000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray();
            if (canSplits1(arr) ^ canSplits2(arr)) {
                System.out.println("Error");
            }
        }
        System.out.println("test end...");
    }
}
