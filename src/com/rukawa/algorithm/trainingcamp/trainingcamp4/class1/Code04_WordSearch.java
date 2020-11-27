package com.rukawa.algorithm.trainingcamp.trainingcamp4.class1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-10 7:31
 * @Version：1.0
 */
public class Code04_WordSearch {
    /**
     * 给定一个字符类型的二维数组board，和一个字符串组成的列表words。
     * 可以从board任何位置出发，每一步可以走向上、下、左、右四个方向，
     * 但是一条路径已经走过的位置，不能重复走。
     * 返回words哪些单词可以被走出来。
     * 例子：
     * board={
     *     {'o','a','a','n'},
     *     {'e','t','a','e'},
     *     {'i','h','k','r'},
     *     {'i','f','l','v'}
     * }
     * words={"oath","pea","eat","rain"}
     * 输出：{"eat","oath"}
     */
    public static List<String> findWords(char[][] board, String[] words) {
        /**
         * 思路：
         * 从board的[i,j]位置出发，能走出words里面的哪些单词，然后单词汇总就是所有的答案
         * 前提：
         *   1、判断board位置的字符是否走回来了
         *   2、所有的后续结束之后再去其他方向尝试，最好把流程设计成深度优先遍历，因为深度优先遍历的过程中方便把走过的字符打上标签防止回头
         *
         * 思路：把words生成前缀树，然后通过board找前缀树的查询
         */
        // 前缀树的头节点
        TrieNode head = new TrieNode();
        HashSet<String> set = new HashSet<>();
        // 对words中每一个字符串建立前缀串
        for (String word : words) {
            if (!set.contains(word)) {
                fillWord(head, word);
                set.add(word);
            }
        }
        // 答案
        List<String> res = new ArrayList<>();
        // 沿途走过的字符，收集起来，存在path里
        LinkedList<Character> path = new LinkedList<>();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                // 枚举board中的所有位置
                // 每一个位置出发的情况下，答案都收集
                process(board, row, col, path, head, res);
            }
        }
        return res;
    }

    /**
     * @param board
     * @param row board 出发的行位置
     * @param col board 出发的列位置
     * @param path  之前的路径上，走过的字符，记录在path里面
     * @param cur cur还没有登上，有待检查能不能登山去的前缀树的节点
     * @param res 从row，col出发，一共找到多少个str
     * @return
     */
    public static int process(char[][] board, int row, int col, LinkedList<Character> path,
                              TrieNode cur, List<String> res) {
        char cha = board[row][col];
        if (cha == 0) { // 这个row col是之前走过的位置
            return 0;
        }
        // 不是回头路， cha有效
        int index = cha - 'a';
        // 对应的位置没有路径 'cur.nextS[index].pass == 0' -> 加速使用，对应下面 "cur.pass -= fix"
        if (cur.nextS[index] == null || cur.nextS[index].pass == 0) {
            return 0;
        }
        // 没有走回头路且能登上去，头节点的下一个节点才是路径开始
        cur = cur.nextS[index];
        path.addLast(cha);  // 当前位置的字符加到路径里去

        int fix = 0; // 从row，col位置出发，后续一共搞定了多少答案
        // 当我来到row col位置，如果决定不往后走了，是不是已经搞定了某个字符串了
        if (cur.end > 0) {
            // "abc"  "abcd" 要消除abc的end记录
            res.add(generatePath(path));
            cur.end--;
            // row和col解决答案的数量有没有解决，自己解决掉一个fix++
            fix++;
        }
        // 往上、下、左、右去尝试，累加到自己搞定的数量上
        board[row][col] = 0;
        if (row > 0) {
            fix += process(board, row - 1, col, path, cur, res);
        }
        if (row < board.length - 1) {
            fix += process(board, row + 1, col, path, cur, res);
        }
        if (col > 0) {
            fix += process(board, row, col - 1, path, cur, res);
        }
        if (col < board[0].length - 1) {
            fix += process(board, row, col + 1, path, cur, res);
        }
        // 恢复字符原有值
        board[row][col] = cha;
        // 恢复现场
        path.pollLast();
        /**
         * 贪心优化：
         *   O
         *   |
         *   O  a
         * / | \
         *O  O  O
         *b  c  d
         *
         * 在a的时候，搞定了3个字符，pass要减去3
         * 如果以后再遇到
         *    d
         *  c a
         *    b
         * 这种情况，如果发现a的pass为0，表示已经收集过了，直接返回
         * 所以返回值fix是用来做加速
         */
        cur.pass -= fix;
        return fix;
    }

    public static String generatePath(LinkedList<Character> path) {
        char[] str = new char[path.size()];
        int index = 0;
        for (Character cha : path) {
            str[index++] = cha;
        }
        return String.valueOf(str);
    }

    // 建立前缀串
    public static void fillWord(TrieNode head, String word) {
        head.pass++;
        char[] str = word.toCharArray();
        int index = 0;
        TrieNode node = head;
        for (int i = 0; i < str.length; i++) {
            index = str[i] - 'a';
            if (node.nextS[index] == null) {
                node.nextS[index] = new TrieNode();
            }
            node = node.nextS[index];
            node.pass++;
        }
        node.end++;
    }

    public static class TrieNode {
        private TrieNode[] nextS;
        private int pass;
        private int end;

        public TrieNode() {
            nextS = new TrieNode[26];
            pass = 0;
            end = 0;
        }
    }
}
