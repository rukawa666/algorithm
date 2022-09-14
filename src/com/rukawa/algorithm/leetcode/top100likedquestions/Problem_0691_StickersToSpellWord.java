package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.HashMap;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-29 7:35
 * @Version：1.0
 */
public class Problem_0691_StickersToSpellWord {
    /**
     * 贴纸拼词
     * 我们给出了 N 种不同类型的贴纸。每个贴纸上都有一个小写的英文单词。
     * 你希望从自己的贴纸集合中裁剪单个字母并重新排列它们，从而拼写出给定的目标字符串 target。
     * 如果你愿意的话，你可以不止一次地使用每一张贴纸，而且每一张贴纸的数量都是无限的。
     * 拼出目标 target 所需的最小贴纸数量是多少？如果任务不可能，则返回 -1。
     *
     *
     * 示例 1：
     * 输入：
     * ["with", "example", "science"], "thehat"
     * 输出：
     * 3
     * 解释：
     * 我们可以使用 2 个 "with" 贴纸，和 1 个 "example" 贴纸。
     * 把贴纸上的字母剪下来并重新排列后，就可以形成目标 “thehat“ 了。
     * 此外，这是形成目标字符串所需的最小贴纸数量。
     *
     * 示例 2：
     * 输入：
     * ["notice", "possible"], "basicbasic"
     * 输出：
     * -1
     * 解释：
     * 我们不能通过剪切给定贴纸的字母来形成目标“basicbasic”。
     *  
     * 提示：
     * stickers 长度范围是 [1, 50]。
     * stickers 由小写英文单词组成（不带撇号）。
     * target 的长度在 [1, 15] 范围内，由小写字母组成。
     * 在所有的测试案例中，所有的单词都是从 1000 个最常见的美国英语单词中随机选取的，目标是两个随机单词的串联。
     * 时间限制可能比平时更具挑战性。预计 50 个贴纸的测试案例平均可在35ms内解决。
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

    // 优化版本
    public static int minStickers2(String[] stickers, String target) {
        int N = stickers.length;
        int[][] count = new int[N][26];
        for (int i = 0; i < N; i++) {
            char[] chs = stickers[i].toCharArray();
            for (char ch : chs) {
                count[i][ch - 'a']++;
            }
        }
        int res = process2(count, target);
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    // stickers 代表所有贴纸
    // 每一种贴纸无穷张
    // 搞定target的最小张数
    public static int process2(int[][] stickers, String t) {
        if (t.length() == 0) {
            return 0;
        }
        // target 做出词频统计
        // "aabbc"  2 2 1
        //          0 1 2
        char[] target = t.toCharArray();
        int[] counts = new int[26];
        for (char chs : target) {
            counts[chs - 'a']++;
        }
        int N = stickers.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            // 尝试贴纸
            int[] sticker = stickers[i];
            // 最关键的优化（重要的剪枝，这一步也是贪心）
            // target：aaabbbck
            // stickers：bbc cck kkb bab
            // target - 'a' 找到target中的第一个字符
            // 找到贴纸中包含target第一个字符的贴纸进行尝试
            // 让包含target第一个字符的贴纸先试，不会影响答案
            // 原来的最优答案可能出现好几次，但是当前把最优答案直接拿到
            if (sticker[target[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (counts[j] > 0) {
                        int nums = counts[j] - sticker[j];
                        for (int k = 0; k < nums; k++) {
                            builder.append((char) (j + 'a'));
                        }
                    }
                }
                String rest = builder.toString();
                min = Math.min(min, process2(stickers, rest));
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    public static void main(String[] args) {
        String[] arr = {"aaaa","bbaa","ccddd"};
        String str = "abcccccdddddbbbaaaaa";
        System.out.println(minStickers01(arr, str));

    }
}
