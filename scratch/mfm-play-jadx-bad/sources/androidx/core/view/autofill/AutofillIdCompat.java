package androidx.core.view.autofill;

import android.view.autofill.AutofillId;
import androidx.credentials.Credential$Companion$$ExternalSyntheticApiModelOutline0;

/* JADX INFO: loaded from: classes.dex */
public class AutofillIdCompat {
    private final Object mWrappedObj;

    private AutofillIdCompat(AutofillId autofillId) {
        this.mWrappedObj = autofillId;
    }

    public static AutofillIdCompat toAutofillIdCompat(AutofillId autofillId) {
        return new AutofillIdCompat(autofillId);
    }

    public AutofillId toAutofillId() {
        return Credential$Companion$$ExternalSyntheticApiModelOutline0.m180m(this.mWrappedObj);
    }
}
