package com.rukawa.algorithm.base.class06_linkedlist;


import com.rukawa.algorithm.base.class02.ListNode;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-19 9:45
 * @Version：1.0
 */
public class Code05_FindFirstIntersectNode {
    /**
     * 给定两个可能有环也可能无环的单链表，头结点head1和head2。请实现一个函数，如果两个链表相交，请返回相交的第一个节点，如果不相交，返回null
     *
     * 要求：
     *    如果两个链表长度和为N，时间复杂度请达到O(N)，额外的空间复杂度请达到O(1)
     */

    public static ListNode getIntersectNode(ListNode head01, ListNode head02) {
        if (head01 == null || head02 == null) {
            return null;
        }

        ListNode loop01 = getLoopNode(head01);
        ListNode loop02 = getLoopNode(head02);
        // 如果两个链表都是无环
        if (loop01 == null && loop02 == null) {
            return noLoop(head01, head02);
        }
        // 如果两个链表有环
        if (loop01 != null && loop02 != null) {
            return bothLoop(head01, loop01, head02, loop02);
        }
        return null;
    }

    /**
     * 第一个问题：如何判断一个链表是否有环，如果有，则返回第一个进入环的节点，如果没有则返回null
     */
    public static ListNode getLoopNode(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        // n1 慢  n2 快
        ListNode slow = head.next;    // n1 -> slow
        ListNode fast = head.next.next;   // n2 -> fast

        while (slow != fast) {
            if (fast.next == null || fast.next.next == null) {
                return null;
            }

            slow = slow.next;
            fast = fast.next.next;
        }
        // slow fast相遇则找到入环节点
        fast = head;  // n2 -> walk again from head
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    /**
     * 如何判断两个无环链表是否相交，相交返回第一个相交节点，不相交返回null
     */
    public static ListNode noLoop(ListNode head01, ListNode head02) {
        if (head01 == null || head02 == null) {
            return null;
        }
        ListNode cur01 = head01;
        ListNode cur02 = head02;
        int n = 0;
        while (cur01 != null) {
            n++;
            cur01 = cur01.next;
        }

        while (cur02 != null) {
            n--;
            cur02 = cur02.next;
        }

        if (cur01 != cur02) {
            return null;
        }
        // n ：链表1的长度减去链表2的长度的值
        cur01 = n > 0 ? head01 : head02;   // 谁长，谁的头变成cur01
        cur02 = cur01 == head01 ? head02 : head01;  // 谁短，谁的头变成cur02
        n = Math.abs(n);
        while (n != 0) {
            n--;
            cur01 = cur01.next;
        }

        while (cur01 != cur02) {
            cur01 = cur01.next;
            cur02 = cur02.next;
        }
        return cur01;
    }


    /**
     * 如何判断两个有环链表是否相交，相交返回第一个相交节点，不相交则返回null
     * 1.两个有环链表不相交
     * 2.两个有环链表相交，入环节点是同一个
     * 3.两个有环链表相交，入环节点不是同一个
     */
    public static ListNode bothLoop(ListNode head01, ListNode loop01, ListNode head02, ListNode loop02) {
        ListNode cur01;
        ListNode cur02;
        if (loop01 == loop02) {
            cur01 = head01;
            cur02 = head02;
            int n = 0;
            while (cur01 != loop01) {
                n++;
                cur01 = cur01.next;
            }

            while (cur02 != loop02) {
                n--;
                cur02 = cur02.next;
            }

            cur01 = n > 0 ? head01 : head02;
            cur02 = cur01 == head01 ? head02 : head01;

            n = Math.abs(n);
            while (n != 0) {
                n--;
                cur01 = cur01.next;
            }
            while (cur01 != cur02) {
                cur01 = cur01.next;
                cur02 = cur02.next;
            }
            return cur01;
        } else {
            // loop1走一圈，没有遇到loop2，说明两个链表没有相交
            // 如果走一圈遇到loop2，则说明是情况三
            cur01 = loop01.next;
            while (cur01 != loop01) {
                if (cur01 == loop02) {
                    return loop02;
                }
                cur01 = cur01.next;
            }
            return null;
        }
    }

    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        head1.next.next.next = new ListNode(4);
        head1.next.next.next.next = new ListNode(5);
        head1.next.next.next.next.next = new ListNode(6);
        head1.next.next.next.next.next.next = new ListNode(7);

        // 0->9->8->6->7->null
        ListNode head2 = new ListNode(0);
        head2.next = new ListNode(9);
        head2.next.next = new ListNode(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).val);

        // 1->2->3->4->5->6->7->4...
        head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        head1.next.next.next = new ListNode(4);
        head1.next.next.next.next = new ListNode(5);
        head1.next.next.next.next.next = new ListNode(6);
        head1.next.next.next.next.next.next = new ListNode(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new ListNode(0);
        head2.next = new ListNode(9);
        head2.next.next = new ListNode(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(getIntersectNode(head1, head2).val);

        // 0->9->8->6->4->5->6..
        head2 = new ListNode(0);
        head2.next = new ListNode(9);
        head2.next.next = new ListNode(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).val);

    }
}
