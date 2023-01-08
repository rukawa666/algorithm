package com.rukawa.algorithm.base.class27;

/**
 * create by hqh on 2023/1/8
 */
public class Code02_SizeBalancedTreeMap {
    /**
     * SizeBalancedTree
     * 	平衡性规定：任何叔叔节点的子树节点个数不小于任何侄子节点的子树节点个数
     * 	不像AVL的平衡性很严苛，SB树的叔叔节点的个数不能比侄子节点个数少，如果整棵树的右孩子节点节点个数比较多，左孩子的节点比较少，差距不会超过2倍以上
     *
     *  四种违规类型：
     * 	LL型违规：左孩子的左孩子的节点个数大于右孩子的节点个数
     * 	RR型违规：右孩子的右孩子的节点个数大于左孩子的节点个数
     * 	LR型违规：左孩子的右孩子的节点个数大于右孩子的节点个数
     * 	RL型违规：右孩子的左孩子的节点个数大于左孩子的节点个数
     */

    public static class SBTNode<K extends Comparable<K>, V> {
        private K k;
        private V v;
        private SBTNode<K, V> l;
        private SBTNode<K, V> r;
        private int size; // 不同的key的数量

        public SBTNode(K key, V value) {
            this.k = key;
            this.v = value;
            size = 1;
        }
    }

    // 右旋：
    // 1、节点的左孩子替代该节点的位置；
    // 2、左孩子的右子树变为该节点的左子树；
    // 3、节点本身变为左孩子的右子树
    /**
     *             cur              left
     *           /    ｜            /   ｜
     *         left   R   ---->   LL   cur
     *        /   ｜                   /  ｜
     *      LL    LR                  LR  R
     */
    public static class SBTreeMap<K extends Comparable<K>, V> {
        private SBTNode<K, V> root;

        private SBTNode<K, V> rightRotate(SBTNode<K, V> cur) {
            SBTNode<K, V> left = cur.l;
            cur.l = left.r;
            left.r = cur;
            left.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
            return left;
        }

        // 左旋：
        // 1、节点的右孩子替代该节点的位置；
        // 2、右孩子的左子树变为该节点的右子树；
        // 3、节点本身变为右孩子的左子树
        /**
         *             cur              right
         *           /    ｜            /   ｜
         *         L    right   ---->  cur   LR
         *              /   ｜        /  |
         *            LL    LR       L  LL
         */
        private SBTNode<K, V> leftRotate(SBTNode<K, V> cur) {
            SBTNode<K, V> right = cur.r;
            // 右孩子的左子树变为该节点的右子树
            cur.r = right.l;
            // 节点的右孩子替代该节点的位置
            right.l = cur;
            right.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
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
            int rightLeftSize = cur.r != null && cur.r.l != null ? cur.r.l.size : 0;
            int rightRightSize = cur.r != null && cur.r.r != null ? cur.r.r.size : 0;
            if (leftLeftSize > rightSize) { // LL型违规：左孩子的左孩子的节点个数大于右孩子的节点个数
                // 头节点右旋
                cur = rightRotate(cur);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (leftRightSize > rightSize) { // LR型违规：左孩子的右孩子的节点个数大于右孩子的节点个数
                cur.l = leftRotate(cur.l);
                cur = rightRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (rightRightSize > leftSize) { // RR型违规：右孩子的右孩子的节点个数大于左孩子的节点个数
                //  头孩子左旋
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur = maintain(cur);
            } else if (rightLeftSize > leftSize) { // RL型违规：右孩子的左孩子的节点个数大于左孩子的节点个数
                cur.r = rightRotate(cur.r);
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
                if (key.compareTo(cur.k) == 0) {
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

        // 现在，以cur为头的树上，新增，加入(key,val)这样的记录
        // 加完之后，会对cur做检查，该调整调整
        // 返回，调整完之后，整棵树的新头部
        private SBTNode<K, V> add(SBTNode<K, V> cur, K key, V val) {
            if (cur == null) {
                return new SBTNode<>(key, val);
            } else {
                cur.size++;
                if (key.compareTo(cur.k) < 0) {
                    cur.l = add(cur.l, key, val);
                } else {
                    cur.r = add(cur.r, key, val);
                }
                return maintain(cur);
            }
        }

        // 在cur这棵树上，删掉key所代表的节点
        // 返回cur这棵树的新头部
        private SBTNode<K, V> delete(SBTNode<K, V> cur, K key) {
            cur.size--;
            if (key.compareTo(cur.k) > 0) {
                cur.r = delete(cur.r, key);
            } else if (key.compareTo(cur.k) < 0) {
                cur.l = delete(cur.l, key);
            } else { // 当前要删掉cur
                if (cur.l == null && cur.r == null) { // 即没有左孩子也没有右孩子
                    cur = null;
                } else if (cur.l == null && cur.r != null) { // 没有左孩子有右孩子
                    cur = cur.r;
                } else if (cur.l != null && cur.r == null) { // 有左孩子没有右孩子
                    cur = cur.l;
                } else { // 有左孩子和右孩子
                    // 找到后继节点替换cur
                    SBTNode<K, V> pre = null;
                    SBTNode<K, V> des = cur.r;
                    des.size--;
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
            // 删除时候不调整，只在加入时候调整，因为调整是递归行为，会把整棵树调整平衡
            /**
             *  如果删除不调整，后续不新增，整棵树有可能变成棒状结构，性能会下降，所以SB树的性能是加入时候的最大的N，
             *  忍受的是所有数据量最大的规模，也就是log(N)，SB能少调整就少调整，哪怕少调不了，牺牲一时的性能也没多少的结构
             *  后续有加入节点也迅速调整平衡
             */
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
                throw new RuntimeException("invalid parameter.");
            }
            SBTNode<K, V> lastNode = findLastIndex(key);
            return lastNode != null && key.compareTo(lastNode.k) == 0 ? true : false;
        }

        public void put(K key, V value) {
            if (key == null) {
                throw new RuntimeException("invalid parameter.");
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
                throw new RuntimeException("invalid parameter.");
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
