package com.felicanetworks.common.cmnview;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.log.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseDialogView extends BaseView implements FunctionCodeInterface {
    private Activity _activity;
    private Dialog _dialog;
    private boolean isDestroyedActivity;
    private boolean isDialogAlive;

    public abstract void defaultButton();

    @Override // com.felicanetworks.common.cmnview.BaseView
    public void onConfigurationChanged(Configuration configuration) {
    }

    public abstract Dialog onCreateDialogView(Activity activity);

    public void onStartDialog() {
    }

    public BaseDialogView(Activity activity) {
        super(activity);
        this._dialog = null;
        this._activity = null;
        this.isDialogAlive = true;
        this.isDestroyedActivity = false;
        this._activity = activity;
        CreateDialogHandler createDialogHandler = new CreateDialogHandler();
        createDialogHandler.sendMessageAtFrontOfQueue(createDialogHandler.obtainMessage());
    }

    private class CreateDialogHandler extends Handler {
        public CreateDialogHandler() {
            super(Looper.getMainLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                super.handleMessage(message);
                if (BaseDialogView.this.isDestroyedActivity) {
                    return;
                }
                BaseDialogView.this._dialog = BaseDialogView.this.onCreateDialogView(BaseDialogView.this._activity);
                BaseDialogView.this._dialog.setCancelable(false);
                BaseDialogView.this._dialog.setOnDismissListener(new DismissListenerImpl());
                BaseDialogView.this._dialog.show();
                BaseDialogView.this.onStartDialog();
            } catch (Exception e) {
                if (AppData.getInstance().logMgr != null) {
                    AppData.getInstance().logMgr.out(LogMgr.CatExp.ERR, BaseDialogView.this, e);
                }
                BaseDialogView.this.transferState(1001);
            }
        }
    }

    @Override // com.felicanetworks.common.cmnview.BaseView
    public void transferState(int i, TransferStateData transferStateData) {
        super.transferState(i, transferStateData);
    }

    @Override // com.felicanetworks.common.cmnview.BaseView
    public void transferState(int i) {
        transferState(i, null);
    }

    @Override // com.felicanetworks.common.cmnview.BaseView
    public void transferFatalError(String str) {
        super.transferFatalError(str);
    }

    public void dismiss() {
        Dialog dialog = this._dialog;
        if (dialog != null) {
            dialog.dismiss();
            this._dialog = null;
            this.isDialogAlive = false;
        }
    }

    @Override // com.felicanetworks.common.cmnview.BaseView
    public void onActivityDestroy() {
        this.isDestroyedActivity = true;
        Dialog dialog = this._dialog;
        if (dialog == null || !dialog.isShowing()) {
            return;
        }
        dismiss();
    }

    private class DismissListenerImpl implements DialogInterface.OnDismissListener {
        private DismissListenerImpl() {
        }

        @Override // android.content.DialogInterface.OnDismissListener
        public void onDismiss(DialogInterface dialogInterface) {
            if (BaseDialogView.this.isDialogAlive) {
                BaseDialogView.this.defaultButton();
            }
        }
    }

    public void refreshView() {
        Dialog dialog = this._dialog;
        if (dialog != null) {
            dialog.getWindow().getDecorView().requestLayout();
        }
    }
}
