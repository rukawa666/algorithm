package com.rukawa.algorithm.trainingcamp.trainingcamp2.class8;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-17 23:52
 * @Version：1.0
 */
public class Code01_AC {

    // 前缀树的节点
    public static class Node {
        // 如果一个node，end为空，不是结尾
        // 如果end不为空，表示这个点事某个字符串的结尾，end的值就是这个字符串
        public String end;
        // 只有在上面end不为空的时候，endUse才有意义
        // 表示，这个字符串之前有没有加入过答案
        public boolean endUse;
        public Node fail;
        public Node[] nextS;

        public Node() {
            endUse = false;
            end = null;
            fail = null;
            nextS = new Node[26];
        }
    }

    public static class ACAutomation {
        private Node root;

        public ACAutomation() {
            root = new Node();
        }

        public void insert(String s) {
            char[] str = s.toCharArray();
            Node cur = root;
            int index = 0;
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                if (cur.nextS[index] == null) {
                    Node next = new Node();
                    cur.nextS[index] = next;
                }
                cur = cur.nextS[index];
            }
            cur.end = s;
        }

        public void build() {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            Node cur = null;
            Node curFail = null;
            while (!queue.isEmpty()) {
                // 当前节点弹出，当前节点的所有后代都加入到队列中去
                // 当前节点給它的子去设置fail指针
                cur = queue.poll();
                for (int i = 0; i < 26; i++) {  // 找到所有的路
                    if (cur.nextS[i] != null) { // 找到所有有效的路
                        cur.nextS[i].fail = root;
                        curFail = cur.fail;
                        while (curFail != null) {
                            if (curFail.nextS[i] != null) {
                                cur.nextS[i].fail = curFail.nextS[i];
                                break;
                            }
                            curFail = curFail.fail;
                        }
                        queue.add(cur.nextS[i]);
                    }
                }
            }
        }

        public List<String> containWords(String content) {
            char[] str = content.toCharArray();
            Node cur = root;
            Node follow = null;
            int path = 0;
            List<String> ans = new ArrayList<>();
            for (int i = 0; i < str.length; i++) {  // 依次遍历文章中的字符
                path = str[i] - 'a';
                // 如果当前字符在这条路上没匹配出来，就随着fail方向走向下条路径
                while (cur.nextS[path] == null && cur != root) {
                    cur = cur.fail;
                }
                // 1、现在来到的路径，是可以继续匹配的
                // 2、现在来到的节点，就是前缀树的根节点
                cur = cur.nextS[path] != null ? cur.nextS[path] : root;
                follow = cur;
                while (follow != root) {
                    if (follow.endUse) {
                        break;
                    }
                    // 不同的需求，在这一段之间修改
                    if (follow.end != null) {
                        ans.add(follow.end);
                        follow.endUse = true;
                    }
                    // 不同的需求，在这一段之间修改
                    follow = follow.fail;
                }
            }
            return ans;
        }
    }
}
