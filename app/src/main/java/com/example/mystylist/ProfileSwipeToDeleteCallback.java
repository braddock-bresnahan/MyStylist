package com.example.mystylist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

abstract public class ProfileSwipeToDeleteCallback extends ItemTouchHelper.Callback {
    public static final float DEFAULT_SWIPE_THRESHOLD = 0.7f;

    public Context context;
    private Paint clearPaint;
    private ColorDrawable background;
    private int backgroundColor;
    private Drawable deleteDrawable;
    private int intrinsicWidth;
    private int intrinsicHeight;

    public float swipeThreshold;

    ProfileSwipeToDeleteCallback(Context context) {
        this(context, DEFAULT_SWIPE_THRESHOLD);
    }
    protected ProfileSwipeToDeleteCallback(Context context, float swipeThreshold) {
        this.context = context;
        this.clearPaint = new Paint();
        this.clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.background = new ColorDrawable();
        this.backgroundColor = Color.parseColor("#b80f0a");
        this.deleteDrawable = ContextCompat.getDrawable(context, android.R.drawable.ic_delete);
        assert this.deleteDrawable != null;
        this.intrinsicWidth = this.deleteDrawable.getIntrinsicWidth();
        this.intrinsicHeight = this.deleteDrawable.getIntrinsicHeight();
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        ProfileAdapter.ViewHolder viewHolder1 = (ProfileAdapter.ViewHolder) viewHolder;
        if (viewHolder1.view_type == ProfileAdapter.ProfileViewHolder.VIEW_TYPE)
            return makeMovementFlags(0, ItemTouchHelper.LEFT);
        else
            return makeMovementFlags(0, 0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dx, float dy, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dx, dy, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int itemHeight = itemView.getHeight();

        boolean isCancelled = (dx == 0 && !isCurrentlyActive);
        if (isCancelled) {
            clearCanvas(c, itemView.getRight() + dx, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
            super.onChildDraw(c, recyclerView, viewHolder, dx, dy, actionState, false);
            return;
        }

        background.setColor(backgroundColor);
        background.setBounds(itemView.getRight() + (int) dx, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        background.draw(c);

        int deleteIconMargin = (itemHeight - intrinsicHeight) / 2;
        int deleteIconTop = itemView.getTop() + deleteIconMargin;
        int deleteIconLeft = itemView.getRight() - deleteIconMargin - intrinsicWidth;
        int deleteIconRight = itemView.getRight() - deleteIconMargin;
        int deleteIconBottom = deleteIconTop + intrinsicHeight;

        deleteDrawable.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
        deleteDrawable.draw(c);

        super.onChildDraw(c, recyclerView, viewHolder, dx, dy, actionState, isCurrentlyActive);
    }

    private void clearCanvas(Canvas c, float left, float top, float right, float bottom) {
        c.drawRect(left, top, right, bottom, clearPaint);
    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return swipeThreshold;
    }
}
