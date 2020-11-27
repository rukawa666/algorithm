package com.rukawa.algorithm.other.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-05-06 8:06
 * @Version：1.0
 */
public class QuickSort {

    /**
     *
     * 基本思想：挖坑填数+分治法。首先选择一个轴值(pivot,也叫基准值)，通过一趟排序将待排记录分割成独立的两部分，其中一部分记录的
     *          关键字均比另一部分的关键字小，则可分别对这两部分记录继续进行排序，以达到整个序列有序
     *
     * 算法的运作：采用"分而治之、各个击破"。快速排序使用分治法(Divide and conquer)策略把一个序列(list)分成两个子序列(sub-lists)。
     *
     * 步骤：
     *     1、从数列中挑出一个元素，称为"基准"
     *     2、重新排序数列，所有比基准值小的元素摆放在基准前面，所有比基准值大的元素摆在基准后面(相同的数可以放在任意一边)。在这个
     *        分区结束后，该基准就处于数列的中间位置。这个称为分区(partition)操作。
     *     3、递归地(recursively)把小于基准值元素的子数列和大于基准值元素的子数列排序
     *        递归到最底部时，数列的大小是零或一，也就是已经排好序。这个算法一定会结束，因为在每次的迭代中，它至少会把一个元素摆到
     *        它最后的位置去
     *
     */

    /**
     * 快速排序：
     *     基本思想：也采用了分治的思想
     *     实现：把原始的数组筛选成较小或者较大的两个子数组，然后递归地排序两个子数组
     *
     *     时间复杂度：
     *     算法分析：
     *         1、最优的情况：被选出来的基准值是当前子数组的中间数
     *            这样分割，能保证对于一个规模大小为n的问题，能被均匀分解成两个规模大小为n/2的子问题(归并排序也采用了
     *            相同的划分方法)，时间复杂度是:T(n)=2*T(n/2) + O(n)
     *            把规模大小为n的问题分解成n/2的两个子问题时，和基准值进行了n-1次比较，复杂度就是O(n)。很显然，在最优情况
     *            下，快速排序的复杂度也是O(nlogn)
     *         2、最坏情况：基准值选择了子数组里的最大或者最小值
     *            每次都把子数组分成了两个更小的子数组，其中一个的长度为1，另外一个的长度只比原子数组少1
     *            和冒泡排序的过程类似，算法复杂度为O(n²)
     *            提示，可以通过随机的选取基准值来避免出现最坏的情况
     *
     *       空间复杂度：
     *       和归并排序不同，快速排序在每次递归的过程中，只需要开辟O(1)的存储空间来完成交换操作实现直接对数组的修改，又因为
     *       递归次数为logn，所以它的整体空间复杂度完全取决于压堆栈的次数，因此它的空间复杂度是O(logn)
     *
     */

    private static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        int[] nums = {2, 1, 7, 8, 5, 4, 9, 3, 6};
        sort(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));
    }

    private static void sort(int[] nums, int l, int r) {
        if (l >= r) return;  // 判断是否只剩下一个元素

        // 利用partition找到一个随机的基准点
        int p = partition(nums, l, r);

        // 递归地对基准点左半边和右半边的数进行排序
        sort(nums, l, p - 1);
        sort(nums, p + 1, r);
    }

    private static int partition(int[] nums, int l, int r) {
        swap(nums, randRange(l, r), r);

        int i, j;
        // 从左到右用每个数和基准值比较，若比基准值小，
        for (i = l,j = l; j < r; j++) {
            if (nums[j] >= nums[r]) {
                swap(nums, i++, j);
            }
        }
        // 末尾的基准值放置到指针i的位置，i指针之后的数
        swap(nums, i , j);
        // 返回指针i，作为基准点的位置
        return i;
    }

    private static void swap(int[] nums, int m, int n) {
        if (nums[n] > nums[m]) {
            nums[m] = nums[m] ^ nums[n];
            nums[n] = nums[m] ^ nums[n];
            nums[m] = nums[m] ^ nums[n];
        }
    }

    private static int randRange(int m, int n) {
        int randomIndex = m + 1 + random.nextInt(n - m);
        return randomIndex;
    }
}

