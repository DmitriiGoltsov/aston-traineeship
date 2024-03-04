package org.goltsov.aston.second.homework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomHashMap<K, V> implements Map<K, V> {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    private Node<K, V>[] table;

    private final double loadFactor = 0.75;

    private int threshold;

    private int size;

    public CustomHashMap() {
        this.table = new Node[DEFAULT_INITIAL_CAPACITY];
        this.threshold = (int) (table.length * loadFactor);
        this.size = 0;
    }

    public CustomHashMap(int capacity) {
        this.table = new Node[capacity];
        this.threshold = (int) (capacity * loadFactor);
        this.size = 0;
    }

    static class Node<K, V> implements Map.Entry<K, V> {

        final int hash;

        final K key;

        V value;

        Node<K, V> nextNode;

        public Node(int hash, K key, V value, Node<K, V> nextNode) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.nextNode = nextNode;
        }

        public Node(int hash, K key, V value) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.nextNode = null;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public String toString() {
            return key + " = " + value;
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int hash = key.hashCode() % DEFAULT_INITIAL_CAPACITY;
        Node<K, V> bucket = table[hash];

        while (bucket != null) {
            if (bucket.key.equals(key)) {
                return true;
            }
            bucket = bucket.nextNode;
        }

        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        int hash = key.hashCode() % DEFAULT_INITIAL_CAPACITY;
        Node<K, V> bucket = table[hash];

        while (bucket != null) {
            if (bucket.key.equals(key)) {
                return bucket.value;
            }
            bucket = bucket.nextNode;
        }

        return null;
    }

    @Override
    public V put(K key, V value) {
        if (size >= threshold) {
            this.enlargeTable();
        }

        int hash = key.hashCode() % DEFAULT_INITIAL_CAPACITY;
        Node<K, V> bucket = table[hash];
        Node<K, V> newNode = new Node<>(hash, key, value);

        if (bucket == null) {
            table[hash] = newNode;
            size++;
            return null;
        } else {
            if (bucket.key.equals(key)) {
                V oldValue = bucket.value;
                bucket.value = value;
                return oldValue;
            } else {
                while (bucket.nextNode != null) {
                    bucket = bucket.nextNode;
                }
                bucket.nextNode = newNode;
                size++;
                return null;
            }
        }
    }

    @Override
    public V remove(Object key) {
        final int hash = key.hashCode() % DEFAULT_INITIAL_CAPACITY;
        Node<K, V> bucket = table[hash];

        while (bucket != null) {
            if (bucket.key.equals(key)) {
                Node<K, V> oldEntry = bucket;
                bucket = bucket.nextNode;
                table[hash] = bucket;
                size--;
                return oldEntry.value;
            }
            bucket = bucket.nextNode;
        }

        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

        if (m == null) {
            throw new NullPointerException("Adding map is null");
        } else if (m.isEmpty()) {
            return;
        }

        var entrySet = m.entrySet();
        for (var entry : entrySet) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        table = new Node[DEFAULT_INITIAL_CAPACITY];
        threshold = (int) (table.length * loadFactor);
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> results = new HashSet<>();
        for (Node<K, V> item : table) {
            while (item != null) {
                results.add(item.key);
                item = item.nextNode;
            }
        }

        return results;
    }

    @Override
    public Collection<V> values() {
        List<V> valueList = new ArrayList<>();
        for (Node<K, V> item : table) {
            while (item != null) {
                valueList.add(item.getValue());
                item = item.nextNode;
            }
        }

        return valueList;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> results = new HashSet<>();
        for (int i = 0; i < table.length - 1; i++) {
            Node<K, V> bucket = table[i];
            while (bucket != null) {
                results.add(bucket);
                bucket = bucket.nextNode;
            }
        }

        return results;
    }

    private void enlargeTable() {
        Node<K, V>[] oldTable = table;
        table = new Node[DEFAULT_INITIAL_CAPACITY * 2];
        this.threshold = (int) (loadFactor * table.length);
        for (Node<K, V> item : oldTable) {
            while (item != null) {
                this.put(item.key, item.value);
                item = item.nextNode;
            }
        }
    }
}
