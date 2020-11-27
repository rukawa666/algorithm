package com.rukawa.algorithm.trainingcamp.trainingcamp4.class4;

import java.util.*;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-14 23:23
 * @Version：1.0
 */
public class Code01_BuildingOutline {
    /**
     * 给定一个N*3的矩阵matrix，对于每一个长度为3的小数组arr，都表示一个大楼的三个数据。
     * arr[0]表示大楼的左边界，arr[1]表示大楼的右边界，arr[2]表示大楼的高度(一定大于0)。
     * 每座大楼的地基都在X轴上，大楼之间可能会有重叠，请返回整体的轮廓线数组
     * 举例：
     *  matrix = {
     *      {2, 5, 6},   {1, 7, 4},
     *      {4, 6, 7},   {3, 6, 5},
     *      {10, 13, 2}, {9, 11, 3},
     *      {12, 14, 4}, {10, 12, 5}
     *  }
     *  返回：
     *  {
     *      {1, 2, 4}, {2, 4, 6},
     *      {4, 6, 7}, {6, 7, 4},
     *      {9, 10, 3}, {10, 12, 5},
     *      {12, 14, 4}
     *  }
     */

    // [s,e,h]
    public static List<List<Integer>> buildingOutline(int[][] matrix) {
        int N = matrix.length;
        Operate[] ops = new Operate[N << 1];
        for (int i = 0; i < N; i++) {
            ops[i * 2] = new Operate(matrix[i][0], true, matrix[i][2]);
            ops[i * 2 + 1] = new Operate(matrix[i][1], false, matrix[i][2]);
        }
        // 把描述高度变化的对象数组，按照规定的排序策略排序
        Arrays.sort(ops, new OperateComparator());
        // TreeMap就是java中的红黑树结构，直接当做有序表使用
        // key -> 高度  value -> 次数
        TreeMap<Integer, Integer> mapHeightTimes = new TreeMap<>();

        TreeMap<Integer, Integer> mapXHeight = new TreeMap<>();

        for (int i = 0; i < ops.length; i++) {
            if (ops[i].isAdd) { // 如果当前操作是加入操作
                if (!mapHeightTimes.containsKey(ops[i].h)) {    // 没有出现此高度直接加入
                    mapHeightTimes.put(ops[i].h, 1);
                } else {    // 之前加入过此高度，次数加1即可
                    mapHeightTimes.put(ops[i].h, mapHeightTimes.get(ops[i].h) + 1);
                }
            } else {   // 如果是删除操作
                if (mapHeightTimes.get(ops[i].h) == 1) {    // 如果当前的高度出现次数为1，直接删除记录
                    mapHeightTimes.remove(ops[i].h);
                } else {    // 如果当前的高度出现次数大于1，次数减1即可
                    mapHeightTimes.put(ops[i].h, mapHeightTimes.get(ops[i].h) - 1);
                }
            }
            // 遍历当前位置，获取当前位置的最大高度
            if (mapHeightTimes.isEmpty()) {
                mapXHeight.put(ops[i].x, 0);
            } else {
                mapXHeight.put(ops[i].x, mapHeightTimes.lastKey());
            }
        }
        // res为结果数组，每一个List<Integer>代表一个轮廓线，有开始的位置，结束位置，高度，一共三个信息
        List<List<Integer>> res = new ArrayList<>();
        // 一个新轮廓线的开始位置
        int start = 0;
        // 之前的最大高度
        int preHeight = 0;
        // 根据mapXHeight生成res数组
        for (Map.Entry<Integer, Integer> entry : mapXHeight.entrySet()) {
            // 当前位置
            int curX = entry.getKey();
            // 当前最大高度
            int curMaxHeight = entry.getValue();
            if (preHeight != curMaxHeight) {    // 之前最大高度和当前最大高度不一样时
                if (preHeight != 0) {
                    res.add(new ArrayList<>(Arrays.asList(start, curX, preHeight)));
                }
                start = curX;
                preHeight = curMaxHeight;
            }
        }
        return res;
    }

    public static class Operate {
        public int x;   // x轴上的值
        public boolean isAdd;   // 高度添加还是减少，true -> +, false -> -
        public int h;   // 高度

        public Operate(int x, boolean isAdd, int h) {
            this.x = x;
            this.isAdd = isAdd;
            this.h = h;
        }
    }

    /**
     * 排序策略
     * 1、第一个维度的x值从小到大排序
     * 2、如果第一个维度的值相等，看第二个维度的值，"加入"排在前，"删除"排在后
     * 3、如果两个对象的第一维度和第二维度的值相等，则认为两个对象相等，谁在前都行
     */
    public static class OperateComparator implements Comparator<Operate> {
        @Override
        public int compare(Operate o1, Operate o2) {
            if (o1.x != o2.x) {
                return o1.x - o2.x;
            }
            // 防止纸片大楼[7,7,6] -> {7,true,6},{7,false,6} 此时要把7处理掉，需要把true排在前面
            if (o1.isAdd != o2.isAdd) {
                return o1.isAdd ? -1 : 1;
            }
            return 0;
        }
    }

    public static void main(String[] args) {
//        // java有序表 -> 红黑树
//        // 有序表 ： 跳表、AVL树、SB树...
//        // 只是底层细节不一样，但是对外提供的接口，和每个接口的性能都一样
//        // O(logN)
//        // 哈希表：O(1)
//        TreeMap<Integer, String> map = new TreeMap<>();
//        map.put(6, "我是6");
//        map.put(3, "我是3");
//        map.put(9, "我是9");
//        map.put(1, "我是1");
//        map.put(2, "我是2");
//        map.put(5, "我是5");
//        map.put(1, "我还是1");
//
//        System.out.println(map.containsKey(5));
//        //map.remove(5);
//        System.out.println(map.containsKey(5));
//        System.out.println(map.get(9));
//
//        System.out.println(map.firstKey());
//        System.out.println(map.lastKey());
//        // 查询 <= num，离这个num最近的key是谁
//        System.out.println(map.floorKey(5));
//        // 查询 >= num，离这个num最近的key是谁
//        System.out.println(map.ceilingKey(5));
//
//        // 所有的方法，性能O(logN)

        int[][] matrix = {{1,3,3},{2,4,4},{5,6,1}};
        System.out.println(buildingOutline(matrix));

    }
}
