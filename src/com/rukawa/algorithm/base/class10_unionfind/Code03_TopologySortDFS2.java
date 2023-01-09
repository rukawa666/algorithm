package com.rukawa.algorithm.base.class10_unionfind;


import java.util.*;

/**
 * create by hqh on 2022/9/4
 */
public class Code03_TopologySortDFS2 {
    /**
     * 原题是LintCode 127
     * https://www.lintcode.com/problem/127/
     */
    public static class DirectedGraphNode {
        int label;
        List<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            this.label = x;
            this.neighbors = new ArrayList<>();
        }
    }

    /**
     * 描述
     * 给定一个有向图，图节点的拓扑排序定义如下:
     * 对于图中的每一条有向边 A -> B , 在拓扑排序中A一定在B之前.
     * 拓扑排序中的第一个节点可以是图中的任何一个没有其他节点指向它的节点.
     * 针对给定的有向图找到任意一种拓扑排序的顺序.
     *
     * 你可以假设图中至少存在一种拓扑排序
     * 有关图的表示详情请看这里
     *
     * 图结点的个数 <= 5000
     * 样例
     * 样例 1：
     * 输入：
     * graph = {0,1,2,3#1,4#2,4,5#3,4,5#4#5}
     * 输出：
     * [0, 1, 2, 3, 4, 5]
     * 解释：
     * 拓扑排序可以为:
     * [0, 1, 2, 3, 4, 5]
     * [0, 2, 3, 1, 5, 4]
     * ...
     * 您只需要返回给定图的任何一种拓扑顺序。
     *
     * 挑战
     * 能否分别用BFS和DFS完成？
     */
    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode, Record> order = new HashMap<>();
        for (DirectedGraphNode cur : graph) {
            f(cur, order);
        }

        ArrayList<Record> recordList = new ArrayList<>();
        for (Record record : order.values()) {
            recordList.add(record);
        }
        recordList.sort(new MyComparator());
        ArrayList<DirectedGraphNode> res = new ArrayList<>();
        for (Record record : recordList) {
            res.add(record.node);
        }
        return res;
    }

    public static class Record {
        public DirectedGraphNode node;
        public long nodes;

        public Record(DirectedGraphNode node, long n) {
            this.node = node;
            this.nodes = n;
        }
    }

    public static class MyComparator implements Comparator<Record> {

        @Override
        public int compare(Record o1, Record o2) {
            return o1.nodes == o2.nodes ? 0 : (o1.nodes > o2.nodes) ? -1 : 1;
        }
    }

    /**
     * 当前来到cur点，请返回cur点所到之处所有的点次
     * 返回cur和点次
     *
     * order是缓存
     */
    public static Record f(DirectedGraphNode cur, HashMap<DirectedGraphNode, Record> order) {
        if (order.containsKey(cur)) {
            return order.get(cur);
        }
        // cur的点次之前没有算过
        long nodes = 0;
        for (DirectedGraphNode next : cur.neighbors) {
            nodes += f(next, order).nodes;
        }
        Record record = new Record(cur, nodes + 1);
        order.put(cur, record);
        return record;
    }
}
