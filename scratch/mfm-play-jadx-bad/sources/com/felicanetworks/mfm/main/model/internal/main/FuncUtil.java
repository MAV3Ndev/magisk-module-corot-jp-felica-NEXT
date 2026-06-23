package com.felicanetworks.mfm.main.model.internal.main;

import android.os.SystemClock;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DateFormatter;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes3.dex */
public class FuncUtil {

    interface Notify {
        void go();
    }

    interface WaitBlock {
        void present(Owner owner) throws Exception;
    }

    static class Owner {
        private boolean _isOwner = true;
        private Exception _exp = null;

        Owner() {
        }

        public String toString() {
            return "Owner{_isOwner=" + this._isOwner + ", _exp=" + this._exp + '}';
        }

        public synchronized void release() {
            this._isOwner = false;
        }

        public synchronized void release(Exception exp) {
            this._exp = exp;
            release();
        }

        private synchronized boolean isOwner() {
            return this._isOwner;
        }

        private synchronized boolean isException() {
            return this._exp != null;
        }

        private Exception getException() {
            return this._exp;
        }
    }

    static void notifySafety(final AsyncRunner runner, final Notify notify) {
        if (runner.isInterrupt()) {
            return;
        }
        new Thread(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.FuncUtil.1
            @Override // java.lang.Runnable
            public void run() {
                while (runner.isRunning()) {
                    SystemClock.sleep(50L);
                }
                notify.go();
            }
        }).start();
    }

    static void notify(final AsyncRunner runner, final Notify n) {
        new Thread(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.FuncUtil.2
            @Override // java.lang.Runnable
            public void run() {
                while (runner.isRunning()) {
                    SystemClock.sleep(50L);
                }
                n.go();
            }
        }).start();
    }

    static class AsyncRunner {
        private Object _failure;
        private String _name;
        private boolean _shutdown;
        private Object _success;
        private Thread _thread;

        public AsyncRunner() {
            this._shutdown = false;
            this._thread = null;
            this._success = null;
            this._failure = null;
            this._name = "";
        }

        public AsyncRunner(String name) {
            this._shutdown = false;
            this._thread = null;
            this._success = null;
            this._failure = null;
            this._name = name;
        }

        public String getName() {
            return this._name;
        }

        synchronized boolean startup(Runnable runnable) {
            return startup(new Thread(runnable));
        }

        synchronized boolean startup(String name, Runnable runnable) {
            return startup(name, new Thread(runnable));
        }

        synchronized boolean startup(String name, Thread thread) {
            thread.setName(name);
            return startup(thread);
        }

        synchronized boolean startup(Thread thread) {
            if (isRunning()) {
                return false;
            }
            this._shutdown = false;
            this._thread = thread;
            this._success = null;
            this._failure = null;
            thread.start();
            return true;
        }

        synchronized void startupOrThrow(Runnable runnable) {
            if (!startup(runnable)) {
                throw new IllegalStateException("This thread is still executing");
            }
        }

        void shutdown() {
            this._shutdown = true;
            if (isRunning()) {
                synchronized (this._thread) {
                    this._thread.notifyAll();
                    this._thread.interrupt();
                }
            }
            synchronized (this) {
                this._thread = null;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:8:0x000d  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        synchronized boolean isRunning() {
            boolean z;
            Thread thread = this._thread;
            if (thread != null) {
                z = thread.isAlive();
            }
            return z;
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x0012  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        synchronized boolean isInterrupt() {
            boolean z;
            if (this._shutdown) {
                z = true;
            } else {
                Thread thread = this._thread;
                if (thread != null) {
                    if (thread.isInterrupted()) {
                    }
                }
                z = false;
            }
            return z;
        }

        synchronized void checkInterrupted() throws InterruptedException {
            if (isInterrupt()) {
                throw new InterruptedException();
            }
        }

        synchronized void putSuccess(Object object) {
            this._success = object;
        }

        synchronized Object getSuccess() {
            return this._success;
        }

        synchronized void putFailure(Object object) {
            this._failure = object;
        }

        synchronized Object getFailure() {
            return this._failure;
        }

        synchronized boolean hasFailure() {
            return this._failure != null;
        }
    }

    static Calendar getCurrentCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone((String) Sg.getValue(Sg.Key.SETTING_TIMEZONE)));
        calendar.setTime(new Date());
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar;
    }

    static String convertFromCalendarToString(String format, Calendar calendar) {
        return new DateFormatter(format, (String) Sg.getValue(Sg.Key.SETTING_TIMEZONE)).format(calendar.getTime());
    }

    static Calendar convertFromStringToCalendar(String format, String str) {
        Date date = new DateFormatter(format, (String) Sg.getValue(Sg.Key.SETTING_TIMEZONE)).parse(str, new ParsePosition(0));
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone((String) Sg.getValue(Sg.Key.SETTING_TIMEZONE)));
        calendar.setTime(date);
        return calendar;
    }

    static class AsyncPacket<T> {
        private T packet = null;

        AsyncPacket() {
        }

        void set(T packet) {
            this.packet = packet;
        }

        boolean exist() {
            return this.packet != null;
        }

        T get() {
            return this.packet;
        }
    }
}
