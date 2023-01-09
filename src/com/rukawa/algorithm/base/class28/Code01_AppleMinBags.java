package com.rukawa.algorithm.base.class28;

/**
 * create by hqh on 2023/1/9
 */
public class Code01_AppleMinBags {
    /**
     * 小虎去买苹果，商店只提供两种类型的袋子，每种类型的袋子任意数量。
     * 1、能装下6个苹果的袋子
     * 2、能装下8个苹果的袋子
     * 小虎自由使用袋子，要求使用袋子最少，且使用的袋子必须装满。
     * 给定一个正整数N，返回至少使用多少袋子。如果N无法让使用的袋子必须装满，返回-1。
     */

    /**
     * 先用8号袋子尝试
     */
    public static int minBags(int apple) {
        if (apple < 0) {
            return -1;
        }
        // 此时不用6号袋子
        int bag6 = -1;
        // 最多需要多个8号袋子
        int bag8 = apple / 8;
        // 最大努力的用了8号袋子，还剩余多少个苹果
        int rest = apple - 8 * bag8;

        while (bag8 >= 0) {
            if (rest % 6 == 0) {
                return bag8 + (rest / 6);
            } else {
                bag8--;
                rest += 8;
            }
        }
        return -1;
    }

    public static int minBagAwesome(int apple) {
        // 如果是奇数，返回-1
        if ((apple & 1) != 0) {
            return -1;
        }
        if (apple < 18) {
            return apple == 0 ? 0 : (apple == 6 || apple == 8) ? 1 : (apple == 12 || apple == 14 || apple == 16) ? 2
                    : -1;
        }
        return (apple - 18) / 8 + 3;
    }

    public static void main(String[] args) {
        for (int apple = 0; apple < 200; apple++) {
            if (minBags(apple) != minBagAwesome(apple)) {
                System.out.println("测试异常！！！");
            }
//            System.out.println(apple + " : " + minBags(apple));
        }
    }
}