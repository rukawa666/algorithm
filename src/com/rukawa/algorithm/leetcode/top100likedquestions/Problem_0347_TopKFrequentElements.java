package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-11 0:44
 * @Version：1.0
 */
public class Problem_0347_TopKFrequentElements {
    /**
     * 前 K 个高频元素
     * 给定一个非空的整数数组，返回其中出现频率前 k 高的元素。
     * 示例 1:
     * 输入: nums = [1,1,1,2,2,3], k = 2
     * 输出: [1,2]
     * 示例 2:
     * 输入: nums = [1], k = 1
     * 输出: [1]
     *
     * 提示：
     * 你可以假设给定的 k 总是合理的，且 1 ≤ k ≤ 数组中不相同的元素的个数。
     * 你的算法的时间复杂度必须优于 O(n log n) , n 是数组的大小。
     * 题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的。
     * 你可以按任意顺序返回答案。
     */
    public int[] topKFrequent01(int[] nums, int k) {
        HashMap<Integer, Node> map = new HashMap<>();
        for (int num : nums) {
            if (!map.containsKey(num)) {
                map.put(num, new Node(num));
            } else {
                map.get(num).count++;
            }
        }

        PriorityQueue<Node> heap = new PriorityQueue<>(new NodeComparator());
        for (Node cur : map.values()) {
            if (heap.size() < k || (heap.size() == k && cur.count > heap.peek().count)) {
                heap.add(cur);
            }
            if (heap.size() > k) {
                heap.poll();
            }
        }
        int[] res = new int[k];
        int index = 0;
        while (!heap.isEmpty()) {
            res[index++] = heap.poll().num;
        }
        return res;
    }

    public static class Node {
        public int num;
        public int count;

        public Node(int k) {
            num = k;
            count = 1;
        }
    }

    public static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.count - o2.count;
        }
    }
}
