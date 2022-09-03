package com.rukawa.algorithm.base.class06;

import java.util.HashMap;
import java.util.StringJoiner;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-18 15:01
 * @Version：1.0
 */
public class Code04_CopyListWithRandom {

    /**
     * 一种特殊的单链表节点类描述如下
     * class Node {
     * int value;
     * Node next;
     * Node rand;
     * Node(int val) { value = val; }
     * }
     *
     * rand指针是单链表节点结构中新增的指针，rand可能指向链表中的任意一个节点，也可能指向null。
     * 给定一个由Node节点类型组成的无环单链表的头节点 head，请实现一个函数完成这个链表的复制，并返回复制的新链表的头节点。
     *【要求】
     * 时间复杂度O(N)，额外空间复杂度O(1)
     */
    public static class Node {
        private int value;
        private Node next;
        private Node rand;

        public Node(int data) {
            this.value = data;
        }

        public Node(int[] nums) {
            if (nums == null || nums.length == 0) {
                throw new IllegalArgumentException("Parameter is invalid");
            }
            Node head = this;
            head.value = nums[0];
            for (int i = 1; i < nums.length; i++) {
                head.next = new Node(nums[i]);
                head = head.next;
            }
        }

        @Override
        public String toString() {
            StringJoiner joiner = new StringJoiner("->", "Node " ,"");
            Node head = this;
            while (head != null) {
                joiner.add(String.valueOf(head.value));
                head = head.next;
            }
            return joiner.toString();
        }
    }


    public static void main(String[] args) {
        int[] arr = {1,2,4,7,4,3,6,5};
        Node heap = new Node(arr);
        Node node = copyListWithRand02(heap);
        System.out.println(node);
    }

    public static Node copyListWithRand02(Node head) {
        if (head == null) {
            return null;
        }

        Node cur = head;
        Node next = null;
        /**
         * copy node and link to every node
         * 1 -> 2
         * 1 -> 1' -> 2
         * 复制当前节点插入到当前节点之后
         */
        while (cur != null) {
            next = cur.next;
            cur.next = new Node(cur.value);
            cur.next.next = next;
            cur = next;
        }

        cur = head;
        Node curCopy = null;
        while (cur != null) {
            // cur 老
            // cur.next 新copy
            next = cur.next.next;
            curCopy = cur.next;
            curCopy.rand = cur.rand != null ? cur.rand.next : null;
            cur = next;
        }
        // res 新链表的头结点
        Node res = head.next;
        cur = head;
        // split
        while (cur != null) {
            next = cur.next.next;
            curCopy = cur.next;
            cur.next = next;
            curCopy.next = next != null ? next.next : null;
            cur = next;
        }
        return res;
    }


    public static Node copyListWithRand01(Node head) {
        HashMap<Node, Node> map = new HashMap<>();
        Node cur = head;
        while (cur != null) {
            map.put(cur, new Node(cur.value));
            cur = cur.next;
        }

        cur = head;
        while (cur != null) {
            map.get(cur).next = map.get(cur.next);
            map.get(cur).rand = map.get(cur.rand);
            cur = cur.next;
        }
        return map.get(head);
    }
}
