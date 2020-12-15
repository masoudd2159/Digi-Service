package ir.ac.sku.service.digiservice.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import ir.ac.sku.service.digiservice.config.MyLog;
import ir.ac.sku.service.digiservice.fragment.dialogfragment.DialogFragmentNoInternetAccess;

@SuppressLint("LongLogTag")
public class ManagerHelper {
    private ManagerHelper() {
        throw new IllegalStateException("Manager Helper");
    }

    public static boolean isInternetAvailable(Context context) {
        if (context != null) {
            Log.i(MyLog.UTILS, "Check device for Internet Available");
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return manager.getActiveNetworkInfo() != null
                    && manager.getActiveNetworkInfo().isAvailable()
                    && manager.getActiveNetworkInfo().isConnected();
        }
        return false;
    }

    public static void noInternetAccess(Context context) {
        Log.i(MyLog.UTILS + ManagerHelper.class.getSimpleName(), "Open Dialog NO Internet Access");
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        new DialogFragmentNoInternetAccess().show(fragmentManager, "DialogFragmentNoInternetAccess");
    }

    public static boolean checkInternetServices(Context context) {
        if (isInternetAvailable(context)) {
            return true;
        } else {
            noInternetAccess(context);
            return false;
        }
    }

    public static String getStyle(String fontFamily, String fontSize, String textAlign, String fontColor) {
        return "<style>" +
                ".center {" +
                "    max-width: 100%;" +
                "    display: block;" +
                "    margin-left: auto;" +
                "    margin-right: auto;" +
                "}" +
                "@font-face {" +
                "    font-family: font;" +
                "    src: url(\"file:///android_asset/" + fontFamily + "\")" +
                "}" +
                "body {" +
                "    font-family: font;" +
                "    font-size: " + fontSize + "px;" +
                "    text-align: " + textAlign + ";" +
                "    color: " + fontColor + ";" +
                "}" +
                "</style>";
    }
}
