package com.rukawa.algorithm.trainingcamp.trainingcamp5.class2;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-10-31 23:36
 * @Version：1.0
 */
public class Code01_PathsToNums {
    /**
     * 给定一个路径数组 paths，表示一张图。paths[i]==j代表城市 i 连向城市 j，如果paths[i]==i，
     * 则表示 i 城市是首都，一张图里只会有一个首都且图中除首都指向自己之 外不会有环。
     * 例如， paths=[9,1,4,9,0,4,8,9,0,1]，
     * 由数组表示的图可以知道，
     *    城市1是首都，所以距离为0，
     *    离首都距离为1的城市只有城市9，离首都距离为2的城市有城市0、3和7，
     *    离首都距离为3的城市有城市4和8，
     *    离首都距离为4的城市有城市 2、5 和 6。
     * 所以距离为0的城市有1座，距离为1的城市有1座，距离为2的城市有3座，距离为3的城市有2座，距离为4的城市有3 座。
     * 那么统计数组为nums=[1,1,3,2,3,0,0,0,0,0]，nums[i]==j 代表距离为i的城市有j座。
     * 要求实现一个void类型的函数，输入一个路径数组 paths，直接在原数组上调整，使之变为nums数组，
     * 即 paths=[9,1,4,9,0,4,8,9,0,1]经过这个函数处理后变成 [1,1,3,2,3,0,0,0,0,0]。
     * 【要求】
     * 如果 paths 长度为 N，请达到时间复杂度为 O(N)，额外空间复杂度为 O(1)。
     */
    public static void pathsToNums(int[] paths) {
        /**
         * 难点：给一个arr，你只能申请有限几个变量，把arr更新为返回数组
         *
         * 方法1：建立图，宽度优先遍历解决
         *
         * 方法2：用有限几个变量更新arr数组
         * 方法2思路：
         *    1、arr当前位置代表当前城市，建立当前位置离首都的距离，用负数形式表示。如果距离是2，则用-2表示
         *    2、自我更新，下标循环推，建立变量 next->要去的地方， last->上一回从哪来的  init->表示从哪个位置开始跳
         *--------------------------------------------------------------------------------------------------------------
         *      举例子：
         *         arr -> 9 1 4 9 0 4 8 9 0 1
         *         i位置-> 0 1 2 3 4 5 6 7 8 9
         *      假设从0位置出发出发，
         *         0  next=9, last=0    0  1  4  9  0  4  8  9  0  1
         *         9  next=1, last=9    0  1  4  9  0  4  8  9  0  0  last填到9的位置
         *         1  next=1, last=9    0  1  4  9  0  4  8  9  0  0  发现是首都，往回走，走到9的位置
         *         9  next=0, last=9    0  1  4  9  0  4  8  9  0 -1  回到9的位置，把当前值0改为-1，代表9到首都的距离是1，用-表示
         *         0  next=0, last=9   -2  1  4  9  0  4  8  9  0 -1  回到0的位置，当前值改为-2，回到开始跳位置，停止跳
         *--------------------------------------------------------------------------------------------------------------
         *         arr -> 6 1 4 1 3
         *         i位置-> 0 1 3 4 6
         *         0  next=6, last=0    6  1  4  1  3
         *         6  next=3, last=6    6  1  4  1  0
         *         3  next=4, last=3    6  1  6  1  0
         *         4  next=1, last=4    6  1  6  3  0
         *         1  此时返现是首都
         *         发现是首都，此时要往回走，跳回4
         *         4  next=3, last=4    6  1  6 -1  0  所以4位置变为-1
         *         3  next=6, last=4    6  1 -2 -1  0  所以3位置变为-2
         *         6  next=0, last=4    6  1 -2 -1 -3  所以6位置变为-3
         *         0  发现是init点       -4  1 -2 -1 -3  所以0位置变为-4
         *--------------------------------------------------------------------------------------------------------------
         *    3、根据以上例子更新arr数组，遇到被扭转过或者首都，返回扭转值-1或者-1
         *    4、最后更新首都位置为0
         *    5、假设根据前4步骤得到数组，接下来统计到首都的距离有几个
         *        arr  -3 -2  0 -1  ...
         *        i     0  1  2  3  ...
         *    6、如果是负数表示：一个城市到首都的距离是多少，如果被改变为正数，则表示统计含义：表示到首都距离为i的城市有多少个
         *    7、下标循环推，
         *      0  index=0, value=0     0 -2  0 -1  当前0位置是-3，要去下一个位置3，初始值更新为0
         *      3  index=3, value=-1    0 -2  0  1  当前3位置是-1，要去下一个位置1，当前值更新为1
         *      1  index=1, value=-2    0  1  0  1  当前1位置是-2，要去下一个位置2，当前值更新为1
         *      2  index=2, value=0     0  1  1  1  发现2位置是0，直接++
         *      最后更新首都距离为0的城市永远有1个，最后更新为 -> 1  1  1  1，
         */
        if (paths == null || paths.length == 0) {
            return;
        }

        // citiesPath —> distancesArray
        pathsToDistances(paths);

        // distanceArray -> countsArray
        distancesToNums(paths);
    }

    public static void pathsToDistances(int[] paths) {
        int cap = 0;
        for (int start = 0; start != paths.length; start++) {
            if (paths[start] == start) {
                cap = start;
            } else if (paths[start] > -1) {
                int curI = paths[start];
                paths[start] = -1;
                int preI = start;
                while (paths[curI] != curI) {
                    if (paths[curI] > -1) {
                        int nextI = paths[curI];
                        paths[curI] = preI;
                        preI = curI;
                        curI = nextI;
                    } else {
                        break;
                    }
                }
                int value = paths[curI] == curI ? 0 : paths[curI];
                while (paths[preI] != -1) {
                    int lastPreI = paths[preI];
                    paths[preI] = --value;
                    curI = preI;
                    preI = lastPreI;
                }
                paths[preI] = --value;
            }
        }
        paths[cap] = 0;
    }

    public static void distancesToNums(int[] disArr) {
        for (int i = 0; i != disArr.length; i++) {
            int index = disArr[i];
            if (index < 0) {
                disArr[i] = 0;
                while (true) {
                    index = -index;
                    if (disArr[index] > -1) {
                        disArr[index]++;
                        break;
                    } else {
                        int nextIndex = disArr[index];
                        disArr[index] = 1;
                        index = nextIndex;
                    }
                }
            }
        }
        disArr[0] = 1;
    }

    public static void printArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] paths = { 9, 1, 4, 9, 0, 4, 8, 9, 0, 1 };
        printArray(paths);
        pathsToNums(paths);
        printArray(paths);

    }
}
