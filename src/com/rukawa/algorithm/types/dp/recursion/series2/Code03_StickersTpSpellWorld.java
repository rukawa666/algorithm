package com.rukawa.algorithm.types.dp.recursion.series2;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/4/2 0002 8:20
 */
public class Code03_StickersTpSpellWorld {

    /**
     * 给定一个字符串，给定一个字符串类型的数组arr，出现的字符都是小写英文
     * arr每一个字符串，代表一张贴纸，你可以把单个字符剪开使用，目的是拼出str来
     * 返回需要多少张贴纸可以完成这个任务
     * 例子：str="babac", arr={"ba", "c", "abcd"}
     * 至少需要两张贴纸"ba"和"abcd"，因为使用这两种贴纸，把每一个字符单独剪开，含有
     * 2个a，2个b，1个c。是可以拼出str的。所以返回2
     */

    public static int minStickers1(String[] stickers, String target) {
        int res = process1(stickers, target);
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    // 所有贴纸Stickers，每一种贴纸都有无穷张
    // target
    // 最少张数
    public static int process1(String[] stickers, String target) {
        // 之前的决策都分解完成，后续需要0张
        if (target.length() == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (String first : stickers) {
            String rest = minus(target, first);
            // 第一张贴纸减完之后没有变化，没有必要去执行以下分支
            if (rest.length() != target.length()) {
                min = Math.min(min, process1(stickers, rest));
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    public static String minus(String s1, String s2) {
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int[] count = new int[26];
        for (char c : str1) {
            count[c - 'a']++;
        }
        for (char c : str2) {
            count[c - 'a']--;
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

    /**
     * 方法2：对于上方法的优化方法
     * @param stickers
     * @param target
     * @return
     */
    public static int minStickers2(String[] stickers, String target) {
        int N = stickers.length;
        // 关键优化(用词频表代替贴纸数组)
        int[][] counts = new int[N][26];
        for (int i = 0; i < N; i++) {
            char[] str = stickers[i].toCharArray();
            for (char cha : str) {
                counts[i][cha - 'a']++;
            }
        }
        int res = process2(counts, target);
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    // stickers[i] 数组，当初i号贴纸的词频统计  int[][] stickers -> 所有的贴纸
    // 每一种贴纸都有无穷张
    // 返回搞定target
    // 最少张数
    public static int process2(int[][] stickers, String s) {
        if (s.length() == 0) {
            return 0;
        }
        // target做出词频统计
        char[] target = s.toCharArray();
        // target词频统计
        int[] tCount = new int[26];
        for (char c : target) {
            tCount[c - 'a']++;
        }
        int N = stickers.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            // 尝试第一张贴纸
            int[] sticker = stickers[i];
            // 最重要的优化(重要的剪枝！这一步为贪心)
            if (sticker[target[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                // a~z所有字符，查看减掉多少
                for (int j = 0; j < 26; j++) {
                    if (tCount[j] > 0) {
                        int nums = tCount[j] - sticker[j];
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

    public static int minStickers3(String[] stickers, String target) {
        int N = stickers.length;
        int[][] count = new int[N][26];
        for (int i = 0; i < N; i++) {
            char[] str = stickers[i].toCharArray();
            for (char cha : str) {
                count[i][cha - 'a']++;
            }
        }
        HashMap<String, Integer> dp = new HashMap<>();
        dp.put("", 0);
        int res = process3(count, target, dp);
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    public static int process3(int[][] stickers, String t, HashMap<String, Integer> dp) {
        if (dp.containsKey(t)) {
            return dp.get(t);
        }
        char[] target = t.toCharArray();
        int[] tCount = new int[26];
        for (char c : target) {
            tCount[c - 'a']++;
        }
        int N = stickers.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            int[] sticker = stickers[i];
            if (sticker[target[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (tCount[j] > 0) {
                        int nums = tCount[j] - sticker[j];
                        for (int k = 0; k < nums; k++) {
                            builder.append((char) (j + 'a'));
                        }
                    }
                }
                String rest = builder.toString();
                min = Math.min(min, process3(stickers, rest, dp));
            }
        }
        int res = min + (min == Integer.MAX_VALUE ? 0 : 1);
        dp.put(t, res);
        return res;
    }

    public static void main(String[] args) {

    }
}
