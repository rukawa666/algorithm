package com.rukawa.algorithm.types.graph;

import java.util.ArrayList;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-29 9:25
 * @Version：1.0
 */
public class Node {
    // 点的编号
    public int value;
    // 入度
    public int in;
    // 出度
    public int out;
    // 相邻的点(出去的点)
    public ArrayList<Node> nextS;
    // 相邻的边(出发的边)
    public ArrayList<Edge> edges;

    public Node(int value) {
        this.value = value;
        this.in = 0;
        this.out = 0;
        nextS = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
