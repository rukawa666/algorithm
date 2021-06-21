package com.rukawa.algorithm.types.ac;

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
}
