package com.cool.zooom;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Oghenefego on 9/19/2015.
 */
public class ZooomApp extends Application {

    private static ZooomApp zooomAppInstance;

    public ZooomApp(){
        super();
        zooomAppInstance = this;
    }

    public static Application getInstance(){
        return zooomAppInstance;
    }
}
