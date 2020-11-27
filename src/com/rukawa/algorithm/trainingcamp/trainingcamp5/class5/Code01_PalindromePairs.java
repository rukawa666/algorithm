package com.rukawa.algorithm.trainingcamp.trainingcamp5.class5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-11-12 8:05
 * @Version：1.0
 */
public class Code01_PalindromePairs {
    /**
     * 给定字符串数组words，其中所有字符串都不同，如果words[i]+words[j]是回文串就记录(i,j)，找到所有记录并返回
     * 例子一:
     * 输入：["abcd","dcba","lls","s","sssll"]
     * 输出：[[0,1],[1,0],[3,2],[2,4]]
     * 解释：输出的每一组数组，两个下标代表字符串拼接在一起，都是回文串
     * abcddcba、 dcbaabcd 、 slls 、 llssssll
     */

    // O(N * K^2)
    public static List<List<Integer>> palindromePairs(String[] words) {
        /**
         * 检查是否回文串的优化？
         * 1、某一个位置的回文半径数组把前面第0个位置的字符包住，整个直径是回文。中心位置找到，他的回文半径位置是否囊括前面的位置，包含则是回文串
         * 2、检查前缀串和后缀串是否是回文，通过回文半径数组，找到它的中心位置，判断是否包含头和尾，如果包含则是回文串。O(1)
         */
        HashMap<String, Integer> wordSet = new HashMap<>();
        // 没有重复字符串
        // key -> word
        // value -> index
        for (int i = 0; i < words.length; i++) {
            wordSet.put(words[i], i);
        }
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            res.addAll(findAll(words[i], i, wordSet));
        }
        return res;
    }

    // 单独一个字符串，在index位置，谁跟word结合能匹配成回文串
    public static List<List<Integer>> findAll(String word, int index, HashMap<String, Integer> wordSet) {
        List<List<Integer>> res = new ArrayList<>();
        // 自己的逆序串
        String reverse = reverse(word);
        Integer rest = wordSet.get("");
        // 检查自己的原串，是否有回文结构
        if (rest != null && rest != index && word.equals(reverse)) {
            addRecord(res, rest, index);
            addRecord(res, index, rest);
        }
        // manacher的回文半径数据
        int[] rs = manacher(word);
        // 中心位置
        int mid = rs.length >> 1;
        // 检查前缀串
        for (int i = 1; i < mid; i++) {
            if (i - rs[i] == -1) {
                rest = wordSet.get(reverse.substring(0, mid - i));
                if (rest != null && rest != index) {
                    addRecord(res, rest, index);
                }
            }
        }
        // 检查后缀串
        for (int i = mid + 1; i < rs.length; i++) {
            if (i + rs[i] == rs.length) {
                rest = wordSet.get(reverse.substring((mid << 1) - i));
                if (rest != null && rest != index) {
                    addRecord(res, index, rest);
                }
            }
        }
        return res;
    }

    public static void addRecord(List<List<Integer>> res, int left, int right) {
        List<Integer> newArr = new ArrayList<>();
        newArr.add(left);
        newArr.add(right);
        res.add(newArr);
    }

    public static String reverse(String word) {
        char[] str = word.toCharArray();
        int l = 0;
        int r = str.length - 1;
        while (l < r) {
            char tmp = str[l];
            str[l++] = str[r];
            str[r--] = tmp;
        }
        return String.valueOf(str);
    }

    public static int[] manacher(String word) {
        char[] str = manacharString(word);
        int R = -1;
        int C = -1;
        int[] radius = new int[str.length];
        for (int i = 0; i < str.length; i++) {
            radius[i] = R > i ? Math.min(radius[(C << 1) - i], R - i) : 1;

            while (i + radius[i] < str.length && i - radius[i] > -1) {
                if (str[i + radius[i]] == str[i - radius[i]]) {
                    radius[i]++;
                } else {
                    break;
                }
            }

            if (i + radius[i] > R) {
                R = i + radius[i];
                C = i;
            }
        }
        return radius;
    }

    public static char[] manacharString(String word) {
        char[] str = word.toCharArray();
        char[] res = new char[str.length * 2 + 1];
        int index = 0;
        for (int i = 0; i < res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : str[index++];
        }
        return res;
    }

    public static void main(String[] args) {
        String test = "\"abcd\",\"dcba\",\"lls\",\"s\",\"sssll\"";
        System.out.println(String.valueOf(manacharString(test)));
        int[] arr = manacher(test);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();

    }
}
