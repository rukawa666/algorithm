package com.rukawa.algorithm.base.class10_unionfind;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-25 0:11
 * @Version：1.0
 */
public class GraphGenerator {

    /**
     * matrix 所有的边
     * N * 3 矩阵
     * [weight，from节点上面的值，to节点上面的值]
     * @param matrix
     * @return
     */
    public static Graph createGraph(Integer[][] matrix) {
        Graph graph = new Graph();
        for (int i = 0; i < matrix.length; i++) {
            // 权重
            int weight = matrix[i][0];
            // from编号
            int from = matrix[i][1];
            // to编号
            int to = matrix[i][2];
            // 如果没有有from编号的点，则新建
            if (!graph.nodes.containsKey(from)) {
                graph.nodes.put(from, new Node(from));
            }
            // 类似
            if (!graph.nodes.containsKey(to)) {
                graph.nodes.put(to, new Node(to));
            }
            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);

            Edge newEdge = new Edge(weight, fromNode, toNode);
            // to点是from的直接邻居
            fromNode.nextS.add(toNode);
            fromNode.out++;
            toNode.in++;
            // 从fromNode出发的边
            fromNode.edges.add(newEdge);
            graph.edges.add(newEdge);
        }
        return graph;
    }
}
