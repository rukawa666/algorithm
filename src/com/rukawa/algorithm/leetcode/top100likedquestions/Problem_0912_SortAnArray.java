package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * create by hqh on 2022/2/24
 */
public class Problem_0912_SortAnArray {
    /**
     * 排序数组
     * 给你一个整数数组 nums，请你将该数组升序排列。
     *
     * 示例 1：
     * 输入：nums = [5,2,3,1]
     * 输出：[1,2,3,5]
     *
     * 示例 2：
     * 输入：nums = [5,1,1,2,0,0]
     * 输出：[0,0,1,1,2,5]
     *
     * 提示：
     * 1 <= nums.length <= 5 * 104
     * -5 * 104 <= nums[i] <= 5 * 104
     */
    public int[] sortArray(int[] nums) {
        if (nums == null || nums.length < 2) {
            return nums;
        }
        int N = nums.length;
        int mergeSize = 1;
        while (mergeSize < N) {
            int L = 0;
            while (L < N) {
                int M = L + mergeSize - 1;
                if (M >= N) {
                    break;
                }
                int R = Math.min(mergeSize + M, N - 1);
                merge(nums, L, M, R);
                L = R + 1;
            }

            if (mergeSize > N / 2) {
                break;
            }
            mergeSize <<= 1;
        }
        return nums;
    }

    public void merge(int[] nums, int L, int M, int R) {
        int[] help = new int[R - L + 1];
        int index = 0;
        int p1 = L;
        int p2 = M + 1;
        while (p1 <= M && p2 <= R) {
            help[index++] = nums[p1] <= nums[p2] ? nums[p1++] : nums[p2++];
        }

        while (p1 <= M) {
            help[index++] = nums[p1++];
        }

        while (p2 <= R) {
            help[index++] = nums[p2++];
        }

        for (index = 0; index <= (R - L); index++) {
            nums[L + index] = help[index];
        }
    }

    public int[] sortArray1(int[] nums) {
        if (nums == null || nums.length < 2) {
            return nums;
        }
        process(nums, 0, nums.length - 1);
        return nums;
    }

    public void process(int[] nums, int l, int r) {
        if (l >= r) {
            return;
        }
        swap(nums, l + (int) (Math.random() * (r - l + 1)), r);
        int[] netherlandsFlag = netherlandsFlag(nums, l, r);
        process(nums, l, netherlandsFlag[0] - 1);
        process(nums, netherlandsFlag[1] + 1, r);
    }

    public int[] netherlandsFlag(int[] nums, int l, int r) {
        if (l > r) {
            return new int[] {-1, -1};
        }
        if (l == r) {
            return new int[] {l, r};
        }
        int less = l - 1;
        int more = r;
        int index = l;
        while (index < more) {
            if (nums[index] == nums[r]) {
                index++;
            } else if (nums[index] < nums[r]) {
                swap(nums, index++, ++less);
            } else {
                swap(nums, index, --more);
            }
        }
        swap(nums, more, r);
        return new int[] {less + 1, more};


    }

    public static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
