package com.example.tomas.quiz.app;

import android.app.Application;

import com.example.tomas.quiz.di.components.DaggerWebServiceComponent;
import com.example.tomas.quiz.di.components.WebServiceComponent;

/**
 * Created by tomas on 11.04.2018.
 *
 * Main application class.
 */

public class MainApp extends Application {

    private WebServiceComponent mWebServiceComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mWebServiceComponent = DaggerWebServiceComponent.builder().build();
    }

    /**
     * Returns web service component.
     * @return
     */
    public WebServiceComponent getWebServiceComponent(){
        return mWebServiceComponent;
    }
}
