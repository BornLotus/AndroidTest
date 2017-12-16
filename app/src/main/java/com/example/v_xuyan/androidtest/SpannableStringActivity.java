package com.example.v_xuyan.androidtest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SpannableStringActivity extends AppCompatActivity {

    private TextView tvShow;
    private SpannableStringBuilder ssb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable_string);

        tvShow = findViewById(R.id.tvShow);

        /*SpannableString spanString = new SpannableString("Hello World!");

        spanString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(SpannableStringActivity.this, "Test", Toast.LENGTH_SHORT).show();
                tvShow.setFocusable(false);
            }
        },0,3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvShow.setText(spanString);

        tvShow.setMovementMethod(LinkMovementMethod.getInstance());*/

        partClickTextView();


    }

    private void partClickTextView() {
        String str = "Hello World!The baidu is correct answer!";
        ssb = new SpannableStringBuilder(str);
        ssb.setSpan(new TextClick("hello"),0,5,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new TextClick("baidu"),str.indexOf("baidu"),str.indexOf("baidu") + "baidu".length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvShow.setMovementMethod(LinkMovementMethod.getInstance());
//        tvShow.setHighlightColor(Color.TRANSPARENT);
        tvShow.setHighlightColor(Color.BLUE);
        tvShow.setText(ssb);
    }

    class TextClick extends ClickableSpan{

        private String queryStr;
        private TextPaint tp;

        public TextClick(String queryStr) {
            this.queryStr = queryStr;
        }

        @Override
        public void onClick(View widget) {
            Toast.makeText(SpannableStringActivity.this, queryStr, Toast.LENGTH_SHORT).show();
            tvShow.setHighlightColor(Color.TRANSPARENT);
            ssb.clearSpans();
            tvShow.setHighlightColor(Color.BLUE);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(Color.BLUE);
            tp = ds;
//            ds.reset();
//            ds.clearShadowLayer();
        }
    }

}
