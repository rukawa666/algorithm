package com.rukawa.algorithm.base.class08_tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-07-21 9:02
 * @Version：1.0
 */
public class Code09_MaxHappy {

    /**
     * 有一个多叉树代表一个公司的层级结构
     * 第一个原则：给每一个员工发请柬，发到的人才能来聚会
     * 第二个原则：选择节点不能选直接上下级
     * 第三个原则：发完请柬后组成的聚会，得到的happy值最大
     */

    /**
     * 分析：以x为头的多叉树，获得的最大快乐值
     *  1.包含x节点
     *      不包含x的下一层节点的快乐值的情况下，多叉子树的最大快乐值
     *  2.不包含x节点
     *      整棵子树的最大快乐值
     *  最终获得两种情况最大的值
     * 最终要的信息是头节点来的情况下最大快乐值，头节点不来的情况下最大快乐值
     */
    public static class Employee {
        public int happy;
        public List<Employee> nextS;

        public Employee(int happy) {
            this.happy = happy;
            nextS = new ArrayList<>();
        }
    }

    public static int maxHappy1(Employee employee) {
        Info info = process(employee);
        return Math.max(info.yes, info.no);
    }


    public static Info process(Employee x) {
        if (x == null) {
            return new Info(0, 0);
        }
        // x来的情况下最大的快乐值
        int yes = x.happy;
        // x不来的时候最大的快乐值
        int no = 0;
        for (Employee next : x.nextS) {
            Info nextInfo = process(next);
            // x来，下一层孩子节点必须不能来
            yes += nextInfo.no;
            // x不来，后代节点可以来也可以不来
            no += Math.max(nextInfo.yes, nextInfo.no);
        }
        return new Info(yes, no);
    }

    public static class Info {
        public int yes; // 头节点在来的情况下，整棵树的最大快乐值
        public int no;  // 头节点在不来的情况下，整棵树的最大快乐值

        public Info(int yes, int no) {
            this.yes = yes;
            this.no = no;
        }
    }

    public static int maxHappy2(Employee boss) {
        if (boss == null) {
            return 0;
        }
        return process2(boss, false);
    }

    public static int process2(Employee cur, boolean up) {
        if (up) {
            int res = 0;
            for (Employee next : cur.nextS) {
                res += process2(next, false);
            }
            return res;
        } else {
            int p1 = cur.happy;
            int p2 = 0;
            for (Employee next : cur.nextS) {
                p1 += process2(next, true);
                p2 += process2(next, false);
            }
            return Math.max(p1, p2);
        }
    }

    // for test
    public static Employee generateBoss(int maxLevel, int maxNextS, int maxHappy) {
        if (Math.random() < 0.2) {
            return null;
        }
        Employee boss = new Employee((int) (Math.random() * (maxHappy + 1)));
        generateNextS(boss, 1, maxLevel, maxNextS, maxHappy);
        return boss;
    }

    public static void generateNextS(Employee boss, int level, int maxLevel, int maxNestS, int maxHappy) {
        if (level > maxLevel) {
            return;
        }
        int nextSize = (int) (Math.random() * (maxNestS + 1));
        for (int i = 0; i < nextSize; i++) {
            Employee next = new Employee((int) (Math.random() * (maxHappy + 1)));
            boss.nextS.add(next);
            generateNextS(next, level + 1, maxLevel, maxNestS, maxHappy);
        }
    }


    public static void main(String[] args) {
        int maxLevel = 4;
        int maxNextS = 7;
        int maxHappy = 100;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            Employee boss = generateBoss(maxLevel, maxNextS, maxHappy);
            if (maxHappy1(boss) != maxHappy2(boss)) {
                System.out.println("Ops!");
            }
        }
        System.out.println("Finish!");
    }

}
