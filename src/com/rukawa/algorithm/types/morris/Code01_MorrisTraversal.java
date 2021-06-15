package com.rukawa.algorithm.types.morris;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/6/14 0014 22:49
 */
public class Code01_MorrisTraversal {

    public static class Node {
        public int value;
        Node left;
        Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * Morris：空间复杂度O(1),时间复杂度O(1)
     * 实现：利用一棵树上大量的右指针空闲空间
     */

    /**
     * 当前节点cur，一开始cur来到整棵树的头节点
     * 1、cur无左树，cur = cur.right
     * 2、cur有左树，找到左树的最右节点mostRight
     *    a、mostRight的右指针指向null的，mostRight.right = cur, cur = cur.left;
     *    b、mostRight的右指针指向cur的，mostRight.right = null, cur = cur.right;
     * 3、cur为空时遍历停止
     *
     * 原版：
     * 假设来到当前节点cur，开始时cur来到头节点位置
     * 1、如果cur没有左孩子，cur向右移动(cur = cur.right)
     * 2、如果cur有左孩子，找到左子树上最右的节点mostRight
     *    a、如果mostRight的右指针指向空，让其指向cur，然后cur向左移动(cur = cur.left)
     *    b、如果mostRight的右指针指向cur，让其指向null，然后cur向右移动(cur = cur.right)
     * 3、cur为空时停止遍历
     */

    public static void morris(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            // 先判断左树
            mostRight = cur.left;
            // 如果有左树，条件2
            if (mostRight != null) {
                // 找到左子树上的最右节点，没有人为干预过
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                // 第一次来到左树最右节点，为空，第一次来到，让其指向cur，cur来到左孩子，继续遍历
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    // 如果最右指针指向cur，第二次来到，还原指针
                    mostRight.right = null;
                }
            }
            // 如果无左树，条件1
            cur = cur.right;
        }
    }


    public static void morrisByPre(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            // 先判断左树
            mostRight = cur.left;
            // 如果有左树，条件2
            if (mostRight != null) {
                // 找到左子树上的最右节点，没有人为干预过
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                // 第一次来到左树最右节点，为空，第一次来到，让其指向cur，cur来到左孩子，继续遍历
                if (mostRight.right == null) {
                    System.out.println(cur.value + " ");
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    // 如果最右指针指向cur，第二次来到，还原指针
                    mostRight.right = null;
                }
            } else {
                // 没有左树
                System.out.println(cur.value + " ");
            }
            // 如果无左树，条件1
            cur = cur.right;
        }
    }

    // Morris遍历第二次来到的时候打印当前值，为中序遍历
    public static void morrisByIn(Node head) {
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
            System.out.println(cur.value + " ");
            cur = cur.right;
        }
        System.out.println();
    }


    // Morris遍历第二次来到的时候逆序的打印左树右边界，morris跑完之后单独打印整颗树的右边界
    // 为后序遍历
    public static void morrisByPos(Node head) {
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
                    printEdge(cur.left);
                }
            }
            cur = cur.right;
        }
        printEdge(head);
        System.out.println();
    }

    public static void printEdge(Node node) {
        Node tail = reverseEdge(node);
        Node cur = tail;
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.right;
        }
        reverseEdge(tail);
    }

    // 链表反转
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

    // 是否是搜索二叉树，中序遍历的结果是升序即为搜索二叉树
    public static boolean isBST(Node head) {
        if (head == null) {
            return true;
        }
        Node cur = head;
        Node mostRight = null;
        Integer pre = null;
        boolean ans = true;
        while (cur != null) {
            mostRight = mostRight.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != null) {
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
            if (pre != null && pre >= cur.value) {
                ans = false;
            }
            pre = cur.value;
            cur = cur.right;
        }
        return ans;
    }
}
