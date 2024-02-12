package com.example.assignment24;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    Context context;
    List<MyCartModel> cartModelList;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    // Constructor that accepts a List<MyCartModel>
    public MyCartAdapter(Context context, List<MyCartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, int position) {
        holder.name.setText(cartModelList.get(position).getName());
        holder.price.setText(cartModelList.get(position).getPrice());
        holder.quantity.setText(cartModelList.get(position).getTotalQuantity());
        holder.totalPrice.setText(String.valueOf(cartModelList.get(position).getTotalPrice()));
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    // Log the documentId before attempting to delete
                    String documentId = cartModelList.get(currentPosition).getDocumentId();
                    Log.d("MyCartAdapter", "Deleting document with ID: " + documentId);

                    // Start the delete operation
                    firestore.collection("CurrentUser")
                            .document(auth.getCurrentUser().getUid())
                            .collection("AddToCart")
                            .document(documentId)
                            .delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("MyCartAdapter", "Item deleted successfully");
                                        cartModelList.remove(currentPosition);
                                        notifyItemRemoved(currentPosition);
                                        Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.e("MyCartAdapter", "Error deleting item: " + task.getException().getMessage());
                                        Toast.makeText(context, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


        // Load the product image using Picasso
        Picasso.get()
                .load(cartModelList.get(position).getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imageUrl);
    }


    @Override
    public int getItemCount() {return cartModelList.size();}

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,price,quantity,totalPrice;
        ImageView imageUrl;
        ImageView deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.productNameTextView);
            price = itemView.findViewById(R.id.productPriceTextView);
            quantity = itemView.findViewById(R.id.productQuantityTextView);
            totalPrice = itemView.findViewById(R.id.productTotalTextView);
            imageUrl = itemView.findViewById(R.id.productImageView);
            deleteButton = itemView.findViewById(R.id.deleteButton);

        }
    }
}
