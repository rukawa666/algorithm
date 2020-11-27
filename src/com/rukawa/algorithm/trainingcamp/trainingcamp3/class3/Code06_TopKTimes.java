package com.rukawa.algorithm.trainingcamp.trainingcamp3.class3;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-25 21:31
 * @Version：1.0
 */
public class Code06_TopKTimes {
    /**
     * 给定一个字符串组成的数组String[] strs,给定一个正数K，返回词频最大的前k个字符串，
     * 假设结果是唯一的
     */
    public static class Node {
        public String str;
        public int times;

        public Node(String str, int times) {
            this.str = str;
            this.times = times;
        }
    }

    public static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.times - o2.times;
        }
    }

    public static void printTopKAndRank(String[] strS, int k) {
        if (strS == null || strS.length == 0 || k < 1) {
            return;
        }

        HashMap<String, Integer> map = new HashMap<>();
        for (String str : strS) {
            if (!map.containsKey(str)) {
                map.put(str, 1);
            } else {
                map.put(str, map.get(str) + 1);
            }
        }
        k = Math.min(strS.length, k);
        PriorityQueue<Node> heap = new PriorityQueue<>(new NodeComparator());
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            Node node = new Node(entry.getKey(), entry.getValue());
            if (heap.size() < k) {
                heap.add(node);
            } else {
                if (heap.peek().times < node.times) {
                    heap.poll();
                    heap.add(node);
                }
            }
        }

        while (!heap.isEmpty()) {
            System.out.print(heap.poll().str + " ");
        }
    }

    public static String[] generateRandomArray(int len, int max) {
        String[] res = new String[len];
        for (int i = 0; i != len; i++) {
            res[i] = String.valueOf((int) (Math.random() * (max + 1)));
        }
        return res;
    }

    public static void printArray(String[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        String[] arr1 = { "A", "B", "A", "C", "A", "C", "B", "B", "K" };
        printTopKAndRank(arr1, 2);
        System.out.println();
        System.out.println("+++++++++++++++++++++++++++++++++++");
        String[] arr2 = generateRandomArray(50, 10);
        int topK = 3;
        printArray(arr2);
        printTopKAndRank(arr2, topK);

    }
}
