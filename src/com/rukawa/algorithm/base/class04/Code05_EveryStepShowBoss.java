package com.rukawa.algorithm.base.class04;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-20 21:20
 * @Version：1.0
 */
public class Code05_EveryStepShowBoss {
    /**
     * 给定一个整型数组，int[] arr; 和一个布尔类型数组，boolean[] op，两个数组一定等长，
     * 假设长度为N，arr[i]代表客户编号，op[i]代表客户操作
     * arr = [3, 3, 1, 2, 1, 2, 5, ...],
     * op = [T, T, T, T, F, T, F, ...]
     * 依次表示：3用户购买了一件商品，3用户购买了一件商品，1用户购买了一件商品，2用户购买了一件商品，
     * 1用户退货了一件商品，2用户购买了一件商品，5用户退货了一件商品。。。
     * 一对arr[i]和op[i]就代表一个事件：
     * 用户号为arr[i],op[i] == T 代表这个用户购买了一件商品，op[i] == F就代表这个用户退货了一件商品
     * 现在你作为电商平台负责人，你想在每一个事件到来的时候，都给购买次数最多的前K名用户颁奖。
     * 所以每个事件发生后，你都需要一个得奖名单(得奖区)
     *
     * 得奖系统的规则：
     *  1、如果某个用户购买商品数为0，但是又发生了退货事件，则认为该事件无效，得奖名单和之前事件时一致，比如例子中的5用户
     *  2、某用户发生购买商品事件，购买商品数+1，发生退货事件，购买商品数-1
     *  3、每次都是最多k个用户得奖，k也为传入的参数，如果根据全部规则，得奖人数确实不够k个，那就以够的情况输出结果
     *  4、得奖系统分为得奖区和候选区，任何用户只要购买数>0，一定在这两个区域中的一个
     *  5、购买数最大的前k个用户进入得奖区，在最初时如果得奖区没有达到k个用户，那么新来的用户直接进入得奖区
     *  6、如果购买数不足以进入得奖区的用户，进入候选区
     *  7、如果候选区购买数最多的用户，已经足以进入得奖区，该用户就会替换得奖区中购买数最少的用户(大于才能替换)
     *     如果得奖区中购买数最少的用户有多个，就替换掉最早进入得奖区的用户
     *     如果候选区购买数最多的用户有多个，机会会给最早进入候选区的用户
     *  8、候选区和得奖区是两套时间
     *     因用户只会在其中一个区域，所以只会有一个区域的时间，
     *     从得奖区出来进入候选区的用户，得奖区时间删除，进入候选区的时间就是当前事件的时间(可以理解为arr[i]和op[i]中的i)
     *     从候选去出来进入得奖区的用户，候选区时间删除，进入得奖区的时间就是当前事件的时间(可以理解为arr[i]和op[i]中的i)
     *  9、如果某用户购买数==0，不管在哪个区域都离开，区域时间删除，离开是指彻底离开，哪个区域也不会找到该用户
     *     如果下次该用户又发生购买行为，产生>0的购买数，会再次根据之前规则回到某个区域中，进入区域的时间重记
     */

    public static class Customer {
        // 用户编号
        public int id;
        // 买的商品数量
        public int buy;
        // 进入得奖区或者候选区的时间
        public int enterTime;

        public Customer(int id, int buy, int enterTime) {
            this.id = id;
            this.buy = buy;
            this.enterTime = enterTime;
        }
    }

