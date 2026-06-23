package com.felicanetworks.mfc;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.IBinder;
import com.felicanetworks.mfc.IFelica;
import com.felicanetworks.mfc.felica.access_control.AccessConfig;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class FelicaAdapter extends Service {
    private static final String NOTIFICATION_CHANNEL_ID = "mfc-channel";
    private static final int REQUEST_CODE_FOR_INTENT = 0;
    private static final int START_FOREGROUND_NOTIFICATION_ID = 1;
    private static final String URL_SCHEME_FOR_PACKAGE = "package";
    private IFelica.Stub mIFelicaImpl = null;
    private boolean mIsGpDevice = false;

    public FelicaAdapter() {
        LogMgr.log(4, "%s", "000");
        LogMgr.log(4, "%s", "999");
    }

    @Override // android.app.Service
    public void onCreate() {
        LogMgr.log(4, "%s", "000");
        this.mIsGpDevice = AccessConfig.isGpDevice();
        if (this.mIsGpDevice) {
            this.mIFelicaImpl = IFelicaGpDeviceImpl.getInstance();
        } else {
            this.mIFelicaImpl = IFelicaImpl.getInstance();
        }
        LogMgr.log(4, "%s : mIFelicaImpl = %s", "999", this.mIFelicaImpl);
    }

    private boolean shouldStartServiceForeground() {
        LogMgr.log(4, "%s", "000");
        int restrictBackgroundStatus = ((ConnectivityManager) getSystemService("connectivity")).getRestrictBackgroundStatus();
        boolean z = true;
        if (restrictBackgroundStatus == 1) {
            LogMgr.log(4, "%s DataSaver is OFF.", "003");
        } else if (restrictBackgroundStatus == 2) {
            LogMgr.log(4, "%s DataSaver is ON (whitelisted).", "002");
        } else {
            if (restrictBackgroundStatus == 3) {
                LogMgr.log(4, "%s DataSaver is ON (not whitelisted).", "001");
            }
            LogMgr.log(6, "%s", "999");
            return z;
        }
        z = false;
        LogMgr.log(6, "%s", "999");
        return z;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        LogMgr.log(4, "%s", "000");
        if (shouldStartServiceForeground()) {
            Intent intent2 = new Intent();
            intent2.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent2.setData(Uri.fromParts(URL_SCHEME_FOR_PACKAGE, getPackageName(), null));
            ((NotificationManager) getSystemService("notification")).createNotificationChannel(new NotificationChannel(NOTIFICATION_CHANNEL_ID, getString(R.string.channel_name), 2));
            startForeground(1, new Notification.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID).setContentTitle(getString(R.string.mfc_app_running_notification_title)).setContentText(getString(R.string.mfc_app_running_notification_text)).setSmallIcon(R.drawable.ic_notification).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_icon)).setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, intent2, 134217728)).build());
        }
        LogMgr.log(4, "%s : mIFelicaImpl = %s", "999", this.mIFelicaImpl);
        return this.mIFelicaImpl;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        LogMgr.log(4, "%s", "000");
        if (this.mIFelicaImpl != null) {
            LogMgr.log(7, "%s", "001");
            if (this.mIsGpDevice) {
                ((IFelicaGpDeviceImpl) this.mIFelicaImpl).doClose(false);
                ((IFelicaGpDeviceImpl) this.mIFelicaImpl).doInactivateFelica(false);
            } else {
                ((IFelicaImpl) this.mIFelicaImpl).doClose(false);
                ((IFelicaImpl) this.mIFelicaImpl).doInactivateFelica(false);
            }
        }
        stopForeground(true);
        LogMgr.log(4, "%s", "999");
        return super.onUnbind(intent);
    }

    @Override // android.app.Service
    public void onDestroy() {
        LogMgr.log(4, "%s", "000");
        if (this.mIFelicaImpl != null) {
            LogMgr.log(7, "%s", "001");
            if (this.mIsGpDevice) {
                ((IFelicaGpDeviceImpl) this.mIFelicaImpl).doClose(false);
                ((IFelicaGpDeviceImpl) this.mIFelicaImpl).doInactivateFelica(false);
            } else {
                ((IFelicaImpl) this.mIFelicaImpl).doClose(false);
                ((IFelicaImpl) this.mIFelicaImpl).doInactivateFelica(false);
            }
        }
        LogMgr.log(4, "%s", "999");
        super.onDestroy();
    }
}
