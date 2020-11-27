package com.rukawa.algorithm.trainingcamp.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-11 0:54
 * @Version：1.0
 */
public class Problem_0387_FirstUniqueCharacterInString {
    /**
     * 字符串中的第一个唯一字符
     * 给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。
     *
     * 示例：
     * s = "leetcode"
     * 返回 0
     * s = "loveleetcode"
     * 返回 2
     *
     * 提示：你可以假定该字符串只包含小写字母。
     */
    public static int firstUniqChar(String s) {
        char[] str = s.toCharArray();
        int N = str.length;
        int[] count = new int[256];
        for (int i = 0; i < N; i++) {
            count[str[i] - 'a']++;
        }

        for (int i = 0; i < N; i++) {
            if (count[str[i] - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        String s = "loveleetcode";
        System.out.println(firstUniqChar(s));
    }

}
