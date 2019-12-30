package ir.ac.sku.service.digiservice.util;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import ir.ac.sku.service.digiservice.config.MyLog;

class IntentHelper {

    @SuppressLint("LongLogTag")
    static void openWiFiSettingScreen(Context context) {
        Log.i(MyLog.DIGI_SERVICE, "Open WiFi Setting Screen");
        context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }

    @SuppressLint("LongLogTag")
    static void openDataUsageScreen(Context context) {
        Log.i(MyLog.DIGI_SERVICE, "Open Data Usage Screen");
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.android.settings",
                "com.android.settings.Settings$DataUsageSummaryActivity"));
        context.startActivity(intent);
    }
}
