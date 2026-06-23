package com.felicanetworks.mfm.main.policy.log;

import android.content.Context;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.CommonUtil;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DateFormatter;
import com.felicanetworks.mfm.main.policy.err.ErrorManager;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class LogUtil {
    private static DateFormatter _date;
    private static String _tag;

    private LogUtil() {
    }

    public static void setup(Context context) {
        _tag = (String) Sg.getValue(Sg.Key.SETTING_MFM_NAME);
        _date = new DateFormatter(DateFormatter.DATE_TIME_MSEC, (String) Sg.getValue(Sg.Key.SETTING_TIMEZONE));
    }

    private static class Builder {
        protected MfmException exception;
        protected boolean isUncaughtExp;
        protected String message;
        protected String tag;
        protected String time;

        private Builder() {
            this.isUncaughtExp = false;
        }

        public Builder setTag(String str) {
            this.tag = str;
            return this;
        }

        public Builder setTime(String str) {
            this.time = str;
            return this;
        }

        public Builder setException(MfmException mfmException) {
            this.exception = mfmException;
            return this;
        }

        public Builder setMessage(String str) {
            this.message = str;
            return this;
        }

        public Builder setIsUncaughtExp(boolean z) {
            this.isUncaughtExp = z;
            return this;
        }

        public String out() {
            StringBuilder sb = new StringBuilder();
            String str = this.tag;
            if (str != null) {
                sb.append(str);
            }
            sb.append(",");
            String str2 = this.time;
            if (str2 != null) {
                sb.append(str2);
            }
            sb.append(",");
            MfmException mfmException = this.exception;
            if (mfmException != null) {
                sb.append(mfmException.getErrorCode());
            }
            sb.append(",");
            StackTraceElement stackTraceElementExtractCallLogElement = extractCallLogElement();
            if (stackTraceElementExtractCallLogElement != null) {
                sb.append(CommonUtil.getClassShortName(stackTraceElementExtractCallLogElement.getClassName()));
                sb.append("#");
                sb.append(stackTraceElementExtractCallLogElement.getMethodName());
                sb.append(":");
                sb.append(CommonUtil.intToDecString(stackTraceElementExtractCallLogElement.getLineNumber(), 4));
            } else {
                sb.append(" null ");
            }
            sb.append(",");
            MfmException mfmException2 = this.exception;
            if (mfmException2 != null) {
                sb.append(mfmException2.getClass().getSimpleName());
                sb.append("/");
                sb.append(this.exception.getMessage());
            } else {
                String str3 = this.message;
                if (str3 != null) {
                    sb.append(str3);
                }
            }
            sb.append(",");
            MfmException mfmException3 = this.exception;
            if (mfmException3 != null) {
                if (this.isUncaughtExp) {
                    sb.append(extractExceptionStackTrace(mfmException3.getFirstCaughtException()));
                    this.isUncaughtExp = false;
                } else {
                    sb.append(extractExceptionStackTrace(mfmException3));
                }
            }
            return new String(sb);
        }

        protected StackTraceElement extractCallLogElement() {
            Iterator it = new ArrayList(Arrays.asList(Thread.currentThread().getStackTrace())).iterator();
            StackTraceElement stackTraceElement = null;
            while (it.hasNext()) {
                stackTraceElement = (StackTraceElement) it.next();
                if (stackTraceElement.getClassName().startsWith(LogUtil.class.getName())) {
                    break;
                }
                it.remove();
            }
            while (it.hasNext()) {
                stackTraceElement = (StackTraceElement) it.next();
                if (!stackTraceElement.getClassName().startsWith(LogUtil.class.getName())) {
                    break;
                }
                it.remove();
            }
            return stackTraceElement;
        }

        protected String extractExceptionStackTrace(Exception exc) {
            StringBuilder sb = new StringBuilder();
            StackTraceElement[] stackTrace = exc.getStackTrace();
            int length = 7 >= stackTrace.length ? stackTrace.length : 7;
            for (int i = 0; i < length; i++) {
                sb.append("[");
                sb.append(i);
                sb.append("]");
                sb.append(CommonUtil.getClassShortName(stackTrace[i].getClassName()));
                sb.append("#");
                sb.append(stackTrace[i].getMethodName());
                sb.append(":");
                sb.append(stackTrace[i].getLineNumber());
            }
            return new String(sb);
        }
    }

    private static class DbgBuilder extends Builder {
        private DbgBuilder() {
            super();
        }

        @Override // com.felicanetworks.mfm.main.policy.log.LogUtil.Builder
        public String out() {
            StringBuilder sb = new StringBuilder();
            StackTraceElement stackTraceElementExtractCallLogElement = extractCallLogElement();
            if (stackTraceElementExtractCallLogElement != null) {
                sb.append(CommonUtil.getClassShortName(stackTraceElementExtractCallLogElement.getClassName()));
                sb.append("#");
                sb.append(stackTraceElementExtractCallLogElement.getMethodName());
                sb.append(":");
                sb.append(stackTraceElementExtractCallLogElement.getLineNumber());
            } else {
                sb.append(" null ");
            }
            sb.append(" -> ");
            sb.append(this.message);
            return new String(sb);
        }
    }

    public static void error(Throwable th) {
        error(th, null);
    }

    public static void error(Throwable th, String str) {
        if (th instanceof MfmException) {
            MfmException mfmException = (MfmException) th;
            String strOut = new Builder().setTag(_tag).setTime(_date.getSystemTime()).setException(mfmException).setMessage(str).out();
            if (mfmException.getFirstCaughtException() instanceof RuntimeException) {
                ErrorManager.storeFatalErrorLog(strOut);
            }
        }
    }

    public static void uncaughtExceptionLog(Throwable th) {
        if (th instanceof MfmException) {
            ErrorManager.storeFatalErrorLog(new Builder().setTag(_tag).setTime(_date.getSystemTime()).setException((MfmException) th).setIsUncaughtExp(true).out());
        }
    }

    public static void warning(Throwable th) {
        warning(th, null);
    }

    public static void warning(Throwable th, String str) {
        if (th instanceof MfmException) {
            new Builder().setTag(_tag).setTime(_date.getSystemTime()).setException((MfmException) th).setMessage(str).out();
        }
    }
}
