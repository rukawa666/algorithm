package com.rukawa.algorithm.trainingcamp.trainingcamp3.class1;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-18 7:58
 * @Version：1.0
 */
public class Code02_NeedParentheses {
    /**
     * 括号有效配对是指：
     * 1、任何一个左括号都能找到和其正确配对的右括号
     * 2、任何一个右括号都能找到和其正确配对的左括号
     * 有效的：(()) ()() (()()) 等
     * 无效的：(() )( 等
     * 问题1：怎么判断一个括号字符串有效？
     * 问题2：如果一个括号字符串无效，返回至少填几个字符能让其有效
     */
    public static boolean valid(String s) {
        char[] str = s.toCharArray();
        int count = 0;
        for (int i = 0; i < str.length; i++) {
            count += str[i] == '(' ? 1 : -1;
            if (count < 0) {
                return false;
            }
        }
        return count == 0;
    }

    public static int needParentheses(String s) {
        char[] str = s.toCharArray();
        int count = 0;
        int need = 0;
        for (int i = 0; i < str.length; i++) {
            if (str[i] == 'C') {
                count++;
            } else {    // 如果是")"括号
                if (count == 0) {   // 此时是")"，count即将要变为-1，此时不需要count--，直接need++
                    need++;
                } else {
                    count--;
                }
            }
        }
        return need + count;
    }
}
