package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 11:21
 * @Version：1.0
 */
public class Problem_0163_MissingRanges {
    /**
     *  缺失的区间
     *  给定一个排序的整数数组 nums ，其中元素的范围在 闭区间 [lower, upper] 当中，返回不包含在数组中的缺失区间。
     *
     * Example 1:
     * Input: nums=[0,1,3,50,75], lower = 0, upper = 99
     * Output: ["2", "4->49", "51->74", "76->99"]
     * Explanation : The ranges are:
     * [2,2] -> "2"
     * [4,49] -> "4->49"
     * [51,74] -> "51->74"
     * [76,99] -> "76->99"
     */
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> res = new ArrayList<>();
        for (int num : nums) {
            // lower:0 upper:99
            // 当前数字7  缺少0～6
            if (num > lower) {
                res.add(miss(lower, num - 1));
            }

            if (num == upper) {
                return res;
            }
            lower = num + 1;
        }
        // 所有数字遍历完了
        if (lower <= upper) {
            res.add(miss(lower, upper));
        }
        return res;
    }

    // "4->49"
    public String miss(int lower, int upper) {
        String left = String.valueOf(lower);
        String right = "";
        if (upper > lower) {
            right = "->" + String.valueOf(upper);
        }
        return left + right;
    }
}
