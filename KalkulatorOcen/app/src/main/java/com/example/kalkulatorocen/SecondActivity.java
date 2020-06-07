package com.example.kalkulatorocen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        setTitle("Wybierz oceny");
        final ArrayList<ExampleItem> exampleList = new ArrayList<>();


        Intent intent = getIntent();
        final int number = intent.getIntExtra("number", 0);
        for (int i = 1; i <= number; i++) {
            exampleList.add(new ExampleItem(i));

        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(exampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        Button button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float result = 0;
                for (int i = 0; i < exampleList.size(); i++)
                    result += exampleList.get(i).getGrade();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result", result / exampleList.size());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

}
