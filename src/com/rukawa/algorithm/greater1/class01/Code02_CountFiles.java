package com.rukawa.algorithm.greater1.class01;

import java.io.File;
import java.util.Stack;

public class Code02_CountFiles {

	/**
	 * 给定一个文件目录的路径，
	 * 写一个函数统计这个目录下所有的文件数量并返回
	 * 隐藏文件也算，但是文件夹不算
	 */

	// 注意这个函数也会统计隐藏文件
	public static int getFileNumber(String folderPath) {
		File root = new File(folderPath);
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
		String path = "/Users/zuochengyun/Desktop/";
		System.out.println(getFileNumber(path));
	}

}
