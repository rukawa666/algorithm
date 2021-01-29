package com.rukawa.algorithm.types.graph;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-29 9:24
 * @Version：1.0
 */
public class Edge {
    // 边所在的权重
    public int weight;
    // 从哪个点进入
    public Node from;
    // 要去哪个点
    public Node to;

    public Edge(int weight, Node from, Node to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}
