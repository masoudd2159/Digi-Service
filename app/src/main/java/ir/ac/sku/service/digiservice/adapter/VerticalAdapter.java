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

import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.model.HomeModel;

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.MyViewHolder> {

    private List<HomeModel.Data> data;
    private Context context;

    public VerticalAdapter(Context context, List<HomeModel.Data> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_home_vertical_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryTitle;
        private RecyclerView recyclerViewHorizontal;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.customHomeVerticalList_CategoryTitle);
            recyclerViewHorizontal = itemView.findViewById(R.id.customHomeVerticalList_RecyclerViewHorizontal);
        }

        void bind(HomeModel.Data dataModel) {
            recyclerViewHorizontal.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_right));
            recyclerViewHorizontal.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            recyclerViewHorizontal.setAdapter(new HorizontalAdapter(context, dataModel.getItems()));
        }
    }
}
