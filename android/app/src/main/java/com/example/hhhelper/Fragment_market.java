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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Fragment_market extends Fragment {
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
        //preference是为了头像base64的mock
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mainView = inflater.inflate(R.layout.fragment_market,container,false);
        initView();
        swipeRefreshLayout = (SwipeRefreshLayout) mainView.findViewById(R.id.fragment_market_swipe);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshTickets();
            }
        });
        return mainView;
    }
    private void initView(){
        mRecyclerView = mainView.findViewById(R.id.recyclerview_market);
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
                    Thread.sleep(2000);  //进行网络查询
                }catch (InterruptedException e){
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

        // TODO: modify this API
        ArrayList<String> ticketIDs = Ticket.getBackendTicketLatestList(20);
        for(String tid: ticketIDs){
            //String senderImage = preferences.getString("avatarBase64","");
            Ticket ticket = Ticket.getBackendTicket(tid);
            data.add(ticket);
        }
    }
}
