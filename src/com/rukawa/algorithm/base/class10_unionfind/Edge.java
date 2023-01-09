package com.rukawa.algorithm.base.class10_unionfind;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-24 19:53
 * @Version：1.0
 */
public class Edge {
    // 权重
    public int weight;
    // 出去
    public Node from;
    // 进入
    public Node to;

    public Edge(int weight, Node from, Node to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}
