package com.rukawa.algorithm.base.class10_unionfind;

import java.util.HashSet;
import java.util.Stack;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-24 20:01
 * @Version：1.0
 */
public class Code02_DFS {

    /**
     *        a
     *    /  |  \
     *   b - c   d
     *   |  /
     *   e
     *
     *   压入a：a
     *   压入b：b，a
     *   压入e：e，b，a
     *   压入c：c，e，b，a
     */
    public static void dfs(Node node) {
        if (node == null) {
            return;
        }

        Stack<Node> stack = new Stack<>();
        HashSet<Node> set = new HashSet<>();
        stack.add(node);
        set.add(node);
        // 进入栈就打印
        System.out.println(node.value);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            for (Node next : cur.nextS) {
                if (!set.contains(next)) {
                    stack.push(cur);
                    stack.push(next);
                    set.add(next);
                    System.out.println(next.value);
                    break;
                }
            }
        }
    }
}
