package com.rukawa.algorithm.trainingcamp.trainingcamp3.class6;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-30 7:46
 * @Version：1.0
 */
public class Code01_MaxEor {
    /**
     * 一个数组的异或和是指数组中所有的数异或在一起的结果
     * 给定一个数组arr，求最大子数组异或和。
     */

    public static int maxEorSubarray2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        int eor = 0;    // 0...i 异或和
        // 前缀树 -> trie
        NumTrie trie = new NumTrie();
        trie.add(0);    // 一个数也没有的时候，异或和是0

        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];  // eor -> 0...i异或和
            // X, 0~0, 0~1, ..., 0~i-1
            max = Math.max(max, trie.maxEor(eor));
            trie.add(eor);
        }
        return max;
    }
    public static class NumTrie {
        // 头节点
        public Node head = new Node();

        // 把某个数字newNum加入到这棵树的前缀树里
        // num是一个32位的整数，所以加入的过程一共走了32步
        public void add(int newNum) {
            Node cur = this.head;
            for (int move = 31; move >= 0; move--) {
                // 从高位到低位，取出每一位的状态，如果当前状态是0
                // path(int) = 0
                // 如果状态是1，path(int) = 1
                int path = ((newNum >> move) & 1);
                // 无路新建、有路复用
                cur.nextS[path] = cur.nextS[path] == null ? new Node() : cur.nextS[path];
                cur = cur.nextS[path];
            }
        }

        // 该结构之前收集了一票数字，并且建好了前缀树
        // sum和谁^ 把最大的结果返回
        // 给定一个待查询数字，它根所有的数字异或后最大的结果返回
        public int maxEor(int sum) {
            Node cur = this.head;
            int res = 0;
            for (int move = 31; move >= 0; move--) {
                // sum 在move位的状态
                int path = (sum >> move) & 1;
                // 期待的路
                // 如果是最高位，希望最高位的符号位和sum的符号位相等
                // 如果不是符号位，则希望和sum的move位状态，则相反
                int best = move == 31 ? path : (path ^ 1);

                // 实际走的路
                // 如果期待的路不为空，则走期待的路，期待的路为空，则只能走相反的路
                best = cur.nextS[best] != null ? best : (best ^ 1);

                // (path ^ best) 当前位异或完的结果
                // path^best -> 异或后最好的结果(如果1存在则为1，否则为0)
                // (path ^ best) << move -> 然后把(path^best)最好的结果放到对应的位置
                // res |= (path ^ best) << move -> 每个move位对应的最好的结果|上去，得到最终的最大结果
                res |= (path ^ best) << move;
                cur = cur.nextS[best];
            }
            return res;
        }
    }

    // 前缀树的节点类型，每个节点向下只可能走向0或1的路
    // node.nextS[0] == null 0方向没路
    // node.nextS[0] != null 0方向有路
    public static class Node {
        public Node[] nextS = new Node[2];
    }

    // 暴力法：O(N^2)
    public static int maxEorSubarray1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 准备一个前缀异或和数组eor
        // eor[i] = arr[0...i]的异或结果
        int[] eor = new int[arr.length];
        eor[0] = arr[0];
        // 生成eor数组，eor[i]代表arr[0...i]的异或和
        for (int i = 1; i < arr.length; i++) {
            eor[i] = arr[i] ^ eor[i - 1];
        }
        int max = Integer.MIN_VALUE;
        // 以j结尾的情况下，每一个子数组最大的异或和
        for (int j = 0; j < arr.length; j++) {
            // 依次尝试arr[0..j]、arr[1...j]、arr[2...j]...arr[j...j]
            /**
             * 以j位置结尾的情况下
             * 0...j
             * 1...j
             * 2...j
             * i...j  = eor[j] ^ eor[i-1]
             *  解释eor[j] -> 0...j的异或和，eor[i-1] -> 0...i-1的异或和
             *
             */
            for (int i = 0; i <= j; i++) {
                max = Math.max(max, i == 0 ? eor[j] : eor[j] ^ eor[i - 1]);
            }
        }
        return max;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 30;
        int maxValue = 50;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int comp = maxEorSubarray1(arr);
            int res = maxEorSubarray2(arr);
            if (res != comp) {
                succeed = false;
                printArray(arr);
                System.out.println(res);
                System.out.println(comp);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

         // int[] arr = generateRandomArray(6, maxValue);
        /* int[] arr = { 3, -28, -29, 2};

         for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
         }
         System.out.println();
         System.out.println("==================");
         System.out.println(maxEorSubarray1(arr));
         System.out.println((int) (-28 ^ -29));*/

    }
}
