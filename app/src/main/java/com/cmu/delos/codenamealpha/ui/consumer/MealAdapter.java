package com.cmu.delos.codenamealpha.ui.consumer;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.util.ScalingUtilities;

import java.io.File;

import static com.cmu.delos.codenamealpha.util.ScalingUtilities.decodeResource;

/**
 * A recycler adapter class for loading data on to recycler view for cards on the application page
 * Created by Vignan on 7/27/2015.
 */
public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> implements View.OnClickListener {

    Cursor dataCursor;
    Context context;
    boolean isProvider;
    String dishName;
    String dishImage;
    String dishPrice;
    String dishQuantity;
    int kitchenId;

    public MealAdapter(Context context, Cursor c) {
        dataCursor = c;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(context).inflate(R.layout.meal_adapter, parent, false);

        ViewHolder viewHolder = new ViewHolder(context, view);
        view.setOnClickListener(this);
        viewHolder.delete.setOnClickListener(this);
        viewHolder.delete.setTag(viewHolder);


        view.setTag(viewHolder);
        return viewHolder;
    }

    public void onClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        Log.v("CLICKED Mealadapter",holder.kitchen_id.getText().toString() + ":" + holder.dish_name.getText().toString());
        Intent intent = new Intent(context, MealDetails.class);

        intent.putExtra("kitchenId", Integer.parseInt(holder.kitchen_id.getText().toString().trim()));
        intent.putExtra("dishName", holder.dish_name.getText().toString());
        context.startActivity(intent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        dataCursor.moveToPosition(position);
        dishName= dataCursor.getString(1).substring(0);
        dishImage= dataCursor.getString(2);
        dishPrice= '$'+dataCursor.getString(3);
        dishQuantity = "Quantity:\t" +dataCursor.getString(4);
        holder.dish_name.setText(dishName);
        kitchenId = dataCursor.getInt(6);

        holder.kitchen_id.setText(kitchenId+"");
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
        Log.v("kitchen Id", kitchenId+"");
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
        private Context context;
        protected CardView cardView;
        protected TextView dish_name;
        protected TextView dish_price;
        protected ImageView dish_image;
        protected TextView dish_quantity;
        protected ImageButton delete;
        protected TextView kitchen_id;

        public ViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            dish_name = (TextView) itemView.findViewById(R.id.dish_name);
            dish_image = (ImageView) itemView.findViewById(R.id.dish_image);
            dish_price = (TextView) itemView.findViewById(R.id.dish_price_text);
            dish_quantity = (TextView) itemView.findViewById(R.id.dish_quantity);
            kitchen_id = (TextView) itemView.findViewById(R.id.kitchen_id);
            delete = (ImageButton) itemView.findViewById(R.id.details_btn);

        }

    }
}