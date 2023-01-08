package com.rukawa.algorithm.base.class26;

/**
 * create by hqh on 2023/1/8
 */
public class Code01 {
    /**
     * 32位无符号整数的范围是0～4294967295(0~2^32-1)
     * 现有一个正好包含40亿个无符号整数的文件，
     * 可以使用最多1GB的内存，怎么找到出现次数最多的数？
     */

    /**
     * 一个无符号整数是4Byte，40亿总共有160亿Byte，需要16G的内存，但是现在只有1G的内存
     * 解决方法：
     * 1、使用哈希表
     *  假设数的种类很单一，假设只有1个整数，所以key占用4Byte，value占用4byte，总共8Byte
     *  假设极端情况，每个整数都不一样，需要40亿*8=32G，不如排序
     *  所以Hash表肯定不行
     *
     * 2、1G的内存可以装下多少个记录，大概是：1亿/8=125000000，只能装下1亿条记录，考虑其他的因素，规定一个文件只能装下1000W条记录
     *  现在有40亿个，可以分为400个文件
     *  每个整数hash之后取模，进入到400个文件中的一个，400个文件中的记录不会超过1000W
     *  现在对每个文件取出最大值，然后得到400个文件中最大值，也就是40亿个整数中的最大值
     */

    public static String[] diffStr(int N) {
        String[] ans = new String[N];

        for (int i = 0; i < N; i++) {
            ans[i] = "hanqinghai" + String.valueOf(i) + "zhangxingyue" + String.valueOf(i);
        }
        return ans;
    }

    public static void main(String[] args) {
        int N = 1080;
        String[] strs = diffStr(N);

        int[] count = new int[10];
        for (String str : strs) {
            int hashCode = str.hashCode();
            count[Math.abs(hashCode % 10)]++;
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(i + " : " + count[i]);
        }
    }
}
