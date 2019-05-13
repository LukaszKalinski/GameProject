package com.example.game;

import android.media.Image;

public class Building extends Village{

    public int level;
    public String description;
    public String product;
    public String artifact;
    public int productionTime;
    public int promotionIndex;
    Image image;

    public Building(int coordX, int coordY, int level, String description, String product, int productionTime) {
        super(coordX, coordY);
        this.level = level;
        this.description = description;
        this.product = product;
        this.productionTime = productionTime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getArtifact() {
        return artifact;
    }

    public void setArtifact(String artifact) {
        this.artifact = artifact;
    }

    public int getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(int productionTime) {
        this.productionTime = productionTime;
    }

    public int getPromotionIndex() {
        return promotionIndex;
    }

    public void setPromotionIndex(int promotionIndex) {
        this.promotionIndex = promotionIndex;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}


