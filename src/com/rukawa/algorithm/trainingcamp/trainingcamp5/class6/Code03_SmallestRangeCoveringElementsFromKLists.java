package com.rukawa.algorithm.trainingcamp.trainingcamp5.class6;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-11-12 8:06
 * @Version：1.0
 */
public class Code03_SmallestRangeCoveringElementsFromKLists {
    /**
     * 最小区间
     * 你有 k 个 非递减排列 的整数列表。找到一个 最小 区间，使得 k 个列表中的每个列表至少有一个数包含在其中。
     * 我们定义如果 b-a < d-c 或者在 b-a == d-c 时 a < c，则区间 [a,b] 比 [c,d] 小。
     *
     * 示例 1：
     * 输入：nums = [[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
     * 输出：[20,24]
     * 解释：
     * 列表 1：[4, 10, 15, 24, 26]，24 在区间 [20,24] 中。
     * 列表 2：[0, 9, 12, 20]，20 在区间 [20,24] 中。
     * 列表 3：[5, 18, 22, 30]，22 在区间 [20,24] 中。
     *
     * 示例 2：
     * 输入：nums = [[1,2,3],[1,2,3],[1,2,3]]
     * 输出：[1,1]
     *
     * 示例 3：
     * 输入：nums = [[10,10],[11,11]]
     * 输出：[10,11]
     *
     * 示例 4：
     * 输入：nums = [[10],[11]]
     * 输出：[10,11]
     *
     * 示例 5：
     * 输入：nums = [[1],[2],[3],[4],[5],[6],[7]]
     * 输出：[1,7]
     *
     * 提示：
     * nums.length == k
     * 1 <= k <= 3500
     * 1 <= nums[i].length <= 50
     * -105 <= nums[i][j] <= 105
     * nums[i] 按非递减顺序排列
     */

    // 01:25:28
    public int[] smallestRange(List<List<Integer>> nums) {
        /**
         * 1、建立一张从小到大的有序表，每次弹出有序表最小值
         * 2、然后在弹出这个最小值的集合中选择下一个入表
         */
        int N = nums.size();
        TreeSet<Node> orderSet = new TreeSet<>(new NodeComparator());
        for (int i = 0; i < N; i++) {
            orderSet.add(new Node(nums.get(i).get(0), i, 0));
        }
        boolean set = false;
        int a = 0;
        int b = 0;
        while (orderSet.size() == N) {
            Node min = orderSet.first();
            Node max = orderSet.last();
            if (!set || (max.value - min.value < b - a)) {
                set = true;
                a = min.value;
                b = max.value;
            }
            min = orderSet.pollFirst();
            int arrid = min.arrid;
            int index = min.index + 1;
            if (index != nums.get(arrid).size()) {
                orderSet.add(new Node(nums.get(arrid).get(index), arrid, index));
            }
        }
        return new int[]{a, b};
    }

    public static class Node {
        public int value;
        // 属于哪一个数组
        public int arrid;
        // 在数组中的哪个位置
        public int index;

        public Node(int v, int ai, int i) {
            this.value = v;
            this.arrid = ai;
            this.index = i;
        }
    }

    public static class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            return o1.value != o2.value ? o1.value - o2.value : o1.arrid - o2.arrid;
        }
    }
}
