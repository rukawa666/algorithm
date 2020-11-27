package com.rukawa.algorithm.base.class02;

import java.util.StringJoiner;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-04-19 23:04
 * @Version：1.0
 */
public class ListNode {
    public int val;
    public ListNode next;
    public ListNode(int x) {
        this.val = x;
    }

    public ListNode(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Parameter is invalid");
        }
        ListNode head = this;
        head.val = nums[0];
        for (int i = 1; i < nums.length; i++) {
            head.next = new ListNode(nums[i]);
            head = head.next;
        }
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("->", "Node " ,"");
        ListNode head = this;
        while (head != null) {
            joiner.add(String.valueOf(head.val));
            head = head.next;
        }
        return joiner.toString();
    }
}
