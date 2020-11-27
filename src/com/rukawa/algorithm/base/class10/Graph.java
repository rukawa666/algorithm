package com.rukawa.algorithm.base.class10;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-24 19:56
 * @Version：1.0
 */
public class Graph {
    // 编号的点的集合
    public HashMap<Integer, Node> nodes;
    // 边的集合
    public HashSet<Edge> edges;

    public Graph() {
        this.nodes = new HashMap<>();
        this.edges = new HashSet<>();
    }
}
