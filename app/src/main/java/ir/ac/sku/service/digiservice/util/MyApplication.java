package ir.ac.sku.service.digiservice.util;

import android.annotation.SuppressLint;
import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import ir.ac.sku.service.digiservice.R;

@SuppressLint("Registered")
public class MyApplication extends Application {
    private static RequestQueue requestQueue;
    private static MyApplication appInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        appInstance = this;

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/IRANSansMobile(FaNum).ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public static synchronized RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(MyApplication.appInstance);
        return requestQueue;
    }
}
