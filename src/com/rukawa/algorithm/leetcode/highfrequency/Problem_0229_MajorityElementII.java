package com.rukawa.algorithm.leetcode.highfrequency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by hqh on 2022/9/26
 */
public class Problem_0229_MajorityElementII {
    /**
     * 多数元素 II
     * 给定一个大小为 n 的整数数组，找出其中所有出现超过 ⌊ n/3 ⌋ 次的元素。
     *
     * 示例 1：
     * 输入：nums = [3,2,3]
     * 输出：[3]
     *
     * 示例 2：
     * 输入：nums = [1]
     * 输出：[1]
     *
     * 示例 3：
     * 输入：nums = [1,2]
     * 输出：[1,2]
     *
     * 提示：
     * 1 <= nums.length <= 5 * 104
     * -109 <= nums[i] <= 109
     *
     * 进阶：尝试设计时间复杂度为 O(n)、空间复杂度为 O(1)的算法解决此问题。
     */
    public List<Integer> majorityElement(int[] nums) {
        // 候选表 最多k-1条记录，> n/k次的数字最多有k-1个，因为等于(18/3)=6 每个数出现6次，最多三个数 所以>(n/k)最多有k-1个
        // key 当前数
        // value 出现的次数
        HashMap<Integer, Integer> candidate = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (candidate.containsKey(nums[i])) {
                candidate.put(nums[i], candidate.get(nums[i]) + 1);
            } else { // num[i]不在候选
                // 候选区已经满了
                if (candidate.size() == 2) {
                    // 当前数不要，此时每一个候选都减少一个血量，血量为0的候选从候选区删除
                    allCandidateMinusOneHP(candidate);
                } else {
                    candidate.put(nums[i], 1);
                }
            }
        }

        List<Integer> res = new ArrayList<>();
        // 获取候选区域所有key的出现次数
        HashMap<Integer, Integer> reals = getReals(candidate, nums);
        for (Map.Entry<Integer, Integer> entry : reals.entrySet()) {
            if (entry.getValue() > nums.length / 3) {
                res.add(entry.getKey());
            }
        }
        return res;
    }

    // 候选区的血量全部-1，如果血量为0了从候选区删除
    public void allCandidateMinusOneHP(HashMap<Integer, Integer> candidate) {
        List<Integer> removeList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : candidate.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            if (value == 1) {
                removeList.add(key);
            }
            candidate.put(key, value - 1);
        }
        for (Integer key : removeList) {
            candidate.remove(key);
        }
    }

    public HashMap<Integer, Integer> getReals(HashMap<Integer, Integer> candidate, int[] nums) {
        HashMap<Integer, Integer> reals = new HashMap<>();
        for (int num : nums) {
            if (candidate.containsKey(num)) {
                if (reals.containsKey(num)) {
                    reals.put(num, reals.get(num) + 1);
                } else {
                    reals.put(num, 1);
                }
            }
        }
        return reals;
    }
}
