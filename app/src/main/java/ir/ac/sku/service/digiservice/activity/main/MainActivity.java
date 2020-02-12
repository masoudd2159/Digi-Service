package ir.ac.sku.service.digiservice.activity.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.fragment.EventsFragment;
import ir.ac.sku.service.digiservice.fragment.HomeFragment;
import ir.ac.sku.service.digiservice.fragment.OfficesFragment;
import ir.ac.sku.service.digiservice.fragment.SearchFragment;
import ir.ac.sku.service.digiservice.util.ColoredSnackBar;
import ir.ac.sku.service.digiservice.util.ConnectivityReceiver;
import ir.ac.sku.service.digiservice.util.CustomToastExit;
import ir.ac.sku.service.digiservice.util.MyActivity;

public class MainActivity extends MyActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    //* ٰ Views
    private Toolbar toolbar;
    private CoordinatorLayout holder;
    private ImageButton drawerButton;
    private DrawerLayout drawerLayout;
    private ImageView digiServiceIcon;
    private NavigationView navigationView;
    private CoordinatorLayout fragmentHolder;
    private BottomNavigationView bottomNavigationView;

    //* Requirements
    private boolean starter = false;
    private boolean doubleBackToExitPressedOnce = false;

    //* Helper Class
    private ConnectivityReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        receiver = new ConnectivityReceiver();

        setUpNavigationDrawerView();
        setUpNavigationBottomView();

        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.bottomNavigationViewTab_Home);
        }

    }

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
    public boolean dispatchTouchEvent(MotionEvent event) {
        View v = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);
        if (v instanceof EditText) {
            View w = getCurrentFocus();
            int[] scrCoords = new int[2];
            if (w != null) {
                w.getLocationOnScreen(scrCoords);
            }
            float x = 0;
            if (w != null) {
                x = event.getRawX() + w.getLeft() - scrCoords[0];
            }
            float y = 0;
            if (w != null) {
                y = event.getRawY() + w.getTop() - scrCoords[1];
            }
            if (w != null && event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom())) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(Objects.requireNonNull(getWindow().getCurrentFocus()).getWindowToken(), 0);
                }
            }
        }
        return ret;
    }

    private void setUpNavigationDrawerView() {
        drawerButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(Gravity.END)) {
                    drawerLayout.closeDrawer(Gravity.END);
                } else {
                    drawerLayout.openDrawer(Gravity.END);
                }
            }
        });

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.drawerNavigationViewTab_Questions:
                        Snackbar.make(holder, "سوالات متداول", Snackbar.LENGTH_SHORT).show();
                        return true;
                    case R.id.drawerNavigationViewTab_Guide:
                        Snackbar.make(holder, "راهنمای سامانه", Snackbar.LENGTH_SHORT).show();
                        return true;
                    case R.id.drawerNavigationViewTab_Rules:
                        Snackbar.make(holder, "قوانین", Snackbar.LENGTH_SHORT).show();
                        return true;
                    case R.id.drawerNavigationViewTab_Settings:
                        Snackbar.make(holder, "تنظیمات", Snackbar.LENGTH_SHORT).show();
                        return true;
                    case R.id.drawerNavigationViewTab_AboutUs:
                        Snackbar.make(holder, "درباره ما", Snackbar.LENGTH_SHORT).show();
                        return true;
                    default:
                        return true;
                }
            }
        });
    }

    private void setUpNavigationBottomView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bottomNavigationViewTab_Home:
                        setUpFragment(new HomeFragment());
                        return true;
                    case R.id.bottomNavigationViewTab_Event:
                        setUpFragment(new EventsFragment());
                        return true;
                    case R.id.bottomNavigationViewTab_Office:
                        setUpFragment(new OfficesFragment());
                        return true;
                    case R.id.bottomNavigationViewTab_Search:
                        setUpFragment(new SearchFragment());
                        return true;
                    default:
                        setUpFragment(new HomeFragment());
                        return true;
                }
            }
        });
    }

    private void setUpFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activityMain_CoordinatorLayout_FragmentHolder, fragment)
                .commit();
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(Gravity.END)) {
            this.drawerLayout.closeDrawer(Gravity.END);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                finish();
                finishAffinity();
                finishAndRemoveTask();
                System.exit(0);
            } else {
                this.doubleBackToExitPressedOnce = true;
                CustomToastExit.exit(MainActivity.this, "برای خروج برنامه دو بار کلید بازگشت را فشار دهید", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }
        }
    }

    @Override
    public void onNetworkConnectionChange(boolean isConnected) {
        if (starter) {
            if (!isConnected) {
                ColoredSnackBar.error(Snackbar.make(holder, "مشکلی در اتصال به اینترنت به وجود آمده!", Snackbar.LENGTH_SHORT)).show();
            }
        } else {
            starter = true;
        }
    }

    private void init() {
        toolbar = findViewById(R.id.activityMain_ToolBar);
        drawerLayout = findViewById(R.id.activityMain_DrawerLayout);
        navigationView = findViewById(R.id.activityMain_NavigationView);
        drawerButton = findViewById(R.id.activityMain_ImageButtonDrawer);
        holder = findViewById(R.id.activityMain_CoordinatorLayout_Holder);
        digiServiceIcon = findViewById(R.id.activityMain_DigiServiceIcon);
        bottomNavigationView = findViewById(R.id.activityMain_BottomNavigationView);
        fragmentHolder = findViewById(R.id.activityMain_CoordinatorLayout_FragmentHolder);
    }
}
