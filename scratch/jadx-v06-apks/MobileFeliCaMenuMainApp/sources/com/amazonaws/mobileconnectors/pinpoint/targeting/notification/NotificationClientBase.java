package com.amazonaws.mobileconnectors.pinpoint.targeting.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.NotificationCompat;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsEvent;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.PinpointContext;
import com.amazonaws.mobileconnectors.pinpoint.targeting.notification.NotificationClient;
import com.amazonaws.services.pinpoint.model.ChannelType;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/* JADX INFO: loaded from: classes.dex */
abstract class NotificationClientBase {
    private static final int ANDROID_JELLYBEAN = 16;
    private static final int ANDROID_KITKAT = 19;
    private static final int ANDROID_LOLLIPOP = 21;
    private static final int ANDROID_MARSHMALLOW = 23;
    private static final int ANDROID_NOUGAT = 24;
    private static final int ANDROID_OREO = 26;
    private static final String APP_OPS_MODE_ALLOWED = "MODE_ALLOWED";
    private static final String APP_OPS_SERVICE = "APP_OPS_SERVICE";
    private static final int BITS_TO_SHIFT_FOR_ALPHA = 24;
    private static final float BLUE_MULTIPLIER = 0.114f;
    protected static final String CAMPAIGN_ACTIVITY_ID_ATTRIBUTE_KEY = "campaign_activity_id";
    protected static final String CAMPAIGN_ACTIVITY_ID_PUSH_KEY = "pinpoint.campaign.campaign_activity_id";
    protected static final String CAMPAIGN_ID_ATTRIBUTE_KEY = "campaign_id";
    protected static final String CAMPAIGN_ID_PUSH_KEY = "pinpoint.campaign.campaign_id";
    protected static final String CAMPAIGN_PUSH_KEY_PREFIX = "pinpoint.campaign.";
    protected static final String CAMPAIGN_TREATMENT_ID_ATTRIBUTE_KEY = "treatment_id";
    protected static final String CAMPAIGN_TREATMENT_ID_PUSH_KEY = "pinpoint.campaign.treatment_id";
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String DEFAULT_NOTIFICATION_CHANNEL_ID = "PINPOINT.NOTIFICATION";
    private static final String DEVICE_TOKEN_PREF_KEY = "AWSPINPOINT.GCMTOKEN";
    static final String DIRECT_CAMPAIGN_SEND = "_DIRECT";
    private static final String EVENT_SOURCE_DEEP_LINK_PUSH_KEY = "pinpoint.deeplink";
    private static final String EVENT_SOURCE_OPEN_APP_PUSH_KEY = "pinpoint.openApp";
    private static final String EVENT_SOURCE_URL_PUSH_KEY = "pinpoint.url";
    private static final String GCM_NOTIFICATION_PUSH_KEY_PREFIX = "pinpoint.notification.";
    private static final float GREEN_MULTIPLIER = 0.587f;
    public static final String INTENT_SNS_NOTIFICATION_DATA = "data";
    public static final String INTENT_SNS_NOTIFICATION_FROM = "from";
    private static final int INVALID_RESOURCE = 0;
    protected static final String JOURNEY_ACTIVITY_ID_ATTRIBUTE_KEY = "journey_activity_id";
    protected static final String JOURNEY_ID_ATTRIBUTE_KEY = "journey_id";
    private static final String NOTIFICATION_BODY_PUSH_KEY = "pinpoint.notification.body";
    private static final int NOTIFICATION_CHANNEL_IMPORTANCE = 4;
    private static final String NOTIFICATION_COLOR_PUSH_KEY = "pinpoint.notification.color";
    private static final String NOTIFICATION_ICON_PUSH_KEY = "pinpoint.notification.icon";
    private static final String NOTIFICATION_SILENT_PUSH_KEY = "pinpoint.notification.silentPush";
    private static final String NOTIFICATION_TITLE_PUSH_KEY = "pinpoint.notification.title";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
    private static final String PINPOINT_IMAGE_ICON_PUSH_KEY = "pinpoint.notification.imageIconUrl";
    private static final String PINPOINT_IMAGE_PUSH_KEY = "pinpoint.notification.imageUrl";
    private static final String PINPOINT_IMAGE_SMALL_ICON_PUSH_KEY = "pinpoint.notification.imageSmallIconUrl";
    protected static final String PINPOINT_PUSH_KEY_PREFIX = "pinpoint.";
    private static final float RED_MULTIPLIER = 0.299f;
    private static final String REQUEST_ID = "requestId";
    private static final int TRANSPARENT_WHITE_COLOR = 16777215;
    private final AppUtil appUtil;
    private Method bigPictureMethod;
    private Method bigTextMethod;
    private Method buildMethod;
    private Method createWithBitmapMethod;
    protected final PinpointContext pinpointContext;
    private Method setContentIntent;
    private Method setContentTextMethod;
    private Method setContentTitleMethod;
    private Method setLargeIconMethod;
    private Method setPriorityMethod;
    private Method setSmallIconMethod;
    private Method setSmallIconResIdMethod;
    private Method setSoundMethod;
    private Method setStyleMethod;
    private Method setSummaryMethod;
    private volatile String theDeviceToken;
    protected static final Log log = LogFactory.getLog((Class<?>) NotificationClientBase.class);
    private static final CharSequence DEFAULT_NOTIFICATION_CHANNEL_NAME = "Notifications";
    private static Random random = new Random();
    private static final int MAX_ALPHA = Color.alpha(-1);
    private Constructor<?> notificationBuilderConstructor = null;
    private Class<?> notificationBuilderClass = null;
    private Class<?> notificationChannelClass = null;
    private Class<?> notificationBigTextStyleClass = null;
    private Class<?> notificationBigPictureStyleClass = null;
    private Class<?> notificationStyleClass = null;
    private Class<?> iconClass = null;
    private Class<?> appOpsClass = null;
    private Method checkOpNoThrowMethod = null;
    private Field opPostNotificationField = null;
    private Field modeAllowedField = null;
    private String notificationChannelId = null;
    private final List<DeviceTokenRegisteredHandler> deviceRegisteredHandlers = new ArrayList();

