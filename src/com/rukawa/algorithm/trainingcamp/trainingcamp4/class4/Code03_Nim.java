package com.rukawa.algorithm.trainingcamp.trainingcamp4.class4;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-14 23:24
 * @Version：1.0
 */
public class Code03_Nim {

    /**
     * Nim博弈问题
     * 给定一个非负数组，每一个值代表在该位置有几个铜板。a和b玩游戏，a先手，b后手，
     * 轮到某个人的时候，只能在一个位置上拿任意数量的铜板，但是不能不拿。
     * 谁最先把铜板拿完谁赢。假设a和b都绝顶聪明，请返回获胜者的名字
     */

    // 保证arr是正数数组
    public static void printWinner(int[] arr) {
        /**
         * 先手：选择铜板的时候。数组异或和不是0，我选完之后，让整体的数组异或和都是0，都是搬空的时候异或和是0
         */
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }

        if (eor == 0) {
            System.out.println("后手赢");
        } else {
            System.out.println("先手赢");
        }
    }
}
