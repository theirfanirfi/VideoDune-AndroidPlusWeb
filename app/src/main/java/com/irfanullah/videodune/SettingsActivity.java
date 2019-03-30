package com.irfanullah.videodune;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    EditText usernameEditText, addressEditText, hashTagEditText;
    String username = "", address = "", hashtag = "";
    Context context;
    Button saveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initObjects();
        saveBtnClickEvent();

    }

    private void saveBtnClickEvent() {
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameEditText.getText().toString();
                address = addressEditText.getText().toString();
                hashtag = hashTagEditText.getText().toString();

                if(username.isEmpty() || address.isEmpty() || hashtag.isEmpty()){

                }else {

                }
            }
        });
    }

    private void initObjects() {
        usernameEditText = findViewById(R.id.usernameTextView);
        addressEditText = findViewById(R.id.addressTextView);
        hashTagEditText = findViewById(R.id.hashTagEditText);
        saveBtn = findViewById(R.id.saveBtn);
    }
}
