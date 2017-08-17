package com.example.toto.projertbutstop;

/**
 * Created by ByDiamon on 17/8/2560.
 */

public class MyConstant {
    private String urlJSON_BusStop = "http://jsontosqlite.esy.es/php_get_data_busstoptable.php";
    private String urlJSON_Bus = "http://jsontosqlite.esy.es/php_get_data_bustable.php";
    private String urlJSON_BusRoute = "http://jsontosqlite.esy.es/inner%20join.php";

    public String getUrlJSON_BusStop() {
        return urlJSON_BusStop;
    }

    public String getUrlJSON_Bus() {
        return urlJSON_Bus;
    }

    public String getUrlJSON_BusRoute() {
        return urlJSON_BusRoute;
    }
}//Main Class
