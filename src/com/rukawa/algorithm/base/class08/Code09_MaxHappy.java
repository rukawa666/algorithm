package com.rukawa.algorithm.base.class08;

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

    public static class Employee {
        public int happy;
        public List<Employee> nextS;

        public Employee(int happy) {
            this.happy = happy;
            nextS = new ArrayList<>();
        }
    }

    public static class Info {
        public int yes; // 头节点在来的情况下，整棵树的最大快乐值
        public int no;  // 头节点在不来的情况下，整棵树的最大快乐值

        public Info(int yes, int no) {
            this.yes = yes;
            this.no = no;
        }
    }

    public static Info process02(Employee x) {
        if (x.nextS.isEmpty()) {
            return new Info(x.happy, 0);
        }

        int yes = x.happy;
        int no = 0;
        for (Employee next : x.nextS) {
            Info nextInfo = process02(next);
            yes += nextInfo.no;
            no += Math.max(nextInfo.yes, nextInfo.no);
        }
        return new Info(yes, no);
    }


}
