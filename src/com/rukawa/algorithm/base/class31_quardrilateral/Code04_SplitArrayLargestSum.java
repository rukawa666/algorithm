package com.rukawa.algorithm.base.class31_quardrilateral;

/**
 * create by hqh on 2023/1/11
 */
public class Code04_SplitArrayLargestSum {
    /**
     * 给定一个整型数组arr，数组中的每个值都为正数，表示完成一幅画作需要的时间，
     * 再给定一个整数num，表示画匠的数量，每个画匠只能画连在一起的画作。
     * 所有的画家并行工作，请返回完成所有的画作需要的最少时间。
     * 【举例】
     * arr=[3,1,4]，num=2。
     * 最好的分配方式为第一个画匠画3和1，所需时间为4。第二个画匠画4，所需时间为4。
     * 因为并行工作，所以最少时间为4。
     * 如果分配方式为第一个画匠画3，所需时间为3。第二个画匠画1和4，所需的时间为5。
     * 那么最少时间为5，显然没有第一种分配方式好。所以返回4。
     * arr=[1,1,1,4,3]，num=3。
     * 最好的分配方式为第一个画匠画前三个1，所需时间为3。第二个画匠画4，所需时间为4。 第三个画匠画3，
     * 所需时间为3。返回4。
     */

    // 求原数组arr[L...R]的累加和
    public static int sum(int[] sum, int L, int R) {
        return sum[R + 1] - sum[L];
    }

