package ir.ac.sku.service.digiservice.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import butterknife.BindView;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.activity.main.MainActivity;
import ir.ac.sku.service.digiservice.activity.phoneLogin.LoginActivity;
import ir.ac.sku.service.digiservice.api.appInfo.AppInfo;
import ir.ac.sku.service.digiservice.config.MyLog;
import ir.ac.sku.service.digiservice.util.ManagerHelper;
import ir.ac.sku.service.digiservice.base.MyActivity;
import ir.ac.sku.service.digiservice.util.MyHandler;
import ir.ac.sku.service.digiservice.util.UserProfilePreferenceManager;

public class SplashScreenActivity extends MyActivity {

    //* Static Method
    public static final int SPLASH_TIME = 1700;

    //* Views
    @BindView(R.id.splashScreenActivity_RelativeLayout) RelativeLayout relativeLayout;
    @BindView(R.id.splashScreenActivity_LinearLayout_Center) LinearLayout centerLayout;
    @BindView(R.id.splashScreenActivity_ImageView_SkuIcon) ImageView skuIcon;
    @BindView(R.id.splashScreenActivity_ImageView_DigiServiceIcon) ImageView digiServiceIcon;
    @BindView(R.id.splashScreenActivity_ImageView_ResponsiveIcon) ImageView responsive;
    @BindView(R.id.splashScreenActivity_TextView_ReservationSystemText) TextView reservationSystem;
    @BindView(R.id.splashScreenActivity_Button_TryAgain) Button tryAgain;

    //* Utils
    private UserProfilePreferenceManager preferenceManager;

    //* Set Content View
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash_screen;
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(MyLog.SPLASH_SCREEN_ACTIVITY, "___Splash Screen___");
        changeStatusBarColor();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.SlideInUp)
                        .duration(300)
                        .playOn(responsive);
                responsive.setVisibility(View.VISIBLE);
            }
        }, 500);

        preferenceManager = new UserProfilePreferenceManager(SplashScreenActivity.this);
        getBasicAppInformation();
        // new BackgroundTask().execute();
    }

    @SuppressLint("LongLogTag")
    private void changeStatusBarColor() {
        Log.i(MyLog.SPLASH_SCREEN_ACTIVITY, "Change Status Bar Color To Transparent");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @SuppressLint("LongLogTag")
    private void getBasicAppInformation() {
        if (!ManagerHelper.isInternetAvailable(SplashScreenActivity.this)) {
            Log.i(MyLog.SPLASH_SCREEN_ACTIVITY, "OFFLine");
            ManagerHelper.noInternetAccess(SplashScreenActivity.this);
            tryAgain.setVisibility(View.VISIBLE);
            tryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tryAgain.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tryAgain.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        }
                    }, 155);
                    Log.i(MyLog.SPLASH_SCREEN_ACTIVITY, "Clicked on Try Again");
                    if (!ManagerHelper.isInternetAvailable(SplashScreenActivity.this)) {
                        ManagerHelper.noInternetAccess(SplashScreenActivity.this);
                    } else {
                        tryAgain.setVisibility(View.GONE);
                        getBasicAppInformation();
                    }
                }
            });
        } else {
            Log.i(MyLog.SPLASH_SCREEN_ACTIVITY, "ONLine");
            AppInfo.fetchFromWeb(SplashScreenActivity.this, null, new MyHandler() {
                @Override
                public void onResponse(boolean ok, Object obj) {
                    if (ok) {
                        checkVersionCode((AppInfo) obj);
                    }
                }
            });
        }
    }

    private void checkVersionCode(AppInfo applicationInformation) {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            if (packageInfo.versionCode < applicationInformation.getData().get(0).getLatestVersion()) {
                showDialogVersionChecker();
            } else {
                new BackgroundTask().execute();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showDialogVersionChecker() {

    }

    @SuppressLint("StaticFieldLeak")
    private class BackgroundTask extends AsyncTask {

        Intent intentLoginActivity;
        Intent intentMainActivity;

        @SuppressLint("LongLogTag")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(MyLog.SPLASH_SCREEN_ACTIVITY, "Create new Intent");
            intentLoginActivity = new Intent(SplashScreenActivity.this, LoginActivity.class);
            intentMainActivity = new Intent(SplashScreenActivity.this, MainActivity.class);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                Thread.sleep(SPLASH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint("LongLogTag")
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Log.i(MyLog.SPLASH_SCREEN_ACTIVITY, "Check Preference Manager : " + preferenceManager.startApplication());
            startActivity(intentMainActivity);
            /*if (preferenceManager.startApplication()) {
                Log.i(MyLog.SPLASH_SCREEN_ACTIVITY, "Start Activity : Login Activity");
                //intentLoginActivity.putExtra("AppInfo", appInfo);
                startActivity(intentLoginActivity);
            } else {
                Log.i(MyLog.SPLASH_SCREEN_ACTIVITY, "Start Activity : Main Activity");
                startActivity(intentMainActivity);
            }*/
            finish();
        }
    }
}
