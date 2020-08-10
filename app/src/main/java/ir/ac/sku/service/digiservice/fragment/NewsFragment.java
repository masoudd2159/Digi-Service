package ir.ac.sku.service.digiservice.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

import butterknife.BindView;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.adapter.NewsAdapter;
import ir.ac.sku.service.digiservice.api.news.NewsModel;
import ir.ac.sku.service.digiservice.base.BaseFragment;
import ir.ac.sku.service.digiservice.util.MyHandler;

public class NewsFragment extends BaseFragment {

    //* Views
    @BindView(R.id.fragmentEvent_RecyclerView) RecyclerView recyclerView;

    //* View Inflater
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_news;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prepareNews("0");
    }

    private void prepareNews(String eventId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("eventId", eventId);

        NewsModel.fetchFromWeb(getContext(), params, new MyHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(boolean ok, Object obj) {
                if (ok) {
                    setUpView((NewsModel) obj);
                }
            }
        });
    }

    private void setUpView(NewsModel newsModel) {
        recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_from_right));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new NewsAdapter(getContext(), newsModel));
    }
}