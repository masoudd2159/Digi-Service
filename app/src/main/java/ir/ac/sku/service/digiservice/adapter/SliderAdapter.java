package ir.ac.sku.service.digiservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.config.MyAPI;
import ir.ac.sku.service.digiservice.util.Tools;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.MyViewHolder> {

    private Context context;
    private List<String> pictureURL;

    public SliderAdapter(Context context, List<String> pictureURL) {
        this.context = context;
        this.pictureURL = pictureURL;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_slider_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(pictureURL.get(position));
    }

    @Override
    public int getItemCount() {
        return pictureURL.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.customSliderView_ImageView) RoundedImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(String url) {
            Tools.displayImageOriginal(context,imageView,MyAPI.DIGI_SERVICE + url);
        }
    }
}
