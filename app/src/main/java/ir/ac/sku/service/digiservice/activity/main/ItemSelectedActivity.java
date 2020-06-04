package ir.ac.sku.service.digiservice.activity.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.config.MyAPI;
import ir.ac.sku.service.digiservice.model.SelectedResourceModel;
import ir.ac.sku.service.digiservice.util.MyActivity;
import ir.ac.sku.service.digiservice.util.MyHandler;

public class ItemSelectedActivity extends MyActivity {

    private ImageView picture;
    private TextView title;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_selected);
        init();

        String[] pageTitle = getResources().getStringArray(R.array.pagerTitles);
        for (int i = 0; i < pageTitle.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(pageTitle[i]));
        }
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            @SuppressLint("InflateParams") TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
            tabLayout.getTabAt(i).setCustomView(textView);
        }
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //tabLayout.setupWithViewPager(viewPager);

        setUpData();
    }

    private void init() {
        picture = findViewById(R.id.activityItemSelected_Picture);
        title = findViewById(R.id.activityItemSelected_Title);
        viewPager = findViewById(R.id.activityItemSelected_ViewPager);
        tabLayout = findViewById(R.id.activityItemSelected_TabLayout);
    }

    private void setUpData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(getIntent().getExtras().getInt("id")));
        SelectedResourceModel.fetchFromWeb(ItemSelectedActivity.this, params, new MyHandler() {
            @Override
            public void onResponse(boolean ok, Object obj) {
                if (ok) {
                    showData((SelectedResourceModel) obj);
                }
            }
        });
    }

    private void showData(SelectedResourceModel model) {
        title.setText(model.getData().get(0).getLabName());
        Glide
                .with(ItemSelectedActivity.this)
                .load(MyAPI.DIGI_SERVICE + model.getData().get(0).getPicture())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .into(picture)
        ;
    }

    public void onBackImageClick(View view) {
        finish();
    }
}