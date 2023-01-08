package com.rukawa.algorithm.base.class27;

/**
 * create by hqh on 2023/1/8
 */
public class Code01_AVLTreeMap {

    // 搜索二叉树的潜台词：不允许重复的key

    /**
     * 平衡二叉查找树：简称平衡二叉树。也称为AVL树。
     * 	性质：
     * 		1、可以是空树
     * 		2、假如不是空树，任何一个节点的左子树与右子树都是平衡二叉树，并且高度之差的绝对值不超过1.
     *
     * 	左旋：1.节点的右孩子替代此节点位置；2.右孩子的左子树变为该节点的右子树；3.节点本身变为右孩子的左子树
     * 	右旋: 1.节点的左孩子替代此节点位置；2.左孩子的右子树变为该节点的左子树；3.节点本身变为左孩子的右子树
     *
     * 	AVL具有最严苛的平衡性；sb树，红黑树具有自己的平衡性；
     * 	各个树的差别：规定的平衡性不一样
     * 	相似点：不管哪种平衡树，增删改查的时间复杂度为O(logN)
     *
     * 	AVL树破坏平衡性的四种情况
     *  LL：根节点3，左孩子挂一个2，此时是AVL，但是在2的左孩子挂一个1，破坏平衡性。此时针对3进行右旋。遇到LL型的违规，只进行右旋。
     *  LR：根节点4，左孩子挂一个2，此时是AVL，但是在2的右孩子挂一个3，破坏平衡性。此时针对2进行左旋，再对4进行右旋。针对RR的违规，先对左孩子节点左旋，再对根节点右旋。
     *  RR：根节点3，右孩子挂一个4，此时是AVl，但是在4的右孩子挂一个5，破坏平衡性。此时针对3进行左旋。遇到RR型的违规，只进行左旋。
     *  RL：根节点2，右孩子挂一个4，此时是AVL，但是在4的左孩子挂一个3，破坏平衡性。此时针对4进行右旋，再对2进行左旋。针对RL的违规，先对右孩子节点右旋，再对根节点左旋。
     */
    public static class AVLNode<K extends Comparable<K>, V> {
        // 可比较的K
        private K k;
        private V v;
        private AVLNode<K, V> l;
        private AVLNode<K, V> r;
        private int h;

        public AVLNode(K key, V val) {
            k = key;
            v = val;
            h = 1;
        }
    }

    public static class AVLTreeMap<K extends Comparable<K>, V> {
        private AVLNode<K, V> root;
        // 加入K的数量
        private int size;

