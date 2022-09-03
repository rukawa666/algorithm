package com.rukawa.algorithm.base.class04;

import java.util.*;

/**
 * create by hqh on 2022/8/28
 */
public class Code06_Test {

    public static class Customer {
        private int id;
        private int buy;
        private int enterTime;

        public Customer(int id, int buy, int enterTime) {
            id = id;
            buy = buy;
            enterTime = enterTime;
        }
    }

    public static class CandidateComparator implements Comparator<Customer> {

        @Override
        public int compare(Customer c1, Customer c2) {
            return c1.buy != c2.buy ? (c2.buy - c1.buy) : (c1.enterTime - c2.enterTime);
        }
    }

    public static class DaddyComparator implements Comparator<Customer> {
        @Override
        public int compare(Customer c1, Customer c2) {
            return c1.buy != c2.buy ? (c1.buy - c2.buy) : (c1.enterTime - c2.enterTime);
        }
    }

    public static class WhosoeverYourDaddy {
        private HashMap<Integer, Customer> customers;
        private MyHeapGreater<Customer> candHeap;
        private MyHeapGreater<Customer> daddyHeap;
        private final int daddyLimit;

        public WhosoeverYourDaddy(int limit) {
            customers = new HashMap<>();
            candHeap = new MyHeapGreater<>(new CandidateComparator());
            daddyHeap = new MyHeapGreater<>(new DaddyComparator());
            daddyLimit = limit;
        }

        public void operate(int index, int id, boolean buyOrRefund) {
            if (!buyOrRefund && !customers.containsKey(id)) {
                return;
            }
            if (!customers.containsKey(id)) {
                customers.put(id, new Customer(id, 0, 0));
            }
            Customer customer = customers.get(id);
            if (buyOrRefund) {
                customer.buy++;
            } else {
                customer.buy--;
            }
            if (customer.buy == 0) {
                customers.remove(id);
            }

            if (!candHeap.isEmpty() && !daddyHeap.isEmpty()) {
                if (daddyHeap.size() < daddyLimit) {
                    customer.enterTime = index;
                    daddyHeap.push(customer);
                } else {
                    customer.enterTime = index;
                    candHeap.push(customer);
                }
            } else if (candHeap.contains(customer)) {
                if (customer.buy == 0) {
                    candHeap.remove(customer);
                } else {
                    candHeap.resign(customer);
                }
            } else {
                if (customer.buy == 0) {
                    daddyHeap.resign(customer);
                } else {
                    daddyHeap.resign(customer);
                }
            }
            daddyMove(index);
        }

        public List<Integer> getDaddies() {
            List<Integer> res = new ArrayList<>();
            List<Customer> allElements = daddyHeap.getAllElements();
            for (Customer customer : allElements) {
                res.add(customer.id);
            }
            return res;
        }

        private void daddyMove(int time) {
            if (candHeap.isEmpty()) {
                return;
            } else if (daddyHeap.size() < daddyLimit) {
                Customer newCustomer = candHeap.pop();
                newCustomer.enterTime = time;
                daddyHeap.push(newCustomer);
            } else {
                if (candHeap.peek().buy > daddyHeap.peek().buy) {
                    Customer newCustomer = candHeap.pop();
                    Customer oldCustomer = daddyHeap.pop();

                    oldCustomer.enterTime = time;
                    candHeap.push(oldCustomer);

                    newCustomer.enterTime = time;
                    daddyHeap.push(newCustomer);
                }
            }
        }
    }

    public static List<List<Integer>> topK(int[] arr, boolean[] op, int k) {
        List<List<Integer>> res = new ArrayList<>();
        WhosoeverYourDaddy whoDaddies = new WhosoeverYourDaddy(k);
        for (int i = 0; i < arr.length; i++) {
            whoDaddies.operate(i, arr[i], op[i]);
            res.add(whoDaddies.getDaddies());
        }
        return res;
    }

