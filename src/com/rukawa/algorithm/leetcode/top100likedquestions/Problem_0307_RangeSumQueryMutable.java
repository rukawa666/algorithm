package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-20 8:29
 * @Version：1.0
 */
public class Problem_0307_RangeSumQueryMutable  {
    /**
     * 区域和检索 - 数组可修改
     * 给定一个整数数组  nums，求出数组从索引 i 到 j  (i ≤ j) 范围内元素的总和，包含 i,  j 两点。
     * update(i, val) 函数可以通过将下标为 i 的数值更新为 val，从而对数列进行修改。
     *
     * 示例:
     * Given nums = [1, 3, 5]
     * sumRange(0, 2) -> 9
     * update(1, 2)
     * sumRange(0, 2) -> 8
     *
     * 说明:
     * 数组仅可以在 update 函数下进行修改。
     * 你可以假设 update 函数与 sumRange 函数的调用次数是均匀分布的。
     */
    class NumArray {
        private int MAXN;
        // 原序列的信息从0开始，但是在arr里是从1开始的
        private int[] arr;
        // 模拟线段树维护区间和
        private int[] sum;
        // 累加懒惰标记
        // 累加和的每一个区间的懒信息
        private int[] lazy;

        // change、update用于更新
        // 更新的值
        private int[] change;
        // 更新慵懒标记
        private boolean[] update;

        public NumArray(int[] nums) {
            if (nums == null || nums.length == 0) {
                return;
            }
            this.MAXN = nums.length + 1;
            arr = new int[MAXN];
            for (int i = 1; i < MAXN; i++) {
                arr[i] = nums[i - 1];
            }
            // MAXN<<2 = MAXN*4
            sum = new int[MAXN << 2];   // 某一个范围的累加和概念
            lazy = new int[MAXN << 2];  // 某一个范围没有往下传递的累加任务
            change = new int[MAXN << 2];  // 某一个范围没有更新操作的任务
            update = new boolean[MAXN << 2]; // 某一个更新范围，更新成了什么
            build(1, MAXN - 1, 1);
        }

        // 向上汇总，=左孩子的sum+右孩子的sum
        private void pushUp(int rt) {
            // sum[rt*2] + sum[rt*2+1]
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
        }

        // 之前的，所有懒增加，和懒更新，从父范围，发送给左右两个子范围
        // 分发的策略是什么
        // ln表示左子树元素节点个数，rn表示右子树元素节点个数
        private void pushDown(int rt, int ln, int rn) {
            // 发布任务，先发布更新任务，在发布add方法
            if (update[rt]) {
                update[rt << 1] = true;
                update[rt << 1 | 1] = true;

                change[rt << 1] = change[rt];
                change[rt << 1 | 1] = change[rt];

                lazy[rt << 1] = 0;
                lazy[rt << 1 | 1] = 0;

                sum[rt << 1] = change[rt] * ln;
                sum[rt << 1 | 1] = change[rt] * rn;

                update[rt] = false;
            }
            // 为什么更新完之后还要lazy更新？
            // 如果当前更新任务，更新任务全包含实际范围，只会懒更新任务，不会实际下发任务
            // 接着中间有一些更新、增加操作操作
            // 此时既有更新操作，又攒了一些增加操作，下发的时候，增加操作的数不能被覆盖，因为时间在更新后面
            // 所有先发更新任务，再去下发lazy任务去调整
            // 什么情况下，update为true，lazy又有值？一定是最晚发生的更新到目前为止还没有发生过，但是中间攒着一些add操作

            // 父亲的懒信息不为0，下发任务给两个孩子
            if (lazy[rt] != 0) {
                // 左孩子处理父节点下发的任务
                lazy[rt << 1] += lazy[rt];
                sum[rt << 1] += lazy[rt] * ln; // ln 左孩子有几个数
                // 右孩子处理父节点下发的任务
                lazy[rt << 1 | 1] += lazy[rt];
                sum[rt << 1 | 1] += lazy[rt] * rn; // rn 右孩子有几个数
                // 父节点lazy清空
                lazy[rt] = 0;
            }
        }

