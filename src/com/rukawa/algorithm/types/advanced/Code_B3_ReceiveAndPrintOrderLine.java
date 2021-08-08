package com.rukawa.algorithm.types.advanced;

import java.util.HashMap;

/**
 * @className: Code_B3_ReceiveAndPrintOrderLine
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/8/5 0005 7:20
 **/
public class Code_B3_ReceiveAndPrintOrderLine {
    /**
     * 一种消息接收并打印的结构设计
     * 已知一个消息流会不断的吐出整数1~N，但不一定按照顺序吐出。如果上次打印的数为i，那么当i+1出现时，
     * 请打印i+1及其之后接收过的并且连续的所有数，直到1~N全部接收并打印完，请设计这种接收并打印的结构。
     * 初始时默认i==0
     */

    public static class Node {
        public String info;
        public Node next;

        public Node(String str) {
            this.info = str;
        }
    }

    public static class MessageBox {
        // 之前有没有以index+1开始的节点
        public HashMap<Integer, Node> head;
        // 之前有没有以index-1结尾的节点
        public HashMap<Integer, Node> tail;
        // 当前等着的信息是什么序号
        private int waitPoint;


        public MessageBox() {
            head = new HashMap<>();
            tail = new HashMap<>();
            waitPoint = 1;
        }

        // 消息的编号，info消息的内容，消息一定是从1开始
        public void receive(int num, String info) {
            if (num < 1) {
                return;
            }
            Node cur = new Node(info);
            head.put(num, cur);
            tail.put(num, cur);

            // 查询有没有哪个连续区间是以num-1的结尾
            if (tail.containsKey(num - 1)) {
                tail.get(num - 1).next = cur;
                tail.remove(num - 1);
                head.remove(num);
            }

            if (head.containsKey(num + 1)) {
                cur.next = head.get(num + 1);
                // 它的头信息失效
                head.remove(num + 1);
                // 我的尾信息失效
                tail.remove(num);
            }
            // 此时来的信息和等着的信息一致，则打印
            if (num == waitPoint) {
                print();
            }
        }

        public void print() {
            /**
             * 1~6  9~13  15~29
             * 此时waitPoint=1
             */
            Node node = this.head.get(waitPoint);
            while (node != null) {
                System.out.print(node.info + " ");
                node = node.next;
                waitPoint++;
            }
            head.remove(waitPoint);
            tail.remove(waitPoint-1);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        MessageBox box = new MessageBox();
        System.out.println("这是2来到的时候");
        box.receive(2, "B");
        System.out.println("这是1来到的时候");
        box.receive(1, "A");
        box.receive(4, "D");
        box.receive(7, "G");
        box.receive(6, "F");
        box.receive(5, "E");
        box.receive(3, "C");
    }

}
