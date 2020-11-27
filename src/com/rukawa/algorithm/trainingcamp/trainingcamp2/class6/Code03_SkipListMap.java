package com.rukawa.algorithm.trainingcamp.trainingcamp2.class6;

import java.util.ArrayList;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-14 8:00
 * @Version：1.0
 */
public class Code03_SkipListMap {

    // 跳表的节点定义
    public static class SkipListNode<K extends Comparable<K>, V> {
        public K key;
        public V value;
        public ArrayList<SkipListNode<K, V>> nextNodes;

        public SkipListNode(K key, V value) {
            this.key = key;
            this.value = value;
            nextNodes = new ArrayList<>();
        }

        /**
         * 遍历的时候，如果是向右遍历到null(next == null),遍历结束
         * 头(null)，头结点的null，认为最小
         * node -> 头，node(null, ""), node.isKeyLess(!null) true
         * node里面的key是否比otherKey小，true，否则false
         * @param otherKey
         * @return
         */
        public boolean isKeyLess(K otherKey) {
            return otherKey != null && (key == null || key.compareTo(otherKey) < 0);
        }

        public boolean isKeyEqual(K otherKey) {
            return (key == null && otherKey == null) || (key != null && otherKey != null && key.compareTo(otherKey) == 0);
        }
    }

    public static class SkipListMap<K extends Comparable<K>, V> {
        private static final double PROBABILITY = 0.5;  // < 0.5继续做，>= 0.5停止
        private SkipListNode<K, V> head;
        private int size;
        private int maxLevel;   // 最大高度

        public SkipListMap() {
            head = new SkipListNode<>(null, null);
            head.nextNodes.add(null);
            size = 0;
            maxLevel = 0;
        }

        // 从高层开始，一路找下去
        // 最终，找到第0层的<key的最右的节点
        private SkipListNode<K, V> mostRightLessNodeInTree(K key) {
            if (key == null) {
                return null;
            }

            int level = maxLevel;
            SkipListNode<K, V> cur = this.head;
            while (level >= 0) {    // 从上层跳下层
                cur = mostRightLessNodeInLevel(key, cur, level--);
            }
            return cur;
        }

        // 在level层里，如果向右移动
        // 现在来到的节点是cur，来到了cur的level层，在level层上，找到<key最右一个节点并返回
        private SkipListNode<K, V> mostRightLessNodeInLevel(K key, SkipListNode<K, V> cur, int level) {

            SkipListNode<K, V> next = cur.nextNodes.get(level);
            while (next != null && next.isKeyLess(key)) {
                cur = next;
                next = cur.nextNodes.get(level);
            }
            return cur;
        }


        public void put(K key, V value) {
            if (key == null) {
                return;
            }
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> find = less.nextNodes.get(0);
            if (find != null && find.isKeyEqual(key)) {
                find.value = value;
            } else {
                size++;
                int newNodeLevel = 0;
                while (Math.random() < PROBABILITY) {
                    newNodeLevel++;
                }
                while (newNodeLevel > maxLevel) {
                    head.nextNodes.add(null);
                    maxLevel++;
                }

                SkipListNode<K, V> newNode = new SkipListNode<>(key, value);
                for (int i = 0; i <= newNodeLevel; i++) {
                    newNode.nextNodes.add(null);
                }

                int level = maxLevel;
                SkipListNode<K, V> pre = this.head;
                while (level > 0) {
                    pre = mostRightLessNodeInLevel(key, pre, level);
                    if (level <= newNodeLevel) {
                        newNode.nextNodes.set(level, pre.nextNodes.get(level));
                        pre.nextNodes.set(level, newNode);
                    }
                    level--;
                }

            }

        }
    }
}
