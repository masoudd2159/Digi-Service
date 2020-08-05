package ir.ac.sku.service.digiservice.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.adapter.AreasAdapter;
import ir.ac.sku.service.digiservice.adapter.DepartmentsAdapter;
import ir.ac.sku.service.digiservice.model.AreasModel;
import ir.ac.sku.service.digiservice.model.DepartmentsModel;
import ir.ac.sku.service.digiservice.util.ManagerHelper;
import ir.ac.sku.service.digiservice.util.MyHandler;
import me.relex.circleindicator.CircleIndicator2;


public class OfficesFragment extends Fragment implements
        DiscreteScrollView.OnItemChangedListener<DepartmentsAdapter.MyViewHolder>,
        DiscreteScrollView.ScrollStateChangeListener<DepartmentsAdapter.MyViewHolder> {

    //* Views
    @BindView(R.id.fragmentOffice_TextViewTitle) TextView title;
    @BindView(R.id.fragmentOffice_CardView) CardView cardView;
    @BindView(R.id.fragmentOffice_RecyclerView) RecyclerView recyclerView;
    @BindView(R.id.fragmentOffice_DiscreteScrollView) DiscreteScrollView scrollView;
    @BindView(R.id.fragmentOffice_PagerIndicator) CircleIndicator2 pagerIndicator;
    private View rootView;
    private AreasModel areasModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_offices, container, false);
        ButterKnife.bind(this, rootView);
        prepareAreasData();
        return rootView;
    }

    private void prepareAreasData() {
        if (ManagerHelper.isNOTOnline(rootView.getContext())) {
            ManagerHelper.noInternetAccess(rootView.getContext());
        } else {
            AreasModel.fetchFromWeb(rootView.getContext(), null, new MyHandler() {
                @Override
                public void onResponse(boolean ok, Object obj) {
                    if (ok) {
                        areasModel = new AreasModel();
                        areasModel = (AreasModel) obj;

                        prepareDepartmentsData("10");
                    }
                }
            });
        }
    }

    private void prepareDepartmentsData(String status) {
        HashMap<String, String> params = new HashMap<>();
        params.put("status", status);
        DepartmentsModel.fetchFromWeb(rootView.getContext(), params, new MyHandler() {
            @Override
            public void onResponse(boolean ok, Object obj) {
                if (ok) {
                    setUpDiscreteScrollView((DepartmentsModel) obj);
                }
            }
        });
    }

    private void setUpDiscreteScrollView(DepartmentsModel departmentsModel) {
        scrollView.setOrientation(DSVOrientation.HORIZONTAL);
        scrollView.setSlideOnFling(true);
        scrollView.setAdapter(new DepartmentsAdapter(rootView.getContext(), departmentsModel));
        scrollView.addOnItemChangedListener(this);
        scrollView.addScrollStateChangeListener(this);
        scrollView.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(scrollView);

        pagerIndicator.attachToRecyclerView(scrollView, pagerSnapHelper);
    }


    @Override
    public void onCurrentItemChanged(@Nullable DepartmentsAdapter.MyViewHolder myViewHolder, int i) {
        if (myViewHolder != null) {
            myViewHolder.setBackground();

            List<AreasModel.Data> data = new ArrayList<>();

            if (areasModel != null) {
                for (int j = 0; j < areasModel.getData().size(); j++) {
                    if (areasModel.getData().get(j).getDepartmentId() == (i + 1)) {
                        if (areasModel.getData().get(j).isActived()) {
                            data.add(areasModel.getData().get(j));
                        }
                    }
                }
                AreasAdapter adapter = new AreasAdapter(rootView.getContext(), data);
                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(rootView.getContext(), R.anim.layout_animation_from_right);
                recyclerView.setLayoutAnimation(animation);
                recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onScrollStart(@NonNull DepartmentsAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.resetBackground();
    }

    @Override
    public void onScrollEnd(@NonNull DepartmentsAdapter.MyViewHolder myViewHolder, int i) {

    }

    @Override
    public void onScroll(float position, int currentIndex, int newIndex, @Nullable DepartmentsAdapter.MyViewHolder currentHolder, @Nullable DepartmentsAdapter.MyViewHolder newHolder) {

    }
}
