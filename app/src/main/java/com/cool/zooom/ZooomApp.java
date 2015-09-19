package com.cool.zooom;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Oghenefego on 9/19/2015.
 */
public class ZooomApp extends Application {

    public RequestQueue requestQueue;

    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        return requestQueue;
    }
}
