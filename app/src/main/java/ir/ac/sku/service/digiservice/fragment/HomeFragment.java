package ir.ac.sku.service.digiservice.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.activity.main.AreaSelectedActivity;
import ir.ac.sku.service.digiservice.adapter.AreaSelectedAdapter;
import ir.ac.sku.service.digiservice.adapter.VerticalAdapter;
import ir.ac.sku.service.digiservice.model.HomeModel;
import ir.ac.sku.service.digiservice.util.MyHandler;

public class HomeFragment extends Fragment {
    private View rootView;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        this.rootView = view;
        init();
        //prepareData();
        return view;
    }

    private void setUpRecyclerView(HomeModel homeModel) {
        recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(rootView.getContext(), R.anim.layout_animation_from_right));
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new VerticalAdapter(rootView.getContext(), homeModel.getData()));
    }

    private void prepareData() {
        HomeModel.fetchFromWeb(rootView.getContext(), null, new MyHandler() {
            @Override
            public void onResponse(boolean ok, Object obj) {
                setUpRecyclerView((HomeModel) obj);
            }
        });
    }

    private void init() {
        recyclerView = rootView.findViewById(R.id.fragmentHome_RecyclerView);
    }
}
