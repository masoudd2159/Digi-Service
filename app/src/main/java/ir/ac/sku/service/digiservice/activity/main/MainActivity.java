package ir.ac.sku.service.digiservice.activity.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import butterknife.BindView;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.activity.SplashScreenActivity;
import ir.ac.sku.service.digiservice.base.BaseActivity;
import ir.ac.sku.service.digiservice.fragment.HomeFragment;
import ir.ac.sku.service.digiservice.fragment.NewsFragment;
import ir.ac.sku.service.digiservice.fragment.OfficesFragment;
import ir.ac.sku.service.digiservice.fragment.SearchFragment;
import ir.ac.sku.service.digiservice.util.CustomToastExit;

@SuppressLint("NonConstantResourceId")
public class MainActivity extends BaseActivity {

    //* Fragments
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private final Fragment homeFragment = new HomeFragment();
    private final Fragment newsFragment = new NewsFragment();
    private final Fragment officesFragment = new OfficesFragment();
    private final Fragment searchFragment = new SearchFragment();
    //* Views
    @BindView(R.id.layout_content) CoordinatorLayout holder;
    @BindView(R.id.activityMain_ImageButtonDrawer) ImageButton drawerButton;
    @BindView(R.id.activityMain_DrawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.activityMain_NavigationView) NavigationView navigationView;
    @BindView(R.id.activityMain_BottomNavigationView) BottomNavigationView bottomNavigationView;
    private Fragment active = homeFragment;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {
        switch (item.getItemId()) {
            case R.id.bottomNavigationViewTab_Home:
                fragmentManager.beginTransaction().hide(active).show(homeFragment).commit();
                active = homeFragment;
                return true;

            case R.id.bottomNavigationViewTab_News:
                fragmentManager.beginTransaction().hide(active).show(newsFragment).commit();
                active = newsFragment;
                return true;

            case R.id.bottomNavigationViewTab_Office:
                fragmentManager.beginTransaction().hide(active).show(officesFragment).commit();
                active = officesFragment;
                return true;

            case R.id.bottomNavigationViewTab_Search:
                fragmentManager.beginTransaction().hide(active).show(searchFragment).commit();
                active = searchFragment;
                return true;
            default:
                break;
        }
        return false;
    };

    //* Requirements
    private boolean doubleBackToExitPressedOnce = false;

    //* Set Content View
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpBottomNavigationView();
        setUpNavigationDrawerView();
    }

    private void setUpBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager.beginTransaction().add(R.id.fragmentHolder, searchFragment, "4").hide(searchFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragmentHolder, officesFragment, "3").hide(officesFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragmentHolder, newsFragment, "2").hide(newsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragmentHolder, homeFragment, "1").commit();
    }

    @SuppressLint("WrongConstant")
    private void setUpNavigationDrawerView() {
        drawerButton.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(Gravity.END)) {
                drawerLayout.closeDrawer(Gravity.END);
            } else {
                drawerLayout.openDrawer(Gravity.END);
            }
        });

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);
            switch (menuItem.getItemId()) {
                case R.id.drawerNavigationViewTab_Questions:
                    Snackbar.make(holder, "سوالات متداول", BaseTransientBottomBar.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(Gravity.END);
                    return true;
                case R.id.drawerNavigationViewTab_Guide:
                    Snackbar.make(holder, "راهنمای سامانه", BaseTransientBottomBar.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(Gravity.END);
                    return true;
                case R.id.drawerNavigationViewTab_Rules:
                    Snackbar.make(holder, "قوانین", BaseTransientBottomBar.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(Gravity.END);
                    return true;
                case R.id.drawerNavigationViewTab_Settings:
                    Snackbar.make(holder, "تنظیمات", BaseTransientBottomBar.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(Gravity.END);
                    return true;
                case R.id.drawerNavigationViewTab_AboutUs:
                    Snackbar.make(holder, "درباره ما", BaseTransientBottomBar.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(Gravity.END);
                    return true;
                case R.id.drawerNavigationViewTab_Logout:
                    if (!getPreferencesUtils().isStartSigningKey()) {
                        getPreferencesUtils().clearSharedPreferences();
                        startActivity(null, SplashScreenActivity.class);
                        finish();
                    }
                    drawerLayout.closeDrawer(Gravity.END);
                    return true;
                default:
                    return true;
            }
        });
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(Gravity.END)) {
            this.drawerLayout.closeDrawer(Gravity.END);
        } else {
            if (R.id.bottomNavigationViewTab_Home != bottomNavigationView.getSelectedItemId()) {
                bottomNavigationView.setSelectedItemId(R.id.bottomNavigationViewTab_Home);
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
                    new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
                }
            }
        }
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
}
