package com.example.toto.projertbutstop;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.net.ConnectivityManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class loaddata extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;
    private  MyManage myManage;
    private boolean databaseBoolean = true; // มี DATA
    private boolean internetBoolean = false; // internet NOT OK
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getSupportActionBar().hide(); // ลบแทบด้านบนของแอปพลิเคชั่น
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaddata);


        //Createc SQLite or Connected
        myManage = new MyManage(loaddata.this);

        //Refresh

        //Check Database
        checkDatabase();

        //Check Internet
        checkInternet();

        //Check Special
        checkSpecial();

        //Refresh SQLiteDatabase
        //RefreahSQLite();

        //Test Add Value
        //testAddValue();
        //relode();
    }

    private void RefreahSQLite() {
        if (databaseBoolean && internetBoolean){
            //Every Thing OK
            try {

                MyConstant myConstant = new MyConstant();
                String[] urlString = new String[]{myConstant.getUrlJSON_BusStop(),
                        myConstant.getUrlJSON_Bus(),myConstant.getUrlJSON_BusRoute()};
                //Log.d("17AugV1","leangh ==>" + urlString.length);
                for (int i=0;i<urlString.length;i+=1){
                    GetAllData getAllData = new GetAllData(loaddata.this);
                    getAllData.execute(urlString[i]);
                    String strJSON = getAllData.get();
                    Log.d("17AugV1","JSON ==> " + strJSON);

                    myUpdateSQLite(i,strJSON);
                }//for
            }catch (Exception e){
                e.printStackTrace();
            }

        }else {
            //95% No Internet
        }
    }

    private void myUpdateSQLite(int index , String strJSON) {
        try{
            JSONArray jsonArray =new JSONArray(strJSON);
            for (int i=0 ;i<jsonArray.length();i+=1){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                MyManage myManage = new MyManage(loaddata.this);
                switch (index){

                    case 0: //for BusStop
                        String strCOLUMN_ID_BUSSTOP = jsonObject.getString("id");
                        String strCOLUMN_Lat = jsonObject.getString("X");
                        String strCOLUMN_Lng = jsonObject.getString("Y");
                        String strCOLUMN_Namebusstop = jsonObject.getString("Namebusstop");

                        myManage.addBusStop(strCOLUMN_ID_BUSSTOP,strCOLUMN_Lat,strCOLUMN_Lng,strCOLUMN_Namebusstop);
                        break;
                    case 1: //for Bus
                        String strCOLUMN_ID_BUS = jsonObject.getString("id_bus");
                        String strCOLUMN_bus = jsonObject.getString("bus");
                        String strCOLUMN_bus_details = jsonObject.getString("bus_details");
                        myManage.addBus(strCOLUMN_ID_BUS,strCOLUMN_bus,strCOLUMN_bus_details);
                        break;
                    case 2: //for BusRoute
                        String strCOLUMN_id_busroute = jsonObject.getString("id_busroute");
                        String strCOLUMN_direction = jsonObject.getString("direction");
                        String strCOLUMN_bus1 = jsonObject.getString("bus");
                        String strCOLUMN_bus_details1 = jsonObject.getString("bus_details");
                        String strCOLUMN_Namebusstop1 = jsonObject.getString("Namebusstop");
                        String strCOLUMN_Lat1 = jsonObject.getString("X");
                        String strCOLUMN_Lng1= jsonObject.getString("Y");
                        myManage.addBusRoute(strCOLUMN_id_busroute,strCOLUMN_direction,strCOLUMN_bus1,strCOLUMN_bus_details1,strCOLUMN_Namebusstop1
                        ,strCOLUMN_Lat1,strCOLUMN_Lng1);

                        break;
                }//switch

            }



        }catch (Exception e){
            Log.d("17AugV1", "e ==> " + e.toString());

        }


    }

    private void checkSpecial() {
        if (databaseBoolean == true){
            //Have Data
            RefreahSQLite();
        }else if (!internetBoolean){
            //No Data and Not Internet
            Toast.makeText(loaddata.this,"Connot Work beacuse no Data And Internet", Toast.LENGTH_LONG).show();
        }
        else{
            RefreahSQLite();
        }
    }
    private void checkInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) loaddata.this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if ((networkInfo != null)&&(networkInfo.isConnected())){
            internetBoolean = true;
        }
    }
    private void checkDatabase() {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM busstopTABLE",null);
        cursor.moveToFirst();
        if (cursor.getCount()==0){
            databaseBoolean =false;

        }
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