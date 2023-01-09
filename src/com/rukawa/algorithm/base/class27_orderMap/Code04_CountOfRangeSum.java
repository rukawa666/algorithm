package com.rukawa.algorithm.base.class27_orderMap;

import java.util.HashSet;

/**
 * create by hqh on 2023/1/9
 */
public class Code04_CountOfRangeSum {
    /**
     * LeetCode 327题目
     * 给定一个数组arr，和两个整数a和b(a<=b)
     * 求arr中有多少个子数组，累加和在[a,b]这个范围上
     * 返回达标的子数组数量
     */


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

    public static int countRangeSum(int[] nums, int lower, int upper) {
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
        private long k;
        private SBTNode l;
        private SBTNode r;
        // 不同key的size
        private long size;
        // 总的size
        private long all;

        public SBTNode(long key) {
            k = key;
            size = 1;
            all = 1;
        }
    }

    public static class SizeBalancedTreeSet {
        private SBTNode root;
        private HashSet<Long> set = new HashSet<>();

        private SBTNode rightRotate(SBTNode cur) {
            long same = cur.all - (cur.l != null ? cur.l.all : 0) - (cur.r != null ? cur.r.all : 0);
            SBTNode leftNode = cur.l;
            cur.l = leftNode.r;
            leftNode.r = cur;
            leftNode.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;

            leftNode.all = cur.all;
            cur.all = (cur.l != null ? cur.l.all : 0) + (cur.r != null ? cur.r.all : 0) + same;
            return leftNode;
        }

        private SBTNode leftRotate(SBTNode cur) {
            long same = cur.all - (cur.l != null ? cur.l.all : 0) - (cur.r != null ? cur.r.all : 0);
            SBTNode rightNode = cur.r;
            cur.r = rightNode.l;
            rightNode.l = cur;
            rightNode.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;

            rightNode.all = cur.all;
            cur.all = (cur.l != null ? cur.l.all : 0) + (cur.r != null ? cur.r.all : 0) + same;
            return rightNode;
        }

        // 平衡性调整
        private SBTNode maintain(SBTNode cur) {
            if (cur == null) {
                return null;
            }
            long leftSize = cur.l != null ? cur.l.size : 0;
            long leftLeftSize = cur.l != null && cur.l.l != null ? cur.l.l.size : 0;
            long leftRightSize = cur.l != null && cur.l.r != null ? cur.l.r.size : 0;
            long rightSize = cur.r != null ? cur.r.size : 0;
            long rightLeftSize = cur.r != null && cur.r.l != null ? cur.r.l.size : 0;
            long rightRightSize = cur.r != null && cur.r.r != null ? cur.r.r.size : 0;
            if (leftLeftSize > rightSize) { // LL型违规：左孩子的左孩子的节点个数大于右孩子的节点个数
                cur = rightRotate(cur);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (leftRightSize > rightSize) { // LR型违规：左孩子的右孩子的节点个数大于右孩子的节点个数
                cur.l = leftRotate(cur.l);
                cur = rightRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (rightRightSize > leftSize) { // RR型违规：右孩子的右孩子的节点个数大于左孩子的节点个数
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur = maintain(cur);
            } else if (rightLeftSize > leftSize) { // RL型违规：右孩子的左孩子的节点个数大于左孩子的节点个数
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
                if (key == cur.k) {
                    return cur;
                } else { // 还在左滑或者右滑
                    // 没有这个key，size才会++
                    if (!contains) {
                        cur.size++;
                    }
                    if (key < cur.k) {
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
            SBTNode cur = this.root;
            long ans = 0;
            while (cur != null) {
                if (key == cur.k) {
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
                } else if (key < cur.k) {
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

    public static int countRangeSum1(int[] nums, int lower, int upper) {
        int n = nums.length;
        long[] sums = new long[n + 1];
        for (int i = 0; i < n; ++i)
            sums[i + 1] = sums[i] + nums[i];
        return countWhileMergeSort(sums, 0, n + 1, lower, upper);
    }

    private static int countWhileMergeSort(long[] sums, int start, int end, int lower, int upper) {
        if (end - start <= 1)
            return 0;
        int mid = (start + end) / 2;
        int count = countWhileMergeSort(sums, start, mid, lower, upper)
                + countWhileMergeSort(sums, mid, end, lower, upper);
        int j = mid, k = mid, t = mid;
        long[] cache = new long[end - start];
        for (int i = start, r = 0; i < mid; ++i, ++r) {
            while (k < end && sums[k] - sums[i] < lower)
                k++;
            while (j < end && sums[j] - sums[i] <= upper)
                j++;
            while (t < end && sums[t] < sums[i])
                cache[r++] = sums[t++];
            cache[r] = sums[i];
            count += j - k;
        }
        System.arraycopy(cache, 0, sums, start, t - start);
        return count;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static int[] generateArray(int len, int varible) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * varible);
        }
        return arr;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int len = 200;
        int varible = 50;
        for (int i = 0; i < 10000; i++) {
            int[] test = generateArray(len, varible);
            int lower = (int) (Math.random() * varible) - (int) (Math.random() * varible);
            int upper = lower + (int) (Math.random() * varible);
            int ans1 = countRangeSum1(test, lower, upper);
            int ans2 = countRangeSum(test, lower, upper);
            if (ans1 != ans2) {
                printArray(test);
                System.out.println(lower);
                System.out.println(upper);
                System.out.println(ans1);
                System.out.println(ans2);
            }
        }
        System.out.println("test end...");
    }
}
