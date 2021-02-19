package ir.ac.sku.service.digiservice.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.activity.main.SelectedResourceActivity;
import ir.ac.sku.service.digiservice.api.home.HomePageModel;
import ir.ac.sku.service.digiservice.config.MyAPI;
import ir.ac.sku.service.digiservice.util.Tools;

@SuppressLint("NonConstantResourceId")
class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

    private Context context;
    private List<HomePageModel.Data> items;

    HorizontalAdapter(Context context, List<HomePageModel.Data> items) {
        this.context = context;
        this.items = (items == null) ? new ArrayList<HomePageModel.Data>() : items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_home_horizontal_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.customHomeHorizontalView_TextView) TextView resourceTitle;
        @BindView(R.id.customHomeHorizontalView_ImageView) ImageView resourcePicture;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(HomePageModel.Data itemsModel) {
            resourceTitle.setText(itemsModel.getLabName());
            Tools.displayImageOriginal(context, resourcePicture, MyAPI.DIGI_SERVICE + itemsModel.getPicture());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, SelectedResourceActivity.class);
            intent.putExtra("id", items.get(getLayoutPosition()).getId());
            context.startActivity(intent);
        }
    }
}
