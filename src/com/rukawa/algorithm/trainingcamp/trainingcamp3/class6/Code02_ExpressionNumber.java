package com.rukawa.algorithm.trainingcamp.trainingcamp3.class6;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-30 7:48
 * @Version：1.0
 */
public class Code02_ExpressionNumber {
    /**
     * 给定一个只有0(假)、1(真)、&(逻辑与)、|(逻辑或)和^(异或)五种字符组成的字符串express，
     * 在给定一个布尔值desired。返回express能有多少种组合方式，可以达到desired的结果。
     *
     * eg：
     *  express="1^0|0|1", desired=false
     *  只有1^((0|0)|1)和1^(0|(0|1))的组合可以得到false，返回2。express="1",desired=false
     *  无组合则可以得到false，返回0
     */

    /**
     * 暴力递归求解
     * @param express
     * @param desired
     * @return
     */
    public static int num1(String express, boolean desired) {
        if (express == null || express.length() == 0) {
            return 0;
        }
        char[] str = express.toCharArray();
        if (!isValid(str)) {
            return 0;
        }
        return f(str, desired, 0, str.length - 1);
    }

    public static boolean isValid(char[] str) {
        // 长度为偶数不合法
        if ((str.length & 1) == 0) {
            return false;
        }
        // 偶数位置必须是'0'和'1'
        for (int i = 0; i < str.length; i = i + 2) {
            if ((str[i] != '1') && (str[i] != '0')) {
                return false;
            }
        }
        // 奇数位置必须是'&'、'|'、'^'
        for (int i = 1; i < str.length; i = i + 2) {
            if ((str[i] != '&') && (str[i] != '|') && (str[i] != '^')) {
                return false;
            }
        }
        return true;
    }

    // str[L...R]返回期待为desired的方法数
    // 潜台词：L R必须是偶数位置
    public static int f(char[] str, boolean desired, int L, int R) {
        if (L == R) {
            if (str[L] == '1') {
                return desired ? 1 : 0;
            } else {   // '0'
                return desired ? 0 : 1;
            }
        }
        int res = 0;
        if (desired) {  // 期待为true
            // i位置尝试L...R范围上的每一个逻辑符号，都是最后结合的
            for (int i = L + 1; i < R; i = i + 2) {
                // str[i]一定是压中逻辑符号
                switch (str[i]) {
                    case '&':
                        res += f(str, true, L, i - 1) * f(str, true, i + 1, R);
                        break;
                    case '|':
                        res += f(str, true, L, i - 1) * f(str, true, i + 1, R);
                        res += f(str, true, L, i - 1) * f(str, false, i + 1, R);
                        res += f(str, false, L, i - 1) * f(str, true, i + 1, R);
                        break;
                    case '^':
                        res += f(str, true, L, i - 1) * f(str, false, i + 1, R);
                        res += f(str, false, L, i - 1) * f(str, true, i + 1, R);
                        break;
                    default:
                        break;
                }
            }
        } else {
            for (int i = L + 1; i < R; i = i + 2) {
                switch (str[i]) {
                    case '&':
                        res += f(str, true, L, i - 1) * f(str, false, i + 1, R);
                        res += f(str, false, L, i - 1) * f(str, true, i + 1, R);
                        res += f(str, false, L, i - 1) * f(str, false, i + 1, R);
                        break;
                    case '|':
                        res += f(str, false, L, i - 1) * f(str, false, i + 1, R);
                        break;
                    case '^':
                        res += f(str, true, L, i - 1) * f(str, true, i + 1, R);
                        res += f(str, false, L, i - 1) * f(str, false, i + 1, R);
                        break;
                    default:
                        break;
                }
            }
        }
        return res;
    }


    /**
     * 暴力递归改动态规划
     * eg:
     *  1、不需要填写的位置，L不可能大于R，所以L>R的区域不用填写，左下角不用填写
     *                   奇数行和奇数列都是运算符，所以不用填写
     *  2、需要填写的位置有[0,0],[0,2],[0,4],[0,6]
     *                 [2,2],[2,4],[2,6]
     *                 [4,4],[4,6]
     *                 [6,6]
     *     true table
     *          R
     *    0 1 2 3 4 5 6
     *  0   x   x   x
     *  1 x x x x x x x
     *  2 x x   x   x
     *L 3 x x x x x x x
     *  4 x x x x   x
     *  5 x x x x x x x
     *  6 x x x x x x
     *
     *      false table
     *           R
     *     0 1 2 3 4 5 6
     *   0   x   x   x
     *   1 x x x x x x x
     *   2 x x   x   x
     * L 3 x x x x x x x
     *   4 x x x x   x
     *   5 x x x x x x x
     *   6 x x x x x x
     *
     */
    public static int dpLive(String express, boolean desired) {
        char[] str = express.toCharArray();
        int N = str.length;
        int[][] tDP = new int[N][N];
        int[][] fDP = new int[N][N];
        // 1、根据base case，str[i] == '1'，则tDP[i][i]=1；str[i] == '0' -> fDP[i][i]=1
        for (int i = 0; i < N; i = i + 2) {
            tDP[i][i] = str[i] == '1' ? 1 : 0;
            fDP[i][i] = str[i] == '0' ? 1 : 0;
        }
        // 2、此时所有的对角线位置已经填好，所以N-1只有对角线的位置，已经填好，此时需要跳过符号行，去填写N-3行
        // 3、如果去填写N-3的位置，对角线的位置已经填好，所以只用填写row+2列的位置即可
        for (int row = N - 3; row >= 0; row -= 2) {
            for (int col = row + 2; col < N; col += 2) {
                for (int i = row + 1; i < col; i += 2) {
                    switch (str[i]) {
                        case '&':
                            tDP[row][col] += tDP[row][i - 1] * tDP[i + 1][col];
                            break;
                        case '|':
                            tDP[row][col] += tDP[row][i - 1] * tDP[i + 1][col];
                            tDP[row][col] += tDP[row][i - 1] * fDP[i + 1][col];
                            tDP[row][col] += fDP[row][i - 1] * tDP[i + 1][col];
                            break;
                        case '^':
                            tDP[row][col] += tDP[row][i - 1] * fDP[i + 1][col];
                            tDP[row][col] += fDP[row][i - 1] * tDP[i + 1][col];
                            break;
                        default:
                            break;
                    }

                    switch (str[i]) {
                        case '&':
                            fDP[row][col] += fDP[row][i - 1] * fDP[i + 1][col];
                            fDP[row][col] += tDP[row][i - 1] * fDP[i + 1][col];
                            fDP[row][col] += fDP[row][i - 1] * tDP[i + 1][col];
                            break;
                        case '|':
                            fDP[row][col] += fDP[row][i - 1] * fDP[i + 1][col];
                            break;
                        case '^':
                            fDP[row][col] += tDP[row][i - 1] * tDP[i + 1][col];
                            fDP[row][col] += fDP[row][i - 1] * fDP[i + 1][col];
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        // 因为要返回的L...R，所以是0~N-1位置的结果
        return desired ? tDP[0][N - 1] : fDP[0][N - 1];
    }

    public static void main(String[] args) {
//        String express = "1^0&0|1&1^0&0^1|0|1&1";
//        boolean desired = true;

        String express = "1^0|0|1";
        boolean desired = false;
        System.out.println(num1(express, desired));
//        System.out.println(num2(express, desired));
//        System.out.println(dp(express, desired));
        System.out.println(dpLive(express, desired));
    }
}