        public AVLTreeMap() {
            root = null;
            size = 0;
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
        private AVLNode<K, V> rightRotate(AVLNode<K, V> cur) {
            AVLNode<K, V> left = cur.l;
            // 左孩子的右子树变为该节点的左子树；
            cur.l = left.r;
            // 左孩子的右节点接管当前节点
            left.r = cur;
            // 重新计算高度
            cur.h = Math.max((cur.l != null ? cur.l.h : 0), (cur.r != null ? cur.r.h : 0)) + 1;
            left.h = Math.max((left.l != null ? left.l.h : 0), (left.r != null ? left.r.h : 0)) + 1;
            // 节点的左孩子替代该节点的位置
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
        private AVLNode<K, V> leftRotate(AVLNode<K, V> cur) {
            AVLNode<K, V> right = cur.r;
            // 右孩子的左子树变成该节点的右子树
            cur.r = right.l;
            // 右孩子的左节点接管该节点
            right.l = cur;
            // 重新计算高度
            cur.h = Math.max((cur.l != null ? cur.l.h : 0), (cur.r != null ? cur.r.h : 0)) + 1;
            right.h = Math.max((right.l != null ? right.l.h : 0), (right.r != null ? right.r.h : 0)) + 1;
            // 节点的右孩子替代该节点的位置
            return right;
        }

        // 平衡性调整
        private AVLNode<K, V> maintain(AVLNode<K, V> cur) {
            if (cur == null) {
                return null;
            }
            int leftHeight = cur.l != null ? cur.l.h : 0;
            int rightHeight = cur.r != null ? cur.r.h : 0;
            // 高度差 > 1，破坏平衡性
            if (Math.abs(leftHeight - rightHeight) > 1) {
                if (leftHeight > rightHeight) {
                    int leftLeftHeight = cur.l != null && cur.l.l != null ? cur.l.l.h : 0;
                    int leftRightHeight = cur.l != null && cur.l.r != null ? cur.l.r.h : 0;
                    // LL型且LR型
                    // leftLeftHeight > leftRightHeight LL型
                    // leftRightHeight = leftRightHeight LL且LR型
                    if (leftLeftHeight >= leftRightHeight) {    // 此时只需要左旋
                        cur = rightRotate(cur);
                    } else {    // LR型  先对左子树进行左旋，然后整颗树进行右旋
                        cur.l = leftRotate(cur.l);
                        cur = rightRotate(cur);
                    }
                } else {
                    int rightLeftHeight = cur.r != null && cur.r.l != null ? cur.r.l.h : 0;
                    int rightRightHeight = cur.r != null && cur.r.r != null ? cur.r.r.h : 0;
                    // RR型且RL型
                    // rightRightHeight > rightLeftHeight RR型
                    // rightRightHeight = rightLeftHeight RR且RL型
                    if (rightRightHeight >= rightLeftHeight) {
                        cur = leftRotate(cur);
                    } else {    // RL型，先对右子树进行右旋，再对整颗树进行左旋
                        cur.r = rightRotate(cur.r);
                        cur = leftRotate(cur);
                    }
                }
            }
            return cur;
        }

        private AVLNode<K, V> add(AVLNode<K, V> cur, K key, V val) {
            if (cur == null) {
                return new AVLNode<>(key, val);
            } else {
                if (key.compareTo(cur.k) < 0) {
                    cur.l = add(cur.l, key, val);
                } else {
                    cur.r = add(cur.r, key, val);
                }
                cur.h = Math.max((cur.l != null ? cur.l.h : 0), (cur.r != null ? cur.r.h : 0)) + 1;
                return maintain(cur);
            }
        }

        // 在cur这棵树上，删掉key所代表的节点
        // 返回cur这棵树的新头部

        /**
         * AVL树的四种删除节点方式
         * 分为四种情况：
         *  1、删除叶子节点；2、删除的节点只有左子树；3、删除的节点只有右子树；4、删除的节点既有左子树又有右子树
         *
         *  只不过AVL树在删除节点后需要重新检查平衡性并进行修正，同时，删除操作于插入操作后的平衡性修复区别在于，
         *  插入操作后只需要对插入栈中的弹出的第一个非平衡节点进行修正，而删除操作需要修正栈中所有非平衡节点
         */
        private AVLNode<K, V> delete(AVLNode<K, V> cur, K key) {
            // 删左子树形成的新头部
            if (key.compareTo(cur.k) < 0) {
                cur.l = delete(cur.l, key);
            } else if (key.compareTo(cur.k) > 0){ //删右子树形成的新头部
                cur.r = delete(cur.r, key);
            } else {
                if (cur.l == null && cur.r == null) {   // 无孩子节点，直接删除
                    cur = null;
                } else if (cur.l == null && cur.r != null) {    // 有右孩子，右孩子替换该节点
                    cur = cur.r;
                } else if (cur.l != null && cur.r == null) {    // 有左孩子，左孩子替换该节点
                    cur = cur.l;
                } else {    // 既有左孩子又有右孩子
                    // 找到右树上的最左的节点，因为此时的节点必然是右子树中第一个大于当前节点
                    // 此时找到的节点直接替换当前节点
                    AVLNode<K, V> des = cur.r;
                    while (des.l != null) {
                        des = des.l;
                    }
                    // 删除找到的右子树最左节点
                    // 先不删除，包含平衡性调整后再把它删掉
                    // 做完cur.r的整颗树的平衡调整
                    cur.r = delete(cur. r, des.k);
                    // 替换cur
                    des.l = cur.l;
                    des.r = cur.r;
                    cur = des;
                }
            }

            if (cur != null) {
                cur.h = Math.max((cur.l != null ? cur.l.h : 0), (cur.r != null ? cur.r.h : 0)) + 1;
            }
            // 每一层都进行平衡性调整
            return maintain(cur);
        }

        // 找到<key最左的节点
        private AVLNode<K, V> findLastIndex(K key) {
            AVLNode<K, V> pre = root;
            AVLNode<K, V> cur = root;
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

        // 找到>=key,离key最近的节点
        private AVLNode<K, V> findLastNoSmallIndex(K key) {
            AVLNode<K, V> ans = null;
            AVLNode<K, V> cur = root;
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

        // 找到<=key,离key最近的节点
        private AVLNode<K, V> findLastNoBigIndex(K key) {
            AVLNode<K, V> ans = null;
            AVLNode<K, V> cur = root;
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

        public int size() {
            return size;
        }

        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }
            AVLNode<K, V> lastNode = findLastIndex(key);
            return lastNode != null && key.compareTo(lastNode.k) == 0 ? true : false;
        }

        public void put(K key, V value) {
            if (key == null) {
                return;
            }
            AVLNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.k) == 0) {
                lastNode.v = value;
            } else {
                size++;
                root = add(root, key, value);
            }
        }

        public void remove(K key) {
            if (key == null) {
                return;
            }
            if (containsKey(key)) {
                size--;
                root = delete(root, key);
            }
        }

        public V get(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.k) == 0) {
                return lastNode.v;
            }
            return null;
        }

        // 找到整颗树最小的key
        public K firstKey() {
            if (root == null) {
                return null;
            }
            AVLNode<K, V> cur = root;
            while (cur.l != null) {
                cur = cur.l;
            }
            return cur.k;
        }

        // 找到整颗树最大的key
        public K lastKey() {
            if (root == null) {
                return null;
            }
            AVLNode<K, V> cur = root;
            while (cur.r != null) {
                cur = cur.r;
            }
            return cur.k;
        }

        // 找到离当前key最近且小的key
        public K floorKey(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> lastNoBigNode = findLastNoBigIndex(key);
            return lastNoBigNode == null ? null : lastNoBigNode.k;
        }


        // 找到离当前key最近且大的key
        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> lastNoSmallNode = findLastNoSmallIndex(key);
            return lastNoSmallNode == null ? null : lastNoSmallNode.k;
        }
    }
}
