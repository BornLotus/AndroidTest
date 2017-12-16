package com.example.v_xuyan.androidtest;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ClipboardManager clipManager;
    private TextView tvShow;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        //getWindow().setBackgroundDrawable(null);//将背景颜色变无

        /*ActionBar bar = getSupportActionBar();
        if (bar != null){
            bar.setElevation(0);
        }*/


        //getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //testClipManager();

        clipManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        tvShow = findViewById(R.id.tvShow);

        findViewById(R.id.btnSpan).setOnClickListener(this);
        findViewById(R.id.btnScroll).setOnClickListener(this);
        findViewById(R.id.btnTabLayout).setOnClickListener(this);


    }


    @Override
    protected void onStart() {
        super.onStart();
        getClipData();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        testClipManager();
//        getClipData();
    }

    //获取剪切板内容
    private void getClipData() {
        if (!clipManager.hasPrimaryClip()) return;
        ClipData clip = clipManager.getPrimaryClip();
        ClipData.Item item = clip.getItemAt(0);
        final String data = item.getText().toString();
        Log.i(TAG, "onPrimaryClipChanged: data = " + data);
        tvShow.post(new Runnable() {
            @Override
            public void run() {
                if (data.matches("[a-zA-Z]+"))
                    tvShow.setText(data);
            }
        });
        ClipDescription description = new ClipDescription("",new String[]{"text/text"});
        ClipData.Item  mItem = new ClipData.Item("Test");
        ClipData clipData =  new ClipData(description,mItem);
        clipManager.setPrimaryClip(clipData);

    }

    //监听剪切板内容发生变化
    private void testClipManager() {
        clipManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                ClipData clipData = clipManager.getPrimaryClip();
                ClipData.Item item = clipData.getItemAt(0);
                final String data = item.getText().toString();
                Log.i(TAG, "onPrimaryClipChanged: data = " + data);
                tvShow.post(new Runnable() {
                    @Override
                    public void run() {
                        tvShow.setText(data);
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnSpan:
                startActivity(new Intent(this, SpannableStringActivity.class));
                break;
            case R.id.btnScroll:
                startActivity(new Intent(this,ScrollHideActivity.class));
                break;
            case R.id.btnTabLayout:
                startActivity(new Intent(this,TabLayoutActivity.class));
                break;
        }
    }

}
