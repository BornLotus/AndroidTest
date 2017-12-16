package com.example.v_xuyan.androidtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.v_xuyan.androidtest.fragment.ContentFragment;

public class TabLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content,new ContentFragment()).commit();


    }
}
