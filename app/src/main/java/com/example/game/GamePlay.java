package com.example.game;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class GamePlay extends AppCompatActivity {

    BuildingsDatabase buildingsDatabase = new BuildingsDatabase(this);
    GoodsDatabase goodsDatabase = new GoodsDatabase(this);
    VillagesDatabase villageDatabase = new VillagesDatabase(this);
    UserDetailDatabase userDb = new UserDetailDatabase(this);
    TextView gamePlayHeader;
    ListView buildingsListView;
    ArrayAdapter buildingListAdapter;
    public String[][] buildings;
    public String[] goodsQuant;
    public String[] villageType;
    public String[] userDatas;

    TextView stoneQuantTextView;
    TextView woodQuantTextView;
    TextView foodQuantTextView;
    TextView waterQuantTextView;
    Button quizGoodsBtn;

    Thread goodThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_play);

        buildingsListView = (ListView) findViewById(R.id.buildingsListView);
        gamePlayHeader = (TextView) findViewById(R.id.gamePlayHeader);
        stoneQuantTextView = (TextView) findViewById(R.id.stoneQuantTextView);
        woodQuantTextView = (TextView) findViewById(R.id.woodQuantTextView);
        foodQuantTextView = (TextView) findViewById(R.id.foodQuantTextView);
        waterQuantTextView = (TextView) findViewById(R.id.waterQuantTextView);
        quizGoodsBtn = (Button) findViewById(R.id.quizGoods);

        getUserData();
        gamePlayHeader.setText("Logged as player: " + userDatas[0]);

        getVillageType();
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

        quizGoodsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //QUIZ
                Intent intent = new Intent(GamePlay.this, QuizForGoods.class);
                startActivity(intent);
            }
        });

        goodsThreads(0);
        goodsThreads(1);
        goodsThreads(2);
        goodsThreads(3);

    }

    @Override
    public void onResume(){
        super.onResume();
        refreshingBuildingsList();
        addingBuildingsToListView();
        getGoodsQuant();

//        goodThread.interrupt();

        System.out.println("OPENED THREADS: " + String.valueOf(Thread.activeCount()));
//        resumeGoodsThreads();
        System.out.println("OPENED THREADS: " + String.valueOf(Thread.activeCount()));

    }

    public void createAndFillDb(){
        //Creating & filling database

        buildingsDatabase.open();
        if (buildingsDatabase.getRecords().getCount()==0){
            //if it doesn't exist!
            System.out.println("DATABASE IS EMPTY");
            buildingsDatabase.AddBuilding(
                    "Quarry",
                    String.valueOf(userDatas[2]),
                    String.valueOf(userDatas[3]),
                    "1",
                    "QuarryMakeStones",
                    "Stone",
                    "6000"
            );

            buildingsDatabase.AddBuilding(
                    "Forester",
                    String.valueOf(userDatas[2]),
                    String.valueOf(userDatas[3]),
                    "1",
                    "ForesterMakeWoods",
                    "Wood",
                    "6000"
            );

            buildingsDatabase.AddBuilding(
                    "Homestead",
                    String.valueOf(userDatas[2]),
                    String.valueOf(userDatas[3]),
                    "1",
                    "HomesteadMakeFood",
                    "Food",
                    "6000"
            );
            buildingsDatabase.AddBuilding(
                    "Deep well",
                    String.valueOf(userDatas[2]),
                    String.valueOf(userDatas[3]),
                    "1",
                    "DeepWellMakeWater",
                    "Water",
                    "6000"
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
        stoneQuantTextView.setText(String.valueOf(goodsQuant[0]));
        woodQuantTextView.setText(String.valueOf(goodsQuant[1]));
        foodQuantTextView.setText(String.valueOf(goodsQuant[2]));
        waterQuantTextView.setText(String.valueOf(goodsQuant[3]));
        System.out.println("Stone quant: " + goodsQuant[0] + ", wood: " + goodsQuant[1] + ", food: " + goodsQuant[2] + ", water: " + goodsQuant[3]);
    }

    public void raiseGoodsQuant(int newStoneQuant, int newWoodQuant, int newFoodQuant, int newWaterQuant){
        goodsDatabase.open();
        goodsDatabase.raiseGoods(Integer.parseInt(goodsQuant[0]),Integer.parseInt(goodsQuant[1]),Integer.parseInt(goodsQuant[2]),Integer.parseInt(goodsQuant[3]),newStoneQuant, newWoodQuant, newFoodQuant, newWaterQuant);
        goodsDatabase.close();
        getGoodsQuant();
        stoneQuantTextView.setText(String.valueOf(goodsQuant[0]));
        woodQuantTextView.setText(String.valueOf(goodsQuant[1]));
        foodQuantTextView.setText(String.valueOf(goodsQuant[2]));
        waterQuantTextView.setText(String.valueOf(goodsQuant[3]));
//        System.out.println("Stone quant: " + goodsQuant[0] + ", wood: " + goodsQuant[1] + ", food: " + goodsQuant[2] + ", water: " + goodsQuant[3]);
    }

    public void getVillageType(){
        int stoneIndex = 2;
        int woodIndex = 3;
        int foodIndex = 4;
        int waterIndex = 5;
        int silverIndex = 7;
        int goldIndex = 6;
        int artifactIndex = 8;
        villageDatabase.open();
        Cursor cursor = villageDatabase.getRecords();
        villageType = new String[7];
        cursor.moveToFirst();
        villageType[0] = cursor.getString(stoneIndex);
        villageType[1] = cursor.getString(woodIndex);
        villageType[2] = cursor.getString(foodIndex);
        villageType[3] = cursor.getString(waterIndex);
        villageType[4] = cursor.getString(silverIndex);
        villageType[5] = cursor.getString(goldIndex);
        villageType[6] = cursor.getString(artifactIndex);
        villageDatabase.close();
        System.out.println("VillageDB - Stone Index: " + villageType[0] +
                        ", Wood Index: " + villageType[1] +
                        ", Food Index: " + villageType[2] +
                        ", Water Index: " + villageType[3] +
                        ", Silver Index: " + villageType[4] +
                        ", Gold Index: " + villageType[5] +
                        ", Artifact Index: " + villageType[6]

                );
    }

    public void getUserData(){
        userDb.open();
        Cursor cursor = userDb.getRecords();
        cursor.moveToFirst();
        userDatas = new String[4];
                userDatas[0] = cursor.getString(1);
                userDatas[1] = cursor.getString(2);
                userDatas[2] = cursor.getString(3);
                userDatas[3] = cursor.getString(4);
        userDb.close();
        System.out.println("User: " + userDatas[0] +
                ", village: " + userDatas[1] +
                ", xCoord: " + userDatas[2] +
                ", yCoord: " + userDatas[3]);
    }

    public void goodsThreads(int i){
        final int number = i;
        goodThread = new Thread() {

            final int timeStamp = Integer.parseInt(buildings[number][4])*1000;
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(timeStamp);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("THREAD building: " + buildings[number][0] + ", productionTime: " + buildings[number][4] );
                                getGoodsQuant();

                                switch (number){
                                    case 0:
                                        raiseGoodsQuant(1,0,0,0);
                                        break;
                                    case 1:
                                        raiseGoodsQuant(0,1,0,0);
                                        break;
                                    case 2:
                                        raiseGoodsQuant(0,0,1,0);
                                        break;
                                    case 3:
                                        raiseGoodsQuant(0,0,0,1);
                                        break;
                                }

//                                boolean ifTimeStampChange = (timeStamp) == Integer.parseInt(buildings[number][4])*1000;
//                                System.out.println(String.valueOf(ifTimeStampChange));
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        goodThread.start();

        //TODO update productionTime on resume
    }

    public void resumeGoodsThreads(){
//        goodsThreads(0);
//        goodsThreads(1);
//        goodsThreads(2);
//        goodsThreads(3);
    }
}
