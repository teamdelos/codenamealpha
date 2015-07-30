package com.cmu.delos.codenamealpha.ui.provider;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cmu.delos.codenamealpha.R;
import com.cmu.delos.codenamealpha.util.ScalingUtilities;

import java.io.File;

import static com.cmu.delos.codenamealpha.util.ScalingUtilities.decodeResource;

/**
 * Created by Vignan on 7/27/2015.
 */
public class KitchenDishesAdapter extends RecyclerView.Adapter<KitchenDishesAdapter.ViewHolder> implements View.OnClickListener {

    Cursor dataCursor;
    Context context;

    String dishName;
    String dishImage;
    String dishPrice;
    String dishQuantity;
    int dish_is_listed;

    public KitchenDishesAdapter(Context context, Cursor c) {
        dataCursor = c;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(context).inflate(R.layout.kitchen_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(context, view);
//        ViewHolder viewHolder = new ViewHolder(context, view, new KitchenDishesAdapter.ViewHolder.ICardViewHolderClicks() {
//            @Override
//            public void onSwitch(CompoundButton buttonView, boolean isChecked, String dishName) {
//                if(isChecked){
//                    Log.i("Switch Working", String.valueOf(isChecked)+ dishName);
//                }{
//                    Log.i("Switch Working", String.valueOf(isChecked)+dishName);
//                }
//
//            }
//        });
        view.setOnClickListener(this);
        viewHolder.dish_is_listed.setOnClickListener(this);

        viewHolder.dish_is_listed.setTag(viewHolder);
        view.setTag(viewHolder);
        return viewHolder;
    }

    public void onClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        int position = holder.getPosition();

        if (view.getId() == holder.dish_is_listed.getId()){
           Toast.makeText(context, "Switch button onClick at" + position+holder.dish_is_listed.isChecked(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        dataCursor.moveToPosition(position);
        dishName= dataCursor.getString(1).substring(0);
        dishImage= dataCursor.getString(2);
        dishPrice= '$'+dataCursor.getString(3);
        dishQuantity = "Quantity:\t" +dataCursor.getString(4);
        dish_is_listed = dataCursor.getInt(5);
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
        Log.i("Meals",dishName+" "+dish_is_listed);
        holder.dish_is_listed.setChecked(dish_is_listed == 0 ? false : true);
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

//    public static class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private Context context;
        protected CardView cardView;
        protected TextView dish_name;
        protected TextView dish_price;
        protected ImageView dish_image;
        protected TextView dish_quantity;
        protected Switch dish_is_listed;
//        public ICardViewHolderClicks mListener;

//        public ViewHolder(Context context, View itemView, ICardViewHolderClicks listener) {
        public ViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
//            mListener = listener;
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            dish_name = (TextView) itemView.findViewById(R.id.dish_name);
            dish_image = (ImageView) itemView.findViewById(R.id.dish_image);
            dish_price = (TextView) itemView.findViewById(R.id.dish_price_text);
            dish_quantity = (TextView) itemView.findViewById(R.id.dish_quantity);
            dish_is_listed = (Switch) itemView.findViewById(R.id.dish_listed_switch);
//            dish_is_listed.setOnCheckedChangeListener(this);
        }

//        @Override
//        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            mListener.onSwitch(buttonView, isChecked, dish_name.getText().toString());
//        }
//
//        public static interface ICardViewHolderClicks {
//            public void onSwitch(CompoundButton buttonView, boolean isChecked, String dishName);
//        }
    }
}