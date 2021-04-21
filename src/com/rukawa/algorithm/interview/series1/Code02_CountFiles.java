package com.rukawa.algorithm.interview.series1;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: Administrator
 * @Date: 2021/4/20 0020 20:19
 */
public class Code02_CountFiles {

    /**
     * 给定一个文件目录的路径
     * 写一个函数统计这个目录下所有的文件数量并返回
     * 隐藏文件也算，但是文件夹不算
     */

    public static int getFileNumber1(String path) {
        File root = new File(path);
        if (!root.isDirectory() && !root.isFile()) {
            return 0;
        }
        if (root.isFile()) {
            return 1;
        }
        Stack<File> stack = new Stack<>();
        stack.add(root);
        int files = 0;
        while (!stack.isEmpty()) {
            File folder = stack.pop();
            for (File file : folder.listFiles()) {
                if (file.isFile()) {
                    files++;
                }
                if (file.isDirectory()) {
                    stack.push(file);
                }
            }
        }
        return files;
    }

    public static int getFileNumber2(String path) {
        File root = new File(path);
        if (!root.isDirectory() && !root.isFile()) {
            return 0;
        }
        if (root.isFile()) {
            return 1;
        }
        Queue<File> queue = new LinkedList<>();
        queue.add(root);
        int files = 0;
        while (!queue.isEmpty()) {
            File folder = queue.poll();
            for (File file : folder.listFiles()) {
                if (file.isFile()) {
                    files++;
                }
                if (file.isDirectory()) {
                    queue.add(file);
                }
            }
        }
        return files;
    }

    public static void main(String[] args) {
        // 你可以自己更改目录
        String path = "D:\\ali-tomcat";
        System.out.println(getFileNumber1(path));
        System.out.println(getFileNumber2(path));
    }
}
