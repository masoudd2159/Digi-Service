package ir.ac.sku.service.digiservice.activity.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Objects;

import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.adapter.AreaSelectedAdapter;
import ir.ac.sku.service.digiservice.model.ResourcesModel;
import ir.ac.sku.service.digiservice.util.MyActivity;
import ir.ac.sku.service.digiservice.util.MyHandler;

public class AreaSelectedActivity extends MyActivity {

    private TextView title;
    private TextView labName;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_selected);
        init();
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

    private void init() {
        title = findViewById(R.id.activityAreaSelected_TextViewTitle);
        labName = findViewById(R.id.activityAreaSelected_TextViewLabName);
        recyclerView = findViewById(R.id.activityAreaSelected_RecyclerView);
    }
}