    // 不优化枚举的动态规划方法，O(N^2 * K)
    // 从左往右的尝试模型
    public static int splitArray1(int[] nums, int K) {
        // nums[0~i]幅画，必须让j个画家画完，最短的结束时间是多少
        int n = nums.length;
        int[] sum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }
        int[][] dp = new int[n][K + 1];
        // 一幅画的让多个画家画
        for (int j = 0; j <= K; j++) {
            dp[0][j] = nums[0];
        }
        // 让1个画家画多幅画
        for (int i = 1; i < n; i++) {
            dp[i][1] = sum(sum, 0, i);
        }
        for (int i = 1; i < n; i++) {
            for (int j = 2; j <= K; j++) {
                int ans = Integer.MAX_VALUE;
                /**
                 * dp[4][2] 现在求4幅画，由2个画家完成
                 * 0～4 由1个画家完成
                 * 0～3 由1个画家完成，第4幅画由第二个画家完成
                 * 0～2 由1个画家完成，第3，4幅画由第二个画家完成
                 * 0～1 由1个画家完成，第2，3，4幅画由第二个画家完成
                 */
                for (int leftEnd = 0; leftEnd <= i; leftEnd++) {
                    int leftCost = leftEnd == -1 ? 0 : dp[leftEnd][j - 1];
                    int rightCost = leftEnd == i ? 0 : sum(sum, leftEnd + 1, i);
                    int cur = Math.max(leftCost, rightCost);
                    if (cur < ans) {
                        ans = cur;
                    }
                }
                dp[i][j] = ans;
            }
        }
        return dp[n - 1][K];
    }

    // 用了枚举优化，O(N * K)
    public static int splitArray2(int[] nums, int m) {
        int N = nums.length;
        int[] sum = new int[N + 1];
        for (int i = 0; i < N; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }
        int[][] dp = new int[N][m + 1];
        // 最优的划分位置
        // 0~i位置有j个画家，最优划分位置是0~m位置有j-1个画家完成，剩下m+1~i位置由j号画家完成，最优划分位置为m
        int[][] best = new int[N][m + 1];
        // 一幅画被画家完成的时间
        for (int j = 1; j <= m; j++) {
            dp[0][j] = nums[0];
            // 最后一个画家完成0~0的画作，之前的画家没有画作，最优划分位置为-1
            best[0][j] = -1;
        }
        // i幅画被1个画家完成的时间
        for (int i = 1; i < N; i++) {
            dp[i][1] = sum(sum, 0, i);
            // 之前的第0个画家一幅画也没负责，所以负责到-1
            // 第1个画家负责从0开始到i的所有画作
            best[i][1] = -1;
        }

        // 从第2列开始，从左往右
        // 每一列，从下往上
        // 为什么这样的顺序？因为要去凑（左，下）优化位置对儿！
        for (int j = 2; j <= m; j++) {
            for (int i = N - 1; i >= 1; i--) {
                // 下限
                int down = best[i][j - 1];
                // 上限
                // 最后一行的格子，不优化上限
                int up = i == N - 1 ? N - 1 : best[i + 1][j];
                int ans = Integer.MAX_VALUE;
                // 最优划分点
                int choose = -1;
                for (int leftEnd = down; leftEnd <= up; leftEnd++) {
                    // 前面j-1个画家什么都没负责 代价为0  否则为dp[leftEnd][j - 1]
                    int leftCost = leftEnd == -1 ? 0 : dp[leftEnd][j - 1];
                    // 最后一个画家没负责 代价为0 否则代价为sum(sum, leftEnd + 1, i)
                    int rightCost = leftEnd == i ? 0 : sum(sum, leftEnd + 1, i);
                    int cur = Math.max(leftCost, rightCost);
                    // 也就是说，只有取得明显的好处才移动！
                    // 举个例子来说明，比如[2,6,4,4]，3个画匠时候，如下两种方案都是最优:
                    // (2,6) (4) 两个画匠负责 | (4) 最后一个画匠负责
                    // (2,6) (4,4)两个画匠负责 | 最后一个画匠什么也不负责
                    // 第一种方案划分为，[0~2] [3~3]
                    // 第二种方案划分为，[0~3] [无]
                    // 两种方案的答案都是8，但是划分点位置一定不要移动!
                    // 只有明显取得好处时(<)，划分点位置才移动!
                    // 也就是说后面的方案如果==前面的最优，不要移动！只有优于前面的最优，才移动
                    // 比如上面的两个方案，如果你移动到了方案二，你会得到:
                    // [2,6,4,4] 三个画匠时，最优为[0~3](前两个画家) [无](最后一个画家)，
                    // 最优划分点为3位置(best[3][3])
                    // 那么当4个画匠时，也就是求解dp[3][4]时
                    // 因为best[3][3] = 3，这个值提供了dp[3][4]的下限
                    // 而事实上dp[3][4]的最优划分为:
                    // [0~2]（三个画家处理） [3~3] (一个画家处理)，此时最优解为6
                    // 所以，你就得不到dp[3][4]的最优解了，因为划分点已经越过2了
                    if (cur < ans) {
                        ans = cur;
                        choose = leftEnd;
                    }
                }
                dp[i][j] = ans;
                best[i][j] = choose;
            }
        }
        return dp[N - 1][m];
    }

    // 最优解
    public static int splitArray3(int[] nums, int M) {
        long sum = 0l;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        long l = 0;
        long r = sum;
        long ans = 0;
        while (l <= r) {
            long mid = (l + r) / 2;
            long cur = getNeedParts(nums, mid);
            if (cur <= M) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return (int) ans;
    }

    // 定好一个目标，返回至少需要几个画家
    public static int getNeedParts(int[] arr, long aim) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > aim) {
                return Integer.MAX_VALUE;
            }
        }
        int parts = 1;
        int all = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (all + arr[i] > aim) {
                parts++;
                all = arr[i];
            } else {
                all += arr[i];
            }
        }
        return parts;
    }

    public static int[] randomArray(int len, int maxValue) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int N = 100;
        int maxValue = 100;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N) + 1;
            int M = (int) (Math.random() * N) + 1;
            int[] arr = randomArray(len, maxValue);
            int ans1 = splitArray1(arr, M);
            int ans2 = splitArray2(arr, M);
            int ans3 = splitArray3(arr, M);
            if (ans1 != ans2 && ans2 != ans3) {
                System.out.print("arr : ");
                printArray(arr);
                System.out.println("M : " + M);
                System.out.println("ans1 : " + ans1);
                System.out.println("ans2 : " + ans2);
                System.out.println("ans3 : " + ans3);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
