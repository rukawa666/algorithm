package com.rukawa.algorithm.base.class10;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-24 8:24
 * @Version：1.0
 */
public class Code01_UnionFind {

    public static class Node<V> {
        V value;

        public Node(V v) {
            this.value = v;
        }
    }

    public static class UnionSet<V> {
        public HashMap<V, Node<V>> nodes;

        // 代表节点
        public HashMap<Node<V>, Node<V>> parents;

        // 因为要小挂大  比如1000大小的集合和2000大小的集合合并，小的挂大的肯定更优
        // sizeMap 可以知道有多少个集合
        public HashMap<Node<V>, Integer> sizeMap;

        public UnionSet(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V value : values) {
                Node<V> node = new Node<>(value);
                nodes.put(value, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        // 时间复杂度O(1)

        /**
         * 优化：把沿途经过的节点挂到最终的父节点
         *      a
         *     /
         *    b
         *   /
         *  c
         *
         *  从c往上找，经过b之后找到最终的父节点
         *     a
         *    / |
         *   b  c
         */
        public Node<V> findFather(Node<V> cur) {
            Stack<Node<V>> path = new Stack<>();
            while (cur != parents.get(cur)) {
                path.push(cur);
                cur = parents.get(cur);
            }

            while (!path.isEmpty()) {
                parents.put(path.pop(), cur);
            }
            return cur;
        }

        public boolean isSameSet(V a, V b) {
            if (!nodes.containsKey(a) || !nodes.containsKey(b)) {
                return false;
            }
            return findFather(nodes.get(a)) == findFather(nodes.get(b));
        }

        public void union(V a, V b) {
            if (!nodes.containsKey(a) || !nodes.containsKey(b)) {
                return;
            } else {
                Node<V> aHead = parents.get(nodes.get(a));
                Node<V> bHead = parents.get(nodes.get(b));
                if (aHead != bHead) {
                    int aSetSize = sizeMap.get(aHead);
                    int bSetSize = sizeMap.get(bHead);
                    Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
                    Node<V> small = big == aHead ? bHead : aHead;
                    parents.put(small, big);
                    sizeMap.put(big, aSetSize + bSetSize);
                    sizeMap.remove(small);
                }
            }
        }

        public int size() {
            return sizeMap.size();
        }
    }

}
