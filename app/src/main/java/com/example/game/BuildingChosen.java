package com.example.game;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BuildingChosen extends AppCompatActivity {

    int clickedPosition;
    String clickedName;
    int clickedLevel;
    String clickedDescription;
    String clickedProduct;
    int clickedProductionTime;
    BuildingsDatabase buildingsDatabase = new BuildingsDatabase(this);

    TextView buildingDetailHeader;
    TextView buildingDetailDescription;
    TextView buildingDetailProduct;
    TextView buildingDetailProductionTime;
    TextView buildingDetailLevel;
    Button expandBuildingBtn;

    GoodsDatabase goodsDatabase = new GoodsDatabase(this);
    String[] goodsQuant;

    public int neededStone;
    public int neededWood;
    public int neededFood;
    public int neededWater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.building_chosen);

        Bundle extras = getIntent().getExtras();
        clickedPosition = Integer.parseInt(extras.getString("sendingPosition"));
        clickedName = extras.getString("sendingName");
        clickedLevel = Integer.parseInt(extras.getString("sendingLevel"));
        clickedDescription = extras.getString("sendingDescription");
        clickedProduct = extras.getString("sendingProduct");
        clickedProductionTime = Integer.parseInt(extras.getString("sendingProductionTime"));
        System.out.println("SEND POSITION: " + String.valueOf(clickedPosition) +
                ", SEND NAME: " + clickedName +
                ", SEND LEVEL: " + String.valueOf(clickedLevel) +
                ", SEND DESCRIPTION: " + clickedDescription +
                ", SEND PRODUCT: " + clickedProduct +
                ", SEND PRODUCTIONTIME: " + String.valueOf(clickedProductionTime));

        buildingDetailHeader = (TextView) findViewById(R.id.buildingDetailHeader);
        buildingDetailHeader.setText(String.valueOf(clickedName));
        buildingDetailDescription = (TextView) findViewById(R.id.buildingDetailDescription);
        buildingDetailDescription.setText(String.valueOf(clickedDescription));
        buildingDetailProduct = (TextView) findViewById(R.id.buildingDetailProduct);
        buildingDetailProduct.setText(String.valueOf(clickedProduct));
        buildingDetailProductionTime = (TextView) findViewById(R.id.buildingDetailProductionTime);
        buildingDetailProductionTime.setText(String.valueOf(clickedProductionTime));
        buildingDetailLevel = (TextView) findViewById(R.id.buildingDetailLevel);
        buildingDetailLevel.setText(String.valueOf(clickedLevel));
        expandBuildingBtn = (Button) findViewById(R.id.expandBuildingBtn);

        expandBuildingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                raiseBuildingLevel(clickedName, clickedLevel, clickedProductionTime);
                finish();
            }
        });

    }

    public void raiseBuildingLevel(String name, int level, int productionTime){
        getGoodsQuant();
        int currentStone = Integer.parseInt(goodsQuant[0]);
        int currentWood = Integer.parseInt(goodsQuant[1]);
        int currentFood = Integer.parseInt(goodsQuant[2]);
        int currentWater = Integer.parseInt(goodsQuant[3]);
        int needed;
        int defaultNeed = -200;


        switch (level){ //from level x to level+1
            case 1:
                needed = defaultNeed;
                neededStone = needed;
                neededWood = needed;
                neededFood = needed;
                neededWater = needed;
                break;
            case 2:
                needed = defaultNeed * 2;
                neededStone = needed;
                neededWood = needed;
                neededFood = needed;
                neededWater = needed;
                break;
            case 3:
                needed = defaultNeed * 4;
                neededStone = needed;
                neededWood = needed;
                neededFood = needed;
                neededWater = needed;
                break;
            case 4:
                needed = defaultNeed * 8;
                neededStone = needed;
                neededWood = needed;
                neededFood = needed;
                neededWater = needed;
                break;
            case 5:
                needed = defaultNeed * 16;
                neededStone = needed;
                neededWood = needed;
                neededFood = needed;
                neededWater = needed;
                break;
            case 6:
                needed = defaultNeed * 32;
                neededStone = needed;
                neededWood = needed;
                neededFood = needed;
                neededWater = needed;
                break;
            case 7:
                needed = defaultNeed * 64;
                neededStone = needed;
                neededWood = needed;
                neededFood = needed;
                neededWater = needed;
                break;
        }

        if (-neededStone <= currentStone && -neededWood <= currentWood && -neededFood <= currentFood && -neededWater <= currentWater){
            buildingsDatabase.open();
            buildingsDatabase.raiseCurrentBuildingLevelByOne(name, level, productionTime);
            raiseGoodsQuant(neededStone, neededWood, neededFood, neededWater);
            buildingsDatabase.close();
            Toast.makeText(BuildingChosen.this,"Level Raised", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(BuildingChosen.this,"You Do Not Have Enough Goods", Toast.LENGTH_LONG).show();
        }


    }

    public void getGoodsQuant(){
        int stoneQuant = 1;
        int woodQuant = 2;
        int foodQuant = 3;
        int waterQuant = 4;
        goodsDatabase.open();
        Cursor cursor = goodsDatabase.getRecords();
        goodsQuant = new String[6];
        cursor.moveToFirst();
        goodsQuant[0] = cursor.getString(stoneQuant);
        goodsQuant[1] = cursor.getString(woodQuant);
        goodsQuant[2] = cursor.getString(foodQuant);
        goodsQuant[3] = cursor.getString(waterQuant);
        cursor.close();
        goodsDatabase.close();
    }

    public void raiseGoodsQuant(int newStoneQuant, int newWoodQuant, int newFoodQuant, int newWaterQuant){
        goodsDatabase.open();
        goodsDatabase.raiseGoods(Integer.parseInt(goodsQuant[0]),Integer.parseInt(goodsQuant[1]),Integer.parseInt(goodsQuant[2]),Integer.parseInt(goodsQuant[3]),newStoneQuant, newWoodQuant, newFoodQuant, newWaterQuant);
        goodsDatabase.close();
        getGoodsQuant();
    }
}
