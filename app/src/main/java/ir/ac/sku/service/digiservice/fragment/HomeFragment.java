package ir.ac.sku.service.digiservice.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.fragment.app.Fragment;
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
import butterknife.ButterKnife;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.adapter.SliderAdapter;
import ir.ac.sku.service.digiservice.adapter.VerticalAdapter;
import ir.ac.sku.service.digiservice.config.MyAPI;
import ir.ac.sku.service.digiservice.model.HomePageModel;
import ir.ac.sku.service.digiservice.model.SliderModel;
import ir.ac.sku.service.digiservice.util.MyHandler;
import me.relex.circleindicator.CircleIndicator2;

public class HomeFragment extends Fragment {

    //* Views
    @BindView(R.id.fragmentHome_RecyclerView) RecyclerView recyclerView;
    @BindView(R.id.fragmentHome_AnimationView) LottieAnimationView loadingAnimationView;
    @BindView(R.id.fragmentHome_DiscreteScrollView) DiscreteScrollView scrollView;
    @BindView(R.id.fragmentHome_PagerIndicator) CircleIndicator2 pagerIndicator;
    private View rootView;
    private int recyclerHandler = 0;
    private List<HomePageModel> homePageModelList = new ArrayList<HomePageModel>();

    private List<String> pictureURL = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);

        loadingAnimationView.setVisibility(View.VISIBLE);

        prepareHomePageModelData(MyAPI.RECENT_RESOURCES, "10");
        prepareHomePageSlider("0");


        return rootView;
    }

    private void prepareHomePageSlider(String eventId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("eventId", eventId);

        SliderModel.fetchFromWeb(rootView.getContext(), params, new MyHandler() {
            @SuppressLint("LongLogTag")
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
        scrollView.setAdapter(new SliderAdapter(rootView.getContext(), pictureURL));
            /*scrollView.addOnItemChangedListener(this);
            scrollView.addScrollStateChangeListener(this);*/
        scrollView.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(scrollView);

        pagerIndicator.attachToRecyclerView(scrollView, pagerSnapHelper);
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
                    recyclerHandler++;
                    if (recyclerHandler == 1) {
                        prepareHomePageModelData(MyAPI.POPULAR_RESOURCES, "10");
                    } else if (recyclerHandler == 2) {
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
}
