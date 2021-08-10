package com.rukawa.algorithm.types.advanced;

import java.util.*;

/**
 * @className: Code_C9_
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/8/9 0009 6:15
 **/
public class Code_C9_DistanceKNodes {
    /**
     * 给定三个参数:
     * 二叉树的头节点head，树上某个节点target，正数K
     * 从target开始，可以向上走或者向下走
     * 返回于target的距离是K的所有节点
     */

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int val) {
            this.value = val;
        }
    }

    public static List<Node> distanceKNodes(Node root, Node target, int K) {
        // 每个节点的parent表
        HashMap<Node, Node> parents = new HashMap<>();
        parents.put(root, null);
        // 每个节点的父节点都维护
        createParentMap(root, parents);
        /**
         * coding 技巧
         *   b   b(是前面的b，线不好连，所以替代)
         *  | \   \
         *  a  c - e
         *  | /   /
         *  d   d(是前面的d，线不好连，所以替代)
         *  从a出发，宽度优先遍历
         *  1、在队列中先放入a
         *  2、开始遍历，先抓取队列的长度，此时长度为1，这一批事件发生一次(弹出a，把b、d放进去)
         *  3、此时长度是2，要弹出两次，先弹出b，把c、e放入队列的前面，然后再弹出d，此时c，e都已经放过了，不重新放，这一批结束
         *  4、...
         */
        Queue<Node> queue = new LinkedList<>();
        // 只要进过队列就等级
        HashSet<Node> visited = new HashSet<>();
        queue.add(target);
        visited.add(target);
        int curLevel = 0;
        // 当我到达第K层收集节点
        List<Node> ans = new ArrayList<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- != 0) {
                Node cur = queue.poll();
                if (curLevel == K) {
                    ans.add(cur);
                }

                if (cur.left != null && !visited.contains(cur.left)) {
                    queue.offer(cur.left);
                    visited.add(cur.left);
                }
                if (cur.right != null && !visited.contains(cur.right)) {
                    queue.offer(cur.right);
                    visited.add(cur.right);
                }
                // 父亲节点不为空且没有访问过，放入队列
                if (parents.get(cur) != null && !visited.contains(parents.get(cur))) {
                    queue.offer(parents.get(cur));
                    visited.add(parents.get(cur));
                }
            }
            curLevel++;
            if (curLevel > K) {
                break;
            }
        }
        return ans;
    }

    public static void createParentMap(Node cur, HashMap<Node, Node> parents) {
        if (cur == null) {
            return;
        }
        if (cur.left != null) {
            parents.put(cur.left, cur);
            createParentMap(cur.left, parents);
        }
        if (cur.right != null) {
            parents.put(cur.right, cur);
            createParentMap(cur.right, parents);
        }
    }

    public static void main(String[] args) {
        Node n0 = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        Node n8 = new Node(8);

        n3.left = n5;
        n3.right = n1;
        n5.left = n6;
        n5.right = n2;
        n1.left = n0;
        n1.right = n8;
        n2.left = n7;
        n2.right = n4;

        Node root = n3;
        Node target = n5;
        int K = 2;

        List<Node> ans = distanceKNodes(root, target, K);
        for (Node o1 : ans) {
            System.out.println(o1.value);
        }

    }

}
