package com.rukawa.algorithm.base.class05;

import java.util.HashMap;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-15 23:46
 * @Version：1.0
 */
public class Code02_TrieTree {

    public static class Node01 {
        private int pass;
        private int end;
        private Node01[] nextS;

        public Node01() {
            pass = 0;
            end = 0;
            nextS = new Node01[26];
        }
    }

    public static class Trie01 {
        private Node01 root;

        public Trie01() {
            root = new Node01();
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] chs = word.toCharArray();
            Node01 node = root;
            node.pass++;
            int path = 0;
            for (int i = 0; i < chs.length; i++) {
                path = chs[i] - 'a';
                if (node.nextS[path] == null) {
                    node.nextS[path] = new Node01();
                }
                node = node.nextS[path];
                node.pass++;
            }
            node.end++;
        }

        public void delete(String word) {
            if (search(word) != 0) {
                char[] chs = word.toCharArray();
                Node01 node = root;
                node.pass--;
                int path = 0;
                for (int i = 0; i < chs.length; i++) {
                    path = chs[i] - 'a';
                    if (--node.nextS[path].pass == 0) {
                        node.nextS[path] = null;
                        return;
                    }
                    node = node.nextS[path];
                }
                node.end--;
            }
        }

        // 查找word这个单词之前加入过几次
        public int search(String word) {
            if (word == null) {
                return 0;
            }
            char[] chs = word.toCharArray();
            Node01 node = root;
            int path = 0;
            for (int i = 0; i < chs.length; i++) {
                path = chs[i] - 'a';
                if (node.nextS[path] == null) {
                    return 0;
                }
                node = node.nextS[path];
            }
            return node.end;
        }

        // 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }
            char[] chs = pre.toCharArray();
            Node01 node = root;
            int path;
            for (int i = 0; i < chs.length; i++) {
                path = chs[i] - 'a';
                if (node.nextS[path] == null) {
                    return 0;
                }
                node = node.nextS[path];
            }
            return node.pass;
        }
    }

    public static class Node02 {
        private int pass;
        private int end;
        private HashMap<Integer, Node02> nextS;

        public Node02() {
            pass = 0;
            end = 0;
            nextS = new HashMap<>();
        }
    }

    public static class Trie02 {
        private Node02 root;

        public Trie02() {
            root = new Node02();
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] chs = word.toCharArray();
            Node02 node = root;
            node.pass++;
            int path = 0;
            for (int i = 0; i < chs.length; i++) {
                path = chs[i];
                if (!node.nextS.containsKey(path)) {
                    node.nextS.put(path, new Node02());
                }
                node = node.nextS.get(path);
                node.pass++;
            }
            node.end++;
        }

        public void delete(String word) {
            if (search(word) != 0) {
                char[] chs = word.toCharArray();
                Node02 node = root;
                node.pass--;
                int path = 0;
                for (int i = 0; i < chs.length; i++) {
                    path = chs[i];
                    if (--node.nextS.get(path).pass == 0) {
                        node.nextS.remove(path);
                        return;
                    }
                    node = node.nextS.get(path);
                }
                node.end--;
            }
        }

        // 查找word这个单词之前加入过几次
        public int search(String word) {
            if (word == null) {
                return 0;
            }
            char[] chs = word.toCharArray();
            Node02 node = root;
            int path = 0;
            for (int i = 0; i < chs.length; i++) {
                path = chs[i];
                if (!node.nextS.containsKey(path)) {
                    return 0;
                }
                node = node.nextS.get(path);
            }
            return node.end;
        }

        // 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }

            char[] chs = pre.toCharArray();
            Node02 node = root;
            int path;
            for (int i = 0; i < chs.length; i++) {
                path = chs[i];
                if (!node.nextS.containsKey(path)) {
                    return 0;
                }
                node = node.nextS.get(path);
            }
            return node.pass;
        }
    }

    // 暴力法
    public static class Right {
        private HashMap<String, Integer> box;

        public Right() {
            box = new HashMap<>();
        }

        public void insert(String word) {
            box.put(word, box.getOrDefault(word, 0) + 1);
        }

        public void delete(String word) {
            if (box.containsKey(word)) {
                if (box.get(word) == 1) {
                    box.remove(word);
                } else {
                    box.put(word, box.get(word) - 1);
                }
            }
        }

        public int search(String word) {
            if (!box.containsKey(word)) {
                return 0;
            } else {
                return box.get(word);
            }
        }

        public int prefixNumber(String pre) {
            int count = 0;
            for (String cur : box.keySet()) {
                if (cur.startsWith(pre)) {
                    count += box.get(cur);
                }
            }
            return count;
        }
    }

    // for test
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 6);
            ans[i] = (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    // for test
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    public static void main(String[] args) {
        int arrLen = 100;
        int strLen = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = generateRandomStringArray(arrLen, strLen);
            Trie01 trie01 = new Trie01();
            Trie02 trie02 = new Trie02();
            Right right = new Right();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    trie01.insert(arr[j]);
                    trie02.insert(arr[j]);
                    right.insert(arr[j]);
                } else if (decide < 0.5) {
                    trie01.delete(arr[j]);
                    trie02.delete(arr[j]);
                    right.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans01 = trie01.search(arr[j]);
                    int ans02 = trie02.search(arr[j]);
                    int ans03 = right.search(arr[j]);
                    if (ans01 != ans02 || ans02 != ans03) {
                        System.out.println("Oops!");
                    }
                } else {
                    int ans01 = trie01.prefixNumber(arr[j]);
                    int ans02 = trie02.prefixNumber(arr[j]);
                    int ans03 = right.prefixNumber(arr[j]);
                    if (ans01 != ans02 || ans02 != ans03) {
                        System.out.println("Oops!");
                    }
                }
            }
        }
        System.out.println("finish!");
    }


}
