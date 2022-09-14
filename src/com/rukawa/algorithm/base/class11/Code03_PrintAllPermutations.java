package com.rukawa.algorithm.base.class11;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-26 22:49
 * @Version：1.0
 */
public class Code03_PrintAllPermutations {

    /**
     * 打印一个字符串的全排列
     * @param str
     * @return
     */
    public static ArrayList<String> permutation(String str) {
        ArrayList<String> res = new ArrayList<>();
        if (str == null || str.length() == 0) {
            return res;
        }

        char[] chs = str.toCharArray();
        process01(chs, 0, res);
        return res;
    }

    /**
     * chs[0...i-1]已经做好决定的
     * chs[i...]i后面的位置都有机会来到i位置
     * i终止位置，chs当前的样子就是一种结果 -> res
     * @param chs
     * @param i
     * @param res
     */
    public static void process01(char[] chs, int i, ArrayList<String> res) {
        if (i == chs.length) {
            res.add(String.valueOf(chs));
        }
        // 如果i没有终止，i...都可以来到i位置
        for (int j = i; j < chs.length; j++) {  // j在尝试i后面的所有字符都有机会
            swap(chs, i, j);
            // 保留上面的结果，到i+1位置开始执行
            process01(chs, i + 1, res);
            // 恢复现场
            swap(chs, i, j);
        }
    }

    /**
     * 打印一个字符串的全排列，要求不要出现重复的排列
     * @param s
     * @return
     */
    public static ArrayList<String> permutationNoRepeat(String s) {
        ArrayList<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] chs = s.toCharArray();
        process02(chs, 0, ans);
        return ans;
    }

    public static void process02(char[] chs, int i, ArrayList<String> ans) {
        if (i == chs.length) {
            ans.add(String.valueOf(chs));
            return;
        }
        // 字典表，记录字符是否存在
        boolean[] visit = new boolean[26]; // visit[0 1 .. 25]
        for (int j = i; j < chs.length; j++) {
            // str[j] = 'a'   -> 0   visit[0] -> 'a'

            // str[j] = 'z'   -> 25   visit[25] -> 'z'
            if (!visit[chs[j] - 'a']) {

                visit[chs[j] - 'a'] = true;
                swap(chs, i, j);
                process02(chs, i + 1, ans);
                // 恢复现场
                swap(chs, i, j);

            }
        }

    }

    public static void swap(char[] chs, int i, int j) {
        char tmp = chs[i];
        chs[i] = chs[j];
        chs[j] = tmp;
    }

    public static void main(String[] args) {
        String s = "abc";
        List<String> ans1 = permutation(s);
        for (String str : ans1) {
            System.out.println(str);
        }
        System.out.println("=======");
        List<String> ans2 = permutationNoRepeat(s);
        for (String str : ans2) {
            System.out.println(str);
        }

    }


}
