package com.rukawa.algorithm.trainingcamp.trainingcamp3.class1;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-18 22:18
 * @Version：1.0
 */
public class Code04_ColorLeftRight {
    /**
     * 有一些排成一行的正方形。每个正方形已经被染成红色或者绿色。现在可以选择任意一个正方形然后用这两种颜色中的任意一种
     * 进行染色，这个正方形的颜色将会被覆盖。目标是完成染色之后，每个红色R都比每个绿色G距离最左侧近。返回最少需要涂染几个正方形。
     * 如样例所示：s=RGRGR我们涂染后变成RRRGG满足要求了，涂染的个数为2，没有比这个更好的涂染方案
     */
    public static int mainPaint1(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[] right = new int[N];
        right[N - 1] = str[N - 1] == 'R' ? 1 : 0;
        for (int i = N - 2; i >= 0; i--) {
            right[i] = right[i + 1] + (str[i] == 'R' ? 1 : 0);
        }

        int res = right[0]; // 如果数组所有范围，都是右侧范围，都变成G
        int left = 0;
        for (int i = 0; i < N - 1; i++) {   // 0...n-2 左侧  n-1...N-1是右侧
            left += str[i] == 'G' ? 1 : 0;
            res = Math.min(res, left + right[i + 1]);
        }
        // 0...N-1判断整个范围都是左侧
        res = Math.min(res, left + (str[N - 1] == 'G' ? 1 : 0));
        return res;
    }

    public static int mainPaint2(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int rAll = 0;
        for (int i = 0; i < N; i++) {
            rAll += str[i] == 'R' ? 1 : 0;
        }

        int res = rAll;
        int left = 0;
        for (int i = 0; i < N - 1; i++) {
            left += str[i] == 'G' ? 1 : 0;
            rAll -= str[i] == 'R' ? 1 : 0;
            res = Math.min(res, left + left);
        }
        res = Math.min(res, left + (str[N - 1] == 'G' ? 1 : 0));
        return res;
    }

    public static void main(String[] args) {
        String test = "GGGGGR";
        System.out.println(mainPaint1(test));
        System.out.println(mainPaint2(test));
    }
}
