package com.rukawa.algorithm.trainingcamp.trainingcamp2.class3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-11 23:11
 * @Version：1.0
 */
public class Code02_Islands {


    /**
     * 一个只有0和1两种数字的二维矩阵中，上下左右能连成一片的1，算1个岛
     * 返回矩阵中，一共有几个岛
     */
    public static int countIslands1(int[][] m) {
        if (m == null || m[0] == null) {
            return 0;
        }
        int res = 0;
        int N = m.length;
        int M = m[0].length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (m[i][j] == 1) {
                    res++;
                    infect(m, i, j, N, M);
                }
            }
        }
        return res;
    }

    // 感染函数
    public static void infect(int[][] m, int i, int j, int N, int M) {
        if (i < 0 || i >= N || j < 0 || j >= M || m[i][j] != '1') {
            return;
        }
        m[i][j] = 2;
        infect(m, i + 1, j, N, M);
        infect(m, i - 1, j, N, M);
        infect(m, i, j + 1, N, M);
        infect(m, i, j - 1, N, M);
    }

    public static class Element<V> {
        public V value;

        public Element(V value) {
            this.value = value;
        }
    }

    public static class UnionFindSet<V> {
        // a->a 生成的点
        public HashMap<V, Element<V>> elementMap;
        public HashMap<Element<V>, Element<V>> fatherMap;
        // sizeMap中的key，每一个key都一定是集合的头节点(代表节点)
        public HashMap<Element<V>, Integer> sizeMap;

        public UnionFindSet(List<V> list) {
            elementMap = new HashMap<>();
            fatherMap = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V v : list) {
                Element<V> element = new Element<>(v);
                elementMap.put(v, element);
                fatherMap.put(element, element);
                sizeMap.put(element, 1);
            }
        }

        public Element<V> findHead(Element<V> element) {
            Stack<Element<V>> path = new Stack<>();
            while (element != fatherMap.get(element)) {
                path.push(element);
                element = fatherMap.get(element);
            }

            while (!path.isEmpty()) {
                fatherMap.put(path.pop(), element);
            }
            return element;
        }

        public boolean isSameSet(V a, V b) {
            if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
                return findHead(elementMap.get(a)) == findHead(elementMap.get(b));
            }
            return false;
        }

        public void union(V a, V b) {
            if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
                Element<V> aHead = findHead(elementMap.get(a));
                Element<V> bHead = findHead(elementMap.get(b));
                if (aHead != bHead) {
                    Element<V> big = sizeMap.get(aHead) > sizeMap.get(bHead) ? aHead : bHead;
                    Element<V> small = big == aHead ? bHead : aHead;
                    fatherMap.put(small, big);
                    sizeMap.put(big, sizeMap.get(aHead) + sizeMap.get(bHead));
                    sizeMap.remove(small);
                }
            }
        }

        public int getNum() {
            return sizeMap.size();
        }
    }

    public static int countIslands2(int[][] m) {
        List<String> list = new ArrayList<>();
        for (int row = 0; row < m.length; row++) {
            for (int col = 0; col < m[0].length; col++) {
                if (m[row][col] == 1) {
                    String position = row + "_" + col;
                    list.add(position);
                }
            }
        }

        UnionFindSet<String> unionSet = new UnionFindSet<>(list);
        for (int row = 0; row < m.length; row++) {
            for (int col = 0; col < m[0].length; col++) {
                if (m[row][col] == 1) {
                    String position = row + "_" + col;
                    if (row - 1 >= 0 && m[row - 1][col] == 1) {
                        String up = (row - 1) + "_" + col;
                        unionSet.union(up, position);
                    }

                    if (col - 1 >= 0 && m[row][col - 1] == 1) {
                        String left = row + "_" + (col - 1);
                        unionSet.union(left, position);
                    }
                }
            }
        }
        return unionSet.getNum();
    }

    public static void main(String[] args) {
        int[][] m1 = { { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 1, 0, 1, 1, 1, 0 }, { 0, 1, 1, 1, 0, 0, 0, 1, 0 },
                { 0, 1, 1, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 1, 1, 0, 0 }, { 0, 0, 0, 0, 1, 1, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };
        System.out.println(countIslands1(m1));

        int[][] m1Other = { { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 1, 0, 1, 1, 1, 0 }, { 0, 1, 1, 1, 0, 0, 0, 1, 0 },
                { 0, 1, 1, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 1, 1, 0, 0 }, { 0, 0, 0, 0, 1, 1, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };
        System.out.println(countIslands2(m1Other));

        int[][] m2 = { { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 1, 1, 1, 1, 1, 0 }, { 0, 1, 1, 1, 0, 0, 0, 1, 0 },
                { 0, 1, 1, 0, 0, 0, 1, 1, 0 }, { 0, 0, 0, 0, 0, 1, 1, 0, 0 }, { 0, 0, 0, 0, 1, 1, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };
        System.out.println(countIslands1(m2));

        int[][] m2Other = { { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 1, 1, 1, 1, 1, 0 }, { 0, 1, 1, 1, 0, 0, 0, 1, 0 },
                { 0, 1, 1, 0, 0, 0, 1, 1, 0 }, { 0, 0, 0, 0, 0, 1, 1, 0, 0 }, { 0, 0, 0, 0, 1, 1, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };

        System.out.println(countIslands2(m2Other));

    }
}
