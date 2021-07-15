package com.rukawa.algorithm.types.subArray;

/**
 * @className: Code03_LongestLessSumSubArrayLength
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/15 0015 21:49
 **/
public class Code03_LongestLessSumSubArrayLength {

    /**
     * 给定一个整数组成的无序数组arr，但可能正、负、0
     * 给定一个整数值K
     * 找到arr的所有子数组中，哪个子数组的累加和<=K，并且是长度最大的返回其长度
     *
     * 不是最优解：前缀和&有序表 O(N*logN)
     *
     * 最优解：假设有一个原数组arr，生成俩辅助数组minSum，minSumEnd
     *        minSum[i]：子数组必须以i开头，哪一个以i开头的子数组能取的最小的累加和
     *        minSumEnd[i]：必须以i开头的子数组，当它取得最小累加和的时候，它的右边界在哪
     *  从后往前填
     *          arr [1 2 -4 3 7 -2 4 -3]
     *               0 1  2 3 4  5 6  7
     *  minSum      [-1 -2 -4 3 5 -2 1 -3]
     *  minSumEnd   [ 2  2  2 3 5  5 7 7]
     *  任何以i开头的子数组后面的所有情况中，最优的最小的累加和存在minSum中，最优的最小的右边界存在minSumEnd
     *  i开头的信息要依赖i+1的信息，所以右边的要比左边的先算，所以从右往左算
     *  如果是i结尾的情况下，需要依赖i-1的信息，所以从左往右算
     */

    public static int maxLength(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[] minSum = new int[arr.length];
        int[] minSumEnd = new int[arr.length + 1];
        minSum[arr.length - 1] = arr[arr.length - 1];
        minSumEnd[arr.length - 1] = arr.length - 1;
        for (int i = arr.length - 2; i >= 0; i--) {
            if (minSum[i + 1] < 0) {
                minSum[i] = arr[i] + minSum[i + 1];
                minSumEnd[i] = minSumEnd[i + 1];
            } else {
                minSum[i] = arr[i];
                minSumEnd[i] = i;
            }
        }
        // 迟迟扩不进来的开头位置
        // 如果扩到13，end就是14位置
        int end = 0;
        // 窗口累加和
        int sum = 0;
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            // while 循环结束后：
            // 1、如果以i开头的情况下，累加和<=k的最长子数组是arr[i...end-i]，看看这个子数组长度能不能更新ans
            // 2、如果以i开头的情况下，累加和<=k的最长子数组比arr[i...end-i]短，更新还是不更新ans都不会影响最终结果
            while (end < arr.length && sum + minSum[end] <= k) {
                sum += minSum[end];
                end = minSumEnd[end] + 1;
            }
            ans = Math.max(ans, end - i);
            if (end > i) {  // 还有窗口，哪怕窗口内没有数字[i~end][4,4)
                sum -= arr[i];
            } else {    // i == end, 即i++，i > end,此时窗口概念维持不住了，所以end跟着i一起走
                end = i + 1;
            }
        }
        return ans;
    }

    // for test
    public static int maxLength2(int[] arr, int k) {
        int[] h = new int[arr.length + 1];
        int sum = 0;
        h[0] = sum;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            h[i + 1] = Math.max(sum, h[i]);
        }
        sum = 0;
        int res = 0;
        int pre = 0;
        int len = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            pre = getLessIndex(h, sum - k);
            len = pre == -1 ? 0 : i - pre + 1;
            res = Math.max(res, len);
        }
        return res;
    }

    // for test
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
            if (maxLength2(arr, k) != maxLength(arr, k)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }

}
