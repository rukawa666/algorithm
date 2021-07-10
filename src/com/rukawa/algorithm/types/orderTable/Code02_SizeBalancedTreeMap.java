package com.rukawa.algorithm.types.orderTable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/7/5 0005 0:03
 */
public class Code02_SizeBalancedTreeMap {

    public static class SBTNode<K extends Comparable<K>, V> {
        public K k;
        public V v;
        public SBTNode<K, V> l;
        public SBTNode<K, V> r;
        public int size;

        public SBTNode(K key, V value) {
            this.k = key;
            this.v = value;
            size = 1;
        }
    }

    public static class SBTreeMap<K extends Comparable<K>, V> {
        public SBTNode<K, V> root;

        // 右旋：1、节点的左孩子替代该节点的位置；2、左孩子的右子树变为该节点的左子树；节点本身变为左孩子的右子树
        private SBTNode<K, V> rightRotate(SBTNode<K, V> node) {
            SBTNode<K, V> left = node.l;
            node.l = left.r;
            left.r = node;
            left.size = node.size;
            node.size = (node.l != null ? node.l.size : 0) + (node.r != null ? node.r.size : 0) + 1;
            return left;
        }

        // 左旋：1、节点的右孩子替代该节点的位置；2、右孩子的左子树变为该节点的右子树；节点本身变为右孩子的左子树
        private SBTNode<K, V> leftRotate(SBTNode<K, V> node) {
            SBTNode<K, V> right = node.r;
            node.r = right.l;
            right.l = node;
            right.size = node.size;
            node.size = (node.l != null ? node.l.size : 0) + (node.r != null ? node.r.size : 0) + 1;
            return right;
        }

        private SBTNode<K, V> maintain(SBTNode<K, V> cur) {
            if (cur == null) {
                return null;
            }
            int leftSize = cur.l != null ? cur.l.size : 0;
            int leftLeftSize = cur.l != null && cur.l.l != null ? cur.l.l.size : 0;
            int leftRightSize = cur.l != null && cur.l.r != null ? cur.l.r.size : 0;
            int rightSize = cur.r != null ? cur.r.size : 0;
            int rightRightSize = cur.r != null && cur.r.r != null ? cur.r.r.size : 0;
            int rightLeftSize = cur.r != null && cur.r.l != null ? cur.r.l.size : 0;
            // 左孩子的左孩子节点个数大于右孩子的节点个数，认为是LL型
            // LL型
            if (leftLeftSize > rightSize) {
                // 头节点右旋
                cur = rightRotate(cur);
                // 对孩子节点个数发生变化的父亲节点继续调整
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (leftRightSize > rightSize) {     // LR型 先对左孩子进行左旋，再对整颗树进行右旋
                cur.l = leftRotate(cur.l);
                cur = rightRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (rightRightSize > leftSize) {     // RR型 头节点左旋
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur = maintain(cur);
            } else if (rightLeftSize > leftSize) {  // RL型  先对右孩子进行右旋  然后整颗树进行左旋
                //  右孩子右旋
                cur.r = rightRotate(cur.r);
                // 整颗树左旋
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }
            return cur;
        }

        // 找到key节点的位置
        private SBTNode<K, V> findLastIndex(K key) {
            SBTNode<K, V> pre = root;
            SBTNode<K, V> cur = root;
            while (cur != null) {
                pre = cur;
                if (key.compareTo(cur.k) == 0) {
                    break;
                } else if (key.compareTo(cur.k) < 0) {
                    cur = cur.l;
                } else {
                    cur = cur.r;
                }
            }
            return pre;
        }

        private SBTNode<K, V> findLastNoSmallIndex(K key) {
            SBTNode<K, V> ans = null;
            SBTNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.k) == 0) {
                    ans = cur;
                    break;
                } else if (key.compareTo(cur.k) < 0) {
                    ans = cur;
                    cur = cur.l;
                } else {
                    cur = cur.r;
                }
            }
            return ans;
        }

