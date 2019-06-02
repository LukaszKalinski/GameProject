package com.example.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.game.LoginActivity.loggedUserName;

public class FirstLogin extends AppCompatActivity {

    TextView userNameTextView;
    TextView xcoordFirstVillage;
    TextView ycoordFirstVillage;
    public int xcoordRandom;
    public int ycoordRandom;
    public static int chosenVillageType = 0;
    TextView chosenVillageTypeNumber;
    Button firstVillageButton;
    Button firstVillageType;
    Button secondVillageType;
    Button thirdVillageType;
    public static VillageType firstChosenVillageType;
    GoodsDatabase goodsDatabase = new GoodsDatabase(this);
    VillagesDatabase villageDatabase = new VillagesDatabase(this);
    UserDetailDatabase userDetailDatabase = new UserDetailDatabase(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_login);

        userNameTextView = (TextView) findViewById(R.id.userNameTextView);
        userNameTextView.setText(loggedUserName);

        //Random village coordinates -------------------------------------------------------------
            xcoordRandom = (int) (Math.random()*100);
            ycoordRandom = (int) (Math.random()*100);

            System.out.println("X coord: " + xcoordRandom + ", Y coord: " + ycoordRandom);

            xcoordFirstVillage = (TextView) findViewById(R.id.xcoordFirstVillage);
            xcoordFirstVillage.setText("X = " + String.valueOf(xcoordRandom));
            ycoordFirstVillage = (TextView) findViewById(R.id.ycoordFirstVillage);
            ycoordFirstVillage.setText("Y = " + String.valueOf(ycoordRandom));

        //Chosen village type and finalizing choice -------------------------------------------------------------
            firstVillageType = (Button) findViewById(R.id.firstVillageType);
            secondVillageType = (Button) findViewById(R.id.secondVillageType);
            thirdVillageType = (Button) findViewById(R.id.thirdVillageType);
            chosenVillageTypeNumber = (TextView) findViewById(R.id.chosenVillageTypeNumber);
            chosenVillageTypeNumber.setText(String.valueOf(chosenVillageType));

            firstVillageType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chosenVillageType = 1;
                    chosenVillageTypeNumber.setText(String.valueOf(chosenVillageType));
                }
            });

            secondVillageType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chosenVillageType = 2;
                    chosenVillageTypeNumber.setText(String.valueOf(chosenVillageType));
                }
            });

            thirdVillageType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chosenVillageType = 3;
                    chosenVillageTypeNumber.setText(String.valueOf(chosenVillageType));
                }
            });

        //Clicking creating button -------------------------------------------------------------
            firstVillageButton = (Button) findViewById(R.id.firstVillageButton);
            firstVillageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (chosenVillageType == 0) {
                        Toast.makeText(FirstLogin.this,"Please click on some TYPE", Toast.LENGTH_LONG).show();
                    } else {
                        switch (chosenVillageType){
                            case 1:
                                firstChosenVillageType = new VillageType(xcoordRandom, ycoordRandom, "villageNearHighlands");
                                firstChosenVillageType.setStoneProductionIndex(2);
                                firstChosenVillageType.setGoldProductionIndex(2);
                                firstChosenVillageType.setSilverProductionIndex(2);
                                break;

                            case 2:
                                firstChosenVillageType = new VillageType(xcoordRandom, ycoordRandom, "villageNearRiver");
                                firstChosenVillageType.setWoodProductionIndex(2);
                                firstChosenVillageType.setFoodProductionIndex(2);
                                firstChosenVillageType.setWaterProductionIndex(2);
                                break;

                            case 3:
                                firstChosenVillageType = new VillageType(xcoordRandom, ycoordRandom, "villageNearWoods");
                                firstChosenVillageType.setStoneProductionIndex(2);
                                firstChosenVillageType.setWoodProductionIndex(2);
                                firstChosenVillageType.setFoodProductionIndex(2);
                                break;

                        }

                        createFirstVillageType();
                        createFirstGoodsDb();
                        createUserDetails();
                        Intent intent = new Intent(FirstLogin.this, GamePlay.class);
                        startActivity(intent);
                    }
                }
            });

    }

    public void createFirstGoodsDb(){
        goodsDatabase.open();
        if (goodsDatabase.getRecords().getCount()>0){
            System.out.println("Goods Database already exists");
        } else {
            int startingValue = 1000;
            goodsDatabase.AddGoodsFirstTime(startingValue, startingValue, startingValue , startingValue );
        }
        goodsDatabase.close();
    }

    public void createFirstVillageType(){
        villageDatabase.open();
        if (villageDatabase.getRecords().getCount()>0){
            System.out.println("Village Database already exists");
        } else {
            villageDatabase.AddVillageType(firstChosenVillageType);
        }
        villageDatabase.close();
    }

    public void createUserDetails(){
        userDetailDatabase.open();
        String villageType = firstChosenVillageType.getType();
        int xs = firstChosenVillageType.coordX;
        int ys = firstChosenVillageType.coordY;
        if (userDetailDatabase.getRecords().getCount()>0){
            System.out.println("User Details Database already exists");
        } else {
            userDetailDatabase.AddData(loggedUserName, villageType, xs, ys);
        }
        userDetailDatabase.close();
    }
}
