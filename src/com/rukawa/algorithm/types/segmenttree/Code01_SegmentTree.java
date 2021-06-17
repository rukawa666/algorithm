package com.rukawa.algorithm.types.segmenttree;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/6/15 0015 22:47
 */
public class Code01_SegmentTree {

    public static class SegmentTree {
        private int MAXN;
        // arr[]为原序列的信息从0开始，但是在arr中是从1开始
        private int[] arr;
        // sum[]模拟线段树维护区间和
        private int[] sum;
        // lazy[]为累加和懒惰标记
        private int[] lazy;

        public SegmentTree(int[] origin) {
            MAXN = origin.length + 1;
            arr = new int[MAXN];
            for (int i = 1; i < MAXN; i++) {
                arr[i] = origin[i - 1];
            }
            // 某一个范围的累加和信息，极端情况为2n+1，如果树的最后一列只有一个叶子节点，则至多需要(4*n)个空间即可
            sum = new int[MAXN << 2];
            // 某一个范围没有往下传递的累加任务
            lazy = new int[MAXN << 2];
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


        }
    }

}
