package com.irfanullah.videodune;

import android.content.Context;
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
                    saveSettingsRequest();
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

    private void saveSettingsRequest(){
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
}
