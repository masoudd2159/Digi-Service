package ir.ac.sku.service.digiservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.activity.main.ItemSelectedActivity;
import ir.ac.sku.service.digiservice.config.MyAPI;
import ir.ac.sku.service.digiservice.model.SearchModel;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private Context context;
    private List<SearchModel.Data> modelData;

    public SearchAdapter(Context context, List<SearchModel.Data> modelData) {
        this.context = context;
        this.modelData = modelData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_area_selected_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.bind(modelData.get(position));
    }

    @Override
    public int getItemCount() {
        return modelData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RoundedImageView imageView;
        private TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.customAreaSelectedList_ImageView);
            textView = itemView.findViewById(R.id.customAreaSelectedList_TextView);

            itemView.setOnClickListener(this);
        }

        void bind(SearchModel.Data data) {
            textView.setText(data.getLabName());
            Glide
                    .with(context)
                    .load(MyAPI.DIGI_SERVICE + data.getPicture())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView)
            ;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ItemSelectedActivity.class);
            intent.putExtra("id", modelData.get(getLayoutPosition()).getId());
            context.startActivity(intent);
        }
    }
}