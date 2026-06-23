package com.felicanetworks.mfc;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.icu.text.SimpleDateFormat;
import android.se.omapi.SEService;
import android.se.omapi.SEService$OnConnectedListener;
import java.util.Locale;
import java.util.concurrent.Executor;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class FSC$$ExternalSyntheticApiModelOutline0 {
    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0002: CONSTRUCTOR (r1v0 android.content.Context), (r2v0 java.lang.String) A[MD:(android.content.Context, java.lang.String):void (c)] call: android.app.Notification.Builder.<init>(android.content.Context, java.lang.String):void type: CONSTRUCTOR */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    public static /* synthetic */ Notification.Builder m(Context context, String str) {
        return new Notification.Builder(context, str);
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0002: CONSTRUCTOR (r1v0 java.lang.String), (r2v0 java.lang.CharSequence), (r3v0 int) A[MD:(java.lang.String, java.lang.CharSequence, int):void (c)] call: android.app.NotificationChannel.<init>(java.lang.String, java.lang.CharSequence, int):void type: CONSTRUCTOR */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    public static /* synthetic */ NotificationChannel m(String str, CharSequence charSequence, int i) {
        return new NotificationChannel(str, charSequence, i);
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0002: CONSTRUCTOR (r1v0 int), (r2v0 android.graphics.BlendMode) A[MD:(int, android.graphics.BlendMode):void (c)] call: android.graphics.BlendModeColorFilter.<init>(int, android.graphics.BlendMode):void type: CONSTRUCTOR */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    public static /* synthetic */ BlendModeColorFilter m(int i, BlendMode blendMode) {
        return new BlendModeColorFilter(i, blendMode);
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0002: CONSTRUCTOR (r1v0 java.lang.String), (r2v0 java.util.Locale) A[MD:(java.lang.String, java.util.Locale):void (c)] call: android.icu.text.SimpleDateFormat.<init>(java.lang.String, java.util.Locale):void type: CONSTRUCTOR */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    public static /* synthetic */ SimpleDateFormat m(String str, Locale locale) {
        return new SimpleDateFormat(str, locale);
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0002: CONSTRUCTOR 
  (r1v0 android.content.Context)
  (r2v0 java.util.concurrent.Executor)
  (r3v0 android.se.omapi.SEService$OnConnectedListener)
 A[MD:(android.content.Context, java.util.concurrent.Executor, android.se.omapi.SEService$OnConnectedListener):void (c)] call: android.se.omapi.SEService.<init>(android.content.Context, java.util.concurrent.Executor, android.se.omapi.SEService$OnConnectedListener):void type: CONSTRUCTOR */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    public static /* synthetic */ SEService m(Context context, Executor executor, SEService$OnConnectedListener sEService$OnConnectedListener) {
        return new SEService(context, executor, sEService$OnConnectedListener);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    /* JADX INFO: renamed from: m, reason: collision with other method in class */
    public static /* synthetic */ void m394m() {
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    /* JADX INFO: renamed from: m$1, reason: collision with other method in class */
    public static /* synthetic */ void m405m$1() {
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    public static /* synthetic */ void m$2() {
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    public static /* synthetic */ void m$3() {
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    public static /* synthetic */ void m$4() {
    }
}
