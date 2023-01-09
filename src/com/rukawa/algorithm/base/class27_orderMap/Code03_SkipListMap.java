package com.rukawa.algorithm.base.class27_orderMap;

import java.util.ArrayList;

/**
 * create by hqh on 2023/1/8
 */
public class Code03_SkipListMap {

    /**
     * 跳表：
     * 在很多情况下。数据是通过链表这种数据结构存储的，如果是有序的链表，真的就没有办法使用二分查找算法了吗？
     * 对有序链表进行改造，实现二分查找链表，这就是跳表。
     */
    // 跳表的节点定义
    public static class SkipListNode<K extends Comparable<K>, V> {
        private K k;
        private V v;
        // 层数，如果有8层，则有8个Node
        private ArrayList<SkipListNode<K, V>> nextNodes;

        public SkipListNode(K key, V val) {
            k = key;
            v = val;
            nextNodes = new ArrayList<>();
        }

        // 遍历的时候，如果是往右遍历到的null(next==null)，遍历结束
        // 头(null)，头节点的null，认为最小
        // node -> 头，node(null, "") node.isKeyLess(!null) true
        // node里面的key是否比otherKey小，true，不是返回false
        public boolean isKeyLess(K otherKey) {
            // otherKey == null  -> false
            return otherKey != null && (k == null || k.compareTo(otherKey) < 0);
        }

        public boolean isKeyEqual(K otherKey) {
            return (k == null && otherKey == null) || (k != null && otherKey != null && k.compareTo(otherKey) == 0);
        }
    }

    public static class SkipListMap<K extends Comparable<K>, V> {
        // 随机因子，用于确定升层
        private static final double PROBABILITY = 0.5; // <0.5 继续做，>=0.5 停
        private SkipListNode<K, V> head;
        // 一共挂了多少个节点
        private int size;
        // 最大高度
        private int maxLevel;

        public SkipListMap() {
            // 整个跳表最左的巨小的点
            head = new SkipListNode<>(null, null);
            // 先让巨小的点有一层链表
            head.nextNodes.add(null); // 0
            size = 0;
            // 巨小的点有第0层，确定最大的层数
            maxLevel = 0;
        }

        // 从最高层开始，一路找下去
        // 最终，找到第0层的<key的最右的节点
        private SkipListNode<K, V> mostRightLessNodeInTree(K key) {
            if (key == null) {
                return null;
            }
            int level = maxLevel;
            SkipListNode<K, V> cur = this.head;
            while (level >= 0) { // 从上层跳下层
                cur = mostRightLessNodeInLevel(key, cur, level--);
            }
            return cur;
        }

        // 在level层里面，如何往右移动
        // 现在来到的节点是cur，来到了cur的level层，在level层上，找到<key最后一个节点并返回
        private SkipListNode<K,V> mostRightLessNodeInLevel(K key, SkipListNode<K,V> cur, int level) {
            SkipListNode<K, V> next = cur.nextNodes.get(level);
            while (next != null && next.isKeyLess(key)) {
                cur = next;
                next = cur.nextNodes.get(level);
            }
            return cur;
        }

        // 判断跳表中有没有当前的key
        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }
            // 从最高层开始找，找到第0层，离key最近的的最右的节点
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            // 第0层离key最近的的最右的节点往下一个节点
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null && next.isKeyEqual(key);
        }

        // 新增、修改value
        public void put(K key, V val) {
            if (key == null) {
                return;
            }
            // 从最高层开始找，找到第0层，离key最近的的最右的节点
            // 假设当前key是70，找到less是68
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            // 第0层离key最近的的最右的节点往下一个节点
            SkipListNode<K, V> find = less.nextNodes.get(0);
            if (find != null && find.isKeyEqual(key)) { // 下一个节点能找到，且和加入的key一样，说明之前加入过
                // 当前的key之前加入过，更新val即可
                find.v = val;
            } else { // 当前的key之前没有加入过，则加入节点
                size++;
                // 新节点永远拥有第0层
                int newNodeLevel = 0;
                // 随机 确定新节点的层数
                while (Math.random() < PROBABILITY) {
                    newNodeLevel++;
                }

                while (newNodeLevel > maxLevel) {
                    // 整个跳表最左的巨小的点的层数要保证和新节点的层数一样高
                    head.nextNodes.add(null);
                    // 整个跳表的高度要更新到新节点的层数
                    maxLevel++;
                }
                // 生成新的节点
                SkipListNode<K, V> newNode = new SkipListNode<>(key, val);
                // 给新的节点的每一层添加null链表
                for (int i = 0; i <= newNodeLevel; i++) {
                    newNode.nextNodes.add(null);
                }
                int level = maxLevel;
                SkipListNode<K, V> pre = this.head;
                while (level >= 0) {
                    // 找到当前层中<key的最右节点
                    pre = mostRightLessNodeInLevel(key, pre, level);
                    // 比如：头节点升到20层，但是新节点的层数只有6层，只有找到和新节点的层数小于等于的时候才会把新节点挂进去
                    if (level <= newNodeLevel) {
                        // 当前层中<key的最右节点 和 pre节点的下一个节点之间 挂新节点
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
            // 从最高层开始找，找到第0层，离key最近的的最右的节点
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            // 第0层离key最近的的最右的节点往下一个节点
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null && next.isKeyEqual(key) ? next.v : null;
        }

        // 跳表中删除一个key
        public void remove(K key) {
            if (containsKey(key)) {
                size--;
                int level = maxLevel;
                SkipListNode<K, V> pre = this.head;
                while (level >= 0) {
                    // 找到当前层中<key的最右节点
                    pre = mostRightLessNodeInLevel(key, pre, level);
                    // 当前层中<key的最右节点的下一个节点
                    SkipListNode<K, V> next = pre.nextNodes.get(level);
                    if (next != null && next.isKeyEqual(key)) { // 有当前删除的节点
                        // 当前层中<key的最右节点 和 next节点的下一个节点之间 删除next节点
                        pre.nextNodes.set(level, next.nextNodes.get(level));
                    }
                    // 在level层只有一个节点了，就默认是head节点
                    if (level != 0 && pre == head && pre.nextNodes.get(level) == null) {
                        // 把当前层的链表删除
                        head.nextNodes.remove(level);
                        // 最高层数-1
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

    // for test
    public static void printAll(SkipListMap<String, String> obj) {
        for (int i = obj.maxLevel; i >= 0; i--) {
            System.out.print("Level " + i + " : ");
            SkipListNode<String, String> cur = obj.head;
            while (cur.nextNodes.get(i) != null) {
                SkipListNode<String, String> next = cur.nextNodes.get(i);
                System.out.print("(" + next.k + " , " + next.v + ") ");
                cur = next;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SkipListMap<String, String> test = new SkipListMap<>();
        printAll(test);
        System.out.println("======================");
        test.put("A", "10");
        printAll(test);
        System.out.println("======================");
        test.remove("A");
        printAll(test);
        System.out.println("======================");
        test.put("E", "E");
        test.put("B", "B");
        test.put("A", "A");
        test.put("F", "F");
        test.put("C", "C");
        test.put("D", "D");
        printAll(test);
        System.out.println("======================");
        System.out.println(test.containsKey("B"));
        System.out.println(test.containsKey("Z"));
        System.out.println(test.firstKey());
        System.out.println(test.lastKey());
        System.out.println(test.floorKey("D"));
        System.out.println(test.ceilingKey("D"));
        System.out.println("======================");
        test.remove("D");
        printAll(test);
        System.out.println("======================");
        System.out.println(test.floorKey("D"));
        System.out.println(test.ceilingKey("D"));
    }
}
