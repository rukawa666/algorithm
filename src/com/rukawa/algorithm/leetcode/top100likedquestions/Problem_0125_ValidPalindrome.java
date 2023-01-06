package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 9:57
 * @Version：1.0
 */
public class Problem_0125_ValidPalindrome {
    /**
     * 验证回文串
     * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
     * 说明：本题中，我们将空字符串定义为有效的回文串。
     *
     * 示例 1:
     * 输入: "A man, a plan, a canal: Panama"
     * 输出: true
     *
     * 示例 2:
     * 输入: "race a car"
     * 输出: false
     */

    public boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        char[] str = s.toCharArray();
        int l = 0;
        int r = str.length - 1;
        while (l < r) {
            if (validChar(str[l]) && validChar(str[r])) {
                if (!isEqual(str[l], str[r])) {
                    return false;
                }
                l++;
                r--;
            } else {
                l += validChar(str[l]) ? 0 : 1;
                r -= validChar(str[r]) ? 0 : 1;
            }
        }
        return true;
    }

    public boolean validChar(char c) {
        return isLetter(c) || isNumber(c);
    }

    public boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public boolean isNumber(char c) {
        return (c >= '0' && c <= '9');
    }

    public boolean isEqual(char a, char b) {
        if (isNumber(a) || isNumber(b)) {
            return a == b;
        }
        return (a == b) || (Math.max(a, b) - Math.min(a, b) == 32);
    }
}
