package com.example.easysalematanbardugo.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easysalematanbardugo.R;
import com.example.easysalematanbardugo.db.ProductEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("all")
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<ProductEntity> productEntities = new ArrayList<>();
    private OnItemClickListener listener;

    // override functions
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // on bind view holder function is called for each item in the list
        ProductEntity productEntity = productEntities.get(position);
        holder.id.setText("ID: " + productEntity.getId());
        holder.title.setText("Title: " + productEntity.getTitle());
        holder.price.setText("Price: " + productEntity.getPrice());
        holder.description.setText("Description: " + productEntity.getDescription());
        holder.category.setText("Category: " + productEntity.getCategory());

        // load image
        Glide.with(holder.image.getContext())
                .load(productEntity.getImage())
                .into(holder.image);

        // set on click listener for the item
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });

        // set on touch listener for the item
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Scale down the view when pressed
                        v.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).start();
                        return true; // Indicate that the touch event is consumed

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // Scale back to the original size when released
                        v.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                        return true; // Indicate that the touch event is consumed

                    default:
                        return false; // Let other events (like click) be handled
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return productEntities.size();
    }

    // setter and getter
    public void setProducts(List<ProductEntity> todoEntityList) {
        this.productEntities = todoEntityList;
        notifyDataSetChanged();
    }
    public List<ProductEntity> getProducts() {
        return productEntities;
    }

    public void deleteItem(int position) {
        ProductEntity productEntity = productEntities.get(position);
        productEntities.remove(productEntity);
        notifyItemRemoved(position);
    }

    // view holder class
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView title;
        private TextView price;
        private TextView description;
        private TextView category;
        private ImageView image;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            this.id = itemView.findViewById(R.id.id);
            this.title = itemView.findViewById(R.id.title);
            this.price = itemView.findViewById(R.id.price);
            this.description = itemView.findViewById(R.id.description);
            this.category = itemView.findViewById(R.id.category);
            this.image = itemView.findViewById(R.id.image);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
