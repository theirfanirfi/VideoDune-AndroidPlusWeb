package com.irfanullah.videodune;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.irfanullah.videodune.Libraries.RetroLib;

public class FinalActivity extends AppCompatActivity {

    EditText emailField, fbField, hashtagField;
    Context context;
    Button finishBtn;
    String email = "", fb = "", hashtag = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        initObjects();
        finishBtnClickListener();
    }



    private void initObjects() {
        context = this;
        emailField = findViewById(R.id.emailField);
        fbField = findViewById(R.id.fbField);
        hashtagField = findViewById(R.id.personalHashTagEditText);
        finishBtn = findViewById(R.id.finishBtn);
    }

    private void finishBtnClickListener() {

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailField.getText().toString();
                fb = fbField.getText().toString();
                hashtag = hashtagField.getText().toString();
                if(email.isEmpty() || fb.isEmpty() || hashtag.isEmpty()){
                    RetroLib.toastHere(context,"Fields cannot be empty.");
                }else {
                    makeFinishRequest();
                }
            }
        });
    }

    private void makeFinishRequest() {

    }
}
