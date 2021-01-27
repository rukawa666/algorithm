package com.rukawa.algorithm.types.bst;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-27 9:15
 * @Version：1.0
 */
public class Code_009_MaxHappy {

    /**
     * 派对的最大快乐值
     *  公司的每个员工都符合Employee类的描述。整个公司的人员结构可以看作是一棵标准的。没有环的多叉树。树的头节点是公司唯一的老板。
     *  除老板之外的每个员工都有唯一的直接上级。叶节点是没有任何下属的基层员工，除基层员工之外，每个员工都有一个或多个直接下级。
     *  这个公司现在要办party，你可以决定哪些员工来，哪些员工不来，规则：
     *  1、如果某个员工来了，那么这个员工所有的直接下级都不能来
     *  2、派对的整体快乐值是所有到场员工快乐值的累加
     *  3、你的目标是让派对的整体快乐值尽量大。
     *  给定一棵多叉树的头节点boss，请返回派对的最大快乐值。
     */

    public static class Employee {
        public int happy;
        public List<Employee> nextS;

        public Employee(int happy) {
            this.happy = happy;
            this.nextS = new ArrayList<>();
        }
    }

    public static int maxHappy(Employee head) {
        Info process = process(head);
        return Math.max(process.no, process.yes);
    }

    public static class Info {
        public int no;
        public int yes;

        public Info(int no, int yes) {
            this.no = no;
            this.yes = yes;
        }
    }

    public static Info process(Employee x) {
        if (x == null) {
            return new Info(0, 0);
        }
        int no = 0;
        int yes = x.happy;
        for (Employee next : x.nextS) {
            Info nextInfo = process(next);
            no += Math.max(nextInfo.no, nextInfo.yes);
            yes += nextInfo.no;
        }
        return new Info(no, yes);
    }
}