    protected abstract PendingIntent createOpenAppPendingIntent(Bundle bundle, Class<?> cls, String str, int i, String str2);

    public abstract String getChannelType();

    protected NotificationClientBase(PinpointContext pinpointContext) {
        this.pinpointContext = pinpointContext;
        this.appUtil = new AppUtil(pinpointContext.getApplicationContext());
        loadDeviceToken();
    }

    public final void addDeviceTokenRegisteredHandler(DeviceTokenRegisteredHandler deviceTokenRegisteredHandler) {
        if (deviceTokenRegisteredHandler == null) {
            throw new IllegalArgumentException("DeviceTokenRegisteredHandler cannot be null.");
        }
        this.deviceRegisteredHandlers.add(deviceTokenRegisteredHandler);
    }

    public final void removeDeviceTokenRegisteredHandler(DeviceTokenRegisteredHandler deviceTokenRegisteredHandler) {
        this.deviceRegisteredHandlers.remove(deviceTokenRegisteredHandler);
    }

    public final void registerDeviceToken(String str) {
        setDeviceToken(str);
    }

    public final void registerDeviceToken(String str, String str2) {
        setDeviceToken(str + ":" + str2);
    }

    private void setDeviceToken(String str) {
        this.theDeviceToken = str;
        this.pinpointContext.getSystem().getPreferences().putString(DEVICE_TOKEN_PREF_KEY, str);
        Iterator<DeviceTokenRegisteredHandler> it = this.deviceRegisteredHandlers.iterator();
        while (it.hasNext()) {
            it.next().tokenRegistered(str);
        }
    }

    private void loadDeviceToken() {
        this.theDeviceToken = this.pinpointContext.getSystem().getPreferences().getString(DEVICE_TOKEN_PREF_KEY, null);
    }

    public final String getDeviceToken() {
        loadDeviceToken();
        return this.theDeviceToken;
    }

    private Resources getPackageResources() {
        PackageManager packageManager = this.pinpointContext.getApplicationContext().getPackageManager();
        try {
            return packageManager.getResourcesForApplication(packageManager.getApplicationInfo(this.pinpointContext.getApplicationContext().getPackageName(), 128));
        } catch (PackageManager.NameNotFoundException e) {
            log.error("Can't find resources for our application package.", e);
            return null;
        }
    }

