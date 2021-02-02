package com.rukawa.algorithm.types.graph;

import java.util.HashSet;
import java.util.Stack;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-02-02 23:56
 * @Version：1.0
 */
public class Code02_DFS {

    // 图的深度优先遍历
    public static void dfs(Node node) {
        if (node == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        HashSet<Node> set = new HashSet<>();
        stack.add(node);
        set.add(node);
        System.out.print(node.value + " ");
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            for (Node next : cur.nextS) {
                if (!set.contains(next)) {
                    stack.push(cur);
                    stack.push(next);
                    set.add(next);
                    System.out.print(next.value + " ");
                    break;
                }
            }
        }
    }
}
