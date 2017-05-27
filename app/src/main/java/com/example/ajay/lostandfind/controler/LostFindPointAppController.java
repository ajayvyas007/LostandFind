package com.example.ajay.lostandfind.controler;

import android.app.Application;
import com.example.ajay.lostandfind.network.ConnectivityReceiver;

/**
 * Created by ajay on 8/3/17.
 */

public class LostFindPointAppController extends Application {
    private static LostFindPointAppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
    }

    public static synchronized LostFindPointAppController getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
