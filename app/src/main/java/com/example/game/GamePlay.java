package com.example.game;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import static com.example.game.FirstLogin.firstChosenVillageType;
import static com.example.game.LoginActivity.loggedUserName;

public class GamePlay extends AppCompatActivity {

    BuildingsDatabase buildingsDatabase = new BuildingsDatabase(this);
    GoodsDatabase goodsDatabase = new GoodsDatabase(this);
    ListView buildingsListView;
    String[][] buildings;
    ArrayAdapter buildingListAdapter;
    String[] goodsQuant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_play);

        buildingsListView = (ListView) findViewById(R.id.buildingsListView);

        System.out.println("Logged as: " + loggedUserName);
        System.out.println("Village type is: " + firstChosenVillageType.getType());
        System.out.println("xCoord is: " + String.valueOf(firstChosenVillageType.coordX));
        System.out.println("yCoord is: " + String.valueOf(firstChosenVillageType.coordY));

        getGoodsQuant();
        createAndFillDb();

        refreshingBuildingsList();
        addingBuildingsToListView();

        buildingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String listViewPosition = String.valueOf(position);
                sendingListViewPosition(listViewPosition);
            }
        });
        
    }

    @Override
    public void onResume(){
        super.onResume();
        refreshingBuildingsList();
        addingBuildingsToListView();


    }

    public void createAndFillDb(){
        //Creating & filling database

        buildingsDatabase.open();
        if (buildingsDatabase.getRecords().getCount()==0){
            //if it doesn't exist!
            System.out.println("DATABASE IS EMPTY");
            buildingsDatabase.AddBuilding(
                    "Quarry",
                    String.valueOf(firstChosenVillageType.coordX),
                    String.valueOf(firstChosenVillageType.coordY),
                    "0",
                    "QuarryMakeStones",
                    "Stone",
                    "300"
            );

            buildingsDatabase.AddBuilding(
                    "Forester",
                    String.valueOf(firstChosenVillageType.coordX),
                    String.valueOf(firstChosenVillageType.coordY),
                    "0",
                    "ForesterMakeWoods",
                    "Wood",
                    "300"
            );

            buildingsDatabase.AddBuilding(
                    "Homestead",
                    String.valueOf(firstChosenVillageType.coordX),
                    String.valueOf(firstChosenVillageType.coordY),
                    "0",
                    "HomesteadMakeFood",
                    "Food",
                    "300"
            );

            buildingsDatabase.AddBuilding(
                    "Deep well",
                    String.valueOf(firstChosenVillageType.coordX),
                    String.valueOf(firstChosenVillageType.coordY),
                    "0",
                    "DeepWellMakeWater",
                    "Water",
                    "300"
            );
            buildingsDatabase.close();

        } else {
//            if it's already exist
//            System.out.println("DATABASE IS ALREADY CREATED");
//            System.out.println("Database has records: " + buildingsDatabase.getRecords().getCount());

        }
    }

    public void refreshingBuildingsList(){
        buildingsDatabase.open();
        int xCoordColumnName = 1;
        int yCoordColumnName = 2;
        int nameColumnIndex = 3;
        int levelColumnName = 4;
        int descriptionColumnName = 5;
        int productColumnName = 6;
        int productionTimeColumnName = 7;
        Cursor cursor = buildingsDatabase.getRecords();
        buildings = new String[cursor.getCount()][10];

        if (cursor.moveToFirst()){
            for (int i = 0; i < cursor.getCount(); i++){
                buildings[i][0] = cursor.getString(nameColumnIndex);
                buildings[i][1] = cursor.getString(levelColumnName);
                buildings[i][2] = cursor.getString(descriptionColumnName);
                buildings[i][3] = cursor.getString(productColumnName);
                buildings[i][4] = cursor.getString(productionTimeColumnName);

                cursor.moveToNext();
            }
        }
        cursor.close();
        buildingsDatabase.close();

//        System.out.println("REFRESHED BUILDING LIST");
    }

    public void addingBuildingsToListView(){
        String[] bList = new String[buildings.length];
        for (int i = 0; i < buildings.length; i++) {
            bList[i] = (String.valueOf(buildings[i][0]) + " with level: " + String.valueOf(buildings[i][1]));
        }

        buildingListAdapter = new ArrayAdapter(this, R.layout.building_simple_row, bList);
        buildingsListView.setAdapter(buildingListAdapter);

//        System.out.println("ADDED BUILDING LIST TO LISTVIEW");
    }

    public void sendingListViewPosition(String position){
        Intent i = new Intent(this, BuildingChosen.class);
        i.putExtra("sendingPosition", position);
        i.putExtra("sendingName", buildings[Integer.parseInt(position)][0]);
        i.putExtra("sendingLevel", buildings[Integer.parseInt(position)][1]);
        i.putExtra("sendingDescription", buildings[Integer.parseInt(position)][2]);
        i.putExtra("sendingProduct", buildings[Integer.parseInt(position)][3]);
        i.putExtra("sendingProductionTime", buildings[Integer.parseInt(position)][4]);
        startActivity(i);
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
        System.out.println("Stone quant: " + goodsQuant[0] + ", wood: " + goodsQuant[1] + ", food: " + goodsQuant[2] + ", water: " + goodsQuant[3]);
    }

    public void raiseGoodsQuant(){
        int newStoneQuant = 100;
        int newWoodQuant = 110;
        int newFoodQuant = 120;
        int newWaterQuant = 130;
        goodsDatabase.open();
        goodsDatabase.raiseGoods(Integer.parseInt(goodsQuant[0]),Integer.parseInt(goodsQuant[1]),Integer.parseInt(goodsQuant[2]),Integer.parseInt(goodsQuant[3]),newStoneQuant, newWoodQuant, newFoodQuant, newWaterQuant);
        goodsDatabase.close();
        getGoodsQuant();
//        System.out.println("Stone quant: " + goodsQuant[0] + ", wood: " + goodsQuant[1] + ", food: " + goodsQuant[2] + ", water: " + goodsQuant[3]);
    }
}
