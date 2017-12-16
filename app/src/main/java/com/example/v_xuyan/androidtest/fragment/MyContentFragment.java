package com.example.v_xuyan.androidtest.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.v_xuyan.androidtest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by v-xuyan on 2017/12/14.
 */

public class MyContentFragment extends Fragment {

    private ViewGroup mContainer;
    private List<String> mList;
    private RecyclerView itemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.content_item_inner,container,false);
        mContainer = (ViewGroup) view;

        itemList = view.findViewById(R.id.item_content_list);

        initData();

        itemList.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemList.setAdapter(new MyAdapter());


        Log.e("yxl", "onCreateView: is called");

        return view;
    }

    private void initData() {
        Bundle mBundle = getArguments();
        mList = new ArrayList<>();
        if (mBundle == null)  return;
        int num = mBundle.getInt("num");
        switch (num) {
            case 1:
                for (int i = 0; i < 5; i++) {
                    mList.add("醉古夫  " + i);
                }
                break;
            case 2:
                for (int i = 0; i < 10; i++) {
                    mList.add("折柳心斋 "  + i);
                }
                break;
        }
    }

    public ViewGroup getContainer(){
        return mContainer;
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.inner_item_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            holder.item.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            private TextView item;
            ViewHolder(View itemView) {
                super(itemView);
                item = itemView.findViewById(R.id.inner_item_text);
            }
        }

    }

}
