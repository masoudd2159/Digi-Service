package ir.ac.sku.service.digiservice.util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.config.MyLog;

public class ManagerHelper {
    @SuppressLint("LongLogTag")
    public static boolean isNOTOnline(Context context) {
        Log.i(MyLog.DIGI_SERVICE, "Check device is Online");
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo == null || !networkInfo.isConnected();
    }

    @SuppressLint("LongLogTag")
    public static void noInternetAccess(Context context) {
        Log.i(MyLog.DIGI_SERVICE, "Open Dialog NO Internet Access");
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_disconnect);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button wifi = dialog.findViewById(R.id.disconnect_WiFi);
        Button data = dialog.findViewById(R.id.disconnect_MobileData);

        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(MyLog.DIGI_SERVICE, "on Wi-Fi Click");
                IntentHelper.openWiFiSettingScreen(context);
            }
        });

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(MyLog.DIGI_SERVICE, "on Mobile Data Click");
                IntentHelper.openDataUsageScreen(context);
            }
        });
        dialog.show();
    }

    public static void successfulOperation(Context context, String successfulMessage) {
    }

    public static void unsuccessfulOperation(Context context, String unSuccessfulMessage) {
    }

    public static String enCodeParameters(Map<String, String> params) {
        if (params != null) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                for (String key : params.keySet()) {
                    String value = params.get(key);
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append("&");
                    }
                    stringBuilder.append(key);
                    stringBuilder.append("=");
                    stringBuilder.append(URLEncoder.encode(value, "UTF-8"));
                }
                return stringBuilder.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static ColorMatrixColorFilter getBlackWhiteFilter() {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        return new ColorMatrixColorFilter(matrix);
    }
}
