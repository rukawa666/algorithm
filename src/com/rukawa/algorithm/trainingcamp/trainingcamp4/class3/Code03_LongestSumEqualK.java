package com.rukawa.algorithm.trainingcamp.trainingcamp4.class3;

import java.util.HashMap;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-13 20:43
 * @Version：1.0
 */
public class Code03_LongestSumEqualK {
    /**
     * 给定一棵二叉树的头节点head，和一个数k
     * 路径的定义：
     * 可以从任何一个点出发，但是只能往下走，往下走到任何节点停止
     * 返回路径累加和为k的所有路径中，最长的路径最多有多少个节点？
     */

    public static int res = 0;  // 收集累加和为k的，最长路径有多少个节点

    public static int longest(Node head, int K) {
        /**
         * 思路：
         *  方法1、树形DP，能解决，可能性比较多
         *  方法2、数组上的累加和为k的最长子数组问题 √
         *
         *  方法2思路：
         *  1、子数组以i位置结尾的情况下，累加和为k的最长子数组是多长
         */
        res = 0;

        // key 前缀和
        // value 该前缀和最早出现在哪一层
        // sumMap 只维持，从头节点出发到当前节点这条路径上的前缀和
        HashMap<Integer, Integer> sumMap = new HashMap<>();
        sumMap.put(0, -1);
        process(head, 0, 0, K, sumMap);
        return res;
    }

    // 节点X在level层，从头节点到X的父节点行形成的累计和是preSum
    // 从头节点到X的路径上，每一个前缀和都存在sumMap的key中，记录的是该前缀和最早出现的层数
    // 求出必须以X节点结尾的，累加和是K的所有路径中，含有最多的节点是多少？
    // 并看看能不能更新全局res
    public static void process(Node X, int level, int preSum, int K, HashMap<Integer, Integer> sumMap) {
        if (X != null) {
            // 头节点出发到当前节点的总的前缀和
            int allSum = preSum + X.value;

            // 总的前缀和是100，目标K是20，所以80的前缀和最早出现在哪一层
            if (sumMap.containsKey(allSum - K)) {
                // 当前层-前缀和所在的层数
                res = Math.max(res, level - sumMap.get(allSum - K));
            }
            // 求的是最长的路径的节点个数，记录最早出现的位置
            if (!sumMap.containsKey(allSum)) {
                sumMap.put(allSum, level);
            }
            process(X.left, level + 1, allSum, K, sumMap);
            process(X.right, level + 1, allSum, K, sumMap);
            // 如果allSum最早出现在当前层，要清除印记
            // 如果if (!sumMap.containsKey(allSum)) {}这一条件成立，下面条件必然成立
            if (sumMap.get(allSum) == level) {
                sumMap.remove(allSum);
            }
        }
    }

    public static class Node {
        int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static void main(String[] args) {
        //                   3
        //           -2             3
        //        1      4      5      -7
        //       3 5   2 -5  -5  -3   1   5
        int K = 0;
        Node head = new Node(3);
        head.left = new Node(-2);
        head.left.left = new Node(1);
        head.left.right = new Node(4);
        head.left.left.left = new Node(3);
        head.left.left.right = new Node(5);
        head.left.right.left = new Node(2);
        head.left.right.right = new Node(5);

        head.right = new Node(3);
        head.right.left = new Node(5);
        head.right.right = new Node(-7);
        head.right.left.left = new Node(-5);
        head.right.left.right = new Node(-3);
        head.right.right.left = new Node(1);
        head.right.right.right = new Node(5);

        System.out.println(longest(head, K));

    }


}
