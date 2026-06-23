package com.felicanetworks.mfm.main.model.internal.main;

import android.os.SystemClock;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DateFormatter;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
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

        public synchronized void release(Exception exc) {
            this._exp = exc;
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

    static void notifySafety(final AsyncRunner asyncRunner, final Notify notify) {
        if (asyncRunner.isInterrupt()) {
            return;
        }
        new Thread(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.FuncUtil.1
            @Override // java.lang.Runnable
            public void run() {
                while (asyncRunner.isRunning()) {
                    SystemClock.sleep(50L);
                }
                notify.go();
            }
        }).start();
    }

    static class AsyncRunner {
        private boolean _shutdown = false;
        private Thread _thread = null;
        private Object _success = null;
        private Object _failure = null;

        AsyncRunner() {
        }

        synchronized boolean startup(Runnable runnable) {
            return startup(new Thread(runnable));
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

        /* JADX WARN: Removed duplicated region for block: B:8:0x000f  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        synchronized boolean isRunning() {
            /*
                r1 = this;
                monitor-enter(r1)
                java.lang.Thread r0 = r1._thread     // Catch: java.lang.Throwable -> L12
                if (r0 == 0) goto Lf
                java.lang.Thread r0 = r1._thread     // Catch: java.lang.Throwable -> L12
                boolean r0 = r0.isAlive()     // Catch: java.lang.Throwable -> L12
                if (r0 == 0) goto Lf
                r0 = 1
                goto L10
            Lf:
                r0 = 0
            L10:
                monitor-exit(r1)
                return r0
            L12:
                r0 = move-exception
                monitor-exit(r1)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.main.FuncUtil.AsyncRunner.isRunning():boolean");
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x0014  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        synchronized boolean isInterrupt() {
            /*
                r1 = this;
                monitor-enter(r1)
                boolean r0 = r1._shutdown     // Catch: java.lang.Throwable -> L17
                if (r0 != 0) goto L14
                java.lang.Thread r0 = r1._thread     // Catch: java.lang.Throwable -> L17
                if (r0 == 0) goto L12
                java.lang.Thread r0 = r1._thread     // Catch: java.lang.Throwable -> L17
                boolean r0 = r0.isInterrupted()     // Catch: java.lang.Throwable -> L17
                if (r0 == 0) goto L12
                goto L14
            L12:
                r0 = 0
                goto L15
            L14:
                r0 = 1
            L15:
                monitor-exit(r1)
                return r0
            L17:
                r0 = move-exception
                monitor-exit(r1)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.main.FuncUtil.AsyncRunner.isInterrupt():boolean");
        }

        synchronized void checkInterrupted() throws InterruptedException {
            if (isInterrupt()) {
                throw new InterruptedException();
            }
        }

        synchronized void putSuccess(Object obj) {
            this._success = obj;
        }

        synchronized Object getSuccess() {
            return this._success;
        }

        synchronized void putFailure(Object obj) {
            this._failure = obj;
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

    static String convertFromCalendarToString(String str, Calendar calendar) {
        return new DateFormatter(str, (String) Sg.getValue(Sg.Key.SETTING_TIMEZONE)).format(calendar.getTime());
    }

    static Calendar convertFromStringToCalendar(String str, String str2) {
        Date date = new DateFormatter(str, (String) Sg.getValue(Sg.Key.SETTING_TIMEZONE)).parse(str2, new ParsePosition(0));
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

        void set(T t) {
            this.packet = t;
        }

        boolean exist() {
            return this.packet != null;
        }

        T get() {
            return this.packet;
        }
    }
}
