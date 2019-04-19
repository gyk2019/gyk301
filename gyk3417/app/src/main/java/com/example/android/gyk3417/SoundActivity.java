package com.example.android.gyk3417;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SoundActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonStartRecord, buttonStopRecord, buttonPlayRecord;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    private static final int REQUEST_AUDIO_PERMISSION_CODE = 200;
    private static final String filePath = Environment.getExternalStorageDirectory().getPath() + "/record.3gp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        buttonStartRecord = findViewById(R.id.buttonStartRecord);
        buttonStopRecord = findViewById(R.id.buttonStopRecord);
        buttonPlayRecord = findViewById(R.id.buttonPlayRecord);

        buttonStartRecord.setOnClickListener(this);
        buttonStopRecord.setOnClickListener(this);
        buttonPlayRecord.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == buttonStartRecord) {
            if(!checkPermissions()) {
                requestPermissions();
            }
            startRecording();
        } else if (view == buttonPlayRecord){
            playRecording();
        } else if (view == buttonStopRecord){
            stopRecording();
        }
    }

    private void startRecording() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(filePath);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            Toast.makeText(getApplicationContext(), "Kayıt başladı", Toast.LENGTH_SHORT).show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void stopRecording() {
        if(mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder=null;
            Toast.makeText(getApplicationContext(),"Ses kaydı durduruldu", Toast.LENGTH_SHORT).show();
        }
    }

    private void playRecording() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setVolume(1,1);

        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
            Toast.makeText(getApplicationContext(), "Ses kaydı oynatılıyor", Toast.LENGTH_SHORT).show();

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer=null;
                    Toast.makeText(getApplicationContext(), "Ses kaydı oynatılması sona erdi", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if(grantResults.length > 0) {
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] ==  PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getApplicationContext(), "Yetki var", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Yetki yok", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermissions() {
        boolean allpermissionGranted;
        int resultWriteExternalStorage = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int resultRecordAudio = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        if( resultWriteExternalStorage == PackageManager.PERMISSION_GRANTED &&
                resultRecordAudio == PackageManager.PERMISSION_GRANTED) {
            allpermissionGranted = true;
        } else {
            allpermissionGranted = false;
        }
        return allpermissionGranted;
    }

    private void requestPermissions() {
        String[] permissions = new String[] { RECORD_AUDIO, WRITE_EXTERNAL_STORAGE };
        ActivityCompat.requestPermissions(this, permissions, REQUEST_AUDIO_PERMISSION_CODE);
    }
}