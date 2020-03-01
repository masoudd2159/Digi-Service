package ir.ac.sku.service.digiservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.CheckedOutputStream;

import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.model.HomeModel;

class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

    private Context context;
    private List<HomeModel.Data.Items> items;

    HorizontalAdapter(Context context, List<HomeModel.Data.Items> items) {
        this.context = context;
        this.items = (items == null) ? new ArrayList<HomeModel.Data.Items>() : items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_department_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(HomeModel.Data.Items itemsModel) {

        }
    }
}
