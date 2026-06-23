package androidx.room.util;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.room.util.TableInfo;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jdk7.AutoCloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: SchemaInfoUtil.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000`\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0012\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u0001\u001a\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H\u0000\u001a\u001e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H\u0002\u001a\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002\u001a$\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H\u0002\u001a \u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0015\u0018\u00010\n2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H\u0002\u001a\"\u0010\u0016\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u0019H\u0002\u001a\u001e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00030\n2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H\u0000\u001a\u001e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00030\n2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H\u0000\u001a\u0016\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00030\n2\u0006\u0010 \u001a\u00020\u0003H\u0000\u001a\u0018\u0010!\u001a\u00020\"2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010#\u001a\u00020\u0003H\u0000\"\u0016\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00030\u001dX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001e¨\u0006$"}, d2 = {"findAffinity", "", "type", "", "readTableInfo", "Landroidx/room/util/TableInfo;", "connection", "Landroidx/sqlite/SQLiteConnection;", "tableName", "readForeignKeys", "", "Landroidx/room/util/TableInfo$ForeignKey;", "readForeignKeyFieldMappings", "", "Landroidx/room/util/ForeignKeyWithSequence;", "stmt", "Landroidx/sqlite/SQLiteStatement;", "readColumns", "", "Landroidx/room/util/TableInfo$Column;", "readIndices", "Landroidx/room/util/TableInfo$Index;", "readIndex", "name", "unique", "", "readFtsColumns", "readFtsOptions", "FTS_OPTIONS", "", "[Ljava/lang/String;", "parseFtsOptions", "createStatement", "readViewInfo", "Landroidx/room/util/ViewInfo;", "viewName", "room-runtime_release"}, k = 2, mv = {2, 0, 0}, xi = 48)
public final class SchemaInfoUtilKt {
    private static final String[] FTS_OPTIONS = {"tokenize=", "compress=", "content=", "languageid=", "matchinfo=", "notindexed=", "order=", "prefix=", "uncompress="};

    public static final int findAffinity(String str) {
        if (str == null) {
            return 5;
        }
        String upperCase = str.toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        String str2 = upperCase;
        if (StringsKt.contains$default((CharSequence) str2, (CharSequence) "INT", false, 2, (Object) null)) {
            return 3;
        }
        if (StringsKt.contains$default((CharSequence) str2, (CharSequence) "CHAR", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str2, (CharSequence) "CLOB", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str2, (CharSequence) "TEXT", false, 2, (Object) null)) {
            return 2;
        }
        if (StringsKt.contains$default((CharSequence) str2, (CharSequence) "BLOB", false, 2, (Object) null)) {
            return 5;
        }
        return (StringsKt.contains$default((CharSequence) str2, (CharSequence) "REAL", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str2, (CharSequence) "FLOA", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str2, (CharSequence) "DOUB", false, 2, (Object) null)) ? 4 : 1;
    }

    public static final TableInfo readTableInfo(SQLiteConnection connection, String tableName) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        Intrinsics.checkNotNullParameter(tableName, "tableName");
        return new TableInfo(tableName, readColumns(connection, tableName), readForeignKeys(connection, tableName), readIndices(connection, tableName));
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    private static final Set<TableInfo.ForeignKey> readForeignKeys(SQLiteConnection sQLiteConnection, String str) throws Exception {
        SQLiteStatement sQLiteStatementPrepare = sQLiteConnection.prepare("PRAGMA foreign_key_list(`" + str + "`)");
        try {
            SQLiteStatement sQLiteStatement = sQLiteStatementPrepare;
            int iColumnIndexOf = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "id");
            int iColumnIndexOf2 = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "seq");
            int iColumnIndexOf3 = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "table");
            int iColumnIndexOf4 = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "on_delete");
            int iColumnIndexOf5 = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "on_update");
            List<ForeignKeyWithSequence> foreignKeyFieldMappings = readForeignKeyFieldMappings(sQLiteStatement);
            sQLiteStatement.reset();
            Set setCreateSetBuilder = SetsKt.createSetBuilder();
            while (sQLiteStatement.step()) {
                if (sQLiteStatement.getLong(iColumnIndexOf2) == 0) {
                    int i = (int) sQLiteStatement.getLong(iColumnIndexOf);
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    ArrayList<ForeignKeyWithSequence> arrayList3 = new ArrayList();
                    for (Object obj : foreignKeyFieldMappings) {
                        if (((ForeignKeyWithSequence) obj).getId() == i) {
                            arrayList3.add(obj);
                        }
                    }
                    for (ForeignKeyWithSequence foreignKeyWithSequence : arrayList3) {
                        arrayList.add(foreignKeyWithSequence.getFrom());
                        arrayList2.add(foreignKeyWithSequence.getTo());
                    }
                    setCreateSetBuilder.add(new TableInfo.ForeignKey(sQLiteStatement.getText(iColumnIndexOf3), sQLiteStatement.getText(iColumnIndexOf4), sQLiteStatement.getText(iColumnIndexOf5), arrayList, arrayList2));
                }
            }
            Set<TableInfo.ForeignKey> setBuild = SetsKt.build(setCreateSetBuilder);
            AutoCloseableKt.closeFinally(sQLiteStatementPrepare, null);
            return setBuild;
        } finally {
        }
    }

    private static final List<ForeignKeyWithSequence> readForeignKeyFieldMappings(SQLiteStatement sQLiteStatement) {
        int iColumnIndexOf = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "id");
        int iColumnIndexOf2 = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "seq");
        int iColumnIndexOf3 = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "from");
        int iColumnIndexOf4 = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, TypedValues.TransitionType.S_TO);
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        while (sQLiteStatement.step()) {
            listCreateListBuilder.add(new ForeignKeyWithSequence((int) sQLiteStatement.getLong(iColumnIndexOf), (int) sQLiteStatement.getLong(iColumnIndexOf2), sQLiteStatement.getText(iColumnIndexOf3), sQLiteStatement.getText(iColumnIndexOf4)));
        }
        return CollectionsKt.sorted(CollectionsKt.build(listCreateListBuilder));
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    private static final Map<String, TableInfo.Column> readColumns(SQLiteConnection sQLiteConnection, String str) throws Exception {
        SQLiteStatement sQLiteStatementPrepare = sQLiteConnection.prepare("PRAGMA table_info(`" + str + "`)");
        try {
            SQLiteStatement sQLiteStatement = sQLiteStatementPrepare;
            if (!sQLiteStatement.step()) {
                Map<String, TableInfo.Column> mapEmptyMap = MapsKt.emptyMap();
                AutoCloseableKt.closeFinally(sQLiteStatementPrepare, null);
                return mapEmptyMap;
            }
            int iColumnIndexOf = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "name");
            int iColumnIndexOf2 = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "type");
            int iColumnIndexOf3 = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "notnull");
            int iColumnIndexOf4 = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "pk");
            int iColumnIndexOf5 = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "dflt_value");
            Map mapCreateMapBuilder = MapsKt.createMapBuilder();
            do {
                String text = sQLiteStatement.getText(iColumnIndexOf);
                mapCreateMapBuilder.put(text, new TableInfo.Column(text, sQLiteStatement.getText(iColumnIndexOf2), sQLiteStatement.getLong(iColumnIndexOf3) != 0, (int) sQLiteStatement.getLong(iColumnIndexOf4), sQLiteStatement.isNull(iColumnIndexOf5) ? null : sQLiteStatement.getText(iColumnIndexOf5), 2));
            } while (sQLiteStatement.step());
            Map<String, TableInfo.Column> mapBuild = MapsKt.build(mapCreateMapBuilder);
            AutoCloseableKt.closeFinally(sQLiteStatementPrepare, null);
            return mapBuild;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                AutoCloseableKt.closeFinally(sQLiteStatementPrepare, th);
                throw th2;
            }
        }
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    private static final Set<TableInfo.Index> readIndices(SQLiteConnection sQLiteConnection, String str) throws Exception {
        SQLiteStatement sQLiteStatementPrepare = sQLiteConnection.prepare("PRAGMA index_list(`" + str + "`)");
        try {
            SQLiteStatement sQLiteStatement = sQLiteStatementPrepare;
            int iColumnIndexOf = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "name");
            int iColumnIndexOf2 = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "origin");
            int iColumnIndexOf3 = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "unique");
            if (iColumnIndexOf != -1 && iColumnIndexOf2 != -1 && iColumnIndexOf3 != -1) {
                Set setCreateSetBuilder = SetsKt.createSetBuilder();
                while (sQLiteStatement.step()) {
                    if (Intrinsics.areEqual("c", sQLiteStatement.getText(iColumnIndexOf2))) {
                        TableInfo.Index index = readIndex(sQLiteConnection, sQLiteStatement.getText(iColumnIndexOf), sQLiteStatement.getLong(iColumnIndexOf3) == 1);
                        if (index == null) {
                            AutoCloseableKt.closeFinally(sQLiteStatementPrepare, null);
                            return null;
                        }
                        setCreateSetBuilder.add(index);
                    }
                }
                Set<TableInfo.Index> setBuild = SetsKt.build(setCreateSetBuilder);
                AutoCloseableKt.closeFinally(sQLiteStatementPrepare, null);
                return setBuild;
            }
            AutoCloseableKt.closeFinally(sQLiteStatementPrepare, null);
            return null;
        } finally {
        }
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    private static final TableInfo.Index readIndex(SQLiteConnection sQLiteConnection, String str, boolean z) throws Exception {
        SQLiteStatement sQLiteStatementPrepare = sQLiteConnection.prepare("PRAGMA index_xinfo(`" + str + "`)");
        try {
            SQLiteStatement sQLiteStatement = sQLiteStatementPrepare;
            int iColumnIndexOf = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "seqno");
            int iColumnIndexOf2 = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "cid");
            int iColumnIndexOf3 = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "name");
            int iColumnIndexOf4 = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "desc");
            if (iColumnIndexOf != -1 && iColumnIndexOf2 != -1 && iColumnIndexOf3 != -1 && iColumnIndexOf4 != -1) {
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                LinkedHashMap linkedHashMap2 = new LinkedHashMap();
                while (sQLiteStatement.step()) {
                    if (((int) sQLiteStatement.getLong(iColumnIndexOf2)) >= 0) {
                        int i = (int) sQLiteStatement.getLong(iColumnIndexOf);
                        String text = sQLiteStatement.getText(iColumnIndexOf3);
                        String str2 = sQLiteStatement.getLong(iColumnIndexOf4) > 0 ? "DESC" : "ASC";
                        linkedHashMap.put(Integer.valueOf(i), text);
                        linkedHashMap2.put(Integer.valueOf(i), str2);
                    }
                }
                List listSortedWith = CollectionsKt.sortedWith(linkedHashMap.entrySet(), new Comparator() { // from class: androidx.room.util.SchemaInfoUtilKt$readIndex$lambda$13$$inlined$sortedBy$1
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        return ComparisonsKt.compareValues((Integer) ((Map.Entry) t).getKey(), (Integer) ((Map.Entry) t2).getKey());
                    }
                });
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSortedWith, 10));
                Iterator it = listSortedWith.iterator();
                while (it.hasNext()) {
                    arrayList.add((String) ((Map.Entry) it.next()).getValue());
                }
                List list = CollectionsKt.toList(arrayList);
                List listSortedWith2 = CollectionsKt.sortedWith(linkedHashMap2.entrySet(), new Comparator() { // from class: androidx.room.util.SchemaInfoUtilKt$readIndex$lambda$13$$inlined$sortedBy$2
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        return ComparisonsKt.compareValues((Integer) ((Map.Entry) t).getKey(), (Integer) ((Map.Entry) t2).getKey());
                    }
                });
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSortedWith2, 10));
                Iterator it2 = listSortedWith2.iterator();
                while (it2.hasNext()) {
                    arrayList2.add((String) ((Map.Entry) it2.next()).getValue());
                }
                TableInfo.Index index = new TableInfo.Index(str, z, list, CollectionsKt.toList(arrayList2));
                AutoCloseableKt.closeFinally(sQLiteStatementPrepare, null);
                return index;
            }
            AutoCloseableKt.closeFinally(sQLiteStatementPrepare, null);
            return null;
        } finally {
        }
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    public static final Set<String> readFtsColumns(SQLiteConnection connection, String tableName) throws Exception {
        Intrinsics.checkNotNullParameter(connection, "connection");
        Intrinsics.checkNotNullParameter(tableName, "tableName");
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        SQLiteStatement sQLiteStatementPrepare = connection.prepare("PRAGMA table_info(`" + tableName + "`)");
        try {
            SQLiteStatement sQLiteStatement = sQLiteStatementPrepare;
            if (sQLiteStatement.step()) {
                int iColumnIndexOf = SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "name");
                do {
                    setCreateSetBuilder.add(sQLiteStatement.getText(iColumnIndexOf));
                } while (sQLiteStatement.step());
            }
            Unit unit = Unit.INSTANCE;
            AutoCloseableKt.closeFinally(sQLiteStatementPrepare, null);
            return SetsKt.build(setCreateSetBuilder);
        } finally {
        }
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    public static final Set<String> readFtsOptions(SQLiteConnection connection, String tableName) throws Exception {
        String text;
        Intrinsics.checkNotNullParameter(connection, "connection");
        Intrinsics.checkNotNullParameter(tableName, "tableName");
        SQLiteStatement sQLiteStatementPrepare = connection.prepare("SELECT * FROM sqlite_master WHERE `name` = '" + tableName + '\'');
        try {
            SQLiteStatement sQLiteStatement = sQLiteStatementPrepare;
            if (sQLiteStatement.step()) {
                text = sQLiteStatement.getText(SQLiteStatementUtil.columnIndexOf(sQLiteStatement, "sql"));
            } else {
                text = "";
            }
            AutoCloseableKt.closeFinally(sQLiteStatementPrepare, null);
            return parseFtsOptions(text);
        } finally {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x00e0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Set<String> parseFtsOptions(String createStatement) {
        Character ch;
        Intrinsics.checkNotNullParameter(createStatement, "createStatement");
        String str = createStatement;
        if (str.length() == 0) {
            return SetsKt.emptySet();
        }
        String strSubstring = createStatement.substring(StringsKt.indexOf$default((CharSequence) str, '(', 0, false, 6, (Object) null) + 1, StringsKt.lastIndexOf$default((CharSequence) str, ')', 0, false, 6, (Object) null));
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        ArrayList arrayList = new ArrayList();
        ArrayDeque arrayDeque = new ArrayDeque();
        String str2 = strSubstring;
        int i = -1;
        int i2 = 0;
        int i3 = 0;
        while (i2 < str2.length()) {
            char cCharAt = str2.charAt(i2);
            int i4 = i3 + 1;
            if (cCharAt == '\"' || cCharAt == '\'') {
                if (arrayDeque.isEmpty()) {
                    arrayDeque.addFirst(Character.valueOf(cCharAt));
                } else {
                    Character ch2 = (Character) arrayDeque.firstOrNull();
                    if (ch2 != null && ch2.charValue() == cCharAt) {
                        CollectionsKt.removeLast(arrayDeque);
                    }
                }
            } else if (cCharAt != ',') {
                if (cCharAt != '[') {
                    if (cCharAt != ']') {
                        if (cCharAt != '`') {
                        }
                    } else if (!arrayDeque.isEmpty() && (ch = (Character) arrayDeque.firstOrNull()) != null && ch.charValue() == '[') {
                        CollectionsKt.removeLast(arrayDeque);
                    }
                } else if (arrayDeque.isEmpty()) {
                    arrayDeque.addFirst(Character.valueOf(cCharAt));
                }
            } else if (arrayDeque.isEmpty()) {
                String strSubstring2 = strSubstring.substring(i + 1, i3);
                Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
                String str3 = strSubstring2;
                int length = str3.length() - 1;
                int i5 = 0;
                boolean z = false;
                while (i5 <= length) {
                    boolean z2 = Intrinsics.compare((int) str3.charAt(!z ? i5 : length), 32) <= 0;
                    if (z) {
                        if (!z2) {
                            break;
                        }
                        length--;
                    } else if (z2) {
                        i5++;
                    } else {
                        z = true;
                    }
                }
                arrayList.add(str3.subSequence(i5, length + 1).toString());
                i = i3;
            }
            i2++;
            i3 = i4;
        }
        String strSubstring3 = strSubstring.substring(i + 1);
        Intrinsics.checkNotNullExpressionValue(strSubstring3, "substring(...)");
        arrayList.add(StringsKt.trim((CharSequence) strSubstring3).toString());
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            String str4 = (String) obj;
            String[] strArr = FTS_OPTIONS;
            int length2 = strArr.length;
            int i6 = 0;
            while (true) {
                if (i6 >= length2) {
                    break;
                }
                if (StringsKt.startsWith$default(str4, strArr[i6], false, 2, (Object) null)) {
                    arrayList2.add(obj);
                    break;
                }
                i6++;
            }
        }
        return CollectionsKt.toSet(arrayList2);
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    public static final ViewInfo readViewInfo(SQLiteConnection connection, String viewName) throws Exception {
        ViewInfo viewInfo;
        Intrinsics.checkNotNullParameter(connection, "connection");
        Intrinsics.checkNotNullParameter(viewName, "viewName");
        SQLiteStatement sQLiteStatementPrepare = connection.prepare("SELECT name, sql FROM sqlite_master WHERE type = 'view' AND name = '" + viewName + '\'');
        try {
            SQLiteStatement sQLiteStatement = sQLiteStatementPrepare;
            if (sQLiteStatement.step()) {
                viewInfo = new ViewInfo(sQLiteStatement.getText(0), sQLiteStatement.getText(1));
            } else {
                viewInfo = new ViewInfo(viewName, null);
            }
            AutoCloseableKt.closeFinally(sQLiteStatementPrepare, null);
            return viewInfo;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                AutoCloseableKt.closeFinally(sQLiteStatementPrepare, th);
                throw th2;
            }
        }
    }
}
