package com.rukawa.algorithm.base.class10;

import java.util.ArrayList;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-24 19:54
 * @Version：1.0
 */
public class Node {
    // 编号
    public int value;
    // 入度 直接接收的边
    public int in;
    // 出度 直接出去的边
    public int out;
    // 直接邻居
    public ArrayList<Node> nextS;
    // 出发的边
    public ArrayList<Edge> edges;

    public Node(int value) {
        this.value = value;
        in = 0;
        out = 0;
        nextS = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
