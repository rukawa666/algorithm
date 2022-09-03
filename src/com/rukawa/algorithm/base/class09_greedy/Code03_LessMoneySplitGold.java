package com.rukawa.algorithm.base.class09_greedy;

import java.util.PriorityQueue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-24 0:06
 * @Version：1.0
 */
public class Code03_LessMoneySplitGold {
    /**
     * 一块金条切成两半，是需要花费和长度数值一样的铜板的
     * 比如长度为20的金条，不管怎么切，都要花费20个铜板。一群人想整分整块金条，怎么分最省铜板？
     *
     * 例如，给定数组{10,20,30},代表一共三个人，整块金条长度为60，金条要切分成10,20,30三个部分。
     * 如果先把长度60的金条分成10和50，花费60；再把长度为50的金条分成20和30，花费50；一共花费110铜板。
     * 但是如果把长度为60的金条分为30和30，花费60；再把长度30金条分为10和20，花费30，一共花费90铜板。
     *
     * 输入一个数组，返回分割的最小代价
     */
    // 暴力方法
    public static int lessMoney01(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr, 0);
    }

    public static int process(int[] arr, int pre) {
        if (arr.length == 1) {
            return pre;
        }

        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                ans = Math.min(ans, process(copyAndMergeTwo(arr, i, j), pre + arr[i] + arr[j]));
            }
        }
        return ans;
    }

    public static int[] copyAndMergeTwo(int[] arr, int i, int j) {
        int[] ans = new int[arr.length - 1];
        int ansi = 0;
        for (int arri = 0; arri < arr.length; arri++) {
            if (arri != i && arri != j) {
                ans[ansi++] = arr[arri];
            }
        }
        ans[ansi] = arr[i] + arr[j];
        return ans;
    }

    // 贪心算法
    /**
     * 哈夫曼编码问题
     * Huffman编码的基本思想是用短的编码表示出现频率高的字符，用长的编码表示出现频率低的字符，这使得编码之后的字符串的平均长度、长度的期望值
     * 降低，从而达到压缩的效果
     *
     * 哈夫曼的构建过程：比如对一个字符串的频率统计的结果如下
     * character  frequency
     *   a           5
     *   b           9
     *   c           12
     *   d           13
     *   e           16
     *   f           45
     * 将每个元素构建成一个节点，即只有一个元素的树，并构建小根堆，包含所有节点，该算法用了最小堆来作为优先队列
     * 选取两个权重最小的节点，并添加一个权值为5+9=14的节点，作为它们的父节点，并更新最小堆。
     *
     *               100
     *           /        ｜
     *         45(f       55
     *               /           ｜
     *              25            30
     *           /    ｜        /   |
     *        12(c)  13(d)    14    16(e)
     *                     /    |
     *                   5(a)   9(b)
     *
     */

    public static int lessMoney02(int[] arr) {
        PriorityQueue<Integer> pQ = new PriorityQueue<>();
        for (int i = 0; i < arr.length; i++) {
            pQ.add(arr[i]);
        }

        int sum = 0;
        int cur = 0;
        while (pQ.size() > 1) {
            cur = pQ.poll() + pQ.poll();
            pQ.add(cur);
            sum += cur;
        }
        return sum;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 6;
        int maxValue = 1000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            if (lessMoney01(arr) != lessMoney02(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
