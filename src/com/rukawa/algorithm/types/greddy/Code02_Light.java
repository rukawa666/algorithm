package com.rukawa.algorithm.types.greddy;

import java.util.HashSet;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-23 8:19
 * @Version：1.0
 */
public class Code02_Light {

    /**
     * 给定一个字符串str，只有"X"和"."两种字符组成
     * X：表示墙，不能放灯，也不需要点亮
     * .：表示居民点，可以放灯，需要点亮
     * 如果灯放在i位置，可以让i-1,i和i+1的三个位置被点亮
     * 返回如果点亮str中所需要点亮的位置，至少需要几盏灯
     * @param road
     * @return
     */

    public static int minLight01(String road) {
        if(road == null || road.length() == 0) {
            return 0;
        }
        return process(road.toCharArray(), 0, new HashSet<>());
    }

    // chs[index...]的位置，自由选择放灯还是不放灯
    // chs[0...index-1]位置，已经做完决定，那些放了灯的位置在lights
    // 要求选出能照亮所有.的方案,并且在这些有效的方案中，返回最少需要几盏灯
    public static int process(char[] chs, int index, HashSet<Integer> lights) {
        if (index == chs.length) {  // 结束的时候，没有位置可以放灯
            for (int i = 0; i < chs.length; i++) {
                if (chs[i] != 'X') { // 当前位置是.的情况，在i-1位置被照亮，或者i位置被照亮，或者i+1位置被照亮
                    // 当前位置无效，返回MAX_VALUE
                    if (!lights.contains(i - 1) && !lights.contains(i) && !lights.contains(i + 1)) {
                        return Integer.MAX_VALUE;
                    }
                }
            }
            // 如果上面条件满足，则返回放置几盏灯
            return lights.size();
        } else {
            // i位置 X或者. 后续位置进行选择
            int no = process(chs, index + 1, lights);
            // i位置是.有可能被设置
            int yes = Integer.MAX_VALUE;
            if (chs[index] == '.') {
                lights.add(index);
                yes = process(chs, index + 1, lights);
                lights.remove(index);
            }
            return Math.min(yes, no);
        }
    }

    public static int minLight02(String road) {
        char[] chs = road.toCharArray();
        int index = 0;
        int lights = 0;
        while (index < chs.length) {
            if (chs[index] == 'X') {
                index++;
            } else {
                lights++;
                if (index + 1 == chs.length) {
                    break;
                } else {
                    if (chs[index + 1] == 'X') {
                        index = index + 2;
                    } else {
                        index = index + 3;
                    }
                }
            }
        }
        return lights;
    }

    // for test
    public static String randomString(int len) {
        char[] res = new char[(int) (Math.random() * len) + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = Math.random() < 0.5 ? 'X' : '.';
        }
        return String.valueOf(res);
    }

    public static void main(String[] args) {
        int len = 20;
        int testTime = 100000;
        for (int i = 0; i < testTime; i++) {
            String test = randomString(len);
            int ans1 = minLight01(test);
            int ans2 = minLight02(test);
            if (ans1 != ans2) {
                System.out.println("oops!");
            }
        }
        System.out.println("finish!");
    }


}
