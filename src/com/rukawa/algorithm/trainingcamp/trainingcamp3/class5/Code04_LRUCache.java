package com.rukawa.algorithm.trainingcamp.trainingcamp3.class5;

import java.util.HashMap;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 11:10
 * @Version：1.0
 */
public class Code04_LRUCache {

    public static class Node<K, V> {
        public K key;
        public V value;
        public Node<K, V> last;
        public Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public static class NodeDoubleLinkedList<K, V> {
        private Node<K, V> head;
        private Node<K, V> tail;

        public NodeDoubleLinkedList() {
            head = null;
            tail = null;
        }

        // 如果一个新的节点加入，放到尾巴上去
        public void addNode(Node<K, V> newNode) {
            if (newNode == null) {
                return;
            }

            if (head == null) { // 双向链表一个节点都没有
                head = newNode;
            } else {    // 双向链表中之前有节点，tail(非null)
                tail.next = newNode;
                newNode.last = tail;
            }
            tail = newNode;
        }

        // 潜台词：双向链表上一定有这个node
        // node分离出，但是node前后环境重新连接
        // node放到尾巴上去
        public void moveNodeToTail(Node<K, V> node) {
            if (this.tail == node) {
                return;
            }

            if (this.head == node) {    // 当前node是老头部
                this.head = node.next;
                this.head.last = null;
            } else {    // 当前node是中间的一个节点
                node.last.next = node.next;
                node.next.last = node.last;
            }

            // 此处node的影响已经消除，把node节点放到最后去
            node.last = this.tail;
            node.next = null;
            this.tail.next = node;
            this.tail = node;
        }

        /**
         * 删除头节点并返回
         * @return
         */
        public Node<K, V> removeHead() {
            if (this.head == null) {
                return null;
            }

            Node<K, V> res = this.head;
            if (this.head == this.tail) {   // 链表中只有一个节点的时候
                this.head = null;
                this.tail = null;
            } else {
                this.head = res.next;
                res.last = null;
                this.head.last = null;
            }
            return res;
        }
    }

    public static class MyCache<K, V> {
        private HashMap<K, Node<K, V>> keyNodeMap;
        private NodeDoubleLinkedList<K, V> nodeList;
        private final int capacity;

        public MyCache(int cap) {
            if (cap < 1) {
                throw new RuntimeException("should be more than 0.");
            }
            keyNodeMap = new HashMap<>();
            nodeList = new NodeDoubleLinkedList<>();
            this.capacity = cap;
        }

        public V get(K key) {
            if (keyNodeMap.containsKey(key)) {
                Node<K, V> res = keyNodeMap.get(key);
                nodeList.moveNodeToTail(res);
                return res.value;
            }
            return null;
        }

        public void put(K key, V value) {
            if (keyNodeMap.containsKey(key)) {
                Node<K, V> node = keyNodeMap.get(key);
                node.value = value;
                nodeList.moveNodeToTail(node);
            } else {    // 这是一个新加的记录，有可能出现替换
                Node<K, V> newNode = new Node<>(key, value);
                keyNodeMap.put(key, newNode);
                nodeList.addNode(newNode);
                if (keyNodeMap.size() == capacity + 1) {
                    removeMostUnusedCache();
                }
            }
        }

        public void removeMostUnusedCache() {
            Node<K, V> removeNode = nodeList.removeHead();
            keyNodeMap.remove(removeNode.key);
        }
    }
}
