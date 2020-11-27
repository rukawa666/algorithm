package com.rukawa.algorithm.base.class12;

import java.util.HashMap;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-29 7:35
 * @Version：1.0
 */
public class Code02_StickersToSpellWord {
    /**
     * 给定一个字符串Str，给定一个字符串类型的数组arr
     * arr里面的每一个字符，代表一张贴纸，你可以把单个字符剪开使用，目的是拼出str
     * 返回需要只要多少张贴纸才可以完成
     * 例子：str="babac"，arr={"ba","c","abcd"}
     * 至少需要两张贴纸"ba"和"abcd"。因为使用这两张贴纸，把每一个字符串单独剪开，还有
     * 2个a、2个b、1个c。是可以拼出str的，所以返回2
     */

    public static int minStickers01(String[] stickers, String target) {
        int n = stickers.length;
        int[][] map = new int[n][26];
        for (int i = 0; i < n; i++) {
            char[] chs = stickers[i].toCharArray();
            for (char ch : chs) {
                map[i][ch - 'a']++;
            }
        }

        HashMap<String, Integer> dp = new HashMap<>();
        dp.put("", 0);
        return process01(dp, map, target);
    }

    /**
     * dp 缓存，如果t已经算过了，直接返回dp中的值
     * t 剩余的目标
     * 0...N每一个字符串所包含字符的词频统计
     * 返回值是-1，map中的贴纸怎么都无法搞定rest
     * @param dp
     * @param map
     * @param rest
     * @return
     */
    public static int process01(HashMap<String, Integer> dp, int[][] map, String rest) {

        if (dp.containsKey(rest)) {
            return dp.get(rest);
        }

        // 以下就是正式的调用递归过程
        int ans = Integer.MAX_VALUE;  // ans -> 搞定rest，使用的最少的贴纸数量
        int n = map.length; // N种贴纸
        int[] restMap = new int[26];    // 替代rest，统计词频
        char[] target = rest.toCharArray();
        for (char c : target) {
            restMap[c - 'a']++;
        }

        // map -> restMap
        for (int i = 0; i < n; i++) {
            // 枚举当前第一张贴纸是谁？
            // target 0位置的字符贴纸有没有，如果没有，则直接跳过
            if (map[i][target[0] - 'a'] == 0) { // 此处为小加速
                continue;
            }

            StringBuilder sb = new StringBuilder();
            // i贴纸， j枚举a-z字符
            for (int j = 0; j < 26; j++) {
                if (restMap[j] > 0) {   // j这个字符是target需要的
                    // 还剩下多少贴纸，在字符串中填多少个
                    for (int k = 0; k < Math.max(0, restMap[j] - map[i][j]); k++) {
                        sb.append((char) ('a' + j));
                    }
                }
            }
            // 还剩下多少贴纸，在sb中保存
            String s = sb.toString();
            // 剩余字符串被后续贴纸搞定的最小数量
            int next = process01(dp, map,s);
            if (next != -1) {
                ans = Math.min(ans, next + 1);
            }
        }
        dp.put(rest, ans == Integer.MAX_VALUE ? -1 : ans);
        return dp.get(rest);
    }

    public static void main(String[] args) {
        String[] arr = {"aaaa","bbaa","ccddd"};
        String str = "abcccccdddddbbbaaaaa";
        System.out.println(minStickers01(arr, str));

    }
}
