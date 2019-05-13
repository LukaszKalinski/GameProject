package com.example.game;

public class VillageType extends Village {

public String type;
public int stoneProductionIndex = 1;
public int woodProductionIndex = 1;
public int foodProductionIndex = 1;
public int goldProductionIndex = 1;
public int silverProductionIndex = 1;
public int waterProductionIndex = 1;
public int artifactProductionIndex = 1;

    public VillageType(int coordX, int coordY, String type) {
        super(coordX, coordY);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getStoneProductionIndex() {
        return stoneProductionIndex;
    }

    public void setStoneProductionIndex(int stoneProductionIndex) {
        this.stoneProductionIndex = stoneProductionIndex;
    }

    public int getWoodProductionIndex() {
        return woodProductionIndex;
    }

    public void setWoodProductionIndex(int woodProductionIndex) {
        this.woodProductionIndex = woodProductionIndex;
    }

    public int getFoodProductionIndex() {
        return foodProductionIndex;
    }

    public void setFoodProductionIndex(int foodProductionIndex) {
        this.foodProductionIndex = foodProductionIndex;
    }

    public int getGoldProductionIndex() {
        return goldProductionIndex;
    }

    public void setGoldProductionIndex(int goldProductionIndex) {
        this.goldProductionIndex = goldProductionIndex;
    }

    public int getSilverProductionIndex() {
        return silverProductionIndex;
    }

    public void setSilverProductionIndex(int silverProductionIndex) {
        this.silverProductionIndex = silverProductionIndex;
    }

    public int getWaterProductionIndex() {
        return waterProductionIndex;
    }

    public void setWaterProductionIndex(int waterProductionIndex) {
        this.waterProductionIndex = waterProductionIndex;
    }

    public int getArtifactProductionIndex() {
        return artifactProductionIndex;
    }

    public void setArtifactProductionIndex(int artifactProductionIndex) {
        this.artifactProductionIndex = artifactProductionIndex;
    }
}
