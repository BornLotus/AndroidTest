package com.example.v_xuyan.androidtest.view;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by v-xuyan on 2017/12/14.
 */

public class WrapWidthTabLayout extends TabLayout {

    public WrapWidthTabLayout(Context context) {
        super(context);
    }

    public WrapWidthTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapWidthTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int[] widths;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        try {


            if (getTabCount() == 0) return;
            //get the width of every tab
            int widthAll = 0;
            ViewGroup tabLayout = (ViewGroup) getChildAt(0);
            if (tabLayout != null) {
                widths = new int[tabLayout.getChildCount()];
                for (int i = 0; i < tabLayout.getChildCount(); i++) {
                    View childAt = tabLayout.getChildAt(i);
                    int width =childAt.getMeasuredWidth();
                    widthAll += width;
                    widths[i] = width;
                }

                setTabMode(widthAll > getMeasuredWidth() ? MODE_SCROLLABLE : MODE_FIXED);
                //MODE_FIXED , resize the width
                if (getTabMode() == MODE_FIXED) {
                    for (int i = 0; i < tabLayout.getChildCount(); i++) {
                        ViewGroup.LayoutParams params = tabLayout.getChildAt(i).getLayoutParams();
                        params.width = widths[i];
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
