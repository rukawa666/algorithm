package com.rukawa.algorithm.base.class10_unionfind;

import java.util.*;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-25 8:55
 * @Version：1.0
 *
 * 拓扑排序：图没有环，有环的图没有拓扑排序的概念
 * 拓扑排序必须是有向无环图
 */
public class Code03_TopologySortBFS {

    // directed graph and no loop
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
