package com.rukawa.algorithm.base.class29_catalan;

import java.util.LinkedList;

/**
 * create by hqh on 2023/1/10
 */
public class Code01_10Ways {
    /**
     * 假设给你N个0和N个1，你必须用全部数字拼序列
     * 返回有多少序列满足：任何前缀串，1的数量都不少于0的数量
     */

    public static long ways1(int N) {
        int zero = N;
        int one = N;
        LinkedList<Integer> path = new LinkedList<>();
        LinkedList<LinkedList<Integer>> ans = new LinkedList<>();
        process(zero, one, path, ans);
        long count = 0;
        for (LinkedList<Integer> cur : ans) {
            int status = 0;
            for (Integer num : cur) {
                if (num == 0) {
                    status++;
                } else {
                    status--;
                }
                if (status < 0) {
                    break;
                }
            }
            if (status == 0) {
                count++;
            }
        }
        return count;
    }

    public static void process(int zero, int one, LinkedList<Integer> path, LinkedList<LinkedList<Integer>> ans) {
        if (zero == 0 && one == 0) {
            LinkedList<Integer> cur = new LinkedList<>();
            for (Integer num : path) {
                cur.add(num);
            }
            ans.add(cur);
        } else {
            if (zero == 0) {
                path.addLast(1);
                process(zero, one - 1, path, ans);
                path.removeLast();
            } else if (one == 0) {
                path.addLast(0);
                process(zero - 1, one, path, ans);
                path.removeLast();
            } else {
                path.addLast(1);
                process(zero, one - 1, path, ans);
                path.removeLast();
                path.addLast(0);
                process(zero - 1, one, path, ans);
                path.removeLast();
            }
        }
    }

    public static long ways2(int n) {
        if (n < 0) {
            return 0;
        }
        if (n < 2) {
            return 1;
        }
        long a = 1;
        long b = 1;
        long limit = n << 1;
        for (long i = 1; i <= limit; i++) {
            if (i <= n) {
                a *= i;
            } else {
                b *= i;
            }
        }
        return (b / a) / (n + 1);
    }

    public static void main(String[] args) {
        System.out.println("test begin");
//        for (int i = 0; i < 10; i++) {
//            long ans1 = ways1(i);
//            long ans2 = ways2(i);
//            if (ans1 != ans2) {
//                System.out.println("Oops!");
//            }
//        }
//        System.out.println(ways1(19));
        System.out.println(ways2(19));
        System.out.println("test finish");
    }
}
