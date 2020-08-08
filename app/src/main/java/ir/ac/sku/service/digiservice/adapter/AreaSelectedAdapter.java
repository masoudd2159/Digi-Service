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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.activity.main.SelectedResourceActivity;
import ir.ac.sku.service.digiservice.config.MyAPI;
import ir.ac.sku.service.digiservice.api.office.ResourcesModel;

public class AreaSelectedAdapter extends RecyclerView.Adapter<AreaSelectedAdapter.MyViewHolder> {

    private Context context;
    private List<ResourcesModel.Data> data;

    public AreaSelectedAdapter(Context context, List<ResourcesModel.Data> data) {
        this.context = context;
        this.data = (data == null) ? new ArrayList<ResourcesModel.Data>() : data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_area_selected_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.customAreaSelectedList_ImageView) RoundedImageView imageView;
        @BindView(R.id.customAreaSelectedList_TextView) TextView textView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        void bind(ResourcesModel.Data dataModel) {
            textView.setText(dataModel.getTitle());
            Glide
                    .with(context)
                    .load(MyAPI.DIGI_SERVICE + dataModel.getPicture())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView)
            ;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, SelectedResourceActivity.class);
            intent.putExtra("id", data.get(getLayoutPosition()).getId());
            context.startActivity(intent);
        }
    }
}
