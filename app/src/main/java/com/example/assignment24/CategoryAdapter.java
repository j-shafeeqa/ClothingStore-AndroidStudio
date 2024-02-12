package com.example.assignment24;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(CategoryModel category);
    }

    private Context context;
    private ArrayList<CategoryModel> categoryArrayList;
    private OnItemClickListener onItemClickListener;

    public CategoryAdapter(Context context, List<CategoryModel> categoryList, OnItemClickListener listener) {
        this.context = context;
        this.categoryArrayList = new ArrayList<>(categoryList);
        this.onItemClickListener = listener;
    }

    public CategoryAdapter(Context context, ArrayList<CategoryModel> categoryArrayList, OnItemClickListener listener) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
        this.onItemClickListener = listener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.category_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CategoryModel category = categoryArrayList.get(position);
        // Set the extracted price to the productPriceTextView
        holder.productPriceTextView.setText(category.getPrice());
        holder.productNameTextView.setText(category.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(category);
                }
            }
        });

        // Check if the image URL is not null or empty before loading with Picasso
        if (category.getImg_url1() != null && !category.getImg_url1().isEmpty()) {
            // Update the current image URL before loading
            holder.currentImageUrl = category.getImg_url1();

            Picasso.get()
                    .load(category.getImg_url1())
                    .into(holder.productimage);
        } else {
            // Handle the case where the image URL is null or empty
            holder.productimage.setImageResource(R.drawable.ic_launcher_background);
            holder.currentImageUrl = null;
        }
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        TextView productPriceTextView;
        ImageView productimage;
        String currentImageUrl;  // Keep track of the current image URL

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productimage = itemView.findViewById(R.id.productimage);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
        }
    }
}


