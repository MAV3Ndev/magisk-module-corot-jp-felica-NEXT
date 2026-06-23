package com.felicanetworks.mfm.main.view.views;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/* JADX INFO: loaded from: classes.dex */
public class NoticeListView extends ListView {
    private BannerLayout mBannerLayout;
    private int mPosition;

    public NoticeListView(Context context) {
        super(context);
        this.mPosition = -1;
    }

    public NoticeListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.listViewStyle);
        this.mPosition = -1;
    }

    void registerOnItemListener() {
        setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.felicanetworks.mfm.main.view.views.NoticeListView.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                NoticeListView.this.mPosition = i;
            }
        });
    }

    public void setBannerLayout(BannerLayout bannerLayout) {
        this.mBannerLayout = bannerLayout;
        registerOnItemListener();
    }

    @Override // android.widget.ListView, android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case 21:
            case 22:
            case 23:
                if (this.mPosition == 0) {
                    if (this.mBannerLayout.getBannerViewPager() == null) {
                        return true;
                    }
                    this.mBannerLayout.getBannerViewPager().requestFocus();
                    this.mBannerLayout.getBannerViewPager().dispatchKeyEvent(keyEvent);
                    return true;
                }
                break;
        }
        return super.dispatchKeyEvent(keyEvent);
    }
}
