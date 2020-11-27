package com.rukawa.algorithm.base.class06;

import com.rukawa.algorithm.base.class02.ListNode;

import java.util.ArrayList;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-17 7:51
 * @Version：1.0
 */
public class Code01_LinkedListMid {

    public static void main(String[] args) {
        int[] arr = {7,4,1,3,2,6,5,8};
        ListNode head = new ListNode(arr);
        ListNode node = midOrDownMidNode(head);
        System.out.println(node);
    }

    // 如果奇数节点，返回中间节点；如果是偶数节点，则返回上中点
    public static ListNode midOrUpMidNode(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }
        // 链表有三个节点或者以上更多节点
        ListNode slow = head.next;
        ListNode fast = head.next.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    // 如果奇数节点，返回中间节点；如果是偶数节点，则返回下中点
    public static ListNode midOrDownMidNode(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode slow = head.next;
        ListNode fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    // 返回中点或者上中点的前一个
    // 奇数时，返回中间节点的前一个；偶数时，返回上中点的前一个
    public static ListNode midOrUpPreNode(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head.next.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    // 返回中点或者下中点的前一个
    // 奇数时，返回中间节点的前一个；偶数时，返回下中点的前一个
    public static ListNode midOrDownPreNode(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        if (head.next.next == null) {
            return head;
        }

        ListNode slow = head;
        ListNode fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static ListNode right01(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode cur = head;
        ArrayList<ListNode> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }

        return arr.get((arr.size() - 1) / 2);
    }

    public static ListNode right02(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode cur = head;
        ArrayList<ListNode> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }

        return arr.get((arr.size()) / 2);
    }

    public static ListNode right03(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }

        ListNode cur = head;
        ArrayList<ListNode> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }

        return arr.get((arr.size() - 3) / 2);
    }

    public static ListNode right04(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode cur = head;
        ArrayList<ListNode> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get((arr.size() - 2) / 2);
    }
}
