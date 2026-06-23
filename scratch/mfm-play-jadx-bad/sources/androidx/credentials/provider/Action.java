package androidx.credentials.provider;

import android.app.PendingIntent;
import android.app.slice.Slice;
import android.app.slice.SliceItem;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.credentials.Credential$Companion$$ExternalSyntheticApiModelOutline0;
import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.google.android.gms.common.internal.BaseGmsClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Action.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u0000 \u00142\u00020\u0001:\u0003\u0012\u0013\u0014B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0007J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0015"}, d2 = {"Landroidx/credentials/provider/Action;", "", "title", "", BaseGmsClient.KEY_PENDING_INTENT, "Landroid/app/PendingIntent;", "subtitle", "(Ljava/lang/CharSequence;Landroid/app/PendingIntent;Ljava/lang/CharSequence;)V", "getPendingIntent", "()Landroid/app/PendingIntent;", "getSubtitle", "()Ljava/lang/CharSequence;", "getTitle", "equals", "", "other", "hashCode", "", "Api34Impl", "Builder", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
public final class Action {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String EXTRA_ACTION_PENDING_INTENT_PREFIX = "androidx.credentials.provider.extra.ACTION_PENDING_INTENT_";
    private static final String EXTRA_ACTION_SIZE = "androidx.credentials.provider.extra.ACTION_SIZE";
    private static final String EXTRA_ACTION_SUBTITLE_PREFIX = "androidx.credentials.provider.extra.ACTION_SUBTITLE_";
    private static final String EXTRA_ACTION_TITLE_PREFIX = "androidx.credentials.provider.extra.ACTION_TITLE_";
    private static final String SLICE_HINT_PENDING_INTENT = "androidx.credentials.provider.action.SLICE_HINT_PENDING_INTENT";
    private static final String SLICE_HINT_SUBTITLE = "androidx.credentials.provider.action.HINT_ACTION_SUBTEXT";
    private static final String SLICE_HINT_TITLE = "androidx.credentials.provider.action.HINT_ACTION_TITLE";
    private static final int SLICE_SPEC_REVISION = 0;
    private static final String SLICE_SPEC_TYPE = "Action";
    private static final String TAG = "Action";
    private final PendingIntent pendingIntent;
    private final CharSequence subtitle;
    private final CharSequence title;

    @JvmStatic
    public static final Action fromAction(android.service.credentials.Action action) {
        return INSTANCE.fromAction(action);
    }

    @JvmStatic
    public static final Action fromSlice(Slice slice) {
        return INSTANCE.fromSlice(slice);
    }

    @JvmStatic
    public static final Slice toSlice(Action action) {
        return INSTANCE.toSlice(action);
    }

