package com.felicanetworks.mfw.a.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.felicanetworks.mfw.a.boot.R;
import com.felicanetworks.mfw.a.boot.q;

/* JADX INFO: compiled from: CustomAlertController.java */
/* JADX INFO: loaded from: classes.dex */
public class i {
    private TextView A;
    private View B;
    private boolean C;
    private ListAdapter D;
    private Handler F;
    private ScrollView G;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final Context f194a;
    private final DialogInterface b;
    private final Window c;
    private CharSequence d;
    private CharSequence e;
    private ListView f;
    private View g;
    private int h;
    private int i;
    private int j;
    private int k;
    private Button m;
    private CharSequence n;
    private Message o;
    private Button p;
    private CharSequence q;
    private Message r;
    private Button s;
    private CharSequence t;
    private Message u;
    private ScrollView v;
    private Drawable x;
    private ImageView y;
    private TextView z;
    private boolean l = false;
    private int w = -1;
    private int E = -1;
    View.OnClickListener H = new a(this);

    public i(Context context, DialogInterface dialogInterface, Window window) {
        this.f194a = context;
        this.b = dialogInterface;
        this.c = window;
        this.F = new h(dialogInterface);
    }

    private boolean A() {
        Button button;
        int i;
        Button button2 = (Button) this.c.findViewById(R.id.button1);
        this.m = button2;
        button2.setOnClickListener(this.H);
        if (TextUtils.isEmpty(this.n)) {
            this.m.setVisibility(8);
            button = null;
            i = 0;
        } else {
            this.m.setText(this.n);
            this.m.setVisibility(0);
            button = this.m;
            i = 1;
        }
        Button button3 = (Button) this.c.findViewById(R.id.button2);
        this.p = button3;
        button3.setOnClickListener(this.H);
        if (TextUtils.isEmpty(this.q)) {
            this.p.setVisibility(8);
        } else {
            this.p.setText(this.q);
            this.p.setVisibility(0);
            if (button == null) {
                Button button4 = this.p;
            }
            i |= 2;
        }
        Button button5 = (Button) this.c.findViewById(R.id.button3);
        this.s = button5;
        button5.setOnClickListener(this.H);
        if (TextUtils.isEmpty(this.t)) {
            this.s.setVisibility(8);
        } else {
            this.s.setText(this.t);
            this.s.setVisibility(0);
            i |= 4;
        }
        if (i == 1) {
            m(this.m);
        } else if (i == 2 || i == 4) {
            m(this.s);
        }
        return i != 0;
    }

