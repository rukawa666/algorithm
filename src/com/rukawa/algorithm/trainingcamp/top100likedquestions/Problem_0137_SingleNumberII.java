package com.rukawa.algorithm.trainingcamp.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 11:06
 * @Version：1.0
 */
public class Problem_0137_SingleNumberII {
    /**
     * 只出现一次的数字 II
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现了三次。找出那个只出现了一次的元素。
     *
     * 说明：
     * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
     *
     * 示例 1:
     * 输入: [2,2,3,2]
     * 输出: 3
     *
     * 示例 2:
     * 输入: [0,1,0,1,0,1,99]
     * 输出: 99
     */
    public static int singleNumber(int[] nums) {
        int[] eo = new int[32];
        for (int i = 0; i != nums.length; i++) {
            setExclusiveOr(eo, nums[i], 3);
        }
        int res = getNumFromKSysNum(eo, 3);
        return res;
    }

    public static void setExclusiveOr(int[] eo, int value, int k) {
        int[] curKSysNum = getKSysNumFromNum(value, k);
        for (int i = 0; i < eo.length; i++) {
            eo[i] = (eo[i] + curKSysNum[i]) % k;
        }
    }

    // 10进制转k进制数的数组
    public static int[] getKSysNumFromNum(int value, int k) {
        int[] res = new int[32];
        int index = 0;
        while (value != 0) {
            res[index++] = value % k;
            value /= k;
        }
        return res;
    }

    // k进制数的数组转为10进制数
    public static int getNumFromKSysNum(int[] eo, int k) {
        int res = 0;
        for (int i = eo.length - 1; i != -1; i--) {
            res = res * k + eo[i];
        }
        return res;
    }
    public static void main(String[] args) {
        int[] test1 = { 0,1,0,1,0,1,99 };
        System.out.println(singleNumber(test1));

        int[] test2 = {-401451,-177656,-2147483646,-473874,-814645,-2147483646,-852036,-457533,-401451,-473874,-401451,-216555,-917279,-457533,-852036,-457533,-177656,-2147483646,-177656,-917279,-473874,-852036,-917279,-216555,-814645,2147483645,-2147483648,2147483645,-814645,2147483645,-216555};
        System.out.println(singleNumber(test2)); // -2147483648

    }

    // TODO 越界问题，需要处理


}
