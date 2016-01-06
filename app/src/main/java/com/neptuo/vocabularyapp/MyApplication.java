package com.neptuo.vocabularyapp;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.neptuo.vocabularyapp.ui.MainActivity;
import com.neptuo.vocabularyapp.ui.TranslateActivity;

/**
 * Created by Windows10 on 1/6/2016.
 */
public class MyApplication extends Application {

    private Thread.UncaughtExceptionHandler defaultExceptionHandler;
    private Thread.UncaughtExceptionHandler exceptionHandler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, final Throwable e) {

            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), "Application crashed: " + e.toString(), Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }.start();

            try
            {
                Thread.sleep(4000); // Let the Toast display before app will get shutdown
            }
            catch (InterruptedException ex)
            {
                // Ignored.
            }

            defaultExceptionHandler.uncaughtException(thread, e);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);
    }
}
