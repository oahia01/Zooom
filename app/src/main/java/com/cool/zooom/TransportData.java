package com.cool.zooom;

/**
 * Created by Oghenefego on 9/19/2015.
 */
public class TransportData {
    String transport_type;
    String cost;
    String walking_time;
    String travel_time;
    int imageRes;

    public TransportData(String transport_type1,String cost1,String walking_time1, String travel_time1, int imageRes1){
        this.transport_type = transport_type1;
        this.cost = cost1;
        this.walking_time = walking_time1;
        this.travel_time = travel_time1;
        this.imageRes = imageRes1;
    }
}
