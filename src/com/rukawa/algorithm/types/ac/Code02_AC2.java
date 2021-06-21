package com.rukawa.algorithm.types.ac;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/6/21 0021 9:04
 */
public class Code02_AC2 {

    public static class Node {
        // 如果一个node，end为空，不是字符串结尾位置
        // 如果end不为空，表示这个点是某个字符串的结尾，end的值就是这个字符串
        public String end;
        // 只有在上面的end变量不为空的时候，endUse才有意义
        // 表示，这个字符串之前有没有加入过答案
        public boolean endUse;
        public Node fail;
        // 前缀树下级的路
        public Node[] nextS;

        public Node() {
            end = null;
            endUse = false;
            fail = null;
            nextS = new Node[26];
        }
    }


    public static class ACAutomation {
        private Node root;

        public ACAutomation() {
            root = new Node();
        }

        // 只建立前缀串
        public void insert(String s) {
            char[] str = s.toCharArray();
            Node cur = root;
            int index = 0;
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                if (cur.nextS[index] == null) {
                    cur.nextS[index] = new Node();
                }
                cur = cur.nextS[index];
            }
            cur.end = s;
        }

        public void build() {
            // 宽度优先遍历，把根节点放进去
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            Node cur = null;
            Node cFail = null;
            /**
             * 父节点弹出设置它子节点的fail指针
             * 不去设置x指针弹出，设置它的fail该指向谁，讨论任何一个节点x出来的时候，所有子节点的fail指针，在当前步骤设置好
             * 为什么？因为x弹出的时候，找不到它的父节点，前缀树没有向上指的指针
             */
            while (!queue.isEmpty()) {
                // 某个父亲节点
                cur = queue.poll();
                // 所有的路
                for (int i = 0; i < 26; i++) {
                    // 找到所有有效的路，只有挂节点才是有路
                    // cur -> 父亲 i号儿子，必须把i号儿子的fail指针设置好
                    if (cur.nextS[i] != null) { // 如果有i号儿子
                        // 每一个节点没有找到，设置为root
                        cur.nextS[i].fail = root;
                        // 父节点的cFail
                        cFail = cur.fail;
                        while (cFail != null) {
                            // 如果cFail有子节点，则直接跳过去
                            if (cFail.nextS[i] != null) {
                                cur.nextS[i].fail = cFail.nextS[i];
                                break;
                            }
                            // 没有继续跳
                            cFail = cFail.fail;
                        }
                        queue.add(cur.nextS[i]);
                    }
                }
            }
        }

        /**
         * 敏感词："abcd", "abc", "bc", "a"
         * 大文章："abcd"
         * 查敏感词的过程：
         *  1、在前缀树找有没有a的路，有，则顺着fail指针转一圈，找到有a结尾的敏感词
         *  2、继续往b走，停止b，顺着fail指针转一圈，沿途节点没有b结尾的敏感词
         *  3、来到c，顺着c的fail指针转一圈，沿途有abc和bc的敏感词
         *  4、来到d，顺着d的fail指针赚一圈，有abcd的敏感词
         */
        // content 大文章
        public List<String> containWords(String content) {
            char[] str = content.toCharArray();
            Node cur = root;
            Node follow = null;
            int index = 0;
            List<String> ans = new ArrayList<>();
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                // 此时没有index方向的路且不是root节点
                /**
                 * 前缀树：a -> b -> c -> k, c -> f  a路开始的通向c路的节点的fail指向c路的节点
                 * 文章：”abcf“
                 * 当前节点匹配到”abc“，发现此时没有走向f方向的路，而且fail指针不是root，意味失败蹦到c的fail
                 *
                 */
                while (cur.nextS[index] == null && cur != root) {
                    cur = cur.fail;
                }
                // 1、现在来到的路径，是可以继续匹配的
                // 2、现在来到的节点，就是前缀树的根节点
                cur = cur.nextS[index] != null ? cur.nextS[index] : root;
                follow = cur;
                // 来到任何一个节点转一圈
                while (follow != root) {
                    // 已经使用过，直接跳过
                    if (follow.endUse) {
                        break;
                    }

                    // 不同的需求，在这一段修改
                    if (follow.end != null) {
                        ans.add(follow.end);
                        follow.endUse = true;
                    }
                    // 不同的需求，在这一段修改
                    follow = follow.fail;
                }
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        ACAutomation ac = new ACAutomation();
        ac.insert("abc");
        ac.insert("xyz");
        ac.insert("mno");

        ac.build();

        List<String> ans = ac.containWords("dadasdaabchdhsahdasjhjashjhjanjdbhajbhjdsavhjxyz");
        for (String an : ans) {
            System.out.println(an);
        }
    }
}
