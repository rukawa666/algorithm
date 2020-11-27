package com.rukawa.algorithm.trainingcamp.trainingcamp4.class7;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-22 7:50
 * @Version：1.0
 */
public class Code01_SDEandPM {
    /**
     * 项目有四个信息：
     * 1、哪个项目经理提的
     * 2、被项目经理润色出来的时间点
     * 3、项目优先级
     * 4、项目花费的时间
     * 项目经理可以提交项目给程序员们，程序员可以做这些项目。
     * 比如长度为4的数组[1,3,2,2]，表示1号项目经理提的，被项目经理润色出来的时间是3，优先级是2，花费程序员2个时间
     * 所以给一个N*4的矩阵，就可以代表这N个项目。
     * 给定一个正数pm，表示项目经理的数量，每个项目经理只负责自己的那些项目，并且一次只能提交一个项目给程序员们，
     * 这个提交的项目做完了，才能再次提交。经理对项目越喜欢，就会越早提交，一个项目优先级越高越被喜欢，花费时间
     * 越少越喜欢；如果还一样，没项目经理润色出来的时间点越早越喜欢。
     * 给定一个正数sde，表示程序员的数量，所有经理提交了的项目，程序员会选自己喜欢的项目做，每个人做完了一个项目，
     * 然后才会再来挑选。当程序员在挑选项目时，有自己喜欢的标准，一个项目花费时间越少越被喜欢，如果花费了同样的
     * 时间，该项目的负责人编号越小越喜欢。
     * 返回一个长度为N的数组，表示N个项目的结束时间
     * 比如：
     *  int pms = 2;
     *  int sde = 2;
     *  int[][] programs = {{1,1,1,2},{1,2,1,1},{1,3,2,2},{2,1,1,2},{2,3,5,5}};
     *  返回：{3,4,5,3,9}
     */
    public static class Program {
        // 项目结束后做完的时间点填在答案数组的什么位置
        public int index;
        // 项目经理编号
        public int pm;
        // 被项目经理润色出来的时间点
        public int start;
        // 项目优先级
        public int rank;
        // 项目花费的时间
        public int cost;

        public Program(int i, int p, int s, int r, int c) {
            this.index = i;
            this.pm = p;
            this.start = s;
            this.rank = r;
            this.cost = c;
        }
    }

