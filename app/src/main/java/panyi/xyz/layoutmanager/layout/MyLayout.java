package panyi.xyz.layoutmanager.layout;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class MyLayout extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT , RecyclerView.LayoutParams.WRAP_CONTENT);
    }


    /**
     * 在此确定子View的布局方式
     * @param recycler
     * @param state
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);

        final int itemCount = getItemCount();
        if(itemCount == 0)
            return;

        int offsetX = 0;

        for(int i = 0 ; i <itemCount ;i++){
            View itemView = recycler.getViewForPosition(i);
            addView(itemView);

            measureChildWithMargins(itemView , 0 , 0 );
            int viewWidth = getDecoratedMeasuredWidth(itemView);
            int viewHeight = getDecoratedMeasuredHeight(itemView);

            layoutDecoratedWithMargins(itemView , offsetX , 0 , offsetX+ viewWidth , viewHeight);
            offsetX += viewWidth;
        }
    }

    /**
     * 可以横向滑动
     * @return
     */
    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    /**
     * 实现滑动效果
     * @param dx
     * @param recycler
     * @param state
     * @return
     */
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        offsetChildrenHorizontal(-dx);
        return dx;
    }
}
