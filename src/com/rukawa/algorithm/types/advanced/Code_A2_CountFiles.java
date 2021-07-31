package com.rukawa.algorithm.types.advanced;

import java.io.File;
import java.util.Stack;

/**
 * @className: Code_A2_CountFiles
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/7/31 0031 19:29
 **/
public class Code_A2_CountFiles {
    /**
     * 给定一个文件目录的路径
     * 写一个函数统计这个目录下所有的文件数量并返回
     * 隐藏文件也算，但是文件夹不算
     */
    public static int getFileNumber(String path) {
        File root = new File(path);
        if (!root.isDirectory() && !root.isFile()) {
            return 0;
        }
        if (root.isFile()) {
            return 1;
        }
        int files = 0;
        Stack<File> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            File folder = stack.pop();
            for (File next : folder.listFiles()) {
                if (next.isFile()) {
                    files++;
                }

                if (next.isDirectory()) {
                    stack.push(next);
                }
            }
        }
        return files;
    }

    public static void main(String[] args) {
        // 你可以自己更改目录
        String path = "C:\\Users\\Administrator\\Desktop";
        System.out.println(getFileNumber(path));
    }
}
