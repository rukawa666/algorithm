package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:32
 * @Version：1.0
 */
public class Problem_0033_SearchInRotatedSortedArray {
    /**
     * 搜索旋转排序数组
     * 整数数组 nums 按升序排列，数组中的值 互不相同 。
     * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，
     * 使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。
     * 例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
     * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
     * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
     *
     * 示例 1：
     * 输入：nums = [4,5,6,7,0,1,2], target = 0
     * 输出：4
     *
     * 示例 2：
     * 输入：nums = [4,5,6,7,0,1,2], target = 3
     * 输出：-1
     * 示例 3：
     * 输入：nums = [1], target = 0
     * 输出：-1
     *
     * 提示：
     * 1 <= nums.length <= 5000
     * -104 <= nums[i] <= 104
     * nums 中的每个值都 独一无二
     * 题目数据保证 nums 在预先未知的某个下标上进行了旋转
     * -104 <= target <= 104
     */
    public int search(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        int m = 0;
        while (l <= r) {
            m = l + ((r - l) >> 1);
            if (nums[m] == target) {
                return m;
            }
            // l == m == r 三个位置的数相等，无法二分，然后l++去找和m不相等的数
            if (nums[l] == nums[m] && nums[m] == nums[r]) {
                while (l != m && nums[l] == nums[m]) {
                    l++;
                }
                // 情况1：l == m l...m一路都相等
                // 情况2：从l...m找到了一个不相等的位置
                if (l == m) {
                    l = m + 1;
                    continue;
                }
            }

            // nums[l]!=nums[r]
            if (nums[l] != nums[m]) {
                /**
                 * 假设nums[l]=1 nums[m]=3,说明此时l，m之间无断点，说明此时断点(断点1)一定在右侧 1222233331111
                 * 如果是断点，则必然是nums[l]>nums[m],比如4567012，0是断点
                 */
                if (nums[m] > nums[l]) { // 断点在右边，l...m的位置一定是有序的
                    if (target >= nums[l] && target < nums[m]) { // target=3 nums[l]=1 nums[m]=5 左侧二分
                        r = m - 1;
                    } else {  // target=1或者9 nums[l]=3 nums[m]=7  右侧二分
                        l = m + 1;
                    }
                } else {  // nums[l] > nums[m]  说明l...m存在断点  34551222222 nums[l]=3 nums[m]=2 此时的中间必然存在断点1
                    if (target > nums[m] && target <= nums[r]) {
                        l = m + 1;
                    } else {
                        r = m - 1;
                    }
                }
            } else { // nums[l] == nums[m] && nums[m] != nums[r]
                if (nums[m] < nums[r]) {
                    if (target > nums[m] && target <= nums[r]) {
                        l = m + 1;
                    } else {
                        r = m - 1;
                    }
                } else {
                    if (target >= nums[l] && target < nums[m]) {
                        r = m - 1;
                    } else {
                        l = m + 1;
                    }
                }
            }
        }
        return -1;
    }
}
