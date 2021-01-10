package com.rukawa.algorithm.offer;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-10 14:48
 * @Version：1.0
 */
public class Problem_0050 {

    public static char firstUniqChar(String s) {
        char[] chs = s.toCharArray();
        int eor = 0;
        for (int i = 0; i < chs.length; i++) {
            eor ^= (int) chs[i];
        }
        return (char) eor;
    }

    public static void main(String[] args) {
        String s = "abaccdeff";
        System.out.println(firstUniqChar(s));

    }
}
