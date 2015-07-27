package com.cmu.delos.codenamealpha.model;

/**
 * Created by Vignan on 7/19/2015.
 */
public class Meal {
    private int mealId;
    private int kitchenId;
    private String dishName;
    private String dishIngredients;
    private String shortDesc;
    private String dishImage;
    private int mealCount;
    private double mealPrice;

    public Meal(int mealId, int kitchenId, String dishName, String shortDesc,
                String dishIngredients, String dishImage, int mealCount, double mealPrice){
        this.mealId=mealId;
        this.kitchenId=kitchenId;
        this.dishName = dishName;
        this.shortDesc=shortDesc;
        this.dishIngredients = dishIngredients;
        this.dishImage=dishImage;
        this.mealCount=mealCount;
        this.mealPrice = mealPrice;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public int getKitchenId() {
        return kitchenId;
    }

    public void setKitchenId(int kitchenId) {
        this.kitchenId = kitchenId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getDishImage() {
        return dishImage;
    }

    public void setDishImage(String dishImage) {
        this.dishImage = dishImage;
    }

    public String getDishIngredients() {
        return dishIngredients;
    }

    public void setDishIngredients(String dishIngredients) {
        this.dishIngredients = dishIngredients;
    }

    public int getMealCount() {
        return mealCount;
    }

    public void setMealCount(int mealCount) {
        this.mealCount = mealCount;
    }

    public double getMealPrice() {
        return mealPrice;
    }

    public void setMealPrice(double mealPrice) {
        this.mealPrice = mealPrice;
    }
}
