

import java.util.ArrayList;

public class CommodityDetailEntity {
    private int id;
    private int storeId;
    private ArrayList<String> images;
    private String name;
    private double price;
    private double discountPrice;
    private String description;
    private String descUrl;
    private ArrayList<CityCommodityEntity> commodityList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescUrl() {
        return descUrl;
    }

    public void setDescUrl(String descUrl) {
        this.descUrl = descUrl;
    }

    public ArrayList<CityCommodityEntity> getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(ArrayList<CityCommodityEntity> commodityList) {
        this.commodityList = commodityList;
    }
}
