package androidx.room.coroutines;

import androidx.collection.CircularArray;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import com.felicanetworks.mfc.mfi.LogSender;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.TimeoutCancellationException;
import kotlinx.coroutines.TimeoutKt;
import kotlinx.coroutines.sync.Semaphore;
import kotlinx.coroutines.sync.SemaphoreKt;

/* JADX INFO: compiled from: ConnectionPoolImpl.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0004\b\u0007\u0010\bJ&\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u001e2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u0005H\u0086@¢\u0006\u0004\b!\u0010\"J\u000e\u0010#\u001a\u00020\u0016H\u0086@¢\u0006\u0002\u0010$J\b\u0010%\u001a\u00020 H\u0002J\u000e\u0010&\u001a\u00020 2\u0006\u0010'\u001a\u00020\u0016J\u0006\u0010(\u001a\u00020 J\u0012\u0010)\u001a\u00020 2\n\u0010*\u001a\u00060+j\u0002`,R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00060\u000ej\u0002`\u000fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00160\u0015X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00160\u001bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Landroidx/room/coroutines/Pool;", "", "capacity", "", "connectionFactory", "Lkotlin/Function0;", "Landroidx/sqlite/SQLiteConnection;", "<init>", "(ILkotlin/jvm/functions/Function0;)V", "getCapacity", "()I", "getConnectionFactory", "()Lkotlin/jvm/functions/Function0;", "lock", "Ljava/util/concurrent/locks/ReentrantLock;", "Landroidx/room/concurrent/ReentrantLock;", "Ljava/util/concurrent/locks/ReentrantLock;", "size", "isClosed", "", "connections", "", "Landroidx/room/coroutines/ConnectionWithLock;", "[Landroidx/room/coroutines/ConnectionWithLock;", "connectionPermits", "Lkotlinx/coroutines/sync/Semaphore;", "availableConnections", "Landroidx/collection/CircularArray;", "acquireWithTimeout", "timeout", "Lkotlin/time/Duration;", "onTimeout", "", "acquireWithTimeout-KLykuaI", "(JLkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "acquire", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "tryOpenNewConnectionLocked", "recycle", "connection", "close", LogSender.EXTRA_VALUE_LOG_TYPE_DUMP, "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "room-runtime_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
final class Pool {
    private final CircularArray<ConnectionWithLock> availableConnections;
    private final int capacity;
    private final Function0<SQLiteConnection> connectionFactory;
    private final Semaphore connectionPermits;
    private final ConnectionWithLock[] connections;
    private boolean isClosed;
    private final ReentrantLock lock;
    private int size;

    /* JADX INFO: renamed from: androidx.room.coroutines.Pool$acquire$1, reason: invalid class name */
    /* JADX INFO: compiled from: ConnectionPoolImpl.kt */
    @Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
    @DebugMetadata(c = "androidx.room.coroutines.Pool", f = "ConnectionPoolImpl.kt", i = {0}, l = {231}, m = "acquire", n = {"this"}, s = {"L$0"})
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return Pool.this.acquire(this);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: kotlin.jvm.functions.Function0<? extends androidx.sqlite.SQLiteConnection> */
    /* JADX WARN: Multi-variable type inference failed */
    public Pool(int i, Function0<? extends SQLiteConnection> connectionFactory) {
        Intrinsics.checkNotNullParameter(connectionFactory, "connectionFactory");
        this.capacity = i;
        this.connectionFactory = connectionFactory;
        this.lock = new ReentrantLock();
        this.connections = new ConnectionWithLock[i];
        this.connectionPermits = SemaphoreKt.Semaphore$default(i, 0, 2, null);
        this.availableConnections = new CircularArray<>(i);
    }

    public final int getCapacity() {
        return this.capacity;
    }

    public final Function0<SQLiteConnection> getConnectionFactory() {
        return this.connectionFactory;
    }

    /* JADX DEBUG: Duplicate block (B:27:0x0070) to fix multi-entry loop: BACK_EDGE: B:27:0x0070 -> B:43:0x0075 */
    /* JADX DEBUG: Multi-variable search result rejected for r10v0, resolved type: T */
    /* JADX WARN: Can't wrap try/catch for region: R(12:11|44|12|24|43|28|(1:(1:37)(2:32|(2:34|35)))(1:30)|36|19|46|20|(1:22)(10:23|24|43|28|(0)(0)|36|19|46|20|(0)(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x006b, code lost:
    
        r2 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0070, code lost:
    
        r12 = r12;
        r11 = r11;
        r2 = r0;
        r0 = r2;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0063 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0079 A[Catch: all -> 0x0089, TryCatch #0 {all -> 0x0089, blocks: (B:28:0x0075, B:30:0x0079, B:32:0x007f, B:34:0x0083, B:37:0x0088), top: B:43:0x0075 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x0064 -> B:24:0x0066). Please report as a decompilation issue!!! */
    /* JADX INFO: renamed from: acquireWithTimeout-KLykuaI, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m321acquireWithTimeoutKLykuaI(long j, Function0<Unit> function0, Continuation<? super ConnectionWithLock> continuation) throws Throwable {
        Pool$acquireWithTimeout$1 pool$acquireWithTimeout$1;
        Pool pool;
        Ref.ObjectRef objectRef;
        Pool$acquireWithTimeout$1 pool$acquireWithTimeout$12;
        Throwable th;
        Pool$acquireWithTimeout$2 pool$acquireWithTimeout$2;
        if (continuation instanceof Pool$acquireWithTimeout$1) {
            pool$acquireWithTimeout$1 = (Pool$acquireWithTimeout$1) continuation;
            if ((pool$acquireWithTimeout$1.label & Integer.MIN_VALUE) != 0) {
                pool$acquireWithTimeout$1.label -= Integer.MIN_VALUE;
            } else {
                pool$acquireWithTimeout$1 = new Pool$acquireWithTimeout$1(this, continuation);
            }
        }
        Object obj = pool$acquireWithTimeout$1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = pool$acquireWithTimeout$1.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            pool = this;
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            pool$acquireWithTimeout$2 = new Pool$acquireWithTimeout$2(objectRef2, pool, null);
            pool$acquireWithTimeout$1.L$0 = pool;
            pool$acquireWithTimeout$1.L$1 = function0;
            pool$acquireWithTimeout$1.L$2 = objectRef2;
            pool$acquireWithTimeout$1.J$0 = j;
            pool$acquireWithTimeout$1.label = 1;
            if (TimeoutKt.m2166withTimeoutKLykuaI(j, pool$acquireWithTimeout$2, pool$acquireWithTimeout$1) != coroutine_suspended) {
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            j = pool$acquireWithTimeout$1.J$0;
            Ref.ObjectRef objectRef3 = (Ref.ObjectRef) pool$acquireWithTimeout$1.L$2;
            Function0<Unit> function02 = (Function0) pool$acquireWithTimeout$1.L$1;
            pool = (Pool) pool$acquireWithTimeout$1.L$0;
            try {
                try {
                    ResultKt.throwOnFailure(obj);
                } catch (Throwable th2) {
                    objectRef = objectRef3;
                    function0 = function02;
                    pool$acquireWithTimeout$12 = pool$acquireWithTimeout$1;
                    th = th2;
                    if (th instanceof TimeoutCancellationException) {
                    }
                    pool$acquireWithTimeout$1 = pool$acquireWithTimeout$12;
                    Ref.ObjectRef objectRef22 = new Ref.ObjectRef();
                    pool$acquireWithTimeout$2 = new Pool$acquireWithTimeout$2(objectRef22, pool, null);
                    pool$acquireWithTimeout$1.L$0 = pool;
                    pool$acquireWithTimeout$1.L$1 = function0;
                    pool$acquireWithTimeout$1.L$2 = objectRef22;
                    pool$acquireWithTimeout$1.J$0 = j;
                    pool$acquireWithTimeout$1.label = 1;
                    if (TimeoutKt.m2166withTimeoutKLykuaI(j, pool$acquireWithTimeout$2, pool$acquireWithTimeout$1) != coroutine_suspended) {
                    }
                }
                if (th instanceof TimeoutCancellationException) {
                    function0.invoke();
                } else {
                    if (th != null) {
                        throw th;
                    }
                    if (objectRef.element != 0) {
                        return objectRef.element;
                    }
                }
                pool$acquireWithTimeout$1 = pool$acquireWithTimeout$12;
                Ref.ObjectRef objectRef222 = new Ref.ObjectRef();
                pool$acquireWithTimeout$2 = new Pool$acquireWithTimeout$2(objectRef222, pool, null);
                pool$acquireWithTimeout$1.L$0 = pool;
                pool$acquireWithTimeout$1.L$1 = function0;
                pool$acquireWithTimeout$1.L$2 = objectRef222;
                pool$acquireWithTimeout$1.J$0 = j;
                pool$acquireWithTimeout$1.label = 1;
                if (TimeoutKt.m2166withTimeoutKLykuaI(j, pool$acquireWithTimeout$2, pool$acquireWithTimeout$1) != coroutine_suspended) {
                    return coroutine_suspended;
                }
                function02 = function0;
                objectRef3 = objectRef222;
                objectRef = objectRef3;
                function0 = function02;
                pool$acquireWithTimeout$12 = pool$acquireWithTimeout$1;
                th = null;
                if (th instanceof TimeoutCancellationException) {
                }
                pool$acquireWithTimeout$1 = pool$acquireWithTimeout$12;
                Ref.ObjectRef objectRef2222 = new Ref.ObjectRef();
                pool$acquireWithTimeout$2 = new Pool$acquireWithTimeout$2(objectRef2222, pool, null);
                pool$acquireWithTimeout$1.L$0 = pool;
                pool$acquireWithTimeout$1.L$1 = function0;
                pool$acquireWithTimeout$1.L$2 = objectRef2222;
                pool$acquireWithTimeout$1.J$0 = j;
                pool$acquireWithTimeout$1.label = 1;
                if (TimeoutKt.m2166withTimeoutKLykuaI(j, pool$acquireWithTimeout$2, pool$acquireWithTimeout$1) != coroutine_suspended) {
                }
            } catch (Throwable th3) {
                ConnectionWithLock connectionWithLock = (ConnectionWithLock) objectRef.element;
                if (connectionWithLock != null) {
                    pool.recycle(connectionWithLock);
                }
                throw th3;
            }
            objectRef = objectRef3;
            function0 = function02;
            pool$acquireWithTimeout$12 = pool$acquireWithTimeout$1;
            th = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object acquire(Continuation<? super ConnectionWithLock> continuation) throws Throwable {
        AnonymousClass1 anonymousClass1;
        Pool pool;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            if ((anonymousClass1.label & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label -= Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = anonymousClass1.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Semaphore semaphore = this.connectionPermits;
            anonymousClass1.L$0 = this;
            anonymousClass1.label = 1;
            if (semaphore.acquire(anonymousClass1) == coroutine_suspended) {
                return coroutine_suspended;
            }
            pool = this;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            pool = (Pool) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        try {
            ReentrantLock reentrantLock = pool.lock;
            reentrantLock.lock();
            try {
                if (pool.isClosed) {
                    SQLite.throwSQLiteException(21, "Connection pool is closed");
                    throw new KotlinNothingValueException();
                }
                if (pool.availableConnections.isEmpty()) {
                    pool.tryOpenNewConnectionLocked();
                }
                return pool.availableConnections.popFirst();
            } finally {
                reentrantLock.unlock();
            }
        } catch (Throwable th) {
            pool.connectionPermits.release();
            throw th;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v2, resolved type: java.lang.Object[] */
    /* JADX WARN: Multi-variable type inference failed */
    private final void tryOpenNewConnectionLocked() {
        if (this.size >= this.capacity) {
            return;
        }
        ConnectionWithLock connectionWithLock = new ConnectionWithLock(this.connectionFactory.invoke(), null, 2, 0 == true ? 1 : 0);
        ConnectionWithLock[] connectionWithLockArr = this.connections;
        int i = this.size;
        this.size = i + 1;
        connectionWithLockArr[i] = connectionWithLock;
        this.availableConnections.addLast(connectionWithLock);
    }

    public final void recycle(ConnectionWithLock connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            this.availableConnections.addLast(connection);
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            this.connectionPermits.release();
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    public final void close() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            this.isClosed = true;
            for (ConnectionWithLock connectionWithLock : this.connections) {
                if (connectionWithLock != null) {
                    connectionWithLock.close();
                }
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            reentrantLock.unlock();
        }
    }

    public final void dump(StringBuilder builder) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            List listCreateListBuilder = CollectionsKt.createListBuilder();
            int size = this.availableConnections.size();
            for (int i = 0; i < size; i++) {
                listCreateListBuilder.add(this.availableConnections.get(i));
            }
            List listBuild = CollectionsKt.build(listCreateListBuilder);
            builder.append('\t' + super.toString() + " (");
            builder.append("capacity=" + this.capacity + ", ");
            builder.append("permits=" + this.connectionPermits.getAvailablePermits() + ", ");
            builder.append("queue=(size=" + listBuild.size() + ")[" + CollectionsKt.joinToString$default(listBuild, null, null, null, 0, null, null, 63, null) + "], ");
            builder.append(")");
            builder.append('\n');
            ConnectionWithLock[] connectionWithLockArr = this.connections;
            int length = connectionWithLockArr.length;
            int i2 = 0;
            for (int i3 = 0; i3 < length; i3++) {
                ConnectionWithLock connectionWithLock = connectionWithLockArr[i3];
                i2++;
                StringBuilder sb = new StringBuilder();
                sb.append("\t\t[");
                sb.append(i2);
                sb.append("] - ");
                sb.append(connectionWithLock != null ? connectionWithLock.toString() : null);
                builder.append(sb.toString());
                builder.append('\n');
                if (connectionWithLock != null) {
                    connectionWithLock.dump(builder);
                }
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            reentrantLock.unlock();
        }
    }
}
