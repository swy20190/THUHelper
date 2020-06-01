package com.example.hhhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SearchResult extends AppCompatActivity {
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        title = (TextView) findViewById(R.id.searchResult_title);
        Intent intent = getIntent();
        String queryString = intent.getStringExtra("queryString");
        title.setText(queryString);

    }
}
