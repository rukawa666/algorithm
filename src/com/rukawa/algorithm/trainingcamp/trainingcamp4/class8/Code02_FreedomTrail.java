package com.rukawa.algorithm.trainingcamp.trainingcamp4.class8;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-22 7:51
 * @Version：1.0
 */
public class Code02_FreedomTrail {
    /**
     * 自由之路
     * 视频游戏“辐射4”中，任务“通向自由”要求玩家到达名为“Freedom Trail Ring”的金属表盘，并使用表盘拼写特定关键词才能开门。
     * 给定一个字符串 ring，表示刻在外环上的编码；给定另一个字符串 key，表示需要拼写的关键词。您需要算出能够拼写关键词中所有字符的最少步数。
     * 最初，ring 的第一个字符与12:00方向对齐。您需要顺时针或逆时针旋转 ring 以使 key 的一个字符在 12:00 方向对齐，然后按下中心按钮，
     * 以此逐个拼写完 key 中的所有字符。
     *
     * 旋转 ring 拼出 key 字符 key[i] 的阶段中：
     * 您可以将 ring 顺时针或逆时针旋转一个位置，计为1步。旋转的最终目的是将字符串 ring 的一个字符与 12:00 方向对齐，并且这个字符必须等于字符 key[i] 。
     * 如果字符 key[i] 已经对齐到12:00方向，您需要按下中心按钮进行拼写，这也将算作 1 步。按完之后，您可以开始拼写 key 的下一个字符（下一阶段）, 直至完成所有拼写。
     *
     * 示例：
     * 输入: ring = "godding", key = "gd"
     * 输出: 4
     * 解释:
     *  对于 key 的第一个字符 'g'，已经在正确的位置, 我们只需要1步来拼写这个字符。
     *  对于 key 的第二个字符 'd'，我们需要逆时针旋转 ring "godding" 2步使它变成 "ddinggo"。
     *  当然, 我们还需要1步进行拼写。
     *  因此最终的输出是 4。
     *  
     * 提示：
     * ring 和 key 的字符串长度取值范围均为 1 至 100；
     * 两个字符串中都只有小写字符，并且均可能存在重复字符；
     * 字符串 key 一定可以由字符串 ring 旋转拼出。
     */

    // 深度优先遍历，会超时
    public static int findRotateSteps1(String ring, String key) {
        char[] str = ring.toCharArray();
        int rSize = str.length;
        HashMap<Character, ArrayList<Integer>> map = new HashMap<>();
        for (int i = 0; i < str.length; i++) {
            if (!map.containsKey(str[i])) {
                map.put(str[i], new ArrayList<>());
            }
            map.get(str[i]).add(i);
        }
        return minStep1(0, 0, key.toCharArray(), map, rSize);
    }

    // 顶针函数
    // i1位置是顶针，现在变为i2是顶针，请问拨号最优走几步
    public static int dial(int i1, int i2, int size) {
        return Math.min(Math.abs(i1 - i2), Math.min(i1, i2) + size - Math.max(i1, i2));
    }

    /**
     * @param preStrIndex  目前被对齐的位置是什么位置
     * @param keyIndex  请搞定key[keyIndex...]
     * @param key   目标串(固定参数)
     * @param map   任何一个字符，什么位置有有它(固定参数)
     * @param rSize 电话表盘上一共有几个参数(固定参数)
     * @return  返回最低代价
     */
    public static int minStep1(int preStrIndex, int keyIndex, char[] key,
                               HashMap<Character, ArrayList<Integer>> map, int rSize) {
        // base case
        if (keyIndex == key.length) {
            return 0;
        }

        int ans = Integer.MAX_VALUE;
        for (int curStrIndex : map.get(key[keyIndex])) {
            int step = dial(preStrIndex, curStrIndex, rSize) + 1
                    + minStep1(curStrIndex, keyIndex + 1, key, map, rSize);
            ans = Math.min(ans, step);
        }
        return ans;
    }


    // 根据暴力递归，用记忆化搜索方法求解
    public static int findRotateSteps2(String ring, String key) {
        char[] str = ring.toCharArray();
        int rSize = str.length;
        HashMap<Character, ArrayList<Integer>> map = new HashMap<>();
        for (int i = 0; i < str.length; i++) {
            if (!map.containsKey(str[i])) {
                map.put(str[i], new ArrayList<>());
            }
            map.get(str[i]).add(i);
        }
        int[][] dp = new int[rSize][key.length() + 1];
        for (int i = 0; i < rSize; i++) {
            for (int j = 0; j <= key.length(); j++) {
                dp[i][j] = -1;
            }
        }
        return minStep2(0, 0, key.toCharArray(), map, rSize, dp);
    }

    public static int minStep2(int preStrIndex, int keyIndex, char[] key,
                               HashMap<Character, ArrayList<Integer>> map, int rSize, int[][] dp) {
        if (dp[preStrIndex][keyIndex] != -1) {
            return dp[preStrIndex][keyIndex];
        }

        if (keyIndex == key.length) {
            dp[preStrIndex][keyIndex] = 0;
            return dp[preStrIndex][keyIndex];
        }
        int ans = Integer.MAX_VALUE;
        for (int curStrIndex : map.get(key[keyIndex])) {
            int step = dial(preStrIndex, curStrIndex, rSize) + 1
                    + minStep2(curStrIndex, keyIndex + 1, key, map, rSize, dp);
            ans = Math.min(ans, step);
        }
        dp[preStrIndex][keyIndex] = ans;
        return dp[preStrIndex][keyIndex];
    }


}
