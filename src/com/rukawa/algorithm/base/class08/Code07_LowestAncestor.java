package com.rukawa.algorithm.base.class08;

import com.rukawa.algorithm.base.class07.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-22 7:34
 * @Version：1.0
 */
public class Code07_LowestAncestor {

    /**
     * 给定一个二叉树的头节点head，和另外两个节点a和b
     * 返回a和b的最低公共祖先
     */
    public static Node lowestAncestor01(Node head, Node n1, Node n2) {
        if (head == null) {
            return null;
        }

        HashMap<Node, Node> parentMap = new HashMap<>();
        parentMap.put(head, null);
        fillParentHashMap(head, parentMap);
        HashSet<Node> n1Set = new HashSet<>();
        Node cur = n1;
        n1Set.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            n1Set.add(cur);
        }

        cur = n2;
        while (!n1Set.contains(cur)) {
            cur = parentMap.get(cur);
        }

        return cur;

    }

    public static void fillParentHashMap(Node head, HashMap<Node, Node> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentHashMap(head.left, parentMap);
        }

        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentHashMap(head.right, parentMap);
        }
    }


    /**
     *  x这棵树上，a和b汇聚在哪
     *  x这棵树上是否发现a和b，如果发现则最低公共祖先在x这棵树上
     *  情况分析：
     *    x不是最低的汇聚点：
     *      1.x的左树上有最低汇聚点
     *      2.x的右树上有最低汇聚点
     *      3.x的整棵树上a和b不全
     *    x是最低的汇聚点：
     *      1.左树发现一个，右树发现另外一个，在x这里汇聚
     *      2.x本身就是a节点，左树或者右树发现了b
     *      3.x本身就是b节点，左树或者右树发现了a
     *
     *  根据情况分析需要以下几个要素：
     *      1.这棵树上有没有a节点
     *      2.这棵树上有没有b节点
     *      3.这棵树上有没有发现a和b的最低汇聚点
     */
    public static Node lowestAncestor02(Node head, Node n1, Node n2) {
        return process(head, n1, n2).res;
    }

    public static Info process(Node node, Node n1, Node n2) {
       if (node == null) {
           return new Info(null, false, false);
       }

        Info leftInfo = process(node.left, n1, n2);
        Info rightInfo = process(node.right, n1, n2);
        boolean findN1 = (node == n1) || leftInfo.findN1 || rightInfo.findN1;
        boolean findN2 = (node == n2) || leftInfo.findN2 || rightInfo.findN2;
        Node res = null;
        if (leftInfo.res != null) { // 左树上发现最低公共祖先
            res = leftInfo.res;
        } else if (rightInfo.res != null) { // 右树上发现了最低公共祖先
            res = rightInfo.res;
        } else {
            if (findN1 && findN2) {
                res = node;
            }
        }
        return new Info(res, findN1, findN2);
    }

    public static class Info {
        public Node res;
        public boolean findN1;
        public boolean findN2;

        public Info(Node res, boolean findN1, boolean findN2) {
            this.res = res;
            this.findN1 = findN1;
            this.findN2 = findN2;
        }
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    // for test
    public static Node pickRandomOne(Node head) {
        if (head == null) {
            return null;
        }
        ArrayList<Node> arr = new ArrayList<>();
        fillPrelist(head, arr);
        int randomIndex = (int) (Math.random() * arr.size());
        return arr.get(randomIndex);
    }

    // for test
    public static void fillPrelist(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPrelist(head.left, arr);
        fillPrelist(head.right, arr);
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Node o1 = pickRandomOne(head);
            Node o2 = pickRandomOne(head);
            if (lowestAncestor01(head, o1, o2) != lowestAncestor02(head, o1, o2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
