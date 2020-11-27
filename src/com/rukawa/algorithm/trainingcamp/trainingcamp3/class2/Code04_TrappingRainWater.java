package com.rukawa.algorithm.trainingcamp.trainingcamp3.class2;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-22 9:03
 * @Version：1.0
 */
public class Code04_TrappingRainWater {
    /**
     * 给定一个数组arr，已知其中所有的值都是非负的，将这个数组看做一个容器，请返回容器能装多少水
     * 比如，arr={3,1,2,5,2,4}，根据值画出的直方图就是容器的形状，该容器可以装下5格水
     * 再比如，arr={4,5,1,3,2}，该容器可以装下2格水
     */
    public static int water1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int[] leftMax = new int[N];
        leftMax[0] = arr[0];
        for (int i = 1; i < N; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], arr[i]);
        }

        int[] rightMax = new int[N];
        rightMax[N - 1] = arr[N - 1];
        for (int i = N - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], arr[i]);
        }
        int water = 0;
        for (int i = 1; i < N - 1; i++) {
            water += Math.max(Math.min(leftMax[i - 1], rightMax[i + 1]) - arr[i], 0);
        }
        return water;
    }

    public static int water2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int L = 1;
        int leftMax = arr[0];
        int R = N - 2;
        int rightMax = arr[N - 1];
        int water = 0;
        while (L <= R) {
            if (leftMax <= rightMax) {
                water += Math.max(0, leftMax - arr[L]);
                leftMax = Math.max(leftMax, arr[L++]);
            } else {
                water += Math.max(0, rightMax - arr[R]);
                rightMax = Math.max(rightMax, arr[R--]);
            }
        }
        return water;
    }

    // for test
    public static int[] generateRandomArray(int len, int value) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value) + 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        int len = 100;
        int value = 200;
        int testTimes = 100000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(len, value);
            int ans1 = water1(arr);
            int ans2 = water2(arr);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
