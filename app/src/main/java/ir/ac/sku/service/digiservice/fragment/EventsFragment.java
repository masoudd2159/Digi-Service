package ir.ac.sku.service.digiservice.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.adapter.NewsAdapter;
import ir.ac.sku.service.digiservice.model.NewsModel;
import ir.ac.sku.service.digiservice.util.MyHandler;

public class EventsFragment extends Fragment {

    //* Views
    @BindView(R.id.fragmentEvent_RecyclerView) RecyclerView recyclerView;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_events, container, false);
        ButterKnife.bind(this, rootView);
        prepareNews("0");
        return rootView;
    }

    private void prepareNews(String eventId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("eventId", eventId);

        NewsModel.fetchFromWeb(rootView.getContext(), params, new MyHandler() {
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
        recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(rootView.getContext(), R.anim.layout_animation_from_right));
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new NewsAdapter(rootView.getContext(), newsModel));
    }
}
