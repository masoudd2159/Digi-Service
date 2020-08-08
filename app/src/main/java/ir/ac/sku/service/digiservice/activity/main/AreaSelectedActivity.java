package ir.ac.sku.service.digiservice.activity.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.adapter.AreaSelectedAdapter;
import ir.ac.sku.service.digiservice.api.office.ResourcesModel;
import ir.ac.sku.service.digiservice.base.MyActivity;
import ir.ac.sku.service.digiservice.config.MyLog;
import ir.ac.sku.service.digiservice.util.MyHandler;

@SuppressLint("LongLogTag")
public class AreaSelectedActivity extends MyActivity {
    //* Views
    @BindView(R.id.activityAreaSelected_TextViewTitle) TextView title;
    @BindView(R.id.activityAreaSelected_TextViewLabName) TextView labName;
    @BindView(R.id.activityAreaSelected_RecyclerView) RecyclerView recyclerView;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_area_selected;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpTextView();
        setUpDataResource();

    }

    private void setUpTextView() {
        String[] str = Objects.requireNonNull(getIntent().getStringExtra("title")).split("-");
        StringBuilder stringBuilder = new StringBuilder();
        title.setText(str[(str.length) - 1]);
        if (str.length > 1) {
            for (int i = 0; i < str.length - 1; i++) {
                stringBuilder.append(str[i]).append(" ");
            }
            labName.setText(stringBuilder);
        } else {
            labName.setVisibility(View.GONE);
        }
    }

    private void setUpDataResource() {
        HashMap<String, String> params = new HashMap<>();
        params.put("areaId", String.valueOf(Objects.requireNonNull(getIntent().getExtras()).getInt("id")));
        Log.i(MyLog.RESOURCES, "area Id : " + params.get("areaId"));

        ResourcesModel.fetchFromWeb(AreaSelectedActivity.this, params, new MyHandler() {
            @Override
            public void onResponse(boolean ok, Object obj) {
                if (ok) {
                    setUpRecyclerView((ResourcesModel) obj);
                }
            }
        });
    }

    private void setUpRecyclerView(ResourcesModel resourcesModel) {
        recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(AreaSelectedActivity.this, R.anim.layout_animation_from_right));
        recyclerView.setLayoutManager(new LinearLayoutManager(AreaSelectedActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new AreaSelectedAdapter(AreaSelectedActivity.this, resourcesModel.getData()));
    }

    public void onBackImageClick(View view) {
        finish();
    }
}
