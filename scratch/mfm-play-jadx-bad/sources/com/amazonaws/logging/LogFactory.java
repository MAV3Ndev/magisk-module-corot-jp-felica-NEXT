package com.amazonaws.logging;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class LogFactory {
    private static final String TAG = "LogFactory";
    private static final Map<String, Log> logMap = new HashMap();
    private static Level globalLogLevel = null;

    public static synchronized Log getLog(Class<?> cls) {
        return getLog(getTruncatedLogTag(cls.getSimpleName()));
    }

    public static synchronized Log getLog(String str) {
        Log androidLog;
        String truncatedLogTag = getTruncatedLogTag(str);
        Map<String, Log> map = logMap;
        Log log = map.get(truncatedLogTag);
        if (log != null) {
            return log;
        }
        if (Environment.isJUnitTest()) {
            androidLog = new ConsoleLog(truncatedLogTag);
        } else {
            androidLog = new AndroidLog(truncatedLogTag);
        }
        map.put(truncatedLogTag, androidLog);
        return androidLog;
    }

    public static void setLevel(Level level) {
        globalLogLevel = level;
    }

    public static Level getLevel() {
        return globalLogLevel;
    }

    private static String getTruncatedLogTag(String str) {
        if (str.length() <= 23) {
            return str;
        }
        getLog(TAG).warn("Truncating log tag length as it exceed 23, the limit imposed by Android on certain API Levels");
        return str.substring(0, 23);
    }

    public enum Level {
        ALL(Integer.MIN_VALUE),
        TRACE(0),
        DEBUG(1),
        INFO(2),
        WARN(3),
        ERROR(4),
        OFF(Integer.MAX_VALUE);

        private final int value;

        public int getValue() {
            return this.value;
        }

        Level(int i) {
            this.value = i;
        }
    }
}
