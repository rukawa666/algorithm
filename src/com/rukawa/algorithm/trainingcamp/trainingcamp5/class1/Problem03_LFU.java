package com.rukawa.algorithm.trainingcamp.trainingcamp5.class1;

import java.util.HashMap;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-31 23:35
 * @Version：1.0
 */
public class Problem03_LFU {
    /**
     * 一个缓存结构需要实现如下功能
     * void set(int key, int value):加入或修改key对应的value
     * int get(int key):查询key对应的value值
     * 但是缓存中最多放K条记录，如果新的第K+1条记录要加入，就需要根据策略删掉一条记录，然后才能把新记录加入。
     * 这个策略为:
     * 在缓存结构的K条记录中，哪一个key从进入缓存结构的时刻开始，被调用set或者get的次数最少，就删掉这个key的记录;
     * 如果调用次数最少的key有多个，上次调用发生最早的key被删除。
     */

    /**
     * 要求：put方法和get方法要求时间复杂度为O(1)
     * 设计：
     *   1、一个大盒子放置一个个大桶
     *   2、然后桶之间的连接方式为双向链表
     *   3、每个桶内部的元素之间也是双向链表
     *
     * 怎么玩？
     *   1、建立一个map保存这个key对应的节点的内存地址是什么，一个节点可能在某个桶中的某个位置，
     *     直接记录key和节点的内存关系，不用遍历桶去找node，可以通过直接map直接找到这个node
     *   2、还有一个map，代表代表某个元素的node代表的桶是什么，可以通过node找到具体在哪个桶
     *   3、每次删除最左桶的尾节点
     */
    public static class Node {
        public Integer key;
        public Integer value;
        // 这个节点发生get或者set的次数总和
        public Integer times;
        // 节点之间是双向链表所以有上一个节点
        public Node up;
        // 节点之间是双向链表所以有下一个节点
        public Node down;

        public Node(Integer key, Integer value, Integer times) {
            this.key = key;
            this.value = value;
            this.times = times;
        }
    }

    public static class NodeList {
        public Node head;   // 桶的头节点
        public Node tail;   // 桶的尾结点
        public NodeList last;   // 桶之间是双向链表所以有上一个桶
        public NodeList next;   // 桶之间是双向链表所以有下一个桶

        public NodeList(Node node) {
            this.head = node;
            this.tail = node;
        }
        // 把一个新节点加入这个桶，新的节点都放在顶端变成新的头部
        public void addNodeFromHead(Node newHead) {
            newHead.down = head;
            head.up = newHead;
            head = newHead;
        }

        // 判断这个桶是否为空
        public boolean isEmpty() {
            return head == null;
        }