    public Action(CharSequence title, PendingIntent pendingIntent, CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(pendingIntent, "pendingIntent");
        this.title = title;
        this.pendingIntent = pendingIntent;
        this.subtitle = charSequence;
        if (title.length() <= 0) {
            throw new IllegalArgumentException("title must not be empty".toString());
        }
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0005: CONSTRUCTOR 
  (r1v0 java.lang.CharSequence)
  (r2v0 android.app.PendingIntent)
  (wrap:java.lang.CharSequence:?: TERNARY null = ((wrap:int:0x0000: ARITH (r4v0 int) & (4 int) A[WRAPPED]) != (0 int)) ? (null java.lang.CharSequence) : (r3v0 java.lang.CharSequence))
 A[MD:(java.lang.CharSequence, android.app.PendingIntent, java.lang.CharSequence):void (m)] (LINE:71) call: androidx.credentials.provider.Action.<init>(java.lang.CharSequence, android.app.PendingIntent, java.lang.CharSequence):void type: THIS */
    public /* synthetic */ Action(CharSequence charSequence, PendingIntent pendingIntent, CharSequence charSequence2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(charSequence, pendingIntent, (i & 4) != 0 ? null : charSequence2);
    }

    public final CharSequence getTitle() {
        return this.title;
    }

    public final PendingIntent getPendingIntent() {
        return this.pendingIntent;
    }

    public final CharSequence getSubtitle() {
        return this.subtitle;
    }

    /* JADX INFO: compiled from: Action.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\b\u001a\u00020\tJ\u0010\u0010\n\u001a\u00020\u00002\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Landroidx/credentials/provider/Action$Builder;", "", "title", "", BaseGmsClient.KEY_PENDING_INTENT, "Landroid/app/PendingIntent;", "(Ljava/lang/CharSequence;Landroid/app/PendingIntent;)V", "subtitle", "build", "Landroidx/credentials/provider/Action;", "setSubtitle", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Builder {
        private final PendingIntent pendingIntent;
        private CharSequence subtitle;
        private final CharSequence title;

        public Builder(CharSequence title, PendingIntent pendingIntent) {
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(pendingIntent, "pendingIntent");
            this.title = title;
            this.pendingIntent = pendingIntent;
        }

        public final Builder setSubtitle(CharSequence subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public final Action build() {
            return new Action(this.title, this.pendingIntent, this.subtitle);
        }
    }

    /* JADX INFO: compiled from: Action.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÃ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Landroidx/credentials/provider/Action$Api34Impl;", "", "()V", "fromAction", "Landroidx/credentials/provider/Action;", "action", "Landroid/service/credentials/Action;", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final class Api34Impl {
        public static final Api34Impl INSTANCE = new Api34Impl();

        private Api34Impl() {
        }

        @JvmStatic
        public static final Action fromAction(android.service.credentials.Action action) {
            Intrinsics.checkNotNullParameter(action, "action");
            Slice slice = action.getSlice();
            Intrinsics.checkNotNullExpressionValue(slice, "action.slice");
            return Action.INSTANCE.fromSlice(slice);
        }
    }

    /* JADX INFO: compiled from: Action.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0007J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0014\u001a\u00020\u0015H\u0007J\u0010\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0011\u001a\u00020\u0010H\u0007J\u001f\u0010\u0017\u001a\u00020\u0018*\b\u0012\u0004\u0012\u00020\u00100\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0000¢\u0006\u0002\b\u001cJ\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00100\u0019*\u00020\u001bH\u0000¢\u0006\u0002\b\u001eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Landroidx/credentials/provider/Action$Companion;", "", "()V", "EXTRA_ACTION_PENDING_INTENT_PREFIX", "", "EXTRA_ACTION_SIZE", "EXTRA_ACTION_SUBTITLE_PREFIX", "EXTRA_ACTION_TITLE_PREFIX", "SLICE_HINT_PENDING_INTENT", "SLICE_HINT_SUBTITLE", "SLICE_HINT_TITLE", "SLICE_SPEC_REVISION", "", "SLICE_SPEC_TYPE", "TAG", "fromAction", "Landroidx/credentials/provider/Action;", "action", "Landroid/service/credentials/Action;", "fromSlice", "slice", "Landroid/app/slice/Slice;", "toSlice", "marshall", "", "", "bundle", "Landroid/os/Bundle;", "marshall$credentials_release", "unmarshallActionList", "unmarshallActionList$credentials_release", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] call: androidx.credentials.provider.Action.Companion.<init>():void type: THIS */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final Slice toSlice(Action action) {
            Intrinsics.checkNotNullParameter(action, "action");
            CharSequence title = action.getTitle();
            CharSequence subtitle = action.getSubtitle();
            PendingIntent pendingIntent = action.getPendingIntent();
            Credential$Companion$$ExternalSyntheticApiModelOutline0.m199m$4();
            Slice.Builder builderAddText = Credential$Companion$$ExternalSyntheticApiModelOutline0.m(Uri.EMPTY, Credential$Companion$$ExternalSyntheticApiModelOutline0.m(JsonDocumentFields.ACTION, 0)).addText(title, null, CollectionsKt.listOf(Action.SLICE_HINT_TITLE)).addText(subtitle, null, CollectionsKt.listOf(Action.SLICE_HINT_SUBTITLE));
            builderAddText.addAction(pendingIntent, Credential$Companion$$ExternalSyntheticApiModelOutline0.m(builderAddText).addHints(Collections.singletonList(Action.SLICE_HINT_PENDING_INTENT)).build(), null);
            Slice sliceBuild = builderAddText.build();
            Intrinsics.checkNotNullExpressionValue(sliceBuild, "sliceBuilder.build()");
            return sliceBuild;
        }

        @JvmStatic
        public final Action fromSlice(Slice slice) {
            Intrinsics.checkNotNullParameter(slice, "slice");
            List items = slice.getItems();
            Intrinsics.checkNotNullExpressionValue(items, "slice.items");
            Iterator it = items.iterator();
            CharSequence text = "";
            PendingIntent action = null;
            CharSequence text2 = null;
            while (it.hasNext()) {
                SliceItem sliceItemM = Credential$Companion$$ExternalSyntheticApiModelOutline0.m(it.next());
                if (sliceItemM.hasHint(Action.SLICE_HINT_TITLE)) {
                    text = sliceItemM.getText();
                    Intrinsics.checkNotNullExpressionValue(text, "it.text");
                } else if (sliceItemM.hasHint(Action.SLICE_HINT_SUBTITLE)) {
                    text2 = sliceItemM.getText();
                } else if (sliceItemM.hasHint(Action.SLICE_HINT_PENDING_INTENT)) {
                    action = sliceItemM.getAction();
                }
            }
            try {
                Intrinsics.checkNotNull(action);
                return new Action(text, action, text2);
            } catch (Exception e) {
                Log.i(JsonDocumentFields.ACTION, "fromSlice failed with: " + e.getMessage());
                return null;
            }
        }

        @JvmStatic
        public final Action fromAction(android.service.credentials.Action action) {
            Intrinsics.checkNotNullParameter(action, "action");
            if (Build.VERSION.SDK_INT >= 34) {
                return Api34Impl.fromAction(action);
            }
            return null;
        }

        public final void marshall$credentials_release(List<Action> list, Bundle bundle) {
            Intrinsics.checkNotNullParameter(list, "<this>");
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            bundle.putInt(Action.EXTRA_ACTION_SIZE, list.size());
            int size = list.size();
            for (int i = 0; i < size; i++) {
                bundle.putParcelable(Action.EXTRA_ACTION_PENDING_INTENT_PREFIX + i, list.get(i).getPendingIntent());
                bundle.putCharSequence(Action.EXTRA_ACTION_TITLE_PREFIX + i, list.get(i).getTitle());
                bundle.putCharSequence(Action.EXTRA_ACTION_SUBTITLE_PREFIX + i, list.get(i).getSubtitle());
            }
        }

        public final List<Action> unmarshallActionList$credentials_release(Bundle bundle) {
            Intrinsics.checkNotNullParameter(bundle, "<this>");
            ArrayList arrayList = new ArrayList();
            int i = bundle.getInt(Action.EXTRA_ACTION_SIZE, 0);
            for (int i2 = 0; i2 < i; i2++) {
                PendingIntent pendingIntent = (PendingIntent) bundle.getParcelable(Action.EXTRA_ACTION_PENDING_INTENT_PREFIX + i2);
                CharSequence charSequence = bundle.getCharSequence(Action.EXTRA_ACTION_TITLE_PREFIX + i2);
                CharSequence charSequence2 = bundle.getCharSequence(Action.EXTRA_ACTION_SUBTITLE_PREFIX + i2);
                if (pendingIntent == null || charSequence == null) {
                    return CollectionsKt.emptyList();
                }
                arrayList.add(new Action(charSequence, pendingIntent, charSequence2));
            }
            return arrayList;
        }
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Action)) {
            return false;
        }
        Action action = (Action) other;
        return Intrinsics.areEqual(this.title, action.title) && Intrinsics.areEqual(this.pendingIntent, action.pendingIntent) && Intrinsics.areEqual(this.subtitle, action.subtitle);
    }

    public int hashCode() {
        int iHashCode = ((this.title.hashCode() * 31) + this.pendingIntent.hashCode()) * 31;
        CharSequence charSequence = this.subtitle;
        return iHashCode + (charSequence != null ? charSequence.hashCode() : 0);
    }
}
