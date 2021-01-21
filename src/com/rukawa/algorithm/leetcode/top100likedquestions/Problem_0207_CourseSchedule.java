package com.rukawa.algorithm.leetcode.top100likedquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-25 21:51
 * @Version：1.0
 */
public class Problem_0207_CourseSchedule {
    /**
     * 课程表
     * 你这个学期必须选修 numCourse 门课程，记为 0 到 numCourse-1 。
     * 在选修某些课程之前需要一些先修课程。 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示他们：[0,1]
     * 给定课程总量以及它们的先决条件，请你判断是否可能完成所有课程的学习？
     *
     * 示例 1:
     * 输入: 2, [[1,0]]
     * 输出: true
     * 解释: 总共有 2 门课程。学习课程 1 之前，你需要完成课程 0。所以这是可能的。
     *
     * 示例 2:
     * 输入: 2, [[1,0],[0,1]]
     * 输出: false
     * 解释: 总共有 2 门课程。学习课程 1 之前，你需要先完成​课程 0；并且学习课程 0 之前，你还应先完成课程 1。这是不可能的。
     *  
     *
     * 提示：
     * 输入的先决条件是由 边缘列表 表示的图形，而不是 邻接矩阵 。详情请参见图的表示法。
     * 你可以假定输入的先决条件中没有重复的边。
     * 1 <= numCourses <= 10^5
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if (prerequisites == null || prerequisites.length == 0) {
            return true;
        }
        HashMap<Integer, Node> nodes = new HashMap<>();
        for (int[] arr : prerequisites) {
            int to = arr[0];
            int from = arr[1];
            if (!nodes.containsKey(to)) {
                nodes.put(to, new Node(to));
            }
            if (!nodes.containsKey(from)) {
                nodes.put(from, new Node(from));
            }

            Node t = nodes.get(to);
            Node f = nodes.get(from);
            f.nextS.add(t);
            t.in++;
        }

        int needPrerequisiteNums = nodes.size();
        Queue<Node> zeroInQueue = new LinkedList<>();
        for (Node node : nodes.values()) {
            if (node.in == 0) {
                zeroInQueue.add(node);
            }
        }
        int count = 0;
        while (!zeroInQueue.isEmpty()) {
            Node cur = zeroInQueue.poll();
            count++;
            for (Node next : cur.nextS) {
                if (--next.in == 0) {
                    zeroInQueue.add(next);
                }
            }
        }
        return count == needPrerequisiteNums;
    }
    public static class Node {
        public int name;
        public int in;
        public ArrayList<Node> nextS;

        public Node(int n) {
            this.name = n;
            this.in = 0;
            nextS = new ArrayList<>();
        }
    }
}
