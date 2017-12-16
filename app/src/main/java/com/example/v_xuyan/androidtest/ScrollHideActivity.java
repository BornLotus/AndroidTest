package com.example.v_xuyan.androidtest;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ScrollHideActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "ScrollHideActivity";

    private TextView mainText;
    private TextView mainSubtitle;
    private ImageView imgShow;
    private ScrollView mainScroller;
    private View mainFrame;
    private Button todayLabel;
    private RecyclerView mainList;
    private MainAdapter mAdapter;
    private List<String> mData;
    private Point windowSize;
    private int todayHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scroll_hide);

        setUpUIComponents();

        //getStatusBarHeight();

    }

    private void setUpUIComponents() {
        mainText = findViewById(R.id.main_text);
        mainSubtitle = findViewById(R.id.main_subtitle);
        imgShow = findViewById(R.id.img_show);
        mainScroller = findViewById(R.id.main_scroller);
        mainFrame = findViewById(R.id.main_frame);
        todayLabel = findViewById(R.id.today_label);
        mainList = findViewById(R.id.main_content_list);

        todayLabel.setOnClickListener(this);

        setTodayLabelPosition();

        setRecyclerView();

    }

    private void setRecyclerView() {
        mData = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            mData.add("有生丶之莲    " + i);
        }
        mainList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MainAdapter();
        mainList.setAdapter(mAdapter);

        mainScroller.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                hideOrShowUI();
            }
        });

    }

    private void hideOrShowUI() {


        int imgY = windowSize.y - imgShow.getBottom() - todayHeight;
        int mainY = windowSize.y - mainText.getBottom() -  todayHeight;
        int subY = windowSize.y - mainSubtitle.getBottom() - todayHeight;

        /*Log.i(TAG, "hideOrShowUI: getY = " + mainScroller.getScrollY());
        Log.i(TAG, "hideOrShowUI: screenHeight = " + windowSize.y);
        Log.i(TAG, "hideOrShowUI: imgY = " + imgY);*/

        if (mainScroller.getScrollY() > imgY) {
            imgShow.setVisibility(View.GONE);
            mainText.setVisibility(View.GONE);
            mainSubtitle.setVisibility(View.GONE);
            todayLabel.setVisibility(View.GONE);
        } else if (mainScroller.getScrollY() > mainY) {
            imgShow.setVisibility(View.VISIBLE);
            mainText.setVisibility(View.GONE);
            mainSubtitle.setVisibility(View.GONE);
            todayLabel.setVisibility(View.VISIBLE);
        } else if (mainScroller.getScrollY() > subY) {
            imgShow.setVisibility(View.VISIBLE);
            mainText.setVisibility(View.VISIBLE);
            mainSubtitle.setVisibility(View.GONE);
            todayLabel.setVisibility(View.VISIBLE);
        } else {
            imgShow.setVisibility(View.VISIBLE);
            mainText.setVisibility(View.VISIBLE);
            mainSubtitle.setVisibility(View.VISIBLE);
            todayLabel.setVisibility(View.VISIBLE);
        }
    }

    private int getContainerHeight(View view) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return params.height + params.bottomMargin + params.topMargin;
    }

    private void setTodayLabelPosition() {
        ViewGroup.LayoutParams params = mainFrame.getLayoutParams();
        windowSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(windowSize);

        todayLabel.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        todayHeight = todayLabel.getMeasuredHeight();

        params.height = windowSize.y - todayHeight + 1;
        mainFrame.setLayoutParams(params);

        //mainScroller.fullScroll(View.FOCUS_UP);

    }

    private void scrollToBottom() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mainScroller.fullScroll(View.FOCUS_UP);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.today_label:
                scrollToBottom();
                break;
        }
    }

    public void getStatusBarHeight() {
        Rect contentSize = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(contentSize);
        Log.i(TAG, "getStatusBarHeight: statusBarHeight = " + contentSize.top);
    }

    class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

        @Override
        public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ScrollHideActivity.this).inflate(R.layout.main_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MainAdapter.ViewHolder holder, int position) {
            holder.mainTitle.setText(mData.get(position));
            holder.subTitle.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView mainTitle;
            private TextView subTitle;

            ViewHolder(View itemView) {
                super(itemView);
                mainTitle = itemView.findViewById(R.id.list_item_main_title);
                subTitle = itemView.findViewById(R.id.list_item_sub_title);
            }
        }

    }


}
