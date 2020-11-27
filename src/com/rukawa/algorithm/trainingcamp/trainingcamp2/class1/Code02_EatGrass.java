package com.rukawa.algorithm.trainingcamp.trainingcamp2.class1;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-07 7:45
 * @Version：1.0
 */
public class Code02_EatGrass {
    /**
     * 给定一个正整数N，表示有N份青草统一放在仓库中，
     * 有一只羊和一只牛，牛先吃，羊后吃，它俩轮流吃草，
     * 不管是牛还是羊，每一份能吃的草的数量是：1、4、16、64(4的某次方)
     * 谁先把草吃完，谁获胜。假设羊和牛都绝顶聪明，都想赢，都会做出理性的判断
     * 根据唯一参数N，返回谁会赢
     * @param n
     * @return
     */
    public static String winner1(int n) {
        if (n < 5) {
            return (n == 0 || n == 2) ? "-->后手" : "先手";
        }

        // n >= 5时
        int base = 1;   // 先手决定吃的草数
        // 当前先手先选
        while (base <= n) {
            // 当前共n份草，先手吃掉的base份，n-base是留给后手的
            // 在后序过程中，我是作为后手的，我如果能赢，则代表我是先手赢了
            if (winner1(n - base).equals("-->后手")) {
                return "先手";
            }
            if (base > n / 4) { // 防止base*4之后溢出，可能变为负数，循环出不来
                break;
            }

            base *= 4;
        }
        return "-->后手";
    }
    public static String winner2(int n) {
        // 规律：0~9 之间  0 2 5 7  是后手
        // 0 ~ 4 之间 0 2 是后手
        if (n % 5 == 0 || n % 5 == 2) {
            return "后手";
        } else {
            return "先手";
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            System.out.println(i + " : " + winner1(i));
        }

        System.out.println(winner1(50));
        System.out.println(winner2(50));
    }
}
