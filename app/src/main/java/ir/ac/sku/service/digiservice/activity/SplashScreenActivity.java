package ir.ac.sku.service.digiservice.activity;

import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import butterknife.BindView;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.activity.main.MainActivity;
import ir.ac.sku.service.digiservice.activity.phonelogin.LoginActivity;
import ir.ac.sku.service.digiservice.api.appInfo.AppInfo;
import ir.ac.sku.service.digiservice.base.BaseActivity;
import ir.ac.sku.service.digiservice.config.MyLog;
import ir.ac.sku.service.digiservice.util.ManagerHelper;

@SuppressLint("NonConstantResourceId")
public class SplashScreenActivity extends BaseActivity {

    //* Static Method
    public static final int SPLASH_TIME = 1700;

    //* Views
    @BindView(R.id.splashScreenActivity_Button_TryAgain) Button tryAgain;
    @BindView(R.id.splashScreenActivity_ImageView_ResponsiveIcon) ImageView responsive;

    //* Set Content View
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash_screen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(MyLog.DIGI_SERVICE + TAG, "___Splash Screen___");
        changeStatusBarColor();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            YoYo.with(Techniques.SlideInUp)
                    .duration(300)
                    .playOn(responsive);
            responsive.setVisibility(View.VISIBLE);
        }, 500);

        getDataFromServer();
    }

    private void getDataFromServer() {
        if (ManagerHelper.isInternetAvailable(this)) {
            AppInfo.fetchFromWeb(this, null, (ok, obj) -> {
                if (ok) {
                    checkVersionCode((AppInfo) obj);
                }
            });
        } else {
            Log.i(MyLog.DIGI_SERVICE + TAG, "Device Offline");
            ManagerHelper.noInternetAccess(this);
            tryAgain.setVisibility(View.VISIBLE);
            tryAgain.setOnClickListener(v -> {
                tryAgain.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                new Handler(Looper.getMainLooper()).postDelayed(() -> tryAgain.setBackgroundColor(getResources().getColor(R.color.colorPrimary)), 155);
                if (ManagerHelper.checkInternetServices(this)) {
                    Log.i(MyLog.DIGI_SERVICE + TAG, "Device Online");
                    tryAgain.setVisibility(View.INVISIBLE);
                    getDataFromServer();
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
                startHomeActivity();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showDialogVersionChecker() {

    }

    private void startHomeActivity() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (!isFinishing()) {
                if (preferencesUtils.isStartSigningKey()) {
                    startActivity(null, LoginActivity.class);
                } else {
                    startActivity(null, MainActivity.class);
                }
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_TIME);
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.exit(0);
    }
}
