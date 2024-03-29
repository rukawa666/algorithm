package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.HashMap;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 11:06
 * @Version：1.0
 */
public class Problem_0146_LRUCache {
    /**
     * LRU缓存机制
     * 运用你所掌握的数据结构，设计和实现一个  LRU (最近最少使用) 缓存机制。它应该支持以下操作： 获取数据 get 和 写入数据 put 。
     * 获取数据 get(key) - 如果关键字 (key) 存在于缓存中，则获取关键字的值（总是正数），否则返回 -1。
     * 写入数据 put(key, value) - 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字/值」。
     * 当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
     *
     * 进阶:
     * 你是否可以在 O(1) 时间复杂度内完成这两种操作？
     *
     * 示例:
     * LRUCache cache = new LRUCache(2); // 缓存容量
     * cache.put(1,1);
     * cache.put(2,2);
     * cache.get(1);       // 返回  1
     * cache.put(3,3);    // 该操作会使得关键字 2 作废
     * cache.get(2);       // 返回 -1 (未找到)
     * cache.put(4,4);    // 该操作会使得关键字 1 作废
     * cache.get(1);       // 返回 -1 (未找到)
     * cache.get(3);       // 返回  3
     * cache.get(4);       // 返回  4
     */
    public static class LRUCache {
        // 扔掉最长时间不操作的记录：双向链表+哈希表实现
        // 哈希表存放key，key+value打包成一个节点，节点是双向链表结构，有last和next指针
        private MyCache cache;
        public LRUCache(int capacity) {
            cache = new MyCache(capacity);
        }

        public int get(int key) {
            Integer value = (Integer) cache.get(key);
            return value == null ? -1 : value;
        }

        public void put(int key, int value) {
            cache.set(key, value);
        }

        public static class MyCache<K, V> {
            private HashMap<K, Node<K, V>> keyNodeMap;
            private DoubleLinkedList<K, V> nodeList;
            private final int capacity;

            public MyCache(int capacity) {
                this.keyNodeMap = new HashMap<>();
                this.nodeList = new DoubleLinkedList<>();
                this.capacity = capacity;
            }

            public V get(K key) {
                if (keyNodeMap.containsKey(key)) {
                    Node<K, V> node = keyNodeMap.get(key);
                    nodeList.moveNodeToTail(node);
                    return node.val;
                }
                return null;
            }

            // set(key,val)
            // 新增或者更新操作
            public void set(K key, V value) {
                if (keyNodeMap.containsKey(key)) {
                    Node<K, V> node = keyNodeMap.get(key);
                    node.val = value;
                    nodeList.moveNodeToTail(node);
                } else {
                    Node<K, V> newNode = new Node<>(key, value);
                    keyNodeMap.put(key, newNode);
                    nodeList.addNode(newNode);
                    if (keyNodeMap.size() == capacity + 1) {
                        removeMostUnusedCache();
                    }
                }
            }

            public void removeMostUnusedCache() {
                Node<K, V> head = nodeList.removeHead();
                keyNodeMap.remove(head.key);
            }
        }

        public static class Node<K, V> {
            K key;
            V val;
            Node<K, V> last;
            Node<K, V> next;
            public Node(K k, V v) {
                this.key = k;
                this.val = v;
            }
        }

        public static class DoubleLinkedList<K, V> {
            private Node<K, V> head;
            private Node<K, V> tail;

            public DoubleLinkedList() {
                this.head = null;
                this.tail = null;
            }

            // 新节点挂到尾巴
            public void addNode(Node<K, V> node) {
                if (node == null) {
                    return;
                }
                if (head == null) {
                    head = node;
                    tail = node;
                } else {
                    tail.next = node;
                    node.last = tail;
                    tail = node;
                }
            }

            // 移动节点到尾巴上
            // node 一定保证在双端链表中
            // node原始的位置，左右重新连好，然后node从链表中分离出来
            // 挂到链表的尾巴上
            public void moveNodeToTail(Node<K, V> node) {
                if (this.tail == node) {
                    return;
                }
                if (this.head == node) {
                    this.head = node.next;
                    this.head.last = null;
                } else {
                    node.last.next = node.next;
                    node.next.last = node.last;
                }
                node.last = this.tail;
                node.next = null;
                this.tail.next = node;
                this.tail = node;
            }

