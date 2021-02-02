package com.rukawa.algorithm.types.graph;

import java.util.*;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-02-03 0:06
 * @Version：1.0
 */
public class Code04_Kruskal {

    // 无向图最小生成树
    // K算法实现  即并查集

    /**
     * 思路：
     *  1、总是从权值最小的边开始考虑，依次考察权值依次变大的边
     *  2、当前的边要么进入最小生成树的集合，要么丢弃
     *  3、如果当前的边进入最小生成树的集合不会形成环，就要当前边
     *  4、如果当前的边进入最小生成树的集合会形成环，就不要当前边
     *  5、考察完所有边之后，最小生成树的集合也就得到了
     */
    public static Set<Edge> kruskalMST(Graph graph) {
        UnionFind uf = new UnionFind();
        uf.makeSets(graph.nodes.values());
        PriorityQueue<Edge> minHeap = new PriorityQueue<>(new EdgeComparator());
        for (Edge edge : graph.edges) { // M条边
            minHeap.add(edge);   // O(logM)
        }

        Set<Edge> res = new HashSet<>();
        while (!minHeap.isEmpty()) {    // M条边
            Edge curEdge = minHeap.poll();  // O(logM)
            // 判断边两头的集合是否是一个集合，如果不是则合并
            if (!uf.isSameSet(curEdge.from, curEdge.to)) {  // O(1)
                uf.union(curEdge.from, curEdge.to);
                res.add(curEdge);
            }
        }
        return res;
    }

    public static class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }



    public static class UnionFind {
        public HashMap<Node, Node> fatherMap;
        public HashMap<Node, Integer> sizeMap;

        public UnionFind() {
            fatherMap = new HashMap<>();
            sizeMap = new HashMap<>();
        }

        public void makeSets(Collection<Node> nodes) {
            fatherMap.clear();
            sizeMap.clear();
            for (Node cur : nodes) {
                fatherMap.put(cur, cur);
                sizeMap.put(cur, 1);
            }
        }

        public Node findFather(Node node) {
            Stack<Node> path = new Stack<>();
            while (node != fatherMap.get(node)) {
                path.push(node);
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
            Node aFather = findFather(a);
            Node bFather = findFather(b);
            while (aFather != bFather) {
                int aSize = sizeMap.get(aFather);
                int bSize = sizeMap.get(bFather);
                Node big = aSize >= bSize ? aFather : bFather;
                Node small = big == aFather ? bFather : aFather;
                fatherMap.put(small, big);
                sizeMap.put(big, aSize + bSize);
                sizeMap.remove(small);
            }
        }
    }




}
