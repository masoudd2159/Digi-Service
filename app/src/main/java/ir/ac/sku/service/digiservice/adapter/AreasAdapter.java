package ir.ac.sku.service.digiservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.activity.main.AreaSelectedActivity;
import ir.ac.sku.service.digiservice.api.office.AreasModel;

public class AreasAdapter extends RecyclerView.Adapter<AreasAdapter.MyViewHolder> {


    private Context context;
    private List<AreasModel.Data> data;

    public AreasAdapter(Context context, List<AreasModel.Data> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new AreasAdapter.MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_areas_list, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.persianTitle.setText(data.get(i).getTitle());
        Log.i("Masoud", "i : " + String.valueOf(i));
        Log.i("Masoud", "size : " + String.valueOf(data.size()));

        if (i == (data.size() - 1)) {
            myViewHolder.line.setVisibility(View.INVISIBLE);
        } else {
            myViewHolder.line.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.customAreasListView_TextViewPersianTitle) TextView persianTitle;
        @BindView(R.id.customAreasListView_ImageViewLine) ImageView line;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, AreaSelectedActivity.class);
            intent.putExtra("id", data.get(getLayoutPosition()).getId());
            intent.putExtra("title", data.get(getLayoutPosition()).getTitle());
            context.startActivity(intent);
        }
    }
}
