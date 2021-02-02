package com.rukawa.algorithm.types.graph;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-02-02 23:45
 * @Version：1.0
 */
public class GraphGenerator {

    /**
     * matrix 所有的边
     * N * 3 矩阵
     * [weight，from节点上面的值，to节点上面的值]
     */
    public static Graph createGraph(Integer[][] matrix) {
        Graph graph = new Graph();
        for (int i = 0; i < matrix.length; i++) {
            int weight = matrix[i][0];
            int from = matrix[i][1];
            int to = matrix[i][2];
            if (!graph.nodes.containsKey(from)) {
                graph.nodes.put(from, new Node(from));
            }
            if (!graph.nodes.containsKey(to)) {
                graph.nodes.put(to, new Node(to));
            }
            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);
            fromNode.out++;
            fromNode.nextS.add(toNode);
            toNode.in++;

            Edge edge = new Edge(weight, fromNode, toNode);
            fromNode.edges.add(edge);
            graph.edges.add(edge);
        }
        return graph;
    }
}