    private void B(LinearLayout linearLayout) {
        ScrollView scrollView = (ScrollView) this.c.findViewById(R.id.scrollView);
        this.v = scrollView;
        scrollView.setFocusable(false);
        ScrollView scrollView2 = (ScrollView) this.B.findViewById(R.id.sv_dlg1);
        this.G = scrollView2;
        scrollView2.setFocusable(false);
        TextView textView = (TextView) this.c.findViewById(R.id.message);
        this.A = textView;
        if (textView == null) {
            return;
        }
        CharSequence charSequence = this.e;
        if (charSequence != null) {
            textView.setText(charSequence);
            return;
        }
        textView.setVisibility(8);
        this.v.removeView(this.A);
        if (this.f == null) {
            linearLayout.setVisibility(8);
            return;
        }
        linearLayout.removeView(this.c.findViewById(R.id.scrollView));
        linearLayout.addView(this.f, new LinearLayout.LayoutParams(-1, -1));
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 1.0f));
    }

    private boolean C(LinearLayout linearLayout) {
        if (this.B != null) {
            linearLayout.addView(this.B, new LinearLayout.LayoutParams(-1, -2));
            this.c.findViewById(R.id.title_template).setVisibility(8);
            return true;
        }
        boolean z = !TextUtils.isEmpty(this.d);
        this.y = (ImageView) this.c.findViewById(R.id.icon);
        if (!z) {
            this.c.findViewById(R.id.title_template).setVisibility(8);
            this.y.setVisibility(8);
            return false;
        }
        TextView textView = (TextView) this.c.findViewById(R.id.alertTitle);
        this.z = textView;
        textView.setText(this.d);
        this.y.setImageResource(R.drawable.ic_dialog_menu_generic);
        int i = this.w;
        if (i > 0) {
            this.y.setImageResource(i);
            return true;
        }
        Drawable drawable = this.x;
        if (drawable != null) {
            this.y.setImageDrawable(drawable);
            return true;
        }
        if (i != 0) {
            return true;
        }
        this.z.setPadding(this.y.getPaddingLeft(), this.y.getPaddingTop(), this.y.getPaddingRight(), this.y.getPaddingBottom());
        this.y.setVisibility(8);
        return true;
    }

    private void D() {
        LinearLayout linearLayout = (LinearLayout) this.c.findViewById(R.id.contentPanel);
        B(linearLayout);
        boolean zA = A();
        LinearLayout linearLayout2 = (LinearLayout) this.c.findViewById(R.id.topPanel);
        View view = null;
        TypedArray typedArrayObtainStyledAttributes = this.f194a.obtainStyledAttributes(null, q.CustomAlertDialog, R.attr.alertDialogStyle, 0);
        boolean zC = C(linearLayout2);
        View viewFindViewById = this.c.findViewById(R.id.buttonPanel);
        if (!zA) {
            viewFindViewById.setVisibility(8);
        }
        if (this.g != null) {
            FrameLayout frameLayout = (FrameLayout) this.c.findViewById(R.id.customPanel);
            FrameLayout frameLayout2 = (FrameLayout) this.c.findViewById(R.id.custom);
            frameLayout2.addView(this.g, new ViewGroup.LayoutParams(-1, -1));
            if (this.l) {
                frameLayout2.setPadding(this.h, this.i, this.j, this.k);
            }
            if (this.f != null) {
                ((LinearLayout.LayoutParams) frameLayout.getLayoutParams()).weight = 0.0f;
            }
            view = frameLayout;
        } else {
            this.c.findViewById(R.id.customPanel).setVisibility(8);
        }
        if (zC && (this.e != null || this.g != null)) {
            this.c.findViewById(R.id.titleDivider).setVisibility(0);
        }
        q(linearLayout2, linearLayout, view, zA, typedArrayObtainStyledAttributes, zC, viewFindViewById);
        typedArrayObtainStyledAttributes.recycle();
    }

    static boolean l(View view) {
        if (view.onCheckIsTextEditor()) {
            return true;
        }
        if (!(view instanceof ViewGroup)) {
            return false;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        while (childCount > 0) {
            childCount--;
            if (l(viewGroup.getChildAt(childCount))) {
                return true;
            }
        }
        return false;
    }

    private void m(Button button) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
        layoutParams.gravity = 1;
        layoutParams.weight = 0.5f;
        button.setLayoutParams(layoutParams);
        this.c.findViewById(R.id.leftSpacer).setVisibility(0);
        this.c.findViewById(R.id.rightSpacer).setVisibility(0);
    }

    private void q(LinearLayout linearLayout, LinearLayout linearLayout2, View view, boolean z, TypedArray typedArray, boolean z2, View view2) {
        int i;
        ListAdapter listAdapter;
        boolean z3;
        int resourceId = typedArray.getResourceId(7, R.drawable.popup_full_dark);
        int resourceId2 = typedArray.getResourceId(9, R.drawable.popup_top_dark);
        int resourceId3 = typedArray.getResourceId(4, R.drawable.popup_center_dark);
        int resourceId4 = typedArray.getResourceId(1, R.drawable.popup_bottom_dark);
        int resourceId5 = typedArray.getResourceId(6, R.drawable.popup_full_bright);
        int resourceId6 = typedArray.getResourceId(8, R.drawable.popup_top_bright);
        int resourceId7 = typedArray.getResourceId(3, R.drawable.popup_center_bright);
        int resourceId8 = typedArray.getResourceId(0, R.drawable.popup_bottom_bright);
        int resourceId9 = typedArray.getResourceId(2, R.drawable.popup_bottom_medium);
        typedArray.getResourceId(5, R.drawable.popup_center_medium);
        View[] viewArr = new View[4];
        boolean[] zArr = new boolean[4];
        if (z2) {
            viewArr[0] = linearLayout;
            zArr[0] = false;
            i = 1;
        } else {
            i = 0;
        }
        viewArr[i] = linearLayout2.getVisibility() == 8 ? null : linearLayout2;
        zArr[i] = this.f != null;
        int i2 = i + 1;
        if (view != null) {
            viewArr[i2] = view;
            zArr[i2] = this.C;
            i2++;
        }
        if (z) {
            viewArr[i2] = view2;
            zArr[i2] = true;
        }
        View view3 = null;
        boolean z4 = false;
        boolean z5 = false;
        for (int i3 = 0; i3 < 4; i3++) {
            View view4 = viewArr[i3];
            if (view4 != null) {
                if (view3 != null) {
                    if (z5) {
                        view3.setBackgroundResource(z4 ? resourceId7 : resourceId3);
                    } else {
                        view3.setBackgroundResource(z4 ? resourceId6 : resourceId2);
                    }
                    z3 = true;
                } else {
                    z3 = z5;
                }
                z5 = z3;
                z4 = zArr[i3];
                view3 = view4;
            }
        }
        if (view3 != null) {
            if (z5) {
                if (z4) {
                    resourceId4 = z ? resourceId9 : resourceId8;
                }
                view3.setBackgroundResource(resourceId4);
            } else {
                if (z4) {
                    resourceId = resourceId5;
                }
                view3.setBackgroundResource(resourceId);
            }
        }
        ListView listView = this.f;
        if (listView == null || (listAdapter = this.D) == null) {
            return;
        }
        listView.setAdapter(listAdapter);
        int i4 = this.E;
        if (i4 > -1) {
            this.f.setItemChecked(i4, true);
            this.f.setSelection(this.E);
        }
    }

    public void n() {
        this.c.requestFeature(1);
        View view = this.g;
        if (view == null || !l(view)) {
            this.c.setFlags(131072, 131072);
        }
        this.c.setContentView(R.layout.alert_dialog);
        D();
    }

    public boolean o(int i, KeyEvent keyEvent) {
        ScrollView scrollView = this.v;
        if (scrollView != null && scrollView.executeKeyEvent(keyEvent)) {
            return true;
        }
        ScrollView scrollView2 = this.G;
        return scrollView2 != null && scrollView2.executeKeyEvent(keyEvent);
    }

    public boolean p(int i, KeyEvent keyEvent) {
        ScrollView scrollView = this.v;
        if (scrollView != null && scrollView.executeKeyEvent(keyEvent)) {
            return true;
        }
        ScrollView scrollView2 = this.G;
        return scrollView2 != null && scrollView2.executeKeyEvent(keyEvent);
    }

    public void r(int i, CharSequence charSequence, DialogInterface.OnClickListener onClickListener, Message message) {
        if (message == null && onClickListener != null) {
            message = this.F.obtainMessage(i, onClickListener);
        }
        if (i == -3) {
            this.t = charSequence;
            this.u = message;
        } else if (i == -2) {
            this.q = charSequence;
            this.r = message;
        } else {
            if (i != -1) {
                throw new IllegalArgumentException("Button does not exist");
            }
            this.n = charSequence;
            this.o = message;
        }
    }

    public void s(View view) {
        this.B = view;
    }

    public void t(int i) {
        this.w = i;
        ImageView imageView = this.y;
        if (imageView != null) {
            if (i > 0) {
                imageView.setImageResource(i);
            } else if (i == 0) {
                imageView.setVisibility(8);
            }
        }
    }

    public void u(Drawable drawable) {
        this.x = drawable;
        ImageView imageView = this.y;
        if (imageView == null || drawable == null) {
            return;
        }
        imageView.setImageDrawable(drawable);
    }

    public void v(boolean z) {
        this.C = z;
    }

    public void w(CharSequence charSequence) {
        this.e = charSequence;
        TextView textView = this.A;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    public void x(CharSequence charSequence) {
        this.d = charSequence;
        TextView textView = this.z;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    public void y(View view) {
        this.g = view;
        this.l = false;
    }

    public void z(View view, int i, int i2, int i3, int i4) {
        this.g = view;
        this.l = true;
        this.h = i;
        this.i = i2;
        this.j = i3;
        this.k = i4;
    }
}
