package com.example.app2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    private EditText mName;
    private EditText mAddress;
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        DBHelper dbHelper = new DBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        Button saveButton = findViewById(R.id.saveButton);
        Button cancelButton = findViewById(R.id.cancelButton);
        mName = findViewById(R.id.NameEditText);
        mAddress = findViewById(R.id.AddressEditText);

        final Intent intent = getIntent();
        if (intent.getStringExtra("newOrEdit").equals("edit")) {
            final long id = intent.getLongExtra("itemId", 0);
            String name = intent.getStringExtra("itemName");
            String address = intent.getStringExtra("itemAddress");
            mName.setText(name);
            mAddress.setText(address);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mName.getText().toString().trim().length() == 0 || mAddress.getText().toString().trim().length() == 0) {
                        return;
                    }
                    String pole1 = mName.getText().toString();
                    String pole2 = mAddress.getText().toString();
                    ContentValues cv = new ContentValues();
                    cv.put(DBHelper.POLE1, pole1);
                    cv.put(DBHelper.POLE2, pole2);
                    mDatabase.update(DBHelper.NAZWA_TABELI, cv, DBHelper.ID + "=" + id, null);
                    finish();
                }
            });
        }
        if (intent.getStringExtra("newOrEdit").equals("new")) {
            mAddress.setText("http://");
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mName.getText().toString().trim().length() == 0) {
                        return;
                    }
                    String pole1 = mName.getText().toString();
                    String pole2 = mAddress.getText().toString();
                    if (pole2.startsWith("http://")) {
                        ContentValues cv = new ContentValues();
                        cv.put(DBHelper.POLE1, pole1);
                        cv.put(DBHelper.POLE2, pole2);
                        mDatabase.insert(DBHelper.NAZWA_TABELI, null, cv);
                        finish();
                    } else {
                        Toast t = Toast.makeText(EditActivity.this, "Adres musi zaczynać się z http://", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.FILL_HORIZONTAL, 0, 0);
                        t.show();
                    }
                }
            });
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
