package com.rukawa.algorithm.base.class11;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-26 10:43
 * @Version：1.0
 */
public class Code01_Hanoi {


    /**
     * 打印N层汉诺塔从最左边到最右边的全部过程
     * @param n
     */
    public static void hanoi01(int n) {
        leftToRight(n);
    }

    // 请把1~N层圆盘，从左 -> 右
    public static void leftToRight(int n) {
        if (n == 1) {
            System.out.println("Move 1 from  left to right");
            return;
        }
        leftToMid(n - 1);
        System.out.println("Move " + n + " from left to right");
        midToRight(n - 1);
    }

    // 请把1~N层圆盘，从右 -> 左
    public static void rightToLeft(int n) {
        if (n == 1) {
            System.out.println("Move 1 from right to left");
        }

        rightToMid(n -1);
        System.out.println("Move " + n + " from right to left");
        midToLeft(n -1);

    }

    // 请把1~N层圆盘，从左 -> 中
    public static void leftToMid(int n) {
        if (n == 1) {
            System.out.println("Move 1 from left to mid");
            return;
        }
        leftToRight(n - 1);
        System.out.println("Move " + n + " from left to mid");
        rightToMid(n - 1);

    }

    // 请把1~N层圆盘，从右 -> 中
    public static void rightToMid(int n) {
        if (n == 1) {
            System.out.println("Move 1 from right to mid");
            return;
        }
        rightToLeft(n - 1);
        System.out.println("Move " + n +" from right to mid");
        leftToMid(n - 1);

    }

    // 请把1~N层圆盘，从中 -> 右
    public static void midToRight(int n) {
        if (n == 1) {
            System.out.println("Move 1 from mid to right");
            return;
        }
        midToLeft(n - 1);
        System.out.println("Move " + n + " from mid to right");
        leftToRight(n - 1);

    }

    // 请把1~N层圆盘，从中 -> 左
    public static void midToLeft(int n) {
        if (n == 1) {
            System.out.println("Move 1 from mid to left");
            return;
        }

        midToRight(n - 1);
        System.out.println("Move " + n + " from mid to left");
        rightToLeft(n - 1);
    }

    // 1~i圆盘目标是from -> to, other是另外一个
    public static void func(int N, String from, String to, String other) {
        if (N == 1) {
            System.out.println("Move 1 from " + from + " to " + to);
            return;
        } else {
            func(N - 1, from, other, to);
            System.out.println("Move " + N + " from " + from + " to " + to);
            func(N - 1, other, to, from);
        }
    }

    public static void hanoi02(int n) {
        if (n > 0) {
            func(n, "left", "right", "mid");
        }
    }


    public static void main(String[] args) {
        int n = 3;
        hanoi01(n);
        System.out.println("---------------------");
        hanoi02(n);
        System.out.println("---------------------");
    }
}
