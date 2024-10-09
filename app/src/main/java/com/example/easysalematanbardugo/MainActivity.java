package com.example.easysalematanbardugo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.easysalematanbardugo.adapters.ProductAdapter;
import com.example.easysalematanbardugo.db.ProductEntity;
import com.example.easysalematanbardugo.viewmodel.ProductViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity{
    
    private ProductViewModel productViewModel;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initViewModel();
        initUI();
        initAddButton();
    }

    private void initAddButton() {
        FloatingActionButton addButton = findViewById(R.id.menu_btn);
        addButton.setOnClickListener(v -> {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.add_product_dialog);
            dialog.show();

            EditText titleInput = dialog.findViewById(R.id.editTextTitle);
            EditText priceInput = dialog.findViewById(R.id.editTextPrice);
            EditText descInput = dialog.findViewById(R.id.editTextDescription);
            EditText categoryInput = dialog.findViewById(R.id.editTextCategory);

            handleDialogAddProduct(dialog, titleInput, priceInput, descInput, categoryInput);
        });
    }

    @SuppressWarnings("all")
    private void handleDialogAddProduct(
            @NonNull Dialog dialog, EditText titleInput, EditText priceInput, EditText descInput, EditText categoryInput) {
        dialog.findViewById(R.id.submitButton).setOnClickListener(v -> {
            String title = titleInput.getText().toString();
            float price = priceInput.getText().toString().isEmpty() ? 0 : Float.parseFloat(priceInput.getText().toString());
            String desc = descInput.getText().toString();
            String category = categoryInput.getText().toString();
            int lastIdItem = productAdapter.getProducts().get(productAdapter.getProducts().size() - 1).getId();
            String id  = lastIdItem + 1 + "";
            String imageUrl = "null";

            ProductEntity productEntity = new ProductEntity();
            productEntity.setId(Integer.parseInt(id));
            productEntity.setTitle(title);
            productEntity.setPrice(price);
            productEntity.setDescription(desc);
            productEntity.setCategory(category);
            productEntity.setImage(imageUrl);

            productViewModel.insertProduct(productEntity);
            productAdapter.notifyDataSetChanged();

            LottieAnimationView animationView = dialog.findViewById(R.id.animationView);
            animationView.setAnimation(R.raw.add_product);
            animationView.setVisibility(LottieAnimationView.VISIBLE);
            animationView.playAnimation();

            //disable button
            dialog.findViewById(R.id.submitButton).setEnabled(false);

            // Add an animator listener to dismiss the dialog after the animation finishes
            animationView.addAnimatorListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    dialog.dismiss(); // Dismiss the dialog
                }
            });

        });
    }

    private void initUI() {
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        productAdapter = new ProductAdapter();
        recyclerView.setAdapter(productAdapter);

        handleSwipeDeleteProduct(recyclerView);
    }

    @SuppressWarnings("all")
    private void handleSwipeDeleteProduct(RecyclerView recyclerView) {
        // Attach the swipe-to-delete helper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                ProductEntity product = productAdapter.getProducts().get(position);
                productViewModel.deleteProduct(product);
                productAdapter.deleteItem(position);

                productAdapter.notifyDataSetChanged();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initViewModel() {
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        loadAllProducts();
    }

    @SuppressWarnings("all")
    private void loadAllProducts() {
            productViewModel.getAllProducts().observe(this, productEntities -> {
                productAdapter.setProducts(productEntities);
                productAdapter.notifyDataSetChanged();
            });
    }

}