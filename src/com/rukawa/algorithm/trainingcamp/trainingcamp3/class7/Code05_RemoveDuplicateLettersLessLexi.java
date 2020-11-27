package com.rukawa.algorithm.trainingcamp.trainingcamp3.class7;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-08 14:12
 * @Version：1.0
 */
public class Code05_RemoveDuplicateLettersLessLexi {
    /**
     * 给定一个全是小写字母的字符串str，删除多余字符，使得每种字符只保留一个，
     * 并让最终结果字符的字典序最小
     * 举例：
     *  str="acbc"，删掉第一个'c'，得到'abc'，是所有结果字符串中字典序最小的。
     *  str="dbcacbca"，删掉第一个'b'、第一个'c'、第二个'c'、第二个'a'，得到
     *  'dabc'，是所有结果字符串中字典序最小的
     */

    public static String removeDuplicateLetters1(String str) {
        /**
         * 思路：
         *    1、遍历字符生成每个字符的个数表
         *    2、从左向右遍历，经过的字符个数-1，直到找到首次出现的0个数的字符
         *    3、然后从该位置i，从0~i，找出字典序最小的第一个字符a
         *    4、然后删除a左边的字符和右边等于a的字符
         *    5、接着依次从剩余的0~i位置中找出字典序最小的字符，依次遍历
         */
        if (str == null || str.length() < 2) {
            return str;
        }
        int[] map = new int[256];
        for (int i = 0; i < str.length(); i++) {
            map[str.charAt(i)]++;
        }

        int minASCIndex = 0;
        for (int i = 0; i < str.length(); i++) {
            // 当前字符 < str.charAt(minASCIndex), 才会更新,index会停留在字典序最小的第一个字符位置
            minASCIndex = str.charAt(i) < str.charAt(minASCIndex) ? i : minASCIndex;
            if (--map[str.charAt(i)] == 0) {
                break;
            }
        }
        return str.charAt(minASCIndex) +
                removeDuplicateLetters1(str.substring(minASCIndex + 1)
                        .replaceAll(String.valueOf(str.charAt(minASCIndex)), ""));
    }

    public static void main(String[] args) {
        String s = "dbcacbca";
        System.out.println(removeDuplicateLetters1(s));
    }
}
