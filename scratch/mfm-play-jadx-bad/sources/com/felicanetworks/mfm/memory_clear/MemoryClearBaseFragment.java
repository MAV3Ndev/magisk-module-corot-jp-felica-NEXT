package com.felicanetworks.mfm.memory_clear;

import android.os.SystemClock;
import android.view.View;
import androidx.fragment.app.Fragment;

/* JADX INFO: loaded from: classes3.dex */
public class MemoryClearBaseFragment extends Fragment implements View.OnClickListener {
    private Thread buttonPressThread;

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        preventButtonPresses(v);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        Thread thread = this.buttonPressThread;
        if (thread != null && thread.isAlive()) {
            this.buttonPressThread.interrupt();
        }
        super.onDestroyView();
    }

    protected void preventButtonPresses(final View v) {
        v.setEnabled(false);
        Thread thread = new Thread(new Runnable() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearBaseFragment.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    SystemClock.sleep(1000L);
                    if (MemoryClearBaseFragment.this.buttonPressThread.isInterrupted()) {
                        return;
                    }
                    v.setEnabled(true);
                } catch (Exception unused) {
                }
            }
        });
        this.buttonPressThread = thread;
        thread.start();
    }
}
