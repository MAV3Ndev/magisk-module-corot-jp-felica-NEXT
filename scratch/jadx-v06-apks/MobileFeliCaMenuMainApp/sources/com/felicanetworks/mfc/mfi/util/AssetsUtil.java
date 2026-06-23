package com.felicanetworks.mfc.mfi.util;

/* JADX INFO: loaded from: classes.dex */
public class AssetsUtil {
    private static final String ENCODE_TYPE = "UTF-8";

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0094 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x009e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:76:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r8v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r8v10, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v8, types: [java.io.InputStream] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:22:0x0051 -> B:69:0x008a). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String load(java.lang.String r8) throws java.lang.Throwable {
        /*
            r0 = 5
            java.lang.String r1 = "000"
            com.felicanetworks.mfc.util.LogMgr.log(r0, r1)
            r1 = 2
            r2 = 0
            if (r8 == 0) goto La7
            int r3 = r8.length()
            if (r3 != 0) goto L12
            goto La7
        L12:
            java.lang.StringBuffer r3 = new java.lang.StringBuffer
            r3.<init>()
            r4 = 7
            com.felicanetworks.mfc.mfi.FelicaAdapter r5 = com.felicanetworks.mfc.mfi.FelicaAdapter.getInstance()     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L60
            android.content.res.Resources r5 = r5.getResources()     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L60
            android.content.res.AssetManager r5 = r5.getAssets()     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L60
            java.io.InputStream r8 = r5.open(r8)     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L60
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L57 java.io.IOException -> L5a
            java.io.InputStreamReader r6 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L57 java.io.IOException -> L5a
            java.lang.String r7 = "UTF-8"
            r6.<init>(r8, r7)     // Catch: java.lang.Throwable -> L57 java.io.IOException -> L5a
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L57 java.io.IOException -> L5a
        L34:
            java.lang.String r6 = r5.readLine()     // Catch: java.io.IOException -> L55 java.lang.Throwable -> L90
            if (r6 == 0) goto L3e
            r3.append(r6)     // Catch: java.io.IOException -> L55 java.lang.Throwable -> L90
            goto L34
        L3e:
            java.lang.String r2 = r3.toString()     // Catch: java.io.IOException -> L55 java.lang.Throwable -> L90
            if (r8 == 0) goto L4c
            r8.close()     // Catch: java.io.IOException -> L48
            goto L4c
        L48:
            r8 = move-exception
            com.felicanetworks.mfc.util.LogMgr.printStackTrace(r4, r8)
        L4c:
            r5.close()     // Catch: java.io.IOException -> L50
            goto L8a
        L50:
            r8 = move-exception
            com.felicanetworks.mfc.util.LogMgr.printStackTrace(r4, r8)
            goto L8a
        L55:
            r3 = move-exception
            goto L63
        L57:
            r0 = move-exception
            r5 = r2
            goto L91
        L5a:
            r3 = move-exception
            r5 = r2
            goto L63
        L5d:
            r0 = move-exception
            r5 = r2
            goto L92
        L60:
            r3 = move-exception
            r8 = r2
            r5 = r8
        L63:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L90
            r6.<init>()     // Catch: java.lang.Throwable -> L90
            java.lang.String r7 = "701 Failed to read assets file. msg:"
            r6.append(r7)     // Catch: java.lang.Throwable -> L90
            java.lang.String r3 = r3.getMessage()     // Catch: java.lang.Throwable -> L90
            r6.append(r3)     // Catch: java.lang.Throwable -> L90
            java.lang.String r3 = r6.toString()     // Catch: java.lang.Throwable -> L90
            com.felicanetworks.mfc.util.LogMgr.log(r1, r3)     // Catch: java.lang.Throwable -> L90
            if (r8 == 0) goto L85
            r8.close()     // Catch: java.io.IOException -> L81
            goto L85
        L81:
            r8 = move-exception
            com.felicanetworks.mfc.util.LogMgr.printStackTrace(r4, r8)
        L85:
            if (r5 == 0) goto L8a
            r5.close()     // Catch: java.io.IOException -> L50
        L8a:
            java.lang.String r8 = "999"
            com.felicanetworks.mfc.util.LogMgr.log(r0, r8)
            return r2
        L90:
            r0 = move-exception
        L91:
            r2 = r8
        L92:
            if (r2 == 0) goto L9c
            r2.close()     // Catch: java.io.IOException -> L98
            goto L9c
        L98:
            r8 = move-exception
            com.felicanetworks.mfc.util.LogMgr.printStackTrace(r4, r8)
        L9c:
            if (r5 == 0) goto La6
            r5.close()     // Catch: java.io.IOException -> La2
            goto La6
        La2:
            r8 = move-exception
            com.felicanetworks.mfc.util.LogMgr.printStackTrace(r4, r8)
        La6:
            throw r0
        La7:
            java.lang.String r8 = "700 name is null or empty."
            com.felicanetworks.mfc.util.LogMgr.log(r1, r8)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.util.AssetsUtil.load(java.lang.String):java.lang.String");
    }
}
