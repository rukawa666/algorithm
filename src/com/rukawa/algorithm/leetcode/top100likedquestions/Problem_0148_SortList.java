package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 11:06
 * @Version：1.0
 */
public class Problem_0148_SortList {
    /**
     * 排序链表
     * 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
     *
     * 示例 1:
     * 输入: 4->2->1->3
     * 输出: 1->2->3->4
     *
     * 示例 2:
     * 输入: -1->5->3->4->0
     * 输出: -1->0->3->4->5
     *
     * 提示：
     * 链表中节点的数目在范围 [0, 5 * 104] 内
     * -105 <= Node.val <= 105
     *
     * 进阶：你可以在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序吗？
     */
    public ListNode sortList(ListNode head) {
        // 使用归并排序  迭代方法
        int n = 0;
        ListNode cur = head;
        while (cur != null) {
            n++;
            cur = cur.next;
        }

        ListNode h = head;
        ListNode teamFirst = head;
        ListNode pre = null;
        for (int len = 1; len < n; len <<= 1) {
            while (teamFirst != null) {
                // 左组从哪到哪 leftStart leftEnd
                // 右组从哪到哪 rightStart rightEnd
                ListNode[] hthtn = hthtn(teamFirst, len);
                // leftStart...leftEnd rightStart...rightEnd 去merge
                ListNode[] merge = merge(hthtn[0], hthtn[1], hthtn[2], hthtn[3]);

                if (h == teamFirst) {
                    h = merge[0];
                    pre = merge[1];
                } else {
                    pre.next = merge[0];
                    pre = merge[1];
                }
                // teamFirst = rightEnd
                teamFirst = hthtn[4];
            }
            teamFirst = h;
            pre = null;
        }
        return h;
    }

    // 归并排序的迭代版本  得到左组的头和尾  右组的头和尾
    public ListNode[] hthtn(ListNode teamFirst, int len) {
        ListNode leftStart = teamFirst;
        ListNode leftEnd = teamFirst;
        ListNode rightStart = null;
        ListNode rightEnd = null;
        ListNode next = null;
        int pass = 0;
        while (teamFirst != null) {
            pass++;
            if (pass <= len) {
                leftEnd = teamFirst;
            }

            if (pass == len + 1) {
                rightStart = teamFirst;
            }
            if (pass > len) {
                rightEnd = teamFirst;
            }

            if (pass == (len << 1)) {
                break;
            }
            teamFirst = teamFirst.next;
        }
        leftEnd.next = null;
        if (rightEnd != null) {
            next = rightEnd.next;
            rightEnd.next = null;
        }
        return new ListNode[] {leftStart, leftEnd, rightStart, rightEnd, next};
    }

    // 左组和右组merge
    public ListNode[] merge(ListNode leftStart, ListNode leftEnd, ListNode rightStart, ListNode rightEnd) {
        if (rightEnd == null) {
            return new ListNode[] {leftStart, leftEnd};
        }
        ListNode head = null;
        ListNode pre = null;
        ListNode cur = null;
        ListNode tail = null;
        while (leftStart != leftEnd.next && rightStart != rightEnd.next) {
            if (leftStart.val < rightStart.val) {
                cur = leftStart;
                leftStart = leftStart.next;
            } else {
                cur = rightStart;
                rightStart = rightStart.next;
            }

            if (pre == null) {
                head = cur;
                pre = cur;
            } else {
                pre.next = cur;
                pre = cur;
            }
        }

        // 剩余未merge的加入进来
        if (leftStart != leftEnd.next) {
            while (leftStart != leftEnd.next) {
                pre.next = leftStart;
                pre = leftStart;
                tail = leftStart;
                leftStart = leftStart.next;
            }
        } else {
            while (rightStart != rightEnd.next) {
                pre.next = rightStart;
                pre = rightStart;
                tail = rightStart;
                rightStart = rightStart.next;
            }
        }
        return new ListNode[] {head, tail};
    }

    public static class ListNode {
        int val;
        ListNode next;

        public ListNode() {
        }

        public ListNode(int x) {
            this.val = x;
            next = null;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