        /**
         * 在初始阶段，先把sum数组填好
         * 在arr[l...r]范围上，去build，1~N
         * rt：这个范围在sum中的下标
         * @param l
         * @param r
         * @param rt
         */
        public void build(int l, int r, int rt) {
            // base case
            if (l == r) {
                sum[rt] = arr[l];
                return;
            }
            int mid = (l + r) >> 1;
            build(l, mid, rt << 1); // rt << 1 = rt * 2
            build(mid + 1, r, rt << 1 | 1);  // rt << 1 | 1 = rt * 2 + 1
            pushUp(rt);
        }

        /**
         * L...R -> 任务的范围，所有的值累加C
         * l...r -> 表达的实际范围
         * rt 去哪找l，r范围上的信息
         * @param L
         * @param R
         * @param C
         * @param l
         * @param r
         * @param rt
         */
        public void add(int L, int R, int C, int l, int r, int rt) {

            // 任务的范围彻底覆盖了，当前表达的范围
            // 任务范围覆盖实际范围，当前实际范围懒住，不往下下发
            if (L <= l && r <= R) {
                sum[rt] += C * (r - l + 1);
                lazy[rt] += C;
                return;
            }
            // 任务并没有把l...r全包住
            // 要把当前任务往下发
            // 任务  L, R  没有把本身表达范围 l,r 彻底包住
            int mid = (l + r) >> 1; // l..mid  (rt << 1)   mid+1...r(rt << 1 | 1)
            // 下发之前所有攒的懒任务
            pushDown(rt, mid - l + 1, r - mid);
            // 左孩子是否需要接到任务
            if (L <= mid) {
                add(L, R, C, l, mid, rt << 1);
            }
            // 右孩子是否需要接到任务
            if (mid < R) {
                add(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            // 左右孩子做完任务后，我更新我的sum信息
            pushUp(rt);
        }

        /**
         * 如果是更新任务，会取消之前所有的lazy
         * @param L 任务左边界
         * @param R 任务右边界
         * @param C 任务范围上所有的数改为C
         * @param l 实际范围左边界
         * @param r 实际范围右边界
         * @param rt 当前范围对应的下标
         */
        public void update(int L, int R, int C, int l, int r, int rt) {
            // 如果更新任务全部包含实际范围
            if (L <= l && r <= R) {
                // 是不是有更新的懒操作
                update[rt] = true;
                // 有更新的懒操作，懒任务的值是C
                change[rt] = C;
                // 累加和是实际范围size*C
                sum[rt] = C * (r - l + 1);
                // 之前lazy全取消
                lazy[rt] = 0;
                return;
            }
            // 当前任务躲不掉，无法懒更新，要往下发
            int mid = (l + r) >> 1;
            // 之前攒的所有懒任务下发
            pushDown(rt, mid - l + 1, r - mid);
            // 更新任务发给左边
            if (L <= mid) {
                update(L, R, C, l, mid, rt << 1);
            }
            // 更新任务发给右边
            if (R > mid) {
                update(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            // 左右孩子更新自己的任务后，汇总父亲位置的sum
            pushUp(rt);
        }

        /**
         * 查询任务
         * @param L 任务左边界
         * @param R 任务右边界
         * @param l 实际左边界
         * @param r 实际右边界
         * @param rt 当前范围对应的下标
         * @return
         */
        public long query(int L, int R, int l, int r, int rt) {
            // query任务全包实际范围
            if (L <= l && r <= R) {
                return sum[rt];
            }
            // query任务揽不住实际范围
            int mid = (l + r) >> 1;
            // 之前攒的任务下发任务
            pushDown(rt, mid - l + 1, r - mid);

            long ans = 0;
            // 左边是有多少个任务落在L...R范围上，累加返回
            if (L <= mid) {
                ans += query(L, R, l, mid, rt << 1);
            }
            // 右边是有多少个任务落在L...R范围上，累加返回
            if (R > mid) {
                ans += query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return ans;
        }

        public void update(int i, int val) {
            int l = i + 1;
            int r = l;
            update(l, r, val, 1, MAXN - 1, 1);
        }

        public int sumRange(int i, int j) {
            int l = i + 1;
            int r = j + 1;
            return (int) query(l, r, 1, MAXN - 1, 1);
        }
    }
}
