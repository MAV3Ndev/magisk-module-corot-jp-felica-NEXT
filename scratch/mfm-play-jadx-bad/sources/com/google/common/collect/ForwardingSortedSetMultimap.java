package com.google.common.collect;

import java.util.Comparator;
import java.util.SortedSet;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes4.dex */
@ElementTypesAreNonnullByDefault
public abstract class ForwardingSortedSetMultimap<K, V> extends ForwardingSetMultimap<K, V> implements SortedSetMultimap<K, V> {
    /* JADX DEBUG: Method merged with bridge method: delegate()Lcom/google/common/collect/Multimap; */
    /* JADX DEBUG: Method merged with bridge method: delegate()Lcom/google/common/collect/SetMultimap; */
    /* JADX DEBUG: Method merged with bridge method: delegate()Ljava/lang/Object; */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.collect.ForwardingSetMultimap, com.google.common.collect.ForwardingMultimap, com.google.common.collect.ForwardingObject
    public abstract SortedSetMultimap<K, V> delegate();

    protected ForwardingSortedSetMultimap() {
    }

    /* JADX DEBUG: Method merged with bridge method: get(Ljava/lang/Object;)Ljava/util/Collection; */
    /* JADX DEBUG: Method merged with bridge method: get(Ljava/lang/Object;)Ljava/util/Set; */
    @Override // com.google.common.collect.ForwardingSetMultimap, com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
    public SortedSet<V> get(@ParametricNullness K k) {
        return delegate().get((Object) k);
    }

    /* JADX DEBUG: Method merged with bridge method: removeAll(Ljava/lang/Object;)Ljava/util/Collection; */
    /* JADX DEBUG: Method merged with bridge method: removeAll(Ljava/lang/Object;)Ljava/util/Set; */
    @Override // com.google.common.collect.ForwardingSetMultimap, com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
    public SortedSet<V> removeAll(@CheckForNull Object obj) {
        return delegate().removeAll(obj);
    }

    /* JADX DEBUG: Method merged with bridge method: replaceValues(Ljava/lang/Object;Ljava/lang/Iterable;)Ljava/util/Collection; */
    /* JADX DEBUG: Method merged with bridge method: replaceValues(Ljava/lang/Object;Ljava/lang/Iterable;)Ljava/util/Set; */
    @Override // com.google.common.collect.ForwardingSetMultimap, com.google.common.collect.ForwardingMultimap, com.google.common.collect.Multimap
    public SortedSet<V> replaceValues(@ParametricNullness K k, Iterable<? extends V> iterable) {
        return delegate().replaceValues((Object) k, (Iterable) iterable);
    }

    @Override // com.google.common.collect.SortedSetMultimap
    @CheckForNull
    public Comparator<? super V> valueComparator() {
        return delegate().valueComparator();
    }
}
