package com.example.app2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    private ExampleAdapter mAdapter;
    //lista była robiona tylko do multi select'a
    private List<ExampleItem> mExampleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper dbHelper = new DBHelper(this);
        mExampleList = new ArrayList<>();
        mDatabase = dbHelper.getReadableDatabase();

        Button delBtn = findViewById(R.id.deleteSelected);
        Button addBtn = findViewById(R.id.addBtn);
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ExampleAdapter(this, getAllItems(), mExampleList);
        mRecyclerView.setAdapter(mAdapter);
        showIfEmptyRecyclerView();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(mRecyclerView);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                editItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(mRecyclerView);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItem();
            }
        });
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedItems();
            }
        });
    }

    private void deleteSelectedItems() {
        for (int i = 0; i < mExampleList.size(); i++) {
            if (mExampleList.get(i).isSelected()) {
                removeItem(mExampleList.get(i).getId());
            }
        }
        mAdapter.swapCursor(getAllItems());
        showIfEmptyRecyclerView();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mAdapter.swapCursor(getAllItems());
        showIfEmptyRecyclerView();
    }

    private void addNewItem() {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("newOrEdit", "new");
        startActivity(intent);
    }

    private void editItem(long id) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("newOrEdit", "edit");

        Cursor cursor = mDatabase.query(true,
                DBHelper.NAZWA_TABELI,
                new String[]{DBHelper.POLE1, DBHelper.POLE2},
                DBHelper.ID + "=" + id, null, null, null, null, null);
        cursor.moveToFirst();
        intent.putExtra("itemId", id);
        intent.putExtra("itemName", cursor.getString(cursor.getColumnIndex(DBHelper.POLE1)));
        intent.putExtra("itemAddress", cursor.getString(cursor.getColumnIndex(DBHelper.POLE2)));
        startActivity(intent);
    }

    private void removeItem(long id) {
        mDatabase.delete(DBHelper.NAZWA_TABELI, DBHelper.ID + "=" + id, null);
        mAdapter.swapCursor(getAllItems());
        showIfEmptyRecyclerView();
    }

    private Cursor getAllItems() {
        return mDatabase.query(DBHelper.NAZWA_TABELI,
                null,
                null,
                null,
                null,
                null,
                DBHelper.ID);
    }

    private void showIfEmptyRecyclerView() {
        TextView ifEmpty = findViewById(R.id.ifEmpty);
        if (mAdapter.getItemCount() <= 0) {
            ifEmpty.setVisibility(View.VISIBLE);
        } else {
            ifEmpty.setVisibility(View.GONE);
        }
    }
}
