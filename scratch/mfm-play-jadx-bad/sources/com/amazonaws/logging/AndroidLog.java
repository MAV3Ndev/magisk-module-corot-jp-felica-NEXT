package com.amazonaws.logging;

import com.amazonaws.logging.LogFactory;

/* JADX INFO: loaded from: classes.dex */
public class AndroidLog implements Log {
    private LogFactory.Level level = null;
    private final String tag;

    public AndroidLog(String str) {
        this.tag = str;
    }

    @Override // com.amazonaws.logging.Log
    public boolean isDebugEnabled() {
        if (android.util.Log.isLoggable(this.tag, 3)) {
            return getLevel() == null || getLevel().getValue() <= LogFactory.Level.DEBUG.getValue();
        }
        return false;
    }

    @Override // com.amazonaws.logging.Log
    public boolean isErrorEnabled() {
        if (android.util.Log.isLoggable(this.tag, 6)) {
            return getLevel() == null || getLevel().getValue() <= LogFactory.Level.ERROR.getValue();
        }
        return false;
    }

    @Override // com.amazonaws.logging.Log
    public boolean isInfoEnabled() {
        if (android.util.Log.isLoggable(this.tag, 4)) {
            return getLevel() == null || getLevel().getValue() <= LogFactory.Level.INFO.getValue();
        }
        return false;
    }

    @Override // com.amazonaws.logging.Log
    public boolean isTraceEnabled() {
        if (android.util.Log.isLoggable(this.tag, 2)) {
            return getLevel() == null || getLevel().getValue() <= LogFactory.Level.TRACE.getValue();
        }
        return false;
    }

    @Override // com.amazonaws.logging.Log
    public boolean isWarnEnabled() {
        if (android.util.Log.isLoggable(this.tag, 5)) {
            return getLevel() == null || getLevel().getValue() <= LogFactory.Level.WARN.getValue();
        }
        return false;
    }

    @Override // com.amazonaws.logging.Log
    public void trace(Object obj) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.TRACE.getValue()) {
            android.util.Log.v(this.tag, obj.toString());
        }
    }

    @Override // com.amazonaws.logging.Log
    public void trace(Object obj, Throwable th) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.TRACE.getValue()) {
            android.util.Log.v(this.tag, obj.toString(), th);
        }
    }

    @Override // com.amazonaws.logging.Log
    public void debug(Object obj) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.DEBUG.getValue()) {
            android.util.Log.d(this.tag, obj.toString());
        }
    }

    @Override // com.amazonaws.logging.Log
    public void debug(Object obj, Throwable th) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.DEBUG.getValue()) {
            android.util.Log.d(this.tag, obj.toString(), th);
        }
    }

    @Override // com.amazonaws.logging.Log
    public void info(Object obj) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.INFO.getValue()) {
            android.util.Log.i(this.tag, obj.toString());
        }
    }

    @Override // com.amazonaws.logging.Log
    public void info(Object obj, Throwable th) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.INFO.getValue()) {
            android.util.Log.i(this.tag, obj.toString(), th);
        }
    }

    @Override // com.amazonaws.logging.Log
    public void warn(Object obj) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.WARN.getValue()) {
            android.util.Log.w(this.tag, obj.toString());
        }
    }

    @Override // com.amazonaws.logging.Log
    public void warn(Object obj, Throwable th) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.WARN.getValue()) {
            android.util.Log.w(this.tag, obj.toString(), th);
        }
    }

    @Override // com.amazonaws.logging.Log
    public void error(Object obj) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.ERROR.getValue()) {
            android.util.Log.e(this.tag, obj.toString());
        }
    }

    @Override // com.amazonaws.logging.Log
    public void error(Object obj, Throwable th) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.ERROR.getValue()) {
            android.util.Log.e(this.tag, obj.toString(), th);
        }
    }

    @Override // com.amazonaws.logging.Log
    public void setLevel(LogFactory.Level level) {
        this.level = level;
    }

    private LogFactory.Level getLevel() {
        LogFactory.Level level = this.level;
        return level != null ? level : LogFactory.getLevel();
    }
}
