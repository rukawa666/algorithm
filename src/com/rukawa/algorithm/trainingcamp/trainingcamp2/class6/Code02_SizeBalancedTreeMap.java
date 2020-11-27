package com.rukawa.algorithm.trainingcamp.trainingcamp2.class6;

import java.util.ArrayList;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-08-13 22:45
 * @Version：1.0
 */
public class Code02_SizeBalancedTreeMap {

    public static class SizeBalancedTreeMap<K extends Comparable<K>, V> {
        private int root;
        private int len;
        private int[] left;
        private int[] right;
        private int[] size;
        private ArrayList<K> keys;
        private ArrayList<V> values;

        public SizeBalancedTreeMap(int init) {
            left = new int[init + 1];
            right = new int[init + 1];
            size = new int[init + 1];
            keys = new ArrayList<>();
            values = new ArrayList<>();
            keys.add(null);
            values.add(null);
            root = 0;
            len = 0;
        }
    }
}
