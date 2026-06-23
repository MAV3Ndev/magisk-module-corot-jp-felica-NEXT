package com.google.android.gms.common.api.internal;

import android.app.Activity;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public final class zaa extends ActivityLifecycleObserver {
    private final WeakReference<C0046zaa> zack;

    public zaa(Activity activity) {
        this(C0046zaa.zaa(activity));
    }

    private zaa(C0046zaa c0046zaa) {
        this.zack = new WeakReference<>(c0046zaa);
    }

    @Override // com.google.android.gms.common.api.internal.ActivityLifecycleObserver
    public final ActivityLifecycleObserver onStopCallOnce(Runnable runnable) {
        C0046zaa c0046zaa = this.zack.get();
        if (c0046zaa == null) {
            throw new IllegalStateException("The target activity has already been GC'd");
        }
        c0046zaa.zaa(runnable);
        return this;
    }

    /* JADX INFO: renamed from: com.google.android.gms.common.api.internal.zaa$zaa, reason: collision with other inner class name */
    static class C0046zaa extends LifecycleCallback {
        private List<Runnable> zacl;

        /* JADX INFO: Access modifiers changed from: private */
        public static C0046zaa zaa(Activity activity) {
            C0046zaa c0046zaa;
            synchronized (activity) {
                LifecycleFragment fragment = getFragment(activity);
                c0046zaa = (C0046zaa) fragment.getCallbackOrNull("LifecycleObserverOnStop", C0046zaa.class);
                if (c0046zaa == null) {
                    c0046zaa = new C0046zaa(fragment);
                }
            }
            return c0046zaa;
        }

        private C0046zaa(LifecycleFragment lifecycleFragment) {
            super(lifecycleFragment);
            this.zacl = new ArrayList();
            this.mLifecycleFragment.addCallback("LifecycleObserverOnStop", this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final synchronized void zaa(Runnable runnable) {
            this.zacl.add(runnable);
        }

        @Override // com.google.android.gms.common.api.internal.LifecycleCallback
        public void onStop() {
            List<Runnable> list;
            synchronized (this) {
                list = this.zacl;
                this.zacl = new ArrayList();
            }
            Iterator<Runnable> it = list.iterator();
            while (it.hasNext()) {
                it.next().run();
            }
        }
    }
}
