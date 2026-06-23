package com.amazonaws.transform;

import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class MapEntry<K, V> implements Map.Entry<K, V> {
    private K key;
    private V value;

    @Override // java.util.Map.Entry
    public K getKey() {
        return this.key;
    }

    @Override // java.util.Map.Entry
    public V getValue() {
        return this.value;
    }

    @Override // java.util.Map.Entry
    public V setValue(V v) {
        this.value = v;
        return v;
    }

    public K setKey(K k) {
        this.key = k;
        return k;
    }
}
