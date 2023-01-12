package com.rukawa.algorithm.greater1.class34;

public class Problem_0328_OddEvenLinkedList {
	/**
	 * 奇偶链表
	 *
	 * 给定单链表的头节点 head ，将所有索引为奇数的节点和索引为偶数的节点分别组合在一起，然后返回重新排序的列表。
	 * 第一个节点的索引被认为是 奇数 ， 第二个节点的索引为 偶数 ，以此类推。
	 * 请注意，偶数组和奇数组内部的相对顺序应该与输入时保持一致。
	 * 你必须在 O(1) 的额外空间复杂度和 O(n) 的时间复杂度下解决这个问题。
	 */
	// 提交时不要提交这个类
	public static class ListNode {
		int val;
		ListNode next;
	}

	public ListNode oddEvenList(ListNode head) {
		ListNode firstOdd = null;
		ListNode firstEven = null;
		ListNode odd = null;
		ListNode even = null;
		ListNode next = null;
		int count = 1;
		while (head != null) {
			next = head.next;
			head.next = null;
			if ((count & 1) == 1) {
				firstOdd = firstOdd == null ? head : firstOdd;
				if (odd != null) {
					odd.next = head;
				}
				odd = head;
			} else {
				firstEven = firstEven == null ? head : firstEven;
				if (even != null) {
					even.next = head;
				}
				even = head;
			}
			count++;
			head = next;
		}
		if (odd != null) {
			odd.next = firstEven;
		}
		return firstOdd != null ? firstOdd : firstEven;
	}

}
