package com.example.toto.projertbutstop;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class home extends AppCompatActivity {
    // กำหนดค่าเริ่มต้นของสถานะเป็น false
    Boolean isInternetPresent = false;
    // เรียกใช้งาน  ConnectionDetector
    Checkinternet cd;
    @Bind(R.id.Onlinemode)
    Button Onlinemode;
    @Bind(R.id.Offlinemode)
    Button Offlinemode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        Toast.makeText(this, "เลือกระบบการใช้งาน ออนไลน์ กดด้านซ้ายของหน้าจอ ออฟไลน์ กดด้านขวาของหน้าจอ", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.Onlinemode, R.id.Offlinemode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Onlinemode:
            {
                    CheckGPS();
                    Log.d("toto", "gg" + CheckGPS());
                    Checkinternet();
                    Log.d("toto", "hh" + Checkinternet());
                if(CheckGPS()==true&&Checkinternet()==true){
                Intent intent = new Intent(this, input_online.class);
                startActivity(intent);}
            }
                break;
            case R.id.Offlinemode:
            {
                Intent intent = new Intent(this, input_offline.class);
                startActivity(intent);
            }
                break;
        }
    }

    private boolean Checkinternet() {
        cd = new Checkinternet(getApplicationContext());
        // get สถานะการเชื่อมต่ออินเตอร์เน็ต
        isInternetPresent = cd.isConnectingToInternet();
        // ตรวจสอบสถานะการเชื่อมต่ออินเตอร์เน็ต
        if (isInternetPresent) {
            //หากเชื่อมต่ออินเตอร์เน็ต
            isInternetPresent = true;
            Toast.makeText(this, "คุณกำลังเชื่อมต่ออินเตอร์เน็ต", Toast.LENGTH_SHORT).show();
        } else {
            // หากไม่ได้เชื่อมต่ออินเตอร์เน็ต
            isInternetPresent = false;
            Toast.makeText(this, "คุณไม่ได้เชื่อมต่ออินเตอร์เน็ต กรุณาเชื่อมต่ออินเตอร์เน็ตของท่าน", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            startActivity(intent);
        }
        Log.d("toto","inttrenet"+isInternetPresent);
        return isInternetPresent;
    }

    private boolean CheckGPS() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        boolean bolGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!bolGPS) {
            //Not Open GPS
            Toast.makeText(this, "คุณไม่ได้เชื่อมต่อจีพีเอส กรุณาเชื่อมจีพีเอสของท่าน", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        Log.d("toto","gps"+bolGPS);
        return bolGPS;
    }
}
