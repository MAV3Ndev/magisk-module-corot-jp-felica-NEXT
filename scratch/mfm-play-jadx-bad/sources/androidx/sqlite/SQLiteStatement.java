package androidx.sqlite;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;

/* JADX INFO: compiled from: SQLiteStatement.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010 \n\u0002\b\u0006\bf\u0018\u00002\u00060\u0001j\u0002`\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&J\u001a\u0010\t\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\nH&J\u001a\u0010\u000b\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\fH\u0016J\u001a\u0010\r\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u000eH&J\u001a\u0010\u000f\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\u001a\u0010\u0010\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0011H\u0016J\u001a\u0010\u0012\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0013H&J\u0012\u0010\u0014\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u0006H&J\u0012\u0010\u0015\u001a\u00020\b2\b\b\u0001\u0010\u0005\u001a\u00020\u0006H&J\u0012\u0010\u0016\u001a\u00020\n2\b\b\u0001\u0010\u0005\u001a\u00020\u0006H&J\u0012\u0010\u0017\u001a\u00020\f2\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0016J\u0012\u0010\u0018\u001a\u00020\u000e2\b\b\u0001\u0010\u0005\u001a\u00020\u0006H&J\u0012\u0010\u0019\u001a\u00020\u00062\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0016J\u0012\u0010\u001a\u001a\u00020\u00112\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0016J\u0012\u0010\u001b\u001a\u00020\u00132\b\b\u0001\u0010\u0005\u001a\u00020\u0006H&J\u0012\u0010\u001c\u001a\u00020\u00112\b\b\u0001\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u001d\u001a\u00020\u0006H&J\u0012\u0010\u001e\u001a\u00020\u00132\b\b\u0001\u0010\u0005\u001a\u00020\u0006H&J\u000e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00130 H\u0016J\u0012\u0010!\u001a\u00020\u00062\b\b\u0001\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\"\u001a\u00020\u0011H&J\b\u0010#\u001a\u00020\u0004H&J\b\u0010$\u001a\u00020\u0004H&J\b\u0010%\u001a\u00020\u0004H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006&À\u0006\u0001"}, d2 = {"Landroidx/sqlite/SQLiteStatement;", "Ljava/lang/AutoCloseable;", "Lkotlin/AutoCloseable;", "bindBlob", "", FirebaseAnalytics.Param.INDEX, "", "value", "", "bindDouble", "", "bindFloat", "", "bindLong", "", "bindInt", "bindBoolean", "", "bindText", "", "bindNull", "getBlob", "getDouble", "getFloat", "getLong", "getInt", "getBoolean", "getText", "isNull", "getColumnCount", "getColumnName", "getColumnNames", "", "getColumnType", "step", "reset", "clearBindings", "close", "sqlite_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
public interface SQLiteStatement extends AutoCloseable {
    /* JADX INFO: renamed from: bindBlob */
    void mo322bindBlob(int index, byte[] value);

    void bindBoolean(int index, boolean value);

    /* JADX INFO: renamed from: bindDouble */
    void mo323bindDouble(int index, double value);

    void bindFloat(int index, float value);

    void bindInt(int index, int value);

    /* JADX INFO: renamed from: bindLong */
    void mo324bindLong(int index, long value);

    /* JADX INFO: renamed from: bindNull */
    void mo325bindNull(int index);

    /* JADX INFO: renamed from: bindText */
    void mo326bindText(int index, String value);

    /* JADX INFO: renamed from: clearBindings */
    void mo327clearBindings();

    @Override // java.lang.AutoCloseable
    void close();

    byte[] getBlob(int index);

    boolean getBoolean(int index);

    int getColumnCount();

    String getColumnName(int index);

    List<String> getColumnNames();

    int getColumnType(int index);

    double getDouble(int index);

    float getFloat(int index);

    int getInt(int index);

    long getLong(int index);

    String getText(int index);

    boolean isNull(int index);

    void reset();

    boolean step();

    /* JADX INFO: renamed from: androidx.sqlite.SQLiteStatement$-CC, reason: invalid class name */
    /* JADX INFO: compiled from: SQLiteStatement.kt */
    public final /* synthetic */ class CC {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0004: CAST (float) (wrap:double:0x0000: INVOKE (r2v0 '_this' androidx.sqlite.SQLiteStatement A[D('_this' androidx.sqlite.SQLiteStatement)]), (r3v0 int) INTERFACE call: androidx.sqlite.SQLiteStatement.getDouble(int):double A[MD:(int):double (m), WRAPPED] (LINE:123)) */
        public static float $default$getFloat(SQLiteStatement _this, int i) {
            return (float) _this.getDouble(i);
        }

        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0004: CAST (int) (wrap:long:0x0000: INVOKE (r2v0 '_this' androidx.sqlite.SQLiteStatement A[D('_this' androidx.sqlite.SQLiteStatement)]), (r3v0 int) INTERFACE call: androidx.sqlite.SQLiteStatement.getLong(int):long A[MD:(int):long (m), WRAPPED] (LINE:141)) */
        public static int $default$getInt(SQLiteStatement _this, int i) {
            return (int) _this.getLong(i);
        }

        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: ?: TERNARY null = ((wrap:long:0x0000: INVOKE (r4v0 '_this' androidx.sqlite.SQLiteStatement A[D('_this' androidx.sqlite.SQLiteStatement)]), (r5v0 int) INTERFACE call: androidx.sqlite.SQLiteStatement.getLong(int):long A[MD:(int):long (m), WRAPPED] (LINE:151)) != (0 long)) ? true : false */
        public static boolean $default$getBoolean(SQLiteStatement _this, int i) {
            return _this.getLong(i) != 0;
        }

        public static List $default$getColumnNames(SQLiteStatement _this) {
            int columnCount = _this.getColumnCount();
            ArrayList arrayList = new ArrayList(columnCount);
            for (int i = 0; i < columnCount; i++) {
                arrayList.add(_this.getColumnName(i));
            }
            return arrayList;
        }
    }
}
