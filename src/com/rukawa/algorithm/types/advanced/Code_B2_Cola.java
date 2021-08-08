package com.rukawa.algorithm.types.advanced;

/**
 * @className: Code_B2_Cola
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/31 0031 19:31
 **/
public class Code_B2_Cola {
    /**
     * 贩卖机只支持硬币支付，且退收都只支持10，50，100三种面额
     * 一次购买只能出一瓶可乐，且投钱和找零都遵循优先使用大钱的原则
     * 需要购买的可乐数量是m，
     * 其中手头拥有的10，50，100的数量分别为a，b，c
     * 可乐的价格是x（x是10的倍数）
     * 请计算出需要投入硬币的次数？
     */

    // 暴力方法，为了验证正式方法
    public static int right(int m, int a, int b, int c, int x) {
        int[] qian = { 100, 50, 10 };
        int[] zhang = { c, b, a };
        int puts = 0;
        while (m != 0) {
            int cur = buy(qian, zhang, x);
            if (cur == -1) {
                return -1;
            }
            puts += cur;
            m--;
        }
        return puts;
    }

    public static int buy(int[] qian, int[] zhang, int rest) {
        int first = -1;
        for (int i = 0; i < 3; i++) {
            if (zhang[i] != 0) {
                first = i;
                break;
            }
        }
        if (first == -1) {
            return -1;
        }
        if (qian[first] >= rest) {
            zhang[first]--;
            giveRest(qian, zhang, first + 1, qian[first] - rest, 1);
            return 1;
        } else {
            zhang[first]--;
            int next = buy(qian, zhang, rest - qian[first]);
            if (next == -1) {
                return -1;
            }
            return 1 + next;
        }
    }


    public static void giveRest(int[] qian, int[] zhang, int i, int oneTimeRest, int times) {
        for (; i < 3; i++) {
            zhang[i] += (oneTimeRest / qian[i]) * times;
            oneTimeRest %= qian[i];
        }
    }

    // 正式的方法
    // m -> 要买的可乐数量
    // a -> 100有多少张
    // b -> 50有多少张
    // c -> 10有多少张
    // 可乐单价x
    public static int putTimes(int m, int a, int b, int c, int x) {
        int[] zhang = {c, b, a};
        int[] money = {100, 50, 10};
        // 总共需要投多少次币
        int puts = 0;
        // 之前面值的钱还剩下多少总钱数
        int preMoneyRest = 0;
        // 之前面值的钱还剩下多少总张数
        int preZhangRest = 0;
        for (int i = 0; i < 3 && m != 0; i++) {
            /**
             * 假设现在到了200元的面值，可乐单价2500，之前的历史所有的钱都算起来2000元的3张，200元的1000张
             * 大面值优先，到了200元，需要把历史给消耗完，然后200元单买可乐
             */
            // 要用之前剩下的钱，当前面值的钱，共同买第一瓶可乐
            // 之前的面值剩下多少钱，是preMoneyRest
            // 之前的面值剩下多少张，是preZhangRest
            // 之前的面值会剩下来，一定是剩下的钱，一直赞不出一瓶可乐的单价
            // 当前的面值付出一些钱+之前剩下的钱，此时有可能凑出一瓶可乐
            // 那么当前面值参与搞定第一瓶可乐，需要掏出多少张？就是curMoneyFirstBuyZhang

            // 可乐单价2500，之前剩余钱数2000，现在到200的面值，搞定一瓶可乐，剩余的500元能被3张200搞定
            // a/x  向上取整 -> (a + (x - 1)) / x
            int curMoneyFirstBuyZhang = (x - preMoneyRest + money[i] - 1) / money[i];

            if (zhang[i] >= curMoneyFirstBuyZhang) {
                giveRest(money, zhang, i + 1, (preMoneyRest + money[i] * curMoneyFirstBuyZhang) - x, 1);
                // 之前剩余的张数+当前面值搞定的张数
                puts += preZhangRest + curMoneyFirstBuyZhang;
                // 当前面值搞定一张剩余多少张
                zhang[i] -= curMoneyFirstBuyZhang;
                // 搞定一张
                m--;
            } else {
                // 可乐单价2500，之前1000剩余1张，500剩余2张，当前200只有一张，此时200搞不定，1000+500*2+200共同成为历史，让下面的面值去搞定
                preMoneyRest += zhang[i] * money[i];
                preZhangRest += zhang[i];
                continue;
            }
            // 单独用一种面值能吃掉多少瓶可乐
            // 凑出第一瓶可乐后，当前的面值有可能继续买更多的可乐
            // 用当前的面值购买一瓶可乐需要多少张
            int curMoneyBuyOneColaZhang = (x + money[i] - 1) / money[i];
            // 用当前面值的钱，一定可以搞定几瓶可乐
            int curMoneyBuyColas = Math.min(zhang[i] / curMoneyBuyOneColaZhang, m);
            // 用当前面值的钱，每搞定一瓶可乐，售货机会吐出多少零钱
            int oneTimeRest = money[i] * curMoneyBuyOneColaZhang - x;
            // 每次买一瓶可乐，吐出的零钱总钱数是oneTimeRest
            // 一共买的可乐数是curMoneyBuyColas，所以把零钱去提升后面几种面值的硬币数
            // 就是giveRest的含义
            giveRest(money, zhang, i + 1, oneTimeRest, curMoneyBuyColas);
            // 当前面值去搞定可乐这件事，一共投了几次币
            puts += curMoneyBuyOneColaZhang * curMoneyBuyColas;
            // 还剩下多少瓶可乐需要去搞定，继续用后面的面值去搞定
            m -= curMoneyBuyColas;
            // 当前面值可能剩下若干张，要参与到后续买可乐的过程中去
            // 所以要更新preMoneyRest和preZhangRest
            // 总张数 - (一瓶可乐搞定的张数 * 总共搞定多少张) = 剩余的张数(搞不定一瓶可乐)
            zhang[i] -= curMoneyBuyOneColaZhang * curMoneyBuyColas;
            preZhangRest = zhang[i];
            preMoneyRest = money[i] * zhang[i];
        }
        // 可乐全部搞定则返回最少投币次数，否则搞不定
        return m == 0 ? puts : -1;
    }

    public static void main(String[] args) {
        int testTime = 1000;
        int zhangMax = 10;
        int colaMax = 10;
        int priceMax = 20;
        System.out.println("如果错误会打印错误数据，否则就是正确");
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int m = (int) (Math.random() * colaMax);
            int a = (int) (Math.random() * zhangMax);
            int b = (int) (Math.random() * zhangMax);
            int c = (int) (Math.random() * zhangMax);
            int x = ((int) (Math.random() * priceMax) + 1) * 10;
            int ans1 = putTimes(m, a, b, c, x);
            int ans2 = right(m, a, b, c, x);
            if (ans1 != ans2) {
                System.out.println("int m = " + m + ";");
                System.out.println("int a = " + a + ";");
                System.out.println("int b = " + b + ";");
                System.out.println("int c = " + c + ";");
                System.out.println("int x = " + x + ";");
                break;
            }
        }
        System.out.println("test end");

    }

}
