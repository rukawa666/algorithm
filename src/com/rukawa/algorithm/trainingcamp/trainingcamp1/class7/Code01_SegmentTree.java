package com.rukawa.algorithm.trainingcamp.trainingcamp1.class7;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-05 7:47
 * @Version：1.0
 */
public class Code01_SegmentTree {

    /**
     * 举例
     *                  1~8
     *        1~4                  5~8
     *    1~2       3~4       5~6       7~8
     * 1~1  2~2  3~3  4~4  5~5   6~6 7~7  8~8
     *
     * 现在下发(1~6)区间+4
     * 先去左孩子(1~4)下发(1~6)+4的任务，发现(1~6)包含(1~4)，此时(1~4)这个值+4，放在lazy，停止下发；
     * 再去右孩子(5~6)下发(1~6)+4的任务，不完全包含，则继续往下发，此时(5~6)被完全包含，此时(5~6)+4,放在lazy中，停止下发，
     * 而(7~8)和任务(1~6)没有交集，直接停止
     */
    public static class SegmentTree {
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

        public SegmentTree(int[] origin) {
            this.MAXN = origin.length + 1;
            arr = new int[MAXN];
            for (int i = 1; i < MAXN; i++) {
                arr[i] = origin[i - 1];
            }
            // MAXN<<2 = MAXN*4
            sum = new int[MAXN << 2];   // 某一个范围的累加和概念
            lazy = new int[MAXN << 2];  // 某一个范围没有往下传递的累加任务
            change = new int[MAXN << 2];  // 某一个范围没有更新操作的任务
            update = new boolean[MAXN << 2]; // 某一个更新范围，更新成了什么
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
            // 之前所有攒的懒任务
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

    }

    public static class Right {
        public int[] arr;

        public Right(int[] origin) {
            arr = new int[origin.length + 1];
            for (int i = 0; i > origin.length; i++) {
                arr[i + 1] = origin[i];
            }
        }

        public void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] = C;
            }
        }

        public void add(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] += C;
            }
        }

        public long query(int L, int R) {
            long ans = 0;
            for (int i = L; i <= R; i++) {
                ans += arr[i];
            }
            return ans;
        }
    }

    public static int[] genarateRandomArray(int len, int max) {
        int size = (int) (Math.random() * len) + 1;
        int[] origin = new int[size];
        for (int i = 0; i < size; i++) {
            origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return origin;
    }

    public static boolean test() {
        int len = 100;
        int max = 1000;
        int testTimes = 5000;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = genarateRandomArray(len, max);
            SegmentTree seg = new SegmentTree(origin);
            int S = 1;
            int N = origin.length;
            int root = 1;
            seg.build(S, N, root);
            Right rig = new Right(origin);
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int C = (int) (Math.random() * max) - (int) (Math.random() * max);
                if (Math.random() < 0.5) {
                    seg.add(L, R, C, S, N, root);
                    rig.add(L, R, C);
                } else {
                    seg.update(L, R, C, S, N, root);
                    rig.update(L, R, C);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                long ans1 = seg.query(L, R, S, N, root);
                long ans2 = rig.query(L, R);
                if (ans1 != ans2) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        /*int[] origin = { 2, 1, 1, 2, 3, 4, 5 };
        SegmentTree seg = new SegmentTree(origin);
        int S = 1; // 整个区间的开始位置，规定从1开始，不从0开始 -> 固定
        int N = origin.length; // 整个区间的结束位置，规定能到N，不是N-1 -> 固定
        int root = 1; // 整棵树的头节点位置，规定是1，不是0 -> 固定
        int L = 2; // 操作区间的开始位置 -> 可变
        int R = 5; // 操作区间的结束位置 -> 可变
        int C = 4; // 要加的数字或者要更新的数字 -> 可变
        // 区间生成，必须在[S,N]整个范围上build
        seg.build(S, N, root);
        // 区间修改，可以改变L、R和C的值，其他值不可改变
        seg.add(L, R, C, S, N, root);
        // 区间更新，可以改变L、R和C的值，其他值不可改变
        seg.update(L, R, C, S, N, root);
        // 区间查询，可以改变L和R的值，其他值不可改变
        long sum = seg.query(L, R, S, N, root);
        System.out.println(sum);

        System.out.println("对数器测试开始...");
        System.out.println("测试结果 : " + (test() ? "通过" : "未通过"));*/

        int[] origin = { 1,3,5};
        SegmentTree seg = new SegmentTree(origin);
        int S = 1;
        int N = origin.length;
        int root = 1;
        seg.build(S, N, root);

        long sum = seg.query(1,3, S, N, root);
        System.out.println(sum);

        seg.update(2, 2, 2, S, N, root);

        sum = seg.query(1,3, S, N, root);
        System.out.println(sum);
    }
}
