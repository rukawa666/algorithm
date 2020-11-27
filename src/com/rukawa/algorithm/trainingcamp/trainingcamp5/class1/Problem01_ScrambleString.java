package com.rukawa.algorithm.trainingcamp.trainingcamp5.class1;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-31 23:35
 * @Version：1.0
 */
public class Problem01_ScrambleString {
    /**
     * 扰乱字符串
     * 给定一个字符串 s1，我们可以把它递归地分割成两个非空子字符串，从而将其表示为二叉树。
     * 下图是字符串 s1 = "great" 的一种可能的表示形式。
     *     great
     *    /    \
     *   gr    eat
     *  / \    /  \
     * g   r  e   at
     *            / \
     *           a   t
     * 在扰乱这个字符串的过程中，我们可以挑选任何一个非叶节点，然后交换它的两个子节点。
     * 例如，如果我们挑选非叶节点 "gr" ，交换它的两个子节点，将会产生扰乱字符串 "rgeat" 。
     *     rgeat
     *    /    \
     *   rg    eat
     *  / \    /  \
     * r   g  e   at
     *            / \
     *           a   t
     * 我们将 "rgeat” 称作 "great" 的一个扰乱字符串。
     * 同样地，如果我们继续交换节点 "eat" 和 "at" 的子节点，将会产生另一个新的扰乱字符串 "rgtae" 。
     *     rgtae
     *    /    \
     *   rg    tae
     *  / \    /  \
     * r   g  ta  e
     *        / \
     *       t   a
     * 我们将 "rgtae” 称作 "great" 的一个扰乱字符串。
     * 给出两个长度相等的字符串 s1 和 s2，判断 s2 是否是 s1 的扰乱字符串。
     * 示例 1:
     * 输入: s1 = "great", s2 = "rgeat"
     * 输出: true
     * 示例 2:
     * 输入: s1 = "abcde", s2 = "caebd"
     * 输出: false
     */

