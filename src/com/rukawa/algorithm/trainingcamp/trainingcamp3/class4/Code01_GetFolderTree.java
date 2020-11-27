package com.rukawa.algorithm.trainingcamp.trainingcamp3.class4;

import java.util.TreeMap;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-25 23:44
 * @Version：1.0
 */
public class Code01_GetFolderTree {
    /**
     * 给你一个字符串类型的数组arr，如
     * String[] arr = {"b\st", "d\", "a\d\c", "a\b\c"};
     * 把这些路径中蕴含的目录结构给打印出来，子目录直接到父目录下面，并比父目录向右进两格，就像这样：
     * a
     *    b
     *       c
     *    d
     *       c
     * b
     *    st
     * d
     * 同一级的需要按字母顺序排列不能乱
     */
    public static class Node {
        // 上一个节点是通过哪条路，到我的
        public String path;
        // key : node下级的路 value : node在key这条路上对应的节点是什么
        public TreeMap<String, Node> nextMap;

        public Node(String p) {
            this.path = p;
            nextMap = new TreeMap<>();
        }
    }

    public static void print(String[] folderPaths) {
        if (folderPaths == null || folderPaths.length == 0) {
            return;
        }
        // 根据所有的字符串，把前缀树建立好，头节点为head
        Node head = generateFolderTree(folderPaths);
        // 深度优先遍历打印
        printProcess(head, 0);
    }

    public static Node generateFolderTree(String[] folderPaths) {
        Node head = new Node("");   // 系统根节点，前缀树头节点
        for (String folderPath : folderPaths) { // 拿出每一条绝对路径
            String[] paths = folderPath.split("\\\\");  // java特性，用一个"\"做分割的意思
            Node cur = head;
            for (int i = 0; i < paths.length; i++) {    // "a" "b" "c"
                if (!cur.nextMap.containsKey(paths[i])) {
                    cur.nextMap.put(paths[i], new Node(paths[i]));
                }
                cur = cur.nextMap.get(paths[i]);
            }
        }
        return head;
    }

    // head节点，当前在level层
    public static void printProcess(Node node, int level) {
        if (level != 0) {
            // 2 * level + 1
            System.out.println(get4nSpace(level) + node.path);
        }

        for (Node next : node.nextMap.values()) {
            printProcess(next, level + 1);
        }
    }

    public static String get4nSpace(int n) {
        String res = "";
        for (int i = 1; i < n; i++) {
            res += "    ";
        }
        return res;
    }

    public static void main(String[] args) {
        //    "a\b\c" '\'  a,b,c
        String test = "a\\b\\cd";
        //  "a\b\c"    "\"    a,b,c
        String[] arr = test.split("\\\\"); //    \\\\    \\   \
        for(String str : arr) {
            System.out.println(str);
        }
    }

}
