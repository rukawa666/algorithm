package com.rukawa.algorithm.other.sort;

import java.util.Arrays;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-05-03 20:39
 * @Version：1.0
 */
public class MergeSort {

    public static void main(String[] args) {

        int[] nums01 = {2, 1, 7, 8, 5, 4};
        sort(nums01, 0, nums01.length - 1);
        System.out.println(Arrays.toString(nums01));
    }

    /**
     * 归并排序
     *     核心：分治，就是把一个复杂的问题分成两个或者多个相同或者相似的子问题，然后把子问题分成更小的子问题，
     *          直到子问题可以简单的直接求解，最原问题的解就是子问题解的合并。
     *
     *     实现：一开始先把数组从中间划分成两个子数组，一直递归地把子数组划分成更小的子数组，直到子数组里面只有一个元素，
     *           才开始排序
     *
     *           排序的方法就是按照大小顺序合并两个元素，接着依次按照递归的返回顺序，不断地合并排好序的子数组，直到
     *           最后把整个数组的顺序排好
     *
     *      空间复杂度：由于合并n个元素需要分配一个大小为n的额外数组，合并完成之后，这个数组的空间就会被释放，所以算法
     *                 的空间复杂度就是O(n).
     *      时间复杂度：每一层的合并复杂度都是O(n)，整体的复杂度就是O(nlogn)
     *
     *      建议：归并排序的思想很重要，其中对两个有序数组合并的操作，在很多面试题中都有用到。
     */

    /**
     * 普通归并排序
     * @param nums
     */
    public static void sort(int[] nums, int l, int r) {
        if (l >= r) {
            return;
        }
        int m = l + (r - l) / 2;
        // 二分递归将左右的两个部分排序
        sort(nums, l, m);
        sort(nums, m + 1, r);
        // 在归并前判断一下，如果左边的最大比右边的最小的还小(或者等于)，那就不用归并了，已经有序了
        if (nums[m] <= nums[m + 1]) {
            return;
        }
        merge(nums, l, m , r);
    }

    public static void merge(int[] nums, int l, int m, int r) {
        int[] t = nums.clone();
        // 定义左右两半的其实指针分别是i，j，将两个区间归并为[l ... m]，[m+1 ... h]
        int k = l, i = l , j = m + 1;

        while (k <= r) {                // 从数组的起始到结束位置
            if (i > m) {                // i > m 越出左半边的区域
                nums[k++] = t[j++];     // 左半边的数处理完毕，只剩下右半边的数，只需要将右半边的数逐个拷贝过去
            } else if (j > r) {         // j > h 越出右半边的区域
                nums[k++] = t[i++];     // 右半边的数都处理完毕，只剩下左半边的数，只需要将左半边的数逐个拷贝过去
            } else if (t[j] < t[i]) {   // 右边数小于左边数
                nums[k++] = t[j++];     // 右边的数小于左边的数，将右边的数拷贝到合适的位置，j指针往前移动一位
            } else {                    // 左边数小于右边数
                nums[k++] = t[i++];     // 左边的数小于右边的数，将左边的数拷贝刀合适的位置，i指针向前移动一位
            }
        }
    }

    /**
     * 优化版本
     * @param nums
     * @param l
     * @param r
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void finalSort(T[] nums, int l, int r) {
        if (l >= r) return;
        if (r - l < 7) {
            insertionSort(nums, l, r);
            return;
        }

        int m = l + (r - l) / 2;
        finalSort(nums, l, m);
        finalSort(nums, m + 1, r);

        // 在归并前判断一下，如果左边的最大比右边的最小的还小(或者等于)，那就不用归并了，已经有序了
        if (nums[m].compareTo(nums[m + 1]) <= 0) {
            return;
        }
        merge(nums, l, m, r);
    }

    /**
     * 当规模小的时候对数组进行插入排序
     * @param nums
     * @param l
     * @param r
     * @param <T>
     */
    private static <T extends Comparable<? super T>> void insertionSort(T[] nums, int l, int r) {
        T t;
        for (int i = l, j; i < nums.length; i++) {
            t = nums[i];
            for (j = i - 1; j >= 0 && t.compareTo(nums[j]) < 0; j--) {
                nums[j + 1] = nums[j];
            }
            nums[j + 1] =  t;
        }
    }

    /**
     * 第二次优化：
     * 利用插入排序进行优化
     *     当递归到规模足够小时，利用插入排序
     * @param nums  待排序的数组
     * @param l 归并的左边界
     * @param m 归并的中间位置，也就是分界线
     * @param r 归并的右边界
     * @param <T>   泛型
     */
    private static <T extends Comparable<? super T>> void merge(T[] nums, int l, int m, int r) {
//        T[] tmp = Arrays.copyOfRange(nums, l, r);
//        T[] tmp = nums.clone();
        T[] tmp = (T[]) new Comparable[nums.length];
        System.arraycopy(nums, 0, tmp, 0 , nums.length);

        int i = l, k = l;  // 左半边的起始指针, 临时指针起始位置
        int j = m + 1;  // 右半边的起始指针

        while (k <= r) {
            if (i > m) {
                nums[k++] = tmp[j++];
            } else if (j > r) {
                nums[k++] = tmp[i++];
            } else if (tmp[j].compareTo(tmp[i]) < 0) {
                nums[k++] = tmp[j++];
            } else {
                nums[k++] = tmp[i++];
            }
        }
    }
}
