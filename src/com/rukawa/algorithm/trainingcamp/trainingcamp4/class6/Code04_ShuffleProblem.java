package com.rukawa.algorithm.trainingcamp.trainingcamp4.class6;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-20 7:36
 * @Version：1.0
 */
public class Code04_ShuffleProblem {
    /**
     * 给定一个长度为偶数的数组arr，长度记为2*N。前N个为左部分，后N个为右部分。
     * arr就可以表示为{L1,L2,L3,...Ln,R1,R2,R3,...Rn}，请将数组调整成
     * {R1,L1,R2,L2,...,Rn,Ln}
     *
     * 有限几个变量，O(N)完成
     */

    // O(N)
    public static void shuffle(int[] arr) {
        /**
         * 思路：
         *
         * 完美洗牌问题结论：
         *    1、特殊长度
         *      a、对于一些非常特殊的长度，如果总长度S(2N),S=(3^k) - 1,k>0
         *           S = 2, 8, 26,...
         *      b、环的出发点从1,3,9,...,3^(k-1)，
         *          当长度为2，只有1个出发点，下标为1
         *          当长度为8，只有2个出发点，下标为1,3
         *          当长度为26，只有3个出发点，下标为1,3,9
         *          当长度为(3^k)-1，有k个出发点，下标为1,3,9,...,3^(k-1)
         *    2、普通长度
         *      a、找出(3^k) - 1 最接近S的大小，假如为80，数组长度为200
         *      b、然后在左半区找出前40个数，剩下的60个数和右半区的前40个交换位置
         *      c、此时对80长度区域内的数进行第一步操作
         *      d、目前已经处理完80个长度，剩余120数，继续从步骤a找<=120，长度为80
         *      e、前面80已经处理完毕，选择80后面40个数，剩余20个数和右半区40个数交换位置
         *      f、对80个数进行特殊长度处理，剩余40个数，继续从步骤a找<=40，长度为26
         *      g、前160个数已经处理完毕，左半区选择13个数，剩余7个数和右半区13个数交换位置
         *      h、26个数进行特殊长度处理，总共处理完毕186个数，剩余14个数，和上面步骤一样
         */
        // 长度必须不为空，且长度必须为偶数
        if (arr != null && arr.length != 0 && (arr.length & 1) == 0) {
            shuffle(arr, 0, arr.length - 1);
        }
    }

    // 在arr[L...R]上做成完美洗牌的调整
    public static void shuffle(int[] arr, int L, int R) {
        // R - L + 1 体现为剩余数字，如果0个数做完了，如果还剩余，肯定是偶数
        // (总数，接近总数的值) -> (32,26) -> (6,2) -> (4,2) -> (2,2) -> 0个数
        while (R - L + 1 > 0) { // 切成一块块解决，每一块的长度满足(3^k) - 1
            int len = R - L + 1;
            int base = 3;
            int k = 1;
            // 计算小于等于len并且是离len最近的，满足(3^k) - 1的数
            // 也就是找到最大的k，满足3^k <= len+1
            while (base <= (len + 1) / 3) { // 防止溢出 (base*3)<=len+1
                base *= 3;
                k++;
            }

            // 3^k - 1
            // 当前解决长度为base-1的块，一半就是再除2
            int half = (base - 1) / 2;
            // [L...R]的中点位置
            int mid = (L + R) / 2;
            // 要旋转的左部分为[L+half...mid],右部分为[mid+1...mid+half]
            // 注意在这里，arr的下标是从0开始的
            rotate(arr, L + half, mid, mid + half);
            // 旋转完成之后，从L开始算起，长度为base-1的部分进行下标连续推
            cycles(arr, L, base - 1, k);
            // 解决完前base-1的长度，剩下的部分继续处理
            L = L + base - 1;
        }
    }

    // 数组的长度为len，调整前的位置是i，返回调整后的位置
    // 下标不从0开始，从1开始
    public static int modifyIndex1(int i, int len) {
        if (i <= len / 2) { // 在左半区
            return i * 2;
        } else {    // 在右半区
            return (i - (len / 2)) * 2 - 1;
        }
    }

    // 剃刀函数，把上面两个分段合并为一个处理
    public static int modifyIndex2(int i, int len) {
        return (2 * i) % (len + 1);
    }

    // 数组arr,[L...M]为左部分，[M+1...R]为右部分，左右两部分互换
    public static void rotate(int[] arr, int L, int M, int R) {
        /**
         * 思路：
         *   1、把数组arr[L...M]的位置逆序
         *   2、把数组arr[M+1...R]的位置逆序
         *   3、整个数组arr逆序
         *   例子：arr -> {a,b,c,d,e,f,g,h}  把a,b,c挪到d,e,f,g,h的后面
         *      1、c,b,a,d,e,f,g,h
         *      2、c,b,a,h,g,f,e,d
         *      3、d,e,f,g,h,a,b,c
         */
        reverse(arr, L, M);
        reverse(arr, M + 1, R);
        reverse(arr, L, R);
    }
    // [L...R]做逆序调整
    public static void reverse(int[] arr, int L, int R) {
        while (L < R) {
            int tmp = arr[L];
            arr[L++] = arr[R];
            arr[R--] = tmp;
        }
    }

    // 主函数，特殊长度的处理
    // 从start位置开始，往右len的长度这一段，做下标连续推
    // 出发位置依次是1,3,9,...,3^(k-1)
    public static void cycles(int[] arr, int start, int len, int k) {
        // 找到每一个出发位置trigger，一共k个
        // 每一个trigger都进行下标连续推
        // 出发位置是从1开始算的，而数组下标是从0开始算的。
        for (int i = 0, trigger = 1; i < k; i++, trigger *= 3) {
            int preValue = arr[trigger + start - 1];
            int cur = modifyIndex1(trigger, len);
            while (cur != trigger) {
                int tmp = arr[cur + start - 1];
                arr[cur + start - 1] = preValue;
                preValue = tmp;
                cur = modifyIndex1(cur, len);
            }
            arr[cur + start - 1] = preValue;
        }
    }

    // for test
    public static boolean isValidWiggle(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if ((i & 1) == 1 && arr[i] < arr[i - 1]) {
                return false;
            }
            if ((i & 1) == 0 && arr[i] > arr[i - 1]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static int[] generateArray() {
        int len = (int) (Math.random() * 10) * 2;
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * 100);
        }
        return arr;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5000000; i++) {
            int[] arr = generateArray();
            shuffle(arr);
            if (!isValidWiggle(arr)) {
                System.out.println("ooops!");
                printArray(arr);
                break;
            }
        }
    }

}
