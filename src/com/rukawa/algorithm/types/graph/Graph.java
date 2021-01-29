package com.rukawa.algorithm.types.graph;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-29 9:29
 * @Version：1.0
 */
public class Graph {
    // 点的集合
    public HashMap<Integer, Node> nodes;
    // 边的集合
    public HashSet<Edge> edges;

    public Graph() {
        this.nodes = new HashMap<>();
        edges = new HashSet<>();
    }
}
