package com.rukawa.algorithm.types.dp.quadrilateral;

/**
 * @className: Code02_BestSplitForEveryPosition
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/18 0018 19:28
 **/
public class Code02_BestSplitForEveryPosition {
    /**
     * 把题目1中提到的min{左部分累加和，右部分累加和}，定义为S(N-1)，也就是说
     * S(N-1)：在arr[0...N-1]范围上，做最优划分所得到的min{左部分的累加和，右部分的累加和}的最大值
     * 现在要求返回长度为N的s数组，s[i]=在arr[0...i]范围上，做最优划分所得到的min{左部分的累加和，右部分的累加和}的最大值
     * 得到整个s数组的过程，做到时间复杂度O(N)
     *
     * 思路：
     *   结论：1、假设0~17得到最优划分，0~8为左部分，9~17为右部分
     *        现在要在0~18范围上怎么划分最优？之前0~17的最优划分需不需要回退，如果不需要，则过程可简化，
     *        尝试从0~8是左部分，9~18是右部分开始
     *
     *        2、怎么找到最右划分点，划分出的最小值往右在移动会变小，则当前的最小值是最佳答案，当前划分为最右划分
     *
     *   证明：当前0~17范围上最右划分在9和10之间，在0~18范围上，最优划分在哪个位置？
     *   0~17的最优划分得出两个部分
     *   1、假设左部分的累加和比右部分小，是答案。此时把18划分到右部分，此时不需要划分需要往左
     *   2、假设右部分的累加和比左部分小，是答案。此时把18划分到右部分
     *      a、18进入后，左部分变为答案。原来右部分是答案，右部分加入18位置的数后现在左部分的和变小，如果再往左划分，则左部分更小，所以不需要往左划分
     *      b、18进入后，右部分还是答案。还是不能继续往左移动，之前没移动现在还是不能移动。右部分是最小值，现右部分加入一个数字还是最小值，所以不能往左划分
     *
     *  结论：0~i位置的最优化分，一旦定了，当0~i+1问题的时候，左侧的划分不需要去尝试，只需要当前划分位置接着去尝试。划分位置不回退。
     */

    // 暴力尝试
    // O(N^3)
    public static int[] bestSplit1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int N = arr.length;
        int[] ans = new int[N];
        ans[0] = 0;
        for (int range = 1; range < N; range++) {
            for (int s = 0; s < range; s++) {
                int sumL = 0;
                for (int L = 0; L <= s; L++) {
                    sumL += arr[L];
                }
                int sumR = 0;
                for (int R = s + 1; R <= range; R++) {
                    sumR += arr[R];
                }
                ans[range] = Math.max(ans[range], Math.min(sumL, sumR));
            }
        }
        return ans;
    }

    // O(N^2)
    public static int[] bestSplit2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int N = arr.length;
        int[] ans = new int[N];
        ans[0] = 0;
        int[] sum = new int[N + 1];
        for (int i = 0; i < N; i++) {
            sum[i + 1] = sum[i] + arr[i];
        }
        for (int range = 1; range < N; range++) {
            for (int s = 0; s < range; s++) {
                int sumL = sum(sum, 0, s);
                int sumR = sum(sum, s + 1, range);
                ans[range] = Math.max(ans[range], Math.min(sumL, sumR));
            }
        }
        return ans;
    }

    // 求原来的数组arr中，arr[L...R]的累加和
    public static int sum(int[] sum, int L, int R) {
        return sum[R + 1] + sum[L];
    }

    // 最优解
    // O(N)
    public static int[] bestSplit3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int N = arr.length;
        int[] ans = new int[N];
        // 0~0划分，左部分或者右部分没有，答案是0
        ans[0] = 0;
        // arr = {5, 3, 1, 3}
        //        0  1  2  3
        // sum = {0, 5, 8, 9, 12}
        //        0  1  2  3  4
        // 0~2 -> sum[3] - sum[0]
        // 1~3 -> sum[4] - sum[1]
        int[] sum = new int[N + 1];
        for (int i = 0; i < N; i++) {
            sum[i + 1] = sum[i] + arr[i];
        }
        // 后面的划分结果大于前面划分的结果，则要动
        // 如果是有0的情况下，后面>=前面 才可以动. [5 0 0 0 5 5 5] 后面>=前面

        // 0~range-1上，最优划分，左部分[0~best],右部分[best+1, range]
        int best = 0;
        for (int range = 1; range < N; range++) {
            /**
             * 0~6最优划分：左部分[0~4]，右部分[5~6]
             * 后面还有一次划分[0~5][6~6]，此时的5是best，左部分的最右边界
             * 如果best+1==range，此时已经是最后的划分位置
             */
            while (best + 1 < range) {  // 后面还有位置可以尝试，右部分不能没数
                // 划分之前的最优划分
                int before = Math.min(sum(sum, 0, best), sum(sum, best + 1, range));
                // 往下继续尝试
                int after = Math.min(sum(sum, 0, best + 1), sum(sum, best + 1, range));
                // 如果之后的划分是当前答案变小，则当前答案是最优划分
                // best不回退
                if (after >= before) {
                    best++;
                } else {
                    break;
                }
            }
            ans[range] = Math.min(sum(sum, 0, best), sum(sum, best + 1, range));
        }
        return ans;
    }

    public static int[] randomArray(int len, int max) {
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * max);
        }
        return ans;
    }

    public static boolean isSameArray(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }
        int N = arr1.length;
        for (int i = 0; i < N; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int N = 20;
        int max = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            int[] arr = randomArray(len, max);
            int[] ans1 = bestSplit1(arr);
            int[] ans2 = bestSplit2(arr);
            int[] ans3 = bestSplit3(arr);
            if (!isSameArray(ans1, ans2) || !isSameArray(ans2, ans3)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }
}
