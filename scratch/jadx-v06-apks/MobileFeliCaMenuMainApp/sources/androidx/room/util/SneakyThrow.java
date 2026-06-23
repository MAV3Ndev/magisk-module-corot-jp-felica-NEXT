package androidx.room.util;

/* JADX INFO: loaded from: classes.dex */
public class SneakyThrow {
    public static void reThrow(Exception e) throws Throwable {
        sneakyThrow(e);
    }

    private static <E extends Throwable> void sneakyThrow(Throwable e) throws Throwable {
        throw e;
    }

    private SneakyThrow() {
    }
}
