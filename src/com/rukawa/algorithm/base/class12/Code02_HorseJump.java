package com.rukawa.algorithm.base.class12;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/4/9 0009 8:28
 */
public class Code02_HorseJump {

    /**
     * 请同学自行搜索或者想象一个象棋的棋盘，
     * 然后把整个棋盘放入第一象限，棋盘的最左下角是(0,0)位置
     * 那么整个棋盘就是横坐标上9条线，纵坐标上10条线的区域
     * 那么给你三个参数x, y, k
     * 返回"马"从(0,0)位置出发，必须走k步
     * 最后落在(x,y)上的方法数有多少种？
     */

    public static int jump(int a, int b, int k) {
        return process(0, 0, k, a, b);
    }

    /**
     * @param x 当前位置的横坐标
     * @param y 当前位置的纵坐标
     * @param rest  还剩余的步数
     * @param a 最终来到位置的横坐标
     * @param b 最终来到位置的纵坐标
     * @return
     */
    public static int process(int x, int y, int rest, int a, int b) {
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }
        if (rest == 0) {
            return (x == a && y == b) ? 1 : 0;
        }
        int ways = 0;
        // 马下一步跳哪
        ways += process(x + 1, y + 2, rest - 1, a, b);
        ways += process(x + 2, y + 1, rest - 1, a, b);
        ways += process(x + 2, y - 1, rest - 1, a, b);
        ways += process(x + 1, y - 2, rest - 1, a, b);
        ways += process(x - 1, y - 2, rest - 1, a, b);
        ways += process(x - 2, y - 1, rest - 1, a, b);
        ways += process(x - 1, y + 2, rest - 1, a, b);
        ways += process(x - 2, y + 1, rest - 1, a, b);
        return ways;
    }

    public static int dp(int a, int b, int k) {
        int[][][] dp = new int[10][9][k + 1];
        dp[a][b][0] = 1;
        for (int rest = 1; rest <= k; rest++) {
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 9; y++) {
                    int ways = pick(dp, x + 1, y + 2, rest - 1);
                    ways += pick(dp, x + 2, y + 1, rest - 1);
                    ways += pick(dp, x + 2, y - 1, rest - 1);
                    ways += pick(dp, x + 1, y - 2, rest - 1);
                    ways += pick(dp, x - 2, y - 1, rest - 1);
                    ways += pick(dp, x - 1, y - 2, rest - 1);
                    ways += pick(dp, x - 2, y + 1, rest - 1);
                    ways += pick(dp, x - 1, y + 2, rest - 1);
                    dp[x][y][rest] = ways;
                }
            }
        }
        return dp[0][0][k];
    }

    private static int pick(int[][][] dp, int x, int y, int rest) {
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }
        return dp[x][y][rest];
    }

    public static void main(String[] args) {
        int x = 7;
        int y = 7;
        int step = 10;
        System.out.println(jump(x, y, step));
        System.out.println(dp(x, y, step));
    }
}
