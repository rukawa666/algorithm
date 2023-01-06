package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-19 11:21
 * @Version：1.0
 */
public class Problem_0169_MajorityElement {
    /**
     * 多数元素
     * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。
     * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
     *
     * 示例 1:
     * 输入: [3,2,3]
     * 输出: 3
     *
     * 示例 2:
     * 输入: [2,2,1,1,1,2,2]
     * 输出: 2
     *
     * 提示：
     * n == nums.length
     * 1 <= n <= 5 * 104
     * -109 <= nums[i] <= 109
     *
     * 进阶：尝试设计时间复杂度为 O(n)、空间复杂度为 O(1) 的算法解决此问题。
     */

    // 超级水王问题
    public int majorityElement(int[] nums) {
        /**
         * Boyer-Moore 投票算法
         * 思路：
         *   依此删除两个不同的数，剩下的数就是水王数
         *   接着把剩下的数再遍历一遍看下出现的次数
         *
         * 定义：候选  血量  血量为0时无候选
         * 规则：1. 如果无候选，当前数立为候选，血量+1
         *      2. 如果有候选，当前数和候选数不一样，血量--
         *         如果有候选，当前数和候选数一样，血量++
         */
        // 候选
        int candidate = 0;
        // 血量
        int HP = 0;
        for (int num : nums) {
            // 无候选
            if (HP == 0) {
                candidate = num;
                HP = 1;
            } else if (num == candidate) { // 有候选，且候选数和当前数一样
                HP++;
            } else { // 有候选，且候选数和当前数不一样
                HP--;
            }
        }
        // 当前无候选
        if (HP == 0) {
            return 0;
        }
        // 候选的次数
        HP = 0;
        for (int num : nums) {
            if (num == candidate) {
                HP++;
            }
        }
        if (HP > nums.length / 2) {
            return candidate;
        } else {
            return 0;
        }
    }

    // 给一个数组nums，长度为N，给定一个正数K，K>1  所有出现N/K次的数有哪些
    public void printMajor(int[] nums, int k) {
        if (k < 2) {
            return;
        }
        // 候选表 最多k-1条记录，> n/k次的数字最多有k-1个，因为等于(18/3)=6 每个数出现6次，最多三个数 所以>(n/k)最多有k-1个
        // key 当前数
        // value 出现的次数
        HashMap<Integer, Integer> candidate = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (candidate.containsKey(nums[i])) {
                candidate.put(nums[i], candidate.get(nums[i]) + 1);
            } else { // num[i]不在候选
                // 候选区已经满了
                if (candidate.size() == k - 1) {
                    // 当前数不要，此时每一个候选都减少一个血量，血量为0的候选从候选区删除
                    allCandidateMinusOneHP(candidate);
                } else {
                    candidate.put(nums[i], 1);
                }
            }
        }

        // 所有可能的候选在candidate，然后遍历一遍原始数组收集次数，判断是否超过n/4，超过则打印
        HashMap<Integer, Integer> reals = getReals(candidate, nums);

        for (Map.Entry<Integer, Integer> entry : reals.entrySet()) {
            if (entry.getValue() > nums.length / k) {
                System.out.println(entry.getKey());
            }
        }
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

    // 收集候选区中数在nums中出现的次数
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
