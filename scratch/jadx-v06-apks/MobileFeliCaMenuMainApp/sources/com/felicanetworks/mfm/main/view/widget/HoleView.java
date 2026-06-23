package com.felicanetworks.mfm.main.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import com.felicanetworks.mfm.main.R;

/* JADX INFO: loaded from: classes.dex */
public class HoleView extends View {
    private Path path;
    private float[] radii;

    public HoleView(Context context) {
        super(context);
        this.path = new Path();
        init(null);
    }

    public HoleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.path = new Path();
        init(attributeSet);
    }

    public HoleView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.path = new Path();
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        setVisibility(4);
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.HoleView);
        float dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
        this.radii = new float[]{dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize};
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.path.addRoundRect(i, i2, i3, i4, this.radii, Path.Direction.CCW);
    }

    public Path getPath() {
        return this.path;
    }
}
