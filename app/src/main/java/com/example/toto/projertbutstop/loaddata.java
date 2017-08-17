package com.example.toto.projertbutstop;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class loaddata extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;
    private  MyManage myManage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getSupportActionBar().hide(); // ลบแทบด้านบนของแอปพลิเคชั่น
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaddata);


        //Createc SQLite or Connected
        myManage = new MyManage(loaddata.this);



        //Test Add Value
        testAddValue();

        //relode();
    }

    private void testAddValue() {
        myManage.addBusStop("1","123.0020","125.12223","NameBusstop");
        myManage.addBus("1","2","Bus_Detail");
        myManage.addBusRoute("1","เข้าเมือง","15","details","NameBus","12.22222","125.25558");
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
        //handler.postDelayed(runnable, 3000);
    }

    public void onStop() {
        super.onStop();
        //handler.removeCallbacks(runnable);
    }
}//main