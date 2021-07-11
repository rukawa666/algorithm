package com.rukawa.algorithm.types.regularPattern;

/**
 * @className: Code02
 * @description: TODO 类描述 
 * @author: 鎏川疯
 * @date: 2021/7/11 0011
 **/
public class Code02_EatGrass {
    /**
     * 给定一个正整数N，表示有N份青草统一堆放到仓库里
     * 有一只羊和一只牛，牛先吃，羊再吃，它俩轮流吃草
     * 不管是牛还是羊，每一轮能吃的草的数量必须是：
     * 1、4、6、16。。。(4的某次方)
     * 谁最先把草吃完，谁获胜
     * 假设牛和羊都绝顶聪明，都想赢，都会做出理性的决定
     * 根据唯一的参数N，返回谁会赢
     */

    // 暴力方法
    public static String whoWin1(int n) {
        if (n < 5) {
            return n == 0 || n == 2 ? "后手" : "先手";
        }

        // 进到这个过程里来，当前的先手，先选
        // 先选1份草去尝试
        int want = 1;
        while (want <= n) {
            // 如果后续过程中，后手赢了，此时的先手一定赢
            if (whoWin1(n - want).equals("后手")) {
                return "先手";
            }
            // want 可能会溢出
            if (want <= (n / 4)) {
                want *= 4;
            } else {
                break;
            }
        }
        return "后手";
    }


    // 发现规律，后先后先先，五个数为一组显示
    public static String whoWin2(int n) {
        if (n % 5 == 0 || n % 5 == 2) {
            return "后手";
        } else {
            return "先手";
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 50; i++) {
//            System.out.println(i + " : " + whoWin1(i));
            if (!whoWin1(i).equals(whoWin2(i))) {
                System.out.println("Failed...");
            }
        }
    }
}
