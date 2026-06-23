package com.felicanetworks.mfm.main.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import com.felicanetworks.mfm.main.R;

/* JADX INFO: loaded from: classes3.dex */
public class HoleView extends View {
    private Path path;
    private float[] radii;

    public HoleView(Context context) {
        super(context);
        this.path = new Path();
        init(null);
    }

    public HoleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.path = new Path();
        init(attrs);
    }

    public HoleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.path = new Path();
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setVisibility(4);
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.HoleView);
        float dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
        this.radii = new float[]{dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize};
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.path.addRoundRect(left, top, right, bottom, this.radii, Path.Direction.CCW);
    }

    public Path getPath() {
        return this.path;
    }
}
