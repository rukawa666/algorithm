package com.rukawa.algorithm.base.class06;


import com.rukawa.algorithm.base.class02.ListNode;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-18 11:51
 * @Version：1.0
 */
public class Code03_SmallerEqualBigger {


    /**
     * 将单向链表按照某值划分为左边小、中间相等、右边打的形式
     * @param args
     */
    public static void main(String[] args) {
        int[] arr = {1,2,4,7,4,3,6,5};
        ListNode heap = new ListNode(arr);
        ListNode node = listPartition02(heap, 4);
        System.out.println(node);
    }

    /**
     * O(1)的时间复杂度
     * 额外的空间复杂度O(1)
     * @param head
     * @param pivot
     * @return
     */
    public static ListNode listPartition02(ListNode head, int pivot) {
        ListNode sH = null;
        ListNode sT = null;
        ListNode eH = null;
        ListNode eT = null;
        ListNode bH = null;
        ListNode bT = null;
        ListNode next = null;
        while (head != null) {
            next = head.next;
            head.next = null;
            if (head.val < pivot) {
                if (sH == null) {
                    sH = head;
                } else {
                    sT.next = head; // 老尾巴的next节点指向当前节点
                }
                sT = head;  // 当前节点变成新的尾巴
            } else if (head.val == pivot) {
                if (eH == null) {
                    eH = head;
                } else {
                    eT.next = head;
                }
                eT = head;
            } else {
                if (bH == null) {
                    bH = head;
                } else {
                    bT.next = head;
                }
                bT = head;
            }
            head = next;
        }
        // 小区域链表和中间区域链表相连接
        if (sT != null) {
            sT.next = eH;
            eT = eT == null ? sT : eT;
        }
        // 连接所有
        if (eT != null) {
            eT.next = bH;
        }

        return sH != null ? sH : (eH != null ? eH : bH);
    }

    /**
     * 时间复杂度O(N)
     * 空间复杂度O(N)
     * @param head
     * @param pivot
     * @return
     */
    public static ListNode listPartition01(ListNode head, int pivot) {
        if (head == null) {
            return null;
        }

        ListNode cur = head;
        int  i = 0;
        while (cur != null) {
            i++;
            cur = cur.next;
        }
        ListNode[] nodeArr = new ListNode[i];
        i = 0;
        cur = head;
        for (; i != nodeArr.length; i++) {
            nodeArr[i] = cur;
            cur = cur.next;
        }
        addPartition(nodeArr, pivot);
        // 通过数组中的节点把链表重新串联起来
        for (i = 1; i != nodeArr.length; i++) {
            nodeArr[i - 1].next = nodeArr[i];
        }
        nodeArr[i - 1].next = null;
        return nodeArr[0];
    }

    public static void addPartition(ListNode[] nodeArr, int pivot) {
        int small = -1;
        int big = nodeArr.length;
        int index = 0;
        while (index != big) {
            if (nodeArr[index].val < pivot) {
                swap(nodeArr, index++, ++small);
            } else if (nodeArr[index].val == pivot) {
                index++;
            } else {
                swap(nodeArr, index, --big);
            }
        }
    }

    public static void swap(ListNode[] nodeArr, int i, int j) {
        ListNode tmp = nodeArr[i];
        nodeArr[i] = nodeArr[j];
        nodeArr[j] = tmp;
    }

}
