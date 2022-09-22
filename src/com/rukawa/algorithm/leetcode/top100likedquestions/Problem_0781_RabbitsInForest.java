package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.Arrays;

/**
 * create by hqh on 2022/9/19
 */
public class Problem_0781_RabbitsInForest {
    /**
     * 森林中的兔子
     * 森林中有未知数量的兔子。提问其中若干只兔子 "还有多少只兔子与你（指被提问的兔子）颜色相同?" ，
     * 将答案收集到一个整数数组 answers 中，其中 answers[i] 是第 i 只兔子的回答。
     * 给你数组 answers ，返回森林中兔子的最少数量。
     * 示例 1：
     * 输入：answers = [1,1,2]
     * 输出：5
     * 解释：
     * 两只回答了 "1" 的兔子可能有相同的颜色，设为红色。
     * 之后回答了 "2" 的兔子不会是红色，否则他们的回答会相互矛盾。
     * 设回答了 "2" 的兔子为蓝色。
     * 此外，森林中还应有另外 2 只蓝色兔子的回答没有包含在数组中。
     * 因此森林中兔子的最少数量是 5 只：3 只回答的和 2 只没有回答的。
     *
     * 示例 2：
     * 输入：answers = [10,10,10]
     * 输出：11
     *
     * 提示：
     * 1 <= answers.length <= 1000
     * 0 <= answers[i] < 1000
     */
    public int numRabbits(int[] answers) {
        // a/b向上取整 = (a+(b-1))/b
        if (answers == null || answers.length == 0) {
            return 0;
        }
        Arrays.sort(answers);
        // 当前这个数是什么
        int x = answers[0];
        // 相同的数有几个
        int c = 1;

        int res = 0;
        for (int i = 1; i < answers.length; i++) {
            if (x != answers[i]) {
                // 当前数没有下一个，开始结算
                // ((7 + 3) / 4) * 4 = 8  如果约掉，等于10，不能约
                res += ((c + x) / (x + 1)) * (x + 1);
                x = answers[i];
                c = 1;
            } else {
                c++;
            }
        }
        // 计算最后一组
        return res + ((c + x) / (x + 1)) * (x + 1);
    }
}
