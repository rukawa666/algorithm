package com.rukawa.algorithm.trainingcamp.trainingcamp5.class1;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-31 23:36
 * @Version：1.0
 */
public class Problem04_GasStations {
    /**
     * N个加油站组成一个环形，给定两个长度都是N的非负数组oil和dis(N>1),oil[i]代表
     * 第i个加油站存的油可以跑多少千米，dis[i]表示第i个加油站到环中下一个加油站相隔
     * 多少千米。假设你有一辆邮箱足够大的车，初始时车里没有油。如果车从第i个加油站出发，
     * 最终可以回到这个加油站，那么第i个加油站就算良好出发点，否则就不算。请返回长度为
     * N的boolean类型数组res，res[i]代表第i个加油站是不是良好出发点。
     */

    public static boolean[] stations(int[] dis, int[] oil) {
        return null;
    }

    public static int lastIndex(int index, int size) {
        return index == 0 ? (size - 1) : index - 1;
    }

    public static int nextIndex(int index, int size) {
        return index == size - 1 ? 0 : (index + 1);
    }

    // for test
    public static boolean[] test(int[] dis, int[] oil) {
        if (dis == null || oil == null || dis.length < 2
                || dis.length != oil.length) {
            return null;
        }
        boolean[] res = new boolean[dis.length];
        for (int i = 0; i < dis.length; i++) {
            dis[i] = oil[i] - dis[i];
        }
        for (int i = 0; i < dis.length; i++) {
            res[i] = canWalkThrough(dis, i);
        }
        return res;
    }

    // for test
    public static boolean canWalkThrough(int[] arr, int index) {
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[index];
            if (sum < 0) {
                return false;
            }
            index = nextIndex(index, arr.length);
        }
        return true;
    }

    // for test
    public static void printArray(int[] dis, int[] oil) {
        for (int i = 0; i < dis.length; i++) {
            System.out.print(oil[i] - dis[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void printBooleanArray(boolean[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static int[] generateArray(int size, int max) {
        int[] res = new int[size];
        for (int i = 0; i < res.length; i++) {
            res[i] = (int) (Math.random() * max);
        }
        return res;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        int[] res = new int[arr.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(boolean[] res1, boolean[] res2) {
        for (int i = 0; i < res1.length; i++) {
            if (res1[i] != res2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("JJJ");
        int max = 20;
        for (int i = 0; i < 5000000; i++) {
            int size = (int) (Math.random() * 20) + 2;
            int[] dis = generateArray(size, max);
            int[] oil = generateArray(size, max);
            int[] dis1 = copyArray(dis);
            int[] oil1 = copyArray(oil);
            int[] dis2 = copyArray(dis);
            int[] oil2 = copyArray(oil);
            boolean[] res1 = stations(dis1, oil1);
            boolean[] res2 = test(dis2, oil2);
            if (!isEqual(res1, res2)) {
                printArray(dis, oil);
                printBooleanArray(res1);
                printBooleanArray(res2);
                System.out.println("what a fucking day!");
                break;
            }
        }
    }
}
