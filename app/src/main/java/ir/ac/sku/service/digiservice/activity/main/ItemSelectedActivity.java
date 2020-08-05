package ir.ac.sku.service.digiservice.activity.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.adapter.ViewPagerAdapter;
import ir.ac.sku.service.digiservice.config.MyAPI;
import ir.ac.sku.service.digiservice.fragment.ItemSelectedTabFragment;
import ir.ac.sku.service.digiservice.fragment.SchedulingFragmentDialog;
import ir.ac.sku.service.digiservice.model.SelectedResourceModel;
import ir.ac.sku.service.digiservice.util.MyActivity;
import ir.ac.sku.service.digiservice.util.MyHandler;

public class ItemSelectedActivity extends MyActivity {

    //* Views
    @BindView(R.id.activityItemSelected_Head) TextView head;
    @BindView(R.id.activityItemSelected_Title) TextView title;
    @BindView(R.id.activityItemSelected_applying) Button applying;
    @BindView(R.id.activityItemSelected_scheduling) Button scheduling;
    @BindView(R.id.activityItemSelected_Picture) ImageView picture;
    @BindView(R.id.activityItemSelected_TabLayout) TabLayout tabLayout;
    @BindView(R.id.activityItemSelected_ViewPager) ViewPager viewPager;

    //* Adapter
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_selected);
        ButterKnife.bind(this);
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
        SchedulingFragmentDialog fragment = new SchedulingFragmentDialog(id, schTable);
        fragment.show(getSupportFragmentManager(), "addUserPersonalInfo");

    }

    private ItemSelectedTabFragment getItemSelectedTabFragment(String htmlCode) {
        return ItemSelectedTabFragment.newInstance(htmlCode);
    }

    public void onBackImageClick(View view) {
        finish();
    }
}