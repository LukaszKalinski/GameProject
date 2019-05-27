package com.example.game;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import static com.example.game.FirstLogin.firstChosenVillageType;
import static com.example.game.LoginActivity.loggedUserName;

public class GamePlay extends AppCompatActivity {

    BuildingsDatabase buildingsDatabase = new BuildingsDatabase(this);
    ListView buildingsListView;
    Button raiseBuildLevelBtn;
    String[][] buildings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_play);

        buildingsListView = (ListView) findViewById(R.id.buildingsListView);
        raiseBuildLevelBtn = (Button) findViewById(R.id.raiseBuildLevelBtn);

        System.out.println("Logged as: " + loggedUserName);
        System.out.println("Village type is: " + firstChosenVillageType.getType());
        System.out.println("xCoord is: " + String.valueOf(firstChosenVillageType.coordX));
        System.out.println("yCoord is: " + String.valueOf(firstChosenVillageType.coordY));

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

        } else {
            //if it's already exist
            System.out.println("DATABASE IS ALREADY CREATED");
            System.out.println("Database has records: " + buildingsDatabase.getRecords().getCount());

        }

        refreshingBuildingsList();
        addingBuildingsToListView();

        raiseBuildLevelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                raiseBuildingLevel(String.valueOf(buildings[0][0]), Integer.parseInt(buildings[0][1]));
            }
        });

    }

    public void refreshingBuildingsList(){
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

        System.out.println("REFRESHED BUILDING LIST");
    }

    public void addingBuildingsToListView(){
        String[] bList = new String[buildings.length];
        for (int i = 0; i < buildings.length; i++) {
            bList[i] = (String.valueOf(buildings[i][0]) + " with level: " + String.valueOf(buildings[i][1]));
        }

        ArrayAdapter buildingListAdapter = new ArrayAdapter(this, R.layout.building_simple_row, bList);
        buildingsListView.setAdapter(buildingListAdapter);

        System.out.println("ADDED BUILDING LIST TO LISTVIEW");
    }

    public void raiseBuildingLevel(String name, int level){
        buildingsDatabase.open();
        buildingsDatabase.raiseCurrentBuildingLevelByOne(name, level);
        buildingsDatabase.close();

        System.out.println("RAISED LEVEL");
    }

    @Override
    public void onResume(){
        super.onResume();
        addingBuildingsToListView();

    }
}
