package com.rukawa.algorithm.base.class10;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-25 11:56
 * @Version：1.0
 *
 * 无向图，最小生成树
 */
public class Code05_Prim {

    public static class EdgeComparator implements Comparator<Edge> {

        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    public static Set<Edge> primMST(Graph graph) {
        // 解锁的边进小根堆
        PriorityQueue<Edge> minHeap = new PriorityQueue<>(new EdgeComparator());

        // 哪些点被解锁出来了
        HashSet<Node> nodeSet = new HashSet<>();
        // 已经考虑过的边不要重复考虑
        HashSet<Edge> edgeSet = new HashSet<>();

        Set<Edge> result = new HashSet<>();
        for (Node node : graph.nodes.values()) {    // 随便挑选一个点，for循环为了防止森林

            if (!nodeSet.contains(node)) {
                nodeSet.add(node);
                for (Edge edge : node.edges) {  // 由一个点，解锁所有相连的边
                    if (!edgeSet.contains(edge)) {
                        minHeap.add(edge);
                        edgeSet.add(edge);
                    }
                }

                while (!minHeap.isEmpty()) {
                    Edge edge = minHeap.poll();   // 弹出解锁的边中，最小的边
                    Node toNode = edge.to;  // 可能的一个新点，fromNode已经在nodeSet中，只需考虑toNode是否包含
                    if (!nodeSet.contains(toNode)) {    // 不含有的时候，就是新的点
                        nodeSet.add(toNode);
                        result.add(edge);
                        for (Edge nextEdge : toNode.edges) {
                            if (!edgeSet.contains(nextEdge)) {
                                minHeap.add(nextEdge);
                                edgeSet.add(nextEdge);
                            }
                        }
                    }
                }
            }
            break; // 如果整个树是一个树，用break，如果是森林，则不加
        }
        return result;
    }

    public static Set<Edge> primMS2(Graph graph) {
        // 解锁的边进小根堆
        PriorityQueue<Edge> minHeap = new PriorityQueue<>(new EdgeComparator());
        // 哪些点被解锁出来了
        HashSet<Node> nodeSet = new HashSet<>();

        Set<Edge> result = new HashSet<>();
        for (Node node : graph.nodes.values()) {    // 随便挑选一个点，for循环为了防止森林

            if (!nodeSet.contains(node)) {
                nodeSet.add(node);
                for (Edge edge : node.edges) {  // 由一个点，解锁所有相连的边
                    minHeap.add(edge);
                }

                while (!minHeap.isEmpty()) {
                    Edge edge = minHeap.poll();   // 弹出解锁的边中，最小的边
                    Node toNode = edge.to;  // 可能的一个新点，fromNode已经在nodeSet中，只需考虑toNode是否包含
                    if (!nodeSet.contains(toNode)) {    // 不含有的时候，就是新的点
                        nodeSet.add(toNode);
                        result.add(edge);
                        for (Edge nextEdge : toNode.edges) {
                            minHeap.add(nextEdge);
                        }
                    }
                }
            }
            break;
        }
        return result;
    }
}
