package ir.ac.sku.service.digiservice.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.adapter.DepartmentsAdapter;
import ir.ac.sku.service.digiservice.model.DepartmentsModel;
import ir.ac.sku.service.digiservice.util.MyHandler;
import me.relex.circleindicator.CircleIndicator2;


public class OfficesFragment extends Fragment implements
        DiscreteScrollView.OnItemChangedListener<DepartmentsAdapter.MyViewHolder>,
        DiscreteScrollView.ScrollStateChangeListener<DepartmentsAdapter.MyViewHolder> {

    private View rootView;
    private TextView title;
    private CircleIndicator2 pagerIndicator;
    private DiscreteScrollView scrollView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offices, container, false);
        this.rootView = view;
        init();
        prepareData();
        return view;
    }

    private void prepareData() {
        DepartmentsModel.fetchFromWeb(rootView.getContext(), null, new MyHandler() {
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


    private void init() {
        title = rootView.findViewById(R.id.fragmentOffice_TextViewTitle);
        scrollView = rootView.findViewById(R.id.fragmentOffice_DiscreteScrollView);
        pagerIndicator = rootView.findViewById(R.id.fragmentOffice_PagerIndicator);
    }

    @Override
    public void onCurrentItemChanged(@Nullable DepartmentsAdapter.MyViewHolder myViewHolder, int i) {
        if (myViewHolder != null) {
            myViewHolder.setBackground();
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
