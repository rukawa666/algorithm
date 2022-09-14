package com.rukawa.algorithm.base.class11;

import java.util.HashMap;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-29 7:35
 * @Version：1.0
 */
public class Code08_StickersToSpellWord {
    /**
     * 给定一个字符串Str，给定一个字符串类型的数组arr
     * arr里面的每一个字符，代表一张贴纸，你可以把单个字符剪开使用，目的是拼出str
     * 返回需要只要多少张贴纸才可以完成
     * 例子：str="babac"，arr={"ba","c","abcd"}
     * 至少需要两张贴纸"ba"和"abcd"。因为使用这两张贴纸，把每一个字符串单独剪开，还有
     * 2个a、2个b、1个c。是可以拼出str的，所以返回2
     */
    // 每一张贴纸都尝试，答案必然在其中

    // 最初始的尝试
    public static int minStickers03(String[] stickers, String target) {
        int res = process03(stickers, target);
        return res == Integer.MAX_VALUE ? -1 : res;
    }
    // 所有贴纸stickers，每一种贴纸都无穷张
    // target
    // 返回最少张数
    public static int process03(String[] stickers, String target) {
        // 如果target已经被贴纸全部搞定了，还需要多少张贴纸
        if (target.length() == 0) {
            return 0;
        }

        int min = Integer.MAX_VALUE;
        for (String sticker : stickers) {
            String rest = minus(target, sticker);
            // 如果当前贴纸能搞定一部分字符串，则继续尝试剩下的字符串
            if (rest.length() != target.length()) {
                min = Math.min(min, process03(stickers, rest));
            }
        }
        // 如果最后搞定的结果无效，则返回无效值
        // 如果最后能搞定，则需要的张数是min+1，要加第一张
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    public static String minus(String target, String sticker) {
        char[] str1 = target.toCharArray();
        char[] str2 = sticker.toCharArray();
        int[] count = new int[26];
        for (char chs : str1) {
            count[chs - 'a']++;
        }

        for (char chs : str2) {
            count[chs - 'a']--;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                for (int j = 0; j < count[i]; j++) {
                    sb.append((char) (i + 'a'));
                }
            }
        }
        return sb.toString();
    }

    // 优化版本
    public static int minStickers02(String[] stickers, String target) {
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
