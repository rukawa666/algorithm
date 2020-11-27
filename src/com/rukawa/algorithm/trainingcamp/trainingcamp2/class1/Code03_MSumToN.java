package com.rukawa.algorithm.trainingcamp.trainingcamp2.class1;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-08 9:13
 * @Version：1.0
 */
public class Code03_MSumToN {

    /**
     *  定义一种数：可以表示成若干(数量>1)连续正数和的数
     *  比如：
     * 	5=2+3，5就是这样的数
     * 	12=3+4+5，12就是这样的数
     * 	1不是这样的数，因为要求数量大于1个、连续正数和
     * 	2=1+1,2也不是，因为等号右边不是连续正数
     * 	给定一个参数N，返回是不是可以表示成若干个连续正数和的数
     * @param num
     * @return
     */
    public static boolean isMSum1(int num) {
        for (int i = 1; i < num; i++) {
            int sum =i;
            for (int j = i + 1; j < num; j++) {
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

    /**
     * num是不是2的某次方
     * (num & (num - 1)) == 0 是2的某次方
     * (num & (num - 1)) != 0 不是2的某次方
     * 解释：因为2的某次方，在二进制中某个位置只有一个1，而num-1会把这个数打散，下面举例说明
     * num=16 ->   0 0 0 1 0 0 0 0
     * num-1=15 -> 0 0 0 0 1 1 1 1
     * 所以num & (num - 1) 必然是等于0
     * @param num
     * @return
     */
    public static boolean isMSum2(int num) {
        if (num < 3) {
            return false;
        }
        return (num & (num - 1)) != 0;
    }

    public static void main(String[] args) {
        for (int num = 1; num < 200; num++) {
            System.out.println(num + " : " + isMSum1(num));
        }
        System.out.println("test begin");
        for (int num = 1; num < 5000; num++) {
            if (isMSum1(num) != isMSum2(num)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");

    }
}
