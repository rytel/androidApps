package com.example.kalkulatorocen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewResult;
    private EditText mEditTextName;
    private EditText mEditTextSurname;
    private EditText mEditTextNumber;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextName = findViewById(R.id.edit_text_name);
        mEditTextSurname = findViewById(R.id.edit_text_surname);
        mEditTextNumber = findViewById(R.id.edit_text_number);
        mTextViewResult = findViewById(R.id.text_view_result);
        mButton = findViewById(R.id.button_open_activity2);

        mEditTextName.addTextChangedListener(TextWatcher);
        mEditTextSurname.addTextChangedListener(TextWatcher);
        mEditTextNumber.addTextChangedListener(TextWatcher);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(mEditTextNumber.getText().toString())>15
                        || Integer.parseInt(mEditTextNumber.getText().toString())<5
                        || mEditTextNumber.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Please insert number textbox 5-15", Toast.LENGTH_SHORT).show();
                } else {
                    String name = mEditTextName.getText().toString();
                    String surname = mEditTextSurname.getText().toString();
                    int number = Integer.parseInt(mEditTextNumber.getText().toString());

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("surname", surname);
                    intent.putExtra("number", number);
                    startActivityForResult(intent, 1);
                }
            }
        });
    }


    private TextWatcher TextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String nameInput = mEditTextName.getText().toString().trim();
            String surnameInput = mEditTextSurname.getText().toString().trim();
            String numberInput = mEditTextNumber.getText().toString().trim();

            final boolean enabled = !numberInput.isEmpty() && !nameInput.isEmpty() && !surnameInput.isEmpty();
            mButton.setEnabled(enabled);
            if (enabled) mButton.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                float result = data.getFloatExtra("result", 0);
                if (result >= 3) {
                    mTextViewResult.setText("Zadałeś! Twoj wynik to: " + result);
                    mButton.setText("Brawo");
                }
                if (result < 3) {
                    mTextViewResult.setText("Nie zdałeś :( Twoj wynik to: " + result);
                    mButton.setText("Smuteczek");
                }
            }
            if (resultCode == RESULT_CANCELED) {
                mTextViewResult.setText("Nothing selected");
            }
        }
    }
}
