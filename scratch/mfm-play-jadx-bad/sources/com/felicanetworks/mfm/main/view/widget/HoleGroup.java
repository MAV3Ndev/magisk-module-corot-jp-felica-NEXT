package com.felicanetworks.mfm.main.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class HoleGroup extends ConstraintLayout {
    private Paint paint;
    private List<HoleView> views;

    public HoleGroup(Context context) {
        super(context);
        this.views = new ArrayList();
        this.paint = new Paint();
        init(null);
    }

    public HoleGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.views = new ArrayList();
        this.paint = new Paint();
        init(attrs);
    }

    public HoleGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.views = new ArrayList();
        this.paint = new Paint();
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        this.paint.setColor(0);
        this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        setLayerType(2, null);
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        if (child instanceof HoleView) {
            this.views.add((HoleView) child);
        }
    }

    private List<HoleView> findHoleViews(View view) {
        ArrayList arrayList = new ArrayList();
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                arrayList.addAll(findHoleViews(viewGroup.getChildAt(i)));
            }
        } else if (view instanceof HoleView) {
            arrayList.add((HoleView) view);
        }
        return arrayList;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Iterator<HoleView> it = this.views.iterator();
        while (it.hasNext()) {
            canvas.drawPath(it.next().getPath(), this.paint);
        }
    }
}