            public Node<K, V> removeHead() {
                if (this.head == null) {
                    return null;
                }
                Node<K, V> node = this.head;
                if (this.head == this.tail) {
                    this.head = null;
                    this.tail = null;
                } else {
                    this.head = node.next;
                    this.head.last = null;
                    node.next = null;
                }
                return node;
            }
        }
    }
//    public static class LRUCache {
//
//        public static class Node<K, V> {
//            public K key;
//            public V value;
//            public Node<K, V> last;
//            public Node<K, V> next;
//
//            public Node(K key, V value) {
//                this.key = key;
//                this.value = value;
//            }
//        }
//
//        public static class NodeDoubleLinkedList<K, V> {
//            private Node<K, V> head;
//            private Node<K, V> tail;
//
//            public NodeDoubleLinkedList() {
//                head = null;
//                tail = null;
//            }
//
//            // 如果一个新的节点加入，放到尾巴上去
//            public void addNode(Node<K, V> newNode) {
//                if (newNode == null) {
//                    return;
//                }
//
//                if (head == null) { // 双向链表一个节点都没有
//                    head = newNode;
//                } else {    // 双向链表中之前有节点，tail(非null)
//                    tail.next = newNode;
//                    newNode.last = tail;
//                }
//                tail = newNode;
//            }
//
//            // 潜台词：双向链表上一定有这个node
//            // node分离出，但是node前后环境重新连接
//            // node放到尾巴上去
//            public void moveNodeToTail(Node<K, V> node) {
//                if (this.tail == node) {
//                    return;
//                }
//
//                if (this.head == node) {    // 当前node是老头部
//                    this.head = node.next;
//                    this.head.last = null;
//                } else {    // 当前node是中间的一个节点
//                    node.last.next = node.next;
//                    node.next.last = node.last;
//                }
//
//                // 此处node的影响已经消除，把node节点放到最后去
//                node.last = this.tail;
//                node.next = null;
//                this.tail.next = node;
//                this.tail = node;
//            }
//
//            /**
//             * 删除头节点并返回
//             * @return
//             */
//            public Node<K, V> removeHead() {
//                if (this.head == null) {
//                    return null;
//                }
//
//                Node<K, V> res = this.head;
//                if (this.head == this.tail) {   // 链表中只有一个节点的时候
//                    this.head = null;
//                    this.tail = null;
//                } else {
//                    this.head = res.next;
//                    res.last = null;
//                    this.head.last = null;
//                }
//                return res;
//            }
//        }
//
//        private HashMap<Integer, Node<Integer, Integer>> keyNodeMap;
//        private NodeDoubleLinkedList<Integer, Integer> nodeList;
//        private final int capacity;
//
//        public LRUCache(int capacity) {
//            if (capacity < 1) {
//                throw new RuntimeException("should be more than 0.");
//            }
//            keyNodeMap = new HashMap<>();
//            nodeList = new NodeDoubleLinkedList<>();
//            this.capacity = capacity;
//        }
//
//        public int get(int key) {
//            if (keyNodeMap.containsKey(key)) {
//                Node<Integer, Integer> res = keyNodeMap.get(key);
//                nodeList.moveNodeToTail(res);
//                return res.value;
//            }
//            return -1;
//        }
//
//        public void put(int key, int value) {
//            if (keyNodeMap.containsKey(key)) {
//                Node<Integer, Integer> node = keyNodeMap.get(key);
//                node.value = value;
//                nodeList.moveNodeToTail(node);
//            } else {    // 这是一个新加的记录，有可能出现替换
//                Node<Integer, Integer> newNode = new Node<>(key, value);
//                keyNodeMap.put(key, newNode);
//                nodeList.addNode(newNode);
//                if (keyNodeMap.size() == capacity + 1) {
//                    removeMostUnusedCache();
//                }
//            }
//        }
//
//        public void removeMostUnusedCache() {
//            Node<Integer, Integer> removeNode = nodeList.removeHead();
//            keyNodeMap.remove(removeNode.key);
//        }
//    }
}
