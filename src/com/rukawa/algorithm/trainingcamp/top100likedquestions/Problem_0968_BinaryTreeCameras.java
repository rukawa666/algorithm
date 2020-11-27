package com.rukawa.algorithm.trainingcamp.top100likedquestions;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-11 0:57
 * @Version：1.0
 */
public class Problem_0968_BinaryTreeCameras  {
    /**
     * 监控二叉树
     * 给定一个二叉树，我们在树的节点上安装摄像头。
     * 节点上的每个摄影头都可以监视其父对象、自身及其直接子对象。
     * 计算监控树的所有节点所需的最小摄像头数量。
     *
     * 示例 1：
     * 输入：[0,0,null,0,0]
     * 输出：1
     * 解释：如图所示，一台摄像头足以监控所有节点。
     *
     * 示例 2：
     * 输入：[0,0,null,0,null,0,null,null,0]
     * 输出：2
     * 解释：需要至少两个摄像头来监视树的所有节点。 上图显示了摄像头放置的有效位置之一。
     *
     * 提示：
     * 给定树的节点数的范围是 [1, 1000]。
     * 每个节点的值都是 0。
     */

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public int minCameraCover(TreeNode root) {
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
