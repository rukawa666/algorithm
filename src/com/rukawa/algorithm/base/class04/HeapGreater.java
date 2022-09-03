package com.rukawa.algorithm.base.class04;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created with Intellij IDEA
 * T 类型不要是基础类型，HashMap的key如果是整型会被覆盖
 * 基础类型可以对T包一层，转换为一个对象
 *
 * 解决的问题：堆的底层是数组，数组中的对象要调整，需要反向索引表
 * 解决类型：动态调整，实时出现的字符串topK，程序员堆和pm堆问题
 * @Author：SuperHai
 * @Date：2021-01-20 20:32
 * @Version：1.0
 */
public class HeapGreater<T> {

    private ArrayList<T> heap;
    // 反向索引表，记录在数组中出现在哪个位置
    private HashMap<T, Integer> indexMap;
    // 堆的大小
    private int heapSize;
    // 比较大小的比较器
    private Comparator<? super T> com;

    public HeapGreater(Comparator<T> c) {
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
        heapSize = 0;
        com = c;
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public int size() {
        return heapSize;
    }

    // O(1) 查询，优化点
    public boolean contains(T obj) {
        return indexMap.containsKey(obj);
    }

    public T peek() {
        return heap.get(0);
    }

    public void push(T obj) {
        heap.add(obj);
        indexMap.put(obj, heapSize);
        heapInsert(heapSize++);
    }

    public T pop() {
        T res = heap.get(0);
        swap(0, heapSize - 1);
        indexMap.remove(res);
        heap.remove(--heapSize);
        heapify(0);
        return res;
    }

    // 系统堆无法实现
    public void remove(T obj) {
        // 最后一个位置的T，然后去替换要删除的位置，然后做堆调整
        T replace = heap.get(heapSize - 1);
        int position = indexMap.get(obj);
        indexMap.remove(obj);
        heap.remove(--heapSize);
        // 如果不是最后一个元素，需要调整位置
        if (obj != replace) {
            heap.set(position, replace);
            indexMap.put(replace, position);
            resign(replace);
        }
    }

    // 调整为有序
    // 只能向上移动或者向下移动，只能有一边
    public void resign(T obj) {
        heapInsert(indexMap.get(obj));
        heapify(indexMap.get(obj));
    }

    public List<T> getAllElements() {
        List<T> res = new ArrayList<>();
        for (T t : heap) {
            res.add(t);
        }
        return res;
    }

    private void heapInsert(int index) {
        while (com.compare(heap.get(index), heap.get(((index - 1) / 2))) < 0) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    private void heapify(int index) {
        int left = (index << 1) + 1;
        while (left < heapSize) {
            int best = left + 1 < heapSize && com.compare(heap.get(left + 1), heap.get(left)) < 0 ? left + 1 : left;
            best = com.compare(heap.get(best), heap.get(index)) < 0 ? best : index;
            if (best == index) {
                break;
            }
            swap(index, best);
            index = best;
            left = (index << 1) + 1;
        }
    }

    private void swap(int i, int j) {
        T ti = heap.get(i);
        T tj = heap.get(j);
        indexMap.put(ti, j);
        indexMap.put(tj, i);
        heap.set(i, tj);
        heap.set(j, ti);
    }
}
