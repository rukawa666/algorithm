package com.rukawa.algorithm.trainingcamp.trainingcamp3.class6;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-30 7:48
 * @Version：1.0
 */
public class Code03_JumpGame {
    /**
     * 给出一组正整数arr，你从第0个数向最后一个数，
     * 每个数的值表示你从这个位置可以向右跳跃的最大长度
     * 计算如何以最少的跳跃次数跳到最后一个位置
     */
    public static int jump(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 目前蹦了几步
        int step = 0;
        // step步内右边界到哪
        int cur = 0;
        // 如果多跳一步，右边界能到哪，当前位置能到达的最远距离
        int next = -1;
        for (int i = 0; i < arr.length; i++) {
            if (cur < i) {
                step++;
                cur = next;
            }
            next = Math.max(next, i + arr[i]);
        }
        return step;
    }

    public static void main(String[] args) {
        int[] arr = {3, 2, 3, 1, 1, 4};
        System.out.println(jump(arr));
    }
}
