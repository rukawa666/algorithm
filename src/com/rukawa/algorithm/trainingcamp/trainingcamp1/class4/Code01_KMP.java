package com.rukawa.algorithm.trainingcamp.trainingcamp1.class4;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-02 22:15
 * @Version：1.0
 */
public class Code01_KMP {

    public static int getIndexOf(String s, String m) {
        if (s == null || m == null || m.length() < 1 || s.length() < m.length()) {
            return -1;
        }

        char[] str = s.toCharArray();
        char[] match = m.toCharArray();
        int x = 0;  // str中当前比对到的位置
        int y = 0;  // match中当前比对到的位置
        // M  M <= N O(N)
        int[] next = getNextArray(match);   // next[i] match中i之前的字符串match[0...i-1]
        // O(N)
        /**
         *                          x(N)   x-y(N)
         * 分支1：x++，y++,此时         升    不变  N
         * 分支2：x++，此时             升    升   2N
         * 分支3：y=next[y],往前跳，此时 不变   升   N
         * 所以最高时间复杂度为O(2N) -> O(N)
         */
        while (x < str.length && y < match.length) {
            if (str[x] == match[y]) {
                x++;
                y++;
            } else if (next[y] == -1) { // 等效于 y == 0,在match中到0位置了还没有比对出来，则x跳到下一个比对
                x++;
            } else {    // 还能跳，y往前跳到next[y]的位置
                y = next[y];
            }
        }
        // 如果x没有越界，y越界，则表示当前y全部匹配成功
        // 如果x越界，但是y没越界，则返回-1
        // 如果x越界，y越界，等于匹配成功
        return y == match.length ? x - y : -1;
    }

    /**
     * 此时时间复杂度为O(M)
     *                              i(M)   i-cn(M)
     * 分支1：i++，++cn,此时           升     不变  M
     * 分支2：cn=next[cn]，前跳，此时   不变    升   M
     * 分支3：i++，此时                升     升   2M
     * 所以最高时间复杂度为O(2M) -> O(M)
     */
    public static int[] getNextArray(char[] match) {
        if (match.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[match.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2;
        int cn = 0; // 1、i-1的情况下，最长前缀和最长后缀的长度 2、当前是哪个字符和i-1的字符比较
        while (i < next.length) {
            if (match[i - 1] == match[cn]) {    // 跳出来的时候
                // next[i] = cn + 1;
                // i++;
                // cn++; 此时i+1依赖于i，此时i=cn+1，直接写做cn++
                // 等效于下面的一句
                next[i++] = ++cn;
            } else if (cn > 0) {    // 跳失败了，往前跳，来到next[cn]值的位置
                cn = next[cn];
            } else {    // 跳到开头，还没相等，next就是0
                next[i++] = 0;
            }
        }
        return next;
    }
}
