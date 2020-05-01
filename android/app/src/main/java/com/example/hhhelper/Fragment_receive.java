package com.example.hhhelper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Fragment_receive extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerViewAdapter mAdapter;
    private View mainView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        mainView = inflater.inflate(R.layout.fragment_receive,container,false);
        initView();
        return mainView;
    }
    private void initView(){
        mRecyclerView = mainView.findViewById(R.id.recyclerview_receive);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        ArrayList<Ticket> data = new ArrayList<>();
        for(int i=0;i<20;i++){
            data.add(new Ticket("我的接单"+i,R.mipmap.ic_launcher));
        }
        mAdapter = new RecyclerViewAdapter(data);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
