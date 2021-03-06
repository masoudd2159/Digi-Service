package ir.ac.sku.service.digiservice.adapter;

import android.content.Context;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.api.news.NewsModel;
import ir.ac.sku.service.digiservice.config.MyAPI;
import ir.ac.sku.service.digiservice.util.Tools;

import static ir.ac.sku.service.digiservice.config.MyAPI.DIGI_SERVICE_NEWS;

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

        @BindView(R.id.customNewsView_ImageView) RoundedImageView picture;
        @BindView(R.id.customNewsView_Title) TextView title;
        @BindView(R.id.customNewsView_TextViewBody) JustifiedTextView body;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(NewsModel.Data data) {
            title.setText(data.getTitle().toString().trim());
            body.setText(data.getBody().toString().trim());
            Tools.displayImageOriginal(context,picture,MyAPI.DIGI_SERVICE + data.getPicture());
        }

        @Override
        public void onClick(View v) {
            Tools.openWebViewActivity(context, DIGI_SERVICE_NEWS + newsModel.getData().get(getLayoutPosition()).getId());
        }
    }
}
