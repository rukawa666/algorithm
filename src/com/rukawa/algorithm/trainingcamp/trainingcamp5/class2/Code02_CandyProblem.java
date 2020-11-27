package com.rukawa.algorithm.trainingcamp.trainingcamp5.class2;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-31 23:36
 * @Version：1.0
 */
public class Code02_CandyProblem {
    /**
     * 一群孩子做游戏，现在请你根据游戏得分来发糖果。
     * 要求如下:
     *    1.每个孩子不管得分多少，起码分到1个糖果。
     *    2.任意两个相邻的孩子之间，得分较多的孩子必须拿多一些的糖果。
     *    给定一个数组arr代表得分数组，请返回最少需要多少糖果。
     * 例如:arr=[1,2,2]，糖果 分配为[1,2,1]，即可满足要求且数量最少，所以返回 4。
     *【进阶】
     * 原题目中的两个规则不变，再加一条规则: 3.任意两个相邻的孩子之间如果得分一样，糖果数必须相同。
     * 给定一个数组 arr 代表得分数组，返回最少需要多少糖果。
     * 例如:arr=[1,2,2]，糖果分配为[1,2,2]，即可满足要求且数量最少，所以返回 5。
     *【要求】
     * arr 长度为 N，原题与进阶题都要求时间复杂度为 O(N)，额外空间复杂度为 O(1)。
     */
    public static int candy1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int index = nextMinIndex1(arr, 0);
        int res = rightCandies(arr, 0, index++);
        int lBase = 1;
        int next = 0;
        int rCandies = 0;
        int rBase = 0;
        while (index != arr.length) {
            if (arr[index] > arr[index - 1]) {
                res += ++lBase;
                index++;
            } else if (arr[index] < arr[index - 1]) {
                next = nextMinIndex1(arr, index - 1);
                rCandies = rightCandies(arr, index - 1, next++);
                rBase = next - index + 1;
                res += rCandies + (rBase > lBase ? -lBase : rBase);
                lBase = 1;
                index = next;
            } else {
                res += 1;
                lBase = 1;
                index++;
            }
        }
        return res;
    }

    public static int nextMinIndex1(int[] arr, int start) {
        for (int i = start; i != arr.length - 1; i++) {
            if (arr[i] < arr[i + 1]) {
                return i;
            }
        }
        return arr.length - 1;
    }

    public static int rightCandies(int[] arr, int left, int right) {
        int n = right - left + 1;
        return n + n * (n - 1) / 2;
    }

    public static int candy2(int[] arr) {
        return 0;
    }
}
