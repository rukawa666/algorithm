package com.rukawa.algorithm.trainingcamp.trainingcamp3.class7;

import java.util.HashMap;
import java.util.Stack;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-08 11:36
 * @Version：1.0
 */
public class Code04_LargestComponentSizeByCommonFactor {
    /**
     * 一个数组中，如果两个数的公共因子有大于1的，则认为这两个数之间有通路
     * 返回数组中，有多少个独立的域
     */
    // 时间复杂度 O(N * 根号v)
    public static int largestComponentSize2(int[] arr) {
        UnionFindSet2 unionFind = new UnionFindSet2(arr.length);
        // key 是某一个因子  value包含key因子的，其中一个数的位置
        HashMap<Integer, Integer> factorsMap = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            int num = arr[i];   // 当前数
            int limit = (int) Math.sqrt(num);   // 试到什么时候停止，根号num停止  1~根号num
            for (int j = 1; j <= limit; j++) {
                if (num % j == 0) { // num中含有j的因子
                    // 第一对第一个数是j自己
                    if (j != 1) {   // 这个因子不是1
                        if (!factorsMap.containsKey(j)) {   // 当前数是含有j因子的第一个数
                            factorsMap.put(j, i);
                        } else {
                            unionFind.union(factorsMap.get(j), i);
                        }
                    }
                    // 第一对第二个数是other
                    int other = num / j;    // other * j = num
                    if (other != 1) {   // num含有other的因子
                        if (!factorsMap.containsKey(other)) {
                            factorsMap.put(other, i);
                        } else {
                            unionFind.union(factorsMap.get(other), i);
                        }
                    }

                }
            }
        }
        return unionFind.maxSize();
    }

    public static class UnionFindSet2 {
        private int[] parents; // parents[i] = j arr[i]的父亲是arr[j]
        private int[] sizes; // sizes[i] = X arr[i]所在的集合大小为X

        public UnionFindSet2(int len) {
            parents = new int[len];
            sizes = new int[len];
            for (int i = 0; i < len; i++) {
                parents[i] = i;
                sizes[i] = 1;
            }
        }

        public int size() {
            int ans = 0;
            for (int i = 0; i < sizes.length; i++) {
                ans += sizes[i] != 0 ? 1 : 0;
            }
            return ans;
        }

        public int maxSize() {
            int ans = 0;
            for (int size : sizes) {
                ans = Math.max(ans, size);
            }
            return ans;
        }

        private int findHead(int element) {
            Stack<Integer> path = new Stack<>();
            while (element != parents[element]) {
                path.push(element);
                element = parents[element];
            }
            while (!path.isEmpty()) {
                parents[path.pop()] = element;
            }
            return element;
        }

        // a 和 b 分别是两个数的位置，不是值
        public void union(int a, int b) {
            int aF = findHead(a);
            int bF = findHead(b);
            if (aF != bF) {
                int big = sizes[aF] >= sizes[bF] ? aF : bF;
                int small = big == aF ? bF : aF;
                parents[small] = big;
                sizes[big] = sizes[aF] + sizes[bF];
                sizes[small] = 0;
            }
        }
    }

    // arr中没有小于1的数
    public static int largestComponentSize1(int[] arr) {
        UnionFindSet1 set = new UnionFindSet1(arr.length);
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (gcd(arr[i], arr[j]) != 1) {
                    set.union(i, j);
                }
            }
        }
        return set.maxSize();
    }

    // 最大公约数
    public static int gcd(int m, int n) {
        return n == 0 ? m : gcd(n, m % n);
    }

    public static class UnionFindSet1 {
        private HashMap<Integer, Integer> fatherMap;
        // key是代表点时候，才有记录，value是所在集合一共有多少个点
        private HashMap<Integer, Integer> sizeMap;

        public UnionFindSet1(int size) {
            this.fatherMap = new HashMap<>();
            this.sizeMap = new HashMap<>();
            for (int i = 0; i < size; i++) {
                fatherMap.put(i, i);
                sizeMap.put(i, 1);
            }
        }

        public int size() {
            return sizeMap.size();
        }

        public int maxSize() {
            int res = 0;
            for (int size : sizeMap.values()) {
                res = Math.max(res, size);
            }
            return res;
        }


        public int findFather(Integer x) {
            Stack<Integer> path = new Stack<>();
            while (x != fatherMap.get(x)) {
                path.push(x);
                x = fatherMap.get(x);
            }
            while (!path.isEmpty()) {
                fatherMap.put(path.pop(), x);
            }
            return x;
        }

        public boolean isSameSet(int a, int b) {
            return findFather(a) == findFather(b);
        }

        public void union(int a, int b) {
            int aHead = findFather(a);
            int bHead = findFather(b);
            if (aHead != bHead) {
                int aSize = sizeMap.get(aHead);
                int bSize = sizeMap.get(bHead);
                Integer big = aSize > bSize ? aHead : bHead;
                Integer small = big == aHead ? bHead : aHead;
                fatherMap.put(small, big);
                sizeMap.put(big, aSize + bSize);
                sizeMap.remove(small);
            }
        }
    }

    public static void main(String[] args) {
//        int[] test = { 2, 3, 6, 7, 4, 12, 21, 39 };
//        System.out.println(largestComponentSize1(test));
//        System.out.println(largestComponentSize2(test));

        int a = 2;
        int b = 4;
        System.out.println(gcd(a, b));
    }
}
