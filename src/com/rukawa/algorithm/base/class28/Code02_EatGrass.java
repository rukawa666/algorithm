package com.rukawa.algorithm.base.class28;

/**
 * create by hqh on 2023/1/9
 */
public class Code02_EatGrass {
    /**
     * 给定一个正整数N，表示有N份青草统一放在仓库中，
     * 有一只羊和一只牛，牛先吃，羊后吃，它俩轮流吃草，
     * 不管是牛还是羊，每一份能吃的草的数量是：1、4、16、64(4的某次方)
     * 谁先把草吃完，谁获胜。假设羊和牛都绝顶聪明，都想赢，都会做出理性的判断
     * 根据唯一参数N，返回谁会赢
     */

    public static String winner1(int n) {
        if (n < 5) {
            return n == 0 || n == 2 ? "后手" : "先手";
        }
        // 进到这个过程中，当前的"先手"先选
        int want = 1;
        while (want <= n) {
            if (winner1(n - want).equals("后手")) {
                return "先手";
            }
            if (want <= (n / 4)) {
                want *= 4;
            } else {
                break;
            }
        }
        return "后手";
    }

    public static String winner2(int n) {
        if (n % 5 == 0 || n % 5 == 2) {
            return "后手";
        } else {
            return "先手";
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 50; i++) {
            if (winner1(i) != winner2(i)) {
                System.out.println("测试异常！！！");
            }
//            System.out.println(i + " : " + winner1(i));
        }
    }
}
