package com.rukawa.algorithm.greater1.class10;

import java.util.*;

// 本题测试链接：https://www.lintcode.com/problem/top-k-frequent-words-ii/
// 以上的代码不要粘贴, 把以下的代码粘贴进java环境编辑器
// 把类名和构造方法名改成TopK, 可以直接通过
public class Code02_TopK {

	/**
	 * 前K个高频单词
	 * 给定一个单词列表 words 和一个整数 k ，返回前 k 个出现次数最多的单词。
	 * 返回的答案应该按单词出现频率由高到低排序。如果不同的单词有相同出现频率， 按字典顺序 排序。
	 *
	 * 示例 1：
	 * 输入: words = ["i", "love", "leetcode", "i", "love", "coding"], k = 2
	 * 输出: ["i", "love"]
	 * 解析: "i" 和 "love" 为出现次数最多的两个单词，均为2次。
	 *     注意，按字母顺序 "i" 在 "love" 之前。
	 *
	 * 示例 2：
	 * 输入: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4
	 * 输出: ["the", "is", "sunny", "day"]
	 * 解析: "the", "is", "sunny" 和 "day" 是出现次数最多的四个单词，
	 *     出现次数依次为 4, 3, 2 和 1 次。
	 *
	 * 注意：
	 * 1 <= words.length <= 500
	 * 1 <= words[i] <= 10
	 * words[i] 由小写英文字母组成。
	 * k 的取值范围是 [1, 不同 words[i] 的数量]
	 *
	 * 进阶：尝试以 O(n log k) 时间复杂度和 O(n) 空间复杂度解决。
	 */
	private Node[] heap;
	private int heapSize;
	// 词频表   key  abc   value  (abc,7)
	private HashMap<String, Node> strNodeMap;
	private HashMap<Node, Integer> nodeIndexMap;
	private NodeHeapComp comp;
	private TreeSet<Node> treeSet;
	
	public Code02_TopK(int K) {
		heap = new Node[K];
		heapSize = 0;
		strNodeMap = new HashMap<String, Node>();
		nodeIndexMap = new HashMap<Node, Integer>();
		comp = new NodeHeapComp();
		treeSet = new TreeSet<>(new NodeTreeSetComp());
	}

	public static class Node {
		public String str;
		public int times;

		public Node(String s, int t) {
			str = s;
			times = t;
		}
	}

	public static class NodeHeapComp implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			return o1.times != o2.times ? (o1.times - o2.times) : (o2.str.compareTo(o1.str));
		}

	}

	public static class NodeTreeSetComp implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			return o1.times != o2.times ? (o2.times - o1.times) : (o1.str.compareTo(o2.str));
		}

	}

	public void add(String str) {
		if (heap.length == 0) {
			return;
		}
		// str   找到对应节点  curNode
		Node curNode = null;
		// 对应节点  curNode  在堆上的位置
		int preIndex = -1;
		if (!strNodeMap.containsKey(str)) {
			curNode = new Node(str, 1);
			strNodeMap.put(str, curNode);
			nodeIndexMap.put(curNode, -1);
		} else {
			curNode = strNodeMap.get(str);
			// 要在time++之前，先在treeSet中删掉
			// 原因是因为一但times++，curNode在treeSet中的排序就失效了
			// 这种失效会导致整棵treeSet出现问题
			if (treeSet.contains(curNode)) {
				treeSet.remove(curNode);
			}
			curNode.times++;
			preIndex = nodeIndexMap.get(curNode);
		}
		if (preIndex == -1) {
			if (heapSize == heap.length) {
				if (comp.compare(heap[0], curNode) < 0) {
					treeSet.remove(heap[0]);
					treeSet.add(curNode);
					nodeIndexMap.put(heap[0], -1);
					nodeIndexMap.put(curNode, 0);
					heap[0] = curNode;
					heapify(0, heapSize);
				}
			} else {
				treeSet.add(curNode);
				nodeIndexMap.put(curNode, heapSize);
				heap[heapSize] = curNode;
				heapInsert(heapSize++);
			}
		} else {
			treeSet.add(curNode);
			heapify(preIndex, heapSize);
		}
	}

	public List<String> topk() {
		ArrayList<String> ans = new ArrayList<>();
		for (Node node : treeSet) {
			ans.add(node.str);
		}
		return ans;
	}

	private void heapInsert(int index) {
		while (index != 0) {
			int parent = (index - 1) / 2;
			if (comp.compare(heap[index], heap[parent]) < 0) {
				swap(parent, index);
				index = parent;
			} else {
				break;
			}
		}
	}

	private void heapify(int index, int heapSize) {
		int l = index * 2 + 1;
		int r = index * 2 + 2;
		int smallest = index;
		while (l < heapSize) {
			if (comp.compare(heap[l], heap[index]) < 0) {
				smallest = l;
			}
			if (r < heapSize && comp.compare(heap[r], heap[smallest]) < 0) {
				smallest = r;
			}
			if (smallest != index) {
				swap(smallest, index);
			} else {
				break;
			}
			index = smallest;
			l = index * 2 + 1;
			r = index * 2 + 2;
		}
	}

	private void swap(int index1, int index2) {
		nodeIndexMap.put(heap[index1], index2);
		nodeIndexMap.put(heap[index2], index1);
		Node tmp = heap[index1];
		heap[index1] = heap[index2];
		heap[index2] = tmp;
	}

}