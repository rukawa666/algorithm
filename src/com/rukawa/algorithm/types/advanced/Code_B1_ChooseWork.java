package com.rukawa.algorithm.types.advanced;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * @className: Code_B1_ChooseWork
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/31 0031 19:30
 **/
public class Code_B1_ChooseWork {
    /**
     * 给定数组hard和money，长度都为N
     * hard[i]表示i号的难度，money[i]表示i号工作的收入
     * 给定数组ability，长度都为M，ability[i]表示j号人的能力
     * 每一号工作，都可以提供无数的岗位，难度和收入都一样
     * 但是人的能力必须>=这份工作的难度，才能上班
     * 返回一个长度为M的数组ans，ans[j]表示i号人能获得的最好收入
     */
    public static class Job {
        public int money;
        public int hard;

        public Job(int money, int hard) {
            this.money = money;
            this.hard = hard;
        }
    }

    public static class JobComparator implements Comparator<Job> {
        @Override
        public int compare(Job o1, Job o2) {
            return o1.hard != o2.hard ? (o1.hard - o2.hard) : (o2.money - o1.money);
        }
    }

    public static int[] getMoneys(Job[] jobs, int[] ability) {
        Arrays.sort(jobs, new JobComparator());
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(jobs[0].hard, jobs[0].money);
        Job pre = jobs[0];
        for (int i = 1; i < jobs.length; i++) {
            if (jobs[i].hard != pre.hard && jobs[i].money > pre.money) {
                pre = jobs[i];
                map.put(jobs[i].hard, jobs[i].money);
            }
        }
        int[] ans = new int[ability.length];
        for (int i = 0; i < ability.length; i++) {
            Integer key = map.floorKey(ability[i]);
            ans[i] = key == null ? 0 : map.get(key);
        }
        return ans;
    }
}
