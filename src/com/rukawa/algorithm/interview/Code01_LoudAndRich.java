package com.rukawa.algorithm.interview;

import java.util.ArrayList;

/**
 * create by hqh on 2023/1/5
 */
public class Code01_LoudAndRich {
    // leetcode:851 https://leetcode.cn/problems/loud-and-rich/
    /**
     * 有一组N个人作为实验对象，从0到N-1编号，其中每个人都有不同数目的钱，以及不同程序的安静值（quietness）
     * 为了方便起见，我们将编号位x的人简称为"person x"。
     * 给你一个数组richer，其中richer[i]=[ai,bi]表示person ai比person bi更有钱
     * 另外给你一个整数数组quiet，其中quiet[i]是person i的安静值
     * richer中所给出的数据逻辑自洽
     * 也就是说，在person x比person y更有钱的同时，不会出现person y比person x更有钱的情况
     * 现在，返回一个整数数组answer作为答案，其中answer[x]=y的前提是：
     * 在所有拥有的钱肯定不少于person x的人中，person y是最安静的人（也就是安静值quiet[y]最小的人）
     */
    // richer[i]={a,b} a比b更有钱
    // quiet[i] = k，这个人安静值是k
    public int[] loudAndRich(int[][] richer, int[] quiet) {
        int N = quiet.length;
        // 有三条边
        // a -> b
        // a -> c
        // b -> c
        // 从a出发的点能的点
        // a : b c
        // b : c
        ArrayList<ArrayList<Integer>> nextS = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            nextS.add(new ArrayList<>());
        }
        // 入度
        int[] degree = new int[N];
        for (int[] r : richer) {
            nextS.get(r[0]).add(r[1]);
            degree[r[1]]++;
        }
        // 所有入度为0的点，入队列
        int[] zeroQueue = new int[N];
        int l = 0;
        int r = 0;
        for (int i = 0; i < N; i++) {
            if (degree[i] == 0) {
                zeroQueue[r++] = i;
            }
        }
        // ans[i]=j，比i有钱的所有人里面，j最安静
        int[] ans = new int[N];
        for (int i = 0; i < N; i++) {
            ans[i] = i;
        }

        while (l < r) { // 如果队列不为空
            // 弹出一个入度位0的点
            int cur = zeroQueue[l++];
            // 消除当前cur的影响
            for (int next : nextS.get(cur)) {
                // cur：比cur有钱，最安静的 ans[cur]

                if (quiet[ans[next]] > quiet[ans[cur]]) {
                    ans[next] = ans[cur];
                }
                // 入度--
                if (--degree[next] == 0) {
                    zeroQueue[r++] = next;
                }
            }
        }
        return ans;
    }
}
