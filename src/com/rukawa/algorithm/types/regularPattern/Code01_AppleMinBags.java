package com.rukawa.algorithm.types.regularPattern;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/7/11 0011 10:48
 */
public class Code01_AppleMinBags {
    /**
     * 小虎去买苹果，商店只提供两种类型的塑料袋，每种类型都有任意数量。
     * 1、能装下6个苹果的袋子
     * 2、能装下8个苹果的袋子
     * 小虎可以自由使用两种袋子来装苹果，但是小虎有强迫症，他要求自己使用的袋子数量
     * 必须最少，且使用的每个袋子都必须装满。
     * 给定一个正整数N，返回至少使用多少袋子。如果N无法让使用的每个袋子必须装满，返回-1.
     */

    // 暴力解
    // 通过暴力解的答案分析
    public static int minBags1(int apple) {
        if (apple < 0) {
            return -1;
        }
        // 8号袋子从apple/8, 向下取整尝试
        int bag8 = apple / 8;
        // 还剩下的苹果
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

    /**
     *  暴力发现规律：
     *  从18~25 偶数返回3，奇数返回-1
     *  从26~33，偶数返回4，奇数返回-1
     *  每8个数，偶数返回正常结果，奇数返回-1
     *
     *  0~17 的规律不知道
     *  18开始的8个数作为第0组，下面8个数作为第1组，依次类推
     *  apple落在0组中，偶数返回0+3，奇数返回-1；1组中，偶数返回1+3，奇数返回-1；。。。。
     */
    public static int minBags2(int apple) {
        if ((apple & 1) != 0) {     // 如果是奇数，返回-1
            return -1;
        }
        if (apple < 18) {
            return apple == 0 ? 0 : (apple == 6 || apple == 8) ? 1 : (apple == 12 || apple == 14 || apple == 16) ? 2 : -1;
        }
        return (apple - 18) / 8 + 3;
    }

    public static int minBagBase6(int rest) {
        return rest % 6 == 0 ? (rest / 6) : -1;
    }

    public static void main(String[] args) {
        for (int apple = 0; apple < 200; apple++) {
//            System.out.println(apple + " : " + minBags1(apple));
            if (minBags1(apple) != minBags2(apple)) {
                System.out.println("failed");
            }
        }
    }
}
