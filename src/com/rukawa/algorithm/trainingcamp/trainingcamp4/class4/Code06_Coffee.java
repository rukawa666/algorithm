package com.rukawa.algorithm.trainingcamp.trainingcamp4.class4;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-14 23:11
 * @Version：1.0
 */
public class Code06_Coffee {
    /**
     * 数组arr代表每一个咖啡机冲一杯咖啡的时间，每个咖啡机只能串行的制造咖啡。
     * 现在有n个人需要喝咖啡，只能用咖啡机来制造咖啡。认为每个人喝咖啡的时间
     * 非常短，冲好的时间即是喝完的时间。每个人喝完咖啡之后咖啡杯可以选择洗或者
     * 选择自然挥发干净，只有一台洗咖啡机杯的机器，只能串行的洗咖啡杯。
     * 洗杯子的机器洗完一个杯子的时间为a，任何一个杯子自然挥发干净的时间为b。
     * 四个参数：arr, n, a, b
     * 假设时间点从0开始，返回所有人喝完咖啡并洗完咖啡杯的全部过程结束后，至少来到什么时间点
     */

    /**
     * 给定一个数组，代表每一个coffee的工作时间，每台咖啡机造完一杯才能去做下一杯，假设
     * 有M个人，都在0号时间点开始排队，返回一个数组，表示M个人得到咖啡的最好时间
     * @param arr
     * @param M
     * @return
     */
    public static int[] bestChoices(int[] arr, int M) {
        int[] res = new int[arr.length];
        PriorityQueue<CoffeeMachine> heap = new PriorityQueue<>();
        for (int coffeeWork : arr) {
            heap.add(new CoffeeMachine(0, coffeeWork));
        }

        for (int i = 0; i < M; i++) {
            CoffeeMachine cur = heap.poll();
            res[i] = cur.stat + cur.work;
            cur.stat = res[i];
            heap.add(cur);
        }
        return res;
    }

    public static class CoffeeMachine {
        public int stat;
        public int work;

        public CoffeeMachine(int stat, int work) {
            this.stat = stat;
            this.work = work;
        }
    }

    public static class CoffeeMachineComparator implements Comparator<CoffeeMachine> {

        @Override
        public int compare(CoffeeMachine o1, CoffeeMachine o2) {
            return (o1.stat + o1.work) - (o2.stat + o2.work);
        }
    }
}
