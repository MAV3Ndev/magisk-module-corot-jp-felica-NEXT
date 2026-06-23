package androidx.credentials.provider;

import android.app.PendingIntent;
import android.app.slice.Slice;
import android.app.slice.SliceItem;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.credentials.Credential$Companion$$ExternalSyntheticApiModelOutline0;
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

/* JADX INFO: compiled from: AuthenticationAction.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u0000 \u00122\u00020\u0001:\u0003\u0010\u0011\u0012B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0013"}, d2 = {"Landroidx/credentials/provider/AuthenticationAction;", "", "title", "", BaseGmsClient.KEY_PENDING_INTENT, "Landroid/app/PendingIntent;", "(Ljava/lang/CharSequence;Landroid/app/PendingIntent;)V", "getPendingIntent", "()Landroid/app/PendingIntent;", "getTitle", "()Ljava/lang/CharSequence;", "equals", "", "other", "hashCode", "", "Api34Impl", "Builder", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
public final class AuthenticationAction {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String EXTRA_AUTH_ACTION_PENDING_INTENT_PREFIX = "androidx.credentials.provider.extra.AUTH_ACTION_PENDING_INTENT_";
    private static final String EXTRA_AUTH_ACTION_SIZE = "androidx.credentials.provider.extra.AUTH_ACTION_SIZE";
    private static final String EXTRA_AUTH_ACTION_TITLE_PREFIX = "androidx.credentials.provider.extra.AUTH_ACTION_TITLE_";
    private static final String SLICE_HINT_PENDING_INTENT = "androidx.credentials.provider.authenticationAction.SLICE_HINT_PENDING_INTENT";
    private static final String SLICE_HINT_TITLE = "androidx.credentials.provider.authenticationAction.SLICE_HINT_TITLE";
    private static final int SLICE_SPEC_REVISION = 0;
    private static final String SLICE_SPEC_TYPE = "AuthenticationAction";
    private static final String TAG = "AuthenticationAction";
    private final PendingIntent pendingIntent;
    private final CharSequence title;

    @JvmStatic
    public static final AuthenticationAction fromAction(android.service.credentials.Action action) {
        return INSTANCE.fromAction(action);
    }

    @JvmStatic
    public static final AuthenticationAction fromSlice(Slice slice) {
        return INSTANCE.fromSlice(slice);
    }

    @JvmStatic
    public static final Slice toSlice(AuthenticationAction authenticationAction) {
        return INSTANCE.toSlice(authenticationAction);
    }

    public AuthenticationAction(CharSequence title, PendingIntent pendingIntent) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(pendingIntent, "pendingIntent");
        this.title = title;
        this.pendingIntent = pendingIntent;
        if (title.length() <= 0) {
            throw new IllegalArgumentException("title must not be empty".toString());
        }
    }

    public final CharSequence getTitle() {
        return this.title;
    }

    public final PendingIntent getPendingIntent() {
        return this.pendingIntent;
    }

    /* JADX INFO: compiled from: AuthenticationAction.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Landroidx/credentials/provider/AuthenticationAction$Builder;", "", "title", "", BaseGmsClient.KEY_PENDING_INTENT, "Landroid/app/PendingIntent;", "(Ljava/lang/CharSequence;Landroid/app/PendingIntent;)V", "build", "Landroidx/credentials/provider/AuthenticationAction;", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Builder {
        private final PendingIntent pendingIntent;
        private final CharSequence title;

        public Builder(CharSequence title, PendingIntent pendingIntent) {
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(pendingIntent, "pendingIntent");
            this.title = title;
            this.pendingIntent = pendingIntent;
        }

        public final AuthenticationAction build() {
            return new AuthenticationAction(this.title, this.pendingIntent);
        }
    }

    /* JADX INFO: compiled from: AuthenticationAction.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÃ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Landroidx/credentials/provider/AuthenticationAction$Api34Impl;", "", "()V", "fromAction", "Landroidx/credentials/provider/AuthenticationAction;", "authenticationAction", "Landroid/service/credentials/Action;", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final class Api34Impl {
        public static final Api34Impl INSTANCE = new Api34Impl();

        private Api34Impl() {
        }

        @JvmStatic
        public static final AuthenticationAction fromAction(android.service.credentials.Action authenticationAction) {
            Intrinsics.checkNotNullParameter(authenticationAction, "authenticationAction");
            Slice slice = authenticationAction.getSlice();
            Intrinsics.checkNotNullExpressionValue(slice, "authenticationAction.slice");
            return AuthenticationAction.INSTANCE.fromSlice(slice);
        }
    }

    /* JADX INFO: compiled from: AuthenticationAction.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0012\u001a\u00020\u0013H\u0007J\u0010\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u000f\u001a\u00020\u000eH\u0007J\u001f\u0010\u0015\u001a\u00020\u0016*\b\u0012\u0004\u0012\u00020\u000e0\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0000¢\u0006\u0002\b\u001aJ\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0017*\u00020\u0019H\u0000¢\u0006\u0002\b\u001cR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Landroidx/credentials/provider/AuthenticationAction$Companion;", "", "()V", "EXTRA_AUTH_ACTION_PENDING_INTENT_PREFIX", "", "EXTRA_AUTH_ACTION_SIZE", "EXTRA_AUTH_ACTION_TITLE_PREFIX", "SLICE_HINT_PENDING_INTENT", "SLICE_HINT_TITLE", "SLICE_SPEC_REVISION", "", "SLICE_SPEC_TYPE", "TAG", "fromAction", "Landroidx/credentials/provider/AuthenticationAction;", "authenticationAction", "Landroid/service/credentials/Action;", "fromSlice", "slice", "Landroid/app/slice/Slice;", "toSlice", "marshall", "", "", "bundle", "Landroid/os/Bundle;", "marshall$credentials_release", "unmarshallAuthActionList", "unmarshallAuthActionList$credentials_release", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] call: androidx.credentials.provider.AuthenticationAction.Companion.<init>():void type: THIS */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final Slice toSlice(AuthenticationAction authenticationAction) {
            Intrinsics.checkNotNullParameter(authenticationAction, "authenticationAction");
            CharSequence title = authenticationAction.getTitle();
            PendingIntent pendingIntent = authenticationAction.getPendingIntent();
            Credential$Companion$$ExternalSyntheticApiModelOutline0.m199m$4();
            Slice.Builder builderM = Credential$Companion$$ExternalSyntheticApiModelOutline0.m(Uri.EMPTY, Credential$Companion$$ExternalSyntheticApiModelOutline0.m("AuthenticationAction", 0));
            builderM.addAction(pendingIntent, Credential$Companion$$ExternalSyntheticApiModelOutline0.m(builderM).addHints(Collections.singletonList(AuthenticationAction.SLICE_HINT_PENDING_INTENT)).build(), null).addText(title, null, CollectionsKt.listOf(AuthenticationAction.SLICE_HINT_TITLE));
            Slice sliceBuild = builderM.build();
            Intrinsics.checkNotNullExpressionValue(sliceBuild, "sliceBuilder.build()");
            return sliceBuild;
        }

        @JvmStatic
        public final AuthenticationAction fromSlice(Slice slice) {
            Intrinsics.checkNotNullParameter(slice, "slice");
            List items = slice.getItems();
            Intrinsics.checkNotNullExpressionValue(items, "slice.items");
            Iterator it = items.iterator();
            CharSequence text = null;
            PendingIntent action = null;
            while (it.hasNext()) {
                SliceItem sliceItemM = Credential$Companion$$ExternalSyntheticApiModelOutline0.m(it.next());
                if (sliceItemM.hasHint(AuthenticationAction.SLICE_HINT_PENDING_INTENT)) {
                    action = sliceItemM.getAction();
                } else if (sliceItemM.hasHint(AuthenticationAction.SLICE_HINT_TITLE)) {
                    text = sliceItemM.getText();
                }
            }
            try {
                Intrinsics.checkNotNull(text);
                Intrinsics.checkNotNull(action);
                return new AuthenticationAction(text, action);
            } catch (Exception e) {
                Log.i("AuthenticationAction", "fromSlice failed with: " + e.getMessage());
                return null;
            }
        }

        @JvmStatic
        public final AuthenticationAction fromAction(android.service.credentials.Action authenticationAction) {
            Intrinsics.checkNotNullParameter(authenticationAction, "authenticationAction");
            if (Build.VERSION.SDK_INT >= 34) {
                return Api34Impl.fromAction(authenticationAction);
            }
            return null;
        }

        public final void marshall$credentials_release(List<AuthenticationAction> list, Bundle bundle) {
            Intrinsics.checkNotNullParameter(list, "<this>");
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            bundle.putInt(AuthenticationAction.EXTRA_AUTH_ACTION_SIZE, list.size());
            int size = list.size();
            for (int i = 0; i < size; i++) {
                bundle.putParcelable(AuthenticationAction.EXTRA_AUTH_ACTION_PENDING_INTENT_PREFIX + i, list.get(i).getPendingIntent());
                bundle.putCharSequence(AuthenticationAction.EXTRA_AUTH_ACTION_TITLE_PREFIX + i, list.get(i).getTitle());
            }
        }

        public final List<AuthenticationAction> unmarshallAuthActionList$credentials_release(Bundle bundle) {
            Intrinsics.checkNotNullParameter(bundle, "<this>");
            ArrayList arrayList = new ArrayList();
            int i = bundle.getInt(AuthenticationAction.EXTRA_AUTH_ACTION_SIZE, 0);
            for (int i2 = 0; i2 < i; i2++) {
                PendingIntent pendingIntent = (PendingIntent) bundle.getParcelable(AuthenticationAction.EXTRA_AUTH_ACTION_PENDING_INTENT_PREFIX + i2);
                CharSequence charSequence = bundle.getCharSequence(AuthenticationAction.EXTRA_AUTH_ACTION_TITLE_PREFIX + i2);
                if (pendingIntent == null || charSequence == null) {
                    return CollectionsKt.emptyList();
                }
                arrayList.add(new AuthenticationAction(charSequence, pendingIntent));
            }
            return arrayList;
        }
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AuthenticationAction)) {
            return false;
        }
        AuthenticationAction authenticationAction = (AuthenticationAction) other;
        return Intrinsics.areEqual(this.title, authenticationAction.title) && Intrinsics.areEqual(this.pendingIntent, authenticationAction.pendingIntent);
    }

    public int hashCode() {
        return (this.title.hashCode() * 31) + this.pendingIntent.hashCode();
    }
}
