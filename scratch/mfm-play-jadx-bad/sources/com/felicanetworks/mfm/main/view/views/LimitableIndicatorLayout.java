package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.felicanetworks.mfm.main.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class LimitableIndicatorLayout extends View {
    private static final int DEFAULT_VISIBLE_DOTS_COUNT = 5;
    private static final int MIN_VISIBLE_DOT_COUNT = 5;
    private int activeDotSize;
    private Paint activePaint;
    private int currentPage;
    private int dotMargin;
    private List<Dot> dotsList;
    private int inactiveDotSize;
    private Paint inactivePaint;
    private int noOfPages;
    private int posY;
    private int previousPage;
    private int startPosX;
    private int visibleDotCounts;

    public LimitableIndicatorLayout(Context context) {
        super(context);
        this.activePaint = new Paint(1);
        this.inactivePaint = new Paint(1);
        this.posY = 0;
        this.previousPage = 0;
        this.currentPage = 0;
        this.dotsList = new ArrayList();
        this.noOfPages = 0;
        this.visibleDotCounts = 5;
        setup(context, null);
    }

    public LimitableIndicatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.activePaint = new Paint(1);
        this.inactivePaint = new Paint(1);
        this.posY = 0;
        this.previousPage = 0;
        this.currentPage = 0;
        this.dotsList = new ArrayList();
        this.noOfPages = 0;
        this.visibleDotCounts = 5;
        setup(context, attrs);
    }

    public LimitableIndicatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.activePaint = new Paint(1);
        this.inactivePaint = new Paint(1);
        this.posY = 0;
        this.previousPage = 0;
        this.currentPage = 0;
        this.dotsList = new ArrayList();
        this.noOfPages = 0;
        this.visibleDotCounts = 5;
        setup(context, attrs);
    }

    public LimitableIndicatorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.activePaint = new Paint(1);
        this.inactivePaint = new Paint(1);
        this.posY = 0;
        this.previousPage = 0;
        this.currentPage = 0;
        this.dotsList = new ArrayList();
        this.noOfPages = 0;
        this.visibleDotCounts = 5;
        setup(context, attrs);
    }

    private void setup(Context context, AttributeSet attributeSet) {
        Resources resources = getResources();
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.LimitableIndicatorLayout);
            this.activePaint.setStyle(Paint.Style.FILL);
            this.activePaint.setColor(typedArrayObtainStyledAttributes.getColor(0, resources.getColor(R.color.color_3E3E3E, context.getTheme())));
            this.inactivePaint.setStyle(Paint.Style.FILL);
            this.inactivePaint.setColor(typedArrayObtainStyledAttributes.getColor(2, resources.getColor(R.color.color_BEBEBE, context.getTheme())));
            this.activeDotSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(1, resources.getDimensionPixelSize(R.dimen.dot_active_size));
            this.inactiveDotSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(3, resources.getDimensionPixelSize(R.dimen.dot_inactive_size));
            this.dotMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(4, resources.getDimensionPixelSize(R.dimen.dot_margin));
            setVisibleDotCounts(typedArrayObtainStyledAttributes.getInteger(7, 5));
            typedArrayObtainStyledAttributes.recycle();
        }
        this.posY = this.activeDotSize / 2;
        initCircles();
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = (this.activeDotSize + this.dotMargin) * (this.dotsList.size() + 1);
        int iMin = this.activeDotSize;
        int mode = View.MeasureSpec.getMode(widthMeasureSpec);
        int size2 = View.MeasureSpec.getSize(widthMeasureSpec);
        int mode2 = View.MeasureSpec.getMode(heightMeasureSpec);
        int size3 = View.MeasureSpec.getSize(heightMeasureSpec);
        if (mode == 1073741824) {
            size = size2;
        } else if (mode == Integer.MIN_VALUE) {
            size = Math.min(size, size2);
        }
        if (mode2 == 1073741824) {
            iMin = size3;
        } else if (mode2 == Integer.MIN_VALUE) {
            iMin = Math.min(iMin, size3);
        }
        setMeasuredDimension(size, iMin);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircles(canvas);
    }

    private void initCircles() {
        int iMin = Math.min(getNoOfPages(), getVisibleDotCounts());
        if (iMin < 1) {
            return;
        }
        int i = 0;
        this.currentPage = 0;
        this.previousPage = 0;
        this.dotsList = new ArrayList(iMin);
        while (i < iMin) {
            Dot dot = new Dot();
            dot.setState(i == 0 ? Dot.State.ACTIVE : Dot.State.INACTIVE);
            this.dotsList.add(dot);
            i++;
        }
        invalidate();
    }

    private void drawCircles(Canvas canvas) {
        int inactiveDotRadius;
        int inactiveDotStartX;
        int startPosX = getStartPosX();
        for (int i = 0; i < this.dotsList.size(); i++) {
            Dot dot = this.dotsList.get(i);
            Paint paint = this.inactivePaint;
            int iOrdinal = dot.getState().ordinal();
            if (iOrdinal == 0) {
                inactiveDotRadius = getInactiveDotRadius();
                inactiveDotStartX = getInactiveDotStartX();
            } else if (iOrdinal == 1) {
                paint = this.activePaint;
                inactiveDotRadius = getActiveDotRadius();
                inactiveDotStartX = getActiveDotStartX();
            } else {
                startPosX = 0;
                inactiveDotRadius = 0;
                canvas.drawCircle(startPosX, this.posY, inactiveDotRadius, paint);
            }
            startPosX += inactiveDotStartX;
            canvas.drawCircle(startPosX, this.posY, inactiveDotRadius, paint);
        }
    }

    public void setNoOfPages(int noOfPages) {
        setVisibility(noOfPages <= 1 ? 4 : 0);
        this.noOfPages = noOfPages;
        recreate();
    }

    public int getNoOfPages() {
        return this.noOfPages;
    }

    public void setVisibleDotCounts(int visibleDotCounts) {
        if (visibleDotCounts < 5) {
            throw new RuntimeException("Visible Dot count cannot be smaller than 5");
        }
        this.visibleDotCounts = visibleDotCounts;
        recreate();
    }

    private void recreate() {
        initCircles();
        requestLayout();
        invalidate();
    }

    public int getVisibleDotCounts() {
        return this.visibleDotCounts;
    }

    public int getStartPosX() {
        return this.startPosX;
    }

    public int getActiveDotStartX() {
        return this.activeDotSize + this.dotMargin;
    }

    private int getInactiveDotStartX() {
        return this.inactiveDotSize + this.dotMargin;
    }

    private int getActiveDotRadius() {
        return this.activeDotSize / 2;
    }

    private int getInactiveDotRadius() {
        return this.inactiveDotSize / 2;
    }

    public void onPageChange(int page) {
        this.currentPage = page;
        if (page == this.previousPage || page < 0 || page > getNoOfPages() - 1) {
            return;
        }
        updateDots();
        this.previousPage = this.currentPage;
    }

    private void updateDots() {
        if (this.noOfPages <= this.visibleDotCounts) {
            setupNormalDots();
            return;
        }
        for (int i = 0; this.dotsList.size() > i; i++) {
            if (this.dotsList.get(i).getState().equals(Dot.State.ACTIVE)) {
                this.dotsList.get(i).setState(Dot.State.INACTIVE);
            }
        }
        int i2 = this.currentPage;
        if (i2 == 0) {
            this.dotsList.get(0).setState(Dot.State.ACTIVE);
        } else if (i2 == 1) {
            this.dotsList.get(1).setState(Dot.State.ACTIVE);
        } else if (i2 == getNoOfPages() - 2) {
            this.dotsList.get(3).setState(Dot.State.ACTIVE);
        } else if (this.currentPage == getNoOfPages() - 1) {
            this.dotsList.get(4).setState(Dot.State.ACTIVE);
        } else {
            this.dotsList.get(2).setState(Dot.State.ACTIVE);
        }
        invalidate();
    }

    private void setupNormalDots() {
        this.dotsList.get(this.currentPage).setState(Dot.State.ACTIVE);
        this.dotsList.get(this.previousPage).setState(Dot.State.INACTIVE);
        invalidate();
    }

    private static class Dot {
        private State state;

        enum State {
            INACTIVE,
            ACTIVE
        }

        private Dot() {
        }

        public State getState() {
            return this.state;
        }

        public void setState(State state) {
            this.state = state;
        }
    }
}
