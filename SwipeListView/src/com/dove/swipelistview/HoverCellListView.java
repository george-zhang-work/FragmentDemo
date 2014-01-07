
package com.dove.swipelistview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class HoverCellListView extends ListView {
    private static final int LINE_THICKNESS = 15;
    /**
     * Record action down pointer x in local coordinate
     */
    private int mDownX = INVALID_POSITION;

    /**
     * Record action down pointer Y in local coordinate
     */
    private int mDownY = INVALID_POSITION;

    private View mMobileView;

    private BitmapDrawable mHoverCell;
    private boolean mCellIsMobile;
    private Rect mHoverCellCurrentBounds;
    private Rect mHoverCellOriginalBounds;
    private int mActivePointerId = INVALID_POSITION;

    private int mTotalOffset;

    /**
     * Swap the visual position of two list items.
     */
    public interface Swappable {
        /**
         * Callback method to be invoked when swaps first item with the second.
         * 
         * @param firstItem Index the first item's position in the adapter.
         * @param secondItem Index the second item's position in the adapter.
         */
        public void swapItems(int firstItem, int secondItem);
    }

    public HoverCellListView(Context context) {
        super(context);
        init(context);
    }

    public HoverCellListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HoverCellListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setOnItemLongClickListener(mOnItemLongClickListener);
    }

    /**
     * dispatchDraw gets invoked when all the child views are about to be drawn.
     * By overriding this method, the hover cell (BitmapDrawable) can be drawn
     * over the listview's items whenever the listview is redrawn.
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mHoverCell != null) {
            mHoverCell.draw(canvas);
        }
    }

    /**
     * Listens for long clicks on any items in the listview. When a cell has
     * been selected, the hover cell is created and set up.
     */
    private final OnItemLongClickListener mOnItemLongClickListener = new OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            mHoverCell = getHoverCellViewDrawable(view);

            view.setVisibility(INVISIBLE);
            mCellIsMobile = true;
            mMobileView = view;

            return onListItemLongClick((ListView) parent, view, position, id);
        }
    };

    protected boolean onListItemLongClick(ListView l, View view, int position, long id) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        final int actionMasked = ev.getActionMasked();
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                onTouchDown(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(ev);
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    protected void onTouchDown(MotionEvent ev) {
        mActivePointerId = ev.getPointerId(0);
    }

    protected void onTouchMove(MotionEvent ev) {
        int pointerIndex = ev.findPointerIndex(mActivePointerId);
        if (pointerIndex == INVALID_POSITION) {
            pointerIndex = 0;
            mActivePointerId = ev.getPointerId(pointerIndex);
        }

        final int y = (int) ev.getY(pointerIndex);

        if (mCellIsMobile) {
            final int deltaY = y - mDownY;
            mHoverCellCurrentBounds.offset(0, deltaY);

            invalidate();

            handleCellSwap();
        }
    }

    /**
     * This method determines whether the hover cell has been shifted far enough
     * to invoke a cell swap. If so, then the respective cell swap candidate is
     * determined and the data set is changed. Upon posting a notification of
     * the data set change, a layout is invoked to place the cells in the right
     * place. Using a ViewTreeObserver and a corresponding OnPreDrawListener, we
     * can offset the cell being swapped to where it previously was and then
     * animate it to its new position.
     */
    private void handleCellSwap() {

        getPositionForView(null);
    }

    private View getViewForPosition(int x, int y) {
        final int pos = pointToPosition(x, y);
        if (pos != INVALID_POSITION) {
            return getChildAt(pos);
        }
        return null;
    }

    /**
     * Creates the hover cell with the appropriate bitmap and of appropriate
     * size. The hover cell's BitmapDrawable is drawn on top of the bitmap every
     * single time an invalidate call is made.
     * 
     * @param v The hover cell original view.
     * @return The hover cell view's BitmapDrawable.
     */
    private BitmapDrawable getHoverCellViewDrawable(View v) {
        mHoverCellCurrentBounds = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        mHoverCellOriginalBounds = new Rect(mHoverCellCurrentBounds);

        Bitmap bm = getBitmapWithBorder(v);
        BitmapDrawable drawable = new BitmapDrawable(getResources(), bm);
        drawable.setBounds(mHoverCellCurrentBounds);

        return drawable;
    }

    /**
     * Draws a black border over the screenshot of the view passed in.
     * 
     * @param v The view passed in.
     * @return The bitmap shows the screenshot of the view after added border.
     */
    private static Bitmap getBitmapWithBorder(View v) {
        Bitmap bm = getBitmapFromView(v);
        Canvas canvas = new Canvas(bm);
        Rect rect = new Rect(0, 0, bm.getWidth(), bm.getHeight());

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(LINE_THICKNESS);
        paint.setColor(Color.BLACK);

        canvas.drawBitmap(bm, 0, 0, null);
        canvas.drawRect(rect, paint);
        return bm;
    }

    /**
     * Make a screenshot of the view as a bitmap.
     * 
     * @param v The view passed in.
     * @return The bitmap shows a screenshot of the view passed in.
     */
    private static Bitmap getBitmapFromView(View v) {
        Bitmap bm = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        v.draw(canvas);
        return bm;
    }
}
