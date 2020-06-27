package ir.ac.sku.service.digiservice.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import java.util.HashMap;

import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.adapter.NewsAdapter;
import ir.ac.sku.service.digiservice.adapter.VerticalAdapter;
import ir.ac.sku.service.digiservice.model.NewsModel;
import ir.ac.sku.service.digiservice.model.SliderModel;
import ir.ac.sku.service.digiservice.util.MyHandler;

public class EventsFragment extends Fragment {
    private View rootView;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        this.rootView = view;
        init();

        prepareNews("0");

        return view;
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

    private void init() {
        recyclerView = rootView.findViewById(R.id.fragmentEvent_RecyclerView);
    }
}
