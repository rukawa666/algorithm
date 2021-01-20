package com.rukawa.algorithm.trainingcamp.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-25 21:51
 * @Version：1.0
 */
public class Problem_0208_Trie {
    /**
     * 实现 Trie (前缀树)
     * 实现一个 Trie (前缀树)，包含 insert, search, 和 startsWith 这三个操作。
     *
     * 示例:
     * Trie trie = new Trie();
     * trie.insert("apple");
     * trie.search("apple");   // 返回 true
     * trie.search("app");     // 返回 false
     * trie.startsWith("app"); // 返回 true
     * trie.insert("app");
     * trie.search("app");     // 返回 true
     * 说明:
     *
     * 你可以假设所有的输入都是由小写字母 a-z 构成的。
     * 保证所有输入均为非空字符串。
     */

//    // 前缀树节点类型
//    public static class Node {
//        public int pass;
//        public int end;
//        public Node[] nextS;
//
//        public Node() {
//            pass = 0;
//            end = 0;
//            nextS = new Node[26];
//        }
//    }


    public static class Trie {
        public static class Node {
            public int pass;
            public int end;
            public Node[] nextS;

            public Node() {
                pass = 0;
                end = 0;
                nextS = new Node[26];
            }
        }

        private Node root;

        /** Initialize your data structure here. */
        public Trie() {
            root = new Node();
        }

        /** Inserts a word into the trie. */
        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] str = word.toCharArray();
            Node node = root;
            node.pass++;
            int path = 0;
            for (int i = 0; i < str.length; i++) {
                path = str[i] - 'a';
                if (node.nextS[path] == null) {
                    node.nextS[path] = new Node();
                }
                node = node.nextS[path];
                node.pass++;
            }
            node.end++;
        }

        /** Returns if the word is in the trie. */
        public boolean search(String word) {
            if (word == null) {
                return false;
            }
            char[] str = word.toCharArray();
            Node node = root;
            int index = 0;
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                if (node.nextS[index] == null) {
                    return false;
                }
                node = node.nextS[index];
            }
            return node.end > 0;
        }

        /** Returns if there is any word in the trie that starts with the given prefix. */
        public boolean startsWith(String prefix) {
            if (prefix == null) {
                return false;
            }
            char[] str = prefix.toCharArray();
            Node node = root;
            int index = 0;
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                if (node.nextS[index] == null) {
                    return false;
                }
                node = node.nextS[index];
            }
            return node.pass > 0;
        }

        public void delete(String word) {
            if (search(word)) {
                char[] str = word.toCharArray();
                Node node = root;
                node.pass--;
                int path = 0;
                for (int i = 0; i < str.length; i++) {
                    path = str[i] - 'a';
                    // 当前节点--之后pass=0,下级对象置为null
                    if (--node.nextS[path].pass == 0) {
                        node.nextS[path] = null;
                        return;
                    }
                    node = node.nextS[path];
                }
                node.end--;
            }
        }
    }


}
