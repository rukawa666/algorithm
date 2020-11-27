package com.rukawa.algorithm.trainingcamp.trainingcamp1.class6;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-04 22:48
 * @Version：1.0
 */
public class Code01_MorrisTraversal {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 当前节点cur，一开始cur来到整棵树的头节点
     * 1、cur无左树，cur = cur.right
     * 2、cur有左树，找到左树的最右节点mostRight
     *    a、mostRight的右指针指向null的，mostRight.right = cur, cur = cur.left;
     *    b、mostRight的右指针指向cur的，mostRight.right = null, cur = cur.right;
     * @param head
     */
    public static void morris(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            // 有左树
            if (mostRight != null) {
                // 找到cur左树上，真实的最右节点
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                // 此时mostRight一定是cur左树上的最右节点
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }
            cur = cur.right;
        }
    }

    // 只要一个节点要往右移动，就打印
    // cur节点没有左树，往右移动的时候打印
    // cur节点有左树，第一次改完节点往左移动，第二次改为null只有往右移动的时候打印
    public static void morrisIn(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }
            System.out.print(cur.value + " ");
            cur = cur.right;
        }
        System.out.println();
    }

    // cur没有左树，就打印
    // cur有左树，第一次来到的节点打印
    public static void morrisPre(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null) {
                    mostRight.right = cur;
                    System.out.print(cur.value + " ");
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            } else {
                System.out.print(cur.value + " ");
            }
            cur = cur.right;
        }
        System.out.println();
    }

    /**
     * morris后序遍历: 整棵树可以由左树的右边界整体划分，逆序打印
     * 打印时机：能回到自己两次，且第二次回到自己的时候，打印它的左树右边界，逆序打印
     *      1
     *    /   \
     *   2    3
     * /  \  / \
     *4   5 6   7
     *
     *1 2 4 2 5 1 3 6 3 7
     *      ^   ^     ^
     * 2 左树右边界：逆序打印：4
     * 1 左树右边界：逆序打印：5 2
     * 3 左树右边界：逆序打印：6
     * 所有过程结束后：逆序打印整棵树的右边界：7 3 1
     * 后序遍历：4 5 2 6 7 3 1
     * @param head
     */
    public static void morrisPos(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                    // 第二次来到的节点，逆序打印它的左树右边界
                    printEdge(cur.left);
                }
            }
            cur = cur.right;
        }
        // 最后打印整棵树的最右边界
        printEdge(head);
        System.out.println();
    }

    public static void printEdge(Node head) {
        Node tail = reverseEdge(head);
        Node cur = tail;
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.right;
        }
        reverseEdge(tail);
    }

    public static Node reverseEdge(Node from) {
        Node pre = null;
        Node next = null;
        while (from != null) {
            next = from.right;
            from.right = pre;
            pre = from;
            from = next;
        }
        return pre;
    }

    /**
     * 搜索二叉树BST(Binary Sort Tree)
     * 特点：
     *   1、查找树的左右子树各是一棵查找树
     *   2、若查找树的左子树非空，则其左子树的各节点均小于根节点的值
     *   3、若查找树的右子树非空，则其右子树的各节点均大于根节点的值
     * @param head
     * @return
     */
    public static boolean isBST(Node head) {
        if (head == null) {
            return true;
        }

        Node cur = head;
        Node mostRight = null;
        Integer pre = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }
            // 中序遍历，搜索二叉树必须递增，所以pre的value必须小于cur的value
            if (pre != null && pre >= cur.value) {
                return false;
            }
            pre = cur.value;
            cur = cur.right;
        }
        return true;
    }


    public static void main(String[] args) {
        Node head = new Node(4);
        head.left = new Node(2);
        head.right = new Node(6);
        head.left.left = new Node(1);
        head.left.right = new Node(3);
        head.right.left = new Node(5);
        head.right.right = new Node(7);
        morrisIn(head);
        morrisPre(head);
        morrisPos(head);

    }
}
