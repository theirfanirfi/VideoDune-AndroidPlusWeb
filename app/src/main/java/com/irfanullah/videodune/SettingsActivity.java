package com.irfanullah.videodune;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.irfanullah.videodune.Libraries.RetroLib;
import com.irfanullah.videodune.Models.Settings;
import com.irfanullah.videodune.Storage.PrefStorage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {

    EditText usernameEditText, addressEditText, hashTagEditText;
    String username = "", address = "", hashtag = "";
    Context context;
    Button saveBtn;
    private static final String TAG = "SettingsActivity";
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
                    RetroLib.toastHere(context,"None of the fields can be empty.");
                }else {
                    if(PrefStorage.checkUser(context)) {
                        saveSettingsRequest();
                    }else {
                        RetroLib.toastHere(context,"You need to login.");
                    }
                }
            }
        });
    }

    private void initObjects() {
        context = this;
        usernameEditText = findViewById(R.id.emailField);
        addressEditText = findViewById(R.id.fbField);
        hashTagEditText = findViewById(R.id.personalHashTagEditText);
        saveBtn = findViewById(R.id.finishBtn);

        loadSaveSettings();
    }

    private void saveSettingsRequest(){
        //Log.i(TAG, "saveSettingsRequest: "+PrefStorage.getUserData(context).toString());
        RetroLib.geApiService().saveSettings(PrefStorage.getUser(context).getTOKEN(),username,address,hashtag).enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                Settings settings = response.body();
                if(settings.isERROR()){
                    RetroLib.toastHere(context,settings.getMESSAGE());
                }else if(settings.isSAVED()) {
                    Gson gson = new Gson();
                    PrefStorage.getEditor(context).putString(PrefStorage.USER_SETTINGS_PREF_DETAILS,gson.toJson(settings.getSETTINGS())).commit();
                    RetroLib.toastHere(context,"Settings Saved.");

                    gotoStartActivity();
                }else {
                    RetroLib.toastHere(context,settings.getMESSAGE());
                }
            }

            @Override
            public void onFailure(Call<Settings> call, Throwable t) {
                RetroLib.toastHere(context,t.getMessage());
            }
        });
    }

    private void gotoStartActivity() {
        Intent startAct = new Intent(context,WelcomeActivity.class);
        startActivity(startAct);
    }

    private void loadSaveSettings(){
        if(PrefStorage.getSharedPreference(context).contains(PrefStorage.USER_SETTINGS_PREF_DETAILS)) {
            Settings settings = PrefStorage.getSettings(context);
            if (!settings.getUSERNAME().isEmpty() && !settings.getADDRESS().isEmpty() && !settings.getHASHTAG().isEmpty()) {
                usernameEditText.setText(settings.getUSERNAME().toString());
                addressEditText.setText(settings.getADDRESS().toString());
                hashTagEditText.setText(settings.getHASHTAG().toString());
            }
        }
    }
}
