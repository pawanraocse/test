package com.practise.leetcode;

import java.util.HashMap;
import java.util.Map;

/*
* Input
["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
Output
[null, null, null, 1, null, -1, null, -1, 3, 4]

Explanation
LRUCache lRUCache = new LRUCache(2);
lRUCache.put(1, 1); // cache is {1=1}
lRUCache.put(2, 2); // cache is {1=1, 2=2}
lRUCache.get(1);    // return 1
lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
lRUCache.get(2);    // returns -1 (not found)
lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
lRUCache.get(1);    // return -1 (not found)
lRUCache.get(3);    // return 3
lRUCache.get(4);    // return 4
*
* */
class LRUCache {

    private final int capacity;
    ListNode head;
    ListNode tail;
    Map<Integer, ListNode> keyValueMap;

    LRUCache(int capacity) {
        this.capacity = capacity;
        head = new ListNode(-1, -1);
        tail = new ListNode(-1, -1);
        head.next = tail;
        tail.prev = head;
        keyValueMap = new HashMap<>();
    }

    public void put(int key, int value) {
        if (keyValueMap.containsKey(key)) {
            removeNode(keyValueMap.get(key));
        }
        addNode(key, value);
        if (keyValueMap.size() > this.capacity) {
            removeNode(tail.prev);
        }
    }

    public int get(int key) {
        final ListNode listNode = keyValueMap.get(key);
        if (listNode == null) {
            return -1;
        }
        removeNode(listNode);
        addNode(listNode.key, listNode.val);
        return listNode.val;
    }

    private void addNode(int key, int value) {
        ListNode node = new ListNode(key, value);
        final ListNode next = head.next;
        node.prev = head;
        node.next = next;

        head.next = node;
        next.prev = node;
        keyValueMap.put(key, node);
    }

    private void removeNode(ListNode node) {
        keyValueMap.remove(node.key);
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    class ListNode {

        ListNode prev;
        ListNode next;
        int val;
        int key;

        ListNode(int key, int val) {
            this.val = val;
            this.key = key;
        }
    }
}
