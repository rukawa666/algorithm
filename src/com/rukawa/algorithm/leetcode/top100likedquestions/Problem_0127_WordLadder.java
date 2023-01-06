package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.*;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 9:59
 * @Version：1.0
 */
public class Problem_0127_WordLadder {
    /**
     * 给定两个单词（beginWord 和 endWord）和一个字典，找到从 beginWord 到 endWord 的最短转换序列的长度。转换需遵循如下规则：
     * 每次转换只能改变一个字母。
     * 转换过程中的中间单词必须是字典中的单词。
     * 说明:
     *
     * 如果不存在这样的转换序列，返回 0。
     * 所有单词具有相同的长度。
     * 所有单词只由小写字母组成。
     * 字典中不存在重复的单词。
     * 你可以假设 beginWord 和 endWord 是非空的，且二者不相同。
     * 示例 1:
     *
     * 输入:
     * beginWord = "hit",
     * endWord = "cog",
     * wordList = ["hot","dot","dog","lot","log","cog"]
     * 输出: 5
     * 解释: 一个最短转换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog",
     *      返回它的长度 5。
     *
     * 示例 2:
     * 输入:
     * beginWord = "hit"
     * endWord = "cog"
     * wordList = ["hot","dot","dog","lot","log"]
     * 输出: 0
     * 解释: endWord "cog" 不在字典中，所以无法进行转换。
     *
     * 提示：
     * 1 <= beginWord.length <= 10
     * endWord.length == beginWord.length
     * 1 <= wordList.length <= 5000
     * wordList[i].length == beginWord.length
     * beginWord、endWord 和 wordList[i] 由小写英文字母组成
     * beginWord != endWord
     * wordList 中的所有字符串 互不相同
     */
    // O(k^2*25*N)
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        /**
         * 宽度优先遍历
         * 左右两边同时找，哪边的发散的邻居少，哪边开始找
         */
        HashSet<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) {
            return 0;
        }
        // beginWord发散的邻居
        HashSet<String> startSet = new HashSet<>();
        // endWord发散的邻居
        HashSet<String> endSet = new HashSet<>();
        // 访问过的字符串
        HashSet<String> visit = new HashSet<>();
        startSet.add(beginWord);
        endSet.add(endWord);

        for (int len = 2; !startSet.isEmpty(); len++) {
            // startSet 是较小的 endSet是较大的
            HashSet<String> nextSet = new HashSet<>();
            for (String s : startSet) {
                for (int j = 0; j < s.length(); j++) {
                    char[] str = s.toCharArray();
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c != str[j]) {
                            str[j] = c;
                            String next = String.valueOf(str);
                            if (endSet.contains(next)) {
                                return len;
                            }
                            if (dict.contains(next) && !visit.contains(next)) {
                                nextSet.add(next);
                                visit.add(next);
                            }
                        }
                    }
                }
            }
            // startSet(小) -> nextSet(某个大小) 和 endSet大小比较
            startSet = nextSet.size() < endSet.size() ? nextSet : endSet;
            endSet = (startSet == nextSet) ? endSet : nextSet;
        }
        return 0;
    }

    public int ladderLength2(String beginWord, String endWord, List<String> wordList) {
        wordList.add(beginWord);
        // 列表中的单词，每个单词都会有记录
        // key这个单词，有那些邻居
        HashMap<String, ArrayList<String>> nextMap = getNextMap(wordList);

        HashMap<String, Integer> distanceMap = new HashMap<>();
        distanceMap.put(beginWord, 1);

        HashSet<String> set = new HashSet<>();
        set.add(beginWord);
        // 宽度优先遍历
        Queue<String> queue = new LinkedList<>();
        queue.add(beginWord);
        while (!queue.isEmpty()) {
            String cur = queue.poll();
            Integer distance = distanceMap.get(cur);
            for (String next : nextMap.get(cur)) {
                if (next.equals(endWord)) {
                    return distance + 1;
                }

                if (!set.contains(next)) {
                    set.add(next);
                    queue.add(next);
                    distanceMap.put(next, distance + 1);
                }
            }
        }
        return 0;
    }

    public HashMap<String, ArrayList<String>> getNextMap(List<String> wordList) {
        HashSet<String> dict = new HashSet<>(wordList);
        HashMap<String, ArrayList<String>> nextMap = new HashMap<>();
        for (int i = 0; i < wordList.size(); i++) {
            nextMap.put(wordList.get(i), getNext(wordList.get(i), dict));
        }
        return nextMap;
    }

    // 如果字符串长度比较短，字符串数量比较多，以下方法适合
    // 如果字符串长度比较长，字符串数量比较少，以下方法不适合
    public ArrayList<String> getNext(String word, HashSet<String> dict) {
        ArrayList<String> res = new ArrayList<>();
        char[] str = word.toCharArray();
        for (char cur = 'a'; cur <= 'z'; cur++) {
           for (int i = 0; i < str.length; i++) {
               if (str[i] != cur) {
                   char tmp = str[i];
                   str[i] = cur;
                   if (dict.contains(String.valueOf(str))) {
                       res.add(String.valueOf(str));
                   }
                   str[i] = tmp;
               }
           }
        }
        return res;
    }
}
