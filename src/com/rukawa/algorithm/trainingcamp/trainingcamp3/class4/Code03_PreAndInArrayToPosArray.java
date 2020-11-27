package com.rukawa.algorithm.trainingcamp.trainingcamp3.class4;

import java.util.HashMap;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-26 7:49
 * @Version：1.0
 */
public class Code03_PreAndInArrayToPosArray {
    /**
     * 已知一个二叉树中没有重复节点，并且给定了这颗树的中序遍历和先序遍历数组，返回后序遍历数组
     *
     * 比如给定：
     * int[] pre = {1,2,4,5,3,6,7};
     * int[] in = {4,2,5,1,6,3,7};
     * 返回：
     * int[] pos = {4,5,2,6,7,3,1}
     */
    public static class Node {

        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static int[] preInToPos1(int[] pre, int[] in) {
        if (pre == null || in == null || pre.length != in.length) {
            return null;
        }
        int N = pre.length;
        int[] pos = new int[N];
        process1(pre, 0, N - 1, in, 0, N - 1, pos, 0, N - 1);
        return pos;
    }

    public static void process1(int[] pre, int L1, int R1,
                                int[] in, int L2, int R2,
                                int[] pos, int L3, int R3) {
        if (L1 > R1) {
            return;
        }
        if (L1 == R1) {
            pos[L3] = pre[L1];
            return;
        }
        pos[R3] = pre[L1];
        int mid = L2;
        for (; mid <= R2; mid++) {
            if (in[mid] == pre[L1]) {
                break;
            }
        }
        int leftSize = mid - L2;
        process1(pre, L1 + 1, L1 + leftSize, in, L2, mid - 1, pos, L3, L3 + leftSize - 1);
        process1(pre, L1 + leftSize + 1, R1, in, mid, R2, pos, L3 + leftSize, R3 - 1);
    }


    public static int[] preInToPos2(int[] pre, int[] in) {
        if (pre == null || in == null || pre.length != in.length) {
            return null;
        }
        int N = pre.length;
        HashMap<Integer, Integer> inMap = new HashMap<>();
        for (int i = 0; i < N; i++) {
            inMap.put(in[i], i);
        }
        int[] pos = new int[N];
        process2(pre, 0, N - 1, in, 0, N - 1, pos, 0, N - 1, inMap);
        return pos;
    }

    public static void process2(int[] pre, int L1, int R1,
                                int[] in, int L2, int R2,
                                int[] pos, int L3, int R3,
                                HashMap<Integer, Integer> inMap) {
        if (L1 > R1) {
            return;
        }
        if (L1 == R1) {
            pos[L3] = pre[L1];
            return;
        }

        pos[R3] = pre[L1];
        int mid = inMap.get(pre[L1]);
        int leftSize = mid - L2;
        process2(pre, L1 + 1, L1 + leftSize, in, L2, mid - 1, pos, L3, L3 + leftSize - 1, inMap);
        process2(pre, L1 + leftSize + 1, R1, in, mid, R2, pos, L3 + leftSize, R3 - 1, inMap);
    }
}
