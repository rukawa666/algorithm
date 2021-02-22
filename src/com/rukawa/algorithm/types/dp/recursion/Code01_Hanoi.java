package com.rukawa.algorithm.types.dp.recursion;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-02-04 9:26
 * @Version：1.0
 */
public class Code01_Hanoi {

    /**
     * 打印N层汉诺塔从左移动到最右边的全部过程
     */

    public static void hanoi1(int n) {
        if (n > 0) {
            func(n , "left", "right", "mid");
        }
    }


    public static void func(int N, String from, String to, String other) {
        if (N == 1) {
            System.out.println("Move 1 from " + from + " to " + to);
        } else {
            func(N - 1, from, other, to);
            System.out.println("Move " + N + " from " + from + " to " + to);
            func(N - 1, other, to, from);
        }
    }

    public static void main(String[] args) {
        int n = 3;
        hanoi1(n);
//        System.out.println("============");
//        hanoi2(n);
//		System.out.println("============");
//		hanoi3(n);
    }
}
