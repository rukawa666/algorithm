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
public class Problem_0017_LetterCombinationsOfAPhoneNumber {
    /**
     * 电话号码的字母组合
     * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
     * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
     * 2:abc 3:def 4:ghi 5:jkl 6:mno 7:pqrs 8:tuv 9:wxyz
     * 示例 1：
     * 输入：digits = "23"
     * 输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
     * 示例 2：
     * 输入：digits = ""
     * 输出：[]
     *
     * 示例 3：
     * 输入：digits = "2"
     * 输出：["a","b","c"]
     *
     * 提示：
     * 0 <= digits.length <= 4
     * digits[i] 是范围 ['2', '9'] 的一个数字。
     */

    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return res;
        }
        char[] str = digits.toCharArray();
        char[] path = new char[str.length];
        process(str, 0, path, res);
        return res;
    }

    // str[index...]收集答案
    // str[0...index-1]已经播过，存放在path中
    public void process(char[] str, int index, char[] path, List<String> res) {
        if (index == str.length) {
            res.add(String.valueOf(path));
        } else {
            // 如果str[index]='2' 则选取phone的0位置数组
            char[] curPhone = phone[str[index] - '2'];
            // 每一个位置去尝试，深度优先遍历
            for (char cur : curPhone) {
                path[index] = cur;
                process(str, index + 1, path, res);
            }
        }
    }

    public static char[][] phone = {
            {'a','b','c'}, // 2
            {'d','e','f'}, // 3
            {'g','h','i'}, // 4
            {'j','k','l'}, // 5
            {'m','n','o'}, // 6
            {'p','q','r', 's'},  // 7
            {'t','u','v'}, // 8
            {'w','x','y', 'z'}, // 9
    };
}
