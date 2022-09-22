package com.rukawa.algorithm.leetcode.highfrequency;

/**
 * create by hqh on 2022/3/3
 */
public class Problem_0006_ZigZagConversion {
    /**
     *  Z 字形变换
     *  将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列。
     * 比如输入字符串为 "PAYPALISHIRING" 行数为 3 时，排列如下：
     * P   A   H   N
     * A P L S I I G
     * Y   I   R
     * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："PAHNAPLSIIGYIR"。
     * 请你实现这个将字符串进行指定行数变换的函数：
     * string convert(string s, int numRows);
     *  
     *
     * 示例 1：
     * 输入：s = "PAYPALISHIRING", numRows = 3
     * 输出："PAHNAPLSIIGYIR"
     *
     * 示例 2：
     * 输入：s = "PAYPALISHIRING", numRows = 4
     * 输出："PINALSIGYAHRPI"
     * 解释：
     * P     I    N
     * A   L S  I G
     * Y A   H R
     * P     I
     *
     * 示例 3：
     * 输入：s = "A", numRows = 1
     * 输出："A"
     *
     * 提示：
     * 1 <= s.length <= 1000
     * s 由英文字母（小写和大写）、',' 和 '.' 组成
     * 1 <= numRows <= 1000
     */
    public String convert(String s, int numRows) {
        /**
         * "PAY P ALI S HIR ING", numRows = 3
         * "PAHN APLSIIG YIR"
         *
         * P     A     H     N
         * A  P  L  S  I  I  G
         * Y     I     R
         */
        int len = s.length();
        if (numRows == 1 || numRows >= len) {
            return s;
        }
        StringBuilder[] res = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            res[i] = new StringBuilder();
        }
        int index = 0;
        int row = 0;
        while (index < len) {
            while (index < len && row < numRows) {
                res[row++].append(s.charAt(index++));
            }

            if (index == len) {
                break;
            }

            row = numRows - 2;
            while (index < len && row >= 0) {
                res[row--].append(s.charAt(index++));
            }
            row += 2;
        }

        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            ans.append(res[i]);
        }
        return ans.toString();
    }

    public String convert1(String s, int numRows) {
        /**
         * 设n为字符串s的长度，r=numRows。对于r=1（只有一行或者r≥n（只有一列）的情况，
         * 答案与s相同，我们可以直接返回s。对于其余情况，考虑创建一个二维矩阵，然后在矩阵上按 Z 字形填写字符串s，最后逐行扫描矩阵中的非空字符，组成答案。
         *
         * 根据题意，当我们在矩阵上填写字符时，会向下填写r个字符，然后向右上继续填写r−2个字符，最后回到第一行，
         * 因此 Z 字形变换的周期 t=r+r-2=2r-2，每个周期会占用矩阵上的 1+r-2=r-1 列。
         * 因此我们有[n/t]（最后一个周期视作完整周期），乘上每个周期的列数，得到矩阵的列数 c=[n/t]⋅(r−1)。
         *
         * 创建一个r行c列的矩阵，然后遍历字符串s并按Z字形填写。具体来说，设当前填写的位置为(x,y)，
         * 即矩阵的x行y列。初始 (x,y)=(0,0)，即矩阵左上角。若当前字符下标i满足i%t<r-1，则向下移动，否则向右上移动。
         *
         * 填写完成后，逐行扫描矩阵中的非空字符，组成答案。
         */
        int n = s.length(), r = numRows;
        if (r == 1 || r >= n) {
            return s;
        }
        int t = r * 2 - 2;
        int c = (n + t - 1) / t * (r - 1);
        char[][] mat = new char[r][c];
        for (int i = 0, x = 0, y = 0; i < n; ++i) {
            mat[x][y] = s.charAt(i);
            if (i % t < r - 1) {
                ++x; // 向下移动
            } else {
                --x;
                ++y; // 向右上移动
            }
        }
        StringBuffer ans = new StringBuffer();
        for (char[] row : mat) {
            for (char ch : row) {
                if (ch != 0) {
                    ans.append(ch);
                }
            }
        }
        return ans.toString();
    }
}
