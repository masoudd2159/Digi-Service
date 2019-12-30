package ir.ac.sku.service.digiservice.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import ir.ac.sku.service.digiservice.config.MyLog;

public class ManagerHelper {
    @SuppressLint("LongLogTag")
    public static boolean isNOTOnline(Context context) {
        Log.i(MyLog.DIGI_SERVICE, "Check device is Online");
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo == null || !networkInfo.isConnected();
    }

    public static void noInternetAccess(Context context) {
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
