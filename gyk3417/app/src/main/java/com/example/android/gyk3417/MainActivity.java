package com.example.android.gyk3417;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.android.gyk3417.R.layout.activity_main);
    }

    public void onClickBtn(View v)
    {
        switch (v.getId()) {
            case R.id.fotografVideo:
                intent = new Intent(this, PhotoVideoActivity.class);
                break;
            case R.id.sesKayitIslemi:
                intent = new Intent(this, SoundActivity.class);
                break;
            case R.id.haritayaGit:
                intent = new Intent(this, MapActivity.class);
                break;
            case R.id.webSayfasinaGit:
                intent = new Intent(this, WebActivity.class);
                break;
            case R.id.smsGonder:
                intent = new Intent(this, SmsActivity.class);
                break;
            case R.id.aramaYap:
                intent = new Intent(this, CallActivity.class);
                break;
        }

        startActivity(intent);
    }
}
