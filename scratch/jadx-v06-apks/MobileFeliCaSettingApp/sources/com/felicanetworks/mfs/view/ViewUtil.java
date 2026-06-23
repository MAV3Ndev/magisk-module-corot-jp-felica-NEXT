package com.felicanetworks.mfs.view;

import android.text.SpannableStringBuilder;
import android.text.style.URLSpan;
import android.view.View;
import com.felicanetworks.common.cmnlib.sg.SgMgrException;
import com.felicanetworks.common.cmnview.ViewLayer;
import com.felicanetworks.mfslib.MfsAppContext;
import com.felicanetworks.mfslib.launch.AppLauncher;
import com.felicanetworks.mfslib.launch.AppLauncherException;
import com.felicanetworks.mfslib.sg.MfsSgMgr;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class ViewUtil {

    public interface ClickListener {
        void onClick(String str);
    }

    public static String toString(ViewLayer viewLayer) {
        return "null";
    }

    public static void showBrowser(MfsAppContext mfsAppContext, String str) throws AppLauncherException {
        new AppLauncher(mfsAppContext, str).startApplication();
    }

    public static CharSequence replaceDisclamerLink(MfsAppContext mfsAppContext, String str, ClickListener clickListener) throws SgMgrException {
        Matcher matcher = Pattern.compile(((MfsSgMgr) mfsAppContext.sgMgr).getDisclamerSearchString(), 16).matcher(str);
        if (!matcher.find()) {
            return null;
        }
        String str2 = (String) mfsAppContext.sgMgr.getSgValue(68);
        LinkUrlSpan linkUrlSpan = new LinkUrlSpan((String) mfsAppContext.sgMgr.getSgValue(69), clickListener);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        spannableStringBuilder.delete(matcher.start(), matcher.end());
        spannableStringBuilder.insert(matcher.start(), (CharSequence) str2);
        spannableStringBuilder.setSpan(linkUrlSpan, matcher.start(), matcher.start() + str2.length(), 33);
        return spannableStringBuilder;
    }

    public static class LinkUrlSpan extends URLSpan {
        ClickListener listener;
        String urlStr;

        public LinkUrlSpan(String str, ClickListener clickListener) {
            super(str);
            this.urlStr = str;
            this.listener = clickListener;
        }

        @Override // android.text.style.URLSpan, android.text.style.ClickableSpan
        public void onClick(View view) {
            this.listener.onClick(this.urlStr);
        }
    }
}
