package com.rukawa.algorithm.leetcode.top100likedquestions;

/**
 * create by hqh on 2022/9/1
 */
public class Problem_0333_LargestBSTSubTree {
    /**
     * 最大 BST 子树
     * 给定一个二叉树，找到其中最大的二叉搜索树（BST）子树，并返回该子树的大小。其中，最大指的是子树节点数最多的。
     * 二叉搜索树（BST）中的所有节点都具备以下属性：
     * 左子树的值小于其父（根）节点的值。
     * 右子树的值大于其父（根）节点的值。
     * 注意：子树必须包含其所有后代。
     *  
     *
     * 示例 1：
     * 输入：root = [10,5,15,1,8,null,7]
     * 输出：3
     * 解释：本例中最大的 BST 子树是高亮显示的子树。返回值是子树的大小，即 3 。
     *
     * 示例 2：
     * 输入：root = [4,2,7,2,3,5,null,2,null,null,null,null,null,1]
     * 输出：2
     *
     * 提示：
     * 树上节点数目的范围是 [0, 104]
     * -104 <= Node.val <= 104
     *
     * 进阶:  你能想出 O(n) 时间复杂度的解法吗？
     */

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode() {}

        public TreeNode(int x) {
            this.val = x;
        }

        public TreeNode(int x, TreeNode left, TreeNode right) {
            this.val = x;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 分析：
     *  1.以head不为头，左树上的最大的二叉树搜索子树的大小，右树上的最大的二叉搜索子树的大小
     *  2.以head为头，head整体全部是搜索叉树
     *
     *  需要得到以下信息：
     *      1。左树是否是搜索二叉树
     *      2。右树是否是搜索二叉树
     *      3。左树的最大值要小于当前节点的值
     *      4。右树的最小值要大于当前节点的值
     *      5。要知道搜索二叉树的节点数量
     *      6。当前节点的所有节点个数
     *
     *  是否是搜索二叉树=当前节点的节点个数=搜索二叉树的节点个数
     */

    public int largestBSTSubtree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return process(root).maxBSTSubTreeSize;
    }

    public Info process(TreeNode node) {
        if (node == null) {
            return null;
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        int max = node.val;
        int min = node.val;
        int allSize = 1;
        if (leftInfo != null) {
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
            allSize += leftInfo.allSize;
        }

        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
            allSize += rightInfo.allSize;
        }

        // 可能性1：当前节点的左树的最大二叉搜索子树的大小
        int p1 = -1;
        if (leftInfo != null) {
            p1 = leftInfo.maxBSTSubTreeSize;
        }

        // 可能性2：当前节点的右树的最大二叉搜索子树的大小
        int p2 = -1;
        if (rightInfo != null) {
            p2 = rightInfo.maxBSTSubTreeSize;
        }

        // 可能性3：要把左树和右树的最大搜索树连接起来
        int p3 = -1;
        boolean leftBST = leftInfo == null ? true : leftInfo.maxBSTSubTreeSize == leftInfo.allSize;
        boolean rightBST = rightInfo == null ? true : rightInfo.maxBSTSubTreeSize == rightInfo.allSize;
        if (leftBST && rightBST) {
            boolean leftMaxLessNodeValue = leftInfo == null ? true : leftInfo.max < node.val;
            boolean rightMinGreaterValue = rightInfo == null ? true : rightInfo.min > node.val;
            if (leftMaxLessNodeValue && rightMinGreaterValue) {
                int leftBSTSize = leftInfo == null ? 0 : leftInfo.allSize;
                int rightBSTSize = rightInfo == null ? 0 : rightInfo.allSize;
                p3 = leftBSTSize + rightBSTSize + 1;
            }
        }
        return new Info(Math.max(p1, Math.max(p2, p3)), max, min, allSize);
    }

    public static class Info {
        public int maxBSTSubTreeSize;
        public int max;
        public int min;
        public int allSize;

        public Info(int maxBSTSubTreeSize, int max, int min, int allSize) {
            this.maxBSTSubTreeSize = maxBSTSubTreeSize;
            this.max = max;
            this.min = min;
            this.allSize = allSize;
        }
    }
}
