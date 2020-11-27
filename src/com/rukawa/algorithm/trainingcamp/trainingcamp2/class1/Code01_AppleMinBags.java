package com.rukawa.algorithm.trainingcamp.trainingcamp2.class1;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-07 7:23
 * @Version：1.0
 */
public class Code01_AppleMinBags {

    /**
     * 小虎去买苹果，商店只提供两种类型的袋子，每种类型的袋子任意数量。
     * 1、能装下6个苹果的袋子
     * 2、能装下8个苹果的袋子
     * 小虎自由使用袋子，要求使用袋子最少，且使用的袋子必须装满。
     * 给定一个正整数N，返回至少使用多少袋子。如果N无法让使用的袋子必须装满，返回-1。
     */
    public static int minBags(int apple) {
        if (apple < 0) {
            return -1;
        }
        int bag6 = -1;
        int bag8 = apple / 8;
        int rest = apple - 8 * bag8;
        while (bag8 >= 0 && rest < 24) {
            int restUse6 = minBagBase6(rest);
            if (restUse6 != -1) {
                bag6 = restUse6;
                break;
            }
            rest = apple - 8 * (--bag8);
        }
        return bag6 == -1 ? -1 : bag6 + bag8;
    }


    public static int minBagBase6(int rest) {
        return rest % 6 == 0 ? (rest / 6) : -1;
    }


    public static int minBagAwesome(int apple) {
        if ((apple & 1) != 0) {
            return -1;
        }

        if (apple < 18) {
            return apple == 0 ? 0 : (apple == 6 || apple == 8) ? 1
                    : (apple == 12 || apple == 14 || apple == 16) ? 2 : -1;
        }

        return (apple - 18) / 8 + 3;
    }

    public static void main(String[] args) {
        System.out.println(minBagAwesome(100));
        System.out.println("------------------------");
        for (int apple = 0; apple <= 100; apple++) {
            System.out.println(apple + " : " + minBags(apple));
        }
    }
}
