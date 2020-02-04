package ir.ac.sku.service.digiservice.activity.main;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
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
    private ImageButton drawerButton;
    private DrawerLayout drawerLayout;
    private ImageView digiServiceIcon;
    private NavigationView navigationView;
    private CoordinatorLayout fragmentHolder;
    private BottomNavigationView bottomNavigationView;

    //* Fragments
    private HomeFragment homeFragment;
    private EventsFragment eventsFragment;
    private SearchFragment searchFragment;
    private OfficesFragment officesFragment;

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

        homeFragment = new HomeFragment();
        eventsFragment = new EventsFragment();
        searchFragment = new SearchFragment();
        officesFragment = new OfficesFragment();

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
                        Snackbar.make(fragmentHolder, "سوالات متداول", Snackbar.LENGTH_SHORT).show();
                        return true;
                    case R.id.drawerNavigationViewTab_Guide:
                        Snackbar.make(fragmentHolder, "راهنمای سامانه", Snackbar.LENGTH_SHORT).show();
                        return true;
                    case R.id.drawerNavigationViewTab_Rules:
                        Snackbar.make(fragmentHolder, "قوانین", Snackbar.LENGTH_SHORT).show();
                        return true;
                    case R.id.drawerNavigationViewTab_Settings:
                        Snackbar.make(fragmentHolder, "تنظیمات", Snackbar.LENGTH_SHORT).show();
                        return true;
                    case R.id.drawerNavigationViewTab_AboutUs:
                        Snackbar.make(fragmentHolder, "درباره ما", Snackbar.LENGTH_SHORT).show();
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
                        setUpFragment(homeFragment);
                        return true;
                    case R.id.bottomNavigationViewTab_Event:
                        setUpFragment(eventsFragment);
                        return true;
                    case R.id.bottomNavigationViewTab_Office:
                        setUpFragment(officesFragment);
                        return true;
                    case R.id.bottomNavigationViewTab_Search:
                        setUpFragment(searchFragment);
                        return true;
                    default:
                        setUpFragment(homeFragment);
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
                ColoredSnackBar.error(Snackbar.make(fragmentHolder, "مشکلی در اتصال به اینترنت به وجود آمده!", Snackbar.LENGTH_SHORT)).show();
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
        digiServiceIcon = findViewById(R.id.activityMain_DigiServiceIcon);
        bottomNavigationView = findViewById(R.id.activityMain_BottomNavigationView);
        fragmentHolder = findViewById(R.id.activityMain_CoordinatorLayout_FragmentHolder);
    }
}
