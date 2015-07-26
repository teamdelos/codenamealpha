package com.cmu.delos.codenamealpha.ui;

import android.support.v7.app.AppCompatActivity;

import com.cmu.delos.codenamealpha.model.Kitchen;
import com.cmu.delos.codenamealpha.model.Meal;
import com.cmu.delos.codenamealpha.model.User;

/**
 * Created by Mrik on 7/24/15.
 */
public class AbstractAlphaActivity extends AppCompatActivity {
    private static User userDetails;
    private static Kitchen kitchenDetails;
    private static Meal mealDetails;

    public void createUser(int userId,String firstName,String lastName,String email,String isProvider){
        userDetails = new User(userId,firstName,lastName,email,isProvider);
    }

    public User getUser(){
        return userDetails;
    }

    public void createKitchen(int kitchenId,int userId,String shortDesc,String kitchenImage){
        kitchenDetails = new Kitchen(kitchenId, userId, shortDesc, kitchenImage);
    }

    public Kitchen getKitchen(){
        return kitchenDetails;
    }

    public void createMeal(int mealId,int kitchenId,String dishName,
                           String shortDesc, String dishIng, String dishImage,
                           int mealCount, double mealPrice){
        mealDetails = new Meal(mealId, kitchenId, dishName,
                            shortDesc, dishIng, dishImage,
                            mealCount,mealPrice);
    }

    public Meal getMeal(){
        return mealDetails;
    }

}
