package ir.ac.sku.service.digiservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.api.home.HomePageModel;

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.MyViewHolder> {

    private Context context;
    private List<HomePageModel> homePageModelList;

    public VerticalAdapter(Context context, List<HomePageModel> homePageModelList) {
        this.context = context;
        this.homePageModelList = homePageModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_home_vertical_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        switch (position) {
            case 0:
                holder.categoryTitle.setText("جدیدترین های منابع");
                setUpRecyclerView(homePageModelList.get(position), holder);
                break;
            case 1:
                holder.categoryTitle.setText("محبوب ترین منابع");
                setUpRecyclerView(homePageModelList.get(position), holder);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    private void setUpRecyclerView(HomePageModel model, MyViewHolder holder) {
        holder.recyclerViewHorizontal.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_right));
        holder.recyclerViewHorizontal.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerViewHorizontal.setAdapter(new HorizontalAdapter(context, model.getData()));
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.customHomeVerticalList_CategoryTitle) TextView categoryTitle;
        @BindView(R.id.customHomeVerticalList_RecyclerViewHorizontal) RecyclerView recyclerViewHorizontal;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
