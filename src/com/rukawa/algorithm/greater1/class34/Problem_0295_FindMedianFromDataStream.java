package com.rukawa.algorithm.greater1.class34;

import java.util.PriorityQueue;

public class Problem_0295_FindMedianFromDataStream {
	/**
	 * 数据流的中位数
	 *
	 * 中位数是有序整数列表中的中间值。如果列表的大小是偶数，则没有中间值，中位数是两个中间值的平均值。
	 * 例如 arr = [2,3,4] 的中位数是 3 。
	 * 例如 arr = [2,3] 的中位数是 (2 + 3) / 2 = 2.5 。
	 * 实现 MedianFinder 类:
	 * MedianFinder() 初始化 MedianFinder 对象。
	 * void addNum(int num) 将数据流中的整数 num 添加到数据结构中。
	 * double findMedian() 返回到目前为止所有元素的中位数。与实际答案相差 10-5 以内的答案将被接受。
	 */
	class MedianFinder {

		private PriorityQueue<Integer> maxh;
		private PriorityQueue<Integer> minh;

		public MedianFinder() {
			maxh = new PriorityQueue<>((a, b) -> b - a);
			minh = new PriorityQueue<>((a, b) -> a - b);
		}

		public void addNum(int num) {
			if (maxh.isEmpty() || maxh.peek() >= num) {
				maxh.add(num);
			} else {
				minh.add(num);
			}
			balance();
		}

		public double findMedian() {
			if (maxh.size() == minh.size()) {
				return (double) (maxh.peek() + minh.peek()) / 2;
			} else {
				return maxh.size() > minh.size() ? maxh.peek() : minh.peek();
			}
		}

		private void balance() {
			if (Math.abs(maxh.size() - minh.size()) == 2) {
				if (maxh.size() > minh.size()) {
					minh.add(maxh.poll());
				} else {
					maxh.add(minh.poll());
				}
			}
		}

	}

}