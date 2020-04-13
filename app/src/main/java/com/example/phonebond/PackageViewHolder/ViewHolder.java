package com.example.phonebond.PackageViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonebond.Interface.ItemClickListener;
import com.example.phonebond.R;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView proName,proColour,proDesc,proPrice,proBond;

    public ImageView proImage;
    public ItemClickListener clickListener;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        proPrice = (TextView)itemView.findViewById(R.id.product_price);
        proName = (TextView) itemView.findViewById(R.id.product_name);
        proColour = (TextView) itemView.findViewById(R.id.product_colour);
        proImage =  (ImageView) itemView.findViewById(R.id.product_Image);
        proDesc = (TextView) itemView.findViewById(R.id.product_description);
        proBond = (TextView)itemView.findViewById(R.id.Bond_Name_address);





    }

    public void setItemClickListener(ItemClickListener listener){
        this.clickListener = listener;

    }

    @Override
    public void onClick(View view) {

        clickListener.onClick(view,getAdapterPosition(),false);

    }
}
