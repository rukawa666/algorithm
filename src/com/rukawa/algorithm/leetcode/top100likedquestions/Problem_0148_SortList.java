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
     * 在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序。
     * 示例 1:
     * 输入: 4->2->1->3
     * 输出: 1->2->3->4
     *
     * 示例 2:
     * 输入: -1->5->3->4->0
     * 输出: -1->0->3->4->5
     */
    public ListNode sortList(ListNode head) {
        int N = 0;
        ListNode cur = head;
        while (cur != null) {
            N++;
            cur = cur.next;
        }

        ListNode h = head;
        ListNode teamFirst = head;
        ListNode pre = null;

        for (int len = 1; len < N; len <<= 1) {
            while (teamFirst != null) {
                // 长度为5的数组，[leftHead,leftTail, rightHead,rightTail, Next]
                ListNode[] hthtn = hthtn(teamFirst, len);
                // 返回一个长度为2的数组，
                ListNode[] mhmt = merge(hthtn[0], hthtn[1], hthtn[2], hthtn[3]);

                if (h == teamFirst) {
                    h = mhmt[0];
                } else {
                    pre.next = mhmt[0];
                }
                pre = mhmt[1];

                teamFirst = hthtn[4];
            }
            teamFirst = h;
            pre = null;
        }
        return h;
    }

    public static ListNode[] hthtn(ListNode teamFirst, int len) {
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
        return new ListNode[]{leftStart, leftEnd, rightStart, rightEnd, next};
    }

    public static ListNode[] merge(ListNode leftStart, ListNode leftEnd, ListNode rightStart, ListNode rightEnd) {
        if (rightStart == null) {
            return new ListNode[]{leftStart, leftEnd};
        }

        ListNode head = null;
        ListNode pre = null;
        ListNode cur = null;
        ListNode tail = null;
        while (leftStart != leftEnd.next && rightStart != rightEnd.next) {
            if (leftStart.val <= rightStart.val) {
                cur = leftStart;
                leftStart = leftStart.next;
            } else {
                cur = rightStart;
                rightStart = rightStart.next;
            }

            if (pre == null) {
                head = cur;
            } else {
                pre.next = cur;
            }
            pre = cur;
        }

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
        return new ListNode[]{head, tail};
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
