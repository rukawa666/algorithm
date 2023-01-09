package com.rukawa.algorithm.base.class06_linkedlist;

import com.rukawa.algorithm.base.class02.ListNode;

import java.util.Stack;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-17 8:23
 * @Version：1.0
 */
public class Code02_IsPalindromeList {

    /**
     * 验证链表是否是回文结构
     */

    public static boolean isPalindrome01(ListNode head) {

        Stack<ListNode> stack = new Stack<>();
        ListNode cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }

        while (head != null) {
            if (head.val != stack.pop().val) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    public static boolean isPalindrome02(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        ListNode right = head.next;
        ListNode cur = head;
        while (cur.next != null && cur.next.next != null) {
            right = right.next;
            cur = cur.next.next;
        }
        Stack<ListNode> stack = new Stack<>();
        while (right != null) {
            stack.push(right);
            right = right.next;
        }

        while (!stack.isEmpty()) {
            if (head.val != stack.pop().val) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    public static boolean isPalindrome03(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        // 慢指针
        ListNode n1 = head;
        // 快指针
        ListNode n2 = head;
        // 此时需要保证n2必须来到链表的最后一个节点，如果链表是偶数节点，则n1返回上中点，如果是奇数节点，则n2返回中点
        while (n2.next != null && n2.next.next != null) {
            n1 = n1.next;   // n1 -> mid
            n2 = n2.next.next;  // n2 -> end
        }

        // 右半部分链表逆序
        n2 = n1.next;   // n2 -> right part first node
        n1.next = null; // mid.next -> null
        ListNode n3 = null;
        while (n2 != null) {    // right part convert
            n3 = n2.next;   // n3 -> save next node
            n2.next = n1;   // next of right node convert
            n1 = n2;    // n1 remove
            n2 = n3;    // n2 remove
        }

        n3 = n1;    // n3 -> save last node
        n2 = head;  // n2 -> left first node
        boolean res = true;
        // 检查链表是否是回文结构
        while (n1 != null && n2 != null) {  // check palindrome
            if (n1.val != n2.val) {
                res = false;
                break;
            }
            n1 = n1.next;   // left to mid
            n2 = n2.next;   // right to mid
        }
        // 还原链表
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
}
