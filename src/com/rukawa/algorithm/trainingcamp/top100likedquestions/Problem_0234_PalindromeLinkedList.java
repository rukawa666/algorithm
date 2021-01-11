package com.rukawa.algorithm.trainingcamp.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-25 21:53
 * @Version：1.0
 */
public class Problem_0234_PalindromeLinkedList {
    /**
     * 回文链表
     * 请判断一个链表是否为回文链表。
     *
     * 示例 1:
     * 输入: 1->2
     * 输出: false
     *
     * 示例 2:
     * 输入: 1->2->2->1
     * 输出: true
     *
     * 进阶：
     * 你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        ListNode n1 = head;
        ListNode n2 = head;
        while (n2.next != null && n2.next.next != null) {
            n1 = n1.next;
            n2 = n2.next.next;
        }

        n2 = n1.next;
        n1.next = null;
        ListNode n3 = null;
        while (n2 != null) {
            n3 = n2.next;
            n2.next = n1;
            n1 = n2;
            n2 = n3;
        }

        n3 = n1;
        n2 = head;
        boolean res = true;
        while (n1 != null && n2 != null) {  // check palindrome
            if (n1.val != n2.val) {
                res = false;
                break;
            }
            n1 = n1.next;   // left to mid
            n2 = n2.next;   // right to mid
        }
        n1 = n3.next;
        n3.next = null;
        while (n1 != null) {    // recover list
            n2 = n1.next;
            n1.next = n3;
            n3 = n1;
            n1 = n2;
        }
        return res;
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
}
