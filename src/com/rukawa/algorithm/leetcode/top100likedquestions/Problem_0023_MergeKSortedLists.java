package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:32
 * @Version：1.0
 */
public class Problem_0023_MergeKSortedLists {
    /**
     * 合并K个升序链表
     * 给你一个链表数组，每个链表都已经按升序排列。
     * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
     *
     * 示例 1：
     * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
     * 输出：[1,1,2,3,4,4,5,6]
     * 解释：链表数组如下：
     * [
     *   1->4->5,
     *   1->3->4,
     *   2->6
     * ]
     * 将它们合并到一个有序链表中得到。
     * 1->1->2->3->4->4->5->6
     *
     * 示例 2：
     * 输入：lists = []
     * 输出：[]
     *
     * 示例 3：
     * 输入：lists = [[]]
     * 输出：[]
     * 提示：
     * k == lists.length
     * 0 <= k <= 10^4
     * 0 <= lists[i].length <= 500
     * -10^4 <= lists[i][j] <= 10^4
     * lists[i] 按 升序 排列
     * lists[i].length 的总和不超过 10^4
     */
    // T(N)=O(M*logM)
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null) {
            return null;
        }
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(Comparator.comparingInt(x -> x.val));
        for (ListNode node : lists) {
            if (node != null) {
                minHeap.add(node);
            }
        }
        if (minHeap.isEmpty()) {
            return null;
        }
        ListNode head = minHeap.poll();
        ListNode pre = head;
        if (pre.next != null) {
            minHeap.add(pre.next);
        }
        while (!minHeap.isEmpty()) {
            ListNode cur = minHeap.poll();
            pre.next = cur;
            pre = cur;
            if (cur.next != null) {
                minHeap.add(cur.next);
            }
        }
        return head;
    }

    public static class ListNode {
        int val;
        ListNode next;

        public ListNode() {}

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
