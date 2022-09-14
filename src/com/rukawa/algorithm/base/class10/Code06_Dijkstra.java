package com.rukawa.algorithm.base.class10;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-25 13:01
 * @Version：1.0
 */
// no negative weight
public class Code06_Dijkstra {

    public static class NodeRecord {
        public Node node;
        public int distance;

        public NodeRecord(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    public static class NodeHeap {
        private Node[] nodes;   // 实际的堆结构
        // key 某一个node，value 上面堆中的位置
        // heapIndexMap value=-1 曾经进来过，但是出去了
        private HashMap<Node, Integer> heapIndexMap;
        // key 某一个node，value 从源节点出发到该节点的的目前最小距离
        private HashMap<Node, Integer> distanceMap;
        // 堆上有多少个点
        private int size;

        public NodeHeap(int size) {
            this.nodes = new Node[size];
            this.heapIndexMap = new HashMap<>();
            this.distanceMap = new HashMap<>();
            this.size = 0;
        }

        /**
         * 从一个点node，现在发现了一个源节点出发到达node的距离为distance
         * 判断要不要更新，如果需要更新的话，就更新
         * @param node
         * @param distance
         */
        public void addOrUpdateOrIgnore(Node node, int distance) {
            // 节点在小根堆，修改
            if (inHeap(node)) {
                distanceMap.put(node, Math.min(distanceMap.get(node), distance));
                // 向上调整
                insertHeapify(node, heapIndexMap.get(node));
            }

            // 不在小根堆，属于新增
            if (!isEntered(node)) {
                nodes[size] = node;
                heapIndexMap.put(node, size);
                distanceMap.put(node, distance);
                // 往上做调整
                insertHeapify(node, size++);
            }
        }

        public NodeRecord pop() {
            // 弹出0位置最小的节点
            NodeRecord nodeRecord = new NodeRecord(nodes[0], heapIndexMap.get(nodes[0]));
            swap(0, size - 1);
            heapIndexMap.put(nodes[size - 1], -1);
            distanceMap.remove(nodes[size - 1]);
            nodes[size - 1] = null;
            heapify(0, --size);
            return nodeRecord;
        }


        public void insertHeapify(Node node, int index) {
            while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        public void heapify(int index, int size) {
            int left = index * 2 + 1;
            while (left < size) {
                int smallest = left + 1 < size
                        && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left])
                        ? left + 1 : left;
                smallest = distanceMap.get(nodes[smallest]) < distanceMap.get(nodes[index])
                        ? smallest : index;

                if (smallest == index) {
                    break;
                }
                swap(smallest, index);
                index = smallest;
                left = index << 1 | 1; // left = index * 2 + 1;
            }
        }

        public void swap(int x, int y) {
            heapIndexMap.put(nodes[x], y);
            heapIndexMap.put(nodes[y], x);
            Node tmp = nodes[x];
            nodes[x] = nodes[y];
            nodes[y] = tmp;
        }

        public boolean isEmpty() {
            return size == 0;
        }
        // 节点进来过
        public boolean isEntered(Node node) {
            return heapIndexMap.containsKey(node);
        }
        // 节点是否在堆上，进来过且在堆中的位置不为-1
        public boolean inHeap(Node node) {
            return isEntered(node) && heapIndexMap.get(node) != -1;
        }
    }

    /**
     * 改进后的dijkstra算法
     * 从head出发，所有head能到达的节点，生成到达每个节点的最小路径记录并返回
     *
     * 慢：每一次都要遍历找到最小值，然后去更新
     * @param from
     * @return
     */
    public static HashMap<Node, Integer> dijkstra02(Node from, int size) {
        NodeHeap nodeHeap = new NodeHeap(size);
        nodeHeap.addOrUpdateOrIgnore(from, 0);

        HashMap<Node, Integer> result = new HashMap<>();
        while (!nodeHeap.isEmpty()) {
            NodeRecord nodeRecord = nodeHeap.pop();
            Node cur = nodeRecord.node;
            int distance = nodeRecord.distance;
            for (Edge edge : cur.edges) {
                nodeHeap.addOrUpdateOrIgnore(edge.to, edge.weight + distance);
            }
            result.put(cur, distance);
        }
        return result;
    }

    public static HashMap<Node, Integer> dijkstra01(Node from) {

        /**
         * 从head出发到所有点的最小距离
         * key ： 从head出发到达key
         * value ：从head出发到达key的最小距离
         * 如果在表中，没有T的记录，含义是从head出发到T这个点的距离为正无穷
         */
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        distanceMap.put(from, 0);
        // 已经求过距离的节点，存在selectedNodes中，以后再也不碰
        HashSet<Node> selectedNodes = new HashSet<>();

        // fromNode 0
        Node minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        while (minNode != null) {
            // 原始点到minNode的最小距离
            int distance = distanceMap.get(minNode);
            for (Edge edge : minNode.edges) {
                Node toNode = edge.to;
                if (!distanceMap.containsKey(toNode)) {
                    distanceMap.put(toNode, distance + edge.weight);
                } else {
                    distanceMap.put(toNode, Math.min(distanceMap.get(toNode), distance + edge.weight));
                }
            }
            selectedNodes.add(minNode);
            minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        }
        return distanceMap;

    }

    public static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> touchedNodes) {
        Node minNode = null;
        int minDistance = Integer.MAX_VALUE;
        for (Map.Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node node = entry.getKey();
            int distance = entry.getValue();
            if (!touchedNodes.contains(node) && distance < minDistance) {
                minDistance = distance;
                minNode = node;
            }
        }
        return minNode;
    }

}
