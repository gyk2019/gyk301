package com.example.android.gyk3417;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class PhotoVideoActivity extends AppCompatActivity {

    Button buttonTakePhoto, buttonSaveVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_video);

        buttonTakePhoto = findViewById(R.id.buttonTakePhoto);
        buttonSaveVideo = findViewById(R.id.buttonSaveVideo);

        buttonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });
        buttonSaveVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveVideo();
            }
        });
    }

    private void takePhoto() {
        Intent intentTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentTakePhoto, 102);
    }

    private void saveVideo() {
        Intent intentSaveVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intentSaveVideo, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) return;

        switch (requestCode) {
            case 101:
                VideoView videoView = findViewById(R.id.videoView);
                videoView.setVideoURI(intent.getData());
                videoView.setMediaController(new MediaController(this));
                videoView.requestFocus();
                videoView.start();
                break;
            case 102:
                ImageView imageView = findViewById(R.id.imageView);
                imageView.setImageBitmap((Bitmap) intent.getExtras().get("data"));
        }
    }
}