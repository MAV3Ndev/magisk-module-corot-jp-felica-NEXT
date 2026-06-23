package com.felicanetworks.mfc.mfi.db;

import android.content.Context;
import androidx.room.Room;

/* JADX INFO: loaded from: classes.dex */
public class MfiDatabaseSingleton {
    private static final String DATABASE_NAME = "mfic_db";
    private static MfiDatabase instance;

    public static MfiDatabase getInstance(Context context) {
        MfiDatabase mfiDatabase = instance;
        if (mfiDatabase != null) {
            return mfiDatabase;
        }
        MfiDatabase mfiDatabase2 = (MfiDatabase) Room.databaseBuilder(context, MfiDatabase.class, DATABASE_NAME).build();
        instance = mfiDatabase2;
        return mfiDatabase2;
    }
}
