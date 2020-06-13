package com.example.hhhelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchResult extends AppCompatActivity {
    private TextView title;
    private RecyclerView mRecyclerView;
    private ArrayList<Ticket> searchResults = new ArrayList<>();
    private String queryString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        title = (TextView) findViewById(R.id.searchResult_title);
        Intent intent = getIntent();
        queryString = intent.getStringExtra("queryString");
        title.setText(queryString);
        initTickets();
        mRecyclerView = (RecyclerView)findViewById(R.id.searchResult_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(searchResults);
        mRecyclerView.setAdapter(adapter);
    }
    private void initTickets(){
        for(int i=0;i<20;i++){
            searchResults.add(new Ticket(queryString+i));
        }
    }
}
