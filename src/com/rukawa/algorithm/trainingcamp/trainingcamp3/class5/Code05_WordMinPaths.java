package com.rukawa.algorithm.trainingcamp.trainingcamp3.class5;

import java.util.*;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-09-29 22:50
 * @Version：1.0
 */
public class Code05_WordMinPaths {
    /**
     * 给定两个字符串，即为start和end，再给定一个字符串列表list，list中一定包含end，
     * list中没有重复字符串，所有的字符串都是小写的。
     * 规定：start每次只能改变一个字符，最终的目标是彻底变成end，但是每次变成的新字符串必须在list中存在。
     * 请返回所有最短的变换路径。
     *
     * 举例：
     * start="abc", end="cab",list={"cab","acc","cbc","ccc","cac","cbb","aab","abb"}
     * 转换路径的方法有很多种，但所有最短的转换路径如下：
     * abc -> abb -> aab -> cab
     * abc -> abb -> cbb -> cab
     * abc -> cbc -> cac -> cab
     * abc -> cbc -> cbb -> cab
     */
    public static List<List<String>> findMinPaths(String start, String end, List<String> list) {
        list.add(start);
        // list里面的所有字符串生成邻居表，图的生成 -> 邻居表
        HashMap<String, ArrayList<String>> nextS = getNextS(list);
        // 所有字符串到start的最短距离是多少，宽度优先遍历 -> 距离表
        HashMap<String, Integer> distances = getDistances(start, nextS);
        // 沿途走过的路径
        LinkedList<String> pathList = new LinkedList<>();
        List<List<String>> res = new ArrayList<>();
        getShortestPaths(start, end, nextS, distances, pathList, res);
        return res;
    }

    // 字符串列表的邻居表，邻居表，改变字符串某个位置的值称为邻居字符串
    public static HashMap<String, ArrayList<String>> getNextS(List<String> words) {
        Set<String> dict = new HashSet<>(words);
        HashMap<String, ArrayList<String>> nextS = new HashMap<>();
        for (int i = 0; i < dict.size(); i++) {
            nextS.put(words.get(i), getNext(words.get(i), dict));
        }
        return nextS;
    }

    /**
     * 时间复杂度O(25 * k) -> k=word.length()
     * 生成字符串的字符串邻居，字符串邻居->改变word的一个字符的结果为邻居
     */
    public static ArrayList<String> getNext(String word, Set<String> dict) {
        ArrayList<String> res = new ArrayList<>();
        char[] str = word.toCharArray();

        for (int i = 0; i < str.length; i++) {
            for (char cur = 'a'; cur <= 'z'; cur++) {
                if (str[i] != cur) {
                    char tmp = str[i];
                    str[i] = cur;
                    // 改变i位置字符的字符串是否在字典表中存在，如果存在，则认为是邻居字符串
                    if (dict.contains(String.valueOf(str))) {
                        res.add(String.valueOf(str));
                    }
                    str[i] = tmp;
                }
            }
        }
        return res;
    }

    // 宽度优先遍历(Breadth First Search),获取所有的邻居字符串到start的距离
    public static HashMap<String, Integer> getDistances(String start, HashMap<String, ArrayList<String>> nextS) {
        HashMap<String, Integer> distances = new HashMap<>();
        distances.put(start, 0);
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        // 保证不重复走字符串
        HashSet<String> set = new HashSet<>();
        set.add(start);
        while (!queue.isEmpty()) {
            String cur = queue.poll();
            for (String next : nextS.get(cur)) {
                if (!set.contains(next)) {
                    distances.put(next, distances.get(cur) + 1);
                    queue.add(next);
                    set.add(next);
                }
            }
        }
        return distances;
    }


    /**
     * 深度优先遍历(Depth First Search)
     * @param start
     * @param end
     * @param nextS 邻居表
     * @param distances 最短距离表
     * @param path 沿途走过的路径
     * @param res  答案，收集所有的最短路径
     */
    public static void getShortestPaths(String start, String end, HashMap<String, ArrayList<String>> nextS,
                                        HashMap<String, Integer> distances,
                                        LinkedList<String> path, List<List<String>> res) {
        path.add(start);
        // 到达目的地，把所有的路径添加到答案中
        if (end.equals(start)) {
            res.add(new LinkedList<>(path));
        } else {
            // 如果没有到达目的地，寻找start距离+1的邻居，继续往下走
            for (String next : nextS.get(start)) {
                if (distances.get(next) == distances.get(start) + 1) {
                    getShortestPaths(next, end, nextS, distances, path, res);
                }
            }
        }
        // DFS擦除轨迹
        // 比如：a->b->c->d，还有一条a->b->d,所有在c尝试完之后要擦除c的轨迹，然后d去尝试
        path.pollLast();
    }

    public static void main(String[] args) {
        String start = "abc";
        String end = "cab";
        String[] test = { "abc", "cab", "acc", "cbc", "ccc", "cac", "cbb",
                "aab", "abb" };
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < test.length; i++) {
            list.add(test[i]);
        }
        List<List<String>> res = findMinPaths(start, end, list);
        for (List<String> obj : res) {
            for (int i = 0; i < obj.size(); i++) {
                String str = obj.get(i);
                if (i != obj.size() - 1) {
                    str += "->";
                }
                System.out.print(str);
            }
            System.out.println();
        }

    }
}
