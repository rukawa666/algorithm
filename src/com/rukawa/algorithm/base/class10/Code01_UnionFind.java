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

        public HashMap<Node<V>, Node<V>> parents;

        public HashMap<Node<V>, Integer> sizeMap;

        public UnionSet(List<V> values) {
            for (V value : values) {
                Node<V> node = new Node<>(value);
                nodes.put(value, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        /**
         * 时间复杂度O(1)
         * @param cur
         * @return
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
