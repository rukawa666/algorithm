package com.rukawa.algorithm.types.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-02-03 7:54
 * @Version：1.0
 */
public class Code06_Dijkstra {

    /**
     * 思路：
     *  1、Dijkstra算法必须指定一个源点
     *  2、生成一个源点到各个点的最小距离表，一开始只有一条记录，即原点到自己的最小距离为0，
     *     源点到其他所有点的最小距离都为正无穷大
     *  3、从距离表中拿出没拿过记录里的最小记录，通过这个点发出的边，更新源点到各个点的最小距
     *     离表，不断重复这一步
     *  4、源点到所有的点记录如果都被拿过一遍，过程停止，最小距离表得到
     */
    // 要求：无负边
    public static HashMap<Node, Integer> dijkstra1(Node from) {
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        distanceMap.put(from, 0);
        // 被选中的点，不能再次被选中
        HashSet<Node> selectedNodes = new HashSet<>();
        Node minNode = getMinDistanceAndUnSelectedNode(distanceMap, selectedNodes);
        while (minNode != null) {
            // 原始点 -> minNode(跳转点)，最小距离
            int distance = distanceMap.get(minNode);
            for (Edge edge : minNode.edges) {
                Node toNode = edge.to;
                // 原始点到toNode的距离是正无穷
                if (!distanceMap.containsKey(toNode)) {
                    distanceMap.put(toNode, distance + edge.weight);
                } else {
                    // toNode已经存在，更新最小距离
                    distanceMap.put(toNode, Math.min(distanceMap.get(toNode), distance + edge.weight));
                }
            }
            // minNode被锁定，下次不会再选中
            selectedNodes.add(minNode);
            // 再选中一个最小点
            minNode = getMinDistanceAndUnSelectedNode(distanceMap, selectedNodes);
        }
        return distanceMap;
    }

    public static Node getMinDistanceAndUnSelectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> selectedNodes) {
        Node minNode = null;
        int minDistance = Integer.MAX_VALUE;
        for (Map.Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node node = entry.getKey();
            int distance = entry.getValue();
            if (selectedNodes.contains(node) && distance < minDistance) {
                minNode = node;
                minDistance = distance;
            }
        }
        return minNode;
    }
}
