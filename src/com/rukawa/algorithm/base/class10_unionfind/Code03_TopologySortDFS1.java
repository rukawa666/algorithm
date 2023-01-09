package com.rukawa.algorithm.base.class10_unionfind;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * create by hqh on 2022/9/4
 */
public class Code03_TopologySortDFS1 {

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

    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode, Record> order = new HashMap<>();
        for (DirectedGraphNode cur : graph) {
            f(cur, order);
        }
        ArrayList<Record> records = new ArrayList<>();
        for (Record record : order.values()) {
            records.add(record);
        }
        records.sort(new RecordComparator());

        // DirectedGraphNode的deep越大，说明拓扑排序要排在最前面
        ArrayList<DirectedGraphNode> res = new ArrayList<>();
        for (Record record : records) {
            res.add(record.node);
        }
        return res;
    }

    public static Record f(DirectedGraphNode cur, HashMap<DirectedGraphNode, Record> order) {
        if (order.containsKey(cur)) {
            return order.get(cur);
        }
        int deep = 0;
        for (DirectedGraphNode next : cur.neighbors) {
            deep = Math.max(deep, f(next, order).deep);
        }
        Record record = new Record(cur, deep + 1);
        order.put(cur, record);
        return record;
    }

    public static class Record {
        private DirectedGraphNode node;
        private int deep;

        public Record(DirectedGraphNode n, int d) {
            this.node = n;
            this.deep = d;
        }
    }

    public static class RecordComparator implements Comparator<Record> {

        @Override
        public int compare(Record o1, Record o2) {
            return o2.deep - o1.deep;
        }
    }
}
