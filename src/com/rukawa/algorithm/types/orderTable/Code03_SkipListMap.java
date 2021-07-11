package com.rukawa.algorithm.types.orderTable;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/7/5 0005 0:03
 */
public class Code03_SkipListMap {

    /**
     * 跳表：
     * 在很多情况下。数据是通过链表这种数据结构存储的，如果是有序的链表，真的就没有办法使用二分查找算法了吗？
     * 对有序链表进行改造，实现二分查找链表，这就是跳表。
     */

    public static class SkipListNode<K extends Comparable<K>, V> {
        public K k;
        public V v;
        // 层数，如果有8层，则有8个Node
        public ArrayList<SkipListNode<K, V>> nextNodes;

        public SkipListNode(K k, V v) {
            this.k = k;
            this.v = v;
            nextNodes = new ArrayList<>();
        }


        public boolean isKeyLess(K otherKey) {
            return otherKey != null && (k == null || k.compareTo(otherKey) < 0);
        }

        public boolean isKeyEqual(K otherKey) {
            return (k == null && otherKey == null) || (k != null && otherKey != null && k.compareTo(otherKey) == 0);
        }
    }

    public static class SkipListMap<K extends Comparable<K>, V> {
        private static final double PROBABILITY = 0.5;
        private SkipListNode<K, V> head;
        private int size;
        private int maxLevel;

        public SkipListMap() {
            head = new SkipListNode<>(null, null);
            head.nextNodes.add(null);
            size = 0;
            maxLevel = 0;
        }

        // 从高层开始，一路找下去
        // 最终，找到第0层 <key 的最右的节点
        private SkipListNode<K, V> mostRightLessNodeInTree(K key) {
            if (key == null) {
                return null;
            }
            int level = maxLevel;
            SkipListNode<K, V> cur = head;
            while (level >= 0) {
                // 在当前层 <key的最右节点
                cur = mostRightLessNodeInLevel(key, cur, level--);
            }
            return cur;
        }

        // 在level层，如果向右移动
        // 现在来到的节点是cur，来到cur的level层，在level层上，找到 <key 最后一个节点并返回
        private SkipListNode<K, V> mostRightLessNodeInLevel(K key, SkipListNode<K, V> cur, int level) {
            SkipListNode<K, V> next = cur.nextNodes.get(level);
            while (next != null && next.isKeyLess(key)) {
                cur = next;
                next = cur.nextNodes.get(level);
            }
            return cur;
        }

        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }
            SkipListNode<K, V> lessNode = mostRightLessNodeInTree(key);
            SkipListNode<K, V> nextNode = lessNode.nextNodes.get(0);
            return nextNode != null && nextNode.isKeyEqual(key);
        }

        public void put(K key, V value) {
            if (key == null) {
                return;
            }
            // 第0层，最右一个 <key的最右一个Node
            // 假设当前key是70，找到less是68
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            // 第0层，<key 的最右一个节点的下一个节点
            // 如果less是68,70存在，则find为70；否则大于70，此时把70插入到中间
            SkipListNode<K, V> find = less.nextNodes.get(0);
            // 如果key存在，则只需要更新value
            if (find != null && find.isKeyEqual(key)) {
                find.v = value;
            } else {    // 新增节点
                size++;
                // 永远拥有第0层链表
                int newNodeLevel = 0;
                // 随机得出newNode节点的层数
                while (Math.random() < PROBABILITY) {
                    newNodeLevel++;
                }
                // 之前最高的层数小于新链表的层数，最高层数则需要补齐到新链表的层数高度
                while (newNodeLevel > maxLevel) {
                    head.nextNodes.add(null);
                    maxLevel++;
                }

                SkipListNode<K, V> newNode = new SkipListNode<>(key, value);
                for (int i = 0; i <= newNodeLevel; i++) {
                    newNode.nextNodes.add(null);
                }

                int level = maxLevel;
                SkipListNode<K, V> pre = head;
                while (level >= 0) {
                    pre = mostRightLessNodeInLevel(key, pre, level);
                    if (level <= newNodeLevel) {
                        newNode.nextNodes.set(level, pre.nextNodes.get(level));
                        pre.nextNodes.set(level, newNode);
                    }
                    level--;
                }
            }
        }

        public V get(K key) {
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> lessNode = mostRightLessNodeInTree(key);
            SkipListNode<K, V> nextNode = lessNode.nextNodes.get(0);
            return nextNode != null && nextNode.isKeyEqual(key) ? nextNode.v : null;
        }

        public void remove(K key) {
            if (containsKey(key)) {
                size--;
                int level = maxLevel;
                SkipListNode<K, V> pre = head;
                while (level >= 0) {
                    pre = mostRightLessNodeInLevel(key, pre, level);
                    SkipListNode<K, V> next = pre.nextNodes.get(level);
                    if (next != null && next.isKeyEqual(key)) {
                        pre.nextNodes.set(level, next.nextNodes.get(level));
                    }

                    if (level != 0 && pre == head && pre.nextNodes.get(level) == null) {
                        head.nextNodes.remove(level);
                        maxLevel--;
                    }
                    level--;
                }
            }
        }

        public K firstKey() {
            return head.nextNodes.get(0) != null ? head.nextNodes.get(0).k : null;
        }

        // 从最高层到最底层，最后一个key
        public K lastKey() {
            int level = maxLevel;
            SkipListNode<K, V> cur = head;
            while (level >= 0) {
                SkipListNode<K, V> next = cur.nextNodes.get(level);
                while (next != null) {
                    cur = next;
                    next = cur.nextNodes.get(level);
                }
                level--;
            }
            return cur.k;
        }

        // 返回大于或者等于给定key的最小key
        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null ? next.k : null;
        }

        // 返回小于或者等于给定key的最大key
        public K floorKey(K key) {
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> lessNode = mostRightLessNodeInTree(key);
            SkipListNode<K, V> nextNode = lessNode.nextNodes.get(0);
            return nextNode != null && nextNode.isKeyEqual(key) ? nextNode.k : lessNode.k;
        }

        public int size() {
            return size;
        }
    }
}
