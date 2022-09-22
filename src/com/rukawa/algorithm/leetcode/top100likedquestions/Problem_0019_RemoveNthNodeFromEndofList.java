package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:31
 * @Version：1.0
 */
public class Problem_0019_RemoveNthNodeFromEndofList {
    /**
     * 删除链表的倒数第 N 个结点
     * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
     *
     * 示例 1：
     * 输入：head = [1,2,3,4,5], n = 2
     * 输出：[1,2,3,5]
     *
     * 示例 2：
     * 输入：head = [1], n = 1
     * 输出：[]
     *
     * 示例 3：
     * 输入：head = [1,2], n = 1
     * 输出：[1]
     *  
     * 提示：
     * 链表中结点的数目为 sz
     * 1 <= sz <= 30
     * 0 <= Node.val <= 100
     * 1 <= n <= sz
     * 进阶：你能尝试使用一趟扫描实现吗？
     */

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode pre = null;
        ListNode cur = head;
        // 假设找到倒数第8个节点，需要找到倒数第9个节点，倒数第7个挂到倒数第9个
        // 假设总共m个节点，先走n步，然后pre来到头，共同走(m-n)步，pre走了m-(m-n)步，找到之前的节点
        // 总共17个节点，n为3，找到倒数第二个节点
        while (cur != null) {
            n--;
            if (n == -1) {
                pre = head;
            }
            if (n < -1) {
                pre = pre.next;
            }
            cur = cur.next;
        }

        if (n > 0) {
            return head;
        }
        if (pre == null) {
            return head.next;
        }
        pre.next = pre.next.next;
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
