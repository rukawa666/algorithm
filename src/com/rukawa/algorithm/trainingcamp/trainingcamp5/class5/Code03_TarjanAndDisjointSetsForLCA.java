package com.rukawa.algorithm.trainingcamp.trainingcamp5.class5;

import java.util.*;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-11-12 8:06
 * @Version：1.0
 */
public class Code03_TarjanAndDisjointSetsForLCA {

    /**
     * public class Query {
     *     public Node o1;
     *     public Node o2;
     *     public Query(Node o1, Node o2) {
     *         this.o1 = o1;
     *         this.o2 = o2;
     *     }
     * }
     *
     * 一个 Query 类的实例表示一条查询语句，表示想要查询 o1 节点和 o2 节点的最近公共 祖先 节点。
     * 给定一棵二叉树的头节点 head，并给定所有的查询语句，即一个 Query 类型的数组 Query[] ques，
     * 请返回Node类型的数组Node[] ans，ans[i]代表ques[i]这条查询的答案，
     * 即ques[i].o1和 ques[i].o2 的最近公共祖先。
     * 【要求】
     * 如果二叉树的节点数为 N，查询语句的条数为 M，整个处理过程的时间复杂度要求达到 O(N+M)。
     */

    public static Node[] tarJanQuery(Node head, Query[] queries) {
        /**
         * Tarjan算法与并查集解决二叉树节点间的最近公共祖先的批量查询问题
         * 思路：
         *   1、二叉树的最低公共祖先，基础课讲过只查一次，O(N)
         *   2、现在设定一大堆查询，要求O（N+M）
         *
         * 流程：
         *   1、建立两张表，map(问题表)  map(答案表)
         *   2、整体二叉树的递归遍历，一定可以来到自己三次，不用区分先序、中序、后序
         *   3、如果没有来过一次，假设来到b，发现b和e与c有关系，但是e和c还未到达，所以删除与e和c的关系，删掉答案表中的记录
         *   4、一个问题中两个节点遇到过就填写答案，所以建立两个反向的表
         *
         *
         * 为什么记录两张表？
         *
         *
         * 举例：
         *  [(b, e), (b, c), (e, c)]
         *
         *  问题表             答案表
         *  b  {e, c}         b {0, 1}
         *  e  {b, c}         e {0, 2}
         *  c  {b, e}         c {1, 2}
         */
        // 生成问题表
        HashMap<Node, LinkedList<Node>> queryMap = new HashMap<>();
        // 生成答案表
        HashMap<Node, LinkedList<Integer>> indexMap = new HashMap<>();
        // key 某个集合的代表节点        value 代表节点所在集合中的所有节点，祖先是谁

        // key 表示一个集合的代表节点 value 这个key所在集合的所有节点，tag点
        HashMap<Node, Node> tagMap = new HashMap<>();

        // 把所有的点初始化到并查集
        UnionFindSet<Node> sets = new UnionFindSet<>(getAllNodes(head));

        Node[] res = new Node[queries.length];
        // 提前填写好无效问题的答案，对于有效的问题，生成好queryMap和indexMap
        setQueriesAndSetEasyAnswers(queries, res, queryMap, indexMap);

        // 二叉树的头节点head，遍历
        // 并查集sets， 打tag，还有tagMap表
        // queryMap, indexMap可以方便的知道有哪些问题
        // res 知道了答案可以填写
        // 遍历递归的过程中，把有效问题解决，填到答案中去
        setAnswers(head, res, queryMap, indexMap, tagMap, sets);
        return res;
    }

    public static List<Node> getAllNodes(Node head) {
        List<Node> res = new ArrayList<>();
        process(head, res);
        return res;
    }

    public static void process(Node head, List<Node> res) {
        if (head == null) {
            return;
        }
        res.add(head);
        process(head.left, res);
        process(head.right, res);
    }

