package com.rukawa.algorithm.base.class32_statusCompression;

/**
 * create by hqh on 2023/1/12
 */
public class Code01_CanIWin {
    /**
     * 我能赢吗
     * 在 "100 game" 这个游戏中，两名玩家轮流选择从 1 到 10 的任意整数，累计整数和，先使得累计整数和 达到或超过  100 的玩家，即为胜者。
     * 如果我们将游戏规则改为 “玩家 不能 重复使用整数” 呢？
     *
     * 例如，两个玩家可以轮流从公共整数池中抽取从 1 到 15 的整数（不放回），直到累计整数和 >= 100。
     * 给定两个整数 maxChoosableInteger （整数池中可选择的最大数）和 desiredTotal（累计和），若先出手的玩家是否能稳赢则返回 true ，
     * 否则返回 false 。假设两位玩家游戏时都表现 最佳 。
     *
     * 示例 1：
     * 输入：maxChoosableInteger = 10, desiredTotal = 11
     * 输出：false
     * 解释：
     * 无论第一个玩家选择哪个整数，他都会失败。
     * 第一个玩家可以选择从 1 到 10 的整数。
     * 如果第一个玩家选择 1，那么第二个玩家只能选择从 2 到 10 的整数。
     * 第二个玩家可以通过选择整数 10（那么累积和为 11 >= desiredTotal），从而取得胜利.
     * 同样地，第一个玩家选择任意其他整数，第二个玩家都会赢。
     *
     * 示例 2:
     * 输入：maxChoosableInteger = 10, desiredTotal = 0
     * 输出：true
     *
     * 示例 3:
     * 输入：maxChoosableInteger = 10, desiredTotal = 1
     * 输出：true
     *
     * 提示:
     * 1 <= maxChoosableInteger <= 20
     * 0 <= desiredTotal <= 300
     */
    // 1~maxChoosableInteger的数字
    // desiredTotal 一开始的剩余
    // 返回先手会不会赢
    // O(N!)
    public static boolean canIWin0(int maxChooseAbleInteger, int desiredTotal) {
        // 题目规定，刚开始遇到desiredTotal，返回true
        if (desiredTotal == 0) {
            return true;
        }
        // 1~maxChooseAbleInteger的累加和 < rest，怎么拿先手都输
        // 先算>> 再*
        if ((maxChooseAbleInteger * (maxChooseAbleInteger + 1) >> 1) < desiredTotal) {
            return false;
        }

        int[] arr = new int[maxChooseAbleInteger];
        for (int i = 0; i < maxChooseAbleInteger; i++) {
            arr[i] = i + 1;
        }
        // arr[i] != -1 表示这个数字还没被拿走
        // arr[i] == -1 表示这个数字已经被拿走
        // 集合arr 1～choose
        return process0(arr, desiredTotal);
    }

    // 当前轮到先手拿
    // 先手只能选择在arr中还存在的数
    // 还剩rest这么多值
    // 返回先手会不会赢
    public static boolean process0(int[] arr, int rest) {
        // 先手去选择发现rest没有剩余的值，则先手输
        if (rest <= 0) {
            return false;
        }
        // 先手去尝试所有的情况
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != -1) {
                int cur = arr[i];
                arr[i] = -1;
                // 子过程的先手即后手 会不会赢
                boolean next = process0(arr, rest - cur);
                arr[i] = cur;
                // 如果后手输，则先手赢
                if (!next) {
                    return true;
                }
            }
        }
        return false;
    }

    // maxChooseAbleInteger不超过20，可以用整形结构替代一维数组
    public boolean canIWin1(int maxChooseAbleInteger, int desiredTotal) {
        // 题目规定，刚开始遇到desiredTotal，返回true
        if (desiredTotal == 0) {
            return true;
        }
        // 1~maxChooseAbleInteger的累加和 < rest，怎么拿先手都输
        // 先算>> 再*
        if ((maxChooseAbleInteger * (maxChooseAbleInteger + 1) >> 1) < desiredTotal) {
            return false;
        }
        return process1(maxChooseAbleInteger, 0, desiredTotal);
    }

    // 当前先手选择
    // 先手可以拿1~choose中的数字
    // status i位如果为0，代表没拿，当前可以拿
    //        i位如果为1，代表已拿，当前不能拿
    // 还剩rest这么多值
    // 返回先手赢还是输
    public static boolean process1(int choose, int status, int rest) {
        // 先手去选择发现rest没有剩余的值，则先手输
        if (rest <= 0) {
            return false;
        }
        // 先手去尝试所有的情况
        for (int i = 1; i <= choose; i++) {
            if (((1 << i) & status) == 0) { // i 这个数字，是此时先手的决定
                // 子过程的先手即后手 会不会赢  子过程输，则先手赢
                if (!process1(choose, (status | (1 << i)), rest - i)) {
                    return true;
                }
            }
        }
        return false;
    }

    // 动态规划
    // O(2^N * N)
    public boolean canIWin2(int maxChooseAbleInteger, int desiredTotal) {
        // 题目规定，刚开始遇到desiredTotal，返回true
        if (desiredTotal == 0) {
            return true;
        }
        // 1~maxChooseAbleInteger的累加和 < rest，怎么拿先手都输
        // 先算>> 再*
        if ((maxChooseAbleInteger * (maxChooseAbleInteger + 1) >> 1) < desiredTotal) {
            return false;
        }
        // 记忆化搜索优化
        // 1，2，3，4，5       rest=10
        // 只要发现2，4没有  rest一定剩余4
        // 只要发现3，5没有  rest一定剩余2
        // 拿的数字的状态可以决定rest，两者不独立。所以一维动态表即可
        // 0～11111可以存下该表的数据
        // 0位置弃而不用，需要准备0～111111
        // 具体需要多少长度：0～1000000 下标就是0～111111
        int[] dp = new int[1 << (maxChooseAbleInteger + 1)];
        // dp[status] = 1 已经算过，true
        // dp[status] = -1  已经算过 false
        // dp[status] = 0 还么算过
        return process2(maxChooseAbleInteger, 0, desiredTotal, dp);
    }

    // 为什么有status和rest两个可变参数，却只用status来代表状态（也就是状态）
    // 因为选了一批数字之后，得到的和一定是一样的，所以rest由status决定，所以rest不参与记忆化搜索
    public static boolean process2(int choose, int status, int rest, int[] dp) {
        if (dp[status] != 0) {
            return dp[status] == 1 ? true : false;
        }
        boolean ans = false;
        if (rest > 0) {
            for (int i = 1; i <= choose; i++) {
                if (((1 << i) & status) == 0) { // 还没算过
                    if (!process2(choose, (status | (1 << i)), rest - i, dp)) {
                        ans = true;
                        break;
                    }
                }
            }
        }
        dp[status] = ans ? 1 : -1;
        return ans;
    }
}
