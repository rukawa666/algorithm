package com.rukawa.algorithm.trainingcamp.trainingcamp2.class2;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-09 22:36
 * @Version：1.0
 */
public class Code03_LongestLessSumSubArrayLength {

    /**
     * 数组有正、负、还有0
     * 子数组 sum <= K，符合这个条件的最长子数组长度是多少
     *
     * 改进前时间复杂度：O(n*logN)
     * 目前这个方法：O(N)
     */
    public static int maxLengthAwesome(int[] arr, int K) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        // minSum:子数组以i开头，往后自由选，在所有可能性中，哪一个i开头的子数组能取得最小的累加和，记录在i位置
        int[] minSums = new int[N];
        // minSumEnd:子数组以i开头，往后自由选，到j位置是i开头到的所有累加和中最下的
        int[] minSumEnds = new int[N];

        minSums[N - 1] = arr[N - 1];
        minSumEnds[N - 1] = N - 1;
        for (int i = N - 2; i >= 0; i--) {
            // 后一个元素为负数，则表示有利可图，表示可以扩展
            if (minSums[i + 1] <= 0) {
                minSums[i] = arr[i] + minSums[i + 1];
                minSumEnds[i] = minSumEnds[i + 1];
            } else {
                minSums[i] = arr[i];
                minSumEnds[i] = i;
            }
        }
        /**
         *  流程实质：维持住一个窗口，看看有没有再往右扩的可能性，如果没有可能性，本来这个开头的答案也不是我要的
         */
        // (i...)(...)(...),(end...),end之前的可以扩，之后的不能扩
        int end = 0;
        // i...(sum) end，i到end之前的累加和
        int sum = 0;
        // 最大长度
        int res = 0;
        // i是窗口的最左的位置，end扩出来的最右有效块的最后一个位置的，再下一个位置
        // end也是下一块儿的开始位置
        // 窗口:[i ~ end)
        for (int i = 0; i < N; i++) {
            // while循环结束之后
            // 1、如果以i开头的情况下，累加和<=K的最长子数组是arr[i...end-1],看看这个子数组长度能不能更新res
            // 2、如果以i开头的情况下，累加和<=K的最长子数组比arr[i...end-1]短，更新还是不更新res都不会影响最终结果
            while (end < N && sum + minSums[end] <= K) {
                // (end...?)(?+1...
                sum += minSums[end];
                end = minSumEnds[end] + 1;
            }
            // [i...][end...X]
            res = Math.max(res, end - i);

            // sum -= arr[i] 要缩窗口
            // 为什么用下面这种，例子：-7 3 3 3 3 3 3 3  k<=0
            // 此时[-7,3,3],3,3,3,3,3,括号里面就是达标的，end的位置就是2,minSums[end]=3,sum=-1
            // 所以end的位置一直是2，i的位置会超过2，所以如果出现这种情况，end要i+1扩
            if (end > i) {  // 窗口内还有数[i...end)
                sum -= arr[i];
            } else {    // 窗口内已经没有数了，说明从i开头的所有子数组累加和都不可能<=K
                end = i + 1;
            }
        }
        return res;
    }

    /**
     * 时间复杂度：O(n*logN)
     * @param arr
     * @param K
     * @return
     */
    public static int maxLength(int[] arr, int K) {
        int[] h = new int[arr.length + 1];
        int sum = 0;
        h[0] = sum;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            h[i + 1] = Math.max(sum, h[i]);
        }
        sum = 0;
        int res = 0;
        int pre = 0;
        int len = 0;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            pre = getLessIndex(h, sum - K);
            len = pre == -1 ? 0 : i - pre + 1;
            res = Math.max(res, len);
        }
        return res;
    }

    public static int getLessIndex(int[] arr, int num) {
        int low = 0;
        int high = arr.length - 1;
        int mid = 0;
        int res = -1;
        while (low <= high) {
            mid = (low + high) / 2;
            if (arr[mid] >= num) {
                res = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return res;
    }

    // for test
    public static int[] generateRandomArray(int len, int maxValue) {
        int[] res = new int[len];
        for (int i = 0; i != res.length; i++) {
            res[i] = (int) (Math.random() * maxValue) - (maxValue / 3);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        for (int i = 0; i < 10000000; i++) {
            int[] arr = generateRandomArray(10, 20);
            int k = (int) (Math.random() * 20) - 5;
            if (maxLengthAwesome(arr, k) != maxLength(arr, k)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }

}
