package com.example.phonebond.PackageViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonebond.Interface.ItemClickListener;
import com.example.phonebond.R;

public class chartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView productPrice;
    public TextView productName;
    public TextView productQuantity;

    private ItemClickListener itemClickListener;

    public chartViewHolder(@NonNull View itemView) {
        super(itemView);

        productPrice = itemView.findViewById(R.id.chart_product_price);
        productName = itemView.findViewById(R.id.chart_product_name);
        productQuantity = itemView.findViewById(R.id.chart_product_quantity);
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
