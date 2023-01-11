package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.ArrayList;

/**
 * create by hqh on 2023/1/4
 */
public class Problem_2193_MinimumNumberOfMovesToMakePalindrome {
    /**
     * 给你一个只包含小写英文字母的字符串 s 。
     * 每一次 操作 ，你可以选择 s 中两个 相邻 的字符，并将它们交换。
     * 请你返回将 s 变成回文串的 最少操作次数 。
     * 注意 ，输入数据会确保 s 一定能变成一个回文串。
     *
     * 示例 1：
     * 输入：s = "aabb"
     * 输出：2
     * 解释：
     * 我们可以将 s 变成 2 个回文串，"abba" 和 "baab" 。
     * - 我们可以通过 2 次操作得到 "abba" ："aabb" -> "abab" -> "abba" 。
     * - 我们可以通过 2 次操作得到 "baab" ："aabb" -> "abab" -> "baab" 。
     * 因此，得到回文串的最少总操作次数为 2 。
     *
     * 示例 2：
     * 输入：s = "letelt"
     * 输出：2
     * 解释：
     * 通过 2 次操作从 s 能得到回文串 "lettel" 。
     * 其中一种方法是："letelt" -> "letetl" -> "lettel" 。
     * 其他回文串比方说 "tleelt" 也可以通过 2 次操作得到。
     * 可以证明少于 2 次操作，无法得到回文串。
     *
     *
     * 提示：
     * 1 <= s.length <= 2000
     * s 只包含小写英文字母。
     * s 可以通过有限次操作得到一个回文串。
     */
    // 暴力方法
    public static int minMovesToMakePalindrome(String s) {
        char[] str = s.toCharArray();
        int n = str.length;
        int minTimes = 0;
        for (int l = 0, r = n - 1; l < r; l++) {
            boolean flag = false;
            for (int pos = r; l != pos; pos--) {
                if (str[l] == str[pos]) {
                    for (; pos < r; pos++) {
                        swap(str, pos, pos + 1);
                        minTimes++;
                    }
                    r--;
                    flag = true;
                    break;
                }
            }
            if (!flag) minTimes += n / 2 - l;
        }
        return minTimes;
    }

    public static void swap(char[] str, int i, int j) {
        char tmp = str[i];
        str[i] = str[j];
        str[j] = tmp;
    }

    public static int minMovesToMakePalindrome2(String s) {
        int n = s.length();
        ArrayList<ArrayList<Integer>> indies = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            indies.add(new ArrayList<>());
        }
        for (int i = 0, j = 1; i < n; i++, j++) {
            int c = s.charAt(i) - 'a';
            indies.get(c).add(j);
        }
        int[] arr = new int[n + 1];
        IndexTree it = new IndexTree(n);
        for (int i = 0, l = 1; i < n; i++, l++) {
            if (arr[l] == 0) {
                int c = s.charAt(i) - 'a';
                int r = indies.get(c).remove(indies.get(c).size() - 1);
                if (l == r) {
                    arr[l] = (1 + n) / 2;
                    it.add(l, -1);
                } else {
                    int kth = it.sum(l);
                    arr[l] = kth;
                    arr[r] = n - kth + 1;
                    it.add(r, -1);
                }
            }
        }
        return number(arr, new int[n + 1], 1, n);
    }

    public static class IndexTree {

        public int[] tree;
        public int n;

        public IndexTree(int size) {
            tree = new int[size + 1];
            n = size;
            for (int i = 1; i <= n; i++) {
                add(i, 1);
            }
        }

        public int sum(int i) {
            int ans = 0;
            while (i > 0) {
                ans += tree[i];
                i -= i & -i;
            }
            return ans;
        }

        public void add(int i, int v) {
            while (i < tree.length) {
                tree[i] += v;
                i += i & -i;
            }
        }
    }

    public static int number(int[] arr, int[] help, int l, int r) {
        if (l >= r) {
            return 0;
        }
        int mid = l + ((r - l) >> 1);
        return number(arr, help, l, mid) + number(arr, help, mid + 1, r) + merge(arr, help, l, mid, r);
    }

    public static int merge(int[] arr, int[] help, int l, int m, int r) {
        int i = r;
        int p1 = m;
        int p2 = r;
        int ans = 0;
        while (p1 >= l && p2 > m) {
            ans += arr[p1] > arr[p2] ? (p2 - m) : 0;
            help[i--] = arr[p1] > arr[p2] ? arr[p1--] : arr[p2--];
        }
        while (p1 >= l) {
            help[i--] = arr[p1--];
        }
        while (p2 > m) {
            help[i--] = arr[p2--];
        }
        for (i = l; i <= r; i++) {
            arr[i] = help[i];
        }
        return ans;
    }

    public static void main(String[] args) {
        String s = "aabb";
        System.out.println(minMovesToMakePalindrome(s));
    }
}
