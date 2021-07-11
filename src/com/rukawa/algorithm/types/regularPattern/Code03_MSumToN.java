package com.rukawa.algorithm.types.regularPattern;

/**
 * @className: Code03_MSumToN
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/11 0011 22:01
 **/
public class Code03_MSumToN {
    /**
     *  定义一种数：可以表示成若干(数量>1)连续正数和的数
     *  比如：
     * 	5=2+3，5就是这样的数
     * 	12=3+4+5，12就是这样的数
     * 	1不是这样的数，因为要求数量大于1个、连续正数和
     * 	2=1+1,2也不是，因为等号右边不是连续正数
     * 	给定一个参数N，返回是不是可以表示成若干个连续正数和的数
     */

    // 暴力尝试
    public static boolean isMSum1(int num) {
        // 从1开始尝试，1+2+3+... 看看能不能加到100
        // 从2开始尝试，2+3+3+... 看看能不能加到100
        for (int start = 1; start <= num; start++) {
            int sum = start;
            for (int j = start + 1; j <= num; j++) {

                if (sum + j > num) {
                    break;
                }

                if (sum + j == num) {
                    return true;
                }
                sum += j;
            }
        }
        return false;
    }

    // 得出结论，2的某次方是false
    public static boolean isMSum2(int num) {
        // 如果一个数的二进制只有一个1，说明是2的某次方
        // 不止有一个1，说明不是2的某次方
        // 把最右侧的1提取出来，和当前数一样大，说明只有一个1
//        return (num & (num - 1)) != 0;
//        return num != (num & (~num + 1));
        return num != (num & (-num));
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
//            System.out.println(i + " : " + isMSum1(i));
//            System.out.println(i + " : " + isMSum2(i));
            if (isMSum1(i) != isMSum2(i)) {
                System.out.println("Failed...");
            }
        }
    }
}
