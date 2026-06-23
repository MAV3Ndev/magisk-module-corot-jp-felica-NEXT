package androidx.room.concurrent;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Synchronized.jvmAndroid.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a1\u0010\u0002\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u00032\n\u0010\u0004\u001a\u00060\u0001j\u0002`\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0007H\u0080\bø\u0001\u0000¢\u0006\u0002\u0010\b*\f\b\u0000\u0010\u0000\"\u00020\u00012\u00020\u0001\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\t"}, d2 = {"SynchronizedObject", "", "synchronized", "T", "lock", "Landroidx/room/concurrent/SynchronizedObject;", "block", "Lkotlin/Function0;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "room-runtime_release"}, k = 2, mv = {2, 0, 0}, xi = 48)
public final class Synchronized_jvmAndroidKt {
    /* JADX INFO: renamed from: synchronized, reason: not valid java name */
    public static final <T> T m317synchronized(Object lock, Function0<? extends T> block) {
        T tInvoke;
        Intrinsics.checkNotNullParameter(lock, "lock");
        Intrinsics.checkNotNullParameter(block, "block");
        synchronized (lock) {
            tInvoke = block.invoke();
        }
        return tInvoke;
    }
}
