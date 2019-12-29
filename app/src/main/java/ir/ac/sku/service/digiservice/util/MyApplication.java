package ir.ac.sku.service.digiservice.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import ir.ac.sku.service.digiservice.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

@SuppressLint("Registered")
public class MyApplication extends Application {
    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANSansMobile(FaNum).ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