    // 较大的购买数排前面，否则最早的客户最前
    public static class CandidateComparator implements Comparator<Customer> {

        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buy != o2.buy ? (o2.buy - o1.buy) : (o1.enterTime - o2.enterTime);
        }
    }


    // 买的少的排前面，否则最早的排前面
    public static class AwardedComparator implements Comparator<Customer> {

        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buy != o2.buy ? (o1.buy - o2.buy) : (o1.enterTime - o2.enterTime);
        }
    }


    // 加强堆实现
    public static class WhosoeverYourDaddy {
        private HashMap<Integer, Customer> customers;
        private HeapGreater<Customer> candidateHeap;
        private HeapGreater<Customer> daddyHeap;
        private final int daddyLimit;

        public WhosoeverYourDaddy(int limit) {
            customers = new HashMap<>();
            candidateHeap = new HeapGreater<>(new CandidateComparator());
            daddyHeap = new HeapGreater<>(new AwardedComparator());
            daddyLimit = limit;
        }


        // O(N*(logN + logK + K))
        public void operate(int time, int number, boolean burOrRefund) {
            if (!burOrRefund && !customers.containsKey(number)) {
                return;
            }

            if (!customers.containsKey(number)) {
                customers.put(number, new Customer(number, 0, 0));
            }

            Customer c = customers.get(number);
            if (burOrRefund) {
                c.buy++;
            } else {
                c.buy--;
            }

            if (c.buy == 0) {
                customers.remove(number);
            }

            // 不在候选区和得奖区，新用户有买入
            if (!candidateHeap.contains(c) && !daddyHeap.contains(c)) {
                if (daddyHeap.size() < daddyLimit) {
                    c.enterTime = time;
                    daddyHeap.push(c);
                } else {
                    c.enterTime = time;
                    candidateHeap.push(c);
                }
            } else if (candidateHeap.contains(c)) {  // 候选区
                if (c.buy == 0) {   // 老用户购买数量为0，候选区剔除用户
                    candidateHeap.remove(c);
                } else {
                    candidateHeap.resign(c);    // 购买商品数量发生变化，堆调整
                }
            } else {    // 得奖区
                if (c.buy == 0) {
                    daddyHeap.remove(c);    // 老用户购买数量为0，候选区剔除用户
                } else {
                    daddyHeap.resign(c); // 购买商品数量发生变化，堆调整
                }
            }
            // 调整候选区和得奖区
            daddyMove(time);
        }

        // 获奖区的用户
        public List<Integer> getDaddies() {
            List<Customer> allElements = daddyHeap.getAllElements();
            List<Integer> res = new ArrayList<>();
            for (Customer element : allElements) {
                res.add(element.id);
            }
            return res;
        }

        private void daddyMove(int time) {
            if (candidateHeap.isEmpty()) {
                return;
            }
            if (daddyHeap.size() < daddyLimit) {
                Customer c = candidateHeap.pop();
                c.enterTime = time;
                daddyHeap.push(c);
            } else {
                if (candidateHeap.peek().buy > daddyHeap.peek().buy) {
                    Customer oldDaddy = daddyHeap.pop();
                    Customer newDaddy = candidateHeap.pop();
                    oldDaddy.enterTime = time;
                    newDaddy.enterTime = time;
                    candidateHeap.push(oldDaddy);
                    daddyHeap.push(newDaddy);
                }
            }
        }
    }

    // 优化方法，使用加强堆
    public static List<List<Integer>> topK(int[] arr, boolean[] op, int k) {
        // 答案数组
        List<List<Integer>> res = new ArrayList<>();
        WhosoeverYourDaddy daddy = new WhosoeverYourDaddy(k);
        for (int i = 0; i < arr.length; i++) {

            res.add(daddy.getDaddies());
        }
        return res;
    }

    // 暴力方法 即对数器
    // 干完所有的事，模拟，不优化
    // 每个时间点的获奖用户
    // O(N^2 * log)
    public static List<List<Integer>> compare(int[] arr, boolean[] op, int k) {
        // 用户id对应的Customer
        HashMap<Integer, Customer> map = new HashMap<>();
        // 候选区数组
        ArrayList<Customer> candidate = new ArrayList<>();
        // 得奖区数组
        ArrayList<Customer> awarded = new ArrayList<>();
        // 答案数组
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            int id = arr[i];
            boolean buyOrRefund = op[i];
            // 规则1：退货行为 且 购买商品为0(没有这个用户)，认为无效事件
            if (!buyOrRefund && !map.containsKey(id)) {
                res.add(getCurRes(awarded));
                continue;
            }
            /**
             * 如果不是无效事件，有以下三种情况
             * 1、无这个用户(购买数等于0)，买货行为
             * 2、有这个用户(购买数大于0)，买货行为
             * 3、有这个用户(购买数大于0)，退货行为
             */
            if (!map.containsKey(id)) {
                map.put(id, new Customer(id, 0, 0));
            }

            Customer c = map.get(id);
            if (buyOrRefund) {
                c.buy++;
            } else {
                c.buy--;
            }
            // 规则9：如果有这个用户，此时退货事件，无购买数，剔除当前用户
            if (c.buy == 0) {
                map.remove(id);
            }

            // 新来的用户有购买行为
            if (!candidate.contains(c) && !awarded.contains(c)) {
                if (awarded.size() < k) {
                    c.enterTime = i;
                    awarded.add(c);
                } else {
                    c.enterTime = i;
                    candidate.add(c);
                }
            }

            cleanZeroBuy(candidate);
            cleanZeroBuy(awarded);
            candidate.sort(new CandidateComparator());
            awarded.sort(new AwardedComparator());
            move(candidate, awarded, k, i);
            res.add(getCurRes(awarded));
        }
        return res;
    }

    public static void move(ArrayList<Customer> candidate, ArrayList<Customer> awarded, int k, int time) {
        if (candidate.isEmpty()) {
            return;
        }
        if (awarded.size() < k) {
            Customer c = candidate.get(0);
            c.enterTime = time;
            awarded.add(c);
            candidate.remove(0);
        } else {
            if (candidate.get(0).buy > awarded.get(0).buy) {
                Customer oldAwarded = awarded.get(0);
                awarded.remove(0);
                Customer newAwarded = candidate.get(0);
                candidate.remove(0);
                oldAwarded.enterTime = time;
                newAwarded.enterTime = time;

                awarded.add(newAwarded);
                candidate.add(oldAwarded);
            }
        }
    }

    public static List<Integer> getCurRes(ArrayList<Customer> list) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            res.add(list.get(i).id);
        }
        return res;
    }

    public static void cleanZeroBuy(ArrayList<Customer> list) {
        List<Customer> noZero = new ArrayList<>();
        for (Customer c : list) {
            if (c.buy == 0) {
                continue;
            }
            noZero.add(c);
        }
        list.clear();
        for (Customer c : noZero) {
            list.add(c);
        }
    }
}
