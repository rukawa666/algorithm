package com.rukawa.algorithm.base.class26;

/**
 * create by hqh on 2023/1/8
 */
public class Code06 {
    /**
     * 32位无符号整数的范围是0～4294967295(0~2^32-1)
     * 有一个10G大小的文件，每一行都装着这种类型的数字，
     * 整个文件是无序的，给你5G的内存空间
     * 请你输出一个10G文件大小的文件，就是原文件所有数字排序的结果
     */

    /**
     * 利用堆
     * 1、假设有10G大小的文件，有一个容器最多只能存放3条记录
     * 2、现有文件中的数字是5，5，3，9，9  容器中记录(5,2),(3,1),(9,2)
     * 3、此时文件中有一个数字4要进入容器，把容器中的最大的记录(9,2)淘汰，进入(4,1)
     * 4、此时文件中有一个数字3要进入容器，把容器中(3,1)的记录改为(3,2)
     * 5、此时文件中有一个数字1要进入容器，把容器中(5,2)淘汰，假如(1,1)
     * 6、此时遍历一遍文件中数字，把最小的前三名数字都正确统计了
     * 7、假设过完所有数字，容器中记录是(1,1000),(6,9W),(13,15W)，生成文件，文件中1生成1000次，6生成9W次，13生成15W次
     * 8、用一个变量记录容器中最大的数字是13，把容器空间释放
     * 9、再过一遍文件，但是对于小于等于13的数字，不在容器中统计，得到大于13的三个数字,比如(26,50W),(27,54W),(49,3)
     * 10、继续在文件中写入，26生成了50W，27生成了54W、49生成了3
     * 11、继续更新变量(此时容器中的最大数字)，把容器释放
     * 12、继续过一遍文件，重复上面的操作
     * 13、利用大根堆维持一个容器，容器大小是5G，按照上面的思路排序
     *
     * 假设大根堆的key是int是4Byte，value是long类型8Byte，总共有5G的内存空间，用不了几次就能排序
     */
}
