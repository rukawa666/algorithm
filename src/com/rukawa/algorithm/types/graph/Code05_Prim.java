package com.rukawa.algorithm.types.graph;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-02-03 0:27
 * @Version：1.0
 */
public class Code05_Prim {

    // 无向图，最小生成树
    // p算法，贪心实现
    /**
     * 思路：
     *  1、可以从任意节点出发寻找最小生成树
     *  2、某个点加入到被选取的点中后，解锁这个点出发的所有新的边
     *  3、在所有解锁的边中选择最小的边，然后看看这个边会不会形成环
     *  4、如果会，不要当前边，继续考察剩下解锁的边中最小的边，重复3步骤
     *  5、如果不会，要当前边，将该边的指向点加入到被选取的点中，重复2步骤
     *  6、当所有点都被选取，最小生成树就得到了
     */
    public static Set<Edge> primMST(Graph graph) {
        Set<Edge> res = new HashSet<>();
        return res;
    }

}
