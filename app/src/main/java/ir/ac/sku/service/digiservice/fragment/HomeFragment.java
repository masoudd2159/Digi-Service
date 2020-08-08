package ir.ac.sku.service.digiservice.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.adapter.SliderAdapter;
import ir.ac.sku.service.digiservice.adapter.VerticalAdapter;
import ir.ac.sku.service.digiservice.api.home.HomePageModel;
import ir.ac.sku.service.digiservice.api.home.SliderModel;
import ir.ac.sku.service.digiservice.base.BaseFragment;
import ir.ac.sku.service.digiservice.config.MyAPI;
import ir.ac.sku.service.digiservice.config.MyLog;
import ir.ac.sku.service.digiservice.util.MyHandler;
import me.relex.circleindicator.CircleIndicator2;

@SuppressLint("LongLogTag")
public class HomeFragment extends BaseFragment {

    //* Views
    @BindView(R.id.fragmentHome_RecyclerView) RecyclerView recyclerView;
    @BindView(R.id.fragmentHome_PagerIndicator) CircleIndicator2 pagerIndicator;
    @BindView(R.id.fragmentHome_DiscreteScrollView) DiscreteScrollView scrollView;
    @BindView(R.id.fragmentHome_AnimationView) LottieAnimationView loadingAnimationView;

    //* Requirement
    private List<String> pictureURL = new ArrayList<String>();
    private List<HomePageModel> homePageModelList = new ArrayList<HomePageModel>();

    //* View Inflater
    @Override protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadingAnimationView.setVisibility(View.VISIBLE);

        prepareHomePageModelData(MyAPI.RECENT_RESOURCES, "10");
        prepareHomePageSlider("0");
    }

    private void prepareHomePageSlider(String eventId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("eventId", eventId);
        Log.i(MyLog.SLIDER, "Event ID : " + eventId);

        SliderModel.fetchFromWeb(getContext(), params, new MyHandler() {
            @Override
            public void onResponse(boolean ok, Object obj) {
                if (ok) {
                    SliderModel sliderModel = (SliderModel) obj;

                    for (int i = 0; i < sliderModel.getData().size(); i++) {
                        pictureURL.add(sliderModel.getData().get(i).getPicture());
                    }

                    if (eventId.equals("0")) {
                        prepareHomePageSlider("-1");
                    } else if (eventId.equals("-1")) {
                        setUpDiscreteScrollView(pictureURL);
                    }
                }
            }
        });
    }

    private void setUpDiscreteScrollView(List<String> pictureURL) {
        scrollView.setOrientation(DSVOrientation.HORIZONTAL);
        scrollView.setSlideOnFling(true);
        scrollView.setAdapter(new SliderAdapter(getContext(), pictureURL));
        scrollView.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(scrollView);

        pagerIndicator.attachToRecyclerView(scrollView, pagerSnapHelper);
    }

    private void prepareHomePageModelData(String urlApi, String status) {
        Log.i(MyLog.HOME, "Status : " + status);
        Log.i(MyLog.HOME, "URL to Request : " + urlApi);

        HashMap<String, String> params = new HashMap<>();
        params.put("status", status);

        HomePageModel.fetchFromWeb(getContext(), urlApi, params, new MyHandler() {
            @Override
            public void onResponse(boolean ok, Object obj) {
                if (ok) {
                    homePageModelList.add((HomePageModel) obj);
                    if (urlApi.equals(MyAPI.RECENT_RESOURCES)) {
                        prepareHomePageModelData(MyAPI.POPULAR_RESOURCES, "10");
                    } else if (urlApi.equals(MyAPI.POPULAR_RESOURCES)) {
                        setUpRecyclerView(homePageModelList);
                        loadingAnimationView.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }

    private void setUpRecyclerView(List<HomePageModel> homePageModelList) {
        recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_from_right));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new VerticalAdapter(getContext(), homePageModelList));
    }
}
