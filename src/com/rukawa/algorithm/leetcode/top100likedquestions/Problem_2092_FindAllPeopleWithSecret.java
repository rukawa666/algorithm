package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * create by hqh on 2023/1/12
 */
public class Problem_2092_FindAllPeopleWithSecret {
    /**
     * 找出知晓秘密的所有专家
     * 给你一个整数 n ，表示有 n 个专家从 0 到 n - 1 编号。另外给你一个下标从 0 开始的二维整数数组 meetings ，
     * 其中 meetings[i] = [xi, yi, timei] 表示专家 xi 和专家 yi 在时间 timei 要开一场会。一个专家可以同时参加 多场会议 。
     * 最后，给你一个整数 firstPerson 。
     * 专家 0 有一个 秘密 ，最初，他在时间 0 将这个秘密分享给了专家 firstPerson 。接着，这个秘密会在每次有知晓这个秘密的专家参加会议时进行传播。
     * 更正式的表达是，每次会议，如果专家 xi 在时间 timei 时知晓这个秘密，那么他将会与专家 yi 分享这个秘密，反之亦然。
     * 秘密共享是 瞬时发生 的。也就是说，在同一时间，一个专家不光可以接收到秘密，还能在其他会议上与其他专家分享。
     * 在所有会议都结束之后，返回所有知晓这个秘密的专家列表。你可以按 任何顺序 返回答案。
     *
     * 示例 1：
     * 输入：n = 6, meetings = [[1,2,5],[2,3,8],[1,5,10]], firstPerson = 1
     * 输出：[0,1,2,3,5]
     * 解释：
     * 时间 0 ，专家 0 将秘密与专家 1 共享。
     * 时间 5 ，专家 1 将秘密与专家 2 共享。
     * 时间 8 ，专家 2 将秘密与专家 3 共享。
     * 时间 10 ，专家 1 将秘密与专家 5 共享。
     * 因此，在所有会议结束后，专家 0、1、2、3 和 5 都将知晓这个秘密。
     *
     * 示例 2：
     * 输入：n = 4, meetings = [[3,1,3],[1,2,2],[0,3,3]], firstPerson = 3
     * 输出：[0,1,3]
     * 解释：
     * 时间 0 ，专家 0 将秘密与专家 3 共享。
     * 时间 2 ，专家 1 与专家 2 都不知晓这个秘密。
     * 时间 3 ，专家 3 将秘密与专家 0 和专家 1 共享。
     * 因此，在所有会议结束后，专家 0、1 和 3 都将知晓这个秘密。
     *
     * 示例 3：
     * 输入：n = 5, meetings = [[3,4,2],[1,2,1],[2,3,1]], firstPerson = 1
     * 输出：[0,1,2,3,4]
     * 解释：
     * 时间 0 ，专家 0 将秘密与专家 1 共享。
     * 时间 1 ，专家 1 将秘密与专家 2 共享，专家 2 将秘密与专家 3 共享。
     * 注意，专家 2 可以在收到秘密的同一时间分享此秘密。
     * 时间 2 ，专家 3 将秘密与专家 4 共享。
     * 因此，在所有会议结束后，专家 0、1、2、3 和 4 都将知晓这个秘密。
     *
     * 提示：
     * 2 <= n <= 105
     * 1 <= meetings.length <= 105
     * meetings[i].length == 3
     * 0 <= xi, yi <= n - 1
     * xi != yi
     * 1 <= timei <= 105
     * 1 <= firstPerson <= n - 1
     */
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        // 0~n-1号专家，各自建立小集合
        // (0, firstPerson)合在一起，作为知道秘密的集合
        UnionFind uf = new UnionFind(n, firstPerson);
        int m = meetings.length;
        Arrays.sort(meetings, (a, b) -> a[2] - b[2]);
        // [1,7,1]  [2,4,2] [3,6,2]
        //  1,7   2,4   3,6
        int[] help = new int[m << 1];
        help[0] = meetings[0][0];
        help[1] = meetings[0][1];
        int size = 2;
        for (int i = 1; i < m; i++) {
            // i 2
            if (meetings[i][2] != meetings[i - 1][2]) {
                share(help, size, uf);
                help[0] = meetings[i][0];
                help[1] = meetings[i][1];
                size = 2;
            } else {
                help[size++] = meetings[i][0];
                help[size++] = meetings[i][1];
            }
        }
        share(help, size, uf);
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (uf.know(i)) {
                ans.add(i);
            }
        }
        return ans;
    }

    public static void share(int[] help, int size, UnionFind uf) {
        for (int i = 0; i < size; i += 2) {
            uf.union(help[i], help[i + 1]);
        }
        for (int i = 0; i < size; i++) {
            if (!uf.know(help[i])) {
                uf.isolate(help[i]);
            }
        }
    }

    public static class UnionFind {
        public int[] father;
        public boolean[] sect;
        public int[] help;

        public UnionFind(int n, int first) {
            father = new int[n];
            sect = new boolean[n];
            help = new int[n];
            for (int i = 1; i < n; i++) {
                father[i] = i;
            }
            father[first] = 0;
            sect[0] = true;
        }

        private int find(int i) {
            int hi = 0;
            while (i != father[i]) {
                help[hi++] = i;
                i = father[i];
            }
            for (hi--; hi >= 0; hi--) {
                father[help[hi]] = i;
            }
            return i;
        }

        public void union(int i, int j) {
            int fatheri = find(i);
            int fatherj = find(j);
            if (fatheri != fatherj) {
                father[fatherj] = fatheri;
                sect[fatheri] |= sect[fatherj];
            }
        }

        public boolean know(int i) {
            return sect[find(i)];
        }

        public void isolate(int i) {
            father[i] = i;
        }
    }
}
