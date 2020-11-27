package com.rukawa.algorithm.trainingcamp.trainingcamp4.class5;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-19 7:34
 * @Version：1.0
 */
public class Code04_PalindromeWays {
    /**
     * 对于一个字符串，从前开始读和从后开始读都是一样的，我们就称这个字符串是回文串。例如"ABCBA","AA","A"是回文串，而
     * "ABCD"，"AAB"不是回文串。牛牛特别喜欢回文串，他手中有一个字符串s，牛牛在思考能否从字符串中移除部分(0个或多个)字
     * 符使其变为回文串，并且牛牛认为空串不是回文串。牛牛发现移除的方案可能有很多种，希望你来帮他计算一下一共有多少种移除
     * 方案可以使s变为回文串。对于两种移除方案，如果移除的字符依次构成的序列不一样就是不同的方案。
     * 例如，XXY 4种  ABA 5种
     * 说明：这是今年的原题，提供的说明和例子都很让人费解。现在根据当时的题目的所有测试用例，重新解释当时的题目含义：
     * 1、"1AB23CD21"，你可以选择删除A、B、C、D，然后剩下子序列{1,2,3,2,1}，只要剩下的子序列是同一个，那么只
     * 算1种方法，和A、B、C、D选择什么样的删除顺序没有关系
     * 2、"121A1"，其中有两个{1,2,1}的子序列，第一个{1,2,1}是由{位置0,位置1,位置2}构成，第二个{1,2,1}是由
     * {位置0,位置1,位置4}构成。这两个子序列被认为是不同的子序列。也就是说在本题中，认为字面值一样但是位置不同的
     * 字符就是不同的。
     * 3、其实这道题是想求，str中有多少个不同的子序列，每一种子序列值对应一种删除方法，那就是把多余的东西去掉，而
     * 和去掉的顺序无关
     * 4、也许你觉得我的解释很荒谬，但真的是这样，不然解释不了为什么，XXY 4种 ABA 五种，而且其他的测试用例都印证了
     * 这一点
     */
    public static int way1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int[][] dp = new int[str.length][str.length];
        for (int i = 0; i < str.length; i++) {
            dp[i][i] = 1;
        }
        for (int i = 0; i < str.length - 1; i++) {
            dp[i][i + 1] = str[i] == str[i + 1] ? 3 : 2;
        }
        /**
         * 范围上的尝试模型
         * L...R 有多少种保留方案，是回文子序列
         * 1、L不包含，R不包含  -> a
         * 2、L包含，R不包含   -> b
         * 3、L不包含，R包含   -> c
         * 4、L包含，R包含    -> d
         * 四种情况互斥：
         *   dp[L][R] = ?
         *   dp[L+1][R] 不包含L -> a + c
         *   dp[L][R-1] 不包含R -> a + b
         * 1、dp[L+1][R] + dp[L][R-1] = 2a + b + c
         * 2、先不管d情况，甲 = a+b+c =  dp[L+1][R] + dp[L][R-1] - dp[L+1][R-1]
         * 3、考虑d情况，L位置的字符=R位置的字符，如果不等 ，则dp[L]][R] = 甲
         * 4、现在L位置的字符=R位置的字符，依赖于a,但是如果[L+1,R-1]是空串，加上L和R位置的字符，构成一个新的回文串，所以是a+1
         */
        for (int i = str.length - 3; i >= 0; i--) {
            for (int j = i + 2; j < str.length; j++) {
                dp[i][j] = dp[i + 1][j] + dp[i][j - 1] - dp[i + 1][j - 1];
                if (str[i] == str[j]) {
                    dp[i][j] += dp[i + 1][j - 1] + 1;
                }
            }
        }
        return dp[0][str.length - 1];
    }

    public static int way2(String str) {
        char[] s = str.toCharArray();
        int len = s.length;
        int[][] dp = new int[len + 1][len + 1];
        for (int i = 0; i <= len; i++) {
            dp[i][i] = 1;
        }
        // dp[i][j]，在空串不算回文串的情况下，求str[i..j]有多少不同的回文子序列
        // index -> dp
        // str[index-1]
        // dp 1 str 0
        // dp 2 str 1
        for (int subLen = 2; subLen <= len; subLen++) {
            for (int l = 1; l <= len - subLen + 1; l++) {
                int r = l + subLen - 1;
                dp[l][r] += dp[l + 1][r];
                dp[l][r] += dp[l][r - 1];
                if (s[l - 1] == s[r - 1])
                    dp[l][r] += 1;
                else
                    dp[l][r] -= dp[l + 1][r - 1];
            }
        }
        return dp[1][len];
    }

    public static void main(String[] args) {
        System.out.println(way1("ABA"));
        System.out.println(way2("ABA"));
        System.out.println("-----------------");
        System.out.println(way1("XXY"));
        System.out.println(way2("XXY"));
    }
}
