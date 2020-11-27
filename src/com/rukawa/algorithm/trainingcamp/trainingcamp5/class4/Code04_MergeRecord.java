package com.rukawa.algorithm.trainingcamp.trainingcamp5.class4;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-11-08 15:28
 * @Version：1.0
 */
public class Code04_MergeRecord {
    // 01:04:37
    /**
     * 给定正数power，给定一个数组arr，给定一个数组reverse。
     * 含义如下：
     * arr的长度一定是2的power次方
     * reverse中的每个值一定都在0~power范围
     * 例如：power=2，arr={3,1,4,2}，reverse={0,1,0,2}
     * 任何一个在前的数字可以和任何一个在后的数组，构成一对数。
     * 可能是升序关系、相等关系或者降序关系。
     * arr开始是有如下降序对，(3,1)、(3,2)、(4,2),一共三个。
     * 接下来根据reverse对arr进行调整：
     * reverse[0]=0，表示在arr中，划分每1(2的0次方)个数为一组，然后每个小组内内部逆序，
     * 那么arr变成[3,1,4,2]，此时有3个降序对；
     * reverse[1]=1，表示在arr中，划分每2(2的1次方)个数为一组，然后每个小组内内部逆序，
     * 那么arr变成[1,3,2,4]，此时有1个降序对；
     * reverse[2]=0，表示在arr中，划分每1(2的0次方)个数为一组，然后每个小组内内部逆序，
     * 那么arr变成[1,3,2,4]，此时有1个降序对；
     * reverse[3]=2，表示在arr中，划分每4(2的2次方)个数为一组，然后每个小组内内部逆序，
     * 那么arr变成[4,2,3,1]，此时有5个降序对；
     * 那么返回[3,1,1,5]，表示每次调整之后的降序对数量
     */
    public static int[] reversePair2(int[] originArr, int[] reverseArr, int power) {
        /**
         * 思路：
         *  arr={3,0,6,2,7,8,6,5}
         *  1、定义一个数组dp1,表示逆序数的数量
         *      dp1[0]表示划分每1(2的0次方)个数为一组，组内有多少个逆序对，共0个
         *      dp1[1]表示划分每2(2的1次方)个数为一组，每个组内的逆序对一共加起来有多少，(3,0),(6,2),(6,5),共3个
         *      dp1[2]表示划分每4(2的2次方)个数为一组，每个组内前两个数中选一个，后两个数选一个，
         *            {3,0,|6,2}为一组，前一半{3,0}选一个，后一半{6,2}选一个, (3,2)
         *            {7,8,|6,5}为一组，前一半{7,8}选一个，后一半{6,5}选一个, (7,6),(7,5),(8,6),(8,5)
         *            共5个
         *      dp1[3]表示划分每8(2的3次方)个数为一组，第一个数来自前一半，第二个数来自后一半，
         *            {3,0,6,2|,7,8,6,5},前一半{3,0,6,2}选一个，后一半{7,8,6,5}选一个，(6,5),共1个
         *      dp1={0,3,5,1}
         *
         *  2、定义一个数组dp2，表示正序对的数量
         *      dp2[0]表示划分每1(2的0次方)个数为一组，组内有多少个正序对，共0个
         *      dp2[1]表示划分每2(2的1次方)个数为一组，每个组内的前一半选一个，后一个选一个，(7,8),共1个
         *      dp2[2]表示划分每4(2的2次方)个数为一组，每个组内前一半中选一个，后一半选一个，
         *            {3,0,|6,2}为一组，前一半{3,0}选一个，后一半{6,2}选一个, (3,6),(0,6),(0,2)
         *            {7,8,|6,5}为一组，前一半{7,8}选一个，后一半{6,5}选一个, 么有
         *            共3个
         *      dp2[3]表示划分每8(2的3次方)个数为一组，第一个数来自前一半，第二个数来自后一半，
         *            {3,0,6,2|,7,8,6,5},前一半{3,0,6,2}选一个，后一半{7,8,6,5}选一个，
         *            (3,7),(3,8),(3,6),(3,5),
         *            (0,7),(0,8),(0,6),(0,5),
         *            (6,7),(6,8),
         *            (2,7),(2,8),(2,6),(2,5)
         *            共14个
         *      dp2={0,1,3,14}
         *  3、arr数组相邻两个数交换位置，{0,3,2,6,8,7,5,6}
         *     建立的新数组，求dp1和dp2，有什么变化？
         *     dp1[1]和dp2[1]交换答案，不会影响dp1[2],dp1[3],dp1[3],dp2[3]的值
         *     不会影响dp1[2],dp1[3],dp1[3],dp2[3]的值？ -> 前一半选一个数，两个数交换，不会影响前一个和后一半的划分
         *
         *  4、arr数组4个数交换位置，{2,6,0,3,5,6,8,7}
         *    建立的新数组，求dp1和dp2，有什么变化？
         *    dp1[1]和dp2[1]交换，dp1[2]和dp2[2]交换，
         *    dp1[3]和dp2[3]不影响 ？ -> 4个数交换，dp[3]在8个数中，前一半选一个，后一半选一个，不会影响其答案
         *
         *  5、arr数组8个数交换位置，{5,6,8,7,2,6,0,3}
         *     建立的新数组，求dp1和dp2，有什么变化？
         *     dp1[1]和dp2[1]交换，dp1[2]和dp2[2]交换，dp1[3]和dp2[3]交换
         *
         *  怎么求dp1和dp2？ 小和问题求解
         */
        int[] reverse = copyArray(originArr);
        reversePart(reverse, 0, reverse.length - 1);
        int[] recordDown = new int[power + 1];
        int[] recordUp = new int[power + 1];
        process(originArr, 0, originArr.length - 1, power, recordDown);
        process(reverse, 0, reverse.length - 1, power, recordUp);
        int[] res = new int[reverseArr.length];
        for (int i = 0; i < reverseArr.length; i++) {
            int curPower = reverseArr[i];
            // 从1开始到当前操作的值，交换
            for (int p = 1; p <= curPower; p++) {
                int tmp = recordDown[p];
                recordDown[p] = recordUp[p];
                recordUp[p] = tmp;
            }

            for (int p = 1; p <= power; p++) {
                res[i] += recordDown[p];
            }
        }
        return res;
    }

    public static int[] copyArray(int[] arr) {
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // 原数组的逆序数组
    public static void reversePart(int[] reverse, int l, int r) {
        while (l < r) {
            int tmp = reverse[l];
            reverse[l++] = reverse[r];
            reverse[r--] = tmp;
        }
    }

    // 怎么求dp数组 归并排序的小和问题
    // arr[l...r] 一共有2的power次方个数
    // arr[0...7] 一共有2的3次方个数
    public static void process(int[] arr, int l, int r, int power, int[] record) {
        if (l == r) {
            return;
        }
        int mid = l + ((r - l) >> 1);
        process(arr, l, mid, power - 1, record);
        process(arr, mid + 1, r, power - 1, record);
        record[power] += merge(arr, l, mid, r);
    }

    public static int merge(int[] arr, int l, int m, int r) {
        int[] help = new int[r - l + 1];
        int i = 0;
        int p1 = l;
        int p2 = m + 1;
        int res = 0;
        while (p1 <= m && p2 <= r) {
            res += arr[p1] > arr[p2] ? (m - p1 + 1) : 0;
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }

        while (p1 <= m) {
            help[i++] = arr[p1++];
        }

        while (p2 <= r) {
            help[i++] = arr[p2++];
        }
        for (i = 0; i < help.length; i++) {
            arr[l + 1] = help[i];
        }
        return res;
    }
}
