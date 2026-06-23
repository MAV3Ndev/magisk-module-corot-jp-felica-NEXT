package androidx.room.util;

import kotlin.Metadata;

/* JADX INFO: compiled from: SQLiteUtil.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\bÀ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Landroidx/room/util/SQLiteResultCode;", "", "<init>", "()V", "SQLITE_ERROR", "", "SQLITE_BUSY", "SQLITE_MISUSE", "room-runtime_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
public final class SQLiteResultCode {
    public static final SQLiteResultCode INSTANCE = new SQLiteResultCode();
    public static final int SQLITE_BUSY = 5;
    public static final int SQLITE_ERROR = 1;
    public static final int SQLITE_MISUSE = 21;

    private SQLiteResultCode() {
    }
}
