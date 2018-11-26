package com.idea_factory.idea_factory.decorder;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Admin on 9/18/2018.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    private int span;

    public SpacesItemDecoration(int space, int span) {
        this.space = space;
        this.span = span;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        if (parent.getChildLayoutPosition(view) < span) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }
    }
}

