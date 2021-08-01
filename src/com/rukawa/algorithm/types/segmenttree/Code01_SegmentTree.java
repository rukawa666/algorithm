package com.rukawa.algorithm.types.segmenttree;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/6/15 0015 22:47
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
        // arr[]为原序列的信息从0开始，但是在arr中是从1开始
        // 2的最小次方的值是1，为了方便从数组从1开始
        private int[] arr;
        // sum[]模拟线段树维护区间和
        private int[] sum;
        // lazy[]为累加和懒惰标记
        private int[] lazy;

        // change && update 用于懒更新
        // 如果下发任务是3~874，则251~500的区间被全部包含，此时在这一块懒住，不往下下发任务
        // 更新的值
        private int[] change;
        // 更新慵懒的标记
        // 如果当前change[i]是0，代表的是没有change信息还是更新为0，不知道
        // 需要update[i] = true，代表更新为0
        // update[i] = false, 没有change信息
        private boolean[] update;

        public SegmentTree(int[] origin) {
            MAXN = origin.length + 1;
            arr = new int[MAXN];    // arr[0]不用，从1开始
            for (int i = 1; i < MAXN; i++) {
                arr[i] = origin[i - 1];
            }
            // 某一个范围的累加和信息，极端情况为2n+1，如果树的最后一列只有一个叶子节点，则至多需要(4*n)个空间即可
            sum = new int[MAXN << 2];
            // 某一个范围没有往下传递的累加任务
            lazy = new int[MAXN << 2];
            // 某一个范围有没有更新操作的任务
            change = new int[MAXN << 2];
            // 某一个范围更新任务，更新成了什么
            update = new boolean[MAXN << 2];
        }

        // 向上汇总
        public void pushUp(int rt) {
            // sum[rt] = sum[rt * 2] + sum[rt * 2 + 1];
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
        }

        // 之前的，所有的懒增加和懒更新，从父范围，发送给左右两个子范围
        // 分发策略
        // ln表示左子树元素的节点个数，rn表示右子树节点个数
        public void pushDown(int rt, int ln, int rn) {

            if (update[rt]) {
                // 左右俩孩子都更新
                update[rt << 1] = true;
                update[rt << 1 | 1] = true;
                // 左右俩孩子都更新为父亲节点的值
                change[rt << 1] = change[rt];
                change[rt << 1 | 1] = change[rt];
                // 左右俩孩子的累加和信息全部盖掉
                sum[rt << 1] = change[rt] * ln;
                sum[rt << 1 | 1] = change[rt] * rn;
                // 左右俩孩子保留的所有lazy都清空
                lazy[rt << 1] = 0;
                lazy[rt << 1 | 1] = 0;
                update[rt] = false;
            }

            // 为什么更新完之后还要lazy更新？
            // 如果当前更新任务，更新任务全包含实际范围，只会懒更新任务，不会实际下发任务
            // 接着中间有一些更新、增加操作操作
            // 此时既有更新操作，又攒了一些增加操作，下发的时候，增加操作的数不能被覆盖，因为时间在更新后面
            // 所有先发更新任务，再去下发lazy任务去调整
            // 什么情况下，update为true，lazy又有值？一定是最晚发生的更新到目前为止还没有发生过，但是中间攒着一些add操作
            /**
             * 举例子说明：
             *  1~500  update 7
             *  1~500  add 3
             *  1~500  add 9
             *  1~500  add 2
             *
             *  既有update信息又有add信息，说明在最近一次update信息到目前位置已经攒了几次累加
             *  所以既有更新任务又有累加任务，需要先下发更新再进行累加
             */

            // 当前父节点有懒信息，下发给左右子树
            if (lazy[rt] != 0) {
                lazy[rt << 1] += lazy[rt];
                sum[rt << 1] += lazy[rt] * ln;
                lazy[rt << 1 | 1] += lazy[rt];
                sum[rt << 1 | 1] += lazy[rt] * rn;
                lazy[rt] = 0;
            }
        }

        // 在初始化阶段，先把sum数组，填好
        // 在arr[l~r]范围上，去build，1~N
        // rt: 这个范围在sum中的下标
        public void build(int l, int r, int rt) {
            // l==r是叶子节点
            if (l == r) {
                sum[rt] = arr[l];
                return;
            }
            int mid = (l + r) >> 1;
            build(l, mid, rt << 1);
            build(mid + 1, r, rt << 1 | 1);
            pushUp(rt);
        }

        // [L~R] 任务范围，所有的值累加上C
        // [l~r] 表达的范围
        // rt 去哪找l，r范围上的信息
        public void add(int L, int R, int C, int l, int r, int rt) {
            // 任务如果把此时的范围全包，需要懒更新
            if (L <= l && r <= R) {
                sum[rt] += C * (r - l + 1);
                lazy[rt] += C;
                return;
            }
            // 不全包，之前的任务需要下发
            int mid = (l + r) >> 1;
            // 下发任务
            pushDown(rt, mid - l + 1, r - mid);
            // 得到的总任务需要发给左边
            if (L <= mid) {
                add(L, R, C, L, mid, rt << 1);
            }
            // 得到的总任务需要发给右边
            if (R > mid) {
                add(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            pushUp(rt);
        }

        // L~R 所有的值更新为C
        // l~r rt
        public void update(int L, int R, int C, int l, int r, int rt) {
            if (L <= l && r <= R) {
                update[rt] = true;
                change[rt] = C;
                // 累加和重新设置为C
                sum[rt] = C * (r - l + 1);
                // 累计的懒全部清空
                lazy[rt] = 0;
                return;
            }
            // 当前的任务躲不掉，无法懒更新，需要下发
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            if (L <= mid) {
                update(L, R, C, l, mid, rt << 1);
            }
            if (R > mid) {
                update(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            // 调整自己的累加和
            pushUp(rt);
        }

        // L~R的累加和是多少？
        // l~r的区间范围上
        // rt 数组的下标
        public long query(int L, int R, int l, int r, int rt) {
            if (L <= l && r <= R) {
                return sum[rt];
            }
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            long ans = 0;
            if (L <= mid) {
                ans += query(L, R, l, mid, rt << 1);
            }
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