    private int getNotificationIconResourceId(String str) {
        int identifier;
        PackageManager packageManager = this.pinpointContext.getApplicationContext().getPackageManager();
        try {
            String packageName = this.pinpointContext.getApplicationContext().getPackageName();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 128);
            return (str == null || (identifier = packageManager.getResourcesForApplication(applicationInfo).getIdentifier(str, "drawable", packageName)) == 0) ? applicationInfo.icon : identifier;
        } catch (PackageManager.NameNotFoundException e) {
            log.error("Can't find icon for our application package.", e);
            return 0;
        }
    }

    private Notification createLegacyNotification(int i, String str, String str2, PendingIntent pendingIntent) {
        return new NotificationCompat.Builder(this.pinpointContext.getApplicationContext()).setContentIntent(pendingIntent).setContentText(str2).setContentTitle(str).setSmallIcon(i).build();
    }

    private boolean initClassesAndMethodsByReflection() {
        if (this.notificationBuilderClass != null) {
            return true;
        }
        try {
            this.notificationBuilderClass = Class.forName("android.app.Notification$Builder");
            this.notificationBigTextStyleClass = Class.forName("android.app.Notification$BigTextStyle");
            this.notificationStyleClass = Class.forName("android.app.Notification$Style");
            this.notificationBigPictureStyleClass = Class.forName("android.app.Notification$BigPictureStyle");
            if (Build.VERSION.SDK_INT >= 24) {
                this.iconClass = Class.forName("android.graphics.drawable.Icon");
            }
            if (Build.VERSION.SDK_INT >= 26) {
                this.notificationChannelClass = Class.forName("android.app.NotificationChannel");
            }
            return buildMethodsByReflection();
        } catch (ClassNotFoundException e) {
            log.debug("Failed to get notification builder classes by reflection : " + e.getMessage(), e);
            return false;
        }
    }

    private boolean buildMethodsByReflection() {
        try {
            if (Build.VERSION.SDK_INT >= 26) {
                this.notificationBuilderConstructor = this.notificationBuilderClass.getDeclaredConstructor(Context.class, String.class);
            } else {
                this.notificationBuilderConstructor = this.notificationBuilderClass.getDeclaredConstructor(Context.class);
                this.setPriorityMethod = this.notificationBuilderClass.getDeclaredMethod("setPriority", Integer.TYPE);
                this.setSoundMethod = this.notificationBuilderClass.getDeclaredMethod("setSound", Uri.class);
            }
            this.setContentTitleMethod = this.notificationBuilderClass.getDeclaredMethod("setContentTitle", CharSequence.class);
            this.setContentTextMethod = this.notificationBuilderClass.getDeclaredMethod("setContentText", CharSequence.class);
            this.setContentIntent = this.notificationBuilderClass.getDeclaredMethod("setContentIntent", PendingIntent.class);
            this.setStyleMethod = this.notificationBuilderClass.getDeclaredMethod("setStyle", this.notificationStyleClass);
            this.setSmallIconResIdMethod = this.notificationBuilderClass.getDeclaredMethod("setSmallIcon", Integer.TYPE);
            this.buildMethod = this.notificationBuilderClass.getDeclaredMethod("build", new Class[0]);
            this.bigTextMethod = this.notificationBigTextStyleClass.getDeclaredMethod("bigText", CharSequence.class);
            this.bigPictureMethod = this.notificationBigPictureStyleClass.getDeclaredMethod("bigPicture", Bitmap.class);
            this.setSummaryMethod = this.notificationBigPictureStyleClass.getDeclaredMethod("setSummaryText", CharSequence.class);
            this.setLargeIconMethod = this.notificationBuilderClass.getDeclaredMethod("setLargeIcon", Bitmap.class);
            if (Build.VERSION.SDK_INT >= 24) {
                this.setSmallIconMethod = this.notificationBuilderClass.getDeclaredMethod("setSmallIcon", this.iconClass);
                this.createWithBitmapMethod = this.iconClass.getDeclaredMethod("createWithBitmap", Bitmap.class);
            }
            return true;
        } catch (NoSuchMethodException e) {
            log.debug("Failed to get notification builder methods by reflection. : " + e.getMessage(), e);
            return false;
        }
    }

    private Object retrieveNotificationChannel(String str) {
        if (str == null) {
            return null;
        }
        try {
            log.info("Notification channel is needed");
            NotificationManager notificationManager = (NotificationManager) this.pinpointContext.getApplicationContext().getSystemService("notification");
            return notificationManager.getClass().getDeclaredMethod("getNotificationChannel", String.class).invoke(notificationManager, str);
        } catch (IllegalAccessException e) {
            log.debug("Failed to get notification channel by reflection. : " + e.getMessage(), e);
            return null;
        } catch (NoSuchMethodException e2) {
            log.debug("Failed to get notification channel by reflection. : " + e2.getMessage(), e2);
            return null;
        } catch (InvocationTargetException e3) {
            log.debug("Failed to get notification channel by reflection. : " + e3.getMessage(), e3);
            return null;
        }
    }

    private boolean registerDefaultNotificationChannel() {
        try {
            if (retrieveNotificationChannel(DEFAULT_NOTIFICATION_CHANNEL_ID) != null) {
                return true;
            }
            Object objNewInstance = this.notificationChannelClass.getDeclaredConstructor(String.class, CharSequence.class, Integer.TYPE).newInstance(DEFAULT_NOTIFICATION_CHANNEL_ID, DEFAULT_NOTIFICATION_CHANNEL_NAME, 4);
            NotificationManager notificationManager = (NotificationManager) this.pinpointContext.getApplicationContext().getSystemService("notification");
            notificationManager.getClass().getDeclaredMethod("createNotificationChannel", this.notificationChannelClass).invoke(notificationManager, objNewInstance);
            return true;
        } catch (IllegalAccessException e) {
            log.debug("Can't access notification channel  " + e.getMessage(), e);
            return false;
        } catch (InstantiationException e2) {
            log.debug("Exception while instantiating notification channel . : " + e2.getMessage(), e2);
            return false;
        } catch (NoSuchMethodException e3) {
            log.debug("Failed to get notification channel method getId by reflection. : " + e3.getMessage(), e3);
            return false;
        } catch (InvocationTargetException e4) {
            log.debug("Can't invoke notification channel constructor. : " + e4.getMessage(), e4);
            return false;
        }
    }

    static Bitmap convertBitmapToAlphaGreyscale(Bitmap bitmap) {
        int width = bitmap.getWidth() * bitmap.getHeight();
        int[] iArr = new int[width];
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int[] iArr2 = new int[width];
        Integer numValueOf = null;
        boolean z = false;
        for (int i = 0; i < width; i++) {
            int i2 = iArr[i];
            int iAlpha = Color.alpha(i2);
            int iRound = MAX_ALPHA - ((Math.round(Color.red(i2) * RED_MULTIPLIER) + Math.round(Color.green(i2) * GREEN_MULTIPLIER)) + Math.round(Color.blue(i2) * BLUE_MULTIPLIER));
            if (iAlpha != 0) {
                if (numValueOf == null) {
                    numValueOf = Integer.valueOf(i2 & 16777215);
                } else if ((i2 & 16777215) != numValueOf.intValue()) {
                    z = true;
                }
            }
            iArr2[i] = (((iRound * iAlpha) / MAX_ALPHA) << 24) | 16777215;
        }
        if (!z) {
            return Bitmap.createBitmap(iArr, bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        }
        return Bitmap.createBitmap(iArr2, bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
    }

    private Bitmap obtainBitmapFromResId(int i) {
        Resources packageResources = getPackageResources();
        if (packageResources == null) {
            return null;
        }
        return BitmapFactory.decodeResource(packageResources, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    private boolean buildNotificationIcons(int i, String str, String str2, Object obj) throws ExecutionException, InterruptedException {
        Bitmap bitmapObtainBitmapFromResId;
        Bitmap bitmapObtainBitmapFromResId2 = 0;
        bitmapObtainBitmapFromResId2 = 0;
        bitmapObtainBitmapFromResId2 = 0;
        try {
            if (str != null) {
                try {
                    bitmapObtainBitmapFromResId = new DownloadImageTask().execute(str).get();
                } catch (InterruptedException e) {
                    log.error("Interrupted when downloading image : " + e.getMessage(), e);
                    bitmapObtainBitmapFromResId = null;
                } catch (ExecutionException e2) {
                    log.error("Failed to execute download image thread : " + e2.getMessage(), e2);
                    bitmapObtainBitmapFromResId = null;
                }
            } else {
                bitmapObtainBitmapFromResId = null;
            }
            if (bitmapObtainBitmapFromResId == null && ((Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT < 24) || (Build.VERSION.SDK_INT >= 24 && (str != null || str2 == null)))) {
                bitmapObtainBitmapFromResId = obtainBitmapFromResId(i);
            }
            if (bitmapObtainBitmapFromResId != null) {
                this.setLargeIconMethod.invoke(obj, bitmapObtainBitmapFromResId);
            }
            if (this.iconClass != null && Build.VERSION.SDK_INT >= 24) {
                if (str2 != null) {
                    try {
                        bitmapObtainBitmapFromResId2 = new DownloadImageTask().execute(str2).get();
                    } catch (InterruptedException e3) {
                        log.error("Interrupted when downloading small icon : " + e3.getMessage(), e3);
                    } catch (ExecutionException e4) {
                        log.error("Failed to execute download image small icon thread : " + e4.getMessage(), e4);
                    }
                }
                if (bitmapObtainBitmapFromResId2 == 0) {
                    bitmapObtainBitmapFromResId2 = obtainBitmapFromResId(i);
                }
                if (bitmapObtainBitmapFromResId2 != 0) {
                    this.setSmallIconMethod.invoke(obj, this.createWithBitmapMethod.invoke(this.iconClass, convertBitmapToAlphaGreyscale(bitmapObtainBitmapFromResId2)));
                    return true;
                }
            }
            this.setSmallIconResIdMethod.invoke(obj, Integer.valueOf(i));
            return true;
        } catch (IllegalAccessException e5) {
            log.debug("Can't access notification builder methods. : " + e5.getMessage(), e5);
            return false;
        } catch (InvocationTargetException e6) {
            log.debug("Can't invoke notification builder methods. : " + e6.getMessage(), e6);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v37, types: [java.lang.reflect.Method] */
    /* JADX WARN: Type inference failed for: r6v16, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v16 */
    /* JADX WARN: Type inference failed for: r7v2, types: [com.amazonaws.mobileconnectors.pinpoint.targeting.notification.NotificationClientBase$1] */
    public Notification createNotification(int i, String str, String str2, String str3, String str4, String str5, PendingIntent pendingIntent) throws ExecutionException, InterruptedException {
        Object objNewInstance;
        String str6;
        log.info("Create Notification:" + str + ", Content:" + str2);
        if (Build.VERSION.SDK_INT < 16) {
            return createLegacyNotification(i, str, str2, pendingIntent);
        }
        if (!initClassesAndMethodsByReflection()) {
            return createLegacyNotification(i, str, str2, pendingIntent);
        }
        ?? r7 = 0;
        r7 = 0;
        r7 = 0;
        if (Build.VERSION.SDK_INT >= 26 && ((str6 = this.notificationChannelId) == null || retrieveNotificationChannel(str6) == null)) {
            this.notificationChannelId = DEFAULT_NOTIFICATION_CHANNEL_ID;
            if (!registerDefaultNotificationChannel()) {
                this.notificationChannelId = null;
            }
        }
        try {
            if (Build.VERSION.SDK_INT < 26 || this.notificationChannelId == null) {
                objNewInstance = this.notificationBuilderConstructor.newInstance(this.pinpointContext.getApplicationContext());
                this.setPriorityMethod.invoke(objNewInstance, 1);
            } else {
                objNewInstance = this.notificationBuilderConstructor.newInstance(this.pinpointContext.getApplicationContext(), this.notificationChannelId);
            }
            Object obj = objNewInstance;
            Object objNewInstance2 = this.notificationBigTextStyleClass.newInstance();
            Object objNewInstance3 = this.notificationBigPictureStyleClass.newInstance();
            try {
                this.setContentTitleMethod.invoke(obj, str);
                this.setContentTextMethod.invoke(obj, str2);
                this.setContentIntent.invoke(obj, pendingIntent);
                if (Build.VERSION.SDK_INT < 26) {
                    this.setSoundMethod.invoke(obj, RingtoneManager.getDefaultUri(2));
                }
                if (!buildNotificationIcons(i, str4, str5, obj)) {
                    return createLegacyNotification(i, str, str2, pendingIntent);
                }
                if (str3 != null) {
                    try {
                        r7 = new DownloadImageTask().execute(str3).get();
                    } catch (InterruptedException e) {
                        log.error("Interrupted when downloading image : " + e.getMessage(), e);
                    } catch (ExecutionException e2) {
                        log.error("Failed execute download image thread : " + e2.getMessage(), e2);
                    }
                }
                if (r7 != 0) {
                    this.bigPictureMethod.invoke(objNewInstance3, new Object[]{r7});
                    this.setSummaryMethod.invoke(objNewInstance3, str2);
                    this.setStyleMethod.invoke(obj, objNewInstance3);
                } else {
                    this.bigTextMethod.invoke(objNewInstance2, str2);
                    this.setStyleMethod.invoke(obj, objNewInstance2);
                }
                return (Notification) this.buildMethod.invoke(obj, new Object[0]);
            } catch (IllegalAccessException e3) {
                log.debug("Can't access notification builder methods. : " + e3.getMessage(), e3);
                return createLegacyNotification(i, str, str2, pendingIntent);
            } catch (InvocationTargetException e4) {
                log.debug("Can't invoke notification builder methods. : " + e4.getMessage(), e4);
                return createLegacyNotification(i, str, str2, pendingIntent);
            }
        } catch (IllegalAccessException e5) {
            log.debug("Can't access notification builder or bigTextStyle or bigPictureStyle classes. : " + e5.getMessage(), e5);
            return createLegacyNotification(i, str, str2, pendingIntent);
        } catch (InstantiationException e6) {
            log.debug("Exception while instantiating notification builder or bigTextStyle or bigPictureStyle classes. : " + e6.getMessage(), e6);
            return createLegacyNotification(i, str, str2, pendingIntent);
        } catch (InvocationTargetException e7) {
            log.debug("Can't invoke notification builder constructor. : " + e7.getMessage(), e7);
            return createLegacyNotification(i, str, str2, pendingIntent);
        }
    }

    final Intent notificationIntent(Bundle bundle, String str, int i, String str2, Class<?> cls) {
        EventSourceType eventSourceType = EventSourceType.getEventSourceType(bundle);
        Intent intent = new Intent(this.pinpointContext.getApplicationContext(), cls);
        intent.setAction(str2);
        intent.putExtras(bundle);
        intent.putExtra("from", eventSourceType.getEventTypeOpenend());
        intent.putExtra(eventSourceType.getEventSourceIdAttributeKey(), str);
        intent.putExtra(REQUEST_ID, i);
        intent.setPackage(this.pinpointContext.getApplicationContext().getPackageName());
        return intent;
    }

    int getNotificationRequestId(String str, String str2) {
        if (DIRECT_CAMPAIGN_SEND.equals(str) && str2 == null) {
            return random.nextInt();
        }
        return (str + ":" + str2).hashCode();
    }

    private boolean displayNotification(final Bundle bundle, final Class<?> cls, final String str, final String str2, final String str3, Map<String, String> map, final String str4, String str5, String str6) {
        log.info("Display Notification: " + bundle.toString());
        final int notificationIconResourceId = getNotificationIconResourceId(bundle.getString(NOTIFICATION_ICON_PUSH_KEY));
        if (notificationIconResourceId == 0) {
            return false;
        }
        final String string = bundle.getString(NOTIFICATION_TITLE_PUSH_KEY);
        final String string2 = bundle.getString(NOTIFICATION_BODY_PUSH_KEY);
        final String str7 = map.get(str5);
        String str8 = map.get(str6);
        final int notificationRequestId = getNotificationRequestId(str7, str8);
        log.debug("Displaying Notification for campaign/journey: " + str7 + " ; activity: " + str8 + " ; notification requestId: " + notificationRequestId);
        new Thread(new Runnable() { // from class: com.amazonaws.mobileconnectors.pinpoint.targeting.notification.NotificationClientBase.1
            @Override // java.lang.Runnable
            public void run() throws ExecutionException, InterruptedException {
                int color;
                NotificationClientBase notificationClientBase = NotificationClientBase.this;
                Notification notificationCreateNotification = notificationClientBase.createNotification(notificationIconResourceId, string, string2, str, str2, str3, notificationClientBase.createOpenAppPendingIntent(bundle, cls, str7, notificationRequestId, str4));
                notificationCreateNotification.flags |= 16;
                notificationCreateNotification.defaults |= 3;
                if (Build.VERSION.SDK_INT >= 21) {
                    NotificationClientBase.log.info("SDK greater than 21 detected: " + Build.VERSION.SDK_INT);
                    String string3 = bundle.getString(NotificationClientBase.NOTIFICATION_COLOR_PUSH_KEY);
                    if (string3 != null) {
                        try {
                            color = Color.parseColor(string3);
                        } catch (IllegalArgumentException e) {
                            NotificationClientBase.log.warn("Couldn't parse notification color.", e);
                            color = 0;
                        }
                        Exception exc = null;
                        try {
                            Field declaredField = notificationCreateNotification.getClass().getDeclaredField(TypedValues.Custom.S_COLOR);
                            declaredField.setAccessible(true);
                            declaredField.set(notificationCreateNotification, Integer.valueOf(color));
                        } catch (IllegalAccessException | NoSuchFieldException e2) {
                            exc = e2;
                        }
                        if (exc != null) {
                            NotificationClientBase.log.error("Couldn't set notification color : " + exc.getMessage(), exc);
                        }
                    }
                }
                ((NotificationManager) NotificationClientBase.this.pinpointContext.getApplicationContext().getSystemService("notification")).notify(notificationRequestId, notificationCreateNotification);
            }
        }).start();
        return true;
    }

    private boolean openApp() {
        Intent launchIntentForPackage = this.pinpointContext.getApplicationContext().getPackageManager().getLaunchIntentForPackage(this.pinpointContext.getApplicationContext().getPackageName());
        if (launchIntentForPackage == null) {
            log.error("Couldn't get app launch intent for pinpoint notification.");
            return false;
        }
        launchIntentForPackage.setFlags(270532608);
        launchIntentForPackage.setPackage(null);
        this.pinpointContext.getApplicationContext().startActivity(launchIntentForPackage);
        return true;
    }

    private void openURL(String str, boolean z) {
        if (!str.startsWith("http://") && !str.startsWith("https://") && !z) {
            str = "http://" + str;
        }
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        intent.setFlags(268435456);
        if (intent.resolveActivity(this.pinpointContext.getApplicationContext().getPackageManager()) != null) {
            this.pinpointContext.getApplicationContext().startActivity(intent);
        }
    }

    NotificationClient.PushResult handleNotificationOpen(Map<String, String> map, Bundle bundle) {
        if (map != null) {
            EventSourceType eventSourceType = EventSourceType.getEventSourceType(bundle);
            if (this.pinpointContext.getSessionClient() != null) {
                this.pinpointContext.getSessionClient().stopSession();
            }
            this.pinpointContext.getAnalyticsClient().updateEventSourceGlobally(map);
            this.pinpointContext.getAnalyticsClient().recordEvent(this.pinpointContext.getAnalyticsClient().createEvent(eventSourceType.getEventTypeOpenend()));
            this.pinpointContext.getAnalyticsClient().submitEvents();
            String string = bundle.getString(EVENT_SOURCE_URL_PUSH_KEY);
            if (string != null) {
                openURL(string, false);
                return NotificationClient.PushResult.NOTIFICATION_OPENED;
            }
            String string2 = bundle.getString(EVENT_SOURCE_DEEP_LINK_PUSH_KEY);
            if (string2 != null) {
                openURL(string2, true);
                return NotificationClient.PushResult.NOTIFICATION_OPENED;
            }
            if (bundle.getString(EVENT_SOURCE_OPEN_APP_PUSH_KEY) == null) {
                log.warn("No key/value present to determine action for pinpoint notification, default to open app.");
            }
            openApp();
        }
        return NotificationClient.PushResult.NOTIFICATION_OPENED;
    }

    public NotificationClient.PushResult handleNotificationReceived(NotificationDetails notificationDetails) {
        AnalyticsEvent analyticsEventCreateEvent;
        NotificationClient.PushResult pushResult;
        EventSourceType eventSourceType = EventSourceType.getEventSourceType(notificationDetails.getBundle());
        if (eventSourceType.isUnkown()) {
            return NotificationClient.PushResult.NOT_HANDLED;
        }
        Bundle bundle = notificationDetails.getBundle();
        Map<String, String> attributes = eventSourceType.getAttributeParser().parseAttributes(bundle);
        if (attributes.isEmpty()) {
            return NotificationClient.PushResult.NOT_HANDLED;
        }
        boolean zIsAppInForeground = this.appUtil.isAppInForeground();
        String from = notificationDetails.getFrom();
        Class<?> targetClass = notificationDetails.getTargetClass();
        String string = bundle.getString(PINPOINT_IMAGE_PUSH_KEY);
        String string2 = bundle.getString(PINPOINT_IMAGE_ICON_PUSH_KEY);
        String string3 = bundle.getString(PINPOINT_IMAGE_SMALL_ICON_PUSH_KEY);
        String intentAction = notificationDetails.getIntentAction();
        this.notificationChannelId = notificationDetails.getNotificationChannelId();
        log.info("Event source Attributes are:" + attributes);
        if (eventSourceType.getEventTypeOpenend().equals(from)) {
            return handleNotificationOpen(attributes, bundle);
        }
        this.pinpointContext.getAnalyticsClient().updateEventSourceGlobally(attributes);
        if (zIsAppInForeground) {
            analyticsEventCreateEvent = this.pinpointContext.getAnalyticsClient().createEvent(eventSourceType.getEventTypeReceivedForeground());
        } else {
            analyticsEventCreateEvent = this.pinpointContext.getAnalyticsClient().createEvent(eventSourceType.getEventTypeReceivedBackground());
        }
        analyticsEventCreateEvent.addAttribute("isAppInForeground", Boolean.toString(zIsAppInForeground));
        try {
            if (!this.pinpointContext.getPinpointConfiguration().getShouldPostNotificationsInForeground() && zIsAppInForeground) {
                pushResult = NotificationClient.PushResult.APP_IN_FOREGROUND;
            } else if ("1".equalsIgnoreCase(bundle.getString(NOTIFICATION_SILENT_PUSH_KEY))) {
                pushResult = NotificationClient.PushResult.SILENT;
            } else if (!areAppNotificationsEnabled() || !displayNotification(bundle, targetClass, string, string2, string3, attributes, intentAction, eventSourceType.getEventSourceIdAttributeKey(), eventSourceType.getEventSourceActivityIdAttributeKey())) {
                analyticsEventCreateEvent.addAttribute("isOptedOut", "true");
                pushResult = NotificationClient.PushResult.OPTED_OUT;
            } else {
                this.pinpointContext.getAnalyticsClient().recordEvent(analyticsEventCreateEvent);
                this.pinpointContext.getAnalyticsClient().submitEvents();
                return NotificationClient.PushResult.POSTED_NOTIFICATION;
            }
            return pushResult;
        } finally {
            this.pinpointContext.getAnalyticsClient().recordEvent(analyticsEventCreateEvent);
            this.pinpointContext.getAnalyticsClient().submitEvents();
        }
    }

    @Deprecated
    public final NotificationClient.PushResult handleCampaignPush(NotificationDetails notificationDetails) {
        return handleNotificationReceived(notificationDetails);
    }

    public final boolean areAppNotificationsEnabled() {
        AppLevelOptOutProvider appLevelOptOutProvider = this.pinpointContext.getPinpointConfiguration().getAppLevelOptOutProvider();
        if (appLevelOptOutProvider == null || !appLevelOptOutProvider.isOptedOut()) {
            return areAppNotificationsEnabledOnPlatform();
        }
        return false;
    }

    boolean areAppNotificationsEnabledOnPlatform() {
        if (Build.VERSION.SDK_INT < 19) {
            return true;
        }
        try {
            Object systemService = this.pinpointContext.getApplicationContext().getSystemService((String) Context.class.getDeclaredField(APP_OPS_SERVICE).get(String.class));
            if (systemService == null) {
                return true;
            }
            ApplicationInfo applicationInfo = this.pinpointContext.getApplicationContext().getApplicationInfo();
            String packageName = this.pinpointContext.getApplicationContext().getPackageName();
            int i = applicationInfo.uid;
            try {
                if (this.appOpsClass == null || this.checkOpNoThrowMethod == null || this.opPostNotificationField == null || this.modeAllowedField == null) {
                    Class<?> cls = Class.forName(systemService.getClass().getName());
                    this.appOpsClass = cls;
                    this.checkOpNoThrowMethod = cls.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
                    this.opPostNotificationField = this.appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
                    this.modeAllowedField = this.appOpsClass.getDeclaredField(APP_OPS_MODE_ALLOWED);
                }
                return this.modeAllowedField.getInt(null) == ((Integer) this.checkOpNoThrowMethod.invoke(systemService, Integer.valueOf(this.opPostNotificationField.getInt(null)), Integer.valueOf(i), packageName)).intValue();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return true;
            }
        } catch (IllegalAccessException e2) {
            log.error(e2.getMessage(), e2);
            return true;
        } catch (NoSuchFieldException e3) {
            log.error(e3.getMessage(), e3);
            return true;
        }
    }

    /* JADX INFO: renamed from: com.amazonaws.mobileconnectors.pinpoint.targeting.notification.NotificationClientBase$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$amazonaws$services$pinpoint$model$ChannelType;

        static {
            int[] iArr = new int[ChannelType.values().length];
            $SwitchMap$com$amazonaws$services$pinpoint$model$ChannelType = iArr;
            try {
                iArr[ChannelType.ADM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$amazonaws$services$pinpoint$model$ChannelType[ChannelType.GCM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$amazonaws$services$pinpoint$model$ChannelType[ChannelType.BAIDU.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static NotificationClientBase createClient(PinpointContext pinpointContext, ChannelType channelType) {
        int i = AnonymousClass2.$SwitchMap$com$amazonaws$services$pinpoint$model$ChannelType[channelType.ordinal()];
        if (i == 1) {
            return new ADMNotificationClient(pinpointContext);
        }
        if (i == 2) {
            return new GCMNotificationClient(pinpointContext);
        }
        if (i == 3) {
            return new BaiduNotificationClient(pinpointContext);
        }
        return new GCMNotificationClient(pinpointContext);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private DownloadImageTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Bitmap doInBackground(String... strArr) {
            try {
                return BitmapFactory.decodeStream(new URL(strArr[0]).openConnection().getInputStream());
            } catch (IOException e) {
                NotificationClientBase.log.error("Cannot download or find image for rich notification.", e);
                return null;
            }
        }
    }
}