    public static void setQueriesAndSetEasyAnswers(Query[] queries, Node[] res,
                                                   HashMap<Node, LinkedList<Node>> queryMap,
                                                   HashMap<Node, LinkedList<Integer>> indexMap) {
        Node o1 = null;
        Node o2 = null;
        for (int i = 0; i < res.length; i++) {
            o1 = queries[i].o1;
            o2 = queries[i].o2;
            if (o1 == o2 || o1 == null || o2 == null) {
                res[i] = o1 != null ? o1 : o2;
            } else {
                if (!queryMap.containsKey(o1)) {
                    queryMap.put(o1, new LinkedList<>());
                    indexMap.put(o1, new LinkedList<>());
                }

                if (!queryMap.containsKey(o2)) {
                    queryMap.put(o2, new LinkedList<>());
                    indexMap.put(o2, new LinkedList<>());
                }

                queryMap.get(o1).add(o2);
                indexMap.get(o1).add(i);
                queryMap.get(o2).add(o1);
                indexMap.get(o2).add(i);

            }
        }
    }

    /**
     * @param head 二叉树的头节点
     * @param res   答案表
     * @param queryMap  问题表
     * @param indexMap  问题表对应的答案表
     * @param tagMap    tag表
     * @param sets  并查集
     */
    public static void setAnswers(Node head, Node[] res,
                                  HashMap<Node, LinkedList<Node>> queryMap,
                                  HashMap<Node, LinkedList<Integer>> indexMap,
                                  HashMap<Node, Node> tagMap,
                                  UnionFindSet<Node> sets) {
        if (head == null) {
            return;
        }
        setAnswers(head.left, res, queryMap, indexMap, tagMap, sets);
        sets.union(head.left, head);
        tagMap.put(sets.findHead(head), head);

        setAnswers(head.right, res, queryMap, indexMap, tagMap, sets);
        sets.union(head.right, head);
        tagMap.put(sets.findHead(head), head);

        // 处理有关于head的问题
        LinkedList<Node> nodeList = queryMap.get(head);
        LinkedList<Integer> indexList = indexMap.get(head);
        Node node = null;
        Node nodeFather = null;
        int index = 0;
        while (nodeList != null && nodeList.isEmpty()) {
            node = nodeList.poll(); // head node
            index = indexList.poll();   // index
            nodeFather = sets.findHead(node);   // o2在集合中的代表节点
            if (tagMap.containsKey(nodeFather)) {   // o2所在的集合是否设置过tag
                res[index] = tagMap.get(nodeFather);
            }
        }

    }

    public static class Element<V> {
        public V value;

        public Element(V val) {
            this.value = val;
        }
    }

    public static class UnionFindSet<V> {
        public HashMap<V, Element<V>> elementMap;
        public HashMap<Element<V>, Element<V>> fatherMap;
        public HashMap<Element<V>, Integer> sizeMap;

        private UnionFindSet(List<V> list) {
            elementMap = new HashMap<>();
            fatherMap = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V v : list) {
                Element<V> cur = new Element<>(v);
                elementMap.put(v, cur);
                fatherMap.put(cur, cur);
                sizeMap.put(cur, 1);
            }
        }

        public Element<V> findHead(Element<V> element) {
            Stack<Element> path = new Stack<>();
            while (element != fatherMap.get(element)) {
                path.add(element);
                element = fatherMap.get(element);
            }

            while (!path.isEmpty()) {
                fatherMap.put(path.pop(), element);
            }
            return element;
        }

        public V findHead(V node) {
            return elementMap.containsKey(node) ? findHead(elementMap.get(node)).value : null;
        }

        public boolean isSameSet(V a, V b) {
            if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
                return findHead(elementMap.get(a)) == findHead(elementMap.get(b));
            }
            return false;
        }

        public void union(V a, V b) {
            if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
                Element aHead = fatherMap.get(elementMap.get(a));
                Element bHead = fatherMap.get(elementMap.get(b));
                if (aHead != bHead) {
                    int aSize = sizeMap.get(aHead);
                    int bSize = sizeMap.get(bHead);
                    Element big = aSize >= bSize ? aHead : bHead;
                    Element small = big == aHead ? bHead : aHead;
                    fatherMap.put(small, big);
                    sizeMap.put(big, aSize + bSize);
                    sizeMap.remove(small);
                }
            }
        }
    }

    public class Query {
        public Node o1;
        public Node o2;
        public Query(Node o1, Node o2) {
            this.o1 = o1;
            this.o2 = o2;
        }
    }

    public class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int val) {
            this.value = val;
        }
    }

}
