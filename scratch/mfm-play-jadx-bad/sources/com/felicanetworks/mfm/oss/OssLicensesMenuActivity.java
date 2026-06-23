package com.felicanetworks.mfm.oss;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.felicanetworks.mfm.oss.util.LogMgr;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class OssLicensesMenuActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private LayoutInflater mInflater;
    private List<OssLicensesInfo> mInfoList;
    private Insets mInsets = Insets.NONE;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LogMgr.log(6, "000");
        try {
            setContentView(R.layout.oss_licenses_menu_activity);
            ListView listView = (ListView) findViewById(R.id.lv_oss_menu_list);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_oss_menu);
            ViewCompat.setOnApplyWindowInsetsListener(toolbar, new OnApplyWindowInsetsListener() { // from class: com.felicanetworks.mfm.oss.OssLicensesMenuActivity.1
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                    LogMgr.log(6, "000");
                    Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                    marginLayoutParams.leftMargin = insets.left;
                    marginLayoutParams.rightMargin = insets.right;
                    marginLayoutParams.topMargin = insets.top;
                    view.setLayoutParams(marginLayoutParams);
                    LogMgr.log(6, "999");
                    return WindowInsetsCompat.CONSUMED;
                }
            });
            adjustViewToNavigationBar(listView);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(R.string.oss_licenses_toolbar_title_oss);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            listView.setFocusableInTouchMode(false);
            this.mInfoList = getDataList(this);
            listView.setAdapter((ListAdapter) new OssLicensesMenuListAdapter(this));
            listView.setOnItemClickListener(this);
        } catch (Exception e) {
            LogMgr.log(1, "800 cause = " + e.getCause());
            LogMgr.log(1, "801 class = " + e.getClass());
            LogMgr.log(1, "802 message = " + e.getMessage());
            finishAndRemoveTask();
        }
        LogMgr.log(6, "999");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        LogMgr.log(6, "000");
        try {
            OssLicensesInfo ossLicensesInfo = this.mInfoList.get(i);
            LogMgr.log(7, "001 OssLicensesInfo length  = " + ossLicensesInfo.getLength());
            LogMgr.log(7, "002 OssLicensesInfo library = " + ossLicensesInfo.getLibraryName());
            LogMgr.log(7, "003 OssLicensesInfo offset  = " + ossLicensesInfo.getOffset());
            OssLicensesInfoCache.getInstance().updateOssLicenseInfo(ossLicensesInfo);
            Intent intent = new Intent();
            intent.setAction(OssLicensesInfo.OSS_LIBRARY_VIEW_ACTION);
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setClassName("com.felicanetworks.mfm.main", OssLicensesInfo.OSS_LIBRARY_URL_VIEW_ACTIVITY_CLASS_NAME);
            LogMgr.performanceIn("UI", "OssLicensesMenuActivity", "onItemClick");
            startActivity(intent);
            LogMgr.performanceOut("UI", "OssLicensesMenuActivity", "onItemClick");
        } catch (Exception e) {
            LogMgr.log(1, "800 onItemClick is failed.");
            LogMgr.log(1, "801 class = " + e.getClass());
            LogMgr.log(1, "802 message = " + e.getMessage());
            finishAndRemoveTask();
        }
        LogMgr.log(6, "999");
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        LogMgr.performanceIn("UI", "OssLicensesMenuActivity", "onOptionsItemSelected");
        LogMgr.log(6, "000");
        if (menuItem.getItemId() == 16908332) {
            LogMgr.log(7, "001");
            finishAndRemoveTask();
        }
        LogMgr.log(6, "999");
        LogMgr.performanceOut("UI", "OssLicensesMenuActivity", "onOptionsItemSelected");
        return true;
    }

    private class OssLicensesMenuListAdapter extends BaseAdapter {
        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return null;
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return 0L;
        }

        public class OssLicensesListViewHolder {
            public TextView mView;

            public OssLicensesListViewHolder(View view) {
                LogMgr.log(6, "000");
                this.mView = (TextView) view.findViewById(R.id.library_name);
                LogMgr.log(6, "999");
            }
        }

        public OssLicensesMenuListAdapter(Context context) {
            LogMgr.log(6, "000");
            OssLicensesMenuActivity.this.mInflater = LayoutInflater.from(context);
            LogMgr.log(6, "999");
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return OssLicensesMenuActivity.this.mInfoList.size();
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            OssLicensesListViewHolder ossLicensesListViewHolder;
            LogMgr.log(6, "000");
            try {
                OssLicensesInfo ossLicensesInfo = (OssLicensesInfo) OssLicensesMenuActivity.this.mInfoList.get(i);
                LogMgr.log(7, "001 getLibrary = " + ossLicensesInfo.getLibraryName());
                LogMgr.log(7, "002 getLength = " + ossLicensesInfo.getLength());
                LogMgr.log(7, "003 getOffset = " + ossLicensesInfo.getOffset());
                if (view == null) {
                    LogMgr.log(7, "004");
                    view = OssLicensesMenuActivity.this.mInflater.inflate(R.layout.oss_licenses_list_item_oss_menu, viewGroup, false);
                    ossLicensesListViewHolder = new OssLicensesListViewHolder(view);
                    view.setTag(ossLicensesListViewHolder);
                } else {
                    LogMgr.log(7, "005");
                    ossLicensesListViewHolder = (OssLicensesListViewHolder) view.getTag();
                }
                updateListItem(ossLicensesListViewHolder, ossLicensesInfo);
            } catch (Exception unused) {
                LogMgr.log(1, "800 UpdatingView is failed.");
                OssLicensesMenuActivity.this.finishAndRemoveTask();
            }
            LogMgr.log(6, "999");
            return view;
        }

        private void updateListItem(OssLicensesListViewHolder ossLicensesListViewHolder, OssLicensesInfo ossLicensesInfo) {
            LogMgr.log(7, "000");
            ossLicensesListViewHolder.mView.setText("");
            String libraryName = ossLicensesInfo.getLibraryName();
            if (libraryName != null && !libraryName.isEmpty()) {
                LogMgr.log(7, "001");
                ossLicensesListViewHolder.mView.setText(libraryName);
            }
            LogMgr.log(7, "999");
        }
    }

    private List<OssLicensesInfo> getDataList(Context context) {
        BufferedReader bufferedReader;
        LogMgr.log(6, "000");
        ArrayList arrayList = new ArrayList();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.third_party_license_metadata)));
            try {
                LogMgr.log(7, "001");
            } finally {
            }
        } catch (Exception e) {
            LogMgr.log(1, "800 OssLicensesActivity#onCreate() is failed.");
            LogMgr.log(7, "801 class = " + e.getClass());
            LogMgr.log(7, "802 message = " + e.getMessage());
        }
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            String[] strArrSplit = line.split(":", 2);
            int i = Integer.parseInt(strArrSplit[0]);
            String[] strArrSplit2 = strArrSplit[1].split(" ", 2);
            int i2 = Integer.parseInt(strArrSplit2[0]);
            String str = strArrSplit2[1];
            arrayList.add(new OssLicensesInfo(i2, str, i));
            LogMgr.log(7, "002 library = " + str);
            LogMgr.log(1, "800 OssLicensesActivity#onCreate() is failed.");
            LogMgr.log(7, "801 class = " + e.getClass());
            LogMgr.log(7, "802 message = " + e.getMessage());
            LogMgr.log(7, "999");
            return arrayList;
        }
        bufferedReader.close();
        Collections.sort(arrayList, new Comparator<OssLicensesInfo>() { // from class: com.felicanetworks.mfm.oss.OssLicensesMenuActivity.2
            /* JADX DEBUG: Method merged with bridge method: compare(Ljava/lang/Object;Ljava/lang/Object;)I */
            @Override // java.util.Comparator
            public int compare(OssLicensesInfo ossLicensesInfo, OssLicensesInfo ossLicensesInfo2) {
                return ossLicensesInfo.getLibraryName().compareTo(ossLicensesInfo2.getLibraryName());
            }
        });
        bufferedReader.close();
        LogMgr.log(7, "999");
        return arrayList;
    }

    protected void adjustViewToNavigationBar(View view) {
        LogMgr.log(6, "000");
        if (view == null) {
            LogMgr.log(6, "998");
        } else {
            ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() { // from class: com.felicanetworks.mfm.oss.OssLicensesMenuActivity.3
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                    LogMgr.log(6, "000");
                    Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
                    if (OssLicensesMenuActivity.this.mInsets.bottom != insets.bottom) {
                        LogMgr.log(7, "001");
                        view2.setPadding(view2.getPaddingLeft(), view2.getPaddingTop(), view2.getPaddingRight(), (view2.getPaddingBottom() + insets.bottom) - OssLicensesMenuActivity.this.mInsets.bottom);
                    }
                    OssLicensesMenuActivity.this.mInsets = insets;
                    LogMgr.log(6, "999");
                    return windowInsetsCompat;
                }
            });
            LogMgr.log(6, "999");
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        LogMgr.log(6, "000");
        super.onConfigurationChanged(configuration);
        try {
            adjustViewToNavigationBar(findViewById(R.id.lv_oss_menu_list));
        } catch (Exception unused) {
        }
        LogMgr.log(6, "999");
    }
}