        private SBTNode<K, V> findLastNoBigIndex(K key) {
            SBTNode<K, V> ans = null;
            SBTNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(key) == 0) {
                    ans = cur;
                    break;
                } else if (key.compareTo(cur.k) < 0) {
                    cur = cur.l;
                } else {
                    ans = cur;
                    cur = cur.r;
                }
            }
            return ans;
        }

        private SBTNode<K, V> add(SBTNode<K, V> cur, K key, V value) {
            if (cur == null) {
                return new SBTNode<>(key, value);
            } else {
                cur.size++;
                if (key.compareTo(cur.k) < 0) {
                    cur.l = add(cur.l, key, value);
                } else {
                    cur.r = add(cur.r, key, value);
                }
                return maintain(cur);
            }
        }

        private SBTNode<K, V> delete(SBTNode<K, V> cur, K key) {
            cur.size--;
            if (key.compareTo(cur.k) > 0) {
                cur.r = delete(cur.r, key);
            } else if (key.compareTo(cur.k) < 0) {
                cur.l = delete(cur.l, key);
            } else {
                if (cur.l == null && cur.r == null) {
                    cur = null;
                } else if (cur.l == null && cur.r != null) {
                    cur = cur.r;
                } else if (cur.l != null && cur.r == null) {
                    cur = cur.l;
                } else {
                    SBTNode<K, V> pre = null;
                    SBTNode<K, V> des = cur.r;
                    while (des.l != null) {
                        pre = des;
                        des = des.l;
                        des.size--;
                    }
                    /**
                     * des没有左孩子节点，但是有右孩子节点，des的右孩子直接替换des节点
                     *      pre
                     *     /
                     *    des
                     *     \
                     *      des.r
                     */
                    if (pre != null) {
                        pre.l = des.r;
                        des.r = cur.r;
                    }

                    des.l = cur.l;
                    des.size = des.l.size + (des.r == null ? 0 : des.r.size) + 1;
                    cur = des;
                }
            }
            //            cur = maintain(cur);
            return cur;
        }

        // 在有序表中找到第kth的节点
        private SBTNode<K, V> getIndex(SBTNode<K, V> cur, int kth) {
            if (kth == (cur.l != null ? cur.l.size : 0) + 1) {
                return cur;
            } else if (kth <= (cur.l != null ? cur.l.size : 0)) {
                return getIndex(cur.l, kth);
            } else {
                return getIndex(cur.r, kth - (cur.l != null ? cur.l.size : 0) - 1);
            }
        }

        public int size() {
            return root == null ? 0 : root.size;
        }

        public boolean containsKey(K key) {
            if (key == null) {
                throw new RuntimeException("Invalid Parameter");
            }
            SBTNode<K, V> lastNode = findLastIndex(key);
            return lastNode != null && key.compareTo(lastNode.k) == 0 ? true : false;
        }

        public void put(K key, V value) {
            if (key == null) {
                throw new RuntimeException("Invalid Parameter");
            }
            SBTNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.k) == 0) {
                lastNode.v = value;
            } else {
                root = add(root, key, value);
            }
        }

        public void remove(K key) {
            if (key == null) {
                throw new RuntimeException("Invalid Parameter");
            }
            if (containsKey(key)) {
                root = delete(root, key);
            }
        }

        public K getIndexKey(int index) {
            if (index < 0 || index > this.size()) {
                throw new RuntimeException("Invalid Parameter");
            }
            return getIndex(root, index + 1).k;
        }

        public V getIndexValue(int index) {
            if (index < 0 || index >= this.size()) {
                throw new RuntimeException("Invalid Parameter.");
            }
            return getIndex(root, index + 1).v;
        }

        public V get(K key) {
            if (key == null) {
                throw new RuntimeException("Invalid Parameter");
            }
            SBTNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.k) == 0) {
                return lastNode.v;
            } else {
                return null;
            }
        }

        public K firstKey() {
            if (root == null) {
                return null;
            }
            SBTNode<K, V> cur = root;
            while (cur.l != null) {
                cur = cur.l;
            }
            return cur.k;
        }

        public K lastKey() {
            if (root == null) {
                return null;
            }
            SBTNode<K, V> cur = root;
            while (cur.r != null) {
                cur = cur.r;
            }
            return cur.k;
        }

        public K floorKey(K key) {
            if (key == null) {
                throw new RuntimeException("Invalid Parameter.");
            }
            SBTNode<K, V> lastNoBigNode = findLastNoBigIndex(key);
            return lastNoBigNode == null ? null : lastNoBigNode.k;
        }

        public K ceilingKey(K key) {
            if (key == null) {
                throw new RuntimeException("Invalid Parameter.");
            }
            SBTNode<K, V> lastNoSmallNode = findLastNoSmallIndex(key);
            return lastNoSmallNode == null ? null : lastNoSmallNode.k;
        }
    }

    // for test
    public static void printAll(SBTNode<String, Integer> head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    // for test
    public static void printInOrder(SBTNode<String, Integer> head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.r, height + 1, "v", len);
        String val = to + "(" + head.k + "," + head.v + ")" + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.l, height + 1, "^", len);
    }

    // for test
    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        SBTreeMap<String, Integer> sbt = new SBTreeMap<String, Integer>();
        sbt.put("d", 4);
        sbt.put("c", 3);
        sbt.put("a", 1);
        sbt.put("b", 2);
        // sbt.put("e", 5);
        sbt.put("g", 7);
        sbt.put("f", 6);
        sbt.put("h", 8);
        sbt.put("i", 9);
        sbt.put("a", 111);
        System.out.println(sbt.get("a"));
        sbt.put("a", 1);
        System.out.println(sbt.get("a"));
        for (int i = 0; i < sbt.size(); i++) {
            System.out.println(sbt.getIndexKey(i) + " , " + sbt.getIndexValue(i));
        }
        printAll(sbt.root);
        System.out.println(sbt.firstKey());
        System.out.println(sbt.lastKey());
        System.out.println(sbt.floorKey("g"));
        System.out.println(sbt.ceilingKey("g"));
        System.out.println(sbt.floorKey("e"));
        System.out.println(sbt.ceilingKey("e"));
        System.out.println(sbt.floorKey(""));
        System.out.println(sbt.ceilingKey(""));
        System.out.println(sbt.floorKey("j"));
        System.out.println(sbt.ceilingKey("j"));
        sbt.remove("d");
        printAll(sbt.root);
        sbt.remove("f");
        printAll(sbt.root);

    }
}
