package com.yts_movies.ytsmovies.ui.widgets;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.yts_movies.ytsmovies.R;

public class SwipeRefreshView extends SwipeRefreshLayout {

    // vars
    private ViewGroup container;

    public SwipeRefreshView(@NonNull Context context) {
        super(context);
    } // constructor with context

    public SwipeRefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setColorSchemeResources(R.color.colorAccent);
        setEnabled(false);
    } // constructor with attr


    @Override
    public boolean canChildScrollUp() {
        ViewGroup container = getContainer();
        if (container == null)
            return false;

        View view = container.getChildAt(0);
        if (view.getVisibility() != View.VISIBLE)
            view = container.getChildAt(1);
            return view.canScrollVertically(-1);
    } // scrolling functionality

    private ViewGroup getContainer()
    {
        if (container != null)
            return container;
            for(int i = 0 ; i < getChildCount() ; i++)
            {
                if (getChildAt(i) instanceof ViewGroup)
                {
                    container = (ViewGroup) getChildAt(i);
                    break;
                }
            }
            if (container == null)
                throw new RuntimeException("Container view not found !!");
            return container;
    } // get container function
}
