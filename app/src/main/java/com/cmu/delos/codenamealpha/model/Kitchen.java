package com.cmu.delos.codenamealpha.model;

/**
 * Created by Vignan on 7/19/2015.
 */
public class Kitchen {

    private int kitchenId;
    private int userId;
    private String shortDesc;
    private String kitchenImage;

    public Kitchen(int kitchenId,int userId,String shortDesc,String kitchenImage){
        this.userId=userId;
        this.kitchenId=kitchenId;
        this.shortDesc=shortDesc;
        this.kitchenImage=kitchenImage;
    }

    public int getKitchenId() {
        return kitchenId;
    }

    public void setKitchenId(int kitchenId) {
        this.kitchenId = kitchenId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getKitchenImage() {
        return kitchenImage;
    }

    public void setKitchenImage(String kitchenImage) {
        this.kitchenImage = kitchenImage;
    }
}
