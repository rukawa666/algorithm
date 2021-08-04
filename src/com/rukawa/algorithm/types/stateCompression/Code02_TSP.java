package com.rukawa.algorithm.types.stateCompression;

import java.util.ArrayList;
import java.util.List;

/**
 * @name: Code02_TSP
 * @description: 描述类的用途
 * @date: 2021/8/4 13:25
 * @auther: hanqinghai
 */
public class Code02_TSP {
    /**
     * TSP问题
     * 有N个城市，任何两个城市之间的都有距离，任何一座城市到自己的距离都为0.
     * 所有点到点的距离都存在一个N*N的二维数组matrix里，也就是整张图由邻接
     * 矩阵表示。现要求一旅行商从k城市出发必须经过每一个城市且只在一个城市逗
     * 留一次，最后回到出发的k城，返回总距离最短的路的距离。
     * 参数给定一个matrix，给定k。
     */


    // 此方法往深层调用，会出现重复解
    public static int t1(int[][] matrix) {
        int N = matrix.length; // 0...N-1个座城市
        // set
        // set.get(i)!=null 表示i这座城市在集合中，否则不在集合中
        List<Integer> set = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            // 用1占位表示城市不空
            set.add(1);
        }
        return func1(matrix, set, 0);
    }

    // 任何城市之间的距离可以在matrix里面获取
    // set中表示着哪些城市的集合
    // start一定在set中
    // 返回，从start出发，把set所有的城市过一遍，最终回到start这座城市，最小距离是多少
    public static int func1(int[][] matrix, List<Integer> set, int start) {
        // 还有多少座城市在set中
        int cityNum = 0;
        for (int i = 0; i < set.size(); i++) {
            if (set.get(i) != null) {
                cityNum++;
            }
        }

        // 如果还剩下一座城市，必然是start
        if (cityNum == 1) {
            return matrix[start][0]; // 返回start~0的距离
        }

        // 不止一座城市
        // set这座城市搞为null
        set.set(start, null);

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < set.size(); i++) {
            if (set.get(i) != null && i != start) {
                // 从start跳到i这座城
                // 剩下的集合中，从i这座城出发都联通，再回到start位置
                int cur = matrix[start][i] + func1(matrix, set, i);
                min = Math.min(min, cur);
            }
        }
        // 恢复现场
        set.set(start, 1);
        return min;
    }

    /**
     * t1方法有重复计算，t2优化重复解, 为什么？
     *  func1(int[][] matrix, List<Integer> set, int start) set{1,3,4} start=1
     *  func1(int[][] matrix, List<Integer> set, int start) set{2,3,4} start=2
     *  接下来继续调用func方法会出现set{3,4} start=3的重复解
     *
     * 优化方法：
     *   func1(int[][] matrix, List<Integer> set, int start) 的set可变参数是一个集合的概念
     *   集合概念违反了猜动态规划的原则，不要让单一的可变参数突破到整型以上
     *   所以，TSP是动态规划的一个异类，叫做状态压缩的动态规划 -> 无论怎么尝试，这个集合都没有办法化成更简的模式
     *
     *  set集合怎么表示，当前位置是空，表示不存在，当前位置是1，表示存在 set=[null, 1, null, 1, 1]
     *  此时可以用位信息表示集合，位信息整合成一个整数
     */
    public static int t2(int[][] matrix) {
        int N = matrix.length;  // 0...N-1座城市
        // 假设此时有7个城市，所有用7个1表示
        // 所以有N座城，用哪个数表示所有城都在  (1<<N) - 1
        // 假设N=7  1<<N=10000000  (1<<N)-1=1111111
        // 表示N个城市都在
        int allCity = (1 << N) - 1;
        return func2(matrix, allCity, 0);

    }

    // 任何城市之间的距离可以在matrix里面获取
    // allStatus中表示有哪些城市
    // 返回，从start出发，把所有的城市过一遍，最终回到start这座城市，最小距离是多少
    public static int func2(int[][] matrix, int cityStatus, int start) {
        // cityStatus或者最右的1  cityStatus & (~cityStatus + 1)
        // 如果cityStatus=最右侧的1，表示上面只有一个1 cityStatus == cityStatus & (~cityStatus+1)
        if (cityStatus == (cityStatus & (~cityStatus + 1))) {    // 如果还剩下一座城市，必然是start
            return matrix[start][0];
        }

        // 不止一座城市
        // 怎么去掉start这座城市，假设start=5
        // 6 5 4 3 2 1 0
        // 0 1 1 0 1 1 0
        // 先把1<<5 获得 100000，然后取反(~100000)=011111，再与cityStatus&(011111)=0010110，把5位置的1变为0
        cityStatus &= (~(1 << start));


        int min = Integer.MAX_VALUE;
        for (int move = 0; move < matrix.length; move++) {
            // 1<<0 检查0这个城市在不在
            // 1<<1 检查1这个城市在不在
            // 1<<move 检查move这个城市在不在
            // 检查是否只剩下move这座城市
            if ((cityStatus & (1 << move)) != 0) {
                // 从start跳到move这座城
                // 剩下的城市中，从move这座城出发都联通，再回到start位置
                int cur = matrix[start][move] + func2(matrix, cityStatus, move);
                min = Math.min(min, cur);
            }
        }
        // 恢复现场
        // 0010110|0100000 = 0110110
        cityStatus |= (1 << start);
        return min;
    }

    // t2方法改成记忆化搜索
    /**
     * t1方法有重复计算，t3优化重复解, 为什么？
     *  func1(int[][] matrix, List<Integer> set, int start) set{1,3,4} start=1
     *  func1(int[][] matrix, List<Integer> set, int start) set{2,3,4} start=2
     *  接下来继续调用func方法会出现set{3,4} start=3的重复解
     */
    public static int t3(int[][] matrix) {
        int N = matrix.length;  // 0...N-1座城市
        // 假设此时有7个城市，所有用7个1表示
        // 所以有N座城，用哪个数表示所有城都在  (1<<N) - 1
        // 假设N=7  1<<N=10000000  (1<<N)-1=1111111
        // 表示N个城市都在
        int allCity = (1 << N) - 1;
        // N=7 allCity=1111111  0状态到1111111的状态，所以一共((1<<7)-1) + 1
        int[][] dp = new int[1<<N][N];
        for (int i = 0; i < (1 << N); i++) {
            for (int j = 0; j < N; j++) {
                dp[i][j] = -1; // 表示没算过
            }
        }

        return func3(matrix, allCity, 0, dp);
    }

    // 任何城市之间的距离可以在matrix里面获取
    // allStatus中表示有哪些城市
    // 返回，从start出发，把所有的城市过一遍，最终回到start这座城市，最小距离是多少
    public static int func3(int[][] matrix, int cityStatus, int start, int[][] dp) {

        // 之前算过
        if (dp[cityStatus][start] != -1) {
            return dp[cityStatus][start];
        }

        // cityStatus或者最右的1  cityStatus & (~cityStatus + 1)
        // 如果cityStatus=最右侧的1，表示上面只有一个1 cityStatus == cityStatus & (~cityStatus+1)
        if (cityStatus == (cityStatus & (~cityStatus + 1))) {    // 如果还剩下一座城市，必然是start
            dp[cityStatus][start] = matrix[start][0];
        } else {
            // 不止一座城市
            // 怎么去掉start这座城市，假设start=5
            // 6 5 4 3 2 1 0
            // 0 1 1 0 1 1 0
            // 先把1<<5 获得 100000，然后取反(~100000)=011111，再与cityStatus&(011111)=0010110，把5位置的1变为0
            cityStatus &= (~(1 << start));

            int min = Integer.MAX_VALUE;
            for (int move = 0; move < matrix.length; move++) {
                // 1<<0 检查0这个城市在不在
                // 1<<1 检查1这个城市在不在
                // 1<<move 检查move这个城市在不在
                if ((cityStatus & (1 << move)) != 0) {
                    // 从start跳到move这座城
                    // 剩下的城市中，从move这座城出发都联通，再回到start位置
                    int cur = matrix[start][move] + func3(matrix, cityStatus, move, dp);
                    min = Math.min(min, cur);
                }
            }
            // 恢复现场
            // 0010110|0100000 = 0110110
            cityStatus |= (1 << start);
            dp[cityStatus][start] = min;
        }
        return dp[cityStatus][start];
    }

    // 彻底的动态规划版本
    // 整理出所有的dp表，怎么整理状态依赖 -> 从简单状态填写到复杂状态
    // 多少行 2^N  多少列 N 具体求每个格子有枚举行为N  所以时间复杂度O(2^N * N^2)
    public static int t4(int[][] matrix) {
        int N = matrix.length;  // 0...N-1座城市
        int statusNums = 1 << N;
        // N=7 allCity=1111111  0状态到1111111的状态，所以一共((1<<7)-1) + 1
        int[][] dp = new int[statusNums][N];

        for (int status = 0; status < statusNums; status++) {
            for (int start = 0; start < N; start++) {

                if ((status & (1 << start)) != 0) { // 状态中不包含start这座城，直接返回
                    // 如果start中只有一座城
                    if (status == (status & (~status + 1))) {   // status==最右侧的1，只有一座城
                        dp[status][start] = matrix[start][0];
                    } else {    // 不止一座城
                        int min = Integer.MAX_VALUE;
                        // 怎么去掉start这座城市，假设start=5
                        // 6 5 4 3 2 1 0
                        // 0 1 1 0 1 1 0
                        // 先把1<<5 获得 100000，然后取反(~100000)=011111，再与cityStatus&(011111)=0010110，把5位置的1变为0
                        int cityStatus = status & (~(1 << start));
                        // 从start跳到i
                        for (int i = 0; i < N; i++) {
                            if ((cityStatus & (1 << i)) != 0) {
                                int cur = matrix[start][i] + dp[cityStatus][i];
                                min = Math.min(min, cur);
                            }
                        }
                        dp[status][start] = min;
                    }
                }
            }
        }

        return dp[statusNums - 1][0];
    }

    public static int tsp2(int[][] matrix, int origin) {
        if (matrix == null || matrix.length < 2 || origin < 0 || origin >= matrix.length) {
            return 0;
        }
        int N = matrix.length - 1; // 除去origin之后是n-1个点
        int S = 1 << N; // 状态数量
        int[][] dp = new int[S][N];
        int icity = 0;
        int kcity = 0;
        for (int i = 0; i < N; i++) {
            icity = i < origin ? i : i + 1;
            // 00000000 i
            dp[0][i] = matrix[icity][origin];
        }
        for (int status = 1; status < S; status++) {
            // 尝试每一种状态 status = 0 0 1 0 0 0 0 0 0
            // 下标 8 7 6 5 4 3 2 1 0
            for (int i = 0; i < N; i++) {
                // i 枚举的出发城市
                dp[status][i] = Integer.MAX_VALUE;
                if ((1 << i & status) != 0) {
                    // 如果i这座城是可以枚举的，i = 6 ， i对应的原始城的编号，icity
                    icity = i < origin ? i : i + 1;
                    for (int k = 0; k < N; k++) { // i 这一步连到的点，k
                        if ((1 << k & status) != 0) { // i 这一步可以连到k
                            kcity = k < origin ? k : k + 1; // k对应的原始城的编号，kcity
                            dp[status][i] = Math.min(dp[status][i], dp[status ^ (1 << i)][k] + matrix[icity][kcity]);
                        }
                    }
                }
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            icity = i < origin ? i : i + 1;
            ans = Math.min(ans, dp[S - 1][i] + matrix[origin][icity]);
        }
        return ans;
    }


    // for test
    public static int[][] generateGraph(int maxSize, int maxValue) {
        int len = (int) (Math.random() * maxSize) + 1;
        int[][] matrix = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                matrix[i][j] = (int) (Math.random() * maxValue) + 1;
            }
        }
        for (int i = 0; i < len; i++) {
            matrix[i][i] = 0;
        }
        return matrix;
    }

    public static void main(String[] args) {
        int len = 9;
        int value = 100;
        System.out.println("test begin");
        for (int i = 0; i < 20000; i++) {
            int[][] matrix = generateGraph(len, value);
            int origin = (int) (Math.random() * matrix.length);
//            int ans1 = t1(matrix);
            int ans1 = t3(matrix);
            int ans2 = t4(matrix);
            int ans3 = tsp2(matrix, origin);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("fuck");
            }
//			System.out.println(ans1);
//			System.out.println(ans2);
//			System.out.println(ans3);
//			System.out.println("=========");
        }
        System.out.println("test finished!");
    }
}
