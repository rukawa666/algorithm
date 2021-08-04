package com.rukawa.algorithm.types.advanced;

import java.util.HashMap;

/**
 * @className: Code_B5_SetAll
 * @description: TODO 类描述
 * @author: 鎏川疯
 * @date: 2021/8/5 0005 7:13
 **/
public class Code_B5_SetAll {

    public static class MyValue<V> {
        public V value;
        public long time;

        public MyValue(V value, long time) {
            this.value = value;
            this.time = time;
        }
    }

    public static class MyHashMap<K, V> {
        private HashMap<K, MyValue<V>> map;
        public long time;
        private MyValue<V> setAll;

        public MyHashMap() {
            map = new HashMap<>();
            time = 0;
            setAll = new MyValue<>(null, -1);
        }

        public void put(K key, V val) {
            map.put(key, new MyValue<V>(val, time++));
        }

        public void setAll(V value) {
            setAll = new MyValue<>(value, time++);
        }

        public V get(K key) {
            if (!map.containsKey(key)) {
                return null;
            }
            if (map.get(key).time > setAll.time) {
                return map.get(key).value;
            } else {
                return setAll.value;
            }
        }
    }
}
