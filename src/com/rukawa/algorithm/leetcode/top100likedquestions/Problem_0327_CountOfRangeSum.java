package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.HashSet;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-11 9:17
 * @Version：1.0
 */
public class Problem_0327_CountOfRangeSum {
    /**
     * 区间和的个数
     * 给定一个整数数组nums，返回区间和在[lower, upper]之间的个数，包含lower和upper。
     * 区间和S(i, j)表示在nums中，位置从i到j的元素之和，包含i和j(i ≤ j)。
     *
     * 说明:
     * 最直观的算法复杂度是O(n2) ，请在此基础上优化你的算法。
     *
     * 示例:
     * 输入: nums = [-2,5,-1], lower = -2, upper = 2,
     * 输出: 3
     * 解释: 3个区间分别是: [0,0], [2,2], [0,2]，它们表示的和分别为: -2, -1, 2。
     */
    public int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        long[] sum = new long[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < sum.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }
        return process(sum, 0, sum.length - 1, lower, upper);
    }

    /**
     * arr[L...R] 不传进来，只传进来sum(前缀和数组)
     * 在原始的arr[L...R]中，有多少个子数组累加和在[lower, upper]上
     * @param sum
     * @param L
     * @param R
     * @param lower
     * @param upper
     * @return
     */
    public static int process(long[] sum, int L, int R, int lower, int upper) {
        if (L == R) {
            return sum[L] >= lower && sum[L] <= upper ? 1 : 0;
        }
        int M = L + ((R - L) >>>1);
        return process(sum, L, M, lower, upper) + process(sum, M + 1, R, lower, upper) + merge(sum, L, M, R, lower, upper);
    }


    /**
     * 假设0~i整体累加和是x，范围是[lower,upper], 求必须从i位置结尾的子数组，目标有多少个在[lower,upper]范围上
     * 等同于，求i之前的所有前缀和中有多少个前缀和在[x-upper, x-lower]上
     */
    public static int merge(long[] arr, int L, int M, int R, int lower, int upper) {
        int ans = 0;
        int windowL = L;
        int windowR = L;
        // 窗口是不可回退的，时间复杂度O(N)
        for (int i = M + 1; i <= R; i++) {
            long min = arr[i] - upper;
            long max = arr[i] - lower;
            while (windowR <= M && arr[windowR] <= max) {
                windowR++;
            }

            while (windowL <= M && arr[windowL] < min) {
                windowL++;
            }
            ans += Math.max(0, windowR - windowL);
        }

        long[] help = new long[R - L + 1];
        int index =0;
        int p1 = L;
        int p2 = M + 1;
        while (p1 <= M && p2 <= R) {
            help[index++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= M) {
            help[index++] = arr[p1++];
        }
        while (p2 <= R) {
            help[index++] = arr[p2++];
        }
        for (index = 0; index < help.length; index++) {
            arr[L + index] = help[index];
        }
        return ans;
    }

    /**
     * 思路：子数组以每个位置结尾的情况下，达标的数量。每个位置结尾的子数组都求出正确的数量，然后都累加起来，就是答案
     *
     * 总结：子数组满足以S标准的有几个或怎么样最长等等的指标，此类问题，可以用以i结尾的情况下，S的答案，以i+1结尾的情况下，
     * S的答案，往往都是这么考虑。
     *
     * 该题：以i位置结尾的情况下，累加和落在在[10,60]范围上的有几个，加入一些设定
     * 假设0~i的整体累加和是100，问题转化成，从0开始有多少个前缀和落在[40,90]范围上
     *
     * 子数组以i位置结尾的情况下，累加和落在[a,b]的数量有多少个？
     * 假设从0~i的整体累加和是s,等同于前面有多少个前缀和落在[s-b, s-a]
     *
     *
     * 举例说明：
     *  [3, -2, 4, 3, 6, -1]     [1,5]
     *  假设有一个接收前缀和的结构
     *  有多少个子数组必须以0位置的3结尾且累加和落在[1,5]上，确认只包含自己达标不达标，或者之前有多少个前缀和落在[-2, 2]上
     *
     *  一个数都没有的情况下，前缀和是0，这个结构中提前放一个前缀和为0的值，在0位置去查有多少个前缀和在[-2, 2]上,此时有一个，答案是1，此时把自己的前缀和3扔到结构中(0,3)
     *  来到1位置，此时有一个变量维持总共的前缀sum=3+(-2)=1,在这个结构中有多少数在[-4,0]上，只有一个，答案是1，此时把前缀和1，加入到结构中(0,3,1)
     *  来到2位置，sum=5，在这个结构中有多少个数在[0,4]上，此时有3个数，答案是3，把前缀和5，加入到结构中(0,3,1,5)
     *  来到3位置，sum=8，在这个结构中多少少个数在[3,7]上，此时有2个数，答案是2，把前缀和8，加入到结构中(0,3,1,5,8)
     *  来到4位置，sum=14，在这个结构中有多少个数在[9,13]上，此时没有数，答案是0，把前缀和14，放入到结构中(0,3,1,5,8,14)
     *  来到5位置，sum=13,在这个结构中有多少个数在[8,12]上，此时只有一个数，答案是1，前缀和13加入到结构中
     */

    public static int countRangeSum2(int[] nums, int lower, int upper) {
        // 有序表黑盒结构
        // 1、可以加入数字(前缀和)；2、可以接受重复的数字；3、支持一种查询，<num有几个数
        SizeBalancedTreeSet treeSet = new SizeBalancedTreeSet();
        // 一个数都没有的时候，就已经有一个前缀和为0的记录
        treeSet.add(0);
        // 从0一直累加到i的和
        long sum = 0;
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            // [sum-upper, sum-lower]这个范围上
            // [10, 20]上的累加和有几个
            // 先查<10的有几个，再查<21的有几个，sum(<21)-sum(<10)的就是答案
            long a = treeSet.lessKeySize(sum - lower + 1);
            long b = treeSet.lessKeySize(sum - upper);
            ans += a - b;
            treeSet.add(sum);
        }
        return ans;

    }

    public static class SBTNode {
        // 参与排序的key
        public long key;
        // 左孩子
        public SBTNode l;
        // 右孩子
        public SBTNode r;
        // SBTree的平衡因子, 不同key的size
        // 一定不重复
        public long size;
        // 附加的数据项 总的size
        // 可重复
        public long all;

        public SBTNode(long k) {
            key = k;
            size = 1;
            all = 1;
        }
    }

    public static class SizeBalancedTreeSet {
        private SBTNode root;
        private HashSet<Long> set = new HashSet<>();

        private SBTNode rightRotate(SBTNode cur) {
            // 当前节点本身的size
            long same = cur.all - (cur.l != null ? cur.l.all : 0) - (cur.r != null ? cur.r.all : 0);
            SBTNode left = cur.l;
            // 左孩子的右孩子作为当前节点的左孩子
            cur.l = left.r;
            // 当前节点作为左孩子的右孩子
            left.r = cur;
            left.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0);

            left.all = cur.all;
            cur.all = (cur.l != null ? cur.l.all : 0) + (cur.r != null ? cur.r.all : 0) + same;
            return left;
        }

        private SBTNode leftRotate(SBTNode cur) {
            // 当前节点本身的size
            long same = cur.all - (cur.l != null ? cur.l.all : 0) - (cur.r != null ? cur.r.all : 0);
            SBTNode right = cur.r;
            // 右孩子的左孩子作为当前节点的右孩子
            cur.r = right.l;
            // 右孩子的左孩子为当前节点
            right.l = cur;

            right.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0);

            right.all = cur.all;
            cur.all = (cur.l != null ? cur.l.all : 0) + (cur.r != null ? cur.r.all : 0) + same;

            return right;
        }

        private SBTNode maintain(SBTNode cur) {
            if (cur == null) {
                return null;
            }
            long leftSize = cur.l != null ? cur.l.size : 0;
            long leftLeftSize = cur.l != null && cur.l.l != null ? cur.l.l.size : 0;
            long leftRightSize = cur.l != null && cur.l.r != null ? cur.l.r.size : 0;
            long rightSize = cur.r != null ? cur.r.size : 0;
            long rightRightSize = cur.r != null && cur.r.r != null ? cur.r.r.size : 0;
            long rightLeftSize = cur.r != null && cur.r.l != null ? cur.r.l.size : 0;
            if (leftLeftSize > rightSize) {    // LL型：父亲节点的左孩子size > 叔叔节点的size
                cur = rightRotate(cur);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (leftRightSize > rightSize) {     // LR型：父亲节点的右孩子size > 叔叔节点的size
                cur.l = leftRotate(cur.l);
                cur = rightRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (rightRightSize > leftSize) {     // RR型，父亲节点的右孩子size > 叔叔节点的size
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur = maintain(cur);
            } else if (rightLeftSize > leftSize) {
                cur.r = rightRotate(cur.r);
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }
            return cur;
        }

        private SBTNode add(SBTNode cur, long key, boolean contains) {
            if (cur == null) {
                return new SBTNode(key);
            } else {
                cur.all++;
                if (key == cur.key) {
                    return cur;
                } else {
                    // 没有这个key，size才会++
                    if (!contains) {
                        cur.size++;
                    }
                    if (key < cur.key) {
                        cur.l = add(cur.l, key, contains);
                    } else {
                        cur.r = add(cur.r, key, contains);
                    }
                    return maintain(cur);
                }
            }
        }

        public void add(long sum) {
            boolean contains = set.contains(sum);
            root = add(root, sum, contains);
            set.add(sum);
        }

        public long lessKeySize(long key) {
            SBTNode cur = root;
            long ans = 0;
            while (cur != null) {
                if (key == cur.key) {
                    /**
                     *     5
                     *       \
                     *       10
                     *       /
                     *     8
                     *  此时要找到 <8的部分
                     *  滑动到8之后，要加上8左边的部分
                     */
                    return ans + (cur.l != null ? cur.l.all : 0);
                } else if (key < cur.key) {
                    // 如果往左滑动，不获得
                    // 此时左边这一块要加上
                    cur = cur.l;
                } else {
                    // 如果向右滑动，左边全是
                    ans += cur.all - (cur.r != null ? cur.r.all : 0);
                    cur = cur.r;
                }
            }
            return ans;
        }
    }
}