        // 删除node节点并保证node的上下环境重新连接
        public void deleteNode(Node node) {
            if (head == null) {
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

    // 总的缓存结构
    public static class LFUCache {
        // 缓存的大小限制，即K
        private int capacity;
        // 缓存目前有多少个节点
        private int size;
        // 表示key(Integer)有哪个节点(Node)代表
        private HashMap<Integer, Node> records;
        // 表示节点(Node)在哪个桶(NodeList)里面
        private HashMap<Node, NodeList> heads;
        // 整个结构中位于最左的桶
        private NodeList headList;

        public LFUCache(int K) {
            this.capacity = K;
            this.size = 0;
            records = new HashMap<>();
            heads = new HashMap<>();
            headList = null;
        }

        /**
         * removeNodeList：刚刚减少了一个节点的桶
         * 这个函数的功能是，判断刚刚减少了一个节点的桶是不是已经空了
         * 1、如果不空，什么都不做
         * 2、如果空了，removeNodeList还是整个缓存结构最左侧的桶，删除这个桶的同时也要让最左侧的桶变成removeNodeList的下一个
         * 3、如果空了，removeNodeList不是整合缓存结构最左的桶(headList)，把这个桶删除，
         *    并且保证上一个桶和下一个桶之间还是双向链表的连接方式
         */
        // 函数的返回值表示刚刚减少了一个节点的桶是不是已经空了，空了返回true，不空则返回false
        private boolean modifyHeadList(NodeList removeNodeList) {
            if (removeNodeList.isEmpty()) { // 移除的桶是空的
                if (headList == removeNodeList) {   // 该移除的桶就是最左桶
                    headList = removeNodeList.next; // 最左桶就是该桶的下一个桶
                    if (headList != null) {
                        headList.last = null; // 最左桶的上一个桶是null
                    }
                } else {    // 当前要移除的桶不是最左桶
                    removeNodeList.last.next = removeNodeList.next; // 移除桶的上一个桶的下一个桶是移除桶的下一个桶
                    if (removeNodeList.next != null) {  // 移除桶的下一个桶不为空
                        removeNodeList.next.last = removeNodeList.last; // 移除桶的下一个桶的上一个桶是移除桶的上一个桶
                    }
                }
                return true;
            }
            return false;
        }

        // 函数的功能
        // node这个节点的次数+1了，这个节点原来在oldNodeList桶里
        // 把node从oldNodeList删掉，然后放到次数+1的桶中
        // 整个过程既要保证桶之间是双向链表，也要保证节点之间仍然是双向链表
        private void move(Node node, NodeList oldNodeList) {
            // 当前桶删除该节点
            oldNodeList.deleteNode(node);
            // preNodeList表示次数+1的桶的前一个桶是谁
            // 如果oldNodeList删掉node之后还有节点，oldNodeList就是次数+1的桶的前一个桶
            // 如果oldNodeList删掉node之后空了，oldNodeList是需要删除的，所以次数+1的桶的前一个桶，是oldNodeList的前一个
            NodeList preNodeList = modifyHeadList(oldNodeList) ? oldNodeList.last : oldNodeList;
            // nextNodeList表示次数+1的桶的后一个桶是谁
            NodeList nextNodeList = oldNodeList.next;
            if (nextNodeList == null) {
                NodeList newList = new NodeList(node);
                if (preNodeList != null) {
                    preNodeList.next = newList;
                }
                newList.last = preNodeList;
                if (headList == null) {
                    headList = newList;
                }
                heads.put(node, newList);
            } else {
                if (nextNodeList.head.times.equals(node.times)) {
                    nextNodeList.addNodeFromHead(node);
                    heads.put(node, nextNodeList);
                } else {
                    NodeList newNodeList = new NodeList(node);
                    if (preNodeList != null) {
                        preNodeList.next = newNodeList;
                    }
                    newNodeList.last = preNodeList;
                    newNodeList.next = nextNodeList;
                    nextNodeList.last = newNodeList;
                    if (headList == nextNodeList) {
                        headList = newNodeList;
                    }
                    heads.put(node, newNodeList);
                }
            }
        }

        public void put(int key, int value) {
            // 容量大小限制为0，直接返回
            if (capacity == 0) {
                return;
            }
            // 如果有当前key对应的节点
            if (records.containsKey(key)) {
                Node node = records.get(key);
                node.value = value;
                node.times++;
                // 获取当前节点对应的桶
                NodeList curNodeList = heads.get(node);
                // 节点的次数+1，所以当前节点要换桶，当前桶要删除当前节点
                move(node, curNodeList);
            } else {    // 该节点是新节点
                if (size == capacity) { // 已经到达最大容量
                    Node node = headList.tail;  // 最左边桶的尾节点是次数最少，并且最早发生的节点，需要删除，给新节点腾空间
                    headList.deleteNode(node);  // 删除该节点
                    modifyHeadList(headList);
                    records.remove(node.key);   // 删除该key的记录
                    heads.remove(node); // 删除该节点对应桶记录的位置
                    size--; // 缩小容量
                }
                Node node = new Node(key, value,1); // 新增节点
                if (headList == null) { // 如果最左的桶为空，可能被上面给删除，则需要重建建立桶结构
                    headList = new NodeList(node);
                } else {    // 如果最左桶不为空
                    if (headList.head.times.equals(node.times)) { // 最左的桶次数为1的桶存在
                        headList.addNodeFromHead(node); // 最晚出现的节点放置在头节点，最后删除
                    } else {    // 最左的桶次数为1的桶不存在
                        // 重新建立次数为1的桶
                        NodeList newNodeList = new NodeList(node);
                        // 更新为最左桶
                        newNodeList.next = headList;
                        headList.last = newNodeList;
                        headList = newNodeList;
                    }
                }
                records.put(key, node); // 记录当前key对应哪个node
                heads.put(node, headList);  // 重新记录当前node对应哪个桶
                size++;
            }
        }

        public Integer get(Integer key) {
            // 当前key不存在node，直接返回
            if (!records.containsKey(key)) {
                return null;
            }
            // 获取当前节点
            Node node = records.get(key);
            node.times++;
            // 获取当前节点所在的桶
            NodeList curNodeList = heads.get(node);
            // 放置到别的桶，当前桶中删除该节点
            move(node, curNodeList);
            return node.value;
        }
    }
}
