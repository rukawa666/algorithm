package com.rukawa.algorithm.trainingcamp.trainingcamp5.class2;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-31 23:36
 * @Version：1.0
 */
public class Code03_MinCameraCover {
    /**
     * 给定一颗二叉树的头节点head，如果在某一个节点x上放置相机，那么x的父节点、
     * x的所有子节点以及x都可以被覆盖。返回如果要把所有数都覆盖，至少需要多少个
     * 相机
     */
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    public static int minCameraCover1(TreeNode root) {
        /**
         * 思路：以x为头节点
         * 1、x节点放相机，底下节点都被覆盖，整个树至少需要几个相机
         * 2、x节点不放相机，x被覆盖，底下节点也都被覆盖，整棵树至少需要几个相机
         * 3、x节点不放相机，x不被覆盖，底下节点被覆盖，整棵树至少需要几个相机
         *
         * 底下节点要被覆盖？
         *    1、因为底下节点不覆盖，往上返回补救不了。
         *    2、比如x节点放了相机，底下某个节点没有被覆盖，不可能从头调研去查找底下节点。
         *    3、x自己本身有状况，x底下节点必须全部覆盖
         *    4、思想：子函数给我，我在这一层能解决，不想去子树上处理情况，不然复杂度就变高了
         */
        Info info = process1(root);
        return (int) Math.min(info.unCovered + 1, Math.min(info.coveredHasCamera, info.coveredNoCamera));
    }

    public static class Info {
        // x没有被覆盖，x为头的树至少需要几个相机
        public long unCovered;
        // x被相机覆盖了，但是x没有相机，x为头的树至少需要几个相机
        public long coveredNoCamera;
        // x被相机覆盖了，并且x上放了相机，x为头的树至少需要几个相机
        public long coveredHasCamera;

        public Info(long un, long no, long has) {
            this.unCovered = un;
            this.coveredNoCamera = no;
            this.coveredHasCamera = has;
        }
    }

    // O(N)
    // 不考虑贪心，列出所有可能性
    public static Info process1(TreeNode x) {
        // base case
        if (x == null) {
            // x为空，能不能说这个点没被覆盖，当然不行，叶子节点有null节点，null节点不牵扯覆盖的问题，所以给系统最大，无解
            // x为空，覆盖且没放相机，节点能放相机吗？不能放相机，所以给0
            // x为空，覆盖且放了相机，不牵扯，认为放了，所以给系统最大，无解
            return new Info(Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
        }

        Info leftInfo = process1(x.left);
        Info rightInfo = process1(x.right);

        // x unCovered x自己不被覆盖，x下方所有节点都被覆盖
        // 左孩子：左孩子没被覆盖，左孩子以下的节点都被覆盖
        //       左孩子被覆盖但没相机，左孩子以下的节点都被覆盖
        //       左孩子被覆盖也有相机，左孩子以下的节点都被覆盖

        // 左孩子被覆盖且没相机，右孩子被覆盖也没相机，得出x不会被覆盖，且x下方所有节点被覆盖
        long unCovered = leftInfo.coveredNoCamera + rightInfo.coveredNoCamera;

        // x下方的点都被覆盖，x也被覆盖，但是x没有相机
        // 以下三种情况，情况1绝对干不过情况2和情况3，但是不使用贪心，列出所有可能性
        long coveredNoCamera = Math.min(
                // 1、左孩子被覆盖且放了相机，右孩子被覆盖且放了相机
                leftInfo.coveredHasCamera + rightInfo.coveredHasCamera,
                    // 2、左孩子被覆盖且放了相机，右孩子被覆盖被放相机
                    Math.min(leftInfo.coveredHasCamera + rightInfo.coveredNoCamera,
                                 // 3、右孩子被覆盖且放了相机，左孩子被覆盖没放相机
                                 rightInfo.coveredHasCamera + leftInfo.coveredNoCamera));
        // x被相机覆盖了，且x放置了相机
        long coveredHasCamera =
                // 左孩子被覆盖还是不覆盖，放不放相机都无所谓
                Math.min(leftInfo.unCovered, Math.min(leftInfo.coveredHasCamera, leftInfo.coveredNoCamera))
                +
                // 右孩子被覆盖还是不覆盖，放不放相机都无所谓
                Math.min(rightInfo.unCovered, Math.min(rightInfo.coveredHasCamera, rightInfo.coveredNoCamera))
                +
                // x节点放置相机
                1;
        return new Info(unCovered, coveredNoCamera, coveredHasCamera);
    }


    // 最优解
    // O(N)
    public static int minCameraCover2(TreeNode root) {
        Data data = process2(root);
        return (int) data.cameras + (data.status == Status.UNCOVERED ? 1 : 0);
    }

    // 以x为头，x下方的节点都是被覆盖的，x自身的情况，分三种
    public static enum Status {
        UNCOVERED, COVERED_NO_CAMERA, COVERED_HAS_CAMERA
    }

    // x为头，x下方的节点都是被覆盖的，得到的最优解中：
    // x是什么状态，在这种状态下，需要至少几个相机
    // 只告诉一种状态下，至少需要几个相机
    public static class Data {
        public Status status;
        // 至少需要几个相机
        public int cameras;

        public Data(Status status, int cameras) {
            this.status = status;
            this.cameras = cameras;
        }
    }

    public static Data process2(TreeNode x) {
        // base case
        if (x == null) {
            // x为空，父节点不需要其他答案，只知道null节点被覆盖且不能放置相机
            return new Data(Status.COVERED_NO_CAMERA, 0);
        }

        Data leftData = process2(x.left);
        Data rightData = process2(x.right);
        int cameras = leftData.cameras + rightData.cameras;

        // 如果左孩子或者右孩子只要有一个没被覆盖，x节点就需要放置相机
        if (leftData.status == Status.UNCOVERED || rightData.status == Status.UNCOVERED) {
            return new Data(Status.COVERED_HAS_CAMERA, cameras + 1);
        }
        // 左右两个孩子，不存在没被覆盖的情况，如果有其中一个孩子放了相机
        if (leftData.status == Status.COVERED_HAS_CAMERA || rightData.status == Status.COVERED_HAS_CAMERA) {
            return new Data(Status.COVERED_NO_CAMERA, cameras);
        }
        // 左孩子被覆盖没相机，右孩子被覆盖没相机，x节点没被覆盖
        return new Data(Status.UNCOVERED, cameras);
    }
}
