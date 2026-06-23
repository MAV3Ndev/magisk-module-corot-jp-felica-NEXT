package com.amazonaws.mobileconnectors.pinpoint.internal.core.util;

import com.amazonaws.SDKGlobalConfiguration;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public final class DateUtil {
    private static final String DATE_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final DateFormat ISO_DATE_FORMATTER_UTC;
    private static final long SECS = 1000;

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        ISO_DATE_FORMATTER_UTC = simpleDateFormat;
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    private DateUtil() {
    }

    private static synchronized DateFormat getDateFormat() {
        return ISO_DATE_FORMATTER_UTC;
    }

    public static synchronized String isoDateFromMillis(long j) {
        return getDateFormat().format(new Date(j));
    }

    public static DateFormat createLocaleIndependentDateFormatter(String str) {
        return new SimpleDateFormat(str, Locale.US);
    }

    public static Date getCorrectedDate() {
        Date date = new Date();
        if (SDKGlobalConfiguration.getGlobalTimeOffset() == 0) {
            return date;
        }
        long time = date.getTime();
        long globalTimeOffset = SDKGlobalConfiguration.getGlobalTimeOffset();
        Long.valueOf(globalTimeOffset).getClass();
        return new Date(time - (globalTimeOffset * SECS));
    }
}
