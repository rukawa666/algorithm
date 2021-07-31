package com.rukawa.algorithm.types.advanced;

/**
 * @className: Code_A4_MinSwapStep
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/31 0031 19:29
 **/
public class Code_A4_MinSwapStep {
    /**
     * 一个数组中只有两种字符'G'和'B'
     * 可以让所有的G都放在左侧，所有的B都放在右侧，
     * 或者可以让所有的G都放在右侧，所有的B都放在左侧
     * 但是只能在相邻字符之间进行交换的操作
     * 返回至少要交换几次
     */
    public static int minStep1(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        char[] str = s.toCharArray();
        int step1 = 0;
        int gIndex = 0;
        for (int i = 0; i < str.length; i++) {
            if (str[i] == 'G') {
                step1 += i - (gIndex++);
            }
        }

        int step2 = 0;
        int bIndex = 0;
        for (int i = 0; i < str.length; i++) {
            if (str[i] == 'B') {
                step2 += i - (bIndex++);
            }
        }
        return Math.min(step1, step2);
    }

    public static int minStep2(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        char[] str = s.toCharArray();
        int step1 = 0;
        int gIndex = 0;
        int step2 = 0;
        int bIndex = 0;
        for (int i = 0; i < str.length; i++) {
            if (str[i] == 'G') {
                step1 += i - (gIndex++);
            } else {
                step2 += i - (bIndex++);
            }
        }
        return Math.min(step1, step2);
    }

    // 为了测试
    public static String randomString(int maxLen) {
        char[] str = new char[(int) (Math.random() * maxLen)];
        for (int i = 0; i < str.length; i++) {
            str[i] = Math.random() < 0.5 ? 'G' : 'B';
        }
        return String.valueOf(str);
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            String str = randomString(maxLen);
            int ans1 = minStep1(str);
            int ans2 = minStep2(str);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }
}