    public static boolean isScramble1(String s1, String s2) {
        if ((s1 == null && s2 != null) || (s1 != null && s2 == null)) {
            return false;
        }
        if (s1 == null && s2 == null) {
            return true;
        }
        if (s1.equals(s2)) {
            return true;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        if (!sameTypeSameNumber(str1, str2)) {
            return false;
        }
        int N = s1.length();
        return process(str1, str2, 0, 0, N);
    }

    // 校验参数
    public static boolean sameTypeSameNumber(char[] str1, char[] str2) {
        if (str1.length != str2.length) {
            return false;
        }
        int[] map = new int[256];
        for (int i = 0; i < str1.length; i++) {
            map[str1[i]]++;
        }
        for (int i = 0; i < str2.length; i++) {
            if (--map[str2[i]] < 0) {
                return false;
            }
        }
        return true;
    }

    // 返回str1[从L1开始往右长度为size的子串]和str2[从L2开始往右长度为size的子串]是否互为旋变字符串
    // 在str1中的这一段和str2中的这一段一定是等长的，所以只用一个参数size
    public static boolean process(char[] str1, char[] str2, int L1, int L2, int size) {
        /**
         * 思路：
         * 0   0   0   0   0   0   0  -> str1
         * L1 | —> 第一刀           R1
         * 0   0   0   0   0   0   0  -> str2
         * L2                      R2
         *
         * 1、在L1到R1的范围上去尝试第一刀的可能性
         * 2、选择[L1,L1]与[L2,L2]互为旋变字符串，[L1+1,R1]与[L2+1,R2]也互为旋变字符串，整体就旋变
         * 3、选择[L1,L1]与[R2,R2]互为旋变字符串，[L1+1,R1]与[L2,R2-1]也互为旋变字符串，整体就旋变
         * 4、依次在范围上去枚举每一种可能性
         *
         * 举例：
         *  str1 -> a c b d e  |  a c b d e
         *          5 6 7 8 9  |  5 6 7 8 9
         *  str2 -> c d b e a  |  a c d b e
         *          3 4 5 6 7  |  3 4 5 6 7
         *  1、str1从[5,5]和str2从[3,3]互为旋变串 && str1从[6,9]和str2从[4,7]互为旋变串，整体就是旋变串
         *  2、str1从[5,5]和str2从[7,7]互为旋变串 && str1从[6,9]和str2从[3,6]互为旋变串，整体就是旋变串
         */
        // base case
        if (size == 1) {
            return str1[L1] == str2[L2];
        }
        // 枚举每一种情况，有一个计算出互为旋变字符串就返回true。都算不出来最后返回false
        for (int leftPart = 1; leftPart < size; leftPart++) {
            if (
                    // 如果1左对2左，并且1右对2右
                    (process(str1, str2, L1, L2, leftPart)
                            && process(str1, str2, L1 + leftPart, L2 + leftPart, size - leftPart))
                    // 如果一左对2右，并且1右对2左
                    || (process(str1, str2, L1, L2 + size - leftPart, leftPart)
                            && process(str1, str2, L1 + leftPart, L2, size - leftPart))) {
                return true;
            }
        }
        return false;
    }

    // 暴力递归改记忆化搜索
    public static boolean isScramble3(String s1, String s2) {
        if ((s1 == null && s2 != null) || (s1 != null && s2 == null)) {
            return false;
        }
        if (s1 == null && s2 == null) {
            return true;
        }
        if (s1.equals(s2)) {
            return true;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        if (!sameTypeSameNumber(str1, str2)) {
            return false;
        }
        int N = s1.length();
        // dp[i][j][k] = 0, 表示processDP(i, j, k)状态之前没有算过
        // dp[i][j][k] = -1, 表示processDP(i, j, k)状态之前算过，返回值是false
        // dp[i][j][k] = 1, 表示processDP(i, j, k)状态之前算过，返回值是true
        int[][][] dp = new int[N][N][N+1]; // size:0~N,所以长度为N+1
        return processDP(str1, str2, 0, 0, N, dp);
    }

    public static boolean processDP(char[] str1, char[] str2, int L1, int L2, int size, int[][][] dp) {
        // 如果之前算过，直接拿值
        if (dp[L1][L2][size] != 0) {
            return dp[L1][L2][size] == 1;
        }

        // 如果之前没算过，开始计算
        boolean ans = false;
        if (size == 1) {
            ans = str1[L1] == str2[L2];
        } else {
            for (int leftPart = 1; leftPart < size; leftPart++) {
                if (
                    // 如果1左对2左，并且1右对2右
                        (processDP(str1, str2, L1, L2, leftPart, dp)
                                && processDP(str1, str2, L1 + leftPart, L2 + leftPart, size - leftPart, dp))
                                // 如果一左对2右，并且1右对2左
                                || (processDP(str1, str2, L1, L2 + size - leftPart, leftPart, dp)
                                && processDP(str1, str2, L1 + leftPart, L2, size - leftPart, dp))) {
                    ans = true;
                    break;
                }
            }
        }
        dp[L1][L2][size] = ans ? 1 : -1;
        return ans;
    }

    public static void main(String[] args) {
        String test1 = "abcd";
        String test2 = "cdab";
        System.out.println(isScramble1(test1, test2));
//        System.out.println(isScramble2(test1, test2));
        System.out.println(isScramble3(test1, test2));

        test1 = "abcd";
        test2 = "cadb";
        System.out.println(isScramble1(test1, test2));
//        System.out.println(isScramble2(test1, test2));
        System.out.println(isScramble3(test1, test2));

        test1 = "bcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcde";
        test2 = "ebcdeebcdebebcdebcdebcdecdebcbcdcdebcddebcbdebbbcdcdebcdeebcdebcdeebcddeebccdebcdbcdebcd";
//         System.out.println(isScramble1(test1, test2));
//        System.out.println(isScramble2(test1, test2));
        //System.out.println(dp(test1, test2));
        System.out.println(isScramble3(test1, test2));
    }
}
