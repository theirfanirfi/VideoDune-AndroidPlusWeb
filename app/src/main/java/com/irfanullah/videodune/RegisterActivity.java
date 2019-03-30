package com.irfanullah.videodune;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.irfanullah.videodune.Libraries.RetroLib;
import com.irfanullah.videodune.Models.User;
import com.irfanullah.videodune.Storage.PrefStorage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText emailTextView, passwordTextView,nameTextView;
    TextView linkToLogin;
    Button loginBtn;
    Context context;
    String email = "", password = "", name = "";

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initObjects();
        loginBtnClickEvent();
        gotoLoginPage();
    }

    private void gotoLoginPage() {
        linkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    private void initObjects(){
        context = this;
        emailTextView = findViewById(R.id.addressTextView);
        passwordTextView = findViewById(R.id.passwordTextView);
        loginBtn = findViewById(R.id.saveBtn);
        linkToLogin = findViewById(R.id.linkToLogin);
        nameTextView = findViewById(R.id.usernameTextView);
    }

    private void loginBtnClickEvent(){
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailTextView.getText().toString();
                password = passwordTextView.getText().toString();
                name = nameTextView.getText().toString();
                if(email.isEmpty() || password.isEmpty() || name.isEmpty()){
                    RetroLib.toastHere(context,"None of the field can be empty");
                }else if(password.length() < 6){
                    RetroLib.toastHere(context,"Password Length must be at least 6 characters.");
                } else {
                    makeRegisterationRequest();
                }
                Log.i(TAG, "onClick: "+email+ " : "+password);
            }
        });
    }

    private void makeRegisterationRequest(){
        RetroLib.geApiService().registerUser(name,email,password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if(user.isERROR()){
                    RetroLib.toastHere(context,user.getMESSAGE());
                }else {
                    if(user.isIS_CREATED()){
                        Gson gson = new Gson();
                        PrefStorage.getEditor(context).putString(PrefStorage.USER_PREF_DETAILS,gson.toJson(user.getUSER())).commit();
                        gotoMainActivity();
                    }else{
                        RetroLib.toastHere(context,user.getMESSAGE());
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                RetroLib.toastHere(context,t.getMessage());
            }
        });
    }

    private void gotoMainActivity(){
        Intent mainAct = new Intent(this,MainActivity.class);
        startActivity(mainAct);
    }
}
