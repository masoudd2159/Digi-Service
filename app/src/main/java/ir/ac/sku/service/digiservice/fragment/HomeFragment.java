package ir.ac.sku.service.digiservice.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.adapter.VerticalAdapter;
import ir.ac.sku.service.digiservice.config.MyAPI;
import ir.ac.sku.service.digiservice.model.HomePageModel;
import ir.ac.sku.service.digiservice.util.MyHandler;

import static ir.ac.sku.service.digiservice.config.MyLog.DIGI_SERVICE;

public class HomeFragment extends Fragment {
    private View rootView;
    private RecyclerView recyclerView;
    private List<HomePageModel> homePageModelList = new ArrayList<HomePageModel>();
    private int handler = 0;
    private LottieAnimationView loadingAnimationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        this.rootView = view;
        init();
        loadingAnimationView.setVisibility(View.VISIBLE);

        prepareHomePageModelData(MyAPI.RECENT_RESOURCES, "10");

        return view;
    }

    private void prepareHomePageModelData(String urlApi, String status) {
        HashMap<String, String> params = new HashMap<>();
        params.put("status", status);

        HomePageModel.fetchFromWeb(rootView.getContext(), urlApi, params, new MyHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(boolean ok, Object obj) {
                if (ok) {
                    homePageModelList.add((HomePageModel) obj);
                    handler++;
                    if (handler == 1) {
                        prepareHomePageModelData(MyAPI.RECENT_RESOURCES, "01");
                    } else if (handler == 2) {
                        prepareHomePageModelData(MyAPI.POPULAR_RESOURCES, "10");
                    } else if (handler == 3) {
                        prepareHomePageModelData(MyAPI.POPULAR_RESOURCES, "01");
                    } else if (handler == 4) {
                        setUpRecyclerView(homePageModelList);
                        loadingAnimationView.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }

    private void setUpRecyclerView(List<HomePageModel> homePageModelList) {
        recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(rootView.getContext(), R.anim.layout_animation_from_right));
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new VerticalAdapter(rootView.getContext(), homePageModelList));
    }

    private void init() {
        recyclerView = rootView.findViewById(R.id.fragmentHome_RecyclerView);
        loadingAnimationView = rootView.findViewById(R.id.fragmentHome_AnimationView);
    }
}
