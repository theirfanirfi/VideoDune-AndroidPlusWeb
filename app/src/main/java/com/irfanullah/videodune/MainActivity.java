package com.irfanullah.videodune;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    Uri videoURI;
    Context context;
    VideoView videoView;
    Button cancelBtn, nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initObjects();
    }

    private void initObjects() {
        context = this;
        videoURI = Uri.parse(getIntent().getExtras().getString("video"));
        videoView = findViewById(R.id.videoView);

        cancelBtn = findViewById(R.id.cancelBtn);
        nextBtn = findViewById(R.id.nextBtn);
        clickListeners();
        videoView.setVideoURI(videoURI);
        MediaController mediaController = new MediaController(context);
        mediaController.setMediaPlayer(videoView);
        mediaController.setEnabled(true);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.requestFocus();
        videoView.start();
    }

    private void clickListeners() {
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startAct = new Intent(context,WelcomeActivity.class);
                startActivity(startAct);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent finalAct = new Intent(context,FinalActivity.class);
                startActivity(finalAct);
            }
        });
    }
}
