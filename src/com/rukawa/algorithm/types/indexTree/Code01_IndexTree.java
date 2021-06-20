package com.rukawa.algorithm.types.indexTree;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/6/20 0020 22:42
 */
public class Code01_IndexTree {

    /**
     * IndexTree
     *  特点：
     *  1、支持区间查询
     *  2、每与线段树那么强，但是非常同意改成一维、二维、三维的结构
     *  3、只支持单点更新
     *
     *
     *  arr[1~12]
     *  help[{1}, {1,2}, {3}, {1,2,3,4}, {5}, {5,6}, {7}, {1,2,3,4,5,6,7,8}, {9}, {9, 10}, {11}, {9,10,11,12}]
     *  两条规则：随便给一个二进制的数01011000
     *  1、此时这个位置的数要+d，还有哪些位置的会受牵连，不断的+最右侧的1，不超过N，都是受牵连的位置
     *  2、想知道1~index的累加和是多少？依次在tree中拿出位置，不断的剥掉最右侧的1拿出来，最终的累加和就是答案
     *
     *  时间复杂度：O(logN)
     *
     *  (l, r)的累加和=(1,r)的累加和 - (1,l)的累加和
     */

    // 一维结构
    public static class IndexTree {
        private int[] tree;
        private int N;

        public IndexTree(int size) {
            N = size;
            tree = new int[N + 1];
        }

        // -index = (~index + 1)
        // index & -index 提取index最右侧的1
        // index:          0011001000
        // index & -index: 0000001000
        // O(logN)
        public void add(int index, int d) {
            while (index <= N) {
                tree[index] += d;
                // index + 最右侧的1，只要index不超过N,收到影响的位置都会被更新
                index += index & -index;
            }
        }

        // 1~index 累加和是多少
        // O(logN)
        public int sum(int index) {
            int ret = 0;
            while (index > 0) {
                ret += tree[index];
                // 拨掉最右侧的1
                index -= index & -index;
            }
            return ret;
        }
    }

    public static class Right {
        private int[] nums;
        private int N;

        public Right(int size) {
            N = size + 1;
            nums = new int[N + 1];
        }

        public int sum(int index) {
            int ret = 0;
            for (int i = 1; i <= index; i++) {
                ret += nums[i];
            }
            return ret;
        }

        public void add(int index, int d) {
            nums[index] += d;
        }

    }

    public static void main(String[] args) {
        int N = 100;
        int V = 100;
        int testTime = 2000000;
        IndexTree tree = new IndexTree(N);
        Right test = new Right(N);
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int index = (int) (Math.random() * N) + 1;
            if (Math.random() <= 0.5) {
                int add = (int) (Math.random() * V);
                tree.add(index, add);
                test.add(index, add);
            } else {
                if (tree.sum(index) != test.sum(index)) {
                    System.out.println("Oops!");
                }
            }
        }
        System.out.println("test finish");
    }
}
