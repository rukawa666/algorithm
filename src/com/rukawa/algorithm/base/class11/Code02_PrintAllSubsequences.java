package com.rukawa.algorithm.base.class11;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-26 22:09
 * @Version：1.0
 */
public class Code02_PrintAllSubsequences {

    /**
     * 打印一个字符串的全部子序列
     * @param s
     * @return
     */
    public static List<String> subs(String s) {
        char[] chs = s.toCharArray();
        String path = "";
        List<String> ans = new ArrayList<>();
        process01(chs, 0, ans, path);
        return ans;
    }

    /**
     * @param chs 固定，不变
     * @param index 此时来到的位置，要 or 不要
     * @param ans   如果index来到chs中的终止位置，把沿途路径的所形成的的答案，放入ans中
     * @param path  沿途路径
     */
    public static void process01(char[] chs, int index, List<String> ans, String path) {
        if (index == chs.length) {
            ans.add(path);
            return;
        }
        // 包含字符的展开
        String no = path;
        process01(chs, index + 1, ans, no);

        // 包含字符的展开
        String yes = path + String.valueOf(chs[index]);
        process01(chs, index + 1, ans, yes);
    }

    /**
     * 打印一个字符串的全部子序列，要求不要出现重复字面值的子序列
     * @param s
     * @return
     */
    public static List<String> subsNoRepeat(String s) {
        char[] chs = s.toCharArray();
        String path = "";
        HashSet<String> set = new HashSet<>();
        process02(chs, 0, set, path);

        List<String> ans = new ArrayList<>();
        for (String cur : set) {
            ans.add(cur);
        }
        return ans;
    }

    public static void process02(char[] chs, int index, HashSet<String> set, String path) {
        if (index == chs.length) {
            set.add(path);
            return;
        }

        String no = path;
        process02(chs, index + 1, set, no);

        String yes = path + String.valueOf(chs[index]);
        process02(chs, index + 1, set, yes);
    }

    public static void main(String[] args) {
        String test = "aacc";
        List<String> ans1 = subs(test);
        List<String> ans2 = subsNoRepeat(test);

        for (String str : ans1) {
            System.out.println(str);
        }
        System.out.println("=================");
        for (String str : ans2) {
            System.out.println(str);
        }
        System.out.println("=================");

    }

}
