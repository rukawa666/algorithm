package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:31
 * @Version：1.0
 */
public class Problem_0022_GenerateParentheses {
    /**
     * 括号生成
     * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
     *
     * 示例 1：
     * 输入：n = 3
     * 输出：["((()))","(()())","(())()","()(())","()()()"]
     *
     * 示例 2：
     * 输入：n = 1
     * 输出：["()"]
     *
     * 提示：
     * 1 <= n <= 8
     */
    public List<String> generateParenthesis(int n) {
        char[] path = new char[n << 1];
        List<String> res = new ArrayList<>();
        process(path, 0, 0, n, res);
        return res;
    }

    // path做的决定 path[0...index-1]已经做过决定了
    // path[index...]还没决定，当前轮到index位置做决定
    // leftMinusRight:左括号减去右括号的数量
    // leftRest左括号还剩余几个
    public void process(char[] path, int index, int leftMinusRight, int leftRest, List<String> res) {
        // 所有决定都做完了
        if (index == path.length) {
            res.add(String.valueOf(path));
        } else {
            // index位置上要放'('的决定，'('要有剩余，才能放
            if (leftRest > 0) {
                path[index] = '(';
                process(path, index + 1, leftMinusRight + 1, leftRest - 1, res);
            }
            // index要放')'，之前有多余的'('才能放')'
            if (leftMinusRight > 0) {
                path[index] = ')';
                process(path, index + 1, leftMinusRight - 1, leftRest, res);
            }
        }
    }
}
