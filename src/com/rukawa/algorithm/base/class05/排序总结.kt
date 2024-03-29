package com.rukawa.algorithm.base.class05

/**
 * create by hqh on 2022/5/19
 */

/**
 * 排序算法的稳定性
 * 稳定性是指同样大小的样本再排序之后不会改变相对次序
 * 对于基础数据类型来说，稳定性毫无意义
 * 对非基础数据类型来说，稳定性有重要意义
 * 有些排序算法可以实现成稳定的，而有些排序算发无论如何都实现不成稳定的
 *
 *
 * 排序算法总结
 * 1、不基于比较的排序，对样本数据有严格的要求，不易改写
 * 2、基于比较的排序，只要规定好两个样本怎么比较大小就可以直接复用
 * 3、基于比较的排序，时间复杂度的极限是O(N*logN)
 * 4、时间复杂度O(N*logN)，额外的空间复杂度低于O(N)，且稳定的基于比较的排序是不存在的
 * 5、为了绝对的速度选快排，为了省空间选堆排，为了稳定性选归并
 *
 *              时间复杂度      空间复杂度    稳定性
 * 选择排序      O(N^2)         O(1)        无
 * 冒泡排序      O(N^2)         O(1)        有
 * 插入排序      O(N^2)         O(1)        有
 * 归并排序      O(N*logN)      O(N)        有
 * 随机快排      O(N*logN)      O(logN)     无
 *   堆排序      O(N*logN)      O(1)        无
 *=============================================
 * 计数排序      O(N)           O(M)        有
 * 基数排序      O(N)           O(M)        有
 */