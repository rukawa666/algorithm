package com.rukawa.algorithm.trainingcamp.top100likedquestions;

import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:29
 * @Version：1.0
 */
public class Problem_0002_AddTwoNumbers {
    /**
     * 两数相加
     * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
     * 请你将两个数相加，并以相同形式返回一个表示和的链表。
     * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     * <p>
     * 示例 1：
     * 输入：l1 = [2,4,3], l2 = [5,6,4]
     * 输出：[7,0,8]
     * 解释：342 + 465 = 807.
     * <p>
     * 示例 2：
     * 输入：l1 = [0], l2 = [0]
     * 输出：[0]
     * <p>
     * 示例 3：
     * 输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
     * 输出：[8,9,9,9,0,0,0,1]
     * <p>
     * 提示：
     * 每个链表中的节点数在范围 [1, 100] 内
     * 0 <= Node.val <= 9
     * 题目数据保证列表表示的数字不含前导零
     */

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 进位
        int ca = 0;
        int n1 = 0;
        int n2 = 0;
        int n = 0;
        ListNode c1 = l1;
        ListNode c2 = l2;
        ListNode node = null;
        ListNode pre = null;
        while (c1 != null || c2 != null) {
            n1 = c1 != null ? c1.val : 0;
            n2 = c2 != null ? c2.val : 0;
            n = n1 + n2 + ca;
            /**
             * 2 -> 4 -> 3
             * 5 -> 6 -> 4
             *
             * null <- 7 <- 0 <- 8
             */
            pre = node;
            node = new ListNode(n % 10);
            node.next = pre;
            ca = n / 10;
            c1 = c1 != null ? c1.next : null;
            c2 = c2 != null ? c2.next : null;
        }

        /**
         * 2 -> 4 -> 3
         * 5 -> 6 -> 7
         * null <- 7 <- 0 <- 1 <- 1
         */
        // 最后如果进位是1，补充一个节点
        if (ca == 1) {
            pre = node;
            node = new ListNode(1);
            node.next = pre;
        }
        return reverseList(node);
    }

    public static ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }


    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
