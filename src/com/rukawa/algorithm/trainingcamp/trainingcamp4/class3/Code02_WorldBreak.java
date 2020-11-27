package com.rukawa.algorithm.trainingcamp.trainingcamp4.class3;

import java.util.HashSet;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-13 20:39
 * @Version：1.0
 */
public class Code02_WorldBreak {
    /**
     * 假设所有字符都是小写字母，长字符串是str，arr是去重的单词表，每个单词都不是空字符串且可以使用任意次。
     * 使用arr中的单词有多少种拼接str的方式，返回方法数
     */
    public static int ways1(String str, String[] words) {
        HashSet<String> set = new HashSet<>();
        for (String word : words) {
            set.add(word);
        }
        return process(str, 0, set);
    }

    // 所有的贴纸都已经放在set
    // str[i...] 能够被set中的贴纸分解的话，返回分解的方法数   经典的从左往右的尝试模型
    // O(N^3)
    public static int process(String str, int i, HashSet<String> set) {
        // 已经没有字符串了，一种方法(什么字符也不用)
        if (i == str.length()) {
            return 1;
        }
        // 普遍位置
        int ways = 0;
        // 枚举过程O(N^2)的代价
        for (int end = i; end < str.length(); end++) {
            String prefix = str.substring(i, end + 1);  // O(N)
            if (set.contains(prefix)) {
                ways += process(str, end + 1, set);
            }
        }
        return ways;
    }

    // O(N^2)
    // 如果建树的过程时间复杂度大于O(N^3)，则使用ways1
    public static int ways2(String str, String[] words) {
        if (str == null || str.length() == 0 || words == null || words.length == 0) {
            return 0;
        }
        // words 所有字符串的所有字符数量是k，经历O(k)的建树过程
        TrieNode root = new TrieNode();
        for (String word : words) {
            char[] chs = word.toCharArray();
            TrieNode cur = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                if (cur.nextS[index] == null) {
                    cur.nextS[index] = new TrieNode();
                }
                cur = cur.nextS[index];
            }
            cur.end = true;
        }
        return g(str.toCharArray(), root, 0);
    }

    // str[i...] 被分解的方法数，然后返回
    public static int g(char[] str, TrieNode root, int i) {
        if (i == str.length) {
            return 1;
        }
        int ways = 0;
        TrieNode cur = root;
        // i...end 这个前缀验证是否可以被分解
        for (int end = i; end < str.length; end++) {
            int path = str[end] - 'a';
            // 如果没有路了，后面的串都不可能是有效的前缀串  第一个加速
            if (cur.nextS[path] == null) {
                break;
            }
            cur = cur.nextS[path];
            if (cur.end) {
                ways += g(str, root, end + 1);
            }
        }
        return ways;
    }

    public static class TrieNode {
        public TrieNode[] nextS;
        public boolean end;

        public TrieNode() {
            nextS = new TrieNode[26];
            end = false;
        }
    }

    // 以下的逻辑都是为了测试
    public static class RandomSample {
        public String str;
        public String[] arr;

        public RandomSample(String s, String[] a) {
            str = s;
            arr = a;
        }
    }

    // 随机样本产生器
    public static RandomSample generateRandomSample(char[] candidates, int num, int len, int joint) {
        String[] seeds = randomSeeds(candidates, num, len);
        HashSet<String> set = new HashSet<>();
        for (String str : seeds) {
            set.add(str);
        }
        String[] arr = new String[set.size()];
        int index = 0;
        for (String str : set) {
            arr[index++] = str;
        }
        StringBuilder all = new StringBuilder();
        for (int i = 0; i < joint; i++) {
            all.append(arr[(int) (Math.random() * arr.length)]);
        }
        return new RandomSample(all.toString(), arr);
    }

    public static String[] randomSeeds(char[] candidates, int num, int len) {
        String[] arr = new String[(int) (Math.random() * num) + 1];
        for (int i = 0; i < arr.length; i++) {
            char[] str = new char[(int) (Math.random() * len) + 1];
            for (int j = 0; j < str.length; j++) {
                str[j] = candidates[(int) (Math.random() * candidates.length)];
            }
            arr[i] = String.valueOf(str);
        }
        return arr;
    }

    public static void main(String[] args) {
        char[] candidates = { 'a', 'b' };
        int num = 20;
        int len = 4;
        int joint = 5;
        int testTimes = 30000;
        boolean testResult = true;
        for (int i = 0; i < testTimes; i++) {
            RandomSample sample = generateRandomSample(candidates, num, len, joint);
            int ans1 = ways1(sample.str, sample.arr);
            int ans2 = ways2(sample.str, sample.arr);
//            int ans3 = ways3(sample.str, sample.arr);
//            int ans4 = ways4(sample.str, sample.arr);
//            if (ans1 != ans2 || ans3 != ans4 || ans2 != ans4) {
            if (ans1 != ans2) {
                testResult = false;
            }
        }
        System.out.println(testTimes + "次随机测试是否通过：" + testResult);
    }
}
