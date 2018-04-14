package com.example.tomas.quiz.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by tomas on 14.04.2018.
 *
 * Internet connection service.
 */
public class NetworkConnection {

    /**
     * Checks if internet connection is established.
     * @param context
     * @return
     */
    public static boolean isConnected(Context context){

        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
