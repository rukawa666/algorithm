package com.rukawa.algorithm.leetcode.highfrequency;

import com.rukawa.algorithm.base.class04.HeapGreater;

import java.util.HashMap;

/**
 * create by hqh on 2022/9/26
 */
public class Problem_0460_LFUCache {
    /**
     * 请你为 最不经常使用（LFU）缓存算法设计并实现数据结构。
     * 实现 LFUCache 类：
     * LFUCache(int capacity) - 用数据结构的容量 capacity 初始化对象
     * int get(int key) - 如果键 key 存在于缓存中，则获取键的值，否则返回 -1 。
     * void put(int key, int value) - 如果键 key 已存在，则变更其值；如果键不存在，请插入键值对。当缓存达到其容量 capacity 时，
     * 则应该在插入新项之前，移除最不经常使用的项。在此问题中，当存在平局（即两个或更多个键具有相同使用频率）时，应该去除 最近最久未使用 的键。
     * 为了确定最不常使用的键，可以为缓存中的每个键维护一个 使用计数器 。使用计数最小的键是最久未使用的键。
     * 当一个键首次插入到缓存中时，它的使用计数器被设置为 1 (由于 put 操作)。对缓存中的键执行 get 或 put 操作，使用计数器的值将会递增。
     * 函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。
     *
     * 提示：
     * 0 <= capacity <= 104
     * 0 <= key <= 105
     * 0 <= value <= 109
     * 最多调用 2 * 105 次 get 和 put 方法
     */

    public static class LFUCache {
        // 思路：建立频次的桶结构
        // 频次桶中，每个节点的频次是一样的，桶中的结构有key，value，times，桶内的结构是双向链表
        // 每个频次桶，是DoubleLinkedList，是双向链表关联

        // 先删除词频低的数据，如果词频一样，把最早的删掉
        // 缓存的大小限制
        private int capacity;
        // 缓存目前的大小
        private int size;
        // 表示key由哪个节点代表
        private HashMap<Integer, Node> records;
        // 表示节点在哪个桶里面
        private HashMap<Node, NodeList> heads;
        // 整个结构中位于最左的桶  频次一定是最小的
        private NodeList headList;
        public LFUCache(int capacity) {
            this.capacity = capacity;
            size = 0;
            records = new HashMap<>();
            heads = new HashMap<>();
            headList = null;
        }

        public int get(int key) {
            if (!records.containsKey(key)) {
                return -1;
            } else {
                Node node = records.get(key);
                node.times++;
                NodeList curNodeList = heads.get(node);
                move(node, curNodeList);
                return node.val;
            }
        }

        public void put(int key, int value) {
            if (capacity == 0) {
                return;
            }
            if (records.containsKey(key)) {
                Node node = records.get(key);
                node.val = value;
                node.times++;

                NodeList curNodeList = heads.get(node);
                move(node, curNodeList);
            } else {
                if (size == capacity) {
                    Node node = headList.tail;
                    headList.deleteNode(node);
                    modifyHeadList(headList);
                    records.remove(node.key);
                    heads.remove(node);
                    size--;
                }
                Node node = new Node(key, value, 1);
                if (headList == null) {
                    headList = new NodeList(node);
                } else {
                    if (headList.head.times.equals(node.times)) {
                        headList.addNodeFromHead(node);
                    } else {
                        NodeList newNodeList = new NodeList(node);
                        newNodeList.next = headList;
                        headList.last = newNodeList;
                        headList = newNodeList;
                    }
                }
                records.put(key, node);
                heads.put(node, headList);
                size++;
            }
        }

        // 刚刚减少了一个节点的桶
        // 功能：判断减少节点的桶是不是空了
        // 如果不空，什么也不做
        // 如果空了，则要删除当前桶，当前桶的前后都要重新连接
        // 当前桶是头桶，则删除当前桶，头桶指向当前桶的下一个
        // 当前桶不是头桶，则删除当前桶，当前桶的上一个桶和下一个桶之间连接
        // 减少了一个节点的桶是不是已经空了，空了返回true，否则false
        private boolean modifyHeadList(NodeList removeNodeList) {
            if (removeNodeList.isEmpty()) {
                if (headList == removeNodeList) {
                    headList = removeNodeList.next;
                    if (headList != null) {
                        headList.last = null;
                    }
                } else {
                    removeNodeList.last.next = removeNodeList.next;
                    if (removeNodeList.next != null) {
                        removeNodeList.next.last = removeNodeList.last;
                    }
                }
                return true;
            }
            return false;
        }

        // node这个节点的频次+1了，这个节点原来在oldNodeList
        // 把node从oldNodeList删除，如果有频次+1的桶，则把该节点放到新桶的头部
        // 如果没有新桶，则要创建新桶，桶之间要连接
        private void move(Node node, NodeList oldNodeList) {
            oldNodeList.deleteNode(node);
            // preList表示次数+1的桶的前一个桶是谁
            // 如果oldNodeList删掉node之后还有节点，oldNodeList就是次数+1的桶的前一个桶
            // 如果oldNodeList删掉node之后空了，oldNodeList是需要删除的，所以次数+1的桶的前一个桶，是oldNodeList的前一个
            NodeList preList = modifyHeadList(oldNodeList) ? oldNodeList.last : oldNodeList;
            // nextList表示次数+1的桶的后一个桶是谁
            NodeList nextList = oldNodeList.next;
            if (nextList == null) {
                NodeList newList = new NodeList(node);
                if (preList != null) {
                    preList.next = newList;
                }
                newList.last = preList;
                if (headList == null) {
                    headList = newList;
                }
                heads.put(node, newList);
            } else {
                if (nextList.head.times.equals(node.times)) {
                    nextList.addNodeFromHead(node);
                    heads.put(node, nextList);
                } else {
                    NodeList newList = new NodeList(node);
                    if (preList != null) {
                        preList.next = newList;
                    }
                    newList.last = preList;
                    newList.next = nextList;
                    nextList.last = newList;
                    if (headList == nextList) {
                        headList = newList;
                    }
                    heads.put(node, newList);
                }
            }
        }

        public static class Node {
            private Integer key;
            private Integer val;
            // 当前节点发生get和set的次数总和
            private Integer times;
            // 节点之间是双向链表所以有上一个节点
            private Node up;
            // 节点之间是双向链表所以有下一个节点
            private Node down;

            public Node(int k, int v, int t) {
                this.key = k;
                this.val = v;
                this.times = t;
            }
        }

        public static class NodeList {
            // 桶的头节点
            private Node head;
            // 桶的尾节点
            private Node tail;
            // 桶之间是双向链表所以有前一个桶
            private NodeList last;
            // 桶之间是双向链表所以有下一个桶
            private NodeList next;

            public NodeList(Node node) {
                head = node;
                tail = node;
            }

            // 把一个新的节点放到桶内，新的节点都放在桶的顶端
            public void addNodeFromHead(Node node) {
                node.down = head;
                head.up = node;
                head = node;
            }

            // 判断这个桶是不是空的
            public boolean isEmpty() {
                return head == null;
            }

            // 保证node一定在桶内
            public void deleteNode(Node node) {
                if (head == tail) {
                    head = null;
                    tail = null;
                } else {
                    if (node == head) {
                        head = node.down;
                        head.up = null;
                    } else if (node == tail) {
                        tail = node.up;
                        tail.down = null;
                    } else {
                        node.up.down = node.down;
                        node.down.up = node.up;
                    }
                }
                node.up = null;
                node.down = null;
            }
        }
    }

}
