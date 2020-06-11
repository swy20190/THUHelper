package com.example.hhhelper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class Fragment_receive extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerViewAdapter mAdapter;
    private View mainView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Ticket> data;
    private SharedPreferences preferences;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        //这个preference是为了头像base64的mock
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mainView = inflater.inflate(R.layout.fragment_receive,container,false);
        initView();
        swipeRefreshLayout = (SwipeRefreshLayout) mainView.findViewById(R.id.fragment_receive_swipe);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshTickets();
            }
        });
        return mainView;
    }
    private void initView(){
        mRecyclerView = mainView.findViewById(R.id.recyclerview_receive);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        initData();
        mAdapter = new RecyclerViewAdapter(data);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
    private void refreshTickets(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initView();
                        mAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initData(){
        data = new ArrayList<>();
        String base64Mock = preferences.getString("avatarBase64","");

        for(int i=0;i<20;i++){
            //String senderImage = preferences.getString("avatarBase64","");
            Ticket ticket = new Ticket("我的接单"+(int)(Math.random()*20));
            ticket.setSenderImageBase64(base64Mock);
            data.add(ticket);
        }
    }
}
