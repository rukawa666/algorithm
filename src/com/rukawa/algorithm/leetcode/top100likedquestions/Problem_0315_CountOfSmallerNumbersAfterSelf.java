package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-11 9:02
 * @Version：1.0
 */
public class Problem_0315_CountOfSmallerNumbersAfterSelf {
    /**
     * 计算右侧小于当前元素的个数
     * 给定一个整数数组 nums，按要求返回一个新数组 counts。数组 counts 有该性质：
     * counts[i] 的值是nums[i] 右侧小于 nums[i] 的元素的数量。
     *
     * 示例：
     * 输入：nums = [5,2,6,1]
     * 输出：[2,1,1,0]
     * 解释：
     * 5 的右侧有 2 个更小的元素 (2 和 1)
     * 2 的右侧仅有 1 个更小的元素 (1)
     * 6 的右侧有 1 个更小的元素 (1)
     * 1 的右侧有 0 个更小的元素
     *  
     *
     * 提示：
     * 0 <= nums.length <= 10^5
     * -10^4 <= nums[i] <= 10^4
     */

    public static List<Integer> countSmaller(int[] nums) {
        if (nums == null) {
            return null;
        }
        int N = nums.length;
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            res.add(0);
        }
        // 索引数组，作用：归并回去的时候，方便知道是哪个下标的元素
        int[] indexes = new int[N];
        for (int i = 0; i < N; i++) {
            indexes[i] = i;
        }
        int mergeSize = 1;
        while (mergeSize < N) {
            int L = 0;
            while (L < N) {
                int M = L + mergeSize - 1;
                if (M >= N) {
                    break;
                }
                int R = Math.min(mergeSize + M, N - 1);
                merge(nums, L, M, R, res);
                L = R + 1;
            }
            if (mergeSize > N / 2) {
                break;
            }
            mergeSize <<= 1;
        }
        return res;
    }

    public static void merge(int[] nums, int L, int M, int R, List<Integer> res) {
        int[] help = new int[R - L + 1];
        int index = R - L;
        int p1 = M;
        int p2 = R;
        while (p1 >= L && p2 > M) {
            res.set(index, nums[p1] > nums[p2] ? (R - p2) : 0);
            help[index--] = nums[p1] > nums[p2] ? nums[p1--] : nums[p2--];
        }

        while (p1 >= L) {
            help[index--] = nums[p1--];
        }

        while (p2 > M) {
            help[index--] = nums[p2--];
        }

        for (index = 0; index <= R - L; index++) {
            nums[L + index] = help[index];
        }
    }

    public static void main(String[] args) {
        int[] nums = {5, 2, 6, 1};
        List<Integer> list = countSmaller(nums);
        System.out.println(list);
    }
}
