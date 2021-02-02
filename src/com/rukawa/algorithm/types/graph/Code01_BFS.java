package com.rukawa.algorithm.types.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-02-02 23:52
 * @Version：1.0
 */
public class Code01_BFS {

    // 图的宽度优先遍历
    public static void bfs(Node node) {
        if (node == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();
        queue.add(node);
        set.add(node);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            System.out.print(cur.value + " ");
            for (Node next : cur.nextS) {
                while (!set.contains(next)) {
                    queue.add(next);
                    set.add(next);
                }
            }
        }
    }
}
