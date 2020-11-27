package com.rukawa.algorithm.trainingcamp.trainingcamp3.class3;

import java.util.HashMap;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-25 21:40
 * @Version：1.0
 */
public class Code07_TopKTimesRealTime {
    /**
     * 请实现如下结构
     * TopRecord {
     *     public TopRecord(int K); // 构造时事先指定好K的大小，构造后就固定不变了
     *     public void add(String str); // 向该结构中加入一个字符串，可以重复加入
     *     public List<String> top();   // 返回之前加入的所有字符串中，词频最大的K个
     * }
     * 要求：
     *    add方法，复杂度为O(logK)
     *    top方法，复杂度为O(K)
     */
    public static class Node {
        public String str;
        public int times;

        public Node(String str, int times) {
            this.str = str;
            this.times = times;
        }
    }

    public static class TopKRecord {
        private Node[] heap;
        private int heapSize;

        private HashMap<String, Node> strNodeMap;
        private HashMap<Node, Integer> nodeIndexMap;

        public TopKRecord(int K) {
            heap = new Node[K];
            heapSize = 0;
            strNodeMap = new HashMap<>();
            nodeIndexMap = new HashMap<>();
        }

        public void add(String str) {
            Node curNode = null;
            int preIndex = -1; // str之前在堆上的位置
            // 查词频表，看看有没有关于这个str的记录
            if (!strNodeMap.containsKey(str)) { // str之前没进来过
                curNode = new Node(str, 1);
                strNodeMap.put(str, curNode);
                nodeIndexMap.put(curNode, -1);
            } else { // str之前进来过
                curNode = strNodeMap.get(str);
                curNode.times++;
                preIndex = nodeIndexMap.get(curNode);
            }

            // 词频表修改完毕，
            if (preIndex == -1) { // 不在堆上
                if (heapSize == heap.length) { // 堆满了
                    if (heap[0].times < curNode.times) {
                        nodeIndexMap.put(heap[0], -1);
                        nodeIndexMap.put(curNode, 0);
                        heap[0] = curNode;
                        heapify(0, heapSize);
                    }
                } else {// 堆没有满
                    nodeIndexMap.put(curNode, heapSize);
                    heap[heapSize] = curNode;
                    heapInsert(heapSize++);
                }
            } else { // str已经在堆上了
                heapify(preIndex, heapSize);
            }
        }

        public void printTopK() {
            System.out.println("TOP: ");
            for (int i = 0; i != heap.length; i++) {
                if (heap[i] == null) {
                    break;
                }
                System.out.print("Str: " + heap[i].str);
                System.out.println(" Times: " + heap[i].times);
            }
        }

        public void heapInsert(int index) {
            while (heap[index].times < heap[(index - 1) / 2].times) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        public void heapify(int index, int heapSize) {
            int l = index * 2 + 1;
            while (l < heapSize) {
                int largest = l + 1 < heapSize && heap[l + 1].times > heap[l].times ? l + 1 : l;
                largest = heap[largest].times > heap[index].times ? largest : index;
                if (index == largest) {
                    break;
                }
                swap(index, largest);
                index = largest;
                l = index * 2 + 1;
            }
        }

        public void swap(int i, int j) {
            nodeIndexMap.put(heap[i], j);
            nodeIndexMap.put(heap[j], i);
            Node tmp = heap[i];
            heap[i] = heap[j];
            heap[j] = tmp;
        }
    }

    public static String[] generateRandomArray(int len, int max) {
        String[] res = new String[len];
        for (int i = 0; i != len; i++) {
            res[i] = String.valueOf((int) (Math.random() * (max + 1)));
        }
        return res;
    }

    public static void printArray(String[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        TopKRecord record = new TopKRecord(2);
        record.add("zuo");
        record.printTopK();
        record.add("cheng");
        record.add("cheng");
        record.printTopK();
        record.add("Yun");
        record.add("Yun");
        record.printTopK();

    }
}
