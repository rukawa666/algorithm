package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.Stack;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:31
 * @Version：1.0
 */
public class Problem_0020_ValidParentheses {
    /**
     * 有效的括号
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
     * 有效字符串需满足：
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     * 每个右括号都有一个对应的相同类型的左括号。
     *
     * 示例 1：
     * 输入：s = "()"
     * 输出：true
     *
     * 示例 2：
     * 输入：s = "()[]{}"
     * 输出：true
     *
     * 示例 3：
     * 输入：s = "(]"
     * 输出：false
     *
     * 提示：
     * 1 <= s.length <= 104
     * s 仅由括号 '()[]{}' 组成
     */
    public boolean isValid(String s) {
        /**
         * 思路：
         *  遇到'(','[','{' 可以直接把对应的')',']','}'压入栈
         *  然后')',']','}'匹配到的时候，直接比较相等
         */
        if (s == null || s.length() == 0) {
            return true;
        }
        char[] str = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (char cur : str) {
            if (cur == '(' || cur == '[' || cur == '{') {
                stack.push(cur == '(' ? ')' : (cur == '[' ? ']' : '}'));
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                char last = stack.pop();
                if (cur != last) {
                    return false;
                }
            }

        }
        return stack.isEmpty();
    }

    public boolean isValid2(String s) {
        /**
         * 思路：
         *  遇到'(','[','{' 可以直接把对应的')',']','}'压入栈
         *  然后')',']','}'匹配到的时候，直接比较相等
         */
        if (s == null || s.length() == 0) {
            return true;
        }
        char[] str = s.toCharArray();
        int n = str.length;
        char[] stack = new char[n];
        int size = 0;
        for (int i = 0; i < n; i++) {
            char cur = str[i];
            if (cur == '(' || cur == '[' || cur == '{') {
                stack[size++] = cur == '(' ? ')' : (cur == '[' ? ']' : '}');
            } else {
                if (size == 0) {
                    return false;
                }
                char last = stack[--size];
                if (last != cur) {
                    return false;
                }
            }
        }
        return size == 0;
    }
}
