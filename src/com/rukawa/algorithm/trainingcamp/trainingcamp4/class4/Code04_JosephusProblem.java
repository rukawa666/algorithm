package com.rukawa.algorithm.trainingcamp.trainingcamp4.class4;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-14 23:24
 * @Version：1.0
 */
public class Code04_JosephusProblem {
    /**
     * 约瑟夫环问题
     * 数学应用问题：已知N个人(以编号1,2,3,...,n分别表示)围坐在一张圆桌周围。
     * 从编号为k的人开始报数，数到m的人出圈；他的下一个人又从1开始报数，数到m
     * 的那个人又出圈；依次规律重复下去，直到剩余最后一个胜利者
     */

    /**
     * 思路：
     *  1、假设出圈后的数得出出圈前的数，比如或者最后的编号是1，调用函数得出两个节点的时候活着的编号是多少，
     *  根据2个节点的时候得出3个节点的时候活着的编号是多少，依次到n，一个节点都不杀的时候，得出原始节点的编号
     *  2、剃刀函数： Y = X % i;
     *  3、Y = X^2， 左加右减，上加下减
     *
     *  举例：
     *  旧：1 2 3 4 5 6 7
     *  新：5 6 x 1 2 3 4
     *
     *  1、初始：
     *    号(Y轴) -> 1 2 3 4 5 6  不断的迭代
     *    位(X轴) -> 1 2 3 4 5 6  依次增加
     *    所以根据剃刀公式：y = x % i  -> 号 = (数 - 1) % i + 1;
     *
     *  2、此时s位置的人被干掉，s+1的点为1号位，如果i位置在i-s号位，转回来后i-s+1在1号位
     *  3、此时号 = (数 - 1) % i + 1;这个图像向左移动s位，得到公式，旧 = (新 + s - 1) % i + 1;  -> s:杀的号  i:杀之前的长度
     *  4、旧 = (新 + s - 1) % i + 1;  -> s:在旧的链表中没杀之前被干掉的节点是s号(不好确定)  i:杀之前的长度
     *  5、确定s -> 报数到m就被杀掉，所以 s = s = (数 - 1) % 1 + 1;
     *  6、结合4和5得出 -> 旧 = (新 + (m - 1) % 1 + 1 - 1) % i + 1;
     *  7、优化  -> 旧 = (新 + (m - 1) % 1) % 1 + 1;
     *      end -> 旧 = (新 + (m - 1)) % 1 + 1;
     *
     *          -> m-1=k*i+r(余数)，k=0，旧 = (新 + r) % i + 1;
     */

    // O(N)
    public static Node josephusKill1(Node head, int m) {
        if (head == null || head.next == head || m < 1) {
            return head;
        }
        Node last = head;
        while (last.next != head) {
            last = last.next;
        }
        int count = 0;
        while (head != last) {
            if (++count == m) {
                last.next = head.next;
                count = 0;
            } else {
                last = last.next;
            }
            head = last.next;
        }
        return head;
    }

    public static Node josephusKill2(Node head, int m) {
        if (head == null || head.next == head || m < 1) {
            return head;
        }
        Node cur = head.next;
        // 获取有多少个节点
        int size = 1;
        while (cur != head) {
            size++;
            cur = cur.next;
        }
        int live = getLive(size, m);
        while (--live != 0) {
            head = head.next;
        }
        head.next = head;
        return head;
    }

    // 现在一共有i个节点，数到m就被杀死，最终活下来的节点，请返回它在有i个节点的时候的编号
    public static int getLive(int n, int m) {
        if (n == 1) {
            return 1;
        }
        // getLive(i-1, m) 长度为i-1的时候，活下来的编号
        return (getLive(n - 1, m) + m - 1) % n + 1;
    }

    // 迭代 TODO 代码有问题
    public static int getLiveFor(int n, int m) {
        if (n == 1) {
            return 1;
        }
        int res = 0;
        for (int i = 2; i <= n; ++i) {
            res = (res + m - 1) % n + 1;
        }
        return res;
    }

    public static void printCircularList(Node head) {
        if (head == null) {
            return;
        }
        System.out.print("Circular List: " + head.value + " ");
        Node cur = head.next;
        while (cur != head) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println("-> " + head.value);
    }

    public static void main(String[] args) {
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = head1;
        printCircularList(head1);
        head1 = josephusKill1(head1, 3);
        printCircularList(head1);

        Node head2 = new Node(1);
        head2.next = new Node(2);
        head2.next.next = new Node(3);
        head2.next.next.next = new Node(4);
        head2.next.next.next.next = new Node(5);
        head2.next.next.next.next.next = head2;
        printCircularList(head2);
        head2 = josephusKill2(head2, 3);
        printCircularList(head2);
        System.out.println(getLive(8,3));
        System.out.println(getLiveFor(8,3));

    }



    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }
}
