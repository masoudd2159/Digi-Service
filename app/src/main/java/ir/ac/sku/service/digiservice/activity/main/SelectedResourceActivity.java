package ir.ac.sku.service.digiservice.activity.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;

import butterknife.BindView;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.adapter.ViewPagerAdapter;
import ir.ac.sku.service.digiservice.api.selectedResource.SelectedResourceModel;
import ir.ac.sku.service.digiservice.base.BaseActivity;
import ir.ac.sku.service.digiservice.config.MyAPI;
import ir.ac.sku.service.digiservice.fragment.ItemSelectedTabFragment;
import ir.ac.sku.service.digiservice.fragment.dialogfragment.SchedulingFragmentDialog;
import ir.ac.sku.service.digiservice.util.Tools;

@SuppressLint("NonConstantResourceId")
public class SelectedResourceActivity extends BaseActivity {

    //* Views
    @SuppressLint("NonConstantResourceId") @BindView(R.id.activityItemSelected_Head) TextView head;
    @BindView(R.id.activityItemSelected_Title) TextView title;
    @BindView(R.id.activityItemSelected_Picture) ImageView picture;
    @BindView(R.id.activityItemSelected_applying) Button applying;
    @BindView(R.id.activityItemSelected_TabLayout) TabLayout tabLayout;
    @BindView(R.id.activityItemSelected_ViewPager) ViewPager viewPager;
    @BindView(R.id.activityItemSelected_scheduling) Button scheduling;

    //* Adapter
    private ViewPagerAdapter adapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_item_selected;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        setUpData();
    }

    private void setUpData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(getIntent().getExtras().getInt("id")));
        Log.i(getTagLog(), "Id : " + params.get("id"));

        SelectedResourceModel.fetchFromWeb(SelectedResourceActivity.this, params, (ok, obj) -> {
            if (ok) {
                SelectedResourceModel selectedResourceModel = (SelectedResourceModel) obj;
                if (selectedResourceModel.isOk()) {
                    showData(selectedResourceModel.getData().get(0));
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showData(SelectedResourceModel.Data dataModel) {
        if (dataModel.isActived()) {
            title.setText(dataModel.getLabName());
            Tools.displayImageOriginal(SelectedResourceActivity.this, picture, MyAPI.DIGI_SERVICE + dataModel.getPicture());
            head.setText("مسئول آزمایشگاه " + dataModel.getHead());

            adapter.addFragment(ItemSelectedTabFragment.newInstance(dataModel.getDescription()), getString(R.string.pagerTitle_1));
            adapter.addFragment(ItemSelectedTabFragment.newInstance(""), getString(R.string.pagerTitle_2));
            adapter.addFragment(ItemSelectedTabFragment.newInstance(""), getString(R.string.pagerTitle_3));
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                @SuppressLint("InflateParams") TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
                tabLayout.getTabAt(i).setCustomView(textView);
            }

            scheduling.setOnClickListener(view -> showDialogScheduling(dataModel.getId(), dataModel.getSchTable()));

        } else {
            Toast.makeText(this, "فعلا این منبع در دسترس نیست !", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void showDialogScheduling(int id, String schTable) {
        Log.i(getTagLog(), "Dialog Scheduling");
        SchedulingFragmentDialog fragment = new SchedulingFragmentDialog(id, schTable);
        fragment.show(getSupportFragmentManager(), "addUserPersonalInfo");

    }

    public void onBackImageClick(View view) {
        finish();
    }
}