package com.google.android.play.integrity.internal;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.android.play:integrity@@1.4.0 */
/* JADX INFO: loaded from: classes3.dex */
final class aq extends ar {
    final transient int a;
    final transient int b;
    final /* synthetic */ ar c;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    aq(ar arVar, int i, int i2) {
        this.c = arVar;
        this.a = i;
        this.b = i2;
    }

    @Override // com.google.android.play.integrity.internal.ao
    final int b() {
        return this.c.c() + this.a + this.b;
    }

    @Override // com.google.android.play.integrity.internal.ao
    final int c() {
        return this.c.c() + this.a;
    }

    @Override // com.google.android.play.integrity.internal.ao
    @CheckForNull
    final Object[] e() {
        return this.c.e();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.play.integrity.internal.ar
    /* JADX INFO: renamed from: f */
    public final ar subList(int i, int i2) {
        al.c(i, i2, this.b);
        int i3 = this.a;
        return this.c.subList(i + i3, i2 + i3);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.util.List
    public final Object get(int i) {
        al.a(i, this.b, FirebaseAnalytics.Param.INDEX);
        return this.c.get(i + this.a);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.b;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.play.integrity.internal.ar, java.util.List
    public final /* bridge */ /* synthetic */ List subList(int i, int i2) {
        return subList(i, i2);
    }
}
