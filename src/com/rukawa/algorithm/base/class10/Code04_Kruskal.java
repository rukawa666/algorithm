package com.rukawa.algorithm.base.class10;

import java.util.*;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-25 10:40
 * @Version：1.0
 */
// undirected graph only
// 最小生成树
public class Code04_Kruskal {
    // Union-Find set
    public static class UnionFind {
        // key 某一个节点，value key节点往上的节点
        private HashMap<Node, Node> fatherMap;
        // key 某一个集合的代表节点，value key所在集合的节点个数
        private HashMap<Node, Integer> sizeMap;

        public UnionFind() {
            this.fatherMap = new HashMap<>();
            this.sizeMap = new HashMap<>();
        }

        // 初始化
        public void makeSets(Collection<Node> nodes) {
            fatherMap.clear();
            sizeMap.clear();
            for (Node node : nodes) {
                fatherMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public Node findFather(Node node) {
            Stack<Node> path = new Stack<>();
            while (node != fatherMap.get(node)) {
                path.add(node);
                node = fatherMap.get(node);
            }

            while (!path.isEmpty()) {
                fatherMap.put(path.pop(), node);
            }

            return node;
        }

        public boolean isSameSet(Node a, Node b) {
            if (a == null || b == null) {
                return false;
            }

            return findFather(a) == findFather(b);
        }

        public void union(Node a, Node b) {
            if (a == null || b == null) {
                return;
            }
            Node aHead = findFather(a);
            Node bHead = findFather(b);
            if (aHead != bHead) {
                int aSetSize = sizeMap.get(aHead);
                int bSetSize = sizeMap.get(bHead);
                Node big = aSetSize >= bSetSize ? aHead : bHead;
                Node small = big == aHead ? bHead : aHead;
                fatherMap.put(small, big);
                sizeMap.put(big, aSetSize + bSetSize);
                sizeMap.remove(small);
            }

        }
    }

    public static class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    public static Set<Edge> kruskalMST(Graph graph) {
        UnionFind unionFind = new UnionFind();
        unionFind.makeSets(graph.nodes.values());
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
        for (Edge edge : graph.edges) { // M条边
            priorityQueue.add(edge);    // O(logM)
        }

        Set<Edge> result = new HashSet<>();
        while (!priorityQueue.isEmpty()) {  // M条边
            Edge edge = priorityQueue.poll();   // O(logM)
            if (!unionFind.isSameSet(edge.from, edge.to)) { // O(1)
                result.add(edge);
                unionFind.union(edge.from, edge.to);
            }
        }
        return result;
    }
}
