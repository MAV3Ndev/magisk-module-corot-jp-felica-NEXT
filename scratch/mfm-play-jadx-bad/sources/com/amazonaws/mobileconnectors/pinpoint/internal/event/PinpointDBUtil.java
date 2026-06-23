package com.amazonaws.mobileconnectors.pinpoint.internal.event;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import androidx.credentials.exceptions.publickeycredential.DomExceptionUtils;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsEvent;

/* JADX INFO: loaded from: classes.dex */
public class PinpointDBUtil {
    private PinpointDBBase pinpointDBBase;

    public PinpointDBUtil(Context context) {
        if (this.pinpointDBBase == null) {
            this.pinpointDBBase = new PinpointDBBase(context);
        }
    }

    public void closeDB() {
        PinpointDBBase pinpointDBBase = this.pinpointDBBase;
        if (pinpointDBBase != null) {
            pinpointDBBase.closeDBHelper();
        }
    }

    public Uri saveEvent(AnalyticsEvent analyticsEvent) {
        PinpointDBBase pinpointDBBase = this.pinpointDBBase;
        return pinpointDBBase.insert(pinpointDBBase.getContentUri(), generateContentValuesFromEvent(analyticsEvent));
    }

    private ContentValues generateContentValuesFromEvent(AnalyticsEvent analyticsEvent) {
        ContentValues contentValues = new ContentValues();
        String string = analyticsEvent.toJSONObject().toString();
        contentValues.put(EventTable.COLUMN_JSON, string);
        contentValues.put(EventTable.COLUMN_SIZE, Integer.valueOf(string.length()));
        return contentValues;
    }

    public Cursor queryAllEvents() {
        PinpointDBBase pinpointDBBase = this.pinpointDBBase;
        return pinpointDBBase.query(pinpointDBBase.getContentUri(), null, null, null, null, null);
    }

    public Cursor queryOldestEvents(int i) {
        PinpointDBBase pinpointDBBase = this.pinpointDBBase;
        return pinpointDBBase.query(pinpointDBBase.getContentUri(), new String[]{EventTable.COLUMN_ID, EventTable.COLUMN_SIZE}, null, null, null, Integer.toString(i));
    }

    public Cursor queryEventById(int i) {
        return this.pinpointDBBase.query(getEventUri(i), null, null, null, null, null);
    }

    public int deleteEvent(int i, Integer num) {
        return this.pinpointDBBase.delete(getEventUri(i), null, null, num);
    }

    public Uri getContentUri() {
        return this.pinpointDBBase.getContentUri();
    }

    public Uri getEventUri(int i) {
        return Uri.parse(this.pinpointDBBase.getContentUri() + DomExceptionUtils.SEPARATOR + i);
    }

    public long getTotalSize() {
        return this.pinpointDBBase.getTotalSize();
    }
}
