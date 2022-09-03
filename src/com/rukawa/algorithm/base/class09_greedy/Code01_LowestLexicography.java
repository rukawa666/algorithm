package com.rukawa.algorithm.base.class09_greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-22 23:39
 * @Version：1.0
 */
public class Code01_LowestLexicography {


    /**
     * 给定一个由字符组成的数组strs
     * 必须把所有字符串拼接起来
     * 返回所有可能的拼接结果中、字典序最小的结果
     */
    // 暴力方法
    public static String lowestString01(String[] strS) {
        if (strS == null || strS.length == 0) {
            return "";
        }

        ArrayList<String> all = new ArrayList<>();
        HashSet<Integer> use = new HashSet<>();
        process(strS, use, "", all);
        String lowest = all.get(0);
        for (int i = 1; i < all.size(); i++) {
            if (all.get(i).compareTo(lowest) < 0) {
                lowest = all.get(i);
            }
        }
        return lowest;
    }

    /**
     *
     * @param strS 放着所有的字符串
     * @param use   已经使用过的字符串的下标，在use里面登记，不要在使用了
     * @param path  之前使用过的字符串，拼接成了 -> path
     * @param all   用all收集所有可能的拼接结果
     */
    public static void process(String[] strS, HashSet<Integer> use, String path, ArrayList<String> all) {

        if (use.size() == strS.length) {
            all.add(path);
        } else {
            for (int i = 0; i < strS.length; i++) {
                if (!use.contains(i)) {
                    use.add(i);
                    process(strS, use, path + strS[i], all);
                    use.remove(i);
                }
            }
        }
    }


    /**
     * 传递性
     * 如果a.concat(b) <= b.concat(a), b.concat(c) <= c.concat(b)
     * 能得出a.concat(c) <= c.concat(a) 则说明具有传递性
     *
     * *******证明传递性********
     * 拼接是什么
     * "123".concat("456")  => "123456"
     * 如果是k进制的，则有如下转化
     * 用数学代替，则是 "123" * k^3 + "456"
     *
     * 拼接得出的表达式：a * k^len(b) + b
     * 如果k^len(x)用函数m(x)代替
     *
     * 则上面的表达式可以改写为：
     * 表达式1：a * m(b) + b <= b * m(a) + a
     * 表达式2：b * m(c) + c <= c * m(b) + b
     *
     * 表达式1等式两边先减b，在同时乘c，得到如下表达式3
     * a * m(b) <= b * m(a) + a - b
     * a * m(b) * c <= c * b * m(a) + ac - bc
     *
     * 表达式2等式两边先减b，在同时乘a，得到如下表达式4
     * b * m(c) + c - b <= c * m(b)
     * a * b * m(c) + ac - ba <= a * m(b) * c
     *
     * 此时比较两个等式3和4
     * a * m(b) * c <= c * b * m(a) + ac - bc
     * a * b * m(c) + ac - ba <= a * m(b) * c
     *
     * 得出a * b * m(c) + ac - ba <= c * b * m(a) + ac - bc
     * a * m(c) - a <= c * m(a) - c
     * a * m(c) + c <= c * m(a) + a
     *
     * 此时得出结论：a.concat(c) <= c.concat(a)
     */
    public static class MyComparator implements Comparator<String> {

        @Override
        public int compare(String a, String b) {
            return (a + b).compareTo(b + a);
        }
    }


    // 贪心方法
    public static String lowestString02(String[] strS) {
        if (strS == null || strS.length == 0) {
            return "";
        }

        Arrays.sort(strS, new MyComparator());
        String res = "";
        for (int i = 0; i < strS.length; i++) {
            res += strS[i];
        }
        return res;
    }

    // for test
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 5);
            ans[i] = (char) (97 + value);
        }
        return String.valueOf(ans);
    }


    // for test
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    // for test
    public static String[] copyStringArray(String[] arr) {
        String[] ans = new String[arr.length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = String.valueOf(arr[i]);
        }
        return ans;
    }

    public static void main(String[] args) {
        int arrLen = 6;
        int strLen = 5;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr1 = generateRandomStringArray(arrLen, strLen);
            String[] arr2 = copyStringArray(arr1);
            if (!lowestString01(arr1).equals(lowestString02(arr2))) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }



}
