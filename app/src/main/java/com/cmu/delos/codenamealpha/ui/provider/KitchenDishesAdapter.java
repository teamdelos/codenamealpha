package com.cmu.delos.codenamealpha.ui.provider;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.util.ScalingUtilities;

import java.io.File;

import static com.cmu.delos.codenamealpha.util.ScalingUtilities.decodeResource;

/**
 * Created by Vignan on 7/27/2015.
 */
public class KitchenDishesAdapter extends RecyclerView.Adapter<KitchenDishesAdapter.ViewHolder> {

    Cursor dataCursor;
    Context ctx;
    View v ;

    public KitchenDishesAdapter(Context context, Cursor c) {
        dataCursor = c;
        ctx = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kitchen_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        dataCursor.moveToPosition(position);
        String dishName= dataCursor.getString(1).substring(0);
        String dishImage= dataCursor.getString(2);
        String dishPrice= '$'+dataCursor.getString(3);
        String dishQuantity = "Quantity:\t" +dataCursor.getString(4);
        holder.dish_name.setText(dishName);
        if(dishImage!=null){
            File imgFile = new File(dishImage);
            // Part 1: Decode image
            Bitmap unscaledBitmap = decodeResource(imgFile, 500, 400, ScalingUtilities.ScalingLogic.FIT);
            // Part 2: Scale image
            Bitmap scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, 500,
                    400, ScalingUtilities.ScalingLogic.FIT);
            unscaledBitmap.recycle();
            // Publish results
            holder.dish_image.setImageBitmap(scaledBitmap);
        }
        holder.dish_price.setText(dishPrice);
        holder.dish_quantity.setText(dishQuantity);
    }

    @Override
    public int getItemCount() {
        return (dataCursor == null) ? 0 : dataCursor.getCount();
    }

    public Cursor swapCursor(Cursor cursor) {
        if (dataCursor == cursor) {
            return null;
        }
        Cursor oldCursor = dataCursor;
        this.dataCursor = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        protected TextView dish_name;
        protected TextView dish_price;
        protected ImageView dish_image;
        protected TextView dish_quantity;
        public ViewHolder(View itemView) {
            super(itemView);
            dish_name =  (TextView) itemView.findViewById(R.id.dish_name);
            dish_image = (ImageView) itemView.findViewById(R.id.dish_image);
            dish_price =  (TextView) itemView.findViewById(R.id.dish_price_text);
            dish_quantity = (TextView) itemView.findViewById(R.id.dish_quantity);
        }
    }
}