    public static List<List<Integer>> compare(int[] arr, boolean[] op, int k) {
        HashMap<Integer, Customer> map = new HashMap<>();
        ArrayList<Customer> cands = new ArrayList<>();
        ArrayList<Customer> daddy = new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            int id = arr[i];
            boolean buyOrRefund = op[i];
            if (!buyOrRefund && !map.containsKey(id)) {
                res.add(getCurRes(daddy));
                continue;
            }

            if (!map.containsKey(id)) {
                map.put(id, new Customer(id, 0, 0));
            }

            Customer customer = map.get(id);
            if (buyOrRefund) {
                customer.buy++;
            } else {
                customer.buy--;
            }

            if (customer.buy == 0) {
                map.remove(id);
            }

            if (!cands.contains(customer) && !daddy.contains(customer)) {
                if (cands.size() < k) {
                    customer.enterTime = i;
                    daddy.add(customer);
                } else {
                    customer.enterTime = i;
                    cands.add(customer);
                }
            }
            cleanZeroBuy(cands);
            cleanZeroBuy(daddy);
            cands.sort(new CandidateComparator());
            daddy.sort(new DaddyComparator());
            moveCandsToDaddy(cands, daddy, k, i);
            res.add(getCurRes(daddy));
        }
        return res;
    }

    public static void cleanZeroBuy(ArrayList<Customer> list) {
        List<Customer> copy = new ArrayList<>();
        for (Customer customer : list) {
            if (customer.buy == 0) {
                continue;
            }
            copy.add(customer);
        }
        list.clear();
        for (Customer customer : copy) {
            list.add(customer);
        }
    }

    public static List<Integer> getCurRes(List<Customer> list) {
        List<Integer> res = new ArrayList<>();
        for (Customer customer : list) {
            res.add(customer.id);
        }
        return res;
    }

    public static void moveCandsToDaddy(ArrayList<Customer> cands, ArrayList<Customer> daddy, int k, int i) {
        if (cands.isEmpty()) {
            return;
        }
        if (daddy.size() < k) {
            Customer candsCustomer = cands.get(0);
            candsCustomer.enterTime = i;
            daddy.add(candsCustomer);
            cands.remove(0);
        } else {
            Customer newCustomer = cands.get(0);
            Customer oldCustomer = daddy.get(0);
            if (newCustomer.buy > oldCustomer.buy) {
                daddy.remove(0);
                cands.remove(0);

                newCustomer.enterTime = i;
                daddy.add(newCustomer);

                oldCustomer.enterTime = i;
                cands.add(oldCustomer);
            }
        }
    }


    public static class MyHeapGreater<T> {
        private ArrayList<T> heap;
        private HashMap<T, Integer> indexMap;
        private int heapSize;
        private Comparator<? super T> comp;

        public MyHeapGreater(Comparator<T> c) {
            heap = new ArrayList<>();
            indexMap = new HashMap<>();
            heapSize = 0;
            comp = c;
        }

        private void heapInsert(int index) {
            while (comp.compare(heap.get(index), heap.get((index - 1) >> 1)) < 0) {
                swap(index, (index - 1) >> 1);
                index = (index - 1) >> 1;
            }
        }

        private void heapify(int index) {
            int left = index << 1 + 1;
            while (left < heapSize) {
                int best = left + 1 < heapSize && comp.compare(heap.get(left + 1), heap.get(left)) < 0 ? left + 1 :
                        left;
                best = comp.compare(heap.get(best), heap.get(index)) < 0 ? best : index;
                if (best == index) {
                    break;
                }
                swap(best, index);
                index = best;
                left = index << 1 + 1;
            }
        }

        private void swap(int i, int j) {
            T a = heap.get(i);
            T b = heap.get(j);
            indexMap.put(a, j);
            indexMap.put(b, i);
            heap.set(i, b);
            heap.set(j, a);
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public int size() {
            return heapSize;
        }

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

        public void resign(T obj) {
            heapInsert(indexMap.get(obj));
            heapify(indexMap.get(obj));
        }

        public void remove(T obj) {
            T replace = heap.get(heapSize - 1);
            int position = indexMap.get(obj);
            indexMap.remove(obj);
            heap.remove(--heapSize);
            if (obj != replace) {
                indexMap.put(replace, position);
                heap.set(position, replace);
                resign(replace);
            }
        }

        public List<T> getAllElements() {
            List<T> res = new ArrayList<>();
            for (T t : heap) {
                res.add(t);
            }
            return res;
        }
    }
}
