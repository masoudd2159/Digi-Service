package ir.ac.sku.service.digiservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.config.MyAPI;
import ir.ac.sku.service.digiservice.model.NewsModel;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private Context context;
    private NewsModel newsModel;

    public NewsAdapter(Context context, NewsModel newsModel) {
        this.context = context;
        this.newsModel = newsModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_news_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(newsModel.getData().get(position));
    }

    @Override
    public int getItemCount() {
        return newsModel.getData().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RoundedImageView picture;
        private TextView title;
        private JustifiedTextView body;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            picture = itemView.findViewById(R.id.customNewsView_ImageView);
            title = itemView.findViewById(R.id.customNewsView_Title);
            body = itemView.findViewById(R.id.customNewsView_TextViewBody);

            itemView.setOnClickListener(this);
        }

        public void bind(NewsModel.Data data) {
            title.setText(data.getTitle().toString().trim());
            body.setText(data.getBody().toString().trim());
            Glide
                    .with(context)
                    .load(MyAPI.DIGI_SERVICE + data.getPicture())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(true)
                    .into(picture)
            ;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://service.sku.ac.ir/News/" + String.valueOf(newsModel.getData().get(getLayoutPosition()).getId())));
            context.startActivity(intent);
        }
    }
}
