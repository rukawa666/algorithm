package com.rukawa.algorithm.leetcode.highfrequency;

import java.util.StringJoiner;

/**
 * create by hqh on 2022/7/8
 */
public class Problem_0143_ReorderList {
    /**
     * 143. 重排链表
     * 给定一个单链表 L 的头节点 head ，单链表 L 表示为：
     * L0 → L1 → … → Ln - 1 → Ln
     * 请将其重新排列后变为：
     * L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …
     * 不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
     *
     * 示例 1：
     * 输入：head = [1,2,3,4]
     * 输出：[1,4,2,3]
     *
     * 示例 2：
     * 输入：head = [1,2,3,4,5]
     * 输出：[1,5,2,4,3]
     *
     * 提示：
     * 链表的长度范围为 [1, 5 * 104]
     * 1 <= node.val <= 1000
     */
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            StringJoiner joiner = new StringJoiner("->", "Node " ,"");
            ListNode head = this;
            while (head != null) {
                joiner.add(String.valueOf(head.val));
                head = head.next;
            }
            return joiner.toString();
        }
    }

    public static void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }
        ListNode midNode = midNode(head);
        ListNode n1 = head;
        ListNode n2 = midNode.next;
        midNode.next = null;
        n2 = reverseList(n2);
        mergeList(n1, n2);
    }

    public static ListNode midNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static ListNode reverseList(ListNode node) {
        ListNode prev = null;
        ListNode tmp = null;
        while (node != null) {
            tmp = node.next;
            node.next = prev;
            prev = node;
            node = tmp;
        }
        return prev;
    }

    public static void mergeList(ListNode n1, ListNode n2) {
        ListNode n3, n4;
        while (n1 != null && n2 != null) {
            n3 = n1.next;
            n4 = n2.next;

            n1.next = n2;
            n1 = n3;

            n2.next = n3;
            n2 = n4;
        }
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(2);
        head.next.next.next.next = new ListNode(1);
        System.out.println(head);
        reorderList(head);
        System.out.println(head);
    }
}
