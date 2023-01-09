//package com.rukawa.algorithm.types.orderTable;
//
//import java.util.ArrayList;
//
///**
// * Created with IntelliJ IDEA.
// * Description:
// *
// * @Author: Administrator
// * @Date: 2021/7/11 0011 0:08
// */
//public class Code06_AddRemoveGetIndexGreat {
//
//    public static class SBTNode<V> {
//        public V val;
//        public SBTNode<V> l;
//        public SBTNode<V> r;
//        public int size;
//
//        public SBTNode(V value) {
//            this.val = value;
//            size = 1;
//        }
//    }
//
//    public static class SBTList<V> {
//        private SBTNode<V> root;
//
//        private SBTNode<V> rightRotate(SBTNode<V> cur) {
//            SBTNode<V> left = cur.l;
//            // 当前节点的左孩子由左孩子的右孩子替代
//            cur.l = left.r;
//            // 左孩子的右孩子由当前节点替代
//            left.r = cur;
//            left.size = cur.size;
//            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
//            return left;
//        }
//
//        private SBTNode<V> leftRotate(SBTNode<V> cur) {
//            SBTNode<V> right = cur.r;
//            // 右孩子的左孩子节点替代当前节点的右孩子
//            cur.r = right.l;
//            // 当前节点替代右孩子的左孩子节点
//            right.l = cur;
//            right.size = cur.size;
//            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
//            // 节点的右孩子替代该节点的位置
//            return right;
//        }
//
//        private SBTNode<V> maintain(SBTNode<V> cur) {
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
//        // cur节点挂到root树上
//        private SBTNode<V> add(SBTNode<V> root, int index, SBTNode<V> cur) {
//            if (root == null) {
//                return cur;
//            }
//            root.size++;
//            // 从1位置开始
//            int leftAndHeadSize = (root.l != null ? root.l.size : 0) + 1;
//            if (index < leftAndHeadSize) {
//                root.l = add(root.l, index, cur);
//            } else {
//                root.r = add(root.r, index - leftAndHeadSize, cur);
//            }
//            root = maintain(root);
//            return root;
//        }
//
//        private SBTNode<V> remove(SBTNode<V> root, int index) {
//            root.size--;
//            int rootIndex = root.l != null ? root.l.size : 0;
//            if (index != rootIndex) {
//                if (index < rootIndex) {
//                    root.l = remove(root.l, index);
//                } else {
//                    root.r = remove(root.r, index - rootIndex - 1);
//                }
//                return root;
//            }
//            if (root.l == null && root.r == null) {
//                return null;
//            }
//            if (root.l == null) {
//                return root.r;
//            }
//            if (root.r == null) {
//                return root.l;
//            }
//            SBTNode<V> pre = null;
//            SBTNode<V> cur = root.r;
//            cur.size--;
//            while (cur.l != null) {
//                pre = cur;
//                cur = cur.l;
//                cur.size--;
//            }
//            if (pre != null) {
//                pre.l = cur.r;
//                cur.r = root.r;
//            }
//            cur.l = root.l;
//            cur.size = cur.l.size + (cur.r == null ? 0 : cur.r.size) + 1;
//            return cur;
//        }
//
//        private SBTNode<V> get(SBTNode<V> root, int index) {
//            int leftSize = root.l != null ? root.l.size : 0;
//            if (index < leftSize) {
//                return get(root.l, index);
//            } else if (index > leftSize) {
//                return get(root.r, index - leftSize - 1);
//            } else {
//                return root;
//            }
//        }
//
//        public void add(int index, V num) {
//            SBTNode<V> cur = new SBTNode<V>(num);
//            if (root == null) {
//                root = cur;
//            } else {
//                if (index <= root.size) {
//                    root = add(root, index, cur);
//                }
//            }
//        }
//
//        public V get(int index) {
//            SBTNode<V> ans = get(root, index);
//            return ans.val;
//        }
//
//        public int size() {
//            return root == null ? 0 : root.size;
//        }
//
//        public void remove(int index) {
//            if (index >= 0 && index < size()) {
//                root = remove(root, index);
//            }
//        }
//    }
//
//    // 通过以下这个测试，
//    // 可以很明显的看到LinkedList的插入、删除、get效率不如SbtList
//    // LinkedList需要找到index所在的位置之后才能插入或者读取，时间复杂度O(N)
//    // SbtList是平衡搜索二叉树，所以插入或者读取时间复杂度都是O(logN)
//    public static void main(String[] args) {
//        // 功能测试
//        int test = 50000;
//        int max = 1000000;
//        boolean pass = true;
//        ArrayList<Integer> list = new ArrayList<>();
//        SBTList<Integer> sbtList = new SBTList<>();
//        for (int i = 0; i < test; i++) {
//            if (list.size() != sbtList.size()) {
//                pass = false;
//                break;
//            }
//            if (list.size() > 1 && Math.random() < 0.5) {
//                int removeIndex = (int) (Math.random() * list.size());
//                list.remove(removeIndex);
//                sbtList.remove(removeIndex);
//            } else {
//                int randomIndex = (int) (Math.random() * (list.size() + 1));
//                int randomValue = (int) (Math.random() * (max + 1));
//                list.add(randomIndex, randomValue);
//                sbtList.add(randomIndex, randomValue);
//            }
//        }
//        for (int i = 0; i < list.size(); i++) {
//            if (!list.get(i).equals(sbtList.get(i))) {
//                pass = false;
//                break;
//            }
//        }
//        System.out.println("功能测试是否通过 : " + pass);
//
//        // 性能测试
//        test = 500000;
//        list = new ArrayList<>();
//        sbtList = new SBTList<>();
//        long start = 0;
//        long end = 0;
//
//        start = System.currentTimeMillis();
//        for (int i = 0; i < test; i++) {
//            int randomIndex = (int) (Math.random() * (list.size() + 1));
//            int randomValue = (int) (Math.random() * (max + 1));
//            list.add(randomIndex, randomValue);
//        }
//        end = System.currentTimeMillis();
//        System.out.println("ArrayList插入总时长(毫秒) ： " + (end - start));
//
//        start = System.currentTimeMillis();
//        for (int i = 0; i < test; i++) {
//            int randomIndex = (int) (Math.random() * (i + 1));
//            list.get(randomIndex);
//        }
//        end = System.currentTimeMillis();
//        System.out.println("ArrayList读取总时长(毫秒) : " + (end - start));
//
//        start = System.currentTimeMillis();
//        for (int i = 0; i < test; i++) {
//            int randomIndex = (int) (Math.random() * list.size());
//            list.remove(randomIndex);
//        }
//        end = System.currentTimeMillis();
//        System.out.println("ArrayList删除总时长(毫秒) : " + (end - start));
//
//        start = System.currentTimeMillis();
//        for (int i = 0; i < test; i++) {
//            int randomIndex = (int) (Math.random() * (sbtList.size() + 1));
//            int randomValue = (int) (Math.random() * (max + 1));
//            sbtList.add(randomIndex, randomValue);
//        }
//        end = System.currentTimeMillis();
//        System.out.println("SbtList插入总时长(毫秒) : " + (end - start));
//
//        start = System.currentTimeMillis();
//        for (int i = 0; i < test; i++) {
//            int randomIndex = (int) (Math.random() * (i + 1));
//            sbtList.get(randomIndex);
//        }
//        end = System.currentTimeMillis();
//        System.out.println("SbtList读取总时长(毫秒) :  " + (end - start));
//
//        start = System.currentTimeMillis();
//        for (int i = 0; i < test; i++) {
//            int randomIndex = (int) (Math.random() * sbtList.size());
//            sbtList.remove(randomIndex);
//        }
//        end = System.currentTimeMillis();
//        System.out.println("SbtList删除总时长(毫秒) :  " + (end - start));
//
//    }
//}
