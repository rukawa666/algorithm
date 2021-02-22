package com.rukawa.algorithm.types.dp.recursion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-02-22 8:25
 * @Version：1.0
 */
public class Code02_PrintAllSubSequences {

    /**
     * 打印一个字符串的全部子序列
     */
    public static List<String> printAll(String s) {
        char[] str = s.toCharArray();
        List<String> res = new ArrayList<>();
        String path = "";
        process1(str, 0, res, path);
        return res;
    }
    // str 固定参数
    // 来到了str[index]字符，index是位置
    // str[0..index-1]已经走过了！之前的决定，都在path上
    // 之前的决定已经不能改变了，就是path
    // str[index....]还能决定，之前已经确定，而后面还能自由选择的话，
    // 把所有生成的子序列，放入到res里去
    public static void process1(char[] str, int index, List<String> res, String path) {
        if (index == str.length) {
            res.add(path);
            return;
        }
        process1(str, index + 1, res, path);
        process1(str, index + 1, res, path + String.valueOf(str[index]));
    }


    /**
     * 打印一个字符串的全部子序列，要求不要出现重复字面值的子序列
     */
    public static List<String> printAllNoRepeat(String s) {
        char[] str = s.toCharArray();
        List<String> res = new ArrayList<>();
        HashSet<String> set = new HashSet<>();
        String path = "";
        process2(str, 0, set, path);
        res.addAll(set);
        return res;
    }

    public static void process2(char[] str, int index, HashSet<String> res, String path) {
        if (index == str.length) {
            res.add(path);
            return;
        }
        process2(str, index + 1, res, path);
        process2(str, index + 1, res, path + String.valueOf(str[index]));
    }

    public static void main(String[] args) {
        String test = "acccc";
        List<String> ans1 = printAll(test);
        List<String> ans2 = printAllNoRepeat(test);

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
