package com.felicanetworks.mfm.main.policy.log;

import android.content.Context;
import android.os.Build;
import androidx.credentials.exceptions.publickeycredential.DomExceptionUtils;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.CommonUtil;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DateFormatter;
import com.felicanetworks.mfm.main.model.internal.main.ModelContext;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.policy.err.ErrorManager;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.main.presenter.internal.ServicePreference;
import com.felicanetworks.mfm.mfcutil.mfc.Felica;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

/* JADX INFO: loaded from: classes3.dex */
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

        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder setTime(String time) {
            this.time = time;
            return this;
        }

        public Builder setException(MfmException exception) {
            this.exception = exception;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setIsUncaughtExp(boolean flag) {
            this.isUncaughtExp = flag;
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
                sb.append(DomExceptionUtils.SEPARATOR);
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

        protected String extractExceptionStackTrace(Exception e) {
            StringBuilder sb = new StringBuilder();
            StackTraceElement[] stackTrace = e.getStackTrace();
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

    public static void error(Throwable exp) {
        error(exp, null);
    }

    public static void error(Throwable exp, String msg) {
        if (exp instanceof MfmException) {
            MfmException mfmException = (MfmException) exp;
            String strOut = new Builder().setTag(_tag).setTime(_date.getSystemTime()).setException(mfmException).setMessage(msg).out();
            if (mfmException.getFirstCaughtException() instanceof RuntimeException) {
                ErrorManager.storeFatalErrorLog(strOut);
            }
        }
    }

    public static void uncaughtExceptionLog(Throwable exp) {
        if (exp instanceof MfmException) {
            ErrorManager.storeFatalErrorLog(new Builder().setTag(_tag).setTime(_date.getSystemTime()).setException((MfmException) exp).setIsUncaughtExp(true).out());
        }
    }

    public static void warning(Throwable exp) {
        warning(exp, null);
    }

    public static void warning(Throwable exp, String msg) {
        if (exp instanceof MfmException) {
            new Builder().setTag(_tag).setTime(_date.getSystemTime()).setException((MfmException) exp).setMessage(msg).out();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    public static void inquiryLog(Context context, String errorCode) {
        String strSubstring;
        String strSubstring2;
        try {
            StringBuilder sb = new StringBuilder("A02B1,");
            try {
                strSubstring = Build.MODEL;
                try {
                    strSubstring2 = Build.MANUFACTURER;
                    try {
                        if (strSubstring.length() > 64) {
                            strSubstring = strSubstring.substring(0, 64);
                        }
                        if (strSubstring2.length() > 16) {
                            strSubstring2 = strSubstring2.substring(0, 16);
                        }
                    } catch (Exception unused) {
                    }
                } catch (Exception unused2) {
                    strSubstring2 = "";
                }
            } catch (Exception unused3) {
                strSubstring = "";
                strSubstring2 = strSubstring;
            }
            sb.append(strSubstring);
            sb.append(",");
            sb.append(strSubstring2);
            sb.append(",");
            try {
                sb.append((String) Sg.getValue(Sg.Key.FELICA_MFC_ISSUER_CODE));
            } catch (Exception unused4) {
                sb.append("");
            }
            sb.append(",");
            try {
                sb.append(ServicePreference.getInstance().loadSeid(context));
            } catch (Exception unused5) {
                sb.append("");
            }
            sb.append(",");
            try {
                sb.append(new Settings().getInbound(context));
            } catch (Exception unused6) {
                sb.append("");
            }
            sb.append(",");
            try {
                sb.append(new MfcExpert(new ModelContext(context.getApplicationContext())).confirmExistMfc(context.getPackageManager()).ordinal());
            } catch (Exception unused7) {
                sb.append("");
            }
            sb.append(",");
            try {
                sb.append(Felica.getMFCVersion(context));
            } catch (Exception unused8) {
                sb.append("");
            }
            sb.append(",");
            sb.append(errorCode);
            sb.append(",");
            try {
                sb.append(new DateFormatter(DateFormatter.DATE_TIME_MSEC, "Asia/Tokyo").format(new Date()));
            } catch (Exception unused9) {
                sb.append("");
            }
            ErrorManager.storeInquiryLog(sb.toString());
        } catch (Exception unused10) {
        }
    }
}
