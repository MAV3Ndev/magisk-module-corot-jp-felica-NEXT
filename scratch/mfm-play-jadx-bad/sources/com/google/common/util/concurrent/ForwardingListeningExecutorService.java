package com.google.common.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import kotlin.UByte$$ExternalSyntheticBackport0;

/* JADX INFO: loaded from: classes4.dex */
@ElementTypesAreNonnullByDefault
public abstract class ForwardingListeningExecutorService extends ForwardingExecutorService implements ListeningExecutorService, AutoCloseable {
    @Override // com.google.common.util.concurrent.ForwardingExecutorService, java.lang.AutoCloseable
    public /* synthetic */ void close() {
        UByte$$ExternalSyntheticBackport0.m((ExecutorService) this);
    }

    /* JADX DEBUG: Method merged with bridge method: delegate()Ljava/lang/Object; */
    /* JADX DEBUG: Method merged with bridge method: delegate()Ljava/util/concurrent/ExecutorService; */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.util.concurrent.ForwardingExecutorService, com.google.common.collect.ForwardingObject
    public abstract ListeningExecutorService delegate();

    protected ForwardingListeningExecutorService() {
    }

    /* JADX DEBUG: Method merged with bridge method: submit(Ljava/util/concurrent/Callable;)Ljava/util/concurrent/Future; */
    @Override // com.google.common.util.concurrent.ForwardingExecutorService, java.util.concurrent.ExecutorService
    public <T> ListenableFuture<T> submit(Callable<T> callable) {
        return delegate().submit((Callable) callable);
    }

    /* JADX DEBUG: Method merged with bridge method: submit(Ljava/lang/Runnable;)Ljava/util/concurrent/Future; */
    @Override // com.google.common.util.concurrent.ForwardingExecutorService, java.util.concurrent.ExecutorService
    public ListenableFuture<?> submit(Runnable runnable) {
        return delegate().submit(runnable);
    }

    /* JADX DEBUG: Method merged with bridge method: submit(Ljava/lang/Runnable;Ljava/lang/Object;)Ljava/util/concurrent/Future; */
    @Override // com.google.common.util.concurrent.ForwardingExecutorService, java.util.concurrent.ExecutorService
    public <T> ListenableFuture<T> submit(Runnable runnable, @ParametricNullness T t) {
        return delegate().submit(runnable, (Object) t);
    }
}
