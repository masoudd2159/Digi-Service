package ir.ac.sku.service.digiservice.util;

import android.annotation.SuppressLint;
import android.app.Application;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.api.OkClientFactory;
import okhttp3.OkHttpClient;

@SuppressLint("Registered")
public class MyApplication extends Application {
    public static final String APPLICATION_ID = "3";
    public static final String API_KEY = "529169d4-9cc8-4b12-fb02-59d9567eaf5a";
    private static MyApplication appInstance;
    private static OkHttpClient mOkHttpClient;

    public static MyApplication getAppInstance() {
        return appInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initializeOkHttpClient();

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/IRANSansMobile(FaNum).ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    private void initializeOkHttpClient() {
        appInstance = this;
        mOkHttpClient = OkClientFactory.provideOkHttpClient(this);
    }
}
