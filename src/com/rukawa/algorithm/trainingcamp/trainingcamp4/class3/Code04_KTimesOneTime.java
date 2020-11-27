package com.rukawa.algorithm.trainingcamp.trainingcamp4.class3;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-13 20:43
 * @Version：1.0
 */
public class Code04_KTimesOneTime {
    /**
     * 给定一个int类型的数组arr，已知除了一种数只出现一次之外
     * 剩下所有的数都出现了k次，如何使用O(1)的额外空间，找到这个数
     */
    public static int onceNum(int[] arr, int k) {
        /**
         * 流程：
         *   1、准备一个进制来表示数，完全取决于其他数出现了多少次，如果出现了k次
         *   2、如果出现了k次，则准备一个三进制的数，实则准备一个数组，因为2进制的数位数不会超过32位，
         *      则3进制数准备的数组长度必然不超过32位
         *      举例：14的三进制 9 + 3 + 1 + 1，9出现在2位置值为1，3出现在1位置值为1,1出现在0位置值为2，出现两次
         *           6的三进制 3 + 3, 3出现在1位置值为1(出现两次)，其余都是0
         *   3、把数组所有的数，按照三进制展开累加到数组上
         *   4、然后数组的每个位置的值%3，得出一个%3的数组，每个位置的值都是0~2的数
         *   5、%3的数组还原成10进制，就是对应的结果，只出现了一次的数
         *
         * 原理：
         *   只要一个数出现k次，最后%k之后的结果都是0，只有出现了一次的值会把自己的东西留下来
         */
        int[] eo = new int[32];
        for (int i = 0; i < arr.length; i++) {
            // 当前数是arr[i],把arr[i]变成k进制的形式，每一位累加到eo
            setExclusiveOr(eo, arr[i], k);
        }
        int res = getNumFromKSysNum(eo, k);
        return res;
    }

    public static void setExclusiveOr(int[] eo, int value, int k) {
        int[] curKSysNum = getKSysNumFromNum(value, k);
        for (int i = 0; i < eo.length; i++) {
            // 每一位都%，防止溢出
            eo[i] = (eo[i] + curKSysNum[i]) % k;
        }
    }

    // 10进制的数得到一个k进制的数
    public static int[] getKSysNumFromNum(int value, int k) {
        int[] res = new int[32];
        int index = 0;
        while (value != 0) {
            res[index++] = value % k;
            value = value / k;
        }
        return res;
    }

    // 把一个数组转换成一个10进制的数
    public static int getNumFromKSysNum(int[] eo, int k) {
        int res = 0;
        for (int i = eo.length - 1; i != -1; i--) {
            res = res * k + eo[i];
        }
        return res;
    }

    public static void main(String[] args) {
        int[] test1 = { 0,1,0,1,0,1,99 };
        System.out.println(onceNum(test1, 3));

        int[] test2 = {-401451,-177656,-2147483646,-473874,-814645,-2147483646,-852036,-457533,-401451,-473874,-401451,-216555,-917279,-457533,-852036,-457533,-177656,-2147483646,-177656,-917279,-473874,-852036,-917279,-216555,-814645,2147483645,-2147483648,2147483645,-814645,2147483645,-216555};
        System.out.println(onceNum(test2, 3));
    }
}