    // PM的喜好
    public static class PmLoveRule implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            if (o1.rank != o2.rank) {
                return o1.rank - o2.rank;
            } else if (o1.cost != o2.cost) {
                return o1.cost - o2.cost;
            } else {
                return o1.start - o2.start;
            }
        }
    }

    // 大黑盒
    // 每一个pm，有自己的堆(PmLoveRule)
    // 每一个pm的堆里都有堆顶，所有的堆顶会再组成一个，程序员堆(程序员喜好)
    // void add(...) 项目 pop()
    public static class BigQueues {

        // PriorityQueue<Program> pmQ = pmQueues.get(i);
        // 有多少个项目经理
        private List<PriorityQueue<Program>> pmQueues;
        // 程序员堆(一个，程序员共享池)
        private Program[] sdeHeap;
        // index[i] -> i号pm的堆顶项目，在sde堆中处在什么位置
        // 举例：indexes[1]=3 -> 1号项目经理最喜欢的项目在程序员堆中在3位置
        private int[] indexes;
        // 程序员堆的大小
        private int heapSize;

        public BigQueues(int pmNum) {
            this.heapSize = 0;
            // 大小就是pm的数量，每一个pm只会提供一份项目给程序员堆，最大数量也就是pm的数量
            sdeHeap = new Program[pmNum];
            // pm编号从1开始，0位置不用
            indexes = new int[pmNum + 1];
            // 一开始项目没有提交项目，所以都在-1位置
            for (int i = 0; i <= pmNum; i++) {
                indexes[i] = -1;
            }
            // 有多个个pm，在pmQueues加多少个堆，0位置的堆弃而不用，没有0号项目经理
            pmQueues = new ArrayList<>();
            for (int i = 0; i <= pmNum; i++) {
                pmQueues.add(new PriorityQueue<>(new PmLoveRule()));
            }
        }

        // 当前是否有项目可以被程序员做
        public boolean isEmpty() {
            return heapSize == 0;
        }

        // 某一个项目加入到黑盒中
        public void add(Program program) {
            // 获取是哪个项目的堆
            PriorityQueue<Program> queue = pmQueues.get(program.pm);
            queue.add(program);
            // 有可能当前的项目，成为此时pm最喜欢的项目，换堆顶，调整sde堆中的项目
            // 新加入的项目有可能顶替为该项目经理最喜欢的项目，可能来到堆顶
            Program head = queue.peek();    // 现在的堆顶
            // 该项目经理最喜欢的项目在程序员堆中的哪个位置
            int heapIndex = indexes[head.pm];
            if (heapIndex == -1) {  // 在程序员堆中没有加入自己的项目
                // 把该项目经理提的项目放在程序员堆中末尾
                sdeHeap[heapSize] = head;
                indexes[head.pm] = heapSize;
                // 在程序员堆内部，有可能往上走
                heapInsert(heapSize++);

            } else {    // 在程序员堆中有放入自己的项目
                // 项目经理最喜欢的项目放入
                sdeHeap[heapIndex] = head;
                // 更被程序员喜欢还是讨厌，会发生变化
                // 以下两种情况只会发生一个
                heapInsert(heapIndex);
                heapify(heapIndex);
            }
        }

        // 程序员挑选项目，返回挑选的项目
        public Program pop() {
            // 掉之前用户自己检查是否为空
            // 程序员堆中最喜欢的项目被挑出来被程序员做
            Program head = sdeHeap[0];
            // 属于哪个项目经理的堆
            PriorityQueue<Program> queue = pmQueues.get(head.pm);
            // 去做该项目经理的项目，直接弹出
            queue.poll();
            if (queue.isEmpty()) {  // 此时的pm手上没有项目了
                // 程序员堆顶的项目被做，然后该项目经理没有可以提交的项目，则程序员堆要减小
                swap(0, heapSize - 1);
                sdeHeap[--heapSize] = null;
                // 该项目经理在程序员堆中最喜欢的项目为-1，没有项目了
                indexes[head.pm] = -1;
            } else {    // 如果该项目经理还有项目可以做，则把该项目加入到程序员堆中的第一个位置
                sdeHeap[0] = queue.peek();
            }
            // 加入一个项目或者删除，程序员的喜欢可能会变化
            heapify(0);
            return head;
        }

        private void heapInsert(int index) {
            while (index != 0) {
                int parent = (index - 1) / 2;
                // 该节点和父亲节点根据程序员的喜好，调整堆
                if (sdeLoveRule(sdeHeap[parent], sdeHeap[index]) > 0) {
                    swap(parent, index);
                    index = parent;
                } else {
                    break;
                }
            }
        }

        private void heapify(int index) {
            int left = index * 2 + 1;
            int right = index * 2 + 2;
            int best = index;
            while (left < heapSize) {
                // 如果左孩子节点更被程序员喜欢，则best为左孩子节点
                if (sdeLoveRule(sdeHeap[left], sdeHeap[index]) < 0) {
                    best = left;
                }
                // 如果右孩子节点更被程序员喜欢，则best为右孩子节点
                if (right < heapSize && sdeLoveRule(sdeHeap[right], sdeHeap[index]) < 0) {
                    best = right;
                }
                if (best == index) {
                    break;
                }
                swap(best, index);
                index = best;
                left = index * 2 + 1;
                right = index * 2 + 2;
            }
        }

        private void swap(int index1, int index2) {
            Program p1 = sdeHeap[index1];
            Program p2 = sdeHeap[index2];
            sdeHeap[index1] = p2;
            sdeHeap[index2] = p1;
            indexes[p1.pm] = index2;
            indexes[p2.pm] = index1;
        }

        // 程序员喜好
        // 花费时间越短约好 -> 花费时间相同则项目经理编号越小约好
        private int sdeLoveRule(Program p1, Program p2) {
            if (p1.cost != p2.cost) {
                return p1.cost - p2.cost;
            } else {
                return p1.pm - p2.pm;
            }
        }
    }

    public static class StartRule implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o1.start - o2.start;
        }
    }

    public static int[] workFinish(int pms, int sdes, int[][] programs) {
        // 所有项目根据润色时间，润色时间早的项目排在上面
        // 作用：如果世界线变动，则知道哪些项目可以进黑盒结构，一开始来到0号时间点，所有项目在startQueue中被锁住
        // 被解锁的项目进入黑盒结构
        PriorityQueue<Program>  startQueue = new PriorityQueue<>(new StartRule());
        for (int i = 0; i < programs.length; i++) {
            Program program = new Program(i, programs[i][0], programs[i][1], programs[i][2], programs[i][3]);
            startQueue.add(program);
        }
        // 所有的项目在最开始的时候，在startQueue中被锁住
        // 每个程序员在什么时间点可以做项目
        // 程序员在1时间点领取一个项目去做，该项目所花费的时间是7，所以该程序员在8时间可以选择项目继续去做其他项目
        // 最近唤醒的程序员可以轻松得到，程序员做完活重新扔进去
        PriorityQueue<Integer> sdeWakeQueue = new PriorityQueue<>();
        for (int i = 0; i < sdes; i++) {
            sdeWakeQueue.add(1);
        }

        BigQueues bigQueues = new BigQueues(pms);
        int finish = 0;     // 目前完成项目的数量
        int[] ans = new int[programs.length];
        while (finish != ans.length) {  // 没有得到所有的答案就继续
            // 最早醒来的程序员的时间，也就是总的推进时间点
            int sdeWakeTime = sdeWakeQueue.poll();
            while (!startQueue.isEmpty()) {
                // 项目润色的时间早于唤醒时间，全部加入到黑盒结构
                if (startQueue.peek().start > sdeWakeTime) {
                    break;
                }
                bigQueues.add(startQueue.poll());
            }

            if (bigQueues.isEmpty()) {  // 当前时间点无项目可见
                // 继续睡，睡到startQueue堆顶的润色时间，睡到最早的润色的时间点
                // 如果时间线，堆顶的润色时间为7，则会把唤醒堆中的所有的1时间全部更新为7时间
                sdeWakeQueue.add(startQueue.peek().start);
            } else {    // 当前时间点有项目可做
                Program program = bigQueues.pop();
                // 什么时间点做完？醒来的时间+做该项目花费的时间
                ans[program.index] = sdeWakeTime + program.cost;
                // 唤醒堆中重新加入该程序员做下个项目的开始时间
                sdeWakeQueue.add(ans[program.index]);
                finish++;
            }
        }
        return ans;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    public static void main(String[] args) {
        int pms = 2;
        int sde = 2;
        int[][] programs = { { 1, 1, 1, 2 }, { 1, 2, 1, 1 }, { 1, 3, 2, 2 }, { 2, 1, 1, 2 }, { 2, 3, 5, 5 } };
        int[] ans = workFinish(pms, sde, programs);
        printArray(ans);
    }
}
