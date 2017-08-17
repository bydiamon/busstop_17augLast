package com.example.toto.projertbutstop;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class loaddata extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getSupportActionBar().hide(); // ลบแทบด้านบนของแอปพลิเคชั่น
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaddata);
        relode();
    }

    private void relode() {
        handler = new Handler();

        runnable = new Runnable() {
            public void run() {
                Intent intent = new Intent(loaddata.this, home.class);
                startActivity(intent);
                finish();
            }
        };
    }

    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }

    public void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }
}