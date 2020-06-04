package ir.ac.sku.service.digiservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.activity.main.ItemSelectedActivity;
import ir.ac.sku.service.digiservice.config.MyAPI;
import ir.ac.sku.service.digiservice.model.HomePageModel;

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

        private TextView resourceTitle;
        private ImageView resourcePicture;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            resourceTitle = itemView.findViewById(R.id.customHomeHorizontalView_TextView);
            resourcePicture = itemView.findViewById(R.id.customHomeHorizontalView_ImageView);

            itemView.setOnClickListener(this);
        }

        void bind(HomePageModel.Data itemsModel) {
            resourceTitle.setText(itemsModel.getLabName());
            Glide
                    .with(context)
                    .load(MyAPI.DIGI_SERVICE + itemsModel.getPicture())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(true)
                    .into(resourcePicture)
            ;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ItemSelectedActivity.class);
            intent.putExtra("id", items.get(getLayoutPosition()).getId());
            context.startActivity(intent);
        }
    }
}
