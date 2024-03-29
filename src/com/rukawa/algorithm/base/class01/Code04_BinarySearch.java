package com.rukawa.algorithm.base.class01;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-17 15:52
 * @Version：1.0
 */
public class Code04_BinarySearch {
    /**
     * lower_bound(x):查找第一个大于等于key元素的下标
     * while (l < r)
     *     int m = (l + r) >>> 1;
     *     if (array[m] >= target) {
     *         r = m;
     *     } else {
     *         l = m + 1;
     *     }
     * }
     * return l;
     *
     * upper_bound(x):查找第一个大于Key元素的下标
     * while(l < r)
     *     int m = (l + r) >>> 1;
     *     if (array[m] > target) {
     *         r = m;
     *     } else {
     *         l = m + 1;
     *     }
     * }
     * return l;
     * @param args
     */

    /**
     * 模板1-1：查找第一个=target的位置
     * 在左闭右开的区间[l,r}
     * @param nums
     * @param target
     * @return
     */
    public static int binarySearch0101(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        while (l <= r) {
            int m = l + ((r - l) >> 1);
            if (nums[m] == target) {
                return m;
            } else if (nums[m] > target) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return -1;
    }

    /**
     * 模板1-2：查找第一个=target的位置
     * @param nums
     * @param target
     * @return
     */
    public static int binarySearch0102(int[] nums, int target) {
        int l = 0;
        int r = nums.length;
        while (l < r) {
            int m = l + ((r - l) >> 1);
            if (nums[m] == target) {
                return m;
            } else if (nums[m] > target) {
                r = m;
            } else {
                l = m + 1;
            }
        }
        return -1;
    }

    /**
     * 模板2-1：查找第一个>=target的位置
     * @param nums
     * @param target
     * @return
     */
    public static int binarySearch0201(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        while (l <= r) {
            int m = l + ((r - l) >> 1);
            if (nums[m] >= target) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return l;
    }

    /**
     * 模板2-2：查找第一个>=target的位置
     * @param nums
     * @param target
     * @return
     */
    public static int binarySearch0202(int[] nums, int target) {
        int l = 0;
        int r = nums.length;
        while (l < r) {
            int m = l + ((r - l) >> 1);
            if (nums[m] >= target) {
                r = m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }

    /**
     * 模板3-1：查找第一个>target的位置
     * @param nums
     * @param target
     * @return
     */
    public static int binarySearch0301(int[] nums, int target) {
        int l = 0;
        int r = nums.length -1;
        while (l <= r) {
            int m = l + ((r - l) >> 1);
            if (nums[m] > target) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return l;
    }

    /**
     * 模板3-2：查找第一个>target的位置
     * @param nums
     * @param target
     * @return
     */
    public static int binarySearch0302(int[] nums, int target) {
        int l = 0;
        int r = nums.length;
        while (l < r) {
            int m = l + ((r - l) >> 1);
            if (nums[m] > target) {
                r = m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }

    /**
     * 模板4-1：查找最后一个=target的位置
     * @param nums
     * @param target
     * @return
     */
    public static int binarySearch0401(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        while (l <= r) {
            int m = l + ((r - l) >> 1);
            if (nums[m] <= target) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        if (r >= 0 && nums[r] == target) {
            return r;
        }
        return -1;
    }

    /**
     * 模板5-1：查找最后一个<=target的位置
     * @param nums
     * @param target
     * @return
     */
    public static int binarySearch0501(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        while (l <= r) {
            int m = l + ((r - l) >> 1);
            if (nums[m] <= target) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return r;
    }

    /**
     * 模板6-1：查找最后一个<target的位置
     * @param nums
     * @param target
     * @return
     */
    public static int binarySearch0601(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        while (l <= r) {
            int m = l + ((r - l) >> 1);
            if (nums[m] < target) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return r;
    }
}
