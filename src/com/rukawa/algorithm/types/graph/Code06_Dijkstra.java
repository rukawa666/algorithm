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

    // 改进后的dijkstra算法
    // 从head出发，所有head能到达的节点，生成到达每个节点的最小路径记录并返回
    public static HashMap<Node, Integer> dijkstra2(Node from, int size) {
        NodeHeap nodeHeap = new NodeHeap(size);
        nodeHeap.addOrUpdateOrIgnore(from, 0);
        HashMap<Node, Integer> res = new HashMap<>();
        while (!nodeHeap.isEmpty()) {
            NodeRecord record = nodeHeap.pop();
            Node cur = record.node;
            int distance = record.distance;
            for (Edge edge : cur.edges) {
                nodeHeap.addOrUpdateOrIgnore(edge.to, edge.weight + distance);
            }
            res.put(cur, distance);
        }
        return res;
    }

    public static class NodeRecord {
        public Node node;
        public int distance;

        public NodeRecord(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    public static class NodeHeap {
        // 堆
        private Node[] nodes;
        // 反向索引表
        private HashMap<Node, Integer> heapIndexMap;
        // 点的距离，原出发点到当前点最优距离
        private HashMap<Node, Integer> distanceMap;
        private int size;

        public NodeHeap(int size) {
            this.nodes = new Node[size];
            this.heapIndexMap = new HashMap<>();
            this.distanceMap = new HashMap<>();
            this.size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        // 有一个点node，现在发现一个从源节点出发到达node的距离为distance
        // 判断要不要更新，如果需要的话，就更新
        public void addOrUpdateOrIgnore(Node node, int distance) {
            // update
            if (inHeap(node)) {
                distanceMap.put(node, Math.min(distanceMap.get(node), distance));
                // 向上调整，因为值只有变小，不可能变大
                insertHeapify(node, heapIndexMap.get(node));
            }
            // add
            if (!isEntered(node)) {
                nodes[size] = node;
                heapIndexMap.put(node, size);
                distanceMap.put(node, distance);
                insertHeapify(node, size++);
            }
            // Ignore
        }

        public NodeRecord pop() {
            NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
            swap(0, size - 1);
            heapIndexMap.put(nodes[size - 1], -1);
            distanceMap.remove(nodes[size - 1]);
            nodes[size - 1] = null;
            heapify(0, --size);
            return nodeRecord;
        }

        private void insertHeapify(Node node, int index) {
            while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])) {
                swap(index, (index - 1) / 2);
                node = nodes[(index - 1) / 2];
            }
        }

        private void heapify(int index, int size) {
            int left = index * 2 + 1;
            while (left < size) {
                int smallest = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left])
                        ? left + 1 : left;

                smallest = distanceMap.get(nodes[smallest]) < distanceMap.get(nodes[index]) ? smallest : index;
                if (smallest == index) {
                    break;
                }
                swap(index, smallest);
                index = smallest;
                left = index * 2 + 1;
            }
        }

        // 节点有没有进入过
        private boolean isEntered(Node node) {
            return heapIndexMap.containsKey(node);
        }

        // 节点是否在堆上
        private boolean inHeap(Node node) {
            return isEntered(node) && heapIndexMap.get(node) != -1;
        }

        private void swap(int i, int j) {
            heapIndexMap.put(nodes[i], j);
            heapIndexMap.put(nodes[j], i);
            Node tmp = nodes[i];
            nodes[i] = nodes[j];
            nodes[j] = tmp;
        }
    }
}
