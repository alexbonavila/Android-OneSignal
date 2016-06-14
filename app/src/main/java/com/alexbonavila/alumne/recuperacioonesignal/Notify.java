package com.alexbonavila.alumne.recuperacioonesignal;

import android.app.Application;
import android.util.Log;

import com.onesignal.OneSignal;

import org.json.JSONObject;

public class Notify extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this).init();

        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.WARN);
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new NotificationOpenedHandler())
                .setAutoPromptLocation(true)
                .init();

    }

    private class NotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        @Override
        public void notificationOpened(String message, JSONObject additionalData, boolean isActive) {
            String additionalMessage = "";
            try {
                if (additionalData != null) {
                    if (additionalData.has("actionSelected"))
                        additionalMessage += "Pressed ButtonID: " + additionalData.getString("actionSelected");
                    additionalMessage = message + "\nFull additionalData:\n" + additionalData.toString();
                }
                Log.d("OneSignalExample", "message:\n" + message + "\nadditionalMessage:\n" + additionalMessage);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}