package com.rukawa.algorithm.types.graph;

import java.util.*;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-02-03 0:00
 * @Version：1.0
 */
public class Code03_TopologySort {

    // 图的拓扑排序
    // 有向无环图

    /**
     * 思路：
     *  1、在图中找到所有入度为0的的点输出
     *  2、把所有入度为0的点在图中删除，继续找入度为0的点输出，周而复始
     *  3、图的所有点都被删除后，依次输出的顺序就是拓扑排序
     *
     *  要求：有向图且其中无环
     *  应用：事件安排，编译顺序
     */
    public static List<Node> sortedTopology(Graph graph) {
        // key : 某一个Node
        // value ：Node还剩下多少入度in
        HashMap<Node, Integer> inMap = new HashMap<>();

        // 剩余入度为0的点，才能进入这个队列
        Queue<Node> zeroInQueue = new LinkedList<>();

        for (Node node : graph.nodes.values()) {
            inMap.put(node, node.in);
            if (node.in == 0) {
                zeroInQueue.add(node);
            }
        }

        // 拓扑排序的结果，依次加入result
        List<Node> result = new ArrayList<>();

        while (!zeroInQueue.isEmpty()) {
            Node cur = zeroInQueue.poll();
            result.add(cur);
            for (Node next : cur.nextS) {
                // 所有cur的邻居节点的入库都-1
                inMap.put(next, inMap.get(next) - 1);
                if (inMap.get(next) == 0) {
                    zeroInQueue.add(next);
                }
            }
        }
        return result;
    }
}
