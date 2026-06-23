package com.felicanetworks.mfm.messenger;

import android.text.TextUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes3.dex */
class Logger {
    static void debug(Object... objArr) {
    }

    static void error(Object... objArr) {
    }

    static void info(Object... objArr) {
    }

    static Stopwatch start() {
        return null;
    }

    static void stop(Stopwatch stopwatch) {
    }

    static void warning(Object... objArr) {
    }

    private Logger() {
    }

    private static class Argument {
        private StackTraceElement element;
        private final StringBuilder builder = new StringBuilder();
        private final List<Throwable> throwables = new ArrayList();

        Argument(Object... objArr) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            int length = stackTrace.length - 1;
            while (true) {
                if (length < 0) {
                    break;
                }
                if (TextUtils.equals(Logger.class.getName(), stackTrace[length].getClassName())) {
                    this.element = stackTrace[length + 1];
                    break;
                }
                length--;
            }
            StackTraceElement stackTraceElement = this.element;
            if (stackTraceElement != null) {
                this.builder.append(String.format("%s#%s", stackTraceElement.getClassName().replace(BuildConfig.LIBRARY_PACKAGE_NAME, ""), this.element.getMethodName()));
            } else {
                this.builder.append("???");
            }
            for (Object obj : objArr) {
                if (obj instanceof Throwable) {
                    this.throwables.add((Throwable) obj);
                }
                StringBuilder sb = this.builder;
                sb.append(", ");
                sb.append(obj);
            }
        }

        boolean hasThrowable() {
            return !this.throwables.isEmpty();
        }

        Throwable throwable() {
            return this.throwables.get(0);
        }

        List<Throwable> throwables() {
            return this.throwables;
        }

        String string() {
            return this.builder.toString();
        }
    }

    static class Stopwatch {
        private final Argument argument;
        private final long baseTime;
        private final SimpleDateFormat timeFormat;

        private Stopwatch(Argument argument) {
            this.timeFormat = new SimpleDateFormat("HH:mm:ss.SSS", Locale.US);
            this.argument = argument;
            this.baseTime = System.currentTimeMillis();
        }

        private String time() {
            return this.timeFormat.format(new Date(System.currentTimeMillis() - this.baseTime));
        }

        String string() {
            return String.format("[%s] %s", time(), this.argument.string());
        }
    }
}
