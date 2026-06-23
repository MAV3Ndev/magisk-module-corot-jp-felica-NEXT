package com.felicanetworks.mfm.main.view.views;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/* JADX INFO: loaded from: classes3.dex */
public class NoticeListView extends ListView {
    private BannerLayout mBannerLayout;
    private int mPosition;

    public NoticeListView(Context context) {
        super(context);
        this.mPosition = -1;
    }

    public NoticeListView(Context context, AttributeSet attrs) {
        super(context, attrs, R.attr.listViewStyle);
        this.mPosition = -1;
    }

    void registerOnItemListener() {
        setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.felicanetworks.mfm.main.view.views.NoticeListView.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NoticeListView.this.mPosition = position;
            }
        });
    }

    public void setBannerLayout(BannerLayout banner) {
        this.mBannerLayout = banner;
        registerOnItemListener();
    }

    @Override // android.widget.ListView, android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case 21:
            case 22:
            case 23:
                if (this.mPosition == 0) {
                    if (this.mBannerLayout.getBannerViewPager() == null) {
                        return true;
                    }
                    this.mBannerLayout.getBannerViewPager().requestFocus();
                    this.mBannerLayout.getBannerViewPager().dispatchKeyEvent(event);
                    return true;
                }
                break;
        }
        return super.dispatchKeyEvent(event);
    }
}
