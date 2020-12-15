package ir.ac.sku.service.digiservice.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.config.MyLog;
import ir.ac.sku.service.digiservice.util.ColoredSnackBar;
import ir.ac.sku.service.digiservice.util.ConnectivityReceiver;
import ir.ac.sku.service.digiservice.util.SharedPreferencesUtils;


@SuppressLint("NonConstantResourceId")
public abstract class BaseActivity
        extends AppCompatActivity
        implements ConnectivityReceiver.ConnectivityReceiverListener {

    @Nullable @BindView(R.id.layout_content) View snackBarLayout;
    private SharedPreferencesUtils preferencesUtils;
    private ConnectivityReceiver receiver;
    private boolean starter = false;

    public String getTagLog() {
        return MyLog.DIGI_SERVICE + getClass().getSimpleName();
    }

    public SharedPreferencesUtils getPreferencesUtils() {
        return preferencesUtils;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        ButterKnife.bind(this);

        receiver = new ConnectivityReceiver();
        preferencesUtils = new SharedPreferencesUtils(this);
    }

    protected abstract int getLayoutResource();

    @Override
    protected void onResume() {
        super.onResume();
        receiver.setListener(this);
        registerReceiver(receiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        receiver.setListener(null);
        unregisterReceiver(receiver);
    }

    @Override
    public void onNetworkConnectionChange(boolean isConnected) {
        if (starter && !isConnected) {
            Log.i(MyLog.DIGI_SERVICE + getTagLog(), "Network NOT Connected");
            if (snackBarLayout != null)
                ColoredSnackBar.error(Snackbar.make(snackBarLayout, getResources().getString(R.string.connection_fail), BaseTransientBottomBar.LENGTH_SHORT)).show();
            else
                Toast.makeText(this, getResources().getString(R.string.connection_fail), Toast.LENGTH_SHORT).show();
        } else starter = true;
    }

    public void changeStatusBarColor() {
        Log.i(MyLog.DIGI_SERVICE + getTagLog(), "Change Status Bar");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void startActivity(Bundle bundle, Class<?> cls) {
        Intent intent = new Intent(this, cls);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
