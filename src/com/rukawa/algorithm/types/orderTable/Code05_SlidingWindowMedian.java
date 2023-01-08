//package com.rukawa.algorithm.types.orderTable;
//
///**
// * Created with IntelliJ IDEA.
// * Description:
// *
// * @Author: Administrator
// * @Date: 2021/7/10 0010 13:02
// */
//public class Code05_SlidingWindowMedian {
//    /**
//     * 有一个滑动窗口
//     * 1、L是滑动窗口最左位置、R是滑动窗口最右位置，一开始LR都在数组左侧
//     * 2、任何一步都可能R往右动，表示某个数进了窗口
//     * 3、任何一部都可能L往右动，表示某个数出了窗口
//     *
//     * 想知道每一个窗口状态的中位数
//     */
//
//    public static double[] medianSlidingWindow(int[] nums, int k) {
//        SizeBalancedTreeMap<Node> map = new SizeBalancedTreeMap<>();
//        // 窗口初次形成，先把前k个数加入到有序表
//        for (int i = 0; i < k - 1; i++) {
//            map.add(new Node(i, nums[i]));
//        }
//        double[] ans = new double[nums.length - k + 1];
//        int index = 0;
//        for (int i = k - 1; i < nums.length; i++) {
//            // 右边的数字进入窗口
//            map.add(new Node(i, nums[i]));
//            // 如果是偶数
//            if (map.size() % 2 == 0) {
//                // 求上中位数和下中位数
//                Node upMid = map.getIndexKey(map.size() / 2 - 1);
//                Node downMid = map.getIndexKey(map.size() / 2);
//                ans[index++] = ((double) upMid.value + (double) downMid.value) / 2;
//            } else {
//                Node mid = map.getIndexKey(map.size() / 2);
//                ans[index++] = (double) mid.value;
//            }
//            // 左边的数字滑出窗口
//            map.remove(new Node(i - k + 1, nums[i - k + 1]));
//        }
//        return ans;
//    }
//
//    public static class Node implements Comparable<Node> {
//        public int index;
//        public int value;
//
//        public Node(int index, int value) {
//            this.index = index;
//            this.value = value;
//        }
//
//        @Override
//        public int compareTo(Node o) {
//            return value != o.value ? Integer.valueOf(value).compareTo(o.value) : Integer.valueOf(index).compareTo(o.index);
//        }
//    }
//
//
//    public static class SBTNode<K extends Comparable<K>> {
//        public K key;
//        public SBTNode<K> l;
//        public SBTNode<K> r;
//        public int size;
//
//        public SBTNode(K k) {
//            this.key = k;
//            size = 1;
//        }
//    }
//
//    public static class SizeBalancedTreeMap<K extends Comparable<K>> {
//        private SBTNode<K> root;
//
//        private SBTNode<K> rightRotate(SBTNode<K> cur) {
//            SBTNode<K> left = cur.l;
//            // 左孩子的右孩子节点作为当前节点的左孩子
//            cur.l = left.r;
//            // 当前节点替代左孩子的右节点
//            left.r = cur;
//
//            left.size = cur.size;
//            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0);
//            // 节点的左孩子替代该节点的位置
//            return left;
//        }
//
//        private SBTNode<K> leftRotate(SBTNode<K> cur) {
//            SBTNode<K> right = cur.r;
//            // 右孩子的左孩子节点替代当前节点的右孩子
//            cur.r = right.l;
//            // 当前节点替代右孩子的左孩子节点
//            right.l = cur;
//            right.size = cur.size;
//            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0);
//            // 节点的右孩子替代该节点的位置
//            return right;
//        }
//
//        private SBTNode<K> maintain(SBTNode<K> cur) {
//            if (cur == null) {
//                return null;
//            }
//            int leftSize = cur.l != null ? cur.l.size : 0;
//            int leftLeftSize = cur.l != null && cur.l.l != null ? cur.l.l.size : 0;
//            int leftRightSize = cur.l != null && cur.l.r != null ? cur.l.r.size : 0;
//            int rightSize = cur.r != null ? cur.r.size : 0;
//            int rightRightSize = cur.r != null && cur.r.r != null ? cur.r.r.size : 0;
//            int rightLeftSize = cur.r != null && cur.r.l != null ? cur.r.l.size : 0;
//
//            if (leftLeftSize > rightSize) { // LL型，只需要右旋
//                cur = rightRotate(cur);
//                cur.r = maintain(cur.r);
//                cur = maintain(cur);
//            } else if (leftRightSize > rightSize) { // LR型， 先对左孩子左旋，再对父亲右旋
//                cur.l = leftRotate(cur.l);
//                cur = rightRotate(cur);
//                cur.l = maintain(cur.l);
//                cur.r = maintain(cur.r);
//                cur = maintain(cur);
//            } else if (rightRightSize > leftSize) { // RR型，只需要左旋
//                cur = leftRotate(cur);
//                cur.l = maintain(cur.l);
//                cur = maintain(cur);
//            } else if (rightLeftSize > leftSize) {
//                cur.r = rightRotate(cur.r);
//                cur = leftRotate(cur);
//                cur.l = maintain(cur.l);
//                cur.r = maintain(cur.r);
//                cur = maintain(cur);
//            }
//            return cur;
//        }
//
//        public SBTNode<K> add(SBTNode<K> cur, K key) {
//            if (cur == null) {
//                return new SBTNode<>(key);
//            } else {
//                cur.size++;
//                if (key.compareTo(cur.key) < 0) {
//                    cur.l = add(cur.l, key);
//                } else {
//                    cur.r = add(cur.r, key);
//                }
//                return maintain(cur);
//            }
//        }
//
//        public SBTNode<K> delete(SBTNode<K> cur, K key) {
//            cur.size--;
//            if (key.compareTo(cur.key) > 0) {
//                cur.r = delete(cur.r, key);
//            } else if (key.compareTo(cur.key) < 0) {
//                cur.l = delete(cur.l, key);
//            } else {
//                if (cur.l == null && cur.r == null) {
//                    cur = null;
//                } else if (cur.l != null && cur.r == null) {
//                    cur = cur.l;
//                } else if (cur.l == null && cur.r != null) {
//                    cur = cur.r;
//                } else {
//                    SBTNode<K> pre = null;
//                    SBTNode<K> des = cur.r;
//                    while (des.l != null) {
//                        pre = des;
//                        des = des.l;
//                        des.size--;
//                    }
//
//                    /**
//                     *      A
//                     *     /
//                     *    B
//                     *     \
//                     *      C
//                     *  找到最左孩子节点是B，如果B有右孩子节点直接替换B
//                     */
//                    if (pre != null) {
//                        pre.l = des.r;
//                        des.r = cur.r;
//                    }
//                    des.l = cur.l;
//                    des.size = des.l.size + (des.r != null ? des.r.size : 0) + 1;
//                    cur = des;
//                }
//            }
//            return cur;
//        }
//
//        private SBTNode<K> findLastIndex(K key) {
//            SBTNode<K> pre = root;
//            SBTNode<K> cur = root;
//            while (cur != null) {
//                pre = cur;
//                if (key.compareTo(cur.key) == 0) {
//                    break;
//                } else if (key.compareTo(cur.key) < 0) {
//                    cur = cur.l;
//                } else {
//                    cur = cur.r;
//                }
//            }
//            return pre;
//        }
//
//        // 在有序表中找到第kth的节点
//        private SBTNode<K> getIndex(SBTNode<K> cur, int kth) {
//            if (kth == (cur.l != null ? cur.l.size : 0) + 1) {
//                return cur;
//            } else if (kth < (cur.l != null ? cur.l.size : 0)) {
//                return getIndex(cur.l, kth);
//            } else {
//                return getIndex(cur.r, kth - (cur.l != null ? cur.l.size : 0) - 1);
//            }
//        }
//
//        public int size() {
//            return root == null ? 0 : root.size;
//        }
//
//        public boolean containsKey(K key) {
//            if (key == null) {
//                throw new RuntimeException("Invalid Parameter...");
//            }
//            SBTNode<K> lastNode = findLastIndex(key);
//            return lastNode != null && key.compareTo(lastNode.key) == 0 ? true : false;
//        }
//
//        public void add(K key) {
//            if (key == null) {
//                throw new RuntimeException("Invalid Parameter...");
//            }
//            SBTNode<K> lastNode = findLastIndex(key);
//            if (lastNode == null || key.compareTo(lastNode.key) != 0) {
//                root = add(root, key);
//            }
//        }
//
//        public void remove(K key) {
//            if (key == null) {
//                throw new RuntimeException("Invalid Parameter...");
//            }
//            if (containsKey(key)) {
//                root = delete(root, key);
//            }
//        }
//
//        public K getIndexKey(int index) {
//            if (index < 0 || index >= this.size()) {
//                throw new RuntimeException("Invalid Parameter...");
//            }
//            return getIndex(root, index + 1).key;
//        }
//    }
//
//}
