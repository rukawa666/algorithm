package com.rukawa.algorithm.types.fibonacci;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/5/26 0026 8:27
 */
public class Code02_ZeroLeftOneStringNumber {

    /**
     * 第一年农场有1只成熟的母牛A，往后的每年：
     * 1、每一只成熟的母牛都会生一直母牛
     * 2、每一只新出生的母牛都在出生的第三年成熟
     * 3、每一只母牛永远不会死
     * 返回N年后牛的数量
     */

    public static int[][] matrixPower(int[][] base, int p) {
        int[][] res = new int[base.length][base[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        int[][] tmp = base;
        for (; p != 0; p >>= 1) {
            if ((p & 1) == 1) {
                res = multiMatrix(res, tmp);
            }
            tmp = multiMatrix(tmp, tmp);
        }
        return res;
    }

    public static int[][] multiMatrix(int[][] m1, int[][] m2) {
        int[][] res = new int[m1.length][m2[0].length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m2[0].length; j++) {
                for (int k = 0; k < m2.length; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }
}
