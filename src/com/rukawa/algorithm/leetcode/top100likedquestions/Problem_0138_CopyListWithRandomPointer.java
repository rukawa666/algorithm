package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.HashMap;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 11:06
 * @Version：1.0
 */
public class Problem_0138_CopyListWithRandomPointer {
    /**
     * 复制带随机指针的链表
     * 给定一个链表，每个节点包含一个额外增加的随机指针，该指针可以指向链表中的任何节点或空节点。
     * 要求返回这个链表的深拷贝。
     * 我们用一个由n个节点组成的链表来表示输入/输出中的链表。每个节点用一个[val, random_index]表示：
     * val：一个表示Node.val的整数。
     * random_index：随机指针指向的节点索引（范围从0到n-1）；如果不指向任何节点，则为null。
     *
     * 示例 1：
     * 输入：head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
     * 输出：[[7,null],[13,0],[11,4],[10,2],[1,0]]
     *
     * 示例 2：
     * 输入：head = [[1,1],[2,1]]
     * 输出：[[1,1],[2,1]]
     *
     * 示例 3：
     * 输入：head = [[3,null],[3,0],[3,null]]
     * 输出：[[3,null],[3,0],[3,null]]
     *
     * 示例 4：
     * 输入：head = []
     * 输出：[]
     * 解释：给定的链表为空（空指针），因此返回 null。
     * 
     *
     * 提示：
     * -10000 <= Node.val <= 10000
     * Node.random为空（null）或指向链表中的节点。
     * 节点数目不超过 1000 。
     */
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }

        Node cur = head;
        Node next = null;
        // 1 -> 2 -> 3 -> null
        // 1 -> 1' -> 2 -> 2' -> 3 -> 3' -> null
        while (cur != null) {
            next = cur.next;
            cur.next = new Node(cur.val);
            cur.next.next = next;
            cur = next;
        }

        cur = head;
        Node copy = null;
        // 1 1'  2 2'  3 3'
        // 依次设置1' 2' 3' random指针
        while (cur != null) {
            next = cur.next.next;
            copy = cur.next;
            // 假设1的random指针指向c
            // 此时1'的random指针需要指向c'
            // copy.random = cur.random.next;
            copy.random = cur.random != null ? cur.random.next : null;
            cur = next;
        }

        Node res = head.next;
        cur = head;
        // 新 老节点 混在一起 next方向上，random正确
        // next方向上，把新老链表分离
        while (cur != null) {
            next = cur.next.next;
            copy = cur.next;
            cur.next = next;
            copy.next = next != null ? next.next : null;
            cur = next;
        }
        return res;
    }

    public Node copyRandomList2(Node head) {
        HashMap<Node, Node> map = new HashMap<>();
        Node cur = head;
        while (cur != null) {
            map.put(cur, new Node(cur.val));
            cur = cur.next;
        }

        cur = head;
        while (cur != null) {
            map.get(cur).next = map.get(cur.next);
            map.get(cur).random = map.get(cur.random);
            cur = cur.next;
        }
        return map.get(head);
    }


    public static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

}
