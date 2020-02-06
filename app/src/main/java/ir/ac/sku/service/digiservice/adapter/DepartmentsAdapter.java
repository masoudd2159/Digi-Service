package ir.ac.sku.service.digiservice.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.model.DepartmentsModel;

public class DepartmentsAdapter extends RecyclerView.Adapter<DepartmentsAdapter.MyViewHolder> {

    private Context context;
    private DepartmentsModel departmentsModel;
    private int index = -1;

    public DepartmentsAdapter(Context context, DepartmentsModel departmentsModel) {
        this.context = context;
        this.departmentsModel = departmentsModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new DepartmentsAdapter.MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_department_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.bind(departmentsModel.getData().get(position));

        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                notifyDataSetChanged();
            }
        });

        if (index == position) {
            myViewHolder.linearLayout.setBackgroundResource(R.drawable.bg_department_selected);
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return departmentsModel.getData().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView departmentTitle;
        private ImageView departmentImage;
        private LinearLayout linearLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            departmentTitle = itemView.findViewById(R.id.customDepartmentView_TextView);
            departmentImage = itemView.findViewById(R.id.customDepartmentView_ImageView);
            linearLayout = itemView.findViewById(R.id.customDepartmentView_LinearLayout);
        }

        void bind(DepartmentsModel.Data data) {
            departmentTitle.setText(data.getTitle());
            //Glide.with(context).load(data.getPicture()).diskCacheStrategy(DiskCacheStrategy.ALL).into(departmentImage);

        }

        public void setBackground() {
            linearLayout.setBackgroundResource(R.drawable.bg_department_selected);
        }

        public void resetBackground() {
            linearLayout.setBackgroundColor(Color.TRANSPARENT);
        }
    }
}
