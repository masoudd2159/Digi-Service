package ir.ac.sku.service.digiservice.activity.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.adapter.ViewPagerAdapter;
import ir.ac.sku.service.digiservice.config.MyAPI;
import ir.ac.sku.service.digiservice.fragment.ItemSelectedTabFragment;
import ir.ac.sku.service.digiservice.fragment.SchedulingFragmentDialog;
import ir.ac.sku.service.digiservice.model.SelectedResourceModel;
import ir.ac.sku.service.digiservice.util.MyActivity;
import ir.ac.sku.service.digiservice.util.MyHandler;

public class ItemSelectedActivity extends MyActivity {

    private TextView head;
    private TextView title;
    private Button applying;
    private Button scheduling;
    private ImageView picture;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_selected);
        init();
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        setUpData();
    }

    private void setUpData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(getIntent().getExtras().getInt("id")));
        SelectedResourceModel.fetchFromWeb(ItemSelectedActivity.this, params, new MyHandler() {
            @Override
            public void onResponse(boolean ok, Object obj) {
                if (ok) {
                    SelectedResourceModel selectedResourceModel = (SelectedResourceModel) obj;
                    if (selectedResourceModel.isOk()) {

                        showData(selectedResourceModel.getData().get(0));
                    }
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showData(SelectedResourceModel.Data dataSelectedResourceModel) {
        if (dataSelectedResourceModel.isActived()) {
            title.setText(dataSelectedResourceModel.getLabName());

            Glide
                    .with(ItemSelectedActivity.this)
                    .load(MyAPI.DIGI_SERVICE + dataSelectedResourceModel.getPicture())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(true)
                    .into(picture)
            ;

            head.setText("مسئول آزمایشگاه " + dataSelectedResourceModel.getHead());

            adapter.addFragment(ItemSelectedTabFragment.newInstance(dataSelectedResourceModel.getDescription()), getString(R.string.pagerTitle_1));
            adapter.addFragment(ItemSelectedTabFragment.newInstance(""), getString(R.string.pagerTitle_2));
            adapter.addFragment(ItemSelectedTabFragment.newInstance(""), getString(R.string.pagerTitle_3));
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                @SuppressLint("InflateParams") TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
                tabLayout.getTabAt(i).setCustomView(textView);
            }

            scheduling.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialogScheduling(dataSelectedResourceModel.getId(), dataSelectedResourceModel.getSchTable());
                }
            });

        } else {
            Toast.makeText(this, "فعلا این منبع در دسترس نیست !", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void showDialogScheduling(int id, String schTable) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SchedulingFragmentDialog fragment = new SchedulingFragmentDialog(id,schTable);
        fragment.show(getSupportFragmentManager(), "addUserPersonalInfo");

    }

    private ItemSelectedTabFragment getItemSelectedTabFragment(String htmlCode) {
        return ItemSelectedTabFragment.newInstance(htmlCode);
    }

    private void init() {
        picture = findViewById(R.id.activityItemSelected_Picture);
        title = findViewById(R.id.activityItemSelected_Title);
        head = findViewById(R.id.activityItemSelected_Head);
        viewPager = findViewById(R.id.activityItemSelected_ViewPager);
        tabLayout = findViewById(R.id.activityItemSelected_TabLayout);
        scheduling = findViewById(R.id.activityItemSelected_scheduling);
        applying = findViewById(R.id.activityItemSelected_applying);
    }

    public void onBackImageClick(View view) {
        finish();
    }
}